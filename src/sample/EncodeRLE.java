package sample;

import variableLengthCodes.EliasDelta;
import variableLengthCodes.EliasGamma;
import variableLengthCodes.Fibonacci;
import variableLengthCodes.Levenstein;

import java.io.File;
import java.util.*;

public class EncodeRLE {

    public void start(String path, int par, String algo){
        File file = new File(path);

        FileToRead.setFileName(path);
        String str = FileToRead.readString(par);

        int esamuBituKiekis = (int)file.length() * 8;
        int nuskaitomuBituKiekis = str.length()*par;
        byte uodega = (byte) (nuskaitomuBituKiekis-esamuBituKiekis);

        // Jei dirbame su fibonacci kodu, susigeneruoju fibonacci skaičius
        if(algo.equals("fib"))
            Fibonacci.generateFibs(45);

        List<Character> output = new ArrayList<>();
        ArrayList<Integer> num = new ArrayList<>();
        int len = str.length();

        // RLE kodavimas
        for (int i = 0; i < len; i++)
        {

            // susiskaičiuoju kiek dabartinis simbolis kartų pasikartoja
            int count = 1;
            while (i < len - 1 && str.charAt(i) == str.charAt(i+1))
            {
                count++;
                i++;
            }

            // įmetu simbolį ir jo pasikartojimų kiekį į sąrašus
            output.add(str.charAt(i));
            num.add(count);
        }

        // inicializuoju failą
        FileToWrite.setFileName("encode.rlen", true);

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

        // įsirašau parametrą (1 baitas)
        FileToWrite.write((byte)par);

        // koduojamų sveikųjų skaičių sąrašo dydis (4 bytes)
        FileToWrite.write(output.size());

        // įsirašau 1 baito (8 bitus) uodegą
        FileToWrite.write(uodega);

        String encoded;
        // pasirenku naudojamą algoritmą sveikųjų skaičių kodavimui
        switch (algo) {
            case "gamma" -> {
                for (Character value : output) {
                    FileToWrite.write(value, par);
                }
                for (int n = 0; n < output.size(); n++) {
                    encoded = EliasGamma.Encode(num.get(n)-1);
                    for (int j = 0; j < encoded.length(); j++) {
                        FileToWrite.write(encoded.charAt(j) == '1');
                    }
                }
            }
            case "fib" -> {
                for (Character character : output) {
                    FileToWrite.write(character, par);
                }
                for (int n = 0; n < output.size(); n++) {
                    encoded = Fibonacci.Encode(num.get(n)-1);
                    for (int j = 0; j < encoded.length(); j++) {
                        FileToWrite.write(encoded.charAt(j) == '1');
                    }
                }
            }
            case "delta" -> {
                for (Character value : output) {
                    FileToWrite.write(value, par);
                }
                for (int n = 0; n < output.size(); n++) {
                    encoded = EliasDelta.Encode(num.get(n)-1);
                    for (int j = 0; j < encoded.length(); j++) {
                        FileToWrite.write(encoded.charAt(j) == '1');
                    }
                }
            }
            default -> {
                for (Character character : output) {
                    FileToWrite.write(character, par);
                }
                for (int n = 0; n < output.size(); n++) {
                    encoded = Levenstein.Encode(num.get(n)-1);
                    for (int j = 0; j < encoded.length(); j++) {
                        FileToWrite.write(encoded.charAt(j) == '1');
                    }
                }
            }
        }

        FileToWrite.close();
    }
}
