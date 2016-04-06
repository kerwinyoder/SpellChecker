package edu.frostburg.cosc610.spellchecker;

/**
 * A singly-linked list node
 *
 * @author Kerwin Yoder
 * @version 2016.03.24
 * @param <E> The type of data stored in the node
 */
public class SLLNode<E> {

    private final E data;
    private SLLNode next;

    /**
     * Creates a new SLLNode with the given data and next node
     *
     * @param data the data stored in the node
     * @param next the next node in the list
     */
    public SLLNode(E data, SLLNode next) {
        this.data = data;
        this.next = next;
    }

    /**
     * Creates a new SLLNode with the given data and no next node
     *
     * @param data the data stored in the node
     */
    public SLLNode(E data) {
        this(data, null);
    }

    /**
     * Gets the data stored in the node
     *
     * @return the data stored in the node
     */
    public E data() {
        return data;
    }

    /**
     * Sets the next node for this node
     *
     * @param next the new next node for this node
     */
    public void setNext(SLLNode<E> next) {
        this.next = next;
    }

    /**
     * Gets the next node in the list
     *
     * @return the next node in the list
     */
    public SLLNode<E> next() {
        return next;
    }
}
