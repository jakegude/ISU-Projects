/**
 * @author jakegude
 */
package cs228hw2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class Deque228<E> implements Deque<E> {

	private ArrayList<E> deque;
	
	/**
	 * constructor
	 */
	public Deque228() {
		deque = new ArrayList<>();
	}
	
	/**
	 * Returns true if this contains no elements
	 */
	public boolean isEmpty() {
		return deque.isEmpty();
	}

	/**
	 * Returns an array containing all of the elements in this collection. 
	 * If this collection makes any guarantees as to what order its elements are 
	 * returned by its iterator, this method must return the elements in the same order.
	 */
	public Object [] toArray() {
		return deque.toArray();
	}
	
	/**
	 * Returns an array containing all of the elements in this collection;
	 *  the runtime type of the returned array is that of the specified array. I
	 *  f the collection fits in the specified array, it is returned therein. 
	 *  Otherwise, a new array is allocated with the runtime type of the specified array 
	 *  and the size of this collection.
	 *  @param a
	 */
	public Object [] toArray(Object[] a) {
		return deque.toArray(a);
	}

	/**
	 * Returns true if this collection contains all of the elements in the specified collection.
	 * @param c
	 */
	public boolean containsAll(Collection c) {
		return deque.containsAll(c);
	}

	/**
	 * Adds all of the elements in the specified collection to this collection (optional operation).
	 *  The behavior of this operation is undefined if the specified collection is modified 
	 *  while the operation is in progress. 
	 *  (This implies that the behavior of this call is undefined if the specified collection is this collection, 
	 *  and this collection is nonempty.)
	 *  @param c
	 */
	public boolean addAll(Collection c) {
		return deque.addAll(c);
	}

	/**
	 * Removes all of this collection's elements that are also contained 
	 * in the specified collection (optional operation). 
	 * After this call returns, this collection will contain no elements in common with the specified collection.
	 * @param c
	 */
	public boolean removeAll(Collection c) {
		return deque.remove(c);
	}

	/**
	 * Retains only the elements in this collection that are contained in the specified collection 
	 * (optional operation). In other words, removes from this collection all of its elements that 
	 * are not contained in the specified collection.
	 * @param c
	 */
	public boolean retainAll(Collection c) {
		return deque.retainAll(c);
	}

	/**
	 * clears the list
	 */
	public void clear() {
		deque.clear();
	}

	/**
	 * adds to the front of the list
	 */
	public void addFirst(Object e) {
		deque.add(0, (E) e);
	}

	/**
	 * adds to the back of the list
	 */
	public void addLast(Object e) {
		deque.add(deque.size() - 1, (E) e);
	}

	/**
	 * Inserts the specified element at the front of this deque unless it would violate capacity restrictions.
	 */
	public boolean offerFirst(Object e) {
		deque.add(0, (E) e);
		return true;
	}

	/**
	 * Inserts the specified element at the end of this deque unless it would violate capacity restrictions.
	 */
	public boolean offerLast(Object e) {
		deque.add(deque.size() - 1, (E) e);
		return true;
	}
	
	/**
	 * removes the front of the list
	 */
	public E removeFirst() {
		if (isEmpty()) throw new NullPointerException();
		else {
			return deque.remove(0);
		}
	}

	/**
	 * removes the back of the list
	 */
	public E removeLast() {
		if (isEmpty()) throw new NullPointerException();
		else {
			return deque.remove(deque.size() - 1);
		}
	}

	/**
	 * Retrieves and removes the first element of this deque, or returns null if this deque is empty.
	 */
	public E pollFirst() {
		if (isEmpty()) return null;
		deque.get(0);
		return deque.remove(0);
	}

	/**
	 * Retrieves and removes the last element of this deque, or returns null if this deque is empty.
	 */
	public E pollLast() {
		if (isEmpty()) return null;
		deque.get(deque.size() - 1);
		return deque.remove(deque.size() - 1);
	}

	/**
	 * returns the front of the list
	 *  throws exception if empty
	 */
	public E getFirst() {
		if (isEmpty()) throw new NullPointerException();
		else {
			return deque.get(0);
		}
	}

	/**
	 * returns the back of the list
	 * throws exception if empty
	 */
	public E getLast() {
		if (isEmpty()) throw new NullPointerException();
		else {
			return deque.get(deque.size() - 1);
		}
	}

	/**
	 * returns the fron of the list
	 * returns null if empty
	 */
	public E peekFirst() {
		if (isEmpty()) return null;
		else {
			return deque.get(0);
		}
	}

	/**
	 * returns the back of the list
	 * returns null if empty
	 */
	public E peekLast() {
		if (isEmpty()) return null;
		else {
			return deque.get(deque.size() -1);
		}
	}

	/**
	 * Removes the first occurrence of the specified element from this deque.
	 * @param o
	 */
	public boolean removeFirstOccurrence(Object o) {
		return deque.remove(deque.indexOf(o)) != null;
	}

	/**
	 * Removes the last occurrence of the specified element from this deque.
	 * @param o
	 */
	public boolean removeLastOccurrence(Object o) {
		return deque.remove(deque.lastIndexOf(o)) != null;
	}

	/**
	 * Inserts the specified element into the queue represented by this deque 
	 * (in other words, at the tail of this deque) 
	 * if it is possible to do so immediately without violating capacity restrictions, 
	 * returning true upon success and throwing an IllegalStateException if no space is currently available.
	 */
	public boolean add(Object e) {
		return deque.add((E) e);
	}

	/**
	 * Inserts the specified element into the queue represented by this deque 
	 * (in other words, at the tail of this deque) 
	 * if it is possible to do so immediately without violating capacity restrictions, 
	 * returning true upon success and false if no space is currently available.
	 */
	public boolean offer(Object e) {
		deque.add(deque.size() - 1, (E) e);
		return true;
	}

	/**
	 * Retrieves and removes the head of the queue represented by this deque
	 *  (in other words, the first element of this deque).
	 */
	public E remove() {
		return deque.remove(0);
	}

	/**
	 * Retrieves and removes the head of the queue represented by this deque 
	 * (in other words, the first element of this deque), or returns null if this deque is empty.
	 */
	public E poll() {
		if (isEmpty()) return null;
		return deque.remove(0);
	}

	/**
	 * Retrieves, but does not remove, 
	 * the head of the queue represented by this deque
	 * (in other words, the first element of this deque)
	 */
	public E element() {
		if (isEmpty()) throw new NullPointerException();
		return deque.get(0);
	}

	/**
	 * Retrieves, but does not remove, 
	 * the head of the queue represented by this deque 
	 * (in other words, the first element of this deque), or returns null if this deque is empty.
	 */
	public E peek() {
		if (isEmpty()) throw new NullPointerException();
		else {
			return deque.get(0);
		}
	}

	/**
	 * Pushes an element onto the stack represented by this deque 
	 * (in other words, at the head of this deque) 
	 * if it is possible to do so immediately without violating capacity restrictions, 
	 * throwing an IllegalStateException if no space is currently available.
	 */
	public void push(Object e) {
		deque.add(0, (E) e);		
	}

	/**
	 * Pops an element from the stack represented by this deque.
	 */
	public E pop() {
		if (isEmpty()) throw new NullPointerException();
		else {
			return deque.remove(0);
		}
	}

	/**
	 * Removes the first occurrence of the specified element from this deque.
	 */
	public boolean remove(Object o) {
		return deque.remove(o);
	}

	/**
	 * Returns true if this deque contains the specified element.
	 */
	public boolean contains(Object o) {
		return deque.contains(o);
	}

	/**
	 * Returns the number of elements in this deque.
	 */
	public int size() {
		return deque.size();
	}
	
	/**
	 * Returns an iterator over the elements in this deque in proper sequence.
	 */
	public Iterator<E> iterator() {
		return deque.iterator();
	}

	/**
	 * iterates through the list backwards
	 */
	public Iterator<E> descendingIterator() {
		ArrayList<E> backward = new ArrayList<>();
		for (int i = size() - 1; i > 0; i--) {
			backward.add(deque.get(i));
		}
		Iterator<E> reviter = backward.iterator();
		return reviter;
	}

}
