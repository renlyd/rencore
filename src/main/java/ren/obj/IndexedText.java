package ren.obj;

public class IndexedText {

    static public void example1(String[] args) {
        String s = "A,B,,C,,,Testing,1,2,,3,";
        IndexedText t = new IndexedText(s);
        t.setDelimitedMode(true);
        t.markCharacter(',');
        t.printText();
    }

    static public void example2(String[] args) {
        String s = "Hello\n\n1\n2 3 4 5 6\nThere is a <!-- Comment --> solution.";
        StringBuilder b = new StringBuilder();
        IndexedText t = new IndexedText(s);
        t.removeDoubleReturns();
        t.removeHTMLComments();
        t.removeJSPComments();
        t.collapseText();
        t.printText();
    }

    public IndexedText(String text) {
        setText(text);
    }

    public IndexedText(IndexedText source) {
        setText(source.getOriginalText());
    }
    private int[] indices;
    private char[] characters;
    private StringBuilder originalText;
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean delimitedMode;

    public boolean getDelimitedMode() {
        return delimitedMode;
    }

    public void setDelimitedMode(boolean delimitedMode) {
        this.delimitedMode = delimitedMode;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public char[] getCharacters() {
        return characters;
    }

    public int[] getIndices() {
        return indices;
    }

    public String getOriginalText() {
        return originalText.toString();
    }

    public void setText(String text) {
        originalText = new StringBuilder(text);
        indices = new int[text.length()];
        characters = new char[text.length()];
        for (int i = 0; i < text.length(); i++) {
            indices[i] = i;
            characters[i] = text.charAt(i);
        }
    }

    public int getSize() {
        if (characters == null) {
            return 0;
        }
        return characters.length;
    }

    public String[] getDelimitedWords() {
        int wordCount = 0;
        int size = getSize();
        for (int i = 0; i < size; i++) {
            if (indices[i] == -1) {
                wordCount++;
            } else {
            }
        }
        if (size > 0) {
            wordCount++;
        }
        String[] words = new String[wordCount];
        StringBuilder word = new StringBuilder();
        wordCount = 0;
        for (int i = 0; i < size; i++) {
            if (indices[i] == -1) {
                words[wordCount] = word.toString();
                word.setLength(0);
                wordCount++;
            } else {
                word.append(getCharacters()[i]);
            }
        }
        if (size > 0) {
            words[wordCount] = word.toString();
        }
        return words;
    }

    public String[] getWords() {
        if (getDelimitedMode()) {
            return getDelimitedWords();
        }
        return getConsolidatedWords();
    }

    public String[] getConsolidatedWords() {
        int wordCount = 0;
        boolean flag = false;
        int size = getSize();
        for (int i = 0; i < size; i++) {
            boolean currentFlag = true;
            if (indices[i] == -1) {
                currentFlag = false;
            }
            if (currentFlag != flag) {
                if (currentFlag) {
                    wordCount++;
                }
                flag = currentFlag;
            }
        }
        String[] words = new String[wordCount];
        flag = false;
        //String word = "";
        StringBuilder word = new StringBuilder();
        wordCount = -1;
        for (int i = 0; i < size; i++) {
            boolean currentFlag = true;
            if (indices[i] == -1) {
                currentFlag = false;
            }
            if (currentFlag != flag) {
                if (currentFlag) {
                    wordCount++;
                } else {
                    words[wordCount] = word.toString();
                    word.setLength(0);
                }
                flag = currentFlag;
            }
            if (currentFlag) {
                word.append(getCharacters()[i]);
            }
        }
        if (flag) {
            words[wordCount] = word.toString();
        }
        return words;
    }

    public void collapseText() {
        int count = 0;
        int size = getSize();
        for (int i = 0; i < size; i++) {
            if (indices[i] != -1) {
                count++;
            }
        }
        int[] tempIndices = new int[count];
        char[] tempChars = new char[count];
        count = 0;
        for (int i = 0; i < size; i++) {
            if (indices[i] != -1) {
                tempIndices[count] = indices[i];
                tempChars[count] = characters[i];
                count++;
            }
        }
        indices = tempIndices;
        characters = tempChars;
    }

    public void printIndexedText() {
        for (int i = 0; i < getSize(); i++) {
            System.out.println(indices[i] + "   " + characters[i]);
        }
    }

    public void printText() {
        System.out.println(getText());
    }

    public String getText() {
        StringBuilder s = new StringBuilder(getSize());
        s.append(characters);
        return s.toString();
    }

    public String getOriginalString(int startIndex, int endIndex) {
        startIndex = getIndices()[startIndex];
        endIndex = getIndices()[endIndex];
        return getOriginalText().substring(startIndex, endIndex + 1);
    }

    @Override
    public String toString() {
        return getText();
    }

    /**
     * Remove Non Alphabet Characters
     *
     * @param line
     * @return
     */
    public void removeNonAlphabetCharacters() {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            if (ren.util.Strings.qualifyAlphabetCharacter(getCharacters()[i]) == false) {
                getIndices()[i] = -1;
            }
        }
    }

