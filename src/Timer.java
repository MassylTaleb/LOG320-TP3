public class Timer {

    public static double timeElapsed;
    private static double timeAtStart;

    public static void startTimer(){

        timeAtStart = System.currentTimeMillis();
    }

    public static void resetTimer(){

        startTimer();
    }

    public static double getTimeElapsed(){

        return (System.currentTimeMillis() - timeAtStart) / 1000;
    }
}
