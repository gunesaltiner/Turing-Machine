public class Transition {

    String sourceState;
    String readingAlphabet;
    String writingAlphabet;
    String direction;
    String targetState;

    Transition() {
    }

    Transition(String sourceState, String readingAlphabet, String writingAlphabet, String direction,
            String targetState) {
        this.sourceState = sourceState;
        this.readingAlphabet = readingAlphabet;
        this.writingAlphabet = writingAlphabet;
        this.direction = direction;
        this.targetState = targetState;
    }

}
