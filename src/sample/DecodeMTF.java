package sample;

import variableLengthCodes.EliasDelta;
import variableLengthCodes.EliasGamma;
import variableLengthCodes.Fibonacci;
import variableLengthCodes.Levenstein;

import java.util.ArrayList;

public class DecodeMTF {
    public void start(String path) {
        ArrayList<Integer> decoded = new ArrayList<>();
        String algo;
        byte tail;
        FileToWrite.setAlgoName("MTF");

        FileToWrite.setFileName(path, false);

        FileToRead.setFileName("src/encoded/encode.mtf");

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

        // perskaitau 1 baito (8 bitu) slenkstį
        int slenkstis = FileToRead.readInt(16);

        // perskaitau užkoduotų sveikųjų skačių sąrašo dydį (4 baitai)
        int algoSize = FileToRead.readInt();

        // persiskaitau uodegos dydį
        tail = FileToRead.readByte();

        // kiek likusio kodo man reikės surašyti
        int uodegosDalis = par - tail;

        // prijungiu užkoduotus sveikuosius skaičius prie abėcėlės sąrašo
        switch (algo) {
            case "gamma" -> decoded.addAll(EliasGamma.Decode(algoSize));
            case "fib" -> decoded.addAll(Fibonacci.convertToIntFib(algoSize));
            case "delta" -> decoded.addAll(EliasDelta.Decode(algoSize));
            default -> decoded.addAll(Levenstein.Decode(algoSize));
        }

        StringBuilder s = new StringBuilder();

        // atkuriu pradinę abėcėlę
        for(int i = 0; i<Math.pow(2, par); i++){
            s.append((char)i);
        }


        // atlieku MTF dekodavima
        for(int idx : decoded){
            char c = s.charAt(idx);
            if(idx==decoded.size()-1) {
                char temp = (char) (c>>tail);
                FileToWrite.write(temp, uodegosDalis);
            }else
                FileToWrite.write(c, par);

            if(idx > slenkstis) {
                s.deleteCharAt(idx).insert(slenkstis-1, c);
            }else
                s.deleteCharAt(idx).insert(0, c);
        }
        FileToWrite.close();
    }
}
