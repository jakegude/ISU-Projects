/**
 * @author jakegude
 */
package cs228hw2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class AmusingLinkedList<E> implements List<E> {

	/**
	 * public inner Node class
	 * @author jakegude
	 */
	public class Node {

		private E data;
		private Node next;
		private Node prev;
		
		/**
		 * Node constructor
		 * @param item
		 * @param n
		 * @param p
		 */
		public Node(E item, Node n, Node p) {
			data = item;
			next = n;
			prev = p;
		}

		/**
		 * returns the next Node
		 * @return
		 */
		public Node getNext() {
			return next;
		}

		/**
		 * returns the previous Node
		 * @return
		 */
		public Node getPrev() {
			return prev;
		}
		
		/**
		 * returns the data of the Node
		 * @return
		 */
		public E getData() {
			return data;
		}
	}
	
	/**
	 *   inner AmusingListIterator class
	 *  @author jakegude
	 *  @param <E>
	 */
	public class AmusingIterator<E> implements Iterator<E> {
		
		private Node cur;
		private int nextindex;
		
		/**
		 * default constructor
		 */
		public AmusingIterator() {
			cur = head;
			nextindex = 0;
		}

		/**
		 * returns if iterator has a next value
		 */
		public boolean hasNext() {
			return cur.next != head && nextindex < size();
		}

		/**
		 * returns iterators next value
		 */
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			} else {
				cur = cur.next;
				nextindex++;
				return (E) cur.data;
			}
		}
	}
	
	/**
	 *   inner AmusingListIterator class
	 *  @author jakegude
	 *  @param <E>
	 */
	public class AmusingListIterator<E> implements ListIterator<E> {

		private Node cur;
		private int nextindex;
		
		public AmusingListIterator() {
			cur = head;
			nextindex = 0;
		}
		
		public AmusingListIterator(int index) {
			if (index > size() || index < 0) {
				throw new IndexOutOfBoundsException();
			}
			cur = getNodeAtIndex(index - 1);
			nextindex = index;
		}
		
		/**
		 * returns if list iterator has a next value
		 */
		public boolean hasNext() {
			return cur.next != head && nextindex < size();
		}
		
		/**
		 * returns if list iterator has a previous value
		 */
		public boolean hasPrevious() {
			return cur != head && nextindex != 0;
		}

		/**
		 * returns list iterators next value
		 */
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			} else {
				cur = cur.next;
				nextindex++;
				return (E) cur.data;
			}
		}

		/**
		 * returns list iterators previous value
		 */
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			} else {
				E curdata = (E) cur.data;
				nextindex--;
				cur = getNodeAtIndex(nextindex - 1);
				return curdata;
			}
		}

		/**
		 * returns the spot of the next item that would be iteratorated over
		 */
		public int nextIndex() {
			return nextindex;
		}

		/**
		 * returns the spot of the item that was just iteratorated over
		 */
		public int previousIndex() {
			return nextindex - 1;
		}

		/**
		 * list iterator add
		 */
		public void add(E e) {
			// optional method
			throw new UnsupportedOperationException();
		}

		/**
		 * list iterator remove
		 */
		public void remove() {
			// optional method
			throw new UnsupportedOperationException();
		}

		/**
		 * list iterator set
		 */
		public void set(E e) {
			// optional method
			throw new UnsupportedOperationException();
		}	
	}
	
	public Node head;
	private Node lasteven;
	private int index = -1;
	
	/**
	 * default constructor 
	 */
 	public AmusingLinkedList() {
 		head = null;
//		head = new Node(null, null, null);
//		head.next = head;
//		head.prev = head;
	}
	
	/**
	 * adds item to the end of the list
	 */
	public boolean add(E item) {
//		if (item == null) return false;
		if (index == -1) {
			Node n = new Node(item, null, null);
			head = new Node(null, n, n);
			n.next = n;
			n.prev = n;
			lasteven = n;
			head.next.prev = lasteven;
		}  else 	if (index % 2 == 0) {		// adding an element that will be at an odd index
			Node n = new Node(item, null, null);
			getNodeAtIndex(index).next = n;
			n.next = head.next;
			n.prev = null;
		} else if (index % 2 == 1) {		// adding an element that will be at an even index
			Node n = new Node(item, lasteven, null);
			getNodeAtIndex(index).next = n;
			n.next = head.next;
			n.prev = lasteven;
			lasteven = n;
			head.next.prev = lasteven;
		} 
		index++;
		return true;			
	}
	
	/**
	 * adds an element into a certain index of the linked list
	 * @param index
	 * @param element
	 */
	public void add(int index, E element) {
		if (index > size() || index < 0) {
			throw new IndexOutOfBoundsException();
		} else {
			int size = size() + 1;
			Node add = new Node(element, null, null);

			if (head != null) {
				Node n = head.next;
				clear();
				for (int i = 0; i < size; i++) {
					if (i == index) {
						add(add.data);
					} else {
						add(n.data);
						n = n.next;
					}
				}
			} else {
				head = new Node(null, add, null);
				this.index++;
			}
		}
	}

	/**
	 * removes a certain data item in the list
	 * relinks list after removing if in the middle of the list
	 * @param item
	 * @return
	 */
	public boolean remove(Object o) {
		if (indexOf(o) < 0) {
			return false;
		} else {
			Node n = head.next;
			int size = size();
			clear();
			for (int i = 0; i < size; i++) {
				if (n.data != o) {
					add(n.data);
				}
				n = n.next;
			}
		}
		return true;
	}
	
	/**
	 *  removes item at a certain index
	 *  @param index
	 *  @return data that was removed
	 */
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		} else {
			Node n = head.next;
			E retval = getNodeAtIndex(index).data;
			int size = size();
			clear();
			
			for (int i = 0; i < size; i++) {
				if (i != index) {
					add(n.data);
				}
				n = n.next;
			}
			return retval;
		}
	}
	
	/**
	 *  checks if the linked list contains a certain item
	 *  returns true if list does contain the item
	 *  returns false if not
	 * @param item
	 * @return boolean
	 */
	public boolean contains(Object o) {
		if (size() == 0) {
			return false;
		} else {
			if (indexOf(o) >= 0) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * clears the list
	 */
	public void clear() {
		head = new Node(null, null, null);
		index = -1;
	}

	/**
	 * returns data item at index
	 */
	public E get(int index) {
		return getNodeAtIndex(index).data;
	}

	/**
	 * sets data item at index to element
	 */
	public E set(int index, E element) {
		if (index < 0 || index >= size()) throw new IndexOutOfBoundsException();
		E retval = getNodeAtIndex(index).data;
		getNodeAtIndex(index).data = element;
		return retval;
	}
	
	/**
	 * returns the size of the list
	 */
	public int size() {
		return index + 1;
	}
	
	/**
	 *  checks if list is empty
	 */
	public boolean isEmpty() {
		if (index == -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * retruns whatever node was at  index
	 * @param index
	 * @return
	 */
	public Node getNodeAtIndex(int index) {
		if (isEmpty() || index > this.index) {
			return null;
		} else {
			Node cur = head;
			for (int i = 0; i <= index; i++) {
				cur = cur.next;
			}
			return cur;
		}	
	}
	
	/**
	 * finds node with item as data and return that node, otherwise return null
	 * @param item
	 * @return
	 */
	public Node findNode(E item) {
		Node n = head;
		while (n != head.next.prev) {
			if (n.data == item) {
				return n;
			}
			n = n.next;
		}
		return null;
	}
	
	/**
	 * changes the linked list into a string
	 */
	@Override
	public String toString() {
		String amusingstring = "";
		Node n = head.next;
		int cur = -1;
		int pre = 0;
		int next = 0;
		E item = null;
		
		for (int i = 0; i < size(); i++) {
			if (cur == -1 && size() + 1 % 2 == 0) {
				pre = size() - 1;
			} else if (cur == -1 && size() % 2 == 0) {
				pre = size() - 2;
			} else if (n.prev != null && i != 0) {
				pre = i - 2;
			} 
			else {
				pre = -1;
			}
			if (i == size() - 1) {
				next = 0;
			} else {
				next++;
			}
			cur++;
			item = n.data;
			amusingstring = amusingstring +  cur + " " + pre + " " + next + " " + item + "\n";
			n = n.next;
		}
		return amusingstring.trim();
	}
	
	/**
	 * returns the amusing linked list in a generic array
	 */
	public Object [] toArray() {
		Object [] amusingarray = new Object [size()];
		AmusingListIterator<E> it = new AmusingListIterator<E>();
		int i = 0;
		while (i < size()) {
			amusingarray[i] = it.next();
			i++;
		}
		return amusingarray;
	}

	/**
	 * returns the linked list in the form of a generic array
	 */
	public <T> T [] toArray(T [] a) {
		if (size() > a.length) {
			a = Arrays.copyOf(a, size());
		}
			AmusingListIterator<E> it = new AmusingListIterator<>();
			for (int i = 0; i < size(); i++) {
				T data = (T) it.next();
				a[i] = data;
			}
		return a;
	}

	/**
	 *  returns true if all data in the collection is in the linked list
	 *  returns false if not.
	 */
	public boolean containsAll(Collection<?> c) {
		if (c.equals(null)) return false;
		int i = 0;
		while (i < c.size()) {
			if (!contains(c.toArray()[i])) {
				return false;
			}
			i++;
		}
		return true;
	}

	/**
	 *  adds all data in the collection to the linked list
	 */
	public boolean addAll(Collection<? extends E> c) {
		if (c.size() == 0 ) return false;
		int i = 0;
		while (i < c.size()) {
			add( (E) c.toArray()[ i ]);
			i++;
		}
		return true;
	}

	/**
	 *  adds all data in the collection starting at index to the linked list
	 */
	public boolean addAll(int index, Collection<? extends E> c) {
		if (c == null || c.size() == 0 || c.equals(null)) return false;
		if ( index < 0 || index > size()) throw new IndexOutOfBoundsException();
		int i = 0;
		int j = index;
		while (i < c.size()) {
			add(j, (E) c.toArray()[i]);
			i++;
			j++;
		}
		return true;
	}

	/**
	 *  removes all data in the collection that is also in the linked list
	 */
	public boolean removeAll(Collection<?> c) {
		int i = 0;
		while (i < size() + 1) {
			if (contains(c.toArray()[i])) {
				remove(c.toArray()[i]);
			}
			i++;
		}
		return true;
	}

	/**
	 * removes all data not in the c that is in the linked list
	 */
	public boolean retainAll(Collection<?> c) {
		Node n = head;
		Object [] compare = c.toArray();
		int size = size() + 1;
		if (c.isEmpty() && isEmpty()) {
			return false;
		}
		if (c.isEmpty()) {
			clear();
			return true;
		}
		clear();	
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < compare.length; j++) {
				if (compare[j] == n.data) {
					add(n.data);
				}
			}
			n = n.next;
		}
		return true;
	}
		
	/**
	 * returns the first index of the element determined by the parameter
	 * @param item
	 * @return
	 */
	public int indexOf(Object o) {
		if (isEmpty()) return -1; 
		Node n = head;
		for (int i = 0; i < size(); i++) {
			n = n.next;
			if (n.data == o) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * returns the last index of o
	 * @param item
	 * @return
	 */
	public int lastIndexOf(Object o) {
		if (isEmpty()) return -1;
		Node n = head.next;
		int retval = -1;
		for (int i = 0; i < size(); i++) {
			if (n.data == o) {
				retval = i;
			}
			n = n.next;
		}
		return retval;
	}

	/**
	 * returns a new list that is a sublist of the orginal amusing linked list from fromIndex to toIndex
	 */
	public AmusingLinkedList<E> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex > size() || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		} else if (fromIndex == toIndex) {
			AmusingLinkedList<E> newlist = new AmusingLinkedList<>();
			return newlist;
		} else {
			AmusingLinkedList<E> newlist = new AmusingLinkedList<>();
			int i = fromIndex;
			do {
				newlist.add(get(i));
				i++;
			} while (i < toIndex);
			return newlist;	
		}
	}
	
	/**
	 * returns new AmusingListIterator
	 * @return
	 */
	public Iterator<E> iterator() {
		return new AmusingIterator<E>();
	}

	/**
	 * returns a new listiterator 
	 */
	public ListIterator<E> listIterator() {
		return new AmusingListIterator<E>();
	}

	/**
	 * returns a new listiterator starting at index
	 */
	public ListIterator<E> listIterator(int index) {
		if (index > size() || index < 0) {
			throw new IndexOutOfBoundsException();
		}	
		AmusingListIterator<E> it = new AmusingListIterator<>();
		for (int i = 0; i < index; i++) {
			it.next();
		}
		return it;
	}

}