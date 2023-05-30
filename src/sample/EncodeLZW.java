package sample;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class EncodeLZW {

    public String readData(String name, int bits) {
        FileToRead.setFileName(name);
        return FileToRead.readString(bits);
    }

    public void writeData(List<Integer> output_code, int k) {
        // whole list (k bits per item)
        for(int i=0; i<output_code.size(); i++)
            FileToWrite.write(output_code.get(i), k);
        FileToWrite.close();
        System.out.println(">>>Successfully encoded<<<");
    }

    public void start(String path, int parametras) {
        long start = System.nanoTime();
        int L=0, INPUT=0, bits=8;
        byte k = (byte)parametras;
        // L = lenteles dydis (L = 2^k)
        // k = pasirinktas parametras
        // INPUT = ppo viena inicializuojamu charu skaicius
        if(k == 0) {
            L = Integer.MAX_VALUE;
            k = 32;
            INPUT = 256;
            bits = 8;
        }else if(k>=5 && k<=7) {
            L = (int) Math.pow(2, k)-1;
            INPUT = (int) Math.pow(2, k);
            bits = 4;
        }else if(k>=3 && k<=4) {
            L = (int) Math.pow(2, k)-1;
            INPUT = (int) Math.pow(2, k);
            bits = 2;
        }else if(k==2) {
            L = (int) Math.pow(2, k)-1;
            INPUT = (int) Math.pow(2, k);
            bits = 1;
        }else if(k==1) {
            System.out.println("k=1 negalimas");
            System.exit(1);
        }else {
            L = (int) Math.pow(2, k)-1;
            INPUT = 256;
            bits = 8;
        }

        String s1 = readData(path, bits);

        String ch;
        Map<String, Integer> table = new HashMap<String, Integer>();

        FileToWrite.setFileName("encode.lzw", true);
        // parametras (baitas)
        FileToWrite.write(k);

        // 1 charo reiksmiu inicializavimas
        for (int i = 0; i < INPUT; i++) {
            ch = "";
            ch += (char)i;
            table.put(ch,i);
        }

        String p = "";
        int code = INPUT+1;

        List<Integer> output_code = new ArrayList<>();

        for (int i = 0; i < s1.length(); i++) {
            if(table.containsKey(p + s1.charAt(i))) {
                p = p + s1.charAt(i);
            }
            else {
                output_code.add(table.get(p));
                if(table.size() < L)
                    table.put(p + s1.charAt(i), code);
                code++;
                p = "" + s1.charAt(i);
            }
        }
        if(!p.equals(""))
            output_code.add(table.get(p));
        output_code.add(INPUT);
        //System.out.println(output_code);
        writeData(output_code, k);
        //System.out.println("dydis: "+table.size());
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        System.out.println("Uzkodavimo laikas (sec): " + (double)timeElapsed / 1_000_000_000.0);
    }
}
