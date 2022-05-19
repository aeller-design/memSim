public class PageTable {
    public PageTableEntry[] pageFrameBlocks = new PageTableEntry[256];

    public int getFrame(int page) {
        int frame = -1;
        if(pageFrameBlocks[page] != null){
            frame = pageFrameBlocks[page].getFrame();
            pageFrameBlocks[page].setValidBit(true);
        }
        return frame;
    }

    public void add(int i, PageTableEntry pte) {
        pageFrameBlocks[i] = pte;
    }

    public void remove(int frame) {

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
