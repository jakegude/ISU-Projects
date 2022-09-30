//Jake Gudenkauf

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>
#include <unistd.h>
#include <ctype.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <semaphore.h>
#include "encrypt-module.h"

//////////////////////
// GLOBAL VARIABLES //
//////////////////////
int inputBufNum = 0;
int outputBufNum = 0;
int bufferIndex = 0;
int curChar = 0;
char* inputBuffer;
char* outputBuffer;

/////////////////////////////////////
// MUTEXES AND CONDITION VARIABLES //
/////////////////////////////////////
pthread_mutex_t inmutex;
pthread_mutex_t outmutex;
// pthread_mutex_t readLock;
// pthread_mutex_t inputCountLock;
// pthread_mutex_t encryptLock;
// pthread_mutex_t outputCountLock;
// pthread_mutex_t writeLock;
// pthread_cond_t readCondition;
// pthread_cond_t inputCountCondition;
// pthread_cond_t encryptCondition;
// pthread_cond_t outputCountCondition;
// pthread_cond_t writeCondition;
sem_t inputCountReady;
sem_t inputCountDone;
sem_t encryptReady;
sem_t encryptDone;
sem_t outputCountReady;
sem_t outputCountDone;
sem_t writeReady;
sem_t writeDone;

/* Must Implement */
void reset_requested() {
	printf("Reset Requested.\n");
	printf("Total input count with current key is: %d.\n", get_input_total_count());
	for (int i = 65; i <= 90; i++) {
		printf("%c:%d ", i, get_input_count(i));
	}
}

/* Must Implement */
void reset_finished() {
	printf("\nTotal input count with current key is: %d.\n", get_output_total_count());
	for (int i = 65; i <= 90; i++) {
		printf("%c:%d ", i, get_output_count(i));
	}
	printf("\nReset Finished.\n");
}

/* function for read thread */
void* read_thread() {
    while (curChar != EOF) {
        // pthread_cond_wait(&writeCondition, &writeLock);
        // pthread_mutex_lock(&readLock);
        sem_wait(&inputCountDone);
        // for (int i = 0; i < inputBufNum; i++) {
            pthread_mutex_lock(&inmutex);
            curChar = read_input();
            inputBuffer[(bufferIndex % inputBufNum)] = curChar;
            bufferIndex++;
            pthread_mutex_unlock(&inmutex);
        // }
        sem_post(&inputCountReady);
        sem_post(&encryptReady);
        // pthread_mutex_unlock(&readLock);
        // pthread_cond_signal(&readCondition);
    }
}

/* function for input counter thread */
void* inputcount_thread() {
    while (1) {
        // pthread_cond_wait(&readCondition, &readLock);
        // pthread_mutex_lock(&inputCountLock);
        sem_wait(&inputCountReady);
        // for (int i = 0; i < inputBufNum; i++) {
            pthread_mutex_lock(&inmutex);
            // count_input(inputBuffer[i % inputBufNum]);
            count_input(inputBuffer[bufferIndex % inputBufNum]);
            pthread_mutex_unlock(&inmutex);
        // }
        sem_post(&inputCountDone);
        // pthread_mutex_unlock(&inputCountLock);
        // pthread_cond_signal(&inputCountCondition);
    }
}

/* function for encryptor thread */
void* encryptor_thread() {
    while (1) {    
        // pthread_cond_wait(&inputCountCondition, &inputCountLock);
        // pthread_mutex_lock(&encryptLock)
        sem_wait(&writeDone);
        sem_wait(&outputCountDone);
        sem_wait(&inputCountDone);
        sem_wait(&encryptReady);
        // int i = 0;
        // int j = 0;
        // while (i < inputBufNum || j < outputBufNum) {
            pthread_mutex_lock(&inmutex);
            pthread_mutex_lock(&outmutex);
            // outputBuffer[j % outputBufNum] = caesar_encrypt(inputBuffer[i % inputBufNum]);
            outputBuffer[bufferIndex % outputBufNum] = caesar_encrypt(inputBuffer[bufferIndex % inputBufNum]);
            pthread_mutex_unlock(&inmutex);
            pthread_mutex_unlock(&outmutex);
        //     j++;
        //     i++;
        // }
        sem_post(&encryptDone);
        sem_post(&inputCountReady);
        sem_post(&outputCountReady);
        sem_post(&writeReady);
        // pthread_mutex_unlock(&encryptLock);
        // pthread_cond_signal(&encryptCondition);
    }
}

/* function for output counter thread */
void* outputcount_thread() {
    // int i = 0;
    while (1) {
        // pthread_cond_wait(&encryptCondition, &encryptLock);
        // pthread_mutex_lock(&outputCountLock);
        sem_wait(&outputCountReady);
        // for (int i = 0; i < outputBufNum; i++) {
            pthread_mutex_lock(&outmutex);
            // count_output(outputBuffer[i % outputBufNum]);
            count_output(outputBuffer[bufferIndex % outputBufNum]);
            pthread_mutex_unlock(&outmutex);
        // }
        sem_post(&outputCountDone);
        // pthread_mutex_unlock(&outputCountLock);
        // pthread_cond_signal(&outputCountCondition);
    }
}

