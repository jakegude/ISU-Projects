//insertion sort for cs321 assignment 1
//james gossling jgos
//9/14/2020
//compile with gcc -o IS insertionSort.c

#include <stdio.h>

void fill(int* arr, int length)
{
    int val = 23;
    for (int i = 0; i < length; i++, val--)
    {
        arr[i] = val;
    }
}

void printArr(int* arr, int length)
{
    printf("[ ");
    for (int i = 0; i < length; i++)
    {
        printf("%d ", arr[i]);
    }
    printf("]\n");
}

//takes array, index of a position, and index of the final element
//overwrites final element and shifts intermediate elements to the write
//leaving a hole at pos
void shiftRight(int* arr, int pos, int firstUnsorted)
{
    //printf("ShiftRight: pos = %d firstUnsortedIndex = %d\n", pos, firstUnsorted);
    //from index just before final to position
    for (int i = firstUnsorted-1; i >= pos; i--)
    {
        //printf("BEFORE SHIFT: ");
        //printArr(arr, 10);
        //index equals the value of index to its left
        arr[i+1] = arr[i];
        //printf("AFTER SHIFT: ");
        //printArr(arr, 10);
    }
}

//parameters: array of sorted ints, a value, and index of last element
//searches array for the sorted posotion of val
//returns its index
int findSortedPos(int* arr, int val, int finalSorted)
{
    //printf("FindSortedPos: val = %d\n",val);
    int ret = 0;
    //from first index to last index
    for (int i = 0; i <= finalSorted; i++, ret++)
    {
        //printf("i = %d\n", i);
        //if current element is larger than val
        if (arr[i] > val)
        {
            //printf("arr[%d](%d) > %d\n",i, arr[i], val);
            //break from loop
            break;
        }
    }
    //return the index
    return ret;
}

//parameters, array of partially sorted ints, index of first element
//not in sorted position, and index of final element
//moves the first unsorted element into its sorted position, shifting
//elements to the right as necessary
void insertSortedPos(int* arr, int nsIn, int finalSorted)
{
    int v = arr[nsIn];

    //printf("InsertSortedPos: v = arr[%d] = %d\n", nsIn, v);

    //find the index of where v should be
    int p = findSortedPos(arr, v, finalSorted);
    //printf("InsertSortedPos: p = %d\n", p);
    //sort all elements to the right of p
    shiftRight(arr, p, finalSorted+1);
    //p = the sorted value
    //printf("value %d placed into index %d\n", v, p);
    arr[p] = v;
    //printf("ARRAY IS NOW: ");
    //printArr(arr, 10);
}

//runs insertion sort algorithm
void insertionSort(int* arr, int length)
{
    //from first index to last
    for (int i = 1; i < length; i++)
    {
        //printf("InsertionSort loop: %d-------------\n", i);
        insertSortedPos(arr, i, i-1);
    }
}

int main()
{
    int arrSize = 10;
    int arr[arrSize];

    //fill array
    fill(arr, arrSize);
    printArr(arr, arrSize);
    //run insertion sort algorithm
    insertionSort(arr, arrSize);
    //print array
    printArr(arr, arrSize);

    return 0;
}