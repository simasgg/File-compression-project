package sample;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecodeLZW {

    public void start(String name, String path) {
        // set algorithm name
        FileToWrite.setAlgoName("LZW");

        File file = new File(path);
        long start = System.nanoTime();
        FileToWrite.setFileName(name, false);
        FileToRead.setFileName("src/encoded/encode.lzw");
        int L=0, INPUT=0, bits=8;
        // parametras (baitas)
        int k = FileToRead.readByte();
        // k=0 zodynas neribotas, skaicius 4 baitu dydzio
        if(k == 0) {
            L = Integer.MAX_VALUE;
            k = 32;
            INPUT = 256;
            bits = 8;
        }else if(k>=5 && k<=7) {
            L = (int) Math.pow(2, k);
            INPUT = (int) Math.pow(2, k)-1;
            bits = 4;
        }else if(k>=3 && k<=4) {
            L = (int) Math.pow(2, k);
            INPUT = (int) Math.pow(2, k)-1;
            bits = 2;
        }else if(k==2) {
            L = (int) Math.pow(2, k);
            INPUT = (int) Math.pow(2, k)-1;
            bits = 1;
        }else if(k==1) {
            System.out.println("k=1 negalimas");
            System.exit(1);
        }else {
            L = (int) Math.pow(2, k);
            INPUT = 256;
            bits = 8;
        }

        // failo dydis
        int failoDydis = (int)file.length() * 8;

        Map<Integer, String> st = new HashMap<Integer, String>();
        int i;
        // initialize symbol table with all 1-character strings
        for (i = 0; i < INPUT; i++)
            st.put(i, "" + (char) i);
        st.put(i++, "");                       // EOF

        int codeword = FileToRead.readInt(k);
        if (codeword == INPUT) return;
        String val = st.get(codeword);
        int sum=0;
        while (true) {
            sum=sum+bits;						// einamu irasytu i faila bitu kiekis
            FileToWrite.write(val, bits);		// rasymas i faila
            if (sum == failoDydis) 				// rasome tol kol pasiekiame failo dydi (uodegos ignoravimas)
                break;
            codeword = FileToRead.readInt(k);	// archyvo elementu skaitymas
            if (codeword == INPUT)
                break;
            String s = st.get(codeword);
            if (i == codeword)
                s = val + val.charAt(0);
            if (i < L)
                st.put(i++, val + s.charAt(0));
            val = s;
        }
        FileToWrite.close();
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        System.out.println(">>>DONE<<<");
        System.out.println("Dekodavimo laikas (sec): " + (double)timeElapsed / 1_000_000_000.0);
    }
}
