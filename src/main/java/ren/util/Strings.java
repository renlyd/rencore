package ren.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TreeSet;
import ren.obj.IndexedText;

public class Strings {

    static public String[] split(boolean delimitedMode, String line, char... separators) {
        if (line == null) {
            return null;
        }
        if (line.length() == 0) {
            return new String[0];
        }
        IndexedText t = new IndexedText(line);
        t.setDelimitedMode(delimitedMode);
        t.markCharacters(separators);
        return t.getWords();
    }

    static public String[] split(String line, char... separators) {
        return split(false, line, separators);
    }

    public static String[] split(String line, String... separators) {
        if (line == null) {
            return null;
        }
        if (line.length() == 0) {
            return null;
        }
        for (int i = 1; i < separators.length; i++) {
            line = line.replace((String) separators[i], (String) separators[0]);
        }
        if (separators[0].equals(".")) {
            separators[0] = "\\.";
        }
        String[] words = line.split(separators[0]);
        return words;
    }
    public final String LineSeparator = "\r\n";

    static public int getIndexofLineSeparators(String s) {
        String[] separators = new String[]{"\r\n", "\n"};
        for (int i = 0; i < separators.length; i++) {
            int index = s.indexOf(separators[i]);
            if (index >= 0) {
                return index;
            }
        }
        return -1;
    }

    static public boolean checkForLineSeparators(String s) {
        if (getIndexofLineSeparators(s) >= 0) {
            return true;
        }
        return false;
    }

    public static boolean compareEquals(String a, String b) {
        if (a == null && b != null) {
            return false;
        }
        if (a != null && b == null) {
            return false;
        }
        if (a != null && b != null) {
            if (a.equals(b) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean compareEqualsIgnoreCase(String a, String b) {
        if (a == null && b != null) {
            return false;
        }
        if (a != null && b == null) {
            return false;
        }
        if (a != null && b != null) {
            if (a.equalsIgnoreCase(b) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean compareGreater(String a, String b) {
        if (a.compareTo(b) == 0) {
            return false;
        }
        if (a.length() == 0) {
            return false;
        }
        if (b.length() == 0) {
            return true;
        }
        for (int i = 0; i < a.length(); i++) {
            if (b.length() > i) {
                if (a.charAt(i) > b.charAt(i)) {
                    return true;
                }
                if (a.charAt(i) == b.charAt(i)) {
                    continue;
                }
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public static int getCount(String line, String character) {
        int count = 0;
        int index = 0;
        int newIndex = 0;
        // CHECK '@'
        while ((newIndex = line.indexOf(character, index)) >= index) {
            index = newIndex + character.length();
            count++;
        }
        return count;
    }

    public static int getCount(String line, String character, int startIndex, int endIndex) {
        int count = 0;
        for (int i = startIndex; i <= endIndex; i++) {
            if (line.substring(i, i + 1).compareTo(character) == 0) {
                count++;
            }
        }
        return count;
    }

    public static String getStringInBetweenStrings(String line, String startPhrase, String endPhrase) {
        return getStringBeforeString(getStringAfterString(line, startPhrase), endPhrase);
    }

    public static String getStringBeforeString(String line, String phrase) {
        String s = "";
        if (line == null || phrase == null) {
            return s;
        }
        int index = line.toUpperCase().indexOf(phrase.toUpperCase());
        if (index >= 0) {
            s = line.substring(0, index);
        }
        return s;
    }

    public static String getStringAfterString(String line, String phrase) {
        String s = "";
        if (line == null || phrase == null) {
            return s;
        }
        int index = line.toUpperCase().indexOf(phrase.toUpperCase());
        if (index >= 0) {
            s = line.substring(index + phrase.length());
        }
        return s;
    }

    public static String getStringAfterWord(String line, String word) {
        String s = "";
        if (line == null || word == null) {
            return s;
        }
        int index = line.indexOf(word);
        if (index >= 0) {
            s = line.substring(index + word.length());
        }
        return s;
    }

    public static int getWordCount(String line) {
        if (line == null) {
            return 0;
        }
        if (line.length() == 0) {
            return 0;
        }
        String words[] = split(line, wordSeparators);
        int counter = 0;
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > 0) {
                counter++;
            }
        }
        return counter;
    }

    public static int getSentenceCount(String line) {
        if (line == null) {
            return 0;
        }
        if (line.length() == 0) {
            return 0;
        }
//        String[] separators = {".", "?", "!"};
        char[] separators = {'.', '?', '!'};
        String words[] = split(line, separators);
        int counter = 0;
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > 0) {
                counter++;
            }
        }
        return counter;
    }

    public static int getWordIndex(String[] words, String word) {
        for (int i = 0; i < words.length; i++) {
            if (words[i].compareTo(word) == 0) {
                return i;
            }
        }
        return - 1;
    }
    static public char[] wordSeparators = {' ', '\t', '\n', '\r'};

    public static String[] getWords(String line) {
        return split(line, wordSeparators);
    }

    public static String[] getWords(String line, int fixedSpacing) {
        int i = 0;
        ArrayList<String> words = new ArrayList<>();
        while (i < line.length()) {
            int end = i + fixedSpacing;
            if (end > line.length()) {
                end = line.length();
            }
            String word = line.substring(i, end);
            if (word != null) {
                words.add(word);
            }
            i += fixedSpacing;
        }
        return words.toArray(new String[words.size()]);
    }

    public static String[] getWordsFromCommas(String line) {
        line = line.replaceAll(",,", " @COMMA@ ");
        line = line.replace(',', ' ');
        String[] words = getWords(line);
        for (int i = 0; i < words.length; i++) {
            if (words[i].compareTo("@COMMA@") == 0) {
                words[i] = "";
            }
        } // PRESERVE WORD POSITION
        return words;
    }

    public static String getWord(String line, int pos) {
        String words[] = getWords(line);
        if (words == null) {
            return "";
        }
        if (words.length == 0) {
            return "";
        }
        if (pos - 1 >= words.length) {
            return "";
        }
        if (pos < 0) {
            return "";
        }
        return words[pos - 1];
    }

    public static String[] duplicate(String[] source) {
        String[] dest = new String[source.length];
        for (int i = 0; i < source.length; i++) {
            dest[i] = String.format(java.util.Locale.ENGLISH, "%s", source[i]);
        }
        return dest;
    }

    public static String duplicate(String name) {
        return String.format(java.util.Locale.ENGLISH, "%s", name);
    }

    public static String readFromString(String line, int startIndex, int length) {
        String s = line.substring(startIndex, startIndex + length);
        return s;
    }

    public static String trimZerosFromBeginning(String word) {
        String temp = word;
        while (temp.length() > 0) {
            if (temp.substring(0, 1).compareTo("0") == 0) {
                temp = temp.substring(1, temp.length());
            } else {
                break;
            }
        }
        return temp;
    }

    public static String trimZerosFromEnd(String word) {
        String temp = word;
        while (temp.length() > 0) {
            if (temp.substring(temp.length() - 1, temp.length()).compareTo("0") == 0) {
                temp = temp.substring(0, temp.length() - 1);
            } else {
                break;
            }
        }
        return temp;
    }

    public static String trimZerosFromEnd(String word, int startIndex) {
        String temp = word;
        while (temp.substring(startIndex).length() > 0) {
            if (temp.substring(temp.length() - 1, temp.length()).compareTo("0") == 0) {
                temp = temp.substring(0, temp.length() - 1);
            } else {
                break;
            }
        }
        return temp;
    }

    public static String trimNumberFromEnd(String word) {
        if (word.length() == 0) {
            return word;
        }
        String s = word;
        String lastChar = word.substring(s.length() - 1, s.length());
        while (checkForOnlyNumeric(lastChar)) {
            s = trimEnd(s, 1);
            lastChar = word.substring(s.length() - 1, s.length());
        }
        return s;
    }

    static public String trimEnd(String s, String ending) {
        if (s.endsWith(ending)) {
            s = ren.util.Strings.trimEnd(s, ending.length());
        }
        return s;
    }

    public static String trimEnd(String s, int length) {
        if (length > s.length()) {
            return s;
        }
        return s.substring(0, s.length() - length);
    }

    static public String trimStart(String s, String beginning) {
        if (s.startsWith(beginning)) {
            s = ren.util.Strings.trimStart(s, beginning.length());
        }
        return s;
    }

    public static String trimStart(String s, int length) {
        if (length > s.length()) {
            return s;
        }
        return s.substring(length);
    }

    static public String removeNumericalParenthesis(String name) {
        if (name.endsWith(")")) {
            int startIndex = name.indexOf("(");
            if (ren.util.Strings.checkForOnlyNumeric(name.substring(startIndex + 1, name.length() - 1))) {
                name = name.substring(0, startIndex);
            }
        }
        return name;
    }

    public static boolean checkForOnlyNumeric(String s) {
        if (s == null) {
            return false;
        }
        if (s.trim().length() == 0) {
            return false;
        }
        String[] parts = Strings.getWords(s);
        for (int j = 0; j < parts.length; j++) {
            for (int i = 0; i < parts[j].length(); i++) {
                char c = parts[j].charAt(i);
                if (c == '.') {
                    continue;
                }
                if (c == '-') {
                    continue;
                }
                if (c == '+') {
                    continue;
                }
                if (c == 'E' || c == 'e') {
                    continue;
                }
                if (Strings.checkForNumeric(c)) {
                    continue;
                }
                return false;
            }
        }
        if (s.equalsIgnoreCase("E")) {
            return false;
        }
        return true;
    }

    public static boolean checkForAlphaNumeric(String s) {
        //IF CONTAINS NUMBER
        for (int i = 0; i < s.length(); i++) {
            //String c = s.substring(i, i + 1);
            char c = s.charAt(i);
            if (c == '.') {
                continue;
            }
            if (c == '-') {
                continue;
            }
            if (c == '+') {
                continue;
            }
            if (c == 'E' || c == 'e') {
                continue;
            }
            if (java.lang.Character.isLetterOrDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    public static boolean checkForAlphabetOnly(String s) {
        if (s == null) {
            return false;
        }
        if (s.trim().length() == 0) {
            return false;
        }
        String[] parts = Strings.getWords(s);
        for (int j = 0; j < parts.length; j++) {
            for (int i = 0; i < parts[j].length(); i++) {
                char c = parts[j].charAt(i);
                if (checkForAlphabet(c) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkForOnlyAlphaNumeric(String s) {
        if (s == null) {
            return false;
        }
        if (s.trim().length() == 0) {
            return false;
        }
        String[] parts = Strings.getWords(s);
        for (int j = 0; j < parts.length; j++) {
            for (int i = 0; i < parts[j].length(); i++) {
                char c = parts[j].charAt(i);
                if (java.lang.Character.isLetter(c) || java.lang.Character.isDigit(c)) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    public static boolean checkForOnlyAlphaNumeric(char c) {
        if (checkForAlphabet(c) || checkForNumeric(c)) {
            return true;
        }
        return false;
    }

    public static boolean checkForAlphabet(char c) {
        if (java.lang.Character.isLetter(c)) {
            return true;
        }
        return false;
    }

    public static boolean checkForNumeric(char c) {
        if (java.lang.Character.isDigit(c)) {
            return true;
        }
        return false;
    }

    public static char generateRandomAlphabetCharacter(boolean lowerUpperCase) {
        String list = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int index = (int) ren.util.Numbers.getRandomNumber(0, list.length() - 1, true);
        char c = list.charAt(index);
        if (lowerUpperCase == false) {
            return String.valueOf(c).toLowerCase().charAt(0);
        }
        return c;
    }

    public static char generateRandomAlphaNumericCharacter() {
        String list = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int index = (int) ren.util.Numbers.getRandomNumber(0, list.length() - 1, true);
        return list.charAt(index);
    }

    public static char generateRandomNumericCharacter() {
        String list = "0123456789";
        int index = (int) ren.util.Numbers.getRandomNumber(0, list.length() - 1, true);
        return list.charAt(index);
    }

    public static String generateRandomAlphaNumericString(int length) {
        String s = "";
        for (int i = 0; i < length; i++) {
            s += generateRandomAlphaNumericCharacter();
        }
        return s;
    }

    public static String generateRandomNumericString(int length) {
        String s = "";
        for (int i = 0; i < length; i++) {
            s += generateRandomNumericCharacter();
        }
        return s;
    }

    public static String generateRandomAlphaString(int length) {
        String s = "";
        for (int i = 0; i < length; i++) {
            s += generateRandomAlphabetCharacter(true);
        }
        return s;
    }

    public static boolean checkIfContainsNumericCharacter(String c) {
        for (int j = 0; j < c.length(); j++) {
            if (java.lang.Character.isDigit(c.charAt(j))) {
                return true;
            }
        }
        return false;
    }

    public static String trimLength(String s, int length) {
        if (s.length() > length) {
            return s.substring(0, length);
        }
        return s;
    }

    public static String trimPart(String s, String part) {
        return s.replace(part, "");
    }

    public static String trimParts(String s, String[] parts) {
        String newString = s;
        for (int i = 0; i < parts.length; i++) {
            newString = trimPart(newString, parts[i]);
        }
        return newString;
    }

    public static String substring(String s, int index, int length) {
        return s.substring(index, index + length);
    }

    static public String swap(String s, char a, char b) {
        TreeSet<Integer> set = new TreeSet<>();
        set.add(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == a) {
                set.add(i);
            }
        }
        s = s.replace(b, a);
        String d = "";
        Integer[] indices = set.toArray(new Integer[set.size()]);
        for (int i = 0; i < indices.length - 1; i++) {
            d += s.substring((int) indices[i] + 1, (int) indices[i + 1]);
            d += b;
        }
        d += s.substring((int) indices[set.size() - 1] + 1);
        return d;
    }

    public static String reverse(String s) {
        byte[] original = s.getBytes();
        byte[] reverse = s.getBytes();
        for (int i = 0; i < reverse.length; i++) {
            reverse[i] = original[reverse.length - i - 1];
        }
        return new String(reverse);
    }

    public static void copy(String[] source, String[] dest) {
        dest = new String[source.length];
        for (int i = 0; i < source.length; i++) {
            dest[i] = new String(source[i]);
        }
    }

    public static boolean readBooleanFromString(String value) {
        if (value.compareToIgnoreCase("TRUE") == 0 || value.compareToIgnoreCase("YES") == 0 || value.compareToIgnoreCase("ON") == 0) {
            return true;
        }
        return false;
    }

    public static String extract(String line, char separator) {
        int startPos = line.indexOf(separator);
        int endPos = line.indexOf(separator, startPos + 1);
        if (startPos < 0 || endPos < 0) {
            return "";
        }
        return line.substring(startPos + 1, endPos);
    }

    public static String breakLine(String line, int max) {
        String ls = java.lang.System.lineSeparator();
        String s = "";
        for (int i = 0; i < line.length(); i++) {
            if (i != 0 && i % max == 0) {
                s += "\\" + ls;
            } else {
                s += line.charAt(i);
            }
        }
        return s;
    }

    public static String joinLine(String line) {
        String ls = java.lang.System.lineSeparator();
        while (line.indexOf("\\") >= 0) {
            line = line.replace("\\", "");
        }
        while (line.indexOf(ls) >= 0) {
            line = line.replace(ls, "");
        }
        return line;
    }

    public static String padZeros(String s, int padding) {
        return padCharacter(s, '0', padding);
    }

    public static String padSpaces(String s, int padding) {
        return padCharacter(s, ' ', padding);
    }

    public static String padCharacter(String s, char c, int padding) {
        while (padding > s.length()) {
            s = c + s;
        }
        return s;
    }

    public static String padZeros(int number, int padding) {
        String s = String.format(java.util.Locale.ENGLISH, "%d", number);
        while (padding > s.length()) {
            s = "0" + s;
        }
        return s;
    }

    public static String padSpaces(int number, int padding) {
        String s = String.format(java.util.Locale.ENGLISH, "%d", number);
        while (padding > s.length()) {
            s = " " + s;
        }
        return s;
    }

    static public String cleanUpSpaces(String line) {
        line = line.replace('\u00A0', ' ').replace('\u2007', ' ').replace('\u202F', ' ');
        return line;
    }

    static public String trim(String line) {
        if (line == null) {
            return line;
        }
        line = cleanUpSpaces(line).trim();
        return line;
    }

    static public String toNonSpace(String name) {
        if (name == null) {
            return name;
        }
        while (name.indexOf(" ") >= 0) {
            name = name.replace(" ", "_");
        }
        return name;
    }

    static public ArrayList<String> toFormalTitles(ArrayList<String> list) {
        ArrayList<String> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            newList.add(toFormalTitle(list.get(i)));
        }
        return newList;
    }

    static public String toFormalTitle(String line) {
        return toFormalTitle(line, true);
    }

    static public String toFormalTitle(String line, boolean makeLowerCase) {
        if (line == null) {
            return line;
        }
        if (makeLowerCase) {
            line = line.toLowerCase();
        }
        boolean upperFlag = true;
        String s = "";
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == ' ' || c == '-' || c == '_') {
                upperFlag = true;
                s += c;
            } else {
                if (upperFlag) {
                    s += String.valueOf(c).toUpperCase();
                    upperFlag = false;
                } else {
                    s += c;
                }
            }
        }
        return s.trim();
    }

    static public String getPositionIndent(int position) {
        return getPositionIndent(position, 8);
    }

    static public String getPositionIndent(int position, int indentSpacing) {
        String s = "";
        for (int j = 0; j < position * indentSpacing; j++) {
            s += " ";
        }
        return s;
    }

    static public String getDifference(String a, String b) {
        if (a.length() > b.length()) {
            if (a.indexOf(b) == 0) {
                return a.substring(b.length());
            }
        }
        if (b.length() > a.length()) {
            if (b.indexOf(a) == 0) {
                return b.substring(a.length());
            }
        }
        return "";
    }

    static public String removeNonAlphabetCharacters(String line) {
        char[] newLine = new char[line.length()];
        for (int i = 0; i < newLine.length; i++) {
            char c = line.charAt(i);
            if (qualifyAlphabetCharacter(c)) {
                newLine[i] = c;
                continue;
            }
            newLine[i] = ' ';
        }
        return new String(newLine);
    }

    static public boolean qualifyWhiteSpacesAndPaddingCharacters(char c) {
        if (c == ' ') {
            return true;
        }
        if (c == '\t') {
            return true;
        }
        return false;
    }

    static public boolean qualifyAlphabetCharacter(char c) {
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        if (c >= 'A' && c <= 'Z') {
            return true;
        }
        if (c == ' ') {
            return true;
        }
        return false;
    }

    static public boolean qualifyNumberCharacter(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }

    static public String removeRepeatingCharacters(String line) {
        String newLine = "";
        char previous = ' ';
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c != previous) {
                newLine += c;
            }
            previous = c;
        }
        return newLine;
    }

    /**
     * Avoid nulls and return a non-null string of zero length if argument is
     * null
     *
     * @param s
     * @return
     */
    static public String getNonNullString(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    static public int skipToCharacter(String data, int index, char c) {
        while (index < data.length() && data.charAt(index) != c) {
            index++;
        }
        return index;
    }

    static public String padSpaces(int numberSpaces) {
        return padCharacter(numberSpaces, ' ');
    }

    static public String padCharacter(int numberSpaces, char c) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < numberSpaces; i++) {
            s.append(c);
        }
        return s.toString();
    }

    static public String padTitle(String title, int numberSpaces, char c) {
        int spaces = numberSpaces - title.length() - 2;
        spaces /= 2;
        String s = padCharacter(spaces, c);
        s += " " + title + " ";
        s += padCharacter(spaces, c);
        return s;
    }

    static public String getYesNoFromBoolean(boolean state) {
        if (state) {
            return "yes";
        }
        return "no";
    }

    public static ArrayList<String> parseStringList(String line) {
        line = line.replaceAll(" -", "-");
        line = line.replaceAll("- ", "-");
        line = line.replaceAll(",", " ");
        line = line.replaceAll("\r\n", " ");
        line = line.replaceAll("\n\r", " ");
        line = line.replaceAll("\r", " ");
        line = line.replaceAll("\n", " ");

        ArrayList<String> list = new ArrayList<String>();
        String[] words = ren.util.Strings.getWords(line);
        if (words == null) {
            return list;
        }
        list.addAll(Arrays.asList(words));
        return list;
    }

    public static TreeSet<String> parseStringGroup(String line) {
        return new TreeSet<String>(parseStringList(line));
    }

    static public String createStringList(ArrayList<String> list) {
        if (list.size() == 0) {
            return "";
        }
        String line = "";
        for (int i = 0; i < list.size(); i++) {
            line += String.format(java.util.Locale.ENGLISH, "%s", list.get(i)) + ", ";
        }
        line = ren.util.Strings.trimEnd(line, 2);
        return line;
    }

    static public String encodeMACAddress(String mac_address) {
        String s = "";
        for (int i = 0; i < mac_address.length(); i++) {
            s += ren.util.Strings.generateRandomAlphaNumericString(1) + mac_address.charAt(i);
        }
        return s;
    }

    static public String decodeMACAddress(String mac_address) {
        String s = "";
        for (int i = 1; i < mac_address.length(); i += 2) {
            s += mac_address.charAt(i);
        }
        return s;
    }

    static public String generateCharacterRangeString(TreeSet<Character> group) {
        String s = "";
        char start = '\0';
        char prev = '\0';
        Iterator<Character> iter = group.iterator();
        int index = 0;
        while (iter.hasNext()) {
            char c = iter.next();
            if (index == 0) {
                start = c;
            }
            if (index > 0) {
                if ((int) c != ((int) prev) + 1) {
                    if (start == prev) {
                        s += start + ",";
                    } else {
                        s += start + "-" + prev + ",";
                    }
                    start = c;
                }
            }
            prev = c;
            index++;
        }
        if (start == prev) {
            s += start;
        } else {
            s += start + "-" + prev;
        }
        return s;
    }

    static public ArrayList<Character> parseCharacterList(String line) throws Exception {
        line = line.replaceAll(" -", "-");
        line = line.replaceAll("- ", "-");
        line = line.replaceAll(",", " ");
        line = line.replaceAll("\r\n", " ");
        line = line.replaceAll("\n\r", " ");
        line = line.replaceAll("\r", " ");
        line = line.replaceAll("\n", " ");

        ArrayList<Character> list = new ArrayList<Character>();
        String[] words = ren.util.Strings.getWords(line);
        if (words == null) {
            return list;
        }
        for (int j = 0; j < words.length; j++) {
            if (words[j].indexOf('-') > 0) {
                TreeSet<Character> range = getRangeOfCharacters(words[j]);
                Iterator<Character> iter = range.iterator();
                while (iter.hasNext()) {
                    list.add(iter.next());
                }
            } else {
                if (words[j].length() > 1) {
                    throw new Exception("Character length exceeds 1.");
                }
                if (words[j].length() == 0) {
                    throw new Exception("Character length cannot be zero.");
                }
                list.add(words[j].charAt(0));
            }
        }
        return list;
    }

    static public TreeSet<Character> getRangeOfCharacters(String range) throws Exception {
        range = range.trim();
        TreeSet<Character> list = new TreeSet<Character>();
        int pos = range.indexOf('-');
        if (pos < 0) {
            return list;
        }
        String start = range.substring(0, pos);
        String end = range.substring(pos + 1, range.length());
        if (start.length() > 1) {
            throw new Exception("Starting character length exceeds 1.");
        }
        if (end.length() > 1) {
            throw new Exception("Ending character length exceeds 1.");
        }
        if (start.length() == 0) {
            throw new Exception("Starting character length cannot be zero.");
        }
        if (end.length() == 0) {
            throw new Exception("Ending character length cannot be zero.");
        }
        if (compareGreater(start, end)) {
            String temp = start;
            start = end;
            end = temp;
        }
        char startC = start.charAt(0);
        char endC = end.charAt(0);
        for (char c = startC; c <= endC; c++) {
            list.add(c);
        }
        return list;
    }

    static public String generateDuplicateName(String name) {
        // DEFAULT, MAY BE OVERRIDDEN
        if (name.indexOf("_") >= 0) {
            // GET LAST OCCURRENCE
            int lastIndex = -1;
            while (name.indexOf("_", lastIndex + 1) >= 0) {
                lastIndex = name.indexOf("_", lastIndex + 1);
            }
            if (ren.util.Strings.checkForOnlyNumeric(name.substring(lastIndex + 1))) {
                int pos = ren.util.Numbers.readIntFromString(name, lastIndex + 1);
                pos++;
                name = name.substring(0, lastIndex) + "_" + pos;
                return name;
            }
        }
        return name + "_1";
    }

    static public String generateMD5(String md5) {
        try {
            // GOOD EXAMPLE: http://www.codejava.net/coding/how-to-calculate-md5-and-sha-hash-values-in-java
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            return toHexString(array);
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    static public String generateSHA256(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] array = md.digest(md5.getBytes());
            return toHexString(array);
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    static public String toHexString(String text) {
        byte[] bytes = text.getBytes();
        return toHexString(bytes);
    }

    static public String toHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; ++i) {
            sb.append(Integer.toHexString((bytes[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    static public String convertStringToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        return hex.toString();
    }

    static public String convertHexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {
            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);
            temp.append(decimal);
        }
        return sb.toString();
    }

    static public String replaceThorough(String line, String search, String replacement) {
        while (line.contains(search)) {
            line = line.replace(search, replacement);
        }
        return line;
    }

    static public String toNonNullString(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    static public String toSafeString(String s, String defaultString) {
        if (s == null) {
            return defaultString;
        }
        return s;
    }

    static public String toProgress(int current, int max) {
        String line = String.format("%d OUT OF %d (%.6f%%)", current, max, ((double) current / (double) max) * 100D);
        return line;
    }

    static public String generateVersionDateOnlyString() {
        long time = new ren.obj.DateTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        String s = String.format("V%04d%02d%02d", c.get(Calendar.YEAR), (c.get(Calendar.MONTH) + 1), c.get(Calendar.DAY_OF_MONTH));
        return s;
    }

    static public String[] toUpperCase(String[] source) {
        String[] upper = new String[source.length];
        for (int i = 0; i < source.length; i++) {
            upper[i] = source[i].toUpperCase();
        }
        return upper;
    }
}
