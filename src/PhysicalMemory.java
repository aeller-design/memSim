public class PhysicalMemory {
    byte[][] physicalMemory;
    int size;
    int index;

    public PhysicalMemory(int size) {
        this.index = 0;
        this.size = size;
        this.physicalMemory = new byte[size][256];
    }

    public void add(int i, byte[] data) {
        physicalMemory[i] = data;
        index++;
    }

    public void replace(int i, byte[] data){
        physicalMemory[i] = data;
    }

    public boolean isFull() {
        return index == size;
    }

    public byte[] get(int i) {
        return physicalMemory[i];
    }

    public int getIndex() {
        return index;
    }

}
