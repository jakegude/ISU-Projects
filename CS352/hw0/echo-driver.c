#include <stdlib.h>
#include <stdio.h>
#include "device-controller.h"
// Jake Gudenkauf

int numelements = 0;
int bufferSize = 0;
int* buffer = NULL;
int head = 0;
int tail = 0;
int writevalue = -1;

int is_full() {
	if (numelements == bufferSize) { return 1; }
	else { return 0; }
}

int is_empty() {
	if (numelements == 0) { return 1; }
	else { return 0; }
}

void enqueue(int val) {
	if (is_full() == 1) { return; }
	else {
		tail++;
		tail = tail % bufferSize;
		numelements++;
		buffer[tail] = val;		
	}
}

void dequeue() {
	// if (is_empty() == 1) { return -1; }
	// else {
	// 	char temp = buffer[head];
	// 	head++;
	// 	numelements--;
	// 	head = head % bufferSize;
	// 	return temp;
	// } // return int version
	if (is_empty() == 1) { return; }
	else {
		head++;
		numelements--;
		head = head % bufferSize;
	}   // return void version
}

void read_interrupt(int c) {	
	if (writevalue != -1) {
		enqueue(c);
	} else {
		write_device(c);
		writevalue = c;
	}
}

void write_done_interrupt() {
	dequeue();
	if (buffer[head] != -1) {
		write_device(buffer[head]);
		writevalue = buffer[head];
	} else {
		writevalue = -1;
	}
}

int main(int argc, char* argv[]) {
	bufferSize = *argv[1] - '0';
	buffer = (int*) malloc(bufferSize * sizeof(int));
	for (int i = 0; i < bufferSize; i++) {
		buffer[i] = -1;
	}
	start();
	free(buffer);
	return 0;
}