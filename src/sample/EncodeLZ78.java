package sample;

import java.util.*;

class EncodeLZ78 {

    static class InnerTrie {

        private final InnerTrieNode root;

        public InnerTrie() {

            root = new InnerTrieNode();
            root.wordEnd = false;
        }

        public void insert(String word) {

            InnerTrieNode node = root;
            for (int i = 0; i < word.length(); i++) {

                Character c = word.charAt(i);
                if (!node.children.containsKey(c)) {

                    node.children.put(c, new InnerTrieNode());
                }
                node = node.children.get(c);
            }
            node.wordEnd = true;
        }

        public boolean search(String word) {

            InnerTrieNode node = root;
            for (int i = 0; i < word.length(); i++) {

                Character c = word.charAt(i);
                if (!node.children.containsKey(c)) {

                    return false;
                }
                node = node.children.get(c);
            }
            return node.wordEnd;
        }

    }


    static class InnerTrieNode {

        Map<Character, InnerTrieNode> children;
        boolean wordEnd;
        public InnerTrieNode() {

            children = new HashMap<>();
            wordEnd = false;
        }
    }

    // klase kodavimui
    static class InnerOutput {

        private final Integer index;
        private final Character character;
        InnerOutput(Integer index, Character character) {

            this.index = index;
            this.character = character;
        }
        public Integer getIndex() {

            return index;
        }
        public Character getCharacter() {

            return character;
        }
    }

    InnerTrie buildTree(Set<String> keys) {

        InnerTrie trie = new InnerTrie();
        keys.forEach(trie::insert);
        return trie;
    }

    public String readData(String p, int bits) {
        FileToRead.setFileName(p);
        return FileToRead.readString(bits);
    }

    public void start(String path, int parametras){

        int L=0, bits=8;
        byte k = (byte)parametras;

        // dealinu su parametru k == 0 == 32
        if(k == 0) {
            L = Integer.MAX_VALUE;
            k = 32;
            bits = 8;
        }else if(k>=5 && k<=7) {
            L = (int) Math.pow(2, k)-1;
            bits = 4;
        }else if(k>=3 && k<=4) {
            L = (int) Math.pow(2, k)-1;
            bits = 2;
        }else if(k==2) {
            L = (int) Math.pow(2, k)-1;
            bits = 1;
        }else if(k==1) {
            System.out.println("k=1 negalimas");
            System.exit(1);
        }else {
            L = (int) Math.pow(2, k)-1;
            bits = 8;
        }


        // read from file
        String str = readData(path, bits);
        List<InnerOutput> outputs = new ArrayList<>();
        Map<String, Integer> treeDict = new HashMap<>();
        int mLen = str.length();
        int i = 0;
        while (i < mLen) {

            Set<String> keySet = treeDict.keySet();

            // generuoju kelis search medzius
            InnerTrie trie = buildTree(keySet);
            char messageI = str.charAt(i);
            String messageIStr = String.valueOf(messageI);

            // naudoju kelis medzius paieskai
            if (!trie.search(messageIStr)) {

                outputs.add(new InnerOutput(0, messageI));

                // pleciame zodyna priklausomai nuo parametro
                if(treeDict.size() < L)
                    treeDict.put(messageIStr, treeDict.size() + 1);
                i++;
            } else if (i == mLen - 1) {
                outputs.add(new InnerOutput(treeDict.get(messageIStr), ' '));
                i++;
            } else {

                for (int j = i + 1; j < mLen; j++) {

                    String substring = str.substring(i, j + 1);
                    String myString = str.substring(i, j);

                    // naudoju kelis medzius paieskai
                    if (!trie.search(substring)) {

                        outputs.add(new InnerOutput(treeDict.get(myString), str.charAt(j)));

                        // pleciame zodyna priklausomai nuo parametro
                        if(treeDict.size() < L)
                            treeDict.put(substring, treeDict.size() + 1);
                        i = j + 1;
                        break;
                    }
                    if (j == mLen - 1) {
                        outputs.add(new InnerOutput(treeDict.get(substring), ' '));
                        i = j + 1;
                    }
                }
            }
        }

        writeData(outputs, k, bits);
        //System.out.println(outputs);
        //for (int j=0; j<outputs.size(); j++) {
        //    System.out.println(outputs.get(j).index+":"+outputs.get(j).character);
        //}
    }

    public void writeData(List<InnerOutput> outputs, byte parametras, int bits){
        FileToWrite.setFileName("encode.lz78", true);

        //parametras
        FileToWrite.write(parametras);

        // zodyno dydis
        System.out.println(outputs.size());
        FileToWrite.write(outputs.size());

        /*int j = 0;
        double bitsForInt = Math.pow(2, j);
        while(outputs.size()>bitsForInt){
            j=j+1;
            bitsForInt = Math.pow(2, j);
        }*/

        //System.out.println(bitsForInt);

        // temp ir bitsforint naudojame kad irasytume mazesni Integer reiksmiu bitu kieki, kai to mums nereikia
        int temp=1;
        for (int i=0; i<outputs.size(); i++) {
            double bitsForInt = Math.pow(2, temp);
            if(bitsForInt-1 < i)
                temp = temp + 1;
            //System.out.println(">"+i);
            //System.out.println(bitsForInt);
            FileToWrite.write(outputs.get(i).getIndex(), temp);
            FileToWrite.write(outputs.get(i).getCharacter(), bits);

        }
        FileToWrite.close();
        System.out.println(">>>Successfully encoded<<<");
    }


}