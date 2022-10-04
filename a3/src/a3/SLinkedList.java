package a3;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Please provide the following information
 *  Name: Sarah Langleben
 *  NetID: sml343
 *  Tell us what you thought about the assignment: Way too hard. I wish expectations had been made
 *  clearer before I started working on the assignment so I didn't have to keep going back and checking
 *  things. The main method required a lot of previous knowledge that hasn't been covered in class/lecture
 *  to complete.
 */

/**
 * Implementation of {@code LList<T>} as a singly linked list.
 */
public class SLinkedList<T> implements LList<T> {

    /**
     * Number of values in the linked list.
     */
    private int size;
    /**
     * First and last nodes of the linked list (null if size is 0)
     */
    private Node<T> head, tail;

    /**
     * Creates: an empty linked list.
     */
    public SLinkedList() {
        size = 0;
        head = tail = null;
    }

    boolean classInv() {
        assert size >= 0;
        if (size == 0) {
            return (head == null && tail == null);
        }
        if(size!=0){
            return(head!= null && tail!=null);
        }
        if(size==1){
            return(head.equals(tail) && head!= null && tail!=null);
        }
        return true;
    }

    public int size() {
        return size;
    }

    public T head() {
        return head.data();
    }

    public T tail() {
        return tail.data();
    }

    public void prepend(T v) {
        assert classInv();
        Node<T> n = new Node<>(v, head);
        head = n;
        if (tail == null) tail = head;
        size++;
        assert classInv();
    }

    /**
     * Return a representation of this list: its values, with "[" at the beginning, "]" at the
     * end, and adjacent ones separated by ", " . Takes time proportional to the length of this
     * list. E.g. for the list containing 4 7 8 in that order, the result it "[4, 7, 8]".
     * E.g. for the list containing two empty strings, the result is "[, ]"
     */
    @Override
    public String toString() {
        // Do not modify the following 2 lines or the return statement
        assert classInv();
        StringBuilder res = new StringBuilder("[");
        Node<T> n = head;
        // TODO 1
        while(n.next()!= null) {
            res.append(n.data()+",");
            n = n.next();
        }
        return res + "]";
        }


    public void append(T v) {
        // TODO 2
        assert classInv();
        Node<T> n = new Node<>(v, null);
        if(size == 0 && head == null && tail == null){
            head = n;
            tail = head;
        }
        else {
            tail.setNext(n);
            tail = n;
        }
        size = size+1;
        assert classInv();
    }

    public void insertBefore(T x, T y) {
        // TODO 3
        //use a while-loop
        // since there is a precondition that y is in the list, we don't have to handle the case of the empty list
        assert classInv();
        Node<T> nodeY = new Node<>(y, null);
        Node<T> appendee = new Node<>(x, nodeY);
        Node<T> temp = new Node<>(head(), null);
        temp.setNext(head.next());
        if (size == 1 || head.data() == y ){
            prepend(x);
        }
        else {
            while(temp.next()!= null) {
                if(temp.next().data() == y){
                    appendee = temp.next();
                    //y = appendee.next().data();
                }
                temp = temp.next();
            }
            size = size + 1;
        }
        assert classInv();
    }

    public T get(int k) {
        // TODO 4 done
        //use a for-loop
        //base cases: k is the head or tail
        assert classInv();
        Node<T> current = new Node<>(head.data(), null);
        current.setNext(head.next());
        if(k < 0 || k > size()){
            return null;
        }
        else if(k == 0){
            return head();
        }
        else if(k == size){
            return tail();
        }
        else {
            Iterator itr = iterator();
            int i = 0;
            while(i != k && itr.next()!= null){
                current = current.next();
                //System.out.println(current.data());
                i++;
            }
            return current.data();
        }
    }

    public boolean contains(T value) {
        // TODO 5
        assert classInv();
        Node<T> current = new Node<>(head.data(), null);
        current.setNext(head.next());
        if(head.data().equals(value) || tail.data().equals(value)){
            return true;
        }
        while(current.next() != null){
            if(current.next().data().equals(value)){
                return true;
            }
            if(!current.next().data().equals(value)){
                current = current.next();
            }
        }
        return false;
    }

    /**
     * This method returns whether x was removed or not. If x is successfully removed,
     * the method should return true, otherwise it should return false.
     * If there is more than one item in the list that has value x, the method should
     * remove the first instance of x from the list.
     */
    public boolean remove(T x) {
        // TODO 6
        assert classInv();
        boolean removed = false;
        Node<T> current = new Node<>(head.data(), null);
        current.setNext(head.next());
        Node<T> prev = new Node<>(null, null);
        prev.setNext(head.next());

        Iterator itr = iterator();
        if(size == 1){
            Node<T> fin = new Node<>(null, null);
            head = fin;
            tail = null;
            return true;
        }
        if(head.data().equals(x)){
            removeHead(x);
            removed = true;
            return removed;
        }

        if(tail.data().equals(x)){
            removeTail(x);
            removed = true;
            return removed;
        }
        Node<T> curr = head;
        Node<T> previous = null;

        while(curr != null) {
            if(curr.data().equals(x)){
                previous.setNext(curr.next());
                removed = true;
                size--;
                return removed;
            }
            previous = curr;
            curr = curr.next();
        }
        assert classInv();
        return removed;
    }

    private boolean removeHead(T x){
        assert classInv();
        Node<T> temp = new Node<>(head.data(), null);
        temp = head;
        head = head.next();
        size = size-1;
        assert classInv();
        return true;
    }
    private boolean removeTail(T x){
        assert classInv();
        Node<T> secondLast = new Node<>(head.data(), null);
        secondLast.setNext(head.next());
        while (secondLast.next().next() != null){
            secondLast = secondLast.next();
        }
        size = size-1;
        tail = secondLast;
        secondLast.setNext(null);
        assert classInv();
        return true;
    }
    /**
     * Iterator support. This method makes it possible to write
     * a for-loop over a list, e.g.:
     * <pre>
     * {@code LList<String> lst = ... ;}
     * {@code for (String s : lst) {}
     *   ... use s here ...
     * }
     * }
     */
    @Override
    public Iterator<T> iterator() {
        assert classInv();
        return new Iterator<T>() {
            private Node<T> current = head;

            public T next() throws NoSuchElementException {
                if (current != null) {
                    T result = current.data();
                    current = current.next();
                    return result;
                } else {
                    throw new NoSuchElementException();
                }
            }

            public boolean hasNext() {
                return (current != null);
            }
        };
    }
}
