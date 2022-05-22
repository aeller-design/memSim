public class PhysicalMemory {
    byte[][] physicalMemory;
    int size;
    int index;
    int[] ptPageRef = new int[256];

    public PhysicalMemory(int size) {
        this.index = 0;
        this.size = size;
        this.physicalMemory = new byte[size][256];

    }

    public void add(int i, byte[] data, int page) {
        physicalMemory[i] = data;
        index++;
        ptPageRef[i] = page;
    }

    public void replace(int i, byte[] data){
        physicalMemory[i] = data;
    }

    public boolean isFull() {
        return index >= size;
    }

    public byte[] get(int i) {
        return physicalMemory[i];
    }

    public int getIndex() {
        return index;
    }

    public int getPage(int index) {
        return ptPageRef[index];
    }

    public void print() {
        System.out.println("Physical Memory");
        for (int i = 0; i < size; i++) {
            if(physicalMemory[i] != null) {
                System.out.println("i: " + i + ", page: " + ptPageRef[i] + ", " +
                        physicalMemory[i][0] + physicalMemory[i][1]);
            }
        }
    }

}
