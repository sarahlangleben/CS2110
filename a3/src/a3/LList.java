package a3;

/**
 * An {@code LList<T>} is a mutable list of elements of type T.
 * Example: [1, 3, 2] is a list of Integers; [] is an empty list.
 */
public interface LList<T> extends Iterable<T> {

    /**
     * Returns: the number of list elements.
     */
    int size();

    /**
     * Returns: the first value in the list,
     * or null if the list is empty.
     */
    T head();

    /**
     * Returns: the last value in the list,
     * or null if the list is empty.
     */
    T tail();

    /**
     * Returns: the element at position k
     * Requires: {@code 0 <= k < size()}
     * <p>
     * Elements are indexed starting from position 0, so the
     * first element is returned if k=0.
     */
    T get(int k);

    /**
     * Effect: Insert v at the beginning of the list. This operation takes
     * constant time. E.g. if the list is [8, 7, 4], prepend(2) changes this
     * list to [2, 8, 7, 4].
     */
    void prepend(T v);

    /**
     * Effect: deletes the specified element from the linked list.
     * Example: [1, 2].remove(2) will result in the linked list: [1].
     * Requires: x is a valid element, a linked list to remove the element x from.
     * @param x
     * @return Whether or not the specified element was successfully removed from the linked list.
     * If element x was successfully, removed, return true. Otherwise, return false. If there are
     * multiple elements with the same value, remove the first occurrence of the specified element.
     * If the specified element is not in the linked list, return false.
     */
    boolean remove(T x);

    /**
     * Effect: Add v to the end of this list. This operation takes constant time. E.g. if the list
     * is [8, 7, 4], append(2) changes this list to [8, 7, 4, 2].
     */
    void append(T v);

    /**
     * Effect: Insert value x into the list before the first occurrence of y.
     * Requires: y must be in the list.
     * Example: If the list is [3, 8, 2], then insertBefore(8, 1) results in the
     * the list changing to [3, 1, 8, 2].
     */
    void insertBefore(T x, T y);

    /**
     * Requires: a linked list to check, a valid element to look for.
     * Example: [1, 2].contains(4) will return False.
     * @param elem
     * @return whether or not the element is in the linked list. If the element is in the linked list,
     * return true. Otherwise, return false.
     */
    boolean contains(T elem);
}
