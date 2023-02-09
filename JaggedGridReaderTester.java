/**
 * File: JaggedGridReaderTester
 * Purpose: test the JaggedGridReader class
 * Date: Sep 15, 2022
 * Version: 3 - Allow 2 arguments
 */

//cell width = width/grid[row].length; //fill space
//cell width = width/maxCols; //equal cell width
//cell height = height/ grid.length;//equal height cells
//cellX = col * cellWidth;
//cellY = row * cellHeight;

import java.util.Scanner;
import java.io.File;

public class JaggedGridReaderTester {
    public static void main(String[] args) {

        // the last readable character in an ASCII file
        final char LAST_CHAR = '~';
        // the End of File Pattern for the Scanner
        final String EOF = "\\Z";
        // use this file
        String FILE_NAME = "JaggedGridReaderTester.java";
        boolean allTestsPass = true;

        // the test to perform
        int index = -1;

        // get the test from the command line
        if (args.length > 0) {
            try {
                index = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                FILE_NAME = args[0];
            }
        }

        if (args.length > 1) {
            try {
                index = Integer.valueOf(args[1]);
            } catch (NumberFormatException e) {
                FILE_NAME = args[1];
            }
        }

        // to keep track of which test to run
        boolean[] test = new boolean[4];

        // either run 1 test or all tests
        if (index >= 0 && index < test.length) {
            test[index] = true;
            System.out.println("Runing test: " + index);
        } else {
            System.out.println("Running all tests!");
            for (int i = 0; i < test.length; ++i) {
                test[i] = true;
            }
        }

        // in case JaggedGridReader or any of the File operations
        // throw an exception
        try {

            // make a JaggedGridReader to be tested
            JaggedGridReader reader = new JaggedGridReader(FILE_NAME);
            System.out.println("Testing File: " + FILE_NAME);
            // test getFileName()
            if (test[0]) {
                String testFileName = reader.getFileName();
                if (testFileName != null &&
                        testFileName.equals(FILE_NAME)) {
                    System.out.println("getFileName() works!");
                    System.out.println("Test 0: PASS");
                } else {
                    System.out.println("getFileName() incomplete.");
                    System.out.println(testFileName + " should be " + FILE_NAME);
                    System.out.println("Test 0: FAIL");
                    allTestsPass = false;
                }
            }

            // test that toString() matches the file
            if (test[1]) {
                String toString = reader.toString();
                if (toString != null) {
                    String[] lines = toString.split("\n");
                    Scanner scan = new Scanner(new File(FILE_NAME));
                    boolean matches = true;
                    int i = 0;
                    while (scan.hasNextLine()) {
                        String testLine = scan.nextLine();
                        if (!testLine.equals(lines[i])) {
                            matches = false;
                        }
                        ++i;
                    }

                    if (matches) {
                        System.out.println("toString() works!");
                        System.out.println("Test 1: PASS");
                    } else {
                        System.out.println("toString() incomplete.");
                        System.out.println("Test 1: FAIL");
                        allTestsPass = false;
                    }
                } else {
                    System.out.println("toString() incomplete (null).");
                    System.out.println("Test 1: FAIL");
                    allTestsPass = false;
                }
            }

            // test that getCopy() returns a copy and not a reference
            if (test[2]) {
                char[][] grid1 = reader.getCopy();
                if (grid1 != null && grid1.length > 0 && grid1[0].length > 0) {
                    if (grid1[0][0] < LAST_CHAR) {
                        grid1[0][0] = (char) (grid1[0][0] + 1);
                    } else {
                        grid1[0][0] = (char) (grid1[0][0] - 1);
                    }
                    char[][] grid2 = reader.getCopy();
                    if (grid1[0][0] != grid2[0][0]) {
                        System.out.println("getCopy() copies the grid!");
                        System.out.println("Test 2: PASS");
                    } else {
                        System.out.println("getCopy() passes a reference to the grid.");
                        System.out.println("Test 2: FAIL");
                        allTestsPass = false;
                    }
                } else {
                    System.out.println("getCopy() incomplete.");
                    System.out.println("Test 2: FAIL");
                    allTestsPass = false;
                }
            }

            // test that getCopy() is holding the contents of the file
            // by comparing getCopy to toString();
            if (test[3]) {
                char[][] grid = reader.getCopy();
                String toString = reader.toString();
                if (grid != null && toString != null) {
                    char[] letters = toString.toCharArray();
                    int row = 0;
                    int col = 0;
                    boolean matches = true;
                    for (char x : letters) {
                        if (x == '\n') {
                            row++;
                            col = 0;
                        } else {
                            if (grid[row][col] != x) {
                                matches = false;
                            }
                            col++;
                        }
                    }
                    if (matches) {
                        System.out.println("getCopy() matches toString()!");
                        System.out.println("Test 3: PASS");
                    } else {
                        System.out.println("getCopy() doesn't match toString().");
                        System.out.println("Test 3: FAIL");
                        allTestsPass = false;
                    }
                } else {
                    System.out.println("getCopy() and/or toString() returns null.");
                    System.out.println("Test 3: FAIL");
                    allTestsPass = false;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception thrown");
            System.out.println("Test: FAIL");
            allTestsPass = false;
            e.printStackTrace();
        }
        if (allTestsPass) {
            System.out.println("All tests PASSED for file: " + FILE_NAME);
        } else {
            System.out
                    .println("At least one test failed for file: " + FILE_NAME + ". Please, see above for what failed");
        }
    }
}
