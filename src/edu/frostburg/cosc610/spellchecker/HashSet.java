package edu.frostburg.cosc610.spellchecker;

import java.math.BigInteger;

/**
 * HashSet is a hash-based implementation of a Set.
 * <P>
 * By definition, sets with repeated elements are considered equal. This
 * implementation does not store repeated elements that are evaluated as equal
 * by their Comparable.
 * <p>
 * The default load factor for this HashSet is 0.75. The underlying table for
 * the HashSet is not dynamic. As a result, when the load factor increases, the
 * efficiency of operations decreases. If the load factor becomes very high,
 * performance is significantly affected. The original size of the HashSet
 * should be chosen to be sufficient for the needs. If necessary the HashSet can
 * be manually expanded by getting an array and creating a new HashSet.
 *
 * @author Kerwin Yoder
 * @version 2016.03.29
 * @param <E> the type of elements stored in the HashSet
 */
public class HashSet<E extends Comparable> {

    // 1610612711 is the maximum prime number that allows a default load factor of less than .75
    private static final int MAX_TABLE_SIZE = 1610612711;
    private final int tableSize; //the size of the table
    private int size; //the number of elements stored in the table
    private final SinglyLinkedList[] table;

    /**
     * Creates a new HashSet with the given size and a load factor of .75.
     *
     * @param size the size of the HashSet
     */
    public HashSet(int size) {
        tableSize = getTableSize(size);
        table = new SinglyLinkedList[tableSize];
        this.size = 0;
    }

    /**
     * Adds a new element to the HashSet
     *
     * @param element the element to add to the HashSet
     * @return true if the set changed as a result of this operation
     */
    public boolean add(E element) {
        if (element == null) {
            return false;
        }
        int index = hash(element);
        SinglyLinkedList list = table[index];
        //if no elements are stored at this index, create a new List
        if (list == null) {
            list = new SinglyLinkedList();
            table[index] = list;
        }
        if (!list.contains(element)) {
            list.append(element);
            ++size;
            return true;
        }
        return false;
    }

    /**
     * Removes the given element from the HashSet
     *
     * @param element the element to remove form the HashSet
     * @return true if the set changed as a result of this operation
     */
    public boolean remove(E element) {
        if (element == null) {
            return false;
        }
        int index = hash(element);
        SinglyLinkedList list = table[index];
        if (list == null) {
            return false;
        }
        boolean removed = list.remove(element);
        if (removed) {
            --size;
        }
        return removed;
    }

    /**
     * Checks if the HashSet contains the given element
     *
     * @param element the element to search for in the HashSet
     * @return true if the element is in the HashSet or false otherwise
     */
    public boolean contains(E element) {
        if (element == null) {
            return false;
        }
        int index = hash(element);
        SinglyLinkedList list = table[index];
        if (list == null) {
            return false;
        }
        return list.contains(element);
    }

    /**
     * Gets the size (number of elements) of the HashSet
     *
     * @return the size (number of elements) in the HashSet
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the HashSet is empty
     *
     * @return true if the HashSet is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the HashSet
     */
    public void clear() {
        for (int i = 0; i < tableSize; ++i) {
            table[i] = null;
        }
        size = 0;
    }

    /**
     * Returns an array containing the elements in the HashSet
     *
     * @return an array containing the elements in the HashSet
     */
    public Object[] toArray() {
        Object[] array = new Object[size];
        int j = 0;
        for (int i = 0; i < tableSize; ++i) {
            SinglyLinkedList list = table[i];
            if (list != null) {
                Object[] bucketArray = list.toArray();
                for (Object element : bucketArray) {
                    array[j++] = element;
                }
            }
        }
        return array;
    }

    /**
     * Returns an array of the given class type containing the elements in the
     * HashSet
     *
     * @param componentType a class literal for the type of elements stored in
     * the array obtained by using ClassName.class (e.g. Integer.class)
     * @return an array of given class type containing the elements in the
     * HashSet
     */
    public E[] toArray(Class<?> componentType) {
        E[] array = (E[]) java.lang.reflect.Array.newInstance(componentType, size);
        int j = 0;
        for (int i = 0; i < tableSize; ++i) {
            SinglyLinkedList list = table[i];
            if (list != null) {
                Object[] bucketArray = list.toArray();
                for (Object element : bucketArray) {
                    array[j++] = (E) element;
                }
            }
        }
        return array;
    }

    /*
     * Determines the size required for a 0.75 load factor using the given size.
     * In order to minimize collisions, ideally this number should be the next
     * prime larger than the size required for a 0.75 load factor. It has a high
     * probability of being prime, but it is not guaranteed.
     *
     * @param size the size requested by the user for the HashSet
     * @return the size required for a 0.75 load factor using the given size
     */
    private int getTableSize(int size) {
        //if the table cannot be created with a load factor of less than .75, reject the requested size
        if (size < 0 || size > MAX_TABLE_SIZE) {
            throw new IllegalArgumentException(String.format("Invalid size. The maximum allowed size is %d", MAX_TABLE_SIZE));
        }
        if (size == MAX_TABLE_SIZE) {
            return size;
        } else {
            int target = (int) (size * 4 / 3);
            BigInteger bigInt = BigInteger.valueOf(target);
            return bigInt.nextProbablePrime().intValue();
        }
    }

    /*
     * Returns the hash value (table index) for the given element.
     *
     * @param element
     * @return
     */
    private int hash(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Invalid element. A null element cannot be hashed.");
        }
        int index = element.hashCode() % tableSize;
        return index < 0 ? index + tableSize : index; //Use math mod function to ensure the index is positive
    }
}
