package a2;

public class Course {
    /**
     * List of all students enrolled in this Course.
     */
    private StudentList students;
    /**
     * The hour at which lecture for this Course is held (in 24-hour time). 0 <= hour <= 23
     */
    private int hour;
    /**
     * The minutes at which lecture for this Course is held. 0 <= min <= 59 The lectures for this
     * course are at hour:min
     */
    private int min;
    /**
     * The location of lectures at this course (e.g. Statler Hall Room 185) Must be non-empty
     */
    private String location;
    /**
     * The last name of the professor of this course (e.g. Myers, Muhlberger, Gries) Must be
     * non-empty
     */
    private String prof;
    /**
     * The name of this course (e.g. Object-Oriented Programming and Data Structures) Must be
     * non-empty
     */
    private String name;

    public boolean classInv(){
        return(0 <= hour && hour <= 23 && 0 <= min && min <= 59 && prof != "" && location != "" &&
                name != "");
    }
    /**
     * Constructor: Create new Course with name n, professor last name prof, location loc,<br> and
     * lectures are held at time hour:min. The course has no students. Precondition: n, prof, and
     * loc have at least one character in them, 0 <= hr <= 23, 0 <= min <= 59.
     */
    public Course(int hr, int min, String loc, String prof, String n) {
        // Note that an empty StudentList is not the same as null
        hour = hr;
        this.min = min;
        location = loc;
        this.prof = prof;
        name = n;
        students = new StudentList();
    }

    /** Return the name of this course. */
    public String getName() {
        return name;
    }

    /**
     * Return the time at which lectures are held for this course in the format hour:min AM/PM using
     * 12-hour time. For example, "11:15 AM", "1:35 PM". Add leading zeros to the minutes if necessary.
     */
    public String getLectureTime() {
        //conditions:
        // hour > 12 --> hour = hour - 12
        // min < 10 --> "0" + min
        // hour >= 12 --> + "PM"
        //default: 9:30 AM
        return(hourHelp()+":"+minHelp()+aM());
    }
    /**Effect: If hour is more than or equal to 12 in military time, "PM" will be added to the end of
     * the lecture time. Else, "AM" will be added. */
    private String aM(){
        /**helper method to decide if hour is an AM or PM**/
        assert classInv();
        if(hour >= 12){
            return " PM";
        }
        assert classInv();
        return " AM";
    }
    /**Effect: Converts military format to 12-hour format. */
    private int hourHelp(){
        /**helper method to standardize hour to a 12-hour time system.*/
        assert classInv();
        if(hour > 12){
            return(hour-12);
        }
        if(hour == 0 || hour == 00){
            return(12);
        }
        assert classInv();
        return hour;
    }
    /**Effect: converts single digit minutes to standard '00' format. Example: 1 --> 01*/
    private String minHelp(){
        /**helper method to add a 0 in front of minutes less than 10*/
        assert classInv();
        if(min < 10){
            return("0"+min);
        }
        assert classInv();
        return (String.valueOf(min));
    }

    /**
     * Return the location of lectures in this course.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Return the name of the professor in the format "Professor LastName"
     */
    public String getProfessor() {
        return "Professor "+ prof;
    }

    /**
     * Return the String representation of the list of students enrolled in this course
     */
    public String getStudents() {
        assert(classInv());
        String s = students + "";
        return s;
    }

    /**
     * Enroll a new student s to this course. If Student s is already enrolled in a course, they
     * cannot enroll in this course. Return true if the student was successfully enrolled in the
     * course.
     */
    public boolean enrollStudent(Student s) {
        // Remember that the class invariant of all classes must be kept true.
        // In other words, make sure that every field is correctly modified based on its
        // Javadoc comments.
        assert classInv();
        if(!students.contains(s)){
            students.append(s);
            assert classInv();
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Drop Student s from this course. If Student s is not enrolled in this course, they cannot be
     * dropped from this course. Return true if the student was successfully dropped from the
     * course.
     */
    public boolean dropStudent(Student s) {
        // Remember that the class invariant of all classes must be kept true.
        // In other words, make sure that every field is correctly modified based on its
        // Javadoc comments.
        assert classInv();
        if(students.contains(s)){
            students.remove(s);
            assert classInv();
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * Print the Course information in tabular format
     */
    public void print() {
        System.out.printf("%-40s%-12s%-20s%-40s", name, getLectureTime(), prof, location);
    }
}