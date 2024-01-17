import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the input file: ");
        String filePath = scanner.nextLine();

        int inputVariableNumber = 0;
        int tapeVariableNumber = 0;
        int statesNumber = 0;

        ArrayList<String> statesList = null;

        String startState = null;
        String acceptState = null;
        String rejectState = null;

        String blankSymbol = null;

        ArrayList<String> tapeAlphabet = null;
        String inputAlphabet = null;

        int transitionNumber = 0;
        ArrayList<Transition> allTransitions = null;

        String input = null;

        try {
            File inputFile = new File(filePath);
            Scanner fileScanner = new Scanner(inputFile);

            inputVariableNumber = fileScanner.nextInt();
            tapeVariableNumber = fileScanner.nextInt();
            statesNumber = fileScanner.nextInt();

            statesList = new ArrayList<>();
            for (int i = 0; i < statesNumber; i++) {
                statesList.add(fileScanner.next());
            }

            startState = fileScanner.next();
            acceptState = fileScanner.next();
            rejectState = fileScanner.next();

            blankSymbol = fileScanner.next();

            tapeAlphabet = new ArrayList<>();
            for (int i = 0; i < tapeVariableNumber + 1; i++) {
                tapeAlphabet.add(fileScanner.next());
            }

            inputAlphabet = fileScanner.next();

            transitionNumber = fileScanner.nextInt();

            allTransitions = new ArrayList<>();
            for (int i = 0; i < (transitionNumber); i++) {

                String sourceState = fileScanner.next();
                String readingAlphabet = fileScanner.next();
                String writingAlphabet = fileScanner.next();
                String direction = fileScanner.next();
                String targetState = fileScanner.next();

                Transition oneTransition = new Transition(sourceState, readingAlphabet, writingAlphabet, direction,
                        targetState);
                allTransitions.add(oneTransition);
            }

            input = fileScanner.next();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }

        ArrayList<String> visitedStates = new ArrayList<>();
        StringBuffer tape = new StringBuffer();

        tape.append(blankSymbol);
        tape.append(input);
        tape.append(blankSymbol);

        int currentIndex = 1;
        String currentState = startState;
        visitedStates.add(currentState);
        boolean isFinish = false;

        while (!isFinish) {

            Transition transition = new Transition();
            String currentTapeChar = String.valueOf(tape.charAt(currentIndex));
            for (Transition t : allTransitions) {
                if (t.sourceState.equals(currentState) && t.readingAlphabet.equals(currentTapeChar)) {
                    transition = t;
                    break;
                }
            }

            tape.replace(currentIndex, currentIndex + 1, transition.writingAlphabet);
            currentState = transition.targetState;
            visitedStates.add(currentState);

            if (transition.direction.equals("L")) {
                currentIndex--;
            } else {
                currentIndex++;
            }
            if (visitedStates.size() > 100 || currentState.equals(acceptState) || currentState.equals(rejectState)) {
                isFinish = true;
            }
        }
        printResult(currentState, visitedStates, acceptState, rejectState);
    }

    public static void printResult(String currentState, ArrayList<String> visitedStates, String acceptState,
            String rejectState) {

        String outputFilePath = "output.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

            if (visitedStates.size() > 100) {
                writer.write("Loop !");
            } else {

                for (String state : visitedStates) {
                    writer.write(state + " ");
                }

                if (currentState.equals(acceptState)) {
                    writer.newLine();
                    writer.write("Accepted !");

                }
                if (currentState.equals(rejectState)) {
                    writer.newLine();
                    writer.write("Rejected !");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