    public void removeWhiteSpacesAndPaddingCharacters() {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            if (ren.util.Strings.qualifyWhiteSpacesAndPaddingCharacters(getCharacters()[i]) == true) {
                getIndices()[i] = -1;
            }
        }
    }

    public void removeRepeatingCharacters() {
        char prevCharacter = ' ';
        int size = getSize();
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                prevCharacter = getCharacters()[i];
                continue;
            }
            if (getCharacters()[i] == prevCharacter) {
                getIndices()[i] = -1;
            }
            prevCharacter = getCharacters()[i];
        }
    }

    public void removeDoubleReturns() {
        markString("\n\r\n\r");
        markString("\r\n\r\n");
        markString("\n\n");
        markString("\r\r");
    }

    public void markRange(int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            getIndices()[i] = -1;
        }
    }

    public void markString(String s) {
        for (int i = 0; i < getCharacters().length; i++) {
            if (compareToString(i, s)) {
                for (int j = 0; j < s.length(); j++) {
                    getIndices()[i + j] = -1;
                }
            }
        }
    }

    public void markCharacters(char... c) {
        for (int i = 0; i < c.length; i++) {
            markCharacter(c[i]);
        }
    }

    public void markCharacter(char c) {
        for (int i = 0; i < getCharacters().length; i++) {
            if (getCharacters()[i] == c) {
                getIndices()[i] = -1;
            }
        }
    }

    public boolean compareToString(int index, String s) {
        for (int i = index; i < index + s.length(); i++) {
            if (i >= getCharacters().length) {
                return false;
            }
            if (getCharacters()[i] != s.charAt(i - index)) {
                return false;
            }
        }
        return true;
    }

    public void removeHTMLTags() {
        boolean htmlFlag = false;
        int size = getSize();
        for (int i = 0; i < size; i++) {
            if (getCharacters()[i] == '<') {
                htmlFlag = true;
            }
            if (htmlFlag) {
                getIndices()[i] = -1;
            }
            if (getCharacters()[i] == '>') {
                htmlFlag = false;
            }
        }
    }

    public void removeHTMLComments() {
        boolean commentFlag = false;
        int size = getSize();
        for (int i = 0; i < size; i++) {
            if (compareToString(i, "<!--")) {
                commentFlag = true;
            }
            if (compareToString(i, "-->")) {
                getIndices()[i] = -1;
                getIndices()[i + 1] = -1;
                getIndices()[i + 2] = -1;
                commentFlag = false;
            }
            if (commentFlag) {
                getIndices()[i] = -1;
            }
        }
    }

    public void removeJSPComments() {
        boolean commentFlag = false;
        int size = getSize();
        for (int i = 0; i < size; i++) {
            if (compareToString(i, "/*")) {
                commentFlag = true;
            }
            if (compareToString(i, "*/")) {
                getIndices()[i] = -1;
                getIndices()[i + 1] = -1;
                commentFlag = false;
            }
            if (commentFlag) {
                getIndices()[i] = -1;
            }
        }
    }

    public void removeSpacingAndPaddingBetweenTags() {
        boolean tagFlag = false;
        int size = getSize();
        for (int i = 0; i < size - 1; i++) {
            if (getCharacters()[i] == '<' && getCharacters()[i + 1] != '%' && getCharacters()[i + 1] != '!') {
                tagFlag = true;
            }
            if (getCharacters()[i] == '>') {
                tagFlag = false;
            }
            if (tagFlag == false) {
                if (getCharacters()[i] == ' ' && getCharacters()[i + 1] == ' ') {
                    getIndices()[i] = -1;
                }
                if (getCharacters()[i] == '\t') {
                    getIndices()[i] = -1;
                }
                if (getCharacters()[i] == '\n') {
                    getIndices()[i] = -1;
                }
            }
        }
    }
}
