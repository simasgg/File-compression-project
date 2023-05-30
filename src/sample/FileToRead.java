package sample;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

public class FileToRead {

    private static final int EOF = -1;
    private static BufferedInputStream in;
    private static int buffer;
    private static String path;
    private static int n;
    private static boolean isInitialized;

    private static void initialize() {
        try {
            String filepath = path;
            InputStream fin = new FileInputStream(filepath);
            in = new BufferedInputStream(fin);
            buffer = 0;
            n = 0;
            fillBuffer();
            isInitialized = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void setFileName(String x) {
        path = x;
        initialize();
    }


    private static void fillBuffer() {
        try {
            buffer = in.read();
            n = 8;
        }
        catch (IOException e) {
            System.out.println("EOF");
            buffer = EOF;
            n = -1;
        }
    }

    public static String readString(int k) {
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");

        StringBuilder sb = new StringBuilder();
        while (!isEmpty()) {
            char c = readChar(k);
            //System.out.println(Integer.toBinaryString(c)+" ");
            sb.append(c);
        }
        isInitialized = false;
        //System.out.println();
        return sb.toString();
    }

    public static boolean isEmpty() {
        if (!isInitialized) initialize();
        return buffer == EOF;
    }


    public static boolean readBoolean() {
        isEmpty();
        n--;
        boolean bit = ((buffer >> n) & 1) == 1;
        if (n == 0) fillBuffer();
        return bit;
    }

    public static char readChar() {
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");

        if (n == 8) {
            int x = buffer;
            fillBuffer();
            return (char) (x & 0xff);
        }

        int x = buffer;
        x <<= (8 - n);
        int oldN = n;
        fillBuffer();
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");
        n = oldN;
        x |= (buffer >>> n);
        return (char) (x & 0xff);
    }

    public static char readChar(int r) {
        if (r == 8) return readChar();
        char x = 0;
        for (int i = 0; i < r; i++) {
            x <<= 1;
            boolean bit = readBoolean();
            if (bit) x |= 1;
        }
        return x;
    }

    public static byte readByte() {
        char c = readChar();
        return (byte) (c & 0xff);
    }

    public static int readInt() {
        int x = 0;
        for (int i = 0; i < 4; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

    public static int readInt(int r) {
        if (r < 1 || r > 32) throw new IllegalArgumentException("Illegal value of r = " + r);

        // optimize r = 32 case
        if (r == 32) return readInt();

        int x = 0;
        for (int i = 0; i < r; i++) {
            x <<= 1;
            boolean bit = readBoolean();
            if (bit) x |= 1;
        }
        return x;
    }

    public static short readShort() {
        short x = 0;
        for (int i = 0; i < 2; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }
}

