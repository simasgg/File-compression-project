package variableLengthCodes;

import sample.FileToRead;

import java.util.ArrayList;

public class Levenstein{

    // Levenstein kodavimas
    public static String Encode(int n){
        StringBuilder sb = new StringBuilder();
        if(n == 0)
            return "0";
        int c = 1;
        while(true) {
            String bin = Integer.toBinaryString(n).substring(1);
            sb.insert(0, bin);
            int m = bin.length();
            if (m != 0) {
                c++;
                n = m;
            }else{
                sb.insert(0, "0");
                for(int i=0; i<c; i++)
                    sb.insert(0, "1");
                break;
            }
        }
        return sb.toString();
    }

    // Levenstein dekodavimas
    public static ArrayList<Integer> Decode(int size){
        ArrayList<Integer> num = new ArrayList<>();
        int decoded;
        for(int k=0; k<size; k++) {
            decoded = FileToRead.readBoolean() ? 1 : 0;
            int c = 0;
            while(decoded == 1) {
                c++;
                decoded = FileToRead.readBoolean() ? 1 : 0;
            }
            if(c == 0) {
                num.add(0);
                continue;
            }
            int n = 1;
            int offset = c+1;
            for(int i=0; i<c-1; i++){
                StringBuilder bin = new StringBuilder();
                for(int j=0; j<n; j++) {
                    int r = FileToRead.readBoolean() ? 1 : 0;
                    bin.append(r);
                }
                bin.insert(0, "1");
                int newValue  = Integer.parseInt(bin.toString(),2);
                offset = offset + n;
                n = newValue;

            }
            num.add(n);
        }
        return num;
    }
}
