package service;

import java.time.ZonedDateTime;
import java.time.ZoneId;

public class Waktu {

    private volatile int jam;
    private volatile int menit;
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
            while (true) {
                try {
                    Thread.sleep(1000);

                    int hitungMenit = this.menit;
                    int hitungJam = this.jam;

                    hitungMenit += 5;

                    if (hitungMenit >= 60) {
                        hitungJam += hitungMenit / 60;
                        hitungMenit %= 60;
                    }
                    if (hitungJam >= 24) {
                        hitungJam %= 24;
                    }

                    this.menit = hitungMenit;
                    this.jam = hitungJam;

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
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
