package sample;

import variableLengthCodes.EliasDelta;
import variableLengthCodes.EliasGamma;
import variableLengthCodes.Fibonacci;
import variableLengthCodes.Levenstein;

import java.io.File;
import java.util.ArrayList;

public class EncodeInterval {

    public void start(String path, int par, String algo) {
        String str;
        ArrayList<Character> alphabet = new ArrayList<> ();
        ArrayList<Integer> distance = new ArrayList<> ();
        File file = new File(path);

        // Jei dirbame su fibonacci kodu, susigeneruoju fibonacci skaičius
        if(algo.equals("fib"))
            Fibonacci.generateFibs(45);

        // skaitau iš failo
        FileToRead.setFileName(path);
        str = FileToRead.readString(par);

        int esamuBituKiekis = (int)file.length() * 8;
        int nuskaitomuBituKiekis = str.length()*par;
        byte uodega = (byte) (nuskaitomuBituKiekis-esamuBituKiekis);

        // susidedu abėcėlę i arraylistą
        for(int i=0; i<Math.pow(2,par); i++){
            alphabet.add((char)i);
        }

        // susidedu koduojamą žodį i arraylistą
        for(int i=0; i<str.length(); i++){
            alphabet.add(str.charAt(i));
        }

        // atlieku invervalinį kodavimą
        for(int i=(int)Math.pow(2,par); i<alphabet.size(); i++){
            for(int j=i-1; j>=0; j--){
                if(alphabet.get(i).equals(alphabet.get(j))) {
                    distance.add(i - j - 1);
                    break;
                }
            }
        }

        // inicializuoju failą
        FileToWrite.setFileName("encode.int", true);

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

        // irašau parametrą (1 baitas)
        FileToWrite.write((byte)par);

        // koduojamų sveikųjų skaičių sąrašo dydis (4 baitai)
        FileToWrite.write(distance.size());

        // isirašau 1 baito (8 bitus) uodegą
        FileToWrite.write(uodega);

        String encoded;
        // pasirenku naudojamą algoritmą sveikųjų skaičių kodavimui
        switch (algo) {
            case "gamma":
                for (Integer n : distance) {
                    encoded = EliasGamma.Encode(n);
                    for (int j = 0; j < encoded.length(); j++) {
                        FileToWrite.write(encoded.charAt(j) == '1');
                    }
                }
                break;
            case "fib":
                for (Integer n : distance) {
                    encoded = Fibonacci.Encode(n);
                    for (int j = 0; j < encoded.length(); j++) {
                        FileToWrite.write(encoded.charAt(j) == '1');
                    }
                }
                break;
            case "delta":
                for (Integer n : distance) {
                    encoded = EliasDelta.Encode(n);
                    for (int j = 0; j < encoded.length(); j++) {
                        FileToWrite.write(encoded.charAt(j) == '1');
                    }
                }
                break;
            default:
                for (Integer n : distance) {
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
