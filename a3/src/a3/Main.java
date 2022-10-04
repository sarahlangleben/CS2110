package a3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    /**
     * Replace "-1" by the time you spent on A3 in hours.
     * Example: for 3 hours 15 minutes, use 3.25
     * Example: for 4 hours 30 minutes, use 4.50
     * Example: for 5 hours, use 5 or 5.0
     */
    public static double timeSpent = 25;

    /* TODO 6
     * Write a function that takes in a csv file name as a string and returns a Linked List
     * representation of the CSV table.
     */
    //line 1 --> netid --> first --> last
    //go line by line in the file, grab line and convert it to a linkedlist
    //put linked list into the top level
    public static LList<LList<String>> csvToList(String fileName) throws FileNotFoundException {
        LList<LList<String>> lst = new SLinkedList<>();
        String[] array_one;
        //This method should use a FileReader to read each line of the CSV and separate it by commas.
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            //storing first row with column names in line_one, making it into an array
            while(line != null){
                LList<String> row1 = new SLinkedList<>();
                String[] arr = line.split(",");
                //do this for every line
                for(int i = 0; i < arr.length; i++){
                    row1.append(arr[i]);
                }
                lst.append(row1);
                line = br.readLine();
            }
        }
        catch(IOException e){
            System.out.println("error reading from file");
            System.out.println(e);
            System.exit(1);
        }
        return lst;
    }

    /* TODO 7
     * Write a function that takes in two lists representing tables and
     * computes their left join.
     */

    public static LList<LList<String>> join(LList<LList<String>> table1, LList<LList<String>> table2) {
        boolean val1 = false;
        boolean val2 = false;
        LList<LList<String>> result = new SLinkedList<>();
        for(LList<String> row1 : table1){
            String col1 = row1.head();
            for(LList<String> row2 : table2){
                if(col1.equals(row2.head())){
                    val1 = true;
                    LList<String> update = new SLinkedList<>();
                    for(String title1 : row1){ update.append(title1);}
                    for(String title2 : row2){
                        if(val2 == false){ val1 = true; }
                        else{ update.append(title2);}
                    }
                    val2 = false;
                    result.append(update);
                }
            }
        }
//
//        //1. create a result table LList<LList<String>>
//        //2 iterate through all the rows in table 1, get the head of those values
//        //3. iterate through all the rows of table 2, check if head value of row in table 2
//        // is equal to head value in row in table 1
//        //4. if so, create a new linkedlist as a new row, append it to new one
//        //a linkedlist where each element is a row
//        //llist of string in table 1
//        //you will need a for-each loop
//
//
//        //1. add in first element in table1, then add in its block and tool.
//        //2. go to the next table, find the corresponding tool
//        //3. empty row will be: [Skeleton,,Stone]
//        //the last line of the csv will be null; call while it's not null
//        LList<LList<String>> result = table1;
//        LList<String> newRow = new SLinkedList<>();
//        String rowContent1 = new String();
//        String rowContent2 = new String();
//        LList<String> rowToAdd = new SLinkedList<>();
////        for(LList<String> row1 : table1){
////            for(int i = 1; i <= table1.head().size(); i++){
////                String col_val = row1.get(i);
////                System.out.println(col_val);
////            }
////        }
//        for(LList<String> row1 : table1) {
//            for(LList<String> row2 : table2) {
//                if(!row1.head().equals(row2.head())){
//                    //LList<String> rowToAdd = new SLinkedList<>();
//                        rowToAdd.append(row2.head());
//                        for(String row2 : table2){
//                            rowToAdd.append(row2);
//                        }
////                        for(String row2 : row1){
//////                            rowToAdd.append(x);
////////                            if(!x.equals(row1.head())){
////////                                rowToAdd.append(x);
////////                            }
////                            for(String z : row2){
////                                rowToAdd.append(z);
////                            }
////                        }
//                            //if(head) skip
//                        //newRow.append(row2.head());
//                }
//            }
//        }
//        result.append(rowToAdd);
//        for(int i = 0; i <= table2.head().size()+1; i++){
//            //this puts in the column headers!
//            String x = table2.head().get(i);
//            if(!result.head().contains(x)){
//                result.head().append(x);
//            }
//        }
////        for(int i = 0; i < table2.size(); i++){
////            for(int j = 0; j < table2.get(i).size(); j++){
////                rowContent2 = table2.get(i).get(j);
////                result.get(i).append(rowContent2);
////            }
////            result.get(i).append(rowContent2);
////        }
////
//        for(int i = 0; i < table1.size(); i++){ //working
//            for(int j = 1; j < table1.get(i).size(); j++){
//                 rowContent1 = table1.get(i).get(j);
//            }
//            result.get(i).append(rowContent1);
//        }
//
//        return result;
        return result;
    }
    /** Effect: Print a usage message to standard error. */
    public static void usage() {
        System.err.println("Usage: a3.Main <file1.csv> <file2.csv>");
    }

    /* TODO 8
     * Write the main method, which parses the command line arguments, reads CSV files
     * into tables, and prints out the resulting table resulting from a left join of the
     * input tables. Hint: use helper methods.
     */
    public static void main(String[] args) throws FileNotFoundException{
        LList<LList<String>> set1 = csvToList(args[0]);
        LList<LList<String>> set2 = csvToList(args[1]);
        LList<LList<String>> result = join(set1, set2);
        for(LList<String> firstLine : result){
            boolean val = true;
            for(String x : firstLine){
                if(val == false){
                    System.out.print(",");
                }
                val = false;
                System.out.print(x);
            }
            System.out.println();
        }
//        if(args.length > 0){
//            File file = new File("tests", args[0]);
//            processCommands(new Scanner(file));
//            processCommands(new Scanner(System.in));
////            try {
////                processCommands(new Scanner(file));
////            }
////            catch(FileNotFoundException e){
////                System.out.println("File not found: "+file);
////            }
////            processCommands(new Scanner(System.in));
//        }
    }
//    public static void processCommands(Scanner sc) throws FileNotFoundException {
//        System.out.print("Please enter two csv files: ");
//        String input = sc.nextLine().trim();
//        String[] words = input.split(" ");
//        LList<LList<String>> file1 = runCsvToList(words[0]); // first make them linked lists, then call join on them
//        LList<LList<String>> file2 = runCsvToList(words[1]);
//        runJoin(file1, file2);
//    }

//    private static void runJoin(LList<LList<String>> file1, LList<LList<String>> file2) {
//        LList<LList<String>> last_lst = join(file1, file2);
//        System.out.println(last_lst);
//    }
//
//    public static LList<LList<String>> runCsvToList(String command) throws FileNotFoundException {
//        return csvToList(command);
//    }
}
