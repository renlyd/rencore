package ren.obj;

import java.util.ArrayList;
import java.util.HashSet;

public class LineTool {

    static public void main(String[] args) {
        example1();
    }

    static public void example1() {
        LineTool.Delimited tool = new LineTool.Delimited(',');
        tool.setLine("a,b,c");
        System.out.println(tool.getWord(1));
        System.out.println(tool.getWord(2));
        System.out.println(tool.getWord(3));
    }

    public LineTool() {
        super();
        init();
    }

    public LineTool(char... dividers) {
        this();
        setDivider(dividers);
    }

    public LineTool(String line) {
        this();
        setLine(line);
    }

    public LineTool(String line, char... dividers) {
        this();
        setLine(line);
        setDivider(dividers);
    }

    public LineTool(String line, int fixedWidth, boolean fixedWidthFlag) {
        this();
        setLine(line);
        setFixedWidth(fixedWidth);
    }

    public void init() {
        clear();
    }

    public void clear() {
        dividers = new HashSet<>();
        setDivider(new char[]{' ', '\t', '\n', '\r'});
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private String line;

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        words = null;
        this.line = line;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean delimitedMode;

    public boolean getDelimitedMode() {
        return delimitedMode;
    }

    public void setDelimitedMode(boolean delimitedMode) {
        this.delimitedMode = delimitedMode;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private String[] words;
    private HashSet<Character> dividers;

    public void addDivider(char divider) {
        dividers.add(divider);
    }

    public void setDivider(char... dividers) {
        this.dividers.clear();
        for (int i = 0; i < dividers.length; i++) {
            this.dividers.add(dividers[i]);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private int fixedWidth;

    public int getFixedWidth() {
        return fixedWidth;
    }

    public void setFixedWidth(int fixedWidth) {
        this.fixedWidth = fixedWidth;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public String[] getWords() {
        return prepareWords();
    }

    public String[] prepareWords() {
        if (words != null) {
            return words;
        }
        if (getFixedWidth() > 0) {
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < getLine().length(); i += getFixedWidth()) {
                int startIndex = i;
                int endIndex = i + getFixedWidth();
                if (endIndex > getLine().length()) {
                    endIndex = getLine().length();
                }
                list.add(getLine().substring(startIndex, endIndex));
            }
            words = list.toArray(new String[list.size()]);
        } else {
            words = ren.util.Strings.split(getDelimitedMode(), getLine(), ren.util.Array.toCharArray(dividers));
        }
        return words;
    }

    public int getWordCount() {
        return prepareWords().length;
    }

    public String getWord(int column) {
        prepareWords();
        if (words == null) {
            return "";
        }
        if (words.length == 0) {
            return "";
        }
        if (column - 1 >= words.length) {
            return "";
        }
        if (column < 0) {
            return "";
        }
        return words[column - 1];
    }

    public int getInteger(int column) {
        return ren.util.Numbers.readIntFromString(getWord(column));
    }

    public double getDouble(int column) {
        return ren.util.Numbers.readDoubleFromString(getWord(column));
    }

    static public class Delimited extends LineTool {

        public Delimited() {
            super();
        }

        public Delimited(char... dividers) {
            super(dividers);
        }

        public Delimited(String line) {
            super(line);
        }

        public Delimited(String line, char... dividers) {
            super(line, dividers);
        }

        @Override
        public void clear() {
            super.clear();
            setDelimitedMode(true);
        }
    }

}
