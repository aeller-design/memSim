public class PageTable {
    public PageTableEntry[] pageFrameBlocks = new PageTableEntry[256];

    public int getFrame(int page) {
        int frame = -1;
        if((pageFrameBlocks[page] != null) && (isInMemory(page))){
            frame = pageFrameBlocks[page].getFrame();
            //pageFrameBlocks[page].setValidBit(true);
        }
        return frame;
    }
    public boolean contains(int page) {
        return pageFrameBlocks[page] != null;
    }
    public void add(int page, int frame) {
        if(contains(page)){
            pageFrameBlocks[page].validBit = true;
        }
        else {
            pageFrameBlocks[page] = new PageTableEntry(frame, true);
        }
    }

    public boolean isInMemory(int page) {
        return pageFrameBlocks[page].validBit;
    }

    public void remove(int page) {
        pageFrameBlocks[page].validBit = false;
    }

    void print() {
        System.out.println("Page Table:");
        for(int i = 0; i < pageFrameBlocks.length; i++) {
            if(pageFrameBlocks[i] != null){
                System.out.print(i + ", ");
                pageFrameBlocks[i].print();
            }
        }
    }
}
