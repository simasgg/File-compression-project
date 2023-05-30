package sample;

import variableLengthCodes.EliasDelta;
import variableLengthCodes.EliasGamma;
import variableLengthCodes.Fibonacci;
import variableLengthCodes.Levenstein;

import java.io.File;
import java.util.*;

public class DecodeRLE {

    public void start(String path){
        String algo;
        byte tail;
        FileToWrite.setAlgoName("RLE");

        FileToWrite.setFileName(path, false);

        FileToRead.setFileName("src/encoded/encode.rlen");

        // pažiūriu koks algoritmas buvo naudotas
        if(FileToRead.readBoolean())
            if(FileToRead.readBoolean())
                algo = "lev";
            else
                algo = "fib";
        else
        if(FileToRead.readBoolean())
            algo = "delta";
        else
            algo = "gamma";

        // perskaitau 1 baito (8 bitu) parametrą
        byte par = FileToRead.readByte();

        // perskaitau užkoduotų sveikųjų skačių sąrašo dydį (4 baitai)
        int stringSize = FileToRead.readInt();

        // persiskaitau uodegos dydį
        tail = FileToRead.readByte();

        // kiek likusio kodo man reikės surašyti
        int uodegosDalis = par - tail;

        StringBuilder decoded = new StringBuilder();
        ArrayList<Integer> num;
        ArrayList<Character> c;

        // prijungiu užkoduotus sveikuosius skaičius prie abėcėlės sąrašo
        switch (algo) {
            case "gamma" -> {
                c = new ArrayList<>();
                for (int i = 0; i < stringSize; i++) {
                    c.add(FileToRead.readChar(par));
                }
                num = new ArrayList<>(EliasGamma.Decode(stringSize));
                for (int i = 0; i < stringSize; i++) {
                    int number = num.get(i)+1;
                    char[] repeat = new char[number];
                    Arrays.fill(repeat, c.get(i));
                    decoded.append(new String(repeat));
                }
            }
            case "fib" -> {
                c = new ArrayList<>();
                for (int i = 0; i < stringSize; i++) {
                    c.add(FileToRead.readChar(par));
                }
                num = new ArrayList<>(Fibonacci.convertToIntFib(stringSize));
                for (int i = 0; i < stringSize; i++) {
                    int number = num.get(i)+1;
                    char[] repeat = new char[number];
                    Arrays.fill(repeat, c.get(i));
                    decoded.append(new String(repeat));
                }
            }
            case "delta" -> {
                c = new ArrayList<>();
                for (int i = 0; i < stringSize; i++) {
                    c.add(FileToRead.readChar(par));
                }
                num = new ArrayList<>(EliasDelta.Decode(stringSize));
                for (int i = 0; i < stringSize; i++) {
                    int number = num.get(i)+1;
                    char[] repeat = new char[number];
                    Arrays.fill(repeat, c.get(i));
                    decoded.append(new String(repeat));
                }
            }
            default -> {
                c = new ArrayList<>();
                for (int i = 0; i < stringSize; i++) {
                    c.add(FileToRead.readChar(par));
                }
                num = new ArrayList<>(Levenstein.Decode(stringSize));
                for (int i = 0; i < stringSize; i++) {
                    int number = num.get(i)+1;
                    char[] repeat = new char[number];
                    Arrays.fill(repeat, c.get(i));
                    decoded.append(new String(repeat));
                }
            }
        }

        // dekoduoju kiekvieną simbolį
        for(int i=0; i<decoded.length(); i++){
            char ch = decoded.charAt(i);
            if(i==decoded.length()-1) {
                char temp = (char) (ch>>tail);
                FileToWrite.write(temp, uodegosDalis);
            }else
                FileToWrite.write(ch, par);
        }

        FileToWrite.close();
    }
}
