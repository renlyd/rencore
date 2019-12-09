package ren.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;

public class App {

    public App() {
    }
    static public boolean debugFlag = true;

    static public void garbageCollect() {
        java.lang.System.gc();
    }

    static public void garbageCollect(boolean override) {
        if (override) {
            java.lang.System.gc();
        } else if (false) {
            garbageCollect();
        }
    }
    static private long time = 0;

    static public long getCurrentTime() {
        return java.lang.System.currentTimeMillis();
    }

    static public long getTimer() {
        return java.lang.System.currentTimeMillis() - time;
    }

    static public long startTimer() {
        time = java.lang.System.currentTimeMillis();
        return time;
    }

    static public long stopTimer() {
        return (java.lang.System.currentTimeMillis() - time);
    }

    static public void markAndPrintTime() {
        printTime();
        startTimer();
    }

    static public void printTime() {
        if (debugFlag) {
            long milliseconds = (long) stopTimer();
            int totalSeconds = (int) (milliseconds / 1000F);
            int minutes = (int) (totalSeconds / 60);
            int secondsRemaining = totalSeconds - (minutes * 60);
            java.lang.System.out.println(String.format(java.util.Locale.ENGLISH, "TIME: %d minutes and %d seconds          (%d total seconds and %d milliseconds)", minutes, secondsRemaining, totalSeconds, milliseconds));
        }
    }

    static public String convertToSeconds(long time) {
        return String.format(java.util.Locale.ENGLISH, "%f", time / 1000F);
    }

    static public String getMemoryUsageString() {
        Runtime rt = Runtime.getRuntime();
        String s = "Total Memory = " + rt.totalMemory() + "/ Free Memory = " + rt.freeMemory();
        return s;
    }

    public static Properties readEnvironmentVariables() throws Throwable {
        Process p = null;
        Properties envVars = new Properties();
        Runtime r = Runtime.getRuntime();
        String OS = java.lang.System.getProperty("os.name").toLowerCase();
        if (OS.indexOf("windows 9") > - 1) {
            p = r.exec("command.com /c set");
        } else if ((OS.indexOf("nt") > - 1) || (OS.indexOf("windows 2000") > - 1) || (OS.indexOf("windows 2003") > - 1) || (OS.indexOf("windows") > - 1) // FOR WINDOWS VISTA
                || (OS.indexOf("windows xp") > - 1)) {
            // xp fix !
            p = r.exec("cmd.exe /c set");
        } else {
            // our last hope, we assume Unix
            p = r.exec("env");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            int idx = line.indexOf('=');
            if (idx < 0) {
                continue;
            }
            String key = line.substring(0, idx);
            String value = line.substring(idx + 1);
            envVars.setProperty(key, value);
        }
        return envVars;
    }
    static public int eventDelay = 100;

    static public void doEvents() {
        sleepQuietly(100);
    }

    static public void sleepQuietly(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (java.lang.InterruptedException ee) {
        }
    }

    static public void checkForThreadInterruption() throws java.lang.InterruptedException {
        if (Thread.interrupted()) {
            throw new java.lang.InterruptedException();
        }
    }

    static public boolean checkForThreadInterruptionSafely() {
        if (Thread.interrupted()) {
            return true;
        }
        return false;
    }

    static public void exit() {
        exit(true);
    }

    static public void exit(boolean quietFlag) {
        Iterator it = Thread.getAllStackTraces().keySet().iterator();
        while (it.hasNext()) {
            Thread value = (Thread) it.next();
            if (!quietFlag) {
                java.lang.System.out.println(" Exiting: " + value);
            }
            try {
                value.interrupt();
            } catch (Exception e) {
            }
        }
        java.lang.System.exit(0);
    }
}
