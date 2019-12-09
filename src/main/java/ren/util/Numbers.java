package ren.util;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;
import ren.obj.LineTool;

public class Numbers {

    public Numbers() {
    }
    static private Random random;
    static public int Precision = 6;

    static public void initializeRandom(long seed) {
        random = new Random(seed);
    }

    static synchronized public Random prepareRandom() {
        if (random == null) {
            random = new Random();
        }
        return random;
    }

    static synchronized private float nextFloat() {
        return prepareRandom().nextFloat();
    }

    static synchronized private double nextDouble() {
        return prepareRandom().nextDouble();
    }

    static public long getRandomLong(long min, long max) {
        long range = max - min;
        long offset = (long) (nextDouble() * (Math.abs(range) + 1));
        if (range != Math.abs(range)) {
            return min - offset;
        }
        return offset + min;
    }

    static public int getRandomInteger(int min, int max) {
        int range = max - min;
        int offset = (int) (nextFloat() * (Math.abs(range) + 1));
        if (range != Math.abs(range)) {
            return min - offset;
        }
        return offset + min;
    }

    static public float getRandomNumber(float min, float max, boolean whole) {
        return (float) getRandomNumber((double) min, (double) max, whole);
    }

    static public double getRandomNumber(double min, double max, boolean whole) {
        if (whole) {
            return (double) getRandomInteger((int) min, (int) max);
        }
        double range = max - min;
        double offset = (double) (nextFloat() * (Math.abs(range)));
        if (range != Math.abs(range)) {
            return min - offset;
        }
        return offset + min;
    }

    static public int getRandomPolarity() {
        if (getRandomNumber(1, 2, true) == 1) {
            return 1;
        }
        return - 1;
    }

    static public boolean getRandomBoolean() {
        if (getRandomNumber(1, 2, true) == 1) {
            return true;
        }
        return false;
    }

    static public String getNonScientific(float number) {
        // String s = String.valueOf (number);
        String s = String.format(java.util.Locale.ENGLISH, "%f", number);
        while (s.substring(s.length() - 1).compareTo("0") == 0) {
            s = s.substring(0, s.length() - 1);
        }
        if (s.substring(s.length() - 1).compareTo(".") == 0) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    static public String getScientific(float number) {
        String s = String.format(java.util.Locale.ENGLISH, "%1.3E", number);
        return s;
    }

    static public float[] copy(float[] source) {
        float[] dest = new float[source.length];
        for (int i = 0; i < source.length; i++) {
            dest[i] = source[i];
        }
        return dest;
    }

    static public float[][] copy(float[][] source) {
        int rows = source.length;
        int columns = source[0].length;
        float[][] dest = new float[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                dest[r][c] = source[r][c];
            }
        }
        return dest;
    }

    static public String toYesNoString(boolean state) {
        if (state) {
            return "yes";
        }
        return "no";
    }

    static public boolean readBooleanFromString(String line) {
        if (line == null) {
            return false;
        }
        if (line.compareToIgnoreCase("true") == 0) {
            return true;
        }
        if (line.compareToIgnoreCase("yes") == 0) {
            return true;
        }
        return false;
    }

    static public float readFloatFromString(String line) {
        float v = 0;
        if (line == null) {
            return v;
        }
        if (line.length() == 0) {
            return v;
        }
        try {
            v = Float.valueOf(line);
            return v;
        } catch (NumberFormatException e) {
        }
        try {
            line = line.replace('+', ' ');
            line = line.replace(" ", "");
            line = line.replace("\t", "");
            line = line.replace("e", "E");
            v = java.text.NumberFormat.getNumberInstance(java.util.Locale.ENGLISH).parse(line).floatValue();
        } catch (java.text.ParseException e) {
            v = 0;
        }
        return v;
    }

    static public double readDoubleFromString(String line) {
        double v = 0;
        if (line == null) {
            return v;
        }
        if (line.length() == 0) {
            return v;
        }
        try {
            v = Double.valueOf(line);
            return v;
        } catch (NumberFormatException e) {
        }
        try {
            line = line.replace('+', ' ');
            line = line.replace(" ", "");
            line = line.replace("\t", "");
            line = line.replace("e", "E");
            v = java.text.NumberFormat.getNumberInstance(java.util.Locale.ENGLISH).parse(line).doubleValue();
        } catch (java.text.ParseException e) {
            v = 0;
        }
        return v;
    }

