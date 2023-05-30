package sample;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class DecodeLZ77 {
    public void start(String name) {
        // set algorithm name
        FileToWrite.setAlgoName("LZ77");

        FileToWrite.setFileName(name, false);
        FileToRead.setFileName("src/encoded/encode.lz77");

        File file = new File("src/encoded/encode.lz77");
        int fileLen = (int) (file.length())*8;
        System.out.println(file.length());


        int m = FileToRead.readByte();
        int windowSize = (1 << m) - 1;
        int n = FileToRead.readByte();

        fileLen -= 24;
        StringBuilder buffer = new StringBuilder(windowSize);
        while (fileLen >= 8) {
            //System.out.println(fileLen);
            boolean flag = FileToRead.readBoolean();
            if (flag == Boolean.TRUE) {
                int s = FileToRead.readByte();
                buffer.append((char) s);
                FileToWrite.write((char)s);
                fileLen -= 1+8;  // boolean + byte
            } else {

                int offsetValue = FileToRead.readInt(m);
                int lengthValue = FileToRead.readInt(n);

                if(offsetValue < 0 || lengthValue < 0) break;

                int end = offsetValue + lengthValue;

                String temp = buffer.substring(offsetValue, end);

                byte[] tempBytes = temp.getBytes(StandardCharsets.ISO_8859_1);
                for (byte tempByte : tempBytes) {
                    //System.out.print((char) tempByte);
                    FileToWrite.write((char) tempByte);
                }

                buffer.append(temp);
                fileLen -= (m+n+1);
            }
            if (buffer.length() > windowSize) {
                buffer.delete(0, buffer.length() - windowSize);
            }
        }
        FileToWrite.close();
    }
}