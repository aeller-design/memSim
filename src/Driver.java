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
    static TLB tlb = new TLB();
    static byte[][] backingStore = new byte[256][256];
    // push to add, removeLast to remove oldest --16 entries

    static String pra = "FIFO";
    static String rsFileName;
    static int frames = 256;
    static ArrayList<Integer> refSequence;
    static byte[][] physicalMemory;


    public static void main(String[] args) throws IOException {
        // check for valid arguments
        if(!parseArgs(args))
            return;

        // read backing store file
        readBin("BACKING_STORE.bin");
        // read reference sequence file
        refSequence = readInputFile(rsFileName);
        // initialize physical memory size to frames input
        physicalMemory = new byte[frames][256];

        /*refSequence.forEach(e->{
            System.out.println("page: " + getPage(e));
            System.out.println("offset: " + getPageOffset(e));
        });
        int page = getPage(53683);
        int off = getPageOffset(53683);

        System.out.println("backing store: " + backingStore[page][off]);
        */
        refSequence.forEach(entry -> {
            int frame = searchFrame(entry);
            if(frame != -1) {
                // frame found, retrieve from memory

            } else {
                // check backend store

            }
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
            // TODO: update frame here
        }

        // check page table
        frame = pageTable.getFrame(page);
        if(frame != -1) {
            // page table hit
            pageTable.hits++;
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

    public static void convertByteToHexadecimal(byte[] byteArray)
    {
        String hex = "";

        for (byte i : byteArray) {
            hex += String.format("%02X", i);
        }

        System.out.print(hex);
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
