package a3;

/**
 * An instance is a node of a linked list.
 */
public class Node<T> {
    /**
     * The value in the node
     */
    private T data;
    /**
     * Previous node on list (null if this is first node) and Next node on list (null if
     * this is last node).
     */
    private Node<T> next;

    /**
     * Constructor: an instance with previous node p (can be null), value v, and next node s
     * (can be null).
     */
    Node(T v, Node<T> s) {
        data = v;
        next = s;
    }

    /**
     * Returns: the value of this node.
     */
    public T data() {
        return data;
    }

    /**
     * Returns: the next node in this list (null if this is the last node of this list).
     */
    Node<T> next() {
        return next;
    }

    /**
     * Effect: set the next field to parameter n.
     */
    void setNext(Node<T> n) {
        next = n;
    }
}