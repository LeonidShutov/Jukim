package telran.jukim.controller;

import telran.jukim.model.Juke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JukimApp {

    private static final int DISTANCE_DEFAULT = 1000;
    private static final int N_JUKIM_DEFAULT = 100;
    private static final int MIN_SLEEP_DEFAULT = 1;
    private static final int MAX_SLEEP_DEFAULT = 5;
    private static int distance = DISTANCE_DEFAULT;
    private static int nJukim = N_JUKIM_DEFAULT;
    private static int minSleep = MIN_SLEEP_DEFAULT;
    private static int maxSleep = MAX_SLEEP_DEFAULT;

    public static void main(String[] args) throws InterruptedException {

        if (args.length > 0) {
            try {
                if (args[0].equalsIgnoreCase("console1"))
                    getArgsFromConsole();
                else
                    getArgsFromFile(args[0]);

            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            }
        }
        Juke[] jukes = new Juke[nJukim];
        Thread[] threads = new Thread[nJukim];
        for (int i = 0; i < nJukim; i++) {
            jukes[i] = new Juke(i, distance, minSleep, maxSleep);
            threads[i] = new Thread(jukes[i]);
            threads[i].start();
        }
        for (int i = 0; i < nJukim; i++)
            threads[i].join();

        Juke.printTable();
    }

    private static void getArgsFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String str = br.readLine();
        String[] args = str.split(" ");
        nJukim = Integer.parseInt(args[0]);
        distance = Integer.parseInt(args[1]);
        minSleep = Integer.parseInt(args[2]);
        maxSleep = Integer.parseInt(args[3]);
    }

    private static void getArgsFromConsole() throws NumberFormatException,
            IOException {
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(ConsoleMessages.CONSOLE_NUMBER_JUKIM);
        nJukim = Integer.parseInt(br.readLine());
        System.out.println(ConsoleMessages.CONSOLE_DISTANCE);
        distance = Integer.parseInt(br.readLine());
        System.out.println(ConsoleMessages.CONSOLE_MIN_SLEEP);
        minSleep = Integer.parseInt(br.readLine());
        System.out.println(ConsoleMessages.CONSOLE_MAX_SLEEP);
        maxSleep = Integer.parseInt(br.readLine());
    }
}
