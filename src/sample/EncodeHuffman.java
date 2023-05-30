package sample;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;


public class EncodeHuffman{
    private static byte par;
    static int total=0;

    class Node{
        char key;
        int value;
        Node left, right;

    }

    class MyComparator implements Comparator<Node> {
        public int compare(Node x, Node y)
        {
            return x.value - y.value;
        }
    }


    public static void myTree(Node root, String s, String[] st)
    {
        //System.out.println(root.left+" "+root.right);
        if (root.left == null && root.right == null) {
            //System.out.println(root.key + ":" + s);
            // binarinis stringas igauna indexa su kokiu nors char, pvz 000 indeksas yra 'a'
            st[root.key]=s;
            FileToWrite.write(true);
            FileToWrite.write(root.key, par);
            return;
        }
        FileToWrite.write(false);
        myTree(root.left, s + "0", st);
        myTree(root.right, s + "1", st);
    }

    public void start(String path, byte parametras) {
        par = parametras;
        //int par_2 = (int) Math.pow(2, parametras);
        //int par_2 = (Math.pow(2, parametras)==65536) ? (int)Math.pow(2, parametras)-1 :  (int)Math.pow(2, parametras);
        //int par_3 = (Math.pow(2, parametras)==65536) ? 65536 :  (int)Math.pow(2, parametras);
        int par_2 = (int)Math.pow(2, par);
        String[] st = new String[par_2];
        int[] count = new int[par_2];
        int sum=0;
        String answer="";
        String str="";

        long start = System.nanoTime();
        //File file = new File("C:\\Users\\User\\Desktop\\program\\JAVA\\eclipse\\Hafmanas\\src\\"+path);
        File file = new File(path);

        // skaitau is sito failo
        FileToRead.setFileName(path);
        str = FileToRead.readString(par);
        int esamuBituKiekis = (int)file.length() * 8;
        System.out.println("failo dydis bitais: "+esamuBituKiekis);
        int nuskaitomuBituKiekis = str.length()*par;
        System.out.println("kiek mes nuskaitome bitu su musu parametru: "+nuskaitomuBituKiekis);
        byte uodega = (byte) (nuskaitomuBituKiekis-esamuBituKiekis);
        System.out.println("uodega bitais: "+uodega);

        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        System.out.println("Failo duomenu nuskaitymo laikas (sec): " + (double)timeElapsed / 1_000_000_000.0);
        PriorityQueue<Node> q = new PriorityQueue<Node>(new MyComparator());

        start = System.nanoTime();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            count[str.charAt(i)]++;
        }
        //System.out.println(count.length);

        // randame visus egzistuojancius charus ir susidedame inicializuotus Node i Priority queue
        for (char i = 0; i < par_2; i++) {
            if(count[i]>0) {
                //System.out.println(count[i]);
                //System.out.println(i + " " + count[str.charAt(i)]);
                Node tree = new Node();
                tree.key=i;
                tree.value=count[i];
                sum = sum + count[i];
                tree.left = null;
                tree.right = null;
                q.add(tree);
            }
            if(i==65535)
                break;
        }

        Node root = null;
        while(q.size()>1) {
            Node x = q.peek();
            q.poll();

            Node y = q.peek();
            q.poll();

            Node my_tree = new Node();
            int n = x.value + y.value;
            my_tree.key = '\0';
            my_tree.value = n;
            my_tree.left=x;
            my_tree.right=y;

            root = my_tree;

            q.add(my_tree);
        }


        finish = System.nanoTime();
        timeElapsed = finish - start;
        System.out.println("Char dazniu radimas ir addinimas i PriorityQueue (sec): " + (double)timeElapsed / 1_000_000_000.0);

        start = System.nanoTime();

        FileToWrite.setFileName("encode.huf", true);
        // isirasau 1 baita (8 bitus) parametra
        FileToWrite.write(par);
        // isirasau 4 baitu (32 bitu) kodo ilgi
        FileToWrite.write(str.length());
        // isirasau 1 baita (8 bitus) uodega
        FileToWrite.write(uodega);

        myTree(root, "", st);

        // str = nuskaitytos raides su parametru
        // len = str.length, nuskaitytu raidziu kiekis
        // st = kodo zodis su indeksu char (st[root.key])
        for(int j=0; j<len; j++) {
            answer = st[str.charAt(j)];
            for (int i = 0; i < answer.length(); i++) {
                if (answer.charAt(i) == '0') {
                    FileToWrite.write(false);
                }
                else if (answer.charAt(i) == '1') {
                    FileToWrite.write(true);
                }
            }
        }
        FileToWrite.close();

        finish = System.nanoTime();
        timeElapsed = finish - start;
        System.out.println("Duomenu surasymas i binarini faila (sec): " + (double)timeElapsed / 1_000_000_000.0);

        System.out.println(">>>Successfully encoded<<<");
    }
}
