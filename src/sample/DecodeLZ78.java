package sample;

import java.util.ArrayList;
import java.util.HashMap;

public class DecodeLZ78 {
    ArrayList<String> dictionary;
    //HashMap<Integer, Character> code_word;
    String[] codeWord;
    int par, bits;

    public void start(String name){
        // set algorithm name
        FileToWrite.setAlgoName("LZ78");

        FileToWrite.setFileName(name, false);
        FileToRead.setFileName("src/encoded/encode.lz78");
        //code_word = new HashMap<>();
        //String[] codeWord = "0:b 0:a 1:c 1:a 4:b 2:a".split(" ");
        bits = 8;

        // parametras
        par = FileToRead.readByte();

        // dealinu su parametru
        if(par == 0) {
            par = 32;
            bits = 8;
        }else if(par>=5 && par<=7) {
            bits = 4;
        }else if(par>=3 && par<=4) {
            bits = 2;
        }else if(par==2) {
            bits = 1;
        }else if(par==1) {
            System.out.println("k=1 negalimas");
            System.exit(1);
        }else {
            bits = 8;
        }

        readData(name);

        dictionary = new ArrayList<String>();
        String first, last;

        for(String d : codeWord){

            first = d.substring(0, d.length()-2);
            last = d.substring(d.length()-1);

            if(Integer.parseInt(first) == 0){
                dictionary.add(last);
            }else{
                int index = Integer.parseInt(first);
                //System.out.println(dictionary);
                dictionary.add(dictionary.get(index-1)+last);
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<dictionary.size(); i++){
            sb.append(dictionary.get(i));
        }

        // fix the problem if last element is empty char
        char lastEl = sb.charAt(sb.length()-1);
        //System.out.println(">"+lastEl);
        if(lastEl==' '){
            //System.out.println("test");
            String myString = sb.toString().substring(0, sb.length()-1);
            FileToWrite.write(myString, bits);
        }else{
            FileToWrite.write(sb.toString(), bits);
        }


        //FileToWrite.write(sb.toString());
        //System.out.println(">"+sb);

        FileToWrite.close();

        System.out.println(">>>DONE<<<");
    }

    public void readData(String name){

        // read size of code words and create string which will hold it's values
        int k = FileToRead.readInt();
        codeWord = new String[k];
        //System.out.println(k);

        //System.out.println("this");
        // recreate code words
        int temp=1;
        for(int i=0; i<k; i++){
            double bitsForInt = Math.pow(2, temp);
            if(bitsForInt-1 < i)
                temp = temp + 1;
            int iii = FileToRead.readInt(temp);
            char c = FileToRead.readChar(bits);
            codeWord[i] = iii+":"+c;
            //code_word.put(iii, c);
            //System.out.print(codeWord[i]+", ");
        }
        //System.out.println();
    }
}