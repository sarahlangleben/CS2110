/** Please provide the following information
 * Name(s): Sarah Langleben
 * NetID(s): sml343
 * Tell us what you thought about the assignment: It was good practice to get used to Java syntax. I wish some of the
 * specifications were clearer; like if magicTrick() needs to also take negative and 4+ digit numbers into account. The
 * preconditions and what we should/shouldn't check for was not always clear.
 */

/** The goal of this assignment is to familiarize yourself with Java and start
 * to establish good programming practices.
 */

/** Class A1 defines a set of methods to implement. Each method has a comment
 * at the top. These are method specifications (specs) Method specs allow
 * anyone who is reading the program to immediately understand what the method
 * is doing.
 *
 * Each function body has in it a return statement. Without it, the function
 * won't compile. Replace the return statement with code you write to implement
 * the spec.
 */
public class A1 {

    /** Replace the "-1" with the amount of time you spent on A1 in hours.
     *  Example: for 1 hour and 30 min, write that timeSpent = 1.5
     *  Example: for 1 hour, write that timeSpent = 1 or timeSpent = 1.0
     */
    public static double timeSpent = 10;

    /** Return the product of the values: 7, 11, and 13. */
    public static int product() {
        return 7*11*13;
    }

    /** In the following order: double x, add 4, divide it by 2, and then
     *  subtract the original x value from the result. */
    public static int theAnsweris2(int x){
        return (((x * 2) + 4) / 2)-x;
    }

    /** If x is not a three-digit number, return -1;
     * Otherwise return the product of x and the values: 7, 11, and 13.
     */
    public static int magicTrick(int x) {
        if(Math.abs(x) <= 999 && Math.abs(x) >= 100){
            return x*7*11*13;
        }
        else {
            return -1;
        }
    }

    /** Given some temperature x of water in degrees Celsius,that the
     * melting point of water is 0ºC , and that the boiling point is 100ºC,
     * determine if the water is liquid. Note: in our program water is not
     * a liquid at 0ºC or 100ºC.
     */
    public static boolean isLiquid(int temperature){
        return ((temperature > 0) && (temperature < 100));
    }

    /** Given some value x, return 42 if x is equal to 42. If not, return -1.
     */
    public static int theAnsweris42Conditional(int x){
        return((x==42) ? x:-1);
    }

    /** Given two triangle legs a and b of a right triangle, return the hypotenuse.
     *  Requires: a and b must be positive values.
     */
    public static double hypotenuse(double a, double b) {
        return Math.sqrt(a*a+b*b);
    }

    /** Given three triangle side lengths a, b, and c, determine if the triangle can exist.
     */
    public static boolean isTriangle(double a, double b, double c) {
        return !(a + b <= c) && !(a + c <= b) && !(b + c <= a);
    }

    /** Given four operations: ADD, SUBTRACT, MULTIPLY, and DIVIDE and two inputs x and y,
     *  return the result of the operation between x and y.
     *  Requires: opp = DIVIDE and y = 0 cannot be true at the same time.
     */
    public static int calculate(String opp, int x, int y) {
        int finalVal = 0;
        if(opp.equals("ADD")){
            finalVal = x+y;
        }
        else if(opp.equals("SUBTRACT")){
            finalVal = x-y;
        }
        else if(opp.equals("MULTIPLY")){
            finalVal = x*y;
        }
        else if(opp.equals("DIVIDE")){
            finalVal = x/y;
        }
        return finalVal;
    }

    /** Return the sum of the values in n to m inclusive.
     */
    public static int rangeSum(int n, int m) {
        int val = 0;
        for(int i = n; i<m+1; i++){
            val = val+i;
        }
        return val;
    }

    /** Return the sum of the odd values in n to m inclusive.
     */
    public static int rangeSumOdd(int n, int m) {
        int oddTotal = 0;
        int number = n;
        while (number <= m){
            if(number%2!=0) {
                oddTotal = oddTotal + number;
            }
            number = number + 1;
        }
        return oddTotal;
    }

    /** Return whether str is a palindrome.
     */
    public static boolean isPalindrome(String str) {
        String revStr = "";
        for(int i = str.length()-1; revStr.length() < str.length(); i--){
                revStr = revStr + str.charAt(i);
        }
        return(str.equals(revStr));
    }

    /** Return a String that return a string that contains the
     *  same characters as str, but with each vowel duplicated.
     */
    public static String duplicateVowels(String str) {
        String dupStr = "";
        for(int i = 0; i < str.length(); i++){
            if(((str.charAt(i) == ('a')) || (str.charAt(i) == ('e')) || (str.charAt(i) == ('i'))
                    || (str.charAt(i) == ('o')) || (str.charAt(i) == ('u')) || (str.charAt(i) == ('A'))
                    || (str.charAt(i) == ('E')) || (str.charAt(i) == ('I')) || (str.charAt(i) == ('O'))
                    || (str.charAt(i) == ('U')))) {
                dupStr = dupStr + str.charAt(i) + str.charAt(i);
            }
            else {
                dupStr = dupStr + str.charAt(i);
            }
        }
        return dupStr;
    }
}