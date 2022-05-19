import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class TLB {
    ArrayDeque<PageFrameBlock> tlb = new ArrayDeque<>();

    float hits = 0;
    float misses = 0;

    Integer getFrame(int page){
        AtomicInteger frame = new AtomicInteger(-1);

        tlb.forEach(block -> {
            if(block.getPage() == page)
                frame.set(block.getFrame());
        });

        return frame.get();
    }

    void add(PageFrameBlock pfb){
        if(tlb.size() == 16)
            tlb.removeFirst();

        tlb.addLast(pfb);
    }

    void print() {
        System.out.println("TLB:");
        tlb.forEach(PageFrameBlock::print);
    }
}

