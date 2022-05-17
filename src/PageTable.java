public class PageTable {
    public PageTableEntry[] pageFrameBlocks = new PageTableEntry[256];

    int hits = 0;
    int misses = 0;

    public int getFrame(int page) {
        int frame = -1;
        if(pageFrameBlocks[page] != null){
            frame = pageFrameBlocks[page].getFrame();
            pageFrameBlocks[page].setValidBit(true);
        }
        return frame;
    }
}
