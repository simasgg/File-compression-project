package sample;

import variableLengthCodes.EliasDelta;
import variableLengthCodes.EliasGamma;
import variableLengthCodes.Fibonacci;
import variableLengthCodes.Levenstein;

import java.util.ArrayList;

public class DecodeInterval {

    public void start(String name) {
        ArrayList<Integer> alphabet = new ArrayList<> ();
        String algo="";
        byte tail;
        FileToWrite.setAlgoName("Interval");

        FileToWrite.setFileName(name, false);

        FileToRead.setFileName("src/encoded/encode.int");

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

        // perskaitau užkoduotų sveikųjų skačių sąrašo dydį (4 bytes)
        int algoSize = FileToRead.readInt();

        // persiskaitau uodegos dydį
        tail = FileToRead.readByte();

        // kiek likusio kodo man reikės surašyti
        int uodegosDalis = par - tail;

        // atkuriu abėcėlę pagal parametrą
        for(int i = 0; i<Math.pow(2, par); i++){
            alphabet.add(i);
        }

        // prijungiu užkoduotus sveikuosius skaičius prie abėcėlės sąrašo
        switch (algo) {
            case "gamma" -> alphabet.addAll(EliasGamma.Decode(algoSize));
            case "fib" -> alphabet.addAll(Fibonacci.convertToIntFib(algoSize));
            case "delta" -> alphabet.addAll(EliasDelta.Decode(algoSize));
            default -> alphabet.addAll(Levenstein.Decode(algoSize));
        }

        // dekoduoju
        for(int i = (int)Math.pow(2, par); i<alphabet.size(); i++){
            int dis = alphabet.get(i);
            int decodedChar = alphabet.get(i-dis-1);
            alphabet.set(i, decodedChar);
            if(i==alphabet.size()-1) {
                char temp = (char) ((char)decodedChar>>tail);
                FileToWrite.write(temp, uodegosDalis);
            }else
                FileToWrite.write((char)decodedChar, par);
        }

        FileToWrite.close();
    }
}

