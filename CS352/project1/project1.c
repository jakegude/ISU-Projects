#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <signal.h>

#define MAX_LINE 80
#define MAX_ARGS (MAX_LINE/2 + 1)
#define REDIRECT_OUT_OP '>'
#define REDIRECT_IN_OP '<'
#define PIPE_OP '|'
#define BG_OP '&'
#define MAX_JOB_COUNT 15

//used for jobs
typedef struct Node {
    pid_t pid;
    int jid;
    int background;
    int intstatus;
	char* stringstatus;
	char* cmd;
} Node;

/* Holds a single command. */
typedef struct Cmd {
	/* The command as input by the user. */
	char line[MAX_LINE + 1];
	/* The command as null terminated tokens. */
	char tokenLine[MAX_LINE + 1];
	/* Pointers to each argument in tokenLine, non-arguments are NULL. */
	char* args[MAX_ARGS];
	/* Pointers to each symbol in tokenLine, non-symbols are NULL. */
	char* symbols[MAX_ARGS];
	/* The process id of the executing command. */
	pid_t pid;
} Cmd;

/* The process of the currently executing foreground command, or 0. */
pid_t foregroundPid = 0;
Node* jobs[MAX_JOB_COUNT];
int jobCount = 0;

//initialize jobs variable
void initialize() {
	for (int i = 0; i < MAX_JOB_COUNT; i++) {
		jobs[i] = (Node*) malloc(sizeof(Node));
	}
}

/* Parses the command string contained in cmd->line.
 * * Assumes all fields in cmd (except cmd->line) are initailized to zero.
 * * On return, all fields of cmd are appropriatly populated. */
void parseCmd(Cmd* cmd) {
	char* token;
	int i=0;
	strcpy(cmd->tokenLine, cmd->line);
	strtok(cmd->tokenLine, "\n");
	token = strtok(cmd->tokenLine, " ");
	while (token != NULL) {
		if (*token == '\n') {
			cmd->args[i] = NULL;
		} else if (*token == REDIRECT_OUT_OP || *token == REDIRECT_IN_OP
				|| *token == PIPE_OP || *token == BG_OP) {
			cmd->symbols[i] = token;
			cmd->args[i] = NULL;
		} else {
			cmd->args[i] = token;
		}
		token = strtok(NULL, " ");
		i++;
	}
	cmd->args[i] = NULL;
}

/* Finds the index of the first occurance of symbol in cmd->symbols.
 * * Returns -1 if not found. */
int findSymbol(Cmd* cmd, char symbol) {
	for (int i = 0; i < MAX_ARGS; i++) {
		if (cmd->symbols[i] && *cmd->symbols[i] == symbol) {
			return i;
		}
	}
	return -1;
}

/* Signal handler for SIGTSTP (SIGnal - Terminal SToP),
 * which is caused by the user pressing control+z. */
void sigtstpHandler(int sig_num) {
	initialize();
	/* Reset handler to catch next SIGTSTP. */
	signal(SIGTSTP, sigtstpHandler);
	if (foregroundPid > 0) {
		/* Foward SIGTSTP to the currently running foreground process. */
		kill(foregroundPid, SIGTSTP);
		/* TODO: Add foreground command to the list of jobs. */
		int status = 0;
		waitpid(foregroundPid, &status, WNOHANG);
		Node* newjob = (Node*) malloc(sizeof(Node));
		newjob->jid = jobCount;
		newjob->pid = foregroundPid;
		//newjob->cmd = cmd->line;
		if (WIFEXITED(status) == 1) {
			newjob->stringstatus = "Stopped";
		} else {
			newjob->stringstatus = "Running";
		}
		newjob->intstatus = status;
		jobs[newjob->jid] = newjob;
		jobCount++;
	}
}

