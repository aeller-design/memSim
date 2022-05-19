public class PageFrameBlock {
    private int page;
    private int frame;

    public PageFrameBlock(int page, int frame) {
        this.page = page;
        this.frame = frame;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public void print() {
        System.out.println(page + ", " + frame);
    }
}
