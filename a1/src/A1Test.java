/** Test harness for class A1.
 */
class A1Test {

    public static void main(String[] args) {
        testProduct();
        testTheAnswerIs2();
        testMagicTrick();
        testIsLiquid();
        testTheAnswerIs42Conditional();
        testHypotenuse();
        testIsTriangle();
        testCalculate();
        testRangeSum();
        testRangeSumOdd();
        testIsPalindrome();
        testDuplicateVowels();
    }

    static void testProduct() {
        System.out.println("product() = " + A1.product());
        System.out.println("expected = 1001");
    }

    static void testTheAnswerIs2() {
        System.out.println("theAnsweris2(17) = " + A1.theAnsweris2(17));
        System.out.println("expected = 2");
    }

    static void testMagicTrick() {
        System.out.println("magicTrick(100) = " + A1.magicTrick(100));
        System.out.println("expected = 100100");
        //added additional test to make sure the if statement works
        System.out.println("magicTrick(1) = " + A1.magicTrick(1));
        System.out.println("expected = -1");
        //3 digit number that doesn't end in 0
        System.out.println("magicTrick(306) = " + A1.magicTrick(306));
        System.out.println("expected = 306306");
        //negative 3 digit number
        System.out.println("magicTrick(-876) = " + A1.magicTrick(-876));
        System.out.println("expected = -876876");
        //negative 2 digit number
        System.out.println("magicTrick(-12) = " + A1.magicTrick(-12));
        System.out.println("expected = -1");
        //4+ digit number
        System.out.println("magicTrick(1201) = " + A1.magicTrick(1200));
        System.out.println("expected = -1");
        System.out.println("magicTrick(525600) = " + A1.magicTrick(525600));
        System.out.println("expected = -1");
        //4+ digit negative number
        System.out.println("magicTrick(-1200) = " + A1.magicTrick(-1200));
        System.out.println("expected = -1");
        System.out.println("magicTrick(-525600) = " + A1.magicTrick(-525600));
        System.out.println("expected = -1");
    }

    static void testIsLiquid() {
        System.out.println("isLiquid(50) = " + A1.isLiquid(50));
        System.out.println("expected = true");

        //extra test case: testing 3+ digit numbers
        System.out.println("isLiquid(3081) = " + A1.isLiquid(3081));
        System.out.println("expected = false");
        //extra test case: testing negative numbers
        System.out.println("isLiquid(-20) = " + A1.isLiquid(-20));
        System.out.println("expected = false");

        System.out.println("isLiquid(0) = " + A1.isLiquid(0));
        System.out.println("expected = false");

        System.out.println("isLiquid(100) = " + A1.isLiquid(100));
        System.out.println("expected = false");
    }

    static void testTheAnswerIs42Conditional() {
        System.out.println("theAnsweris42Conditional(42) = " + A1.theAnsweris42Conditional(42));
        System.out.println("expected = " + 42);

        //additional test to check else statement
        System.out.println("theAnsweris42Conditional(0) = " + A1.theAnsweris42Conditional(0));
        System.out.println("expected = " + -1);
        System.out.println("theAnsweris42Conditional(-10) = " + A1.theAnsweris42Conditional(-10));
        System.out.println("expected = " + -1);
    }

    static void testHypotenuse() {
        System.out.println("hypotenuse(3, 4) = " + A1.hypotenuse(3, 4));
        System.out.println("expected = 5");

        //extra test case: decimal a or b value
        System.out.println("hypotenuse(.5, 5.2) = " + A1.hypotenuse(.5, 5.2));
        System.out.println("expected = 5.2");
        System.out.println("hypotenuse(210, 69) = " + A1.hypotenuse(210, 69));
        System.out.println("expected = 221.05");
    }

    static void testIsTriangle() {
        System.out.println("isTriangle(3, 4, 5) = " + A1.isTriangle(3, 4, 5));
        System.out.println("expected = true");

        System.out.println("isTriangle(3, 4, 8) = " + A1.isTriangle(3, 4, 8));
        System.out.println("expected = false");

        //extra test case; decimal points
        System.out.println("isTriangle(1.5, 1.5, 10) = " + A1.isTriangle(1.5, 1.5, 10));
        System.out.println("expected = false");
        //extra test case; double digits
        System.out.println("isTriangle(1.5, 15.5, 10) = " + A1.isTriangle(1.5, 15.5, 10));
        System.out.println("expected = false");
    }

