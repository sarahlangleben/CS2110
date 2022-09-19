package a2;

/** Test harness for Assignment 2
 */
public class A2Test {
    public static void main(String[] args) {
        testEmptyList();
        testAppend();
        testContains();
        testRemove();
        testGetName();
        testGetLectureTime();
        testGetLocation();
        testGetProfessor();
        testGetStudentsEmpty();
        testEnrollStudent();
        testDropStudent();
        //
        // The methods provided do not necessarily test everything in each
        // case.  You will need to add more to the existing testing procedures
        // as well as add new testing procedures.  You can also add tests to
        // test the Course and Student classes.
        // 
        // Try to keep tests small and test features as independently as
        // possible.
    }

    interface J1{
        void foo();
    }
    public static void testEmptyList() {
        StudentList list = new StudentList();
        Student s = new Student("Bill", "Nye", 4);
        System.out.println("list.size() = " + list.size());
        System.out.println("expected = 0");
        System.out.println("list.contains(s) = " + list.contains(s));
        System.out.println("expected = false");
    }

    public static void testAppend() {
        StudentList list = new StudentList();
        Student s = new Student("Bill", "Nye", 4);
        Student jlo = new Student("Jennifer", "Lopez", 10);
        Student may = new Student("May", "Flowers",2);
        Student ken = new Student("ken", "doll", 1);
        Student pit = new Student("Pit", "Bull",20);
        Student ash = new Student("Ash", "Ketchum",12);
        list.append(s);
        list.append(jlo);
        list.append(may);
        list.append(ken);
        list.append(pit);
        list.append(ash);
        list.append(jlo);
        System.out.println(list);
        System.out.println("expected = [Bill Nye, Jennifer Lopez, May Flowers, ken doll, Pit Bull, "
                        + "Ash Ketchum");
        System.out.println(list.size());
        System.out.println("expected size = 7");
    }

    public static void testContains(){
        StudentList list = new StudentList();
        Student s = new Student("Bill", "Nye", 4);
        Student julia = new Student("Julia", "Childe", 4);
        Student may = new Student("May","Flowers",6);
        list.append(s);
        list.append(julia);
        list.append(may);
        System.out.println("list.contains(s) = " + list.contains(julia));
        System.out.println("expected = true");

        StudentList list1 = new StudentList();
        list1.append(may);
        System.out.println("list1.contains(s)= "+list1.contains(may));
        System.out.println("expected = true");
        list1.remove(may);
        System.out.println("list1.contains(s)= "+list1.contains(may));
        System.out.println("expected = false");
    }

    public static void testRemove(){
        StudentList list = new StudentList();
        Student julia = new Student("Julia", "Childe", 1);
        Student may = new Student("May", "Flowers", 4);
        Student april = new Student("April", "Showers", 4);
        Student oct = new Student("Octavius", "Eurydice", 2);
        list.append(julia);
        list.append(april);
        list.append(may);
        list.append(julia);
        System.out.println("original StudentList: "+list);
        list.remove(julia);
        System.out.println("updated StudentList: "+list);

        StudentList list1 = new StudentList();
        list1.append(julia);
        list1.append(julia);
        list1.append(julia);
        list1.append(julia);
        list.remove(julia);
        System.out.println("updated StudentList: "+list1);

        StudentList list2 = new StudentList();
        list2.append(julia);
        list2.append(may);
        list2.append(julia);
        list2.append(may);
        list2.remove(julia);
        list2.remove(may);
        System.out.println("updated StudentList: "+list2);

        StudentList list3 = new StudentList();
        list3.append(julia);
        list3.append(oct);
        list3.append(oct);
        list3.append(may);
        list3.append(april);
        list3.append(julia);
        list3.remove(oct);
        System.out.println("updated StudentList: "+list3);
    }

    public static void testGetName(){
        Course course = new Course(12,12,"Gates 122","Mimno","INFO 2950");
        System.out.println(course.getName());
        System.out.println("expected: INFO 2950");
    }

    public static void testGetLocation(){
        Course course = new Course(12,12,"Gates 122","Mimno","INFO 2950");
        System.out.println(course.getLocation());
        System.out.println("expected: Gates 122");
    }

    public static void testGetProfessor(){
        Course course = new Course(12,12,"Gates 122","Mimno","INFO 2950");
        System.out.println(course.getProfessor());
        System.out.println("expected: Professor Mimno");

    }

    public static void testGetLectureTime(){
        Course course = new Course(12,12,"Gates 122","Mimno","INFO 2950");
        System.out.println(course.getLectureTime());
        System.out.println("expected: 12:12 PM");

        Course courseMilTime = new Course(18,5,"Gates 122","Mimno","INFO 2950");
        System.out.println(courseMilTime.getLectureTime());
        System.out.println("expected: 6:05 PM");

        Course courseNormTime = new Course(9,25,"Gates 122","Mimno","INFO 2950");
        System.out.println(courseNormTime.getLectureTime());
        System.out.println("expected: 9:25 AM");

    }

    public static void testGetStudentsEmpty(){
        Course course = new Course(12,12,"Gates 122","Mimno","INFO 2950");
        System.out.println(course.getStudents());
        System.out.println("expected = []");
    }
    public static void testEnrollStudent(){
        Course course = new Course(10, 5,"Rockefeller B81" ,"Teng","CHIN 4411");
        Student s = new Student("Huiwen","Zhang",3);
        course.enrollStudent(s);
        System.out.println(course.getStudents());
        System.out.println("expected = [Huiwen Zhang]");
    }
    public static void testDropStudent(){
        Course course = new Course(10, 5,"Rockefeller B81" ,"Teng","CHIN 4411");
        Student sarah = new Student("Huiwen","Zhang",3);
        Student bill = new Student("Bill", "Nye",4);
        course.enrollStudent(sarah);
        course.enrollStudent(bill);
        System.out.println("original student list expected: [Huiwen Zhang, Bill Nye]");
        System.out.println("original student list:"+course.getStudents());
        course.dropStudent(sarah);
        System.out.println("updated student list expected: [Bill Nye]");
        System.out.println("updated student list:"+course.getStudents());
    }
}