import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Driver {
    static PageTable pageTable = new PageTable();
    static PhysicalMemory physicalMemory;
    static TLB tlb = new TLB();
    static ArrayList<Integer> refSequence;
    static byte[][] backingStore = new byte[256][256];

    static String pra = "FIFO";
    static String rsFileName;
    static int frames = 256;

    public static void main(String[] args) throws IOException {
        // initialize all tables
        initialize(args);

        processRequest();

        // TODO: print final counts here

    }

    public static void initialize(String[] args) throws IOException {
        // check for valid arguments
        if(!parseArgs(args))
            return;

        // read backing store file
        readBin("BACKING_STORE.bin");
        // read reference sequence file
        refSequence = readInputFile(rsFileName);
        // initialize physical memory size to frames input
        physicalMemory = new PhysicalMemory(frames);
    }

    public static void printInfo(
            int address, int byteReferenced, int frameNumber, byte[] frameContent) {
        System.out.println(
                address + ", " + byteReferenced + ", " + frameNumber + ", " + convertByteToHexadecimal(frameContent));
    }

    public static void processRequest() {
        //int address, byteReferenced, frameNumber;
        //byte[] frameContent;
        refSequence.forEach(entry -> {
            int frame = searchFrame(entry);
            byte[] frameContent;
            if(frame != -1) {
                // frame found, retrieve from memory
                frameContent = physicalMemory.get(frame);
            } else {
                // check backing store
                frameContent = checkBackingStore(entry);

                // if physical memory has space
                if(!physicalMemory.isFull()){
                    frame = physicalMemory.getIndex();
                    physicalMemory.add(frame, frameContent);
                } else {
                    // physical memory is full, run replacement algorithm
                    // TODO: Handle replacement algorithm here
                    // TODO: ****IMPORTANT**** REPLACE PLACEHOLDER FRAME NUMBER
                    frame = -5;
                }

                // update page table and tlb
                pageTable.add(getPage(entry), new PageTableEntry(frame, true));



            }
            int offset = getPageOffset(entry);
            printInfo(entry, frameContent[offset], frame, frameContent);
        });
    }

    // check page table and tlb for frame
    public static int searchFrame(int request) {
        int page = getPage(request);
        //int offset = getPageOffset(request);
        int frame;

        // check TLB
        frame = tlb.getFrame(page);
        if(frame != -1){
            // TLB hit
            tlb.hits++;
            return frame;
        } else {
            tlb.misses++;
            // TODO: update TLB here
        }

        // check page table
        frame = pageTable.getFrame(page);
        if(frame != -1) {
            // page table hit
            pageTable.hits++;

            // update TLB
            tlb.add(new PageFrameBlock(page, frame));

            return frame;
        }
        else {
            pageTable.misses++;
            // TODO: update page table here
        }

        // not in TLB or PT --check backend store
        return -1;
    }

    public static byte[] checkBackingStore(int request) {
        int page = getPage(request);
        return backingStore[page];
    }

    // return true if args valid
    public static boolean parseArgs(String[] args) {
        if(args.length < 1){
            System.out.println("USAGE: memSim <reference-sequence-file.txt> <FRAMES> <PRA>");
            return false;
        }
        rsFileName = args[0];
        if(args.length >= 2)
            frames = Integer.parseInt(args[1]);
        if(args.length == 3)
            pra = args[2];

        return true;
    }
    public static void readBin(String fn) throws IOException {
        File file = new File(fn);
        byte[] backing_store_bytes = Files.readAllBytes(file.toPath());
        for(int i = 0; i < 256; i++) {
            backingStore[i] = Arrays.copyOfRange(backing_store_bytes,(i * 256),((i+1) * 256)-1);
        }
    }

    public static int getPage(int num) {
        char bin = (char)num;
        int page = bin >>8;
        return page;
    }

    public static int getPageOffset(int num){
        char bin = (char)num;
        System.out.println(Integer.toBinaryString(num));
        int page = bin & 0xFF;
        System.out.println(Integer.toBinaryString(page));
        return page;
    }

    public static String convertByteToHexadecimal(byte[] byteArray)
    {
        String hex = "";

        for (byte i : byteArray) {
            hex += String.format("%02X", i);
        }

        return hex;
    }

    public static ArrayList<Integer> readInputFile(String fn) throws IOException {
        ArrayList<Integer> retList = new ArrayList<>();
        String buffer;
        BufferedReader parser = new BufferedReader(new FileReader(fn));

        while ((buffer = parser.readLine()) != null) {
            retList.add(Integer.parseInt(buffer));
        }
        parser.close();

        return retList;
    }

    public static void printArgs(){
        System.out.println("PRA: " + pra);
        System.out.println("rsFile: " + rsFileName);
        System.out.println("Frames: " + frames);
    }

    public static void printRefSeq() {refSequence.forEach(System.out::println);}
}
