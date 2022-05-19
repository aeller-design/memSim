import java.util.ArrayDeque;

public class ReplacementAlgorithm {
    ArrayDeque<Integer> replacementList = new ArrayDeque<>();
    int size;
    String pra;
    int fifoCount = 0;

    ReplacementAlgorithm(int size, String pra) {
        this.size = size;
        this.pra = pra;
    }

    void add(int frame) {
        if(replacementList.contains(frame))
            replacementList.removeFirstOccurrence(frame);

        replacementList.addLast(frame);
    }

    int get() {
        if(pra.equals("FIFO")) {
            return fifoCount % size;
        }
        else if(pra.equals("LRU")) {
            return replacementList.removeFirst();
        }

        return -1;
    }
}

