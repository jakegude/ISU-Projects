#Jake Gudenkauf
all: project2

project2: encrypt-module.o main.o
	gcc encrypt-module.o main.o -o  encrypt352

main.o : main.c
	gcc main.c -lpthread -c

encrypt-module.o: encrypt-module.c encrypt-module.h
	gcc encrypt-module.c encrypt-module.h -c 

clean:
	rm -f *.o encrypt352