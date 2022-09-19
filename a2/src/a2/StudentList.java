package a2;
/**
 * Please provide the following information
 *  Name: Sarah Langleben
 *  NetID: sml343
 *  Testing Partner Name: Lindsey Varkevisser
 *  Testing Partner NetID: lgv9
 *  Tell us what you thought about the assignment: It was interesting and I feel like it helped me
 *  practice testing in increments rather than all at once at the end. I wish the specs had been
 *  clearer and expectations regarding invariants/commenting had been more clear. the
 *  examples we were given in the source code wasn't super consistent (example:
 *  "The location of lectures at this course (e.g. Statler Hall Room 185)"; I wasn't sure if we were
 *  meant to be doing anything with the spaces.)
 */

/** A mutable list of {@code Student} objects.
 */
public class StudentList {
    // Implementation: the StudentList is implemented
    // as a resizable array data structure. It should contain
    // an array that has a large enough capacity to hold all the elements,
    // plus possibly extra elements. It should be able to hold
    // a large number of students but not use up a large amount
    // of memory if it holds a small number of students.
    /** class invariant: array has enough capacity to hold all elements;
     * capacity >= size (number of elements)*/
    private int size; // * size. number of elements present in the list currently.
    /** class invariant: size == number of elements in the array, size <= capacity */
    private int capacity; // * capacity. total storage in the backing array.
    /** class invariant: capacity >= size, capacity <= size*2*/
    // You may not use any classes from the java.util package.
    private Student[] studentList; // * studentList. resizable array that contains the students enrolled in a course.

    /** How long you spent on this assignment */
    public static double timeSpent = 12;
    public boolean classInv(){
        /**method to check the class invariant */
        return(capacity>=size && capacity > -1);
    }
    /** Constructor: A new empty {@code StudentList}. */
    public StudentList() {
        // The capacity of the StudentList is not the same as the size.
        // The capacity is the length of the backing array.
        // We suggest starting with a capacity of 5.
        // If the backing array runs out of space, double the size of the backing array
        // and copy all elements to the new backing array
        studentList = new Student[5];
        capacity = 5;
        size = 0;
    }

    /** The number of elements in this StudentList. */
    public int size() {
        // Note: Do not confuse size and capacity.
        return size;
    }

    /** The element in the list at position i. Positions start from 0.
     * Requires: 0 <= i < size of StudentList
     */
    public Student get(int i) {
        return studentList[i];
    }

    /** Effect: Add Student s to the end of the list. Capacity will be doubled if size >= capacity.
     * */
    public void append(Student s) {
        // Make sure that BEFORE adding a Student to the array, you
        // ensure that the capacity of the array is enough to add a
        // Student to it.
        // Note: Make sure you are keeping the class invariant for ALL classes true.
        assert classInv();
        if(size >= capacity){
            Student[] temp = new Student[capacity*2];
            for(int i=0; i < capacity; i++){
                temp[i] = studentList[i];
                temp[capacity] = s;
            }
            capacity = capacity*2;
            size+=1;
            studentList = temp;
        }
        else {
            studentList[size] = s;
            size= size+1;
        }
        assert classInv();
    }

    /** Returns: whether this list contains Student s. */
    public boolean contains(Student s) {
        assert(classInv());
        boolean test = false;
        for(Student element : studentList){
            if(element == s){
                test = true;
            }
        }
        assert(classInv());
        return test;
    }

    /** Effect: If Student s is in this StudentList, remove Student s from the array and return true.
     * Otherwise return false. Elements other than s remain in the same relative order.
     */
    public boolean remove(Student s) {
        // Note: Make sure you are keeping the class invariant for ALL classes true.
        assert classInv();
        int i;
        int count = 0;
        for(i = 0; i < size && count < 1; i++){
            if(studentList[i] == s){
                if(i == 0){
                    removeFirst(s);
                    count+=1;
                    return true;
                } else if (i == size-1) {
                    count+=1;
                    removeLast(s);
                    return true;
                }
                else {
                    count+=1;
                    removeMid(s);
                    return true;
                }
            }
        }
        assert classInv();
        return false;
    }
    /** Effect: if Student s's index is 0, remove Student s from the array.
     * Returns: updated array.*/
    private Student[] removeFirst(Student s){
        /** helper to remove(). called when the element to remove is the very first one in studentList.*/
        assert classInv();
        size = size-1;
        Student[] temp = new Student[size];
        for(int i = 0; i < size; i++){
            temp[i] = studentList[i+1];
        }
        studentList = temp;
        assert classInv();
        return studentList;
    }
    /**Effect: if Student s's index is size (the last element), remove Student s from the array.
     * Size of array should be original size minus one. Capacity is unchanged.
     * Returns: updated array.
     */
    private Student[] removeLast(Student s){
        /** helper to remove(). called when the element being removed is the last in studentList.*/
        assert classInv();
        size = size-1;
        Student[] temp = new Student[size];
        for(int i = 0; i < size; i++){
            temp[i] = studentList[i];
        }
        studentList = temp;
        assert classInv();
        return studentList;
    }

    /**Effect: if Student s's index is neither 0 nor the last element in the array, remove student s from
     * the array. Size of array should be original size minus one. Capacity is unchanged.
     * Returns: updated array.
     */
    private Student[] removeMid(Student s){
        /**helper to remove(). called when the element being removed is neither the last or first
         * element in studentList.*/
        assert classInv();
        int midIndex = 0;
        size = size-1;
        for(int i = 0; i < size-1; i++){
            if(studentList[i] == s){
                midIndex = i;
            }
        }
        for(int i = midIndex; i < size; i++){
            studentList[i] = studentList[i+1];
        }
        assert(classInv());
        return studentList;
    }

    /** The String representation of this StudentList */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(get(i));
        }
        sb.append("]");
        return sb.toString();
    }
}
