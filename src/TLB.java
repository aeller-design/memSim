import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class TLB {
    ArrayDeque<PageFrameBlock> tlb = new ArrayDeque<>();

    int hits = 0;
    int misses = 0;

    Integer getFrame(int page){
        AtomicInteger frame = new AtomicInteger(-1);

        tlb.forEach(block -> {
            if(block.getPage() == page)
                frame.set(block.getFrame());
        });

        return frame.get();
    }
}

