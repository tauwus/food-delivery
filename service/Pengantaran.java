package service;

import enums.JenisPengantaran;
import java.util.ArrayList;
import java.util.List;

public class Pengantaran {

    public static double hitungOngkir(JenisPengantaran jenis, int jarak, int jam) {
        if (jarak <= 0) {
            jarak = 1;
        }

        int biayaDasar = jenis.getBiayaDasarPerKm();
        double biayaFinalPerKm = biayaDasar;

        switch (jenis) {
            case EXPRESS:
                if (jam >= 6 && jam < 12) {
                    biayaFinalPerKm = Math.max(500, biayaDasar - 500);
                } else if (jam >= 18 && jam < 22) {
                    biayaFinalPerKm = biayaDasar + 500;
                }
                break;
            case REGULER:
                if (jam >= 12 && jam < 18) {
                    biayaFinalPerKm = biayaDasar + 200;
                }
                break;
            case EKONOMIS:
                if (jam < 6 || jam >= 22) {
                    return -1;
                }
                if (jam >= 18 && jam < 22) {
                    biayaFinalPerKm = biayaDasar + 300;
                } else if (jam >= 12 && jam < 18) {
                    biayaFinalPerKm = biayaDasar + 500;
                }
                break;
        }

        if (biayaFinalPerKm < 0) {
            biayaFinalPerKm = 0;
        }

        return biayaFinalPerKm * jarak;
    }

    public static List<JenisPengantaran> getPilihanTersedia(int jam) {
        List<JenisPengantaran> pilihan = new ArrayList<>();
        pilihan.add(JenisPengantaran.EXPRESS);
        pilihan.add(JenisPengantaran.REGULER);

        if (jam >= 6 && jam < 22) {
            pilihan.add(JenisPengantaran.EKONOMIS);
        }
        return pilihan;
    }
}
