public class PageTableEntry {
    int frame;
    boolean validBit;

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public boolean isValidBit() {
        return validBit;
    }

    public void setValidBit(boolean validBit) {
        this.validBit = validBit;
    }
}