    static public float readFloatFromString(String line, int startIndex, int length) {
        if (line == null) {
            return 0;
        }
        float v = 0;
        String value = "";
        if (startIndex >= line.length()) {
            return 0;
        }
        if (startIndex + length > line.length()) {
            value = line.substring(startIndex);
        } else {
            value = line.substring(startIndex, startIndex + length);
        }
        value = value.trim();
        try {
            v = Float.valueOf(value);
            return v;
        } catch (NumberFormatException e) {
        }
        try {
            value = value.replace('+', ' ');
            value = value.replace(" ", "");
            value = value.replace("\t", "");
            value = value.replace("e", "E");
            v = java.text.NumberFormat.getNumberInstance(java.util.Locale.ENGLISH).parse(value).floatValue();
        } catch (java.text.ParseException e) {
            v = 0;
        }
        return v;
    }

    static public int readIntFromString(String line, int startIndex, int length) {
        if (line == null) {
            return 0;
        }
        int v = 0;
        String value = "";
        if (startIndex >= line.length()) {
            return 0;
        }
        if (startIndex + length > line.length()) {
            value = line.substring(startIndex);
        } else {
            value = line.substring(startIndex, startIndex + length);
        }
        value = value.trim();
        try {
            v = Integer.valueOf(line);
            return v;
        } catch (NumberFormatException e) {
        }
        try {
            value = value.replace('+', ' ');
            value = value.replace(" ", "");
            value = value.replace("\t", "");
            value = value.replace("e", "E");
            v = java.text.NumberFormat.getNumberInstance(java.util.Locale.ENGLISH).parse(value).intValue();
        } catch (java.text.ParseException e) {
            v = 0;
        }
        return v;
    }

    static public int readIntFromString(String line, int startIndex) {
        if (line == null) {
            return 0;
        }
        int v = 0;
        String value = line.substring(startIndex);
        try {
            v = Integer.valueOf(line);
            return v;
        } catch (NumberFormatException e) {
        }
        try {
            value = value.replace('+', ' ');
            value = value.replace(" ", "");
            value = value.replace("\t", "");
            value = value.replace("e", "E");
            v = java.text.NumberFormat.getNumberInstance(java.util.Locale.ENGLISH).parse(value).intValue();
            // } catch (NumberFormatException e) {
        } catch (java.text.ParseException e) {
            v = 0;
        }
        return v;
    }

    static public int readIntFromString(String line) {
        if (line == null) {
            return 0;
        }
        int v = 0;
        try {
            v = Integer.valueOf(line);
            return v;
        } catch (NumberFormatException e) {
        }
        try {
            line = line.replace('+', ' ');
            line = line.replace(" ", "");
            line = line.replace("\t", "");
            line = line.replace("e", "E");
            v = java.text.NumberFormat.getNumberInstance(java.util.Locale.ENGLISH).parse(line).intValue();
        } catch (java.text.ParseException e) {
            v = 0;
        }
        return v;
    }

    static public long readLongFromString(String line) {
        if (line == null) {
            return 0;
        }
        long v = 0;
        try {
            v = Long.valueOf(line);
            return v;
        } catch (NumberFormatException e) {
        }
        try {
            line = line.replace('+', ' ');
            line = line.replace(" ", "");
            line = line.replace("\t", "");
            line = line.replace("e", "E");
            v = java.text.NumberFormat.getNumberInstance(java.util.Locale.ENGLISH).parse(line).longValue();
        } catch (java.text.ParseException e) {
            v = 0;
        }
        return v;
    }

    static public String roundDecimal(String number) {
        if (number.indexOf(".") < 0) {
            return number;
            // CHECK FOR SCIENTIFIC NOTATION
        }
        if (number.indexOf("E") >= 0 || number.indexOf("e") >= 0) {
            return number;
        }
        int index = number.length() - 1;
        boolean flag = false;
        int ending = number.length();
        while (index >= 0 && number.charAt(index) != '.') {
            if (readIntFromString(number.substring(index, index + 1)) == 9) {
                flag = true;
            }
            if (flag) {
                ending--;
                index--;
                if (number.substring(index, index + 1).compareTo(".") == 0) {
                    flag = false;
                    ending++;
                    index = - 1;
                    continue;
                }
                if (number.substring(index - 1, index).compareTo(".") == 0) {
                    continue;
                }
                int digit = readIntFromString(number.substring(index, index + 1));
                digit++;
                if (digit == 10) {
                    digit = 0;
                } else {
                    flag = false;
                }
                if (index != number.length()) {
                    number = number.substring(0, index) + digit + number.substring(index + 1);
                } else {
                    number = number.substring(0, index) + digit;
                }
            } else {
                break;
            }
        }
        if (ending > 0) {
            number = number.substring(0, ending);
        } else {
            return "0";
        }
        return number;
    }