    static void testCalculate() {
        System.out.println("calculate(\"ADD\", 2, 3)) = " + A1.calculate("ADD", 2, 3));
        System.out.println("expected = 5");
        //division
        System.out.println("calculate(\"DIVIDE\", 4, 2)) = " + A1.calculate("DIVIDE", 4, 2));
        System.out.println("expected = 2");
        //multiplication
        System.out.println("calculate(\"MULTIPLY\", 4, 2)) = " + A1.calculate("MULTIPLY", 4, 2));
        System.out.println("expected = 8");
        //subtraction
        System.out.println("calculate(\"SUBTRACT\", 4, 2)) = " + A1.calculate("SUBTRACT", 4, 2));
        System.out.println("expected = 2");
        //negative numbers
        System.out.println("calculate(\"DIVIDE\", 4, -2)) = " + A1.calculate("DIVIDE", 4, -2));
        System.out.println("expected = -2");
        System.out.println("calculate(\"MULTIPLY\", -4, 2)) = " + A1.calculate("MULTIPLY", -4, 2));
        System.out.println("expected = -8");
        System.out.println("calculate(\"SUBTRACT\", -4, 2)) = " + A1.calculate("SUBTRACT", -4, 2));
        System.out.println("expected = -6");
        System.out.println("calculate(\"ADD\", 2, -3)) = " + A1.calculate("ADD", 2, -3));
        System.out.println("expected = -1");
        //0 values
        System.out.println("calculate(\"DIVIDE\", 0, 2)) = " + A1.calculate("DIVIDE", 0, 2));
        System.out.println("expected = 0");
        System.out.println("calculate(\"MULTIPLY\", 0, 2)) = " + A1.calculate("MULTIPLY", 0, 2));
        System.out.println("expected = 0");
        System.out.println("calculate(\"SUBTRACT\", 0, 2)) = " + A1.calculate("SUBTRACT", 0, 2));
        System.out.println("expected = -2");
        System.out.println("calculate(\"ADD\", 0, 3)) = " + A1.calculate("ADD", 0, 3));
        System.out.println("expected = 3");
    }

    static void testRangeSum() {
        System.out.println("rangeSum(1, 4) = " + A1.rangeSum(1, 4));
        System.out.println("expected = " + 10);
        //larger numbers
        System.out.println("rangeSum(45, 100) = " + A1.rangeSum(45, 100));
        System.out.println("expected = " + 4060);
        //negative numbers
        System.out.println("rangeSum(-1, 4) = " + A1.rangeSum(-1, 4));
        System.out.println("expected = " + 9);
    }

    static void testRangeSumOdd() {
        System.out.println("rangeSumOdd(1, 4) = " + A1.rangeSumOdd(1, 4));
        System.out.println("expected = " + 4);

        //triple digits
        System.out.println("rangeSumOdd(1, 100) = " + A1.rangeSumOdd(1, 100));
        System.out.println("expected = " + 2500);
        //negative number
        System.out.println("rangeSumOdd(-1, 100) = " + A1.rangeSumOdd(-1, 100));
        System.out.println("expected = " + 2499);
    }

    static void testIsPalindrome() {
        System.out.println("isPalindrome(\"a\") = " + A1.isPalindrome("a"));
        System.out.println("expected = " + true);

        System.out.println("isPalindrome(\"kayak\") = " + A1.isPalindrome("kayak"));
        System.out.println("expected = " + true);

        System.out.println("isPalindrome(\"I did did I\") = " + A1.isPalindrome("I did did I"));
        System.out.println("expected = " + true);

        System.out.println("isPalindrome(\"taco cat\") = " + A1.isPalindrome("taco cat"));
        System.out.println("expected = " + false);

    }

    static void testDuplicateVowels() {
        System.out.println("duplicateVowels(\"abracadabra\") = " + A1.duplicateVowels("abracadabra"));
        System.out.println("expected = aabraacaadaabraa");
        //spaces, capital letters
        System.out.println("duplicateVowels(\"Amy Adams\") = " + A1.duplicateVowels("Amy Adams"));
        System.out.println("expected = AAmy AAdaams");
        //punctuation, dashes, capital letters
        System.out.println("duplicateVowels(\"e-v-e-r-y day, I'm shufflin!'\") = " + A1.duplicateVowels("e-v-e-r-y day I'm shufflin!'"));
        System.out.println("expected = ee-v-ee-r-y daay, II'm shuuffliin!'");
    }
}
