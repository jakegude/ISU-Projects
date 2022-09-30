# Jake Gudenkauf

all: echo-driver

echo-driver: device-controller-simulator.o echo-driver.o
	gcc device-controller-simulator.o echo-driver.o -o echo-driver

device-controller-simulator.o: device-controller-simulator.c
	gcc -c device-controller-simulator.c

echo-driver.o: echo-driver.c
	gcc -c echo-driver.c

debug: device-controller-simulator.c echo-driver.c device-controller.h
	gcc -g device-controller-simulator.c echo-driver.c device-controller.h

clean:
	rm -f *.o echo-driver a.out