    static public String filterIfInteger(double number, int padding) {
        if (number == (int) number) {
            return String.format(java.util.Locale.ENGLISH, "%" + padding + "d", (int) number);
        }
        return String.format(java.util.Locale.ENGLISH, "%" + padding + "s", ren.util.Strings.trimZerosFromEnd(String.format(java.util.Locale.ENGLISH, "%f", number)));
    }

    static public float fixNumber(float value) {
        return fixNumber(value, -10);
    }

    static public float fixNumber(float value, int exponent) {
        // IF NUMBER IS TOO SMALL THEN SET TO ZERO
        if (Math.log10(Math.abs(value)) <= exponent) {
            return 0;
        }
        return value;
    }

    static public double fixNumber(double value) {
        return fixNumber(value, -10);
    }

    static public double fixNumber(double value, int exponent) {
        // IF NUMBER IS TOO SMALL THEN SET TO ZERO
        if (Math.log10(Math.abs(value)) <= exponent) {
            return 0;
        }
        return value;
    }

    static public boolean checkIfRealNumber(String value) {
        if (value.indexOf('.') < 0) {
            return false;
        }
        return true;
    }

    static public void translateNumberList(ArrayList list, int translate) {
        for (int i = 0; i < list.size(); i++) {
            int number = (java.lang.Integer) list.get(i);
            number += translate;
            list.set(i, number);
        }
    }

    static public String createIntegerList(ArrayList<Integer> list) {
        if (list.size() == 0) {
            return "";
        }
        int prevNumber = (java.lang.Integer) list.get(0);
        ArrayList rangeList = new ArrayList();
        rangeList.add(prevNumber);
        String line = "";
        for (int i = 1; i < list.size(); i++) {
            int number = (java.lang.Integer) list.get(i);
            if (number - prevNumber != 1) {
                if (rangeList.size() > 1) {
                    line += String.format(java.util.Locale.ENGLISH, "%d-%d", (java.lang.Integer) rangeList.get(0),
                            (java.lang.Integer) rangeList.get(rangeList.size() - 1)) + ",";
                } else {
                    line += String.format(java.util.Locale.ENGLISH, "%d", prevNumber) + ",";
                }
                rangeList.clear();
            }
            rangeList.add(number);
            prevNumber = number;
        }
        if (rangeList.size() > 1) {
            line += String.format(java.util.Locale.ENGLISH, "%d-%d", (java.lang.Integer) rangeList.get(0),
                    (java.lang.Integer) rangeList.get(rangeList.size() - 1)) + ",";
        } else {
            line += String.format(java.util.Locale.ENGLISH, "%d", prevNumber) + ",";
        }
        line = ren.util.Strings.trimEnd(line, 1);
        return line;
    }

    static public String createRealNumberList(ArrayList list) {
        if (list.size() == 0) {
            return "";
        }
        String line = "";
        for (int i = 0; i < list.size(); i++) {
            float number = (java.lang.Float) list.get(i);
            String s = String.format(java.util.Locale.ENGLISH, "%f", number);
            s = ren.util.Numbers.trimFloatString(s);
            line += s + ",";
        }
        line = ren.util.Strings.trimEnd(line, 1);
        return line;
    }

    static public TreeSet<Integer> parseNumberGroup(String line) {
        return new TreeSet<Integer>(parseNumberList(line));
    }

    static public ArrayList<Integer> parseNumberList(String line) {
        while (line.contains("  ")) {
            line = line.replaceAll("  ", " ");
        }
        line = line.replaceAll(" -", "-");
        line = line.replaceAll("- ", "-");
        line = line.replaceAll(" ", ",");
        line = line.replaceAll("\r\n", ",");
        line = line.replaceAll("\n\r", ",");
        line = line.replaceAll("\r", ",");
        line = line.replaceAll("\n", ",");
        while (line.contains(",,")) {
            line = line.replaceAll(",,", ",");
        }
        if (line.startsWith(",")) {
            line = line.substring(1);
        }
        if (line.endsWith(",")) {
            line = line.substring(0, line.length() - 1);
        }
        LineTool tool = new LineTool(line, ',');
        tool.setDelimitedMode(true);
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (tool.getWordCount() == 0) {
            return list;
        }
        for (int j = 0; j < tool.getWordCount(); j++) {
            String word = tool.getWord(j + 1);
            if (word.indexOf('-') >= 0) {
                int startIndex = 0;
                if (word.startsWith("-")) {
                    startIndex++;
                }
                int separatorIndex = word.indexOf("-", startIndex);
                if (separatorIndex < 0) {
                    list.add(readIntFromString(word));
                    continue;
                }
                String fromWord = word.substring(0, separatorIndex);
                String toWord = word.substring(separatorIndex + 1);
                int fromAngle = ren.util.Numbers.readIntFromString(fromWord);
                int toAngle = ren.util.Numbers.readIntFromString(toWord);
                if (fromAngle > toAngle) {
                    int temp = fromAngle;
                    fromAngle = toAngle;
                    toAngle = temp;
                }
                for (int angle = fromAngle; angle <= toAngle; angle++) {
                    list.add(angle);
                }
            } else {
                list.add(readIntFromString(word));
            }
        }
        return list;
    }