/*  function for writer thread */
void* writer_thread() {
    // int i = 0;
    while (1) {
        // pthread_cond_wait(&outputCountCondition, &outputCountLock);
        // pthread_mutex_lock(&writeLock);
        sem_wait(&encryptDone);
        sem_wait(&writeReady);
        // for (int i = 0; i < outputBufNum; i++) {
            pthread_mutex_lock(&outmutex);
            // write_output(outputBuffer[i % outputBufNum]);
            write_output(outputBuffer[bufferIndex % outputBufNum]);
            pthread_mutex_unlock(&outmutex);
        // }
        sem_post(&writeDone);
        sem_post(&encryptReady);
        // pthread_mutex_unlock(&writeLock);
        // pthread_cond_signal(&writeCondition);
    }
}

/* Main Method */
int main(int argc, char** argv) {

    /* check for correct usage of inputs */
    if (argc != 3) {
        printf("Incorrect number of arguments,\n Correct usage is \"./encrypt352 infile outfile\"");
        return 0;
    } else {
        /* if correct, initialize */
        init(argv[1], argv[2]); 
    }

    /* ask for input and output buffer numbers until each is greater than 1 */
    while (inputBufNum <= 1) {
        printf("Enter input buffer size: \n");
        scanf("%d", &inputBufNum);
    }
    while (outputBufNum <= 1) {
        printf("Enter output buffer size: \n");
        scanf("%d", &outputBufNum);
    }

    /* allocate memory for input and output buffer arrays with given size */
    inputBuffer = malloc(sizeof(char)*inputBufNum);
    outputBuffer = malloc(sizeof(char)*outputBufNum);
    
    /* declare threads */
    pthread_t reader;
    pthread_t inputcount;
    pthread_t encryptor;
    pthread_t outputcount;
    pthread_t writer;
 
    /* initialize semaphores, mutexes, and condition variables */
    // sem_init(&inputCountReady, 0, 0);
    // sem_init(&inputCountDone, 0, 1);
    // sem_init(&encryptReady, 0, 0);
    // sem_init(&encryptDone, 0, 0); 
    // sem_init(&outputCountReady, 0, 0);
    // sem_init(&outputCountDone, 0, 1);
    // sem_init(&writeReady, 0, 0);
    // sem_init(&writeDone, 0, 1); 
    inputCountReady = sem_open("/inputCountReady", O_CREAT, 0644, 0);
    sem_unlink("/inputCountReady");
    inputCountDone = sem_open("/inputCountDone", O_CREAT, 0644, 1);
    sem_unlink("/inputCountDone");
    encryptReady = sem_open("/encryptReady", O_CREAT, 0644, 0);
    sem_unlink("/encryptReady");
    encryptDone = sem_open("/encryptDone", O_CREAT, 0644, 0);
    sem_unlink("/encryptDone");
    outputCountReady = sem_open("/outputCountReady", O_CREAT, 0644, 0);
    sem_unlink("/outputCountReady");
    outputCountDone = sem_open("/outputCountDone", O_CREAT, 0644, 1);
    sem_unlink("/outputCountDone");
    writeReady = sem_open("/writeReady", O_CREAT, 0644, 0);
    sem_unlink("/writeReady");
    writeDone = sem_open("/writeDone", O_CREAT, 0644, 1);
    sem_unlink("/writeDone");
  
    pthread_mutex_init(&inmutex, NULL);
    pthread_mutex_init(&outmutex, NULL);
    // pthread_mutex_init(&readLock, NULL);
    // pthread_mutex_init(&inputCountLock, NULL);
    // pthread_mutex_init(&encryptLock, NULL);
    // pthread_mutex_init(&outputCountLock, NULL);
    // pthread_mutex_init(&writeLock, NULL);
    // pthread_cond_init(&readCondition, NULL);
    // pthread_cond_init(&inputCountCondition, NULL);
    // pthread_cond_init(&encryptCondition, NULL);
    // pthread_cond_init(&outputCountCondition, NULL);
    // pthread_cond_init(&writeCondition, NULL);

    /* initialize threads */
    pthread_create(&reader, NULL, read_thread, NULL);
    sleep(1);
    pthread_create(&inputcount, NULL, inputcount_thread, NULL);
    sleep(1);
    pthread_create(&encryptor, NULL, encryptor_thread, NULL);
    sleep(1);
    pthread_create(&outputcount, NULL, outputcount_thread, NULL);
    sleep(1);
    pthread_create(&writer, NULL, writer_thread, NULL);

    /* join threads */
    pthread_join(&reader, NULL);
    pthread_join(&inputcount, NULL);
    pthread_join(&encryptor, NULL);
    pthread_join(&outputcount, NULL);
    pthread_join(&writer, NULL);

    /* destroy mutexes and condition vaiables */
    pthread_mutex_destroy(&inmutex);
    pthread_mutex_destroy(&outmutex);
    // sem_destroy(&inputCountReady);
    // sem_destroy(&inputCountDone);
    // sem_destroy(&encryptReady);
    // sem_destroy(&encryptDone);
    // sem_destroy(&outputCountReady);
    // sem_destroy(&outputCountDone);
    // sem_destroy(&writeReady);
    // sem_destroy(&writeDone);
    // pthread_mutex_destroy(&readLock);
    // pthread_mutex_destroy(&readCondition);
    // pthread_mutex_destroy(&inputCountLock);
    // pthread_mutex_destroy(&inputCountCondition);
    // pthread_mutex_destroy(&encryptLock);
    // pthread_cond_destroy(&encryptCondition);
    // pthread_cond_destroy(&outputCountLock);
    // pthread_cond_destroy(&outputCountCondition);
    // pthread_cond_destroy(&writeLock);
    // pthread_cond_destroy(&writeCondition);

    return 0;
}