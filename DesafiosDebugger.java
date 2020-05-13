import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DesafiosDebugger {
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private static Scanner outputScanner;

    private static Scanner expectedScanner;

    private static List<String> outputResult = new ArrayList<>();
    
    private static List<String> expectedResult = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        initializeScanners();
        getOutputResult();
        getExpectedResult();
        assertResults();
    }

    public static void assertResults() {
        System.out.println("\n");

        if(outputResult.size() != expectedResult.size()) {
            System.out.println(ANSI_RED + "The outputFile has a different number of lines than expectedResult, please make sure you updated the expectedResult file to match the new input");
            System.out.println(ANSI_RED + "If you did than your code is returning a different amount of lines than expected");
        }
        else {
            System.out.println(ANSI_BLUE + "Your Results are:\n");
            int correctLines = 0;
            int incorrectLines = 0;

            int counter = 0;
            for(String correctLine : expectedResult) {

                if(correctLine.equals(outputResult.get(counter))) {
                    System.out.printf("%sLine %s is correct\n", ANSI_GREEN, counter + 1);
                    correctLines++;
                }
                if(!correctLine.equals(outputResult.get(counter))) {
                    System.out.printf("%sLine %s is incorrect\n", ANSI_RED, counter + 1);
                    String errorOutlinedLine = findErrors(correctLine, outputResult.get(counter));
                    System.out.printf("%s%s %s!= %s%s\n", ANSI_YELLOW, errorOutlinedLine, ANSI_RED, ANSI_GREEN, correctLine);
                    incorrectLines++;
                }
                counter++;
            }

            System.out.println("");
            System.out.println(ANSI_BLUE + "Summary:\n");
            System.out.println(String.format("%sCorrect Lines: %s", ANSI_GREEN, correctLines));
            System.out.println(String.format("%sCorrect Lines: %s", ANSI_RED, incorrectLines));
            System.out.println(String.format("%sError percentage: %.2f%%", ANSI_RED, ((float) incorrectLines / (float) correctLines) * 100));
        }
    }

    private static String findErrors(String base, String incorrect) {
        String returnString = incorrect;
        for(String word : incorrect.split(" ")) {
            if(!base.contains(word)) returnString = returnString.replace(word, (ANSI_RED + word + ANSI_YELLOW));
        }

        return returnString;
    }

    private static void initializeScanners() throws FileNotFoundException {
        outputScanner = new Scanner(new FileReader(new File("/Users/nerlucas/Documents/DesafiosPython/outputFile.txt")));
        expectedScanner = new Scanner(new FileReader(new File("/Users/nerlucas/Documents/DesafiosPython/assets/expectedOutputFile.txt")));
    }

    private static void getOutputResult() {
        while(outputScanner.hasNext()) {
            String line = outputScanner.nextLine();
            outputResult.add(line);
        }
    }

    private static void getExpectedResult() {
        while(expectedScanner.hasNext()) {
            String line = expectedScanner.nextLine();
            expectedResult.add(line);
        }
    }
}