    static public TreeSet<Float> parseFloatNumberGroup(String line) {
        return new TreeSet<Float>(parseFloatNumberList(line));
    }

    static public ArrayList<Float> parseFloatNumberList(String line) {
        line = line.replaceAll(",", " ");
        line = line.replaceAll("\r\n", " ");
        line = line.replaceAll("\n\r", " ");
        line = line.replaceAll("\r", " ");
        line = line.replaceAll("\n", " ");

        ArrayList<Float> list = new ArrayList<Float>();
        String[] words = ren.util.Strings.getWords(line);
        if (words == null) {
            return list;
        }
        for (int j = 0; j < words.length; j++) {
            list.add(readFloatFromString(words[j]));
        }
        return list;
    }

    static public TreeSet<Double> parseDoubleNumberGroup(String line) {
        return new TreeSet<Double>(parseDoubleNumberList(line));
    }

    static public ArrayList<Double> parseDoubleNumberList(String line) {
        line = line.replaceAll(",", " ");
        line = line.replaceAll("\r\n", " ");
        line = line.replaceAll("\n\r", " ");
        line = line.replaceAll("\r", " ");
        line = line.replaceAll("\n", " ");

        ArrayList<Double> list = new ArrayList<Double>();
        String[] words = ren.util.Strings.getWords(line);
        if (words == null) {
            return list;
        }
        for (int j = 0; j < words.length; j++) {
            list.add(readDoubleFromString(words[j]));
        }
        return list;
    }

    static public TreeSet<Integer> getRangeOfNumbers(String range) {
        TreeSet<Integer> list = new TreeSet<Integer>();
        int pos = range.indexOf('-');
        if (pos < 0) {
            return list;
        }
        int startNumber = readIntFromString(range.substring(0, pos));
        int endNumber = readIntFromString(range.substring(pos + 1, range.length()));
        if (startNumber > endNumber) {
            int temp = startNumber;
            startNumber = endNumber;
            endNumber = temp;
        }
        for (int i = startNumber; i <= endNumber; i++) {
            list.add(i);
        }
        return list;
    }

    static public boolean compareNumbers(float a, float b, int precision) {
        if (precision == 3) {
            if (String.format(java.util.Locale.ENGLISH, "%.3f", a).compareTo(
                    String.format(java.util.Locale.ENGLISH, "%.3f", b)) == 0) {
                return true;
            }
            return false;
        }
        return false;
    }

    static public int getIntegerFromHexString(String hexString) {
        hexString = hexString.toLowerCase().trim();
        if (hexString.indexOf("0x") == 0) // TRIM OFF THE BEGINNING 0x MARKER
        {
            hexString = hexString.substring(2);
        }
        int number = -1;
        try {
            number = java.lang.Integer.parseInt(hexString, 16);
        } catch (Exception e) {
            return -1;
        }
        return number;
    }

    static public long getLongFromHexString(String hexString) {
        hexString = hexString.toLowerCase().trim();
        if (hexString.indexOf("0x") == 0) // TRIM OFF THE BEGINNING 0x MARKER
        {
            hexString = hexString.substring(2);
        }
        long number = -1;
        try {
            number = java.lang.Long.parseLong(hexString, 16);
        } catch (Exception e) {
            return -1;
        }
        return number;
    }

    static public float round(float number, int decimalPlaces) {
        return (float) round((double) number, decimalPlaces);
    }

    static public double round(double number, int decimalPlaces) {
        double p = Math.pow(10, decimalPlaces);
        double n = number * p;
        double tmp = Math.round(n);
        return (double) (tmp / p);
    }

    static public String toUSACurrency(double value) {
        return String.format(java.util.Locale.ENGLISH, "$%.2f", value);
    }

    static public double roundToUSACurrency(double value) {
        return round(value, 2);
    }

    static public float getDegreeFromRadians(float radians) {
        float degree = (float) radians * 180F / (float) Math.PI;
        return degree;
    }

    static public float getRadiansFromDegree(float degree) {
        float radians = (float) degree * (float) Math.PI / 180F;
        return radians;
    }

    static public double getDegreeFromRadians(double radians) {
        double degree = radians * 180F / Math.PI;
        return degree;
    }

    static public double getRadiansFromDegree(double degree) {
        double radians = degree * Math.PI / 180F;
        return radians;
    }

    static public String trimFloatString(String value) {
        String s = ren.util.Strings.trimZerosFromEnd(value);
        if (s.endsWith(".")) {
            s = ren.util.Strings.trimEnd(s, 1);
        }
        return s;
    }

    static public String fixFloat(float value, int numberDigitCutoff) {
        String s = String.format(java.util.Locale.ENGLISH, "%." + numberDigitCutoff + "f", value);
        if (ren.util.Numbers.readFloatFromString(s) == ren.util.Numbers.readIntFromString(s)) {
            return String.format(java.util.Locale.ENGLISH, "%d", (int) value);
        }
        return s;
    }

    static public double findPercentageFactor(double value, double fromRange, double toRange) {
        double factor = (value - fromRange) / (toRange - fromRange);
        if (java.lang.Double.isNaN(factor)) {
            factor = 0;
        }
        return factor;
    }

    static public double findProductFromRange(double fromRange, double toRange, double factor) {
        double range = toRange - fromRange;
        double v = range * factor;
        v += fromRange;
        return v;
    }

    static public boolean checkIfNegative(ArrayList<Double> list) {
        if (list.size() == 0) {
            return false;
        }
        double value = list.get(list.size() - 1);
        return checkIfNegative(value);
    }

    static public boolean checkIfNegative(double value) {
        if (value == Math.abs(value)) {
            return false;
        }
        return true;
    }

    static public void scale(double[] values, double scale) {
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i] * scale;
        }
    }

    static public void offset(double[] values, double offset) {
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i] + offset;
        }
    }

    static public double getMinimum(double... values) {
        double min = 0;
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                min = values[i];
            } else {
                min = Math.min(min, values[i]);
            }
        }
        return min;
    }

    static public double getMaximum(double... values) {
        double max = 0;
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                max = values[i];
            } else {
                max = Math.max(max, values[i]);
            }
        }
        return max;
    }

    static public boolean checkIfZero(double... values) {
        if (values == null) {
            return true;
        }
        if (values.length == 0) {
            return true;
        }
        if (values.length == 1) {
            if (values[0] == 0) {
                return true;
            }
        }
        for (int i = 0; i < values.length; i++) {
            if (values[i] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Random drawing with faster processing than original method. Used for
     * picking a number of samples from a population while removing the elements
     * from the population
     *
     * @param counts
     * @param newCount
     * @return
     */
    static public int[] randomDrawing(int[] counts, int newCount) {
        return randomDrawing(random, counts, newCount);

    }

    static public int[] randomDrawing(Random random, int[] counts, int newCount) {
        int[] newCounts = new int[counts.length];
        if (counts.length == 0) {
            return newCounts;
        }
        int countTotal = 0;
        for (int i = 0; i < counts.length; i++) {
            countTotal += counts[i];
        }
        if (countTotal == 0) {
            return newCounts;
        }
        // ASSIGN POINTERS
        int[] pointers = new int[countTotal];
        int index = 0;
        for (int i = 0; i < counts.length; i++) {
            int max = counts[i];
            for (int j = 0; j < max; j++) {
                pointers[index] = i;
                index++;
            }
        }
        int length = pointers.length;
        for (int i = 0; i < newCount; i++) {
            index = (int) (random.nextDouble() * (length - i));
            int temp = pointers[i];
            pointers[i] = pointers[index + i];
            pointers[index + i] = temp;
        }
        // NOW ASSIGN NEW COUNTS
        for (int i = 0; i < newCount; i++) {
            index = pointers[i];
            newCounts[index]++;
        }
        //
        int totalA = 0;
        for (int i = 0; i < newCounts.length; i++) {
            totalA += newCounts[i];
        }
        if (newCount != totalA) {
            java.lang.System.out.println("MISTMATCH IN SPLIT TOTALS: " + newCount + " != " + totalA);
        }
        return newCounts;
    }

    static public String toBinaryString(int value) {
        return ren.util.Strings.padZeros(Integer.toBinaryString(value), 32);
    }

    static public String toBinaryString(long value) {
        return ren.util.Strings.padZeros(Long.toBinaryString(value), 64);
    }
}
