package variableLengthCodes;

import sample.FileToRead;

import java.util.ArrayList;

public class EliasGamma {

    // Elias gamma kodavimas
    public static String Encode(int n){
        n = n + 1;                             // sveikasis neneigiamas skaičius į sveikąjį teigiamą
        int N = log(n);                        // 1 žingsnis: Apskaičiuojame N
        int len = 1 + log(n);                  // kiekis nulių, kurių mums prireiks unarinio kodo skaičiavimui
        int L = (int) (n - Math.pow(2, N));    // L išvedimas pagal formulę n=2^N+L
        return Unary(len) + Binary(L, N);      // 2 + 3 žingsniai: Sudedame unarinį kodą ir L binarinę išraišką
    }

    // Elias gamma dekodavimas
    public static ArrayList<Integer> Decode(int size){
        ArrayList<Integer> num = new ArrayList<>();
        for(int i=0; i<size; i++) {
            StringBuilder bin = new StringBuilder();
            int decoded = FileToRead.readBoolean() ? 1 : 0;
            if (decoded == 1) {
                num.add(0);
                continue;
            }
            int N = 0;
            while (true) {
                if (decoded == 1) {
                    break;
                } else
                    N += 1;
                decoded = FileToRead.readBoolean() ? 1 : 0;
            }
            for(int j=0; j<N; j++) {
                decoded = FileToRead.readBoolean() ? 1 : 0;
                bin.append(decoded);
            }
            int L = Integer.parseInt(bin.toString(), 2);
            num.add((int) (Math.pow(2,N)+L)-1);
        }
        return num;
    }

    // logaritmas pagrindu 2
    private static int log(int number){
        return (int)(Math.log(number) / Math.log(2));
    }

    // Konstruojame unarinį kodą su len-1 nulių ir gale pridedame vienetą
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
