package sample;

import variableLengthCodes.EliasDelta;
import variableLengthCodes.EliasGamma;
import variableLengthCodes.Fibonacci;
import variableLengthCodes.Levenstein;

import java.io.File;
import java.util.ArrayList;

public class EncodeMTF{
    private int threshold;

    public void setThreshold(int s){
        threshold = s;
    }

    public void start(String path, int par, String algo) {
        File file = new File(path);

        FileToRead.setFileName(path);
        String str = FileToRead.readString(par);

        int esamuBituKiekis = (int)file.length() * 8;
        int nuskaitomuBituKiekis = str.length()*par;
        byte uodega = (byte) (nuskaitomuBituKiekis-esamuBituKiekis);

        // Jei dirbame su fibonacci kodu, susigeneruoju fibonacci skaičius
        if(algo.equals("fib"))
            Fibonacci.generateFibs(45);

        ArrayList<Integer> output = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        // susidedu pradinę abėcėlę
        for(int i=0; i<Math.pow(2,par); i++){
            s.append((char)i);
        }

        // atlieku MTF transformaciją
        for(char c : str.toCharArray()){
            int idx = s.indexOf("" + c);
            if(idx > threshold){
                output.add(idx);
                s.deleteCharAt(idx).insert(threshold-1, c);
            }else{
                output.add(idx);
                s.deleteCharAt(idx).insert(0, c);
            }
        }

        // inicializuoju faila
        FileToWrite.setFileName("encode.mtf", true);

        // naudojamas algoritmas (2 bitai)
        switch (algo) {
            case "gamma" -> {   // 00
                FileToWrite.write(false);
                FileToWrite.write(false);
            }
            case "delta" -> {   // 01
                FileToWrite.write(false);
                FileToWrite.write(true);
            }
            case "fib" -> {     // 10
                FileToWrite.write(true);
                FileToWrite.write(false);
            }
            case "lev" -> {     // 11
                FileToWrite.write(true);
                FileToWrite.write(true);
            }
            default -> throw new IllegalArgumentException("No such VLC for Integers: " + algo);
        }

        // įrašau parametrą (1 baitas)
        FileToWrite.write((byte)par);

        // įrašau slenkstį (2 baitai)
        FileToWrite.write(threshold, 16);

        // koduojamų sveikųjų skaičių sąrašo dydis (4 bytes)
        FileToWrite.write(output.size());

        // įsirašau 1 baitą (8 bitus) uodegą
        FileToWrite.write(uodega);

        String encoded;
        // pasirenku naudojamą algoritmą sveikųjų skaičių kodavimui
        switch (algo) {
            case "gamma":
                for (Integer n : output) {
                    encoded = EliasGamma.Encode(n);
                    for (int j = 0; j < encoded.length(); j++) {
                        FileToWrite.write(encoded.charAt(j) == '1');
                    }
                }
                break;
            case "fib":
                for (Integer n : output) {
                    encoded = Fibonacci.Encode(n);
                    for (int j = 0; j < encoded.length(); j++) {
                        FileToWrite.write(encoded.charAt(j) == '1');
                    }
                }
                break;
            case "delta":
                for (Integer n : output) {
                    encoded = EliasDelta.Encode(n);
                    for (int j = 0; j < encoded.length(); j++) {
                        FileToWrite.write(encoded.charAt(j) == '1');
                    }
                }
                break;
            default:
                for (Integer n : output) {
                    encoded = Levenstein.Encode(n);
                    for (int j = 0; j < encoded.length(); j++) {
                        FileToWrite.write(encoded.charAt(j) == '1');
                    }
                }
                break;
        }

        FileToWrite.close();
    }
}
