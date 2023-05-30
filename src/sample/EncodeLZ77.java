package sample;

public class EncodeLZ77 {
    private static final int minLength = 8;

    public void start(String path, int m, int n) {
        int windowSize = (1 << m) - 1;
        int maxLength = (1 << n) - 1;

        FileToWrite.setFileName("encode.lz77", true);
        StringBuilder buffer = new StringBuilder(windowSize);

        FileToWrite.write((byte)m);
        FileToWrite.write((byte)n);

        try {
            String str = readData(path, 8);
            String currentMatch = "";
            int matchIndex = 0, tempIndex, nextChar;

            for (int i=0; i<str.length(); i++) {
                nextChar = str.charAt(i);
                tempIndex = buffer.indexOf(currentMatch + (char) nextChar);
                if (tempIndex != -1 && currentMatch.length() < maxLength) {
                    currentMatch += (char) nextChar;
                    matchIndex = tempIndex;
                } else {
                    if (currentMatch.length() >= minLength) {

                        FileToWrite.write(Boolean.FALSE);
                        FileToWrite.write(matchIndex, m);
                        FileToWrite.write(currentMatch.length(), n);

                        buffer.append(currentMatch);
                        currentMatch = "" + (char) nextChar;
                        matchIndex = 0;

                    } else {
                        currentMatch += (char) nextChar;
                        matchIndex = -1;
                        while (matchIndex == -1) {

                            FileToWrite.write(Boolean.TRUE);
                            FileToWrite.write((byte) currentMatch.charAt(0));

                            buffer.append(currentMatch.charAt(0));
                            currentMatch = currentMatch.substring(1);
                            matchIndex = buffer.indexOf(currentMatch);
                        }
                    }
                    if (buffer.length() > windowSize) {
                        buffer.delete(0, buffer.length() - windowSize);
                    }
                }
            }
            //Check what left
            while (currentMatch.length() > 0) {
                if (currentMatch.length() >= minLength) {

                    FileToWrite.write(Boolean.FALSE);
                    FileToWrite.write(matchIndex, m);
                    FileToWrite.write(currentMatch.length(), n);

                    buffer.append(currentMatch); // append to the search buffer
                    currentMatch = "";
                    matchIndex = 0;

                } else {
                    matchIndex = -1;
                    while (currentMatch.length() > 0) {

                        FileToWrite.write(Boolean.TRUE);
                        FileToWrite.write((byte) currentMatch.charAt(0));

                        buffer.append(currentMatch.charAt(0));
                        currentMatch = currentMatch.substring(1, currentMatch.length());
                        matchIndex = buffer.indexOf(currentMatch);
                    }
                }
                if (buffer.length() > windowSize) {
                    buffer.delete(0, buffer.length() - windowSize);
                }
            }

        } finally {
            FileToWrite.close();
            System.out.println("uzkoduota");
        }
    }


    public String readData(String p, int bits) {
        FileToRead.setFileName(p);
        return FileToRead.readString(bits);
    }

}