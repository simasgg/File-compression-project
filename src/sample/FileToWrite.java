package sample;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileToWrite{
    private static BufferedOutputStream out;
    private static int buffer;
    private static int n;
    private static boolean isInitialized;
    private static String name;
    private static boolean temp;
    private static String algorithm;

    private static void initialize() {


        try {
            String filename;
            if(temp == true)
                filename = "src/encoded/"+name;
            else
                filename = "src/decoded/"+name;
            OutputStream os = new FileOutputStream(filename);
            out = new BufferedOutputStream(os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        buffer = 0;
        n = 0;
        isInitialized = true;
    }

    public static void setAlgoName(String str){
        algorithm = str;
    }

    // nameOrExtension (true=name, false=extension)
    public static void setFileName(String x, boolean nameOrExtension) {
        temp = nameOrExtension;
        // pvz jei gauname faila tekstas.txt, tai false atveju dirbame su decode.txt
        if(temp == false) {
            String extension = "";

            int i = x.lastIndexOf('.');
            int p = Math.max(x.lastIndexOf('/'), x.lastIndexOf('\\'));

            if (i > p) {
                extension = x.substring(i+1);
            }
            if(algorithm.equals("Huffman"))
                name="decodeHuffman."+extension;
            else if(algorithm.equals("LZ77"))
                name="decodeLZ77."+extension;
            else if(algorithm.equals("LZ78"))
                name="decodeLZ78."+extension;
            else if(algorithm.equals("LZW"))
                name="decodeLZW."+extension;
            else if(algorithm.equals("MTF"))
                name="decodeMTF."+extension;
            else if(algorithm.equals("Interval"))
                name="decodeInterval."+extension;
            else if(algorithm.equals("RLE"))
                name="decodeRLE."+extension;
        }else
            name=x;
        initialize();
    }


    public static void write(boolean x) {
        writeBit(x);
    }

    public static void write(String s, int k) {
        for (int i = 0; i < s.length(); i++)
            write(s.charAt(i), k);
    }


    public static void write(char ch, int r) {
        if (!isInitialized) initialize();
        if (r == 8) {
            write(ch);
            return;
        }
        if (r < 1 || r > 16) throw new IllegalArgumentException("Illegal value for r = " + r);
        //if (ch >= (1 << r))   throw new IllegalArgumentException("Illegal " + r + "-bit char = " + ch);
        for (int i = 0; i < r; i++) {
            boolean bit = ((ch >>> (r - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    public static void write(char x) {
        writeByte((int)x);
    }

    private static void writeByte(int x) {
        if (n == 0) {
            try {
                out.write(x);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }


        for (int i = 0; i < 8; i++) {
            boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    private static void writeBit(boolean bit) {
        if (!isInitialized) initialize();

        buffer <<= 1;
        if (bit) buffer |= 1;

        n++;
        if (n == 8) clearBuffer();
    }

    // 4 baitai
    public static void write(int x) {
        writeByte((x >>> 24) & 0xff);
        writeByte((x >>> 16) & 0xff);
        writeByte((x >>>  8) & 0xff);
        writeByte((x >>>  0) & 0xff);
    }

    // 2 baitai
    public static void write(short x) {
        writeByte((x >>>  8) & 0xff);
        writeByte((x >>>  0) & 0xff);
    }

    // 1 baitas
    public static void write(byte x) {
        writeByte(x & 0xff);
    }

    public static void write(int x, int r) {
        if (r == 32) {
            write(x);
            return;
        }
        if (r < 1 || r > 32)        throw new IllegalArgumentException("Illegal value for r = " + r);
        //if (x < 0 || x >= (1 << r)) throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
        for (int i = 0; i < r; i++) {
            boolean bit = ((x >>> (r - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    public static void write(String s) {
        for (int i = 0; i < s.length(); i++)
            write(s.charAt(i));
    }


    private static void clearBuffer() {
        if (!isInitialized) initialize();

        if (n == 0) return;
        if (n > 0) buffer <<= (8 - n);

        try {
            out.write(buffer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        n = 0;
        buffer = 0;
    }

    public static void close() {
        flush();
        try {
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        isInitialized = false;
    }

    public static void flush() {
        clearBuffer();
        try {
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
