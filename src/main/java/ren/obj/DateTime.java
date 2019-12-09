package ren.obj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTime extends java.util.Date {

    public DateTime() {
        super();
    }

    public DateTime(java.util.Date date) {
        if (date != null) {
            setTime(date.getTime());
        }
    }

    public DateTime(long time) {
        super(time);
    }

    public DateTime(String dateString) {
        super();
        if (dateString != null) {
            setFromDateString(dateString);
        }
    }

    public boolean setFromDateString(String dateString) {
        if (dateString == null) {
            return false;
        }
        dateString = dateString.trim();
        boolean relativeFlag = false;
        if (dateString.startsWith("-") || dateString.startsWith("+")) {
            relativeFlag = true;
        } else {
            if (dateString.length() <= 3) {
                relativeFlag = true;
            }
        }
        if (relativeFlag) {
            int days = ren.util.Numbers.readIntFromString(dateString);
            offsetDateByDays(days);
            return true;
        }
        String[] formats = new String[]{
            "MM/dd/yy hh:mm:ss aaa z", "MM/dd/yy hh:mm:ss aaa", "MM/dd/yy hh:mm aaa",
            "MM/dd/yy", "MM-dd-yy",
            "MM/dd/yyyy", "MM-dd-yyyy",
            "yyyyMMdd",
            "yyyy-MM-dd",
            "yyyy",
            "d-MMM-yy",
            "yy-MM-dd",
            "MMMMMMMM dd, yyyy",
            "dd-MMM-yyyy",            
            "EEE, dd MMM yyyy hh:mm:ss aaa" //Fri, 12 Aug 2016 20:23:02 GMT
        };
        // FIRST TRY TO FILTER OUT BEST MATCH
        for (int i = 0; i < formats.length; i++) {
            try {
                SimpleDateFormat d = new SimpleDateFormat(formats[i]);
                set(d.parse(dateString));
                if (d.format(this).compareToIgnoreCase(dateString) == 0) {
                    return true;
                }
            } catch (java.text.ParseException e) {
            }
        }
        // IF NOT, GO FOR CLOSEST MATCH
        // FIRST TRY TO FILTER OUT BEST MATCH
        for (int i = 0; i < formats.length; i++) {
            try {
                SimpleDateFormat d = new SimpleDateFormat(formats[i]);
                set(d.parse(dateString));
                return true;
            } catch (java.text.ParseException e) {
            }
        }
        return false;
    }

    public void set(Date date) {
        if (date != null) {
            setTime(date.getTime());
        }
    }

    public DateTime createDateTime(String dateString) {
        if (dateString != null) {
            if (dateString.length() > 0) {
                return new DateTime(dateString);
            }
        }
        return null;
    }

    public DateTime createDateTime() {
        return createDateTime(new Date());
    }

    public DateTime createDateTime(Date date) {
        return new DateTime(date);
    }

    static public String getDisplayNameForCurrentMonth() {
        return new DateTime().getDisplayNameForMonth();
    }

    public String getDisplayNameForMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        return c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
    }

    public String getDisplayShortNameForMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        return c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
    }

    static public DateTime getStartingDateOfCurrentYear() {
        return new DateTime().getStartingDateOfYear();
    }

    public DateTime getStartingDateOfYear() {
        Date date = toDateWithNoTime();
        date = createDateTime(date).getStartingDateOfMonth();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        c.add(Calendar.MONTH, -month);
        return createDateTime(c.getTime());
    }

    static public DateTime getStartingDateOfCurrentMonth() {
        return new DateTime().getStartingDateOfMonth();
    }

    public DateTime getStartingDateOfMonth() {
        Date date = toDateWithNoTime();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_MONTH) - 1;
        c.add(Calendar.DAY_OF_MONTH, -day);
        return createDateTime(c.getTime());
    }

    static public DateTime getStartingDateOfCurrentWeek() {
        return new DateTime().getStartingDateOfWeek();
    }

    public DateTime getStartingDateOfWeek() {
        Date date = toDateWithNoTime();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DAY_OF_MONTH, Calendar.SUNDAY - day);
        return createDateTime(c.getTime());
    }

    static public DateTime getStartingDateOfCurrentDay() {
        return new DateTime().getStartingDateOfDay();
    }

    public DateTime getStartingDateOfDay() {
        Date date = toDateWithNoTime();
        return createDateTime(date);
    }

    /*
     * DATE WITH NO TIME
     */
    static public DateTime getCurrentDateWithNoTime() {
        return new DateTime().toDateWithNoTime();
    }

    static public DateTime getDateWithNoTime(Date date) {
        return new DateTime(date).toDateWithNoTime();
    }

    public DateTime toDateWithNoTime() {
        // STRIP DATE OFF FROM TIME AND SECONDS
        try {
            SimpleDateFormat d = new SimpleDateFormat("MM/dd/yyyy");
            return createDateTime(d.parse(d.format(this)));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String toDateFormatString(String dateFormat) {
        SimpleDateFormat date = new SimpleDateFormat(dateFormat);
        return date.format(this);
    }

    public String toStandardDateString() {
        SimpleDateFormat d = new SimpleDateFormat("MM/dd/yyyy");
        return d.format(this);
    }

    public void setFromStandardDateString(String dateString) {
        try {
            SimpleDateFormat d = new SimpleDateFormat("MM/dd/yyyy");
            set(d.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    static public String getStandardCurrentDateString() {
        return new DateTime().toStandardDateTimeString();
    }

    public String toStandardDateTimeString() {
        SimpleDateFormat d = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aaa");
        return d.format(this);
    }

    public void setFromStandardDateTimeString(String dateString) {
        try {
            SimpleDateFormat d = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aaa");
            set(d.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String toFormalDateTimeOnly() {
        java.text.DateFormat d = java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG, java.util.Locale.ENGLISH);
        java.text.DateFormat t = java.text.DateFormat.getTimeInstance(java.text.DateFormat.LONG, java.util.Locale.ENGLISH);
        String dateTimeString = d.format(this) + " at " + t.format(this);
        return dateTimeString;
    }

    public String toFormalDateOnly() {
        java.text.DateFormat d = java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG, java.util.Locale.ENGLISH);
        String dateString = d.format(this);
        return dateString;
    }

    public String toShortDateAndTime() {
        //SimpleDateFormat d = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aaa z");
        SimpleDateFormat d = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa z");
        String dateString = d.format(this);
        return dateString;
    }

    public String toShortDateOnly() {
        //SimpleDateFormat d = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aaa z");
        SimpleDateFormat d = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = d.format(this);
        return dateString;
    }

    public String toYearOnly() {
        SimpleDateFormat d = new SimpleDateFormat("yyyy");
        String dateString = d.format(this);
        return dateString;
    }

    @Override
    public int getYear() {
        return ren.util.Numbers.readIntFromString(toYearOnly());
    }

    public int getAgeInYears() {
        return new DateTime().getYear() - getYear();
    }

    public String toMonthOnly() {
        SimpleDateFormat d = new SimpleDateFormat("MM");
        String dateString = d.format(this);
        return dateString;
    }

    public String toDayOnly() {
        SimpleDateFormat d = new SimpleDateFormat("dd");
        String dateString = d.format(this);
        return dateString;
    }

    public String toLogDateOnly() {
        SimpleDateFormat d = new SimpleDateFormat("yyyyMMdd");
        String dateString = d.format(this);
        return dateString;
    }

    public String toEncryptedDateOnly() {
        String dateString = toLogDateOnly();
        String s = "";
        for (int i = 0; i < dateString.length(); i++) {
            int c = dateString.charAt(i);
            s += ren.util.Strings.padZeros(c, 3);
        }
        return ren.util.Strings.reverse(s);
    }

    public DateTime fromEncryptedDateOnly(String dateString) {
        dateString = ren.util.Strings.reverse(dateString);
        try {
            String s = "";
            for (int i = 0; i < dateString.length(); i += 3) {
                String c = dateString.substring(i, i + 3);
                int value = ren.util.Numbers.readIntFromString(c);
                char ch = (char) value;
                s += ch;
            }
            return new DateTime(s);
        } catch (Exception e) {
        }
        return null;
    }

    public float getDifferenceInSeconds(DateTime compare) {
        long difference = compare.getTime() - getTime();
        float seconds = difference / 1000F;
        return seconds;
    }

    public float getDifferenceInMinutes(DateTime compare) {
        float hours = getDifferenceInSeconds(compare) / 60F;
        return hours;
    }

    public float getDifferenceInHours(DateTime compare) {
        float hours = getDifferenceInMinutes(compare) / 60F;
        return hours;
    }

    public float getDifferenceInDays(DateTime compare) {
        float hours = getDifferenceInHours(compare) / 24F;
        return hours;
    }

    public float getDifferenceInWeeks(DateTime compare) {
        float hours = getDifferenceInDays(compare) / 7F;
        return hours;
    }

    public float getDifferenceInMonths(DateTime compare) {
        // APPROXIMATE
        float hours = getDifferenceInMonths(compare) / 30F;
        return hours;
    }

    public String getRelativeTimeTitle() {
        return getRelativeTimeTitle(new DateTime());
    }

    public String getRelativeTimeTitle(DateTime compare) {
        long difference = compare.getTime() - getTime();
        if (difference < 0) {
            return "";
        }
        int value = 0;
        String description = "";
        if (getDifferenceInDays(compare) < 8) {
            value = (int) getDifferenceInDays(compare);
            if (value == 1) {
                description = "day ago";
            } else {
                description = "days ago";
            }
        }
        if (getDifferenceInHours(compare) < 24) {
            value = (int) getDifferenceInHours(compare);
            if (value == 1) {
                description = "hour ago";
            } else {
                description = "hours ago";
            }
        }
        if (getDifferenceInMinutes(compare) < 60) {
            value = (int) getDifferenceInMinutes(compare);
            if (value == 1) {
                description = "minute ago";
            } else {
                description = "minutes ago";
            }
        }
        if (getDifferenceInSeconds(compare) < 60) {
            value = (int) getDifferenceInSeconds(compare);
            if (value == 1) {
                description = "second ago";
            } else {
                description = "seconds ago";
            }
        }
        if (description.length() > 0) {
            return "<b>" + value + "</b> " + description;
        } else {
            return "";
        }
    }

    public String toPresentableDateAndTime() {
        String s = getRelativeTimeTitle();
        if (s.length() > 0) {
            return ren.util.Strings.toFormalTitle(s);
        }
        return toFormalDateTimeOnly();
    }

    public String toPresentableShortDateAndTime() {
        String s = getRelativeTimeTitle();
        if (s.length() > 0) {
            return ren.util.Strings.toFormalTitle(s);
        }
        return toShortDateAndTime();
    }

    public void offsetDateByYears(int years) {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.add(Calendar.YEAR, years);
        setTime(c.getTime().getTime());
    }

    public void offsetDateByDays(int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.add(Calendar.DAY_OF_MONTH, days);
        setTime(c.getTime().getTime());
    }

    public void offsetDateByBusinessDays(int days) {
        for (int i = 0; i < Math.abs(days); i++) {
            if (days < 0) {
                offsetDateByDays(-1);
                adjustDateToPreviousBusinessDayIfNecessary();
            } else {
                offsetDateByDays(1);
                adjustDateToNextBusinessDayIfNecessary();
            }
        }
    }

    public void offsetDateByHours(int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.add(Calendar.HOUR, hours);
        setTime(c.getTime().getTime());
    }

    public void offsetDateByMinutes(int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.add(Calendar.MINUTE, minutes);
        setTime(c.getTime().getTime());
    }

    public boolean checkIfBusinessDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        int day = c.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SATURDAY) {
            return false;
        }
        if (day == Calendar.SUNDAY) {
            return false;
        }
        return true;
    }

    public boolean checkIfHoliday() {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (month == 0 && day == 1) {
            return true;
        }
        if (month == 6 && day == 4) {
            return true;
        }
        if (month == 11 && day == 25) {
            return true;
        }
        return false;
    }

    public void adjustDateToNextBusinessDayIfNecessary() {
        if (checkIfHoliday()) {
            offsetDateByDays(1);
        }
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        int day = c.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SATURDAY) {
            offsetDateByDays(2);
            adjustDateToNextBusinessDayIfNecessary();
            return;
        }
        if (day == Calendar.SUNDAY) {
            offsetDateByDays(1);
            adjustDateToNextBusinessDayIfNecessary();
            return;
        }
    }

    public void adjustDateToPreviousBusinessDayIfNecessary() {
        if (checkIfHoliday()) {
            offsetDateByDays(-1);
        }
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        int day = c.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SATURDAY) {
            offsetDateByDays(-1);
            adjustDateToPreviousBusinessDayIfNecessary();
            return;
        }
        if (day == Calendar.SUNDAY) {
            offsetDateByDays(-2);
            adjustDateToPreviousBusinessDayIfNecessary();
            return;
        }
    }
}
