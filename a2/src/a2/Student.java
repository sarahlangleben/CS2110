package a2;

public class Student {
    // Declare the following fields. These fields will hold information describing each Student.
    // Make the fields private and include comments describing each of them in the form of a class invariant
    private String firstName; // * firstName. First name of this Student. Must be a non-empty String.
    /**class invariant: firstName != "" */
    private String lastName; // * lastName. Last name of this Student. Must be a non-empty String.
    /**class invariant: lastName != "" */
    private int year; // * The year this Student is in school. E.g. 1 if Freshman, 2 if Sophomore, etc. Must be > 0
    /**class invariant: year > 0 && year != 0 */
    private Course course; // * course. The Course this Student is enrolled in. This Student may be enrolled in at most 1 course.
    //  null if this Student is not enrolled in any course.
    /** class invariant: course <= 1. if course == 0, then course = null.*/

    public boolean classInv(){
        /**method to check the class invariant**/
        return(year > 0 && firstName != "" && lastName != "");
    }
    /** Constructor: Create a new Student with first name f, last name l, and year y.
     * This student is not enrolled in any Courses.
     * Requires: f and l have at least one character and y > 0. */
    public Student(String f, String l, int y) {
        firstName = f;
        lastName = l;
        year = y;
        course = null;
    }

    /** The first name of this Student. */
    /** field contains the Student's first name.
     * Requires: String of at least 1 character.*/
    public String firstName() {
        return firstName;
    }

    /** The last name of this Student. */
    /** field contains the Student's last name.
     * Requires: String of at least 1 character.
     */
    public String lastName() {
        return lastName;
    }

    /** The first and last name of this Student in the format "First Last". */
    /** field contains Student's full name in the format "First Last"
     * Requires: first and last name are both Strings of at least 1 character.*/
    public String fullName() {
        return firstName()+" "+lastName();
    }

    /** The year in school this Student is in. */
    /**field contains students' year in school.
     * Requires: year is an int with a value more than 0.*/
    public int year() {
        return year;
    }

    /** The course this student is enrolled in. */
    /**field contains course the student is enrolled in.
     * Requires: course is not more than 1.*/
    public Course course() {
        return course;
    }

    /** Enroll this Student in Course c.
     * Requires: The student is not enrolled in a course already.*/
    public void joinCourse(Course c) {
        assert(classInv());
        course = c;
        assert(classInv());
    }

    /**
     * Drop this Student from their Course. Requires: This student is enrolled in a course already.
     */
    public void leaveCourse() {
        assert(classInv());
        course = null;
        assert(classInv());
    }

    /** Return the full name of this Student */
    public String toString() {
        return fullName();
    }
}