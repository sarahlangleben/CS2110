package a2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static StudentList students = new StudentList();
    public static Course[] courses = new Course[0];

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length > 0) {
            File file = new File("tests", args[0]);
            try {
                processCommands(new Scanner(file));
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + file);
            }
        } else {
            processCommands(new Scanner(System.in));
        }
    }

    /**
     * Read commands from a Scanner and execute them. Return when the "exit" command is read.
     *
     * @param sc The scanner
     */
    public static void processCommands(Scanner sc) {
        while (true) {
            System.out.print("Please enter a command: ");
            String input = sc.nextLine().trim();
            String[] words = input.split(" ");
            switch (words[0]) {
                case "help":
                    getCommandHelp();
                    break;
                case "addstudent":
                    runAddStudentCommand(words);
                    break;
                case "addcourse":
                    runAddCourseCommand(words);
                    break;
                case "enroll":
                    runEnrollCommand(words);
                    break;
                case "drop":
                    runDropCommand(words);
                    break;
                case "courses":
                    runListCoursesCommand();
                    break;
                case "students":
                    runListStudentsCommand();
                    break;
                case "enrollment":
                    runListEnrollmentCommand(words);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println(
                            "This is not a valid command. For help, enter the command \"help\"");
            }
        }
    }

    public static void getCommandHelp() {
        System.out.println("\nhelp\n" +
                "addstudent <firstName> <lastName> <year>\n" +
                "addcourse <courseName> <profName> <location> <time (24-hour)>\n" +
                "enroll <student index> <course index> \n" +
                "drop <student index> course index>\n" +
                "students\n" +
                "courses\n" +
                "enrollment <course index>\n" +
                "exit\n");
    }

    private static void invalidCommand(String cmd) {
        System.out.println("Invalid " + cmd + "command. " +
                "Enter the command \"help\" for information about that command.");
    }

    public static void runAddStudentCommand(String[] command) {
        if (command.length < 4) {
            invalidCommand("add student");
            return;
        }
        String first = command[1];
        String last = command[2];
        int yr;
        try {
            yr = Integer.parseInt(command[3]);
        } catch (NumberFormatException e) {
            invalidCommand("add student");
            return;
        }
        Student newStudent = new Student(first, last, yr);
        students.append(newStudent);
        System.out.println(
                "Successfully created new student: " + newStudent + ", year: " + newStudent.year());
    }

    public static void runAddCourseCommand(String[] command) {
        if (command.length < 5) {
            invalidCommand("add course");
            return;
        }
        String name = command[1];
        String prof = command[2];
        String loc = command[3];
        int hr;
        int min;
        String time = command[4];
        String[] timeArr = time.split(":");
        if (timeArr.length < 2) {
            System.out.println("Invalid time input");
            return;
        } else {
            try {
                hr = Integer.parseInt(timeArr[0]);
                if (hr < 0 || hr > 23) {
                    System.out.println("Invalid time input");
                    return;
                } else {
                    min = Integer.parseInt(timeArr[1]);
                    if (min < 0 || min > 59) {
                        System.out.println("Invalid time input");
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid time input");
                return;
            }
        }
        Course newCourse = new Course(hr, min, loc, prof, name);
        Course[] newCourses = new Course[courses.length + 1];
        for (int i = 0; i < courses.length; i++) {
            newCourses[i] = courses[i];
        }
        newCourses[courses.length] = newCourse;
        courses = newCourses;
        System.out.println("Successfully created new course: " + newCourse.getName() + " taught by "
                + newCourse.getProfessor() + " at " + newCourse.getLectureTime() + ", Location: "
                + newCourse.getLocation());
    }

    public static void runEnrollCommand(String[] command) {
        if (command.length < 3) {
            invalidCommand("enroll");
            return;
        }
        try {
            int studentInd = Integer.parseInt(command[1]);
            int courseInd = Integer.parseInt(command[2]);
            if (studentInd < students.size() && courseInd < courses.length) {
                Student student = students.get(studentInd);
                Course course = courses[courseInd];
                if (course.enrollStudent(student)) {
                    System.out.println(student +
                            " was successfully enrolled in " + course.getName());
                } else {
                    System.out.println(students.get(studentInd) +
                            " is already enrolled in " + student.course().getName());
                }
            } else {
                System.out.println("One of the indices you provided is out of bounds");
            }
        } catch (NumberFormatException e) {
            invalidCommand("enroll");
        }
    }

    public static void runDropCommand(String[] command) {
        if (command.length < 3) {
            invalidCommand("drop");
            return;
        }
        try {
            int studentInd = Integer.parseInt(command[1]);
            int courseInd = Integer.parseInt(command[2]);
            if (studentInd < students.size() && courseInd < courses.length) {
                Student student = students.get(studentInd);
                Course course = courses[courseInd];
                boolean success = course.dropStudent(student);
                if (success) {
                    System.out.println(student +
                            " was successfully dropped from " + course.getName());
                } else {
                    System.out.println(students.get(studentInd) +
                            " is not enrolled in " + course.getName());
                }
            } else {
                System.out.println("One of the indices you provided is out of bounds");
            }
        } catch (NumberFormatException e) {
            invalidCommand("drop");
        }
    }

    public static void runListStudentsCommand() {
        System.out.println();
        for (int i = 0; i < students.size(); i++) {
            System.out.println(i + " " + students.get(i));
        }
    }

    public static void runListCoursesCommand() {
        System.out.println();
        System.out.printf("%3s%-40s%-12s%-20s%-40s%n",
                "i"," Name", "Time", "Instructor", "Location");
        for (int i = 0; i < courses.length; i++) {
            System.out.printf("%3d ", i);
            courses[i].print();
            System.out.println();
        }
    }

    public static void runListEnrollmentCommand(String[] command) {
        if (command.length < 2) {
            invalidCommand("list enrollment");
            return;
        }
        try {
            int courseInd = Integer.parseInt(command[1]);
            if (courseInd < courses.length) {
                Course course = courses[courseInd];
                System.out.println(course.getStudents());
            } else {
                System.out.println("Index " + courseInd + " is out of bounds");
            }
        } catch (NumberFormatException e) {
            invalidCommand("list enrollment");
        }
    }
}
