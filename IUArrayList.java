import java.util.*;

/**
 * Array-based implementation of IndexedUnsortedList.
 * 
 * @author
 *
 * @param <E> type to store
 */
public class IUArrayList<E> implements IndexedUnsortedList<E> {
	private static final int DEFAULT_CAPACITY = 10;
	private static final int NOT_FOUND = -1;

	private E[] array;
	private int rear;
	private int modCount; // DO NOT REMOVE ME

	/** Creates an empty list with default initial capacity */
	public IUArrayList() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates an empty list with the given initial capacity
	 * 
	 * @param initialCapacity
	 */
	@SuppressWarnings("unchecked")
	public IUArrayList(int initialCapacity) {
		array = (E[]) (new Object[initialCapacity]);
		rear = 0;
		modCount = 0; // DO NOT REMOVE ME
	}

	/** Double the capacity of array */
	private void expandCapacity() {
		array = Arrays.copyOf(array, array.length * 2);
	}

	@Override
	public void addToFront(E element) {
		expandIfNecessary();
		shiftRightFromIndex(0);
		array[0] = element;

		modCount++; // DO NOT REMOVE ME

	}

	@Override
	public void addToRear(E element) {
		expandIfNecessary();
		array[rear] = element;
		rear++;

		modCount++; // DO NOT REMOVE ME
	}

	@Override
	public void add(E element) {
		expandIfNecessary();
		array[rear] = element;
		rear++;

		modCount++; // DO NOT REMOVE ME
	}

	@Override
	public void addAfter(E element, E target) {
		int index = indexOf(target);
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}

		expandIfNecessary();
		shiftRightFromIndex(index + 1);
		array[index + 1] = element;

		modCount++; // DO NOT REMOVE ME
	}

	@Override
	public void add(int index, E element) {
		// Not using checkIndexValidity helper for this one,
		// as index can be added at the end of the list,
		// so index == rear is valid
		if (index < 0 || index > rear) {
			throw new IndexOutOfBoundsException();
		}

		expandIfNecessary();
		shiftRightFromIndex(index);
		array[index] = element;

		modCount++; // DO NOT REMOVE ME
	}

	@Override
	public E removeFirst() {
		throwIfEmpty();

		E retVal = array[0];
		shiftLeftFromIndex(0);

		modCount++; // DO NOT REMOVE ME
		return retVal;
	}

	@Override
	public E removeLast() {
		throwIfEmpty();

		E retVal = array[rear - 1];
		array[rear - 1] = null;
		rear--;

		modCount++; // DO NOT REMOVE ME
		return retVal;
	}

	@Override
	public E remove(E element) {
		int index = indexOf(element);
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}

		E retVal = array[index];

		shiftLeftFromIndex(index);

		modCount++; // DO NOT REMOVE ME
		return retVal;
	}

	@Override
	public E remove(int index) {
		checkIndexValidity(index);

		E returnValue = array[index];

		shiftLeftFromIndex(index);

		modCount++; // DO NOT REMOVE ME

		return returnValue;
	}

	@Override
	public void set(int index, E element) {
		checkIndexValidity(index);
		array[index] = element;

		modCount++; // DO NOT REMOVE ME
	}

	@Override
	public E get(int index) {
		checkIndexValidity(index);
		return array[index];
	}

	@Override
	public int indexOf(E element) {
		int index = NOT_FOUND;

		if (!isEmpty()) {
			int i = 0;
			while (index == NOT_FOUND && i < rear) {
				if (element.equals(array[i])) {
					index = i;
				} else {
					i++;
				}
			}
		}

		return index;
	}

	@Override
	public E first() {
		throwIfEmpty();
		return array[0];
	}

	@Override
	public E last() {
		throwIfEmpty();
		return array[rear - 1];
	}

	@Override
	public boolean contains(E target) {
		return (indexOf(target) != NOT_FOUND);
	}

	@Override
	public boolean isEmpty() {
		return rear == 0;
	}

	@Override
	public int size() {
		return rear;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		for (int i = 0; i < rear; i++) {
			result.append(array[i]);
			if (i < rear - 1) {
				result.append(", ");
			}
		}
		result.append("]");
		return result.toString();
	}

	/**
	 * Checks if the list is empty and throws a NoSuchElementException if it is.
	 * This should be called at the beginning of any method that tries to access
	 * or remove an element from the list.
	 */
	private void throwIfEmpty() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
	}

	/**
	 * Checks if the array is full and expands it if necessary.
	 * This should be called at the beginning of any method
	 * that adds an element to the list.
	 */
	private void expandIfNecessary() {
		if (rear == array.length) {
			expandCapacity();
		}
	}

	/**
	 * Verifies that the index is within the bounds of the list's indices
	 * 
	 * @param index the index to check
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	private void checkIndexValidity(int index) {
		if (index < 0 || index >= rear) {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * Shifts all elements to the left starting from the given index. This should be
	 * called after an element is removed from the list at the given index to ensure
	 * that there are no gaps in the array.
	 * 
	 * @param index the index from which to start shifting elements to the left
	 */
	private void shiftLeftFromIndex(int index) {
		rear--;
		for (int i = index; i < rear; i++) {
			array[i] = array[i + 1];
		}
		array[rear] = null;
	}

	/**
	 * Shifts all elements to the right starting from the given index. This should
	 * be called before an element is added to the list at the given index to ensure
	 * that there is space in the array for the new element.
	 * 
	 * @param index the index from which to start shifting elements to the right
	 */
	private void shiftRightFromIndex(int index) {
		for (int i = rear; i > index; i--) {
			array[i] = array[i - 1];
		}
		rear++;
	}

	// IGNORE THE FOLLOWING COMMENTED OUT CODE UNTIL LAB 10
	// DON'T DELETE ME, HOWEVER!!!
	@Override
	public Iterator<E> iterator() {
		// return new IUArrayListIterator(); // UNCOMMENT ME IN LAB 10
		return null; // REMOVE ME IN LAB 10
	}

	// UNCOMMENT THE CODE BELOW IN LAB 10

	// private class IUArrayListIterator implements Iterator<E> {

	// private int iterModCount, current;
	// private boolean canRemove;

	// public IUArrayListIterator() {
	// iterModCount = modCount;
	// current = 0;
	// canRemove = false;
	// }

	// @Override
	// public boolean hasNext() {
	// if (iterModCount != modCount) {
	// throw new ConcurrentModificationException();
	// }
	// return current < rear;
	// }

	// @Override
	// public E next() {
	// if (!hasNext()) {
	// throw new NoSuchElementException();
	// }
	// E item = array[current];
	// current++;
	// canRemove = true;
	// return item;
	// }

	// @Override
	// public void remove() {
	// if (iterModCount != modCount) {
	// throw new ConcurrentModificationException();
	// }
	// if (!canRemove) {
	// throw new IllegalStateException();
	// }
	// // remove the element in the array at index current-1
	// // presumably decrement the rear
	// // presumably the modCount is getting incremented
	// // all indices have to back up by one
	// current--;
	// rear--;
	// // shift elements to the left
	// for (int i = current; i < rear; i++) {
	// array[i] = array[i + 1];
	// }
	// array[rear] = null;
	// modCount++;
	// iterModCount++;
	// // Can only remove the LAST "seen" element
	// // set back to a non-removal state
	// canRemove = false;
	// }

	// }

	// IGNORE THE FOLLOWING CODE
	// DON'T DELETE ME, HOWEVER!!!
	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int startingIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
