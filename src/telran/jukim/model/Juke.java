package telran.jukim.model;

import java.util.concurrent.CopyOnWriteArrayList;

public class Juke implements Runnable {

    private int distance;
    private int minSleep;
    private int maxSleep;
    private int number;
    private long timeExec;
    private long timeFinish;
    private static final Object mutex = new Object();
    private static CopyOnWriteArrayList<Juke> table = new CopyOnWriteArrayList<Juke>();

    public Juke(int number, int distance, int minSleep, int maxSleep) {
        this.number = number;
        this.distance = distance;
        this.minSleep = minSleep;
        this.maxSleep = maxSleep;
    }

    @Override
    public void run() {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < distance; i++) {
            System.out.println(number);
            try {
                Thread.sleep(getSleepTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (mutex) {
            timeFinish = System.currentTimeMillis();
            timeExec = timeFinish - timeStart;
            // winner.setWinner(number);
        }
        fillTable();
    }

    private void fillTable() {
        try {
            Thread.sleep(maxSleep);
            table.add(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private long getTimeExec() {
        return timeExec;
    }

    private long getTimeFinish() {
        return timeFinish;
    }

    private int getSleepTime() {
        return (int) (Math.random() * (maxSleep - minSleep) + minSleep);
    }

    public static void printTable() {
        for (Juke juke : table)
            System.out.println("Juke number: " + juke.number + " finished at "
                    + juke.getTimeFinish() + " runs for " + juke.getTimeExec()
                    + " ms.");

        System.out.println("Winner is " + table.get(0).number);
    }
}