int main(void) {
	/* Listen for control+z (suspend process). */
	signal(SIGTSTP, sigtstpHandler);
	while (1) {
		printf("352> ");
		fflush(stdout);
		Cmd *cmd = (Cmd*) calloc(1, sizeof(Cmd));
		fgets(cmd->line, MAX_LINE, stdin);
		parseCmd(cmd);
		pid_t pid;
		
		if (!cmd->args[0]) {
			free(cmd);
		} else if (strcmp(cmd->args[0], "exit") == 0) {
			//exit the program
			free(cmd);
			exit(0);
			break;
		} else if (strcmp(cmd->args[0], "jobs") == 0) {
			//output the list of jobs
			for (int i = 0; i < jobCount; i++) {
                printf("[%d] %s %s\n", jobs[i]->jid, jobs[i]->stringstatus, jobs[i]->cmd);
            }
		} else if (strcmp(cmd->args[0], "bg") == 0) {
			//continue a stopped command
			int jobToStart = cmd->args[1];
			for (int i = 0; i < MAX_JOB_COUNT; i++) {
				if (jobs[i]->jid == jobToStart) {
					kill(jobs[i]->pid, SIGCONT);
					jobs[i]->stringstatus = "Running";
				}
			}

		} else {
			if (findSymbol(cmd, BG_OP) != -1) {
				/* TODO: Run command in background. */
				//here the command is done in the background
				pid = fork();
				if (pid < 0) {
					printf("Process Failed\n");
				} else if (pid == 0) {
					//child executing the command
					execvp(cmd->args[0], cmd->args);
				} else {
					//parent adding the job to the job list and waiting for the child
					int status = 0;
					waitpid(pid, &status, WNOHANG);
					Node* newjob = (Node*) malloc(sizeof(Node));
					newjob->jid = jobCount;
					newjob->pid = pid;
					newjob->cmd = cmd->line;
					printf("[%d] %s\n", newjob->jid, newjob->cmd);
					if (WIFEXITED(status) == 1) {
						newjob->stringstatus = "Stopped";
					} else {
						newjob->stringstatus = "Running";
					}
					newjob->intstatus = status;
					jobs[jobCount] = newjob;
					jobCount++;
					if (setpgid(pid, 0) != 0) {
						perror("setpgid() error");
					}
				}
			} else if (findSymbol(cmd, REDIRECT_OUT_OP) != -1) {
				//here the command is a output redirection
				int sym = findSymbol(cmd, REDIRECT_OUT_OP);
				pid = fork();
				if (pid < 0) {
					printf("Process Failed\n");
				} else if (pid == 0) {
					//child opens or creates a a file, switches the file descriptors
					int file = open(cmd->args[sym+1], O_CREAT | O_TRUNC | O_WRONLY, 0777);
					if (file == -1) { printf("File not created.\n"); break; }
					dup2(file, STDOUT_FILENO);
					close(file);
					execvp(cmd->args[0], cmd->args);
				} else {
					//parent waits for child to finish
					wait(NULL);
				}
			} else if (findSymbol(cmd, REDIRECT_IN_OP) != -1) {
				//here is a input redirection
				int sym = findSymbol(cmd, REDIRECT_IN_OP);
				pid = fork();
				if (pid < 0) {
					printf("Process Failed\n");
				} else if (pid == 0) {
					//child opens file to be read and taken as input and switches file descripors
					int file = open(cmd->args[sym-1], O_RDONLY);
					if (file == -1) { printf("File not read.\n"); break; }
					dup2(file, STDIN_FILENO);
					close(file);
					execvp(cmd->args[0], cmd->args);
				} else {
					//parent waits for child to finish
					wait(NULL);
				}
			} else if (findSymbol(cmd, PIPE_OP) != -1) {
				//pipes
				int fd[2];
				if (pipe(fd) < 0) { printf("Piping Error.\n"); break; }
				int sym = findSymbol(cmd, PIPE_OP);
				//creates arrays for each side of the pipe and assigns each accordingly
				char* leftargs[sym];
				char* rightargs[MAX_ARGS];
				for (int i = 0; i < sym; i++) {
					leftargs[i] = cmd->args[i];
				}
				int j = 0;
				for (int i = sym+1; i < MAX_ARGS; i++) {					
					if (cmd->args[i] != NULL) {
						rightargs[j] = cmd->args[i];
						j++;
					} 
				}
				rightargs[j] = NULL;
				
				//'leftargs' process is made
				int pid = fork();
				if (pid < 0) {
					printf("Process Failed\n");
					break;
				} 
				if (pid == 0) {
					close(fd[0]);
					dup2(fd[1], STDOUT_FILENO);
					close(fd[1]);
					execvp(leftargs[0], leftargs);
				}
				//'rightargs' process is made
				int pid1 = fork();
				if (pid1 < 0) {
					printf("Process Failed\n");
					break;
				} 
				if (pid1 == 0) {
					close(fd[1]);
					dup2(fd[0], STDIN_FILENO);
					close(fd[0]);
					execvp(rightargs[0], rightargs);
				}

				close(fd[0]);
				close(fd[1]);
				waitpid(pid, NULL, 0);
				waitpid(pid1, NULL, 0);

			} else {
				/* TODO: Run command in foreground. */
				pid = fork();
				if (pid < 0) {
					printf("Process Failed\n");
				} else if (pid == 0) {
					execvp(cmd->args[0], cmd->args);
				} else {
					wait(NULL);
				}
			}
		}

		/* TODO: Check on status of background processes. */
		for (int i = 0; i < jobCount; i++) {
			int curstatus = jobs[i]->intstatus;
			int curpid = jobs[i]->pid;
			waitpid(jobs[i]->pid, &jobs[i]->intstatus, WNOHANG);
			if (curstatus != jobs[i]->intstatus) {
				if (WIFEXITED(jobs[i]->intstatus)){
					printf("[%d] Done", jobs[i]->jid);
				}
			}

		}

	}
	return 0;
}
