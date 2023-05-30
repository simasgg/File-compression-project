package variableLengthCodes;

import sample.FileToRead;

import java.util.ArrayList;

public class Fibonacci{
    public static int[] fib = new int[45];

    // fibonacci skaičių generavimas
    public static void generateFibs(int count){
        int n1=0,n2=1,n3,i;
        fib[0]=0;
        fib[1]=1;
        for(i=2;i<count;++i) {
            n3=n1+n2;
            fib[i]=n3;
            n1=n2;
            n2=n3;
        }
    }

    // didžiausio fibonacci skaičiaus Fn mažesnio arba lygaus skaičiui N radimas
    private static int getIndex(int n)
    {
        int i=2;
        while(fib[i - 1] <= n)
        {
            i++;
        }
        return(i - 2);
    }

    public static String Encode(int n){
        n = n + 1;                             // iš sveikųjų neneigiamų skaičių padarom sveikuosius teigiamus
        int index = getIndex(n);
        StringBuilder codeword = new StringBuilder(index-1);
        codeword.append("0".repeat(index-1));
        while(n > 0){
            codeword.setCharAt(index-2, '1');
            n = n - f(index);
            index -= 1;

            while (index >= 0 && f(index) > n)
            {
                if(index-2 < 0)
                    break;
                codeword.setCharAt(index-2, '0');
                index -= 1;
            }
        }
        codeword.append("1");

        return codeword.toString();
    }

    // Fibonacci dekodavimas
    public static int Decode(String str){
        StringBuilder codeword = new StringBuilder();
        codeword.append(str);
        codeword.deleteCharAt(codeword.length()-1);
        int sum = 0, fib;
        for(int i=0; i<codeword.length(); i++){
            fib = Character.getNumericValue(codeword.charAt(i));
            sum += (fib*f(i+2));
        }
        return sum;
    }


    //Fibonacci seka naudojant rekursiją
    private static int f(int n)
    {
        if (n <= 1)
            return n;
        return f(n-1) + f(n-2);
    }

    // Fibonacci dekodavimas vyksta kiek kitaip, nei Elias ar Levenšteino kodų.
    // Tiesiog skaitome bitus tol, kol pasiekiami 2 iš eilės einantys vienetai
    // Ir butent tai indikuos, kad gavome pirmąjį dekoduotą kodo žodį.
    public static ArrayList<Integer> convertToIntFib(int size){
        ArrayList<Integer> num = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            StringBuilder bin = new StringBuilder();
            while(true){
                bin.append(FileToRead.readBoolean() ? 1 : 0);
                if(bin.length()>1 && bin.charAt(bin.length()-1) == '1' && bin.charAt(bin.length()-2) == '1'){
                    break;
                }
            }
            num.add(Fibonacci.Decode(bin.toString())-1);
        }
        return num;
    }
}
