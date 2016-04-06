package edu.frostburg.cosc610.spellchecker;

/**
 * A singly-linked list
 *
 * @author Kerwin Yoder
 * @version 2016.03.24
 * @param <E> the type of elements stored in the list
 */
public class SinglyLinkedList<E extends Comparable> implements Cloneable {

    private SLLNode<E> head;
    private SLLNode<E> tail;
    private int size;

    /**
     * Creates a new empty SinglyLinkedList
     */
    public SinglyLinkedList() {
        head = tail = null;
        size = 0;
    }

    /**
     * Appends the new element to the end of the list
     *
     * @param data the data to add to the list
     */
    public void append(E data) {
        if (head == null) {
            head = tail = new SLLNode(data);
        } else {
            SLLNode node = new SLLNode(data);
            tail.setNext(node);
            tail = node;
        }
        ++size;
    }

    /**
     * Inserts the new element at the front of the list
     *
     * @param data the data to add to the list
     */
    public void insert(E data) {
        if (head == null) {
            head = tail = new SLLNode(data);
        } else {
            head = new SLLNode(data, head);
        }
        ++size;
    }

    /**
     * Removes the given element from the list. If there are multiple
     * occurrences of the same element, the first occurrence is removed.
     *
     * @param data the data to remove from the list
     * @return true if the data was removed
     */
    public boolean remove(E data) {
        if (head == null) {
            return false;
        }
        if (((Comparable) head.data()).compareTo(data) == 0) {
            if (head == tail) {
                head = tail = null;
            } else {
                head = head.next();
            }
            --size;
            return true;
        }
        if (head == tail) {
            return false;
        }
        SLLNode ref = head;
        SLLNode next = ref.next();
        while (next != tail) {
            if (((Comparable) next.data()).compareTo(data) == 0) {
                ref.setNext(next.next());
                --size;
                return true;
            }
            ref = next;
            next = next.next();
        }
        if (((Comparable) tail.data()).compareTo(data) == 0) {
            tail = ref;
            tail.setNext(null);
            --size;
            return true;
        }
        return false;
    }

    /**
     * Checks if the list contains the given data
     *
     * @param data the data to search for in the list
     * @return true if the list contains the data and false otherwise
     */
    public boolean contains(E data) {
        for (SLLNode node = head; node != null; node = node.next()) {
            if (((Comparable) node.data()).compareTo(data) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clears the list
     */
    public void clear() {
        head = tail = null;
        size = 0;
    }

    /**
     * Checks if the list is empty
     *
     * @return true if the list is empty and false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Gets the number of elements in the list
     *
     * @return the number of elements in the list
     */
    public int size() {
        return size;
    }

    /**
     * Creates a shallow copy of the list (The elements themselves are not
     * cloned)
     *
     * @return a shallow copy of the list
     */
    @Override
    public SinglyLinkedList<E> clone() {
        try {
            SinglyLinkedList<E> clone = (SinglyLinkedList<E>) super.clone();
            clone.clear();
            for (SLLNode<E> node = head; node != null; node = node.next()) {
                clone.append(node.data());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            // Clone is supported so some other error must have occurred.
            throw new InternalError(e);
        }
    }

    /**
     * Returns an array containing the elements in the list
     *
     * @return an array containing the elements in the list
     */
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (SLLNode<E> node = head; node != null; node = node.next()) {
            array[i++] = node.data();
        }
        return array;
    }

    /**
     * Returns an array of the given class type containing the elements in the
     * list
     *
     * @param componentType a class literal for the type of elements stored in
     * the array obtained by using ClassName.class (e.g. Integer.class)
     * @return an array of the given class type containing the elements in the
     * list
     */
    public E[] toArray(Class<?> componentType) {
        E[] array = (E[]) java.lang.reflect.Array.newInstance(componentType, size);
        int i = 0;
        for (SLLNode<E> node = head; node != null; node = node.next()) {
            array[i++] = node.data();
        }
        return array;
    }

    /**
     * Returns a string representation of the SLList
     *
     * @return A string representation of the SLList
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(String.format("The SLList contains %d nodes:\r\n", size));
        SLLNode ref = head;
        if (isEmpty()) {
            return builder.toString();
        } else {
            builder.append("head-->\t");
        }
        while (ref.next() != null) {
            builder.append(ref.data());
            builder.append("\t-->\t");
            ref = ref.next();
        }
        builder.append(ref.data());
        builder.append("\t-->\tnull");
        return builder.toString();
    }
}
