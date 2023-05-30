package sample;

import java.util.Comparator;

public class DecodeHuffman {
    private static int par;
    private static class Node{
        private final char key;
        private final int value;
        private final Node left, right;

        Node(char key, int value, Node left, Node right) {
            this.key  = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

    }

    class MyComparator implements Comparator<Node> {
        public int compare(Node x, Node y)
        {
            return x.value - y.value;
        }
    }



    private Node readTree() {
        boolean isLeaf = FileToRead.readBoolean();
        if (isLeaf) {
            return new Node(FileToRead.readChar(par), -1, null, null);
        }
        else {
            return new Node('\0', -1, readTree(), readTree());
        }
    }


    public void start(String name) {
        // set algorithm name
        FileToWrite.setAlgoName("Huffman");

        FileToRead.setFileName("src/encoded/encode.huf");
        // perskaitau 1 baito (8 bitu) parametra
        par = FileToRead.readByte();
        //System.out.println("parametras " + par);

        // perskaitau 4 baitu (32 bitu) eilutes ilgi
        int length = FileToRead.readInt();

        // perskaitau 1 bait0 (8 bitu) uodega
        int uodega = FileToRead.readByte();

        // kiek likusio kodo man reikes surasyti
        int uodegosDalis = par - uodega;

        FileToWrite.setFileName(name, false);

        long start = System.nanoTime();


        Node root = readTree();
        for (int i = 0; i < length; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                boolean bit = FileToRead.readBoolean();
                if (bit) x = x.right;
                else     x = x.left;
            }
            //System.out.println(Integer.toBinaryString(x.key)+" ");
            if(i==length-1) {
                char temp = (char) (x.key>>uodega);
                FileToWrite.write(temp, uodegosDalis);
            }else
                FileToWrite.write(x.key, par);
        }


        FileToWrite.close();

        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        System.out.println("Visas dekodavimas (sec): " + (double)timeElapsed / 1_000_000_000.0);

        System.out.println(">>>DONE<<<");
    }
}
