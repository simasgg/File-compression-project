package variableLengthCodes;

import sample.FileToRead;

import java.util.ArrayList;

import static java.lang.Math.floor;

public class EliasDelta{

    // Elias Delta kodavimas
    public static String Encode(int n){
        n = n + 1;                                                  // sveikasis neneigiamas į sveikąjį teigiamą
        String gamma = EncodeGamma((int)(1 + floor(log(n))));
        return gamma + Integer.toBinaryString(n).substring(1);      // Dvejetaine reprezentacija
    }                                                               // be reiksmingiausio bito

    // Elias gamma kodavimas
    private static String EncodeGamma(int n){
        int N = log(n);                        // 1 žingsnis: Apskaičiuojame N
        int len = 1 + log(n);                  // kiekis nulių, kurių mums prireiks unarinio kodo skaičiavimui
        int L = (int) (n - Math.pow(2, N));    // L išvedimas pagal formulę n=2^N+L
        return Unary(len) + Binary(L, N);      // 2 + 3 žingsniai: Sudedame unarinį kodą ir L binarinę išraišką
    }

    // Elias delta dekodavimas
    public static ArrayList<Integer> Decode(int size){
        ArrayList<Integer> num = new ArrayList<>();
        int decoded;
        for(int j=0; j<size; j++) {
            StringBuilder bin = new StringBuilder();
            decoded = FileToRead.readBoolean() ? 1 : 0;
            bin.append(decoded);
            int L = 0;
            while (decoded == 0) {
                L++;
                decoded = FileToRead.readBoolean() ? 1 : 0;
                bin.append(decoded);
            }
            for(int i=0; i<L; i++){
                decoded = FileToRead.readBoolean() ? 1 : 0;
                bin.append(decoded);
            }
            int len = Integer.parseInt(bin.toString(), 2);
            bin = new StringBuilder();
            bin.append(1);
            for(int i=0; i<len-1; i++){
                decoded = FileToRead.readBoolean() ? 1 : 0;
                bin.append(decoded);
            }
            num.add(Integer.parseInt(bin.toString(), 2)-1);
        }
        return num;
    }

    // logaritmas pagrindu 2
    private static int log(int number){
        return (int)(Math.log(number) / Math.log(2));
    }

    // Konstruojame unarinį kodą su len-1 nuliu ir gale pridedame vienetą
    private static StringBuilder Unary(int len){
        StringBuilder s = new StringBuilder();
        s.append("0".repeat(len - 1));
        return s.append("1");
    }

    // konvertuojame L į N ilgio binarinę formą su priekyje einančiais nuliais
    private static String Binary(int L, int N){
        if(N == 0)
            return "";
        return String.format("%"+N+"s", Integer.toBinaryString(L)).replace(' ', '0');
    }
}
