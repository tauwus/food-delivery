package service;

import java.time.ZonedDateTime;
import java.time.ZoneId;

public class Waktu {

    private volatile int jam;
    private volatile int menit;
    private volatile boolean running = true;
    private Thread clockThread;

    public Waktu() {
        ZonedDateTime waktuJakarta = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
        this.jam = waktuJakarta.getHour();
        this.menit = waktuJakarta.getMinute();
        startClock();
    }

    public int getJam() {
        return jam;
    }

    public int getMenit() {
        return menit;
    }

    private void startClock() {
        clockThread = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(1000);

                    int currentMinute = this.menit;
                    int currentHour = this.jam;

                    currentMinute += 5;

                    if (currentMinute >= 60) {
                        currentHour += currentMinute / 60;
                        currentMinute %= 60;
                    }
                    if (currentHour >= 24) {
                        currentHour %= 24;
                    }

                    this.menit = currentMinute;
                    this.jam = currentHour;

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    running = false;
                }
            }
        });
        clockThread.setDaemon(true);
        clockThread.start();
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", jam, menit);
    }
}
