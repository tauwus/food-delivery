
import exceptions.HandlerInputTidakValid;
import enums.JenisPengantaran;
import model.*;
import service.*;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.InputMismatchException;

public class GoFoodApp {

    static Scanner scan;
    static Pengguna pengguna;
    static Restoran[] daftarRestoran = {
        new RestoranCepatSaji("McDonald's"),
        new RestoranCepatSaji("Burger King"),
        new RestoranCepatSaji("KFC"),
        new RestoranCepatSaji("Gacoan"),
        new TokoDessert("Mixue"),
        new TokoDessert("Retawu Deli"),
        new TokoDessert("Beli Kopi")
    };
    static List<Pesanan> riwayatPesanan;
    static Waktu waktuAplikasi;

    public static void main(String[] args) {
        scan = new Scanner(System.in);
        riwayatPesanan = new ArrayList<>();
        pengguna = null;
        waktuAplikasi = new Waktu();

        System.out.println("\n");
        System.out.println("*================================*");
        System.out.println("|    Selamat Datang di GoFood!   |");
        System.out.println("*================================*");

        registrasiPengguna();

        int pilihan = -1;
        do {
            tampilkanMenuUtama();
            try {
                System.out.print("Pilihan: ");
                pilihan = scan.nextInt();
                scan.nextLine();

                prosesPilihanMenuUtama(pilihan);
            } catch (InputMismatchException e) {
                System.err.println("\n(!) Error: Input tidak valid. Harap masukkan angka.");
                scan.nextLine();
                pilihan = -1;
            } catch (HandlerInputTidakValid e) {
                System.err.println("\n(!) Error: " + e.getMessage() + " Silakan coba lagi.");
            }

            if (pilihan != 5 && pilihan >= 1 && pilihan <= 4) {
                System.out.println("\nTekan Enter untuk kembali ke menu utama...");
            }

        } while (pilihan != 5);

        System.out.println("\nTerima kasih telah menggunakan GoFood!");
        scan.close();
    }

    public static void registrasiPengguna() {
        System.out.println("\n--- Registrasi Akun Baru ---");
        String user, no, alamat;
        do {
            System.out.print("Masukkan username: ");
            user = scan.nextLine().trim();
            if (user.isEmpty()) {
                System.out.println("(!) Username tidak boleh kosong.");
            }
        } while (user.isEmpty());
        do {
            System.out.print("Masukkan nomor handphone: ");
            no = scan.nextLine().trim();
            if (no.isEmpty() || !no.matches("\\d+")) {
                System.out.println("(!) Nomor HP tidak valid (hanya angka).");
            }
        } while (no.isEmpty() || !no.matches("\\d+"));
        do {
            System.out.print("Masukkan alamat: ");
            alamat = scan.nextLine().trim();
            if (alamat.isEmpty()) {
                System.out.println("(!) Alamat tidak boleh kosong.");
            }
        } while (alamat.isEmpty());
        pengguna = new Pengguna(user, no, alamat);
        System.out.println("Registrasi berhasil! Selamat datang, " + pengguna.getUsername() + "!");
        pengguna.tambahSaldo(50000);
        System.out.println("Anda mendapatkan saldo awal Rp 50.000!");
    }

    public static void tampilkanMenuUtama() {
        System.out.println("\n=========================");
        System.out.println("    GoFood - Menu Utama");
        System.out.println("=========================");
        System.out.println("Waktu Saat Ini: " + waktuAplikasi);
        System.out.println("Halo, " + pengguna.getUsername() + "!");
        System.out.printf("Saldo Anda    : Rp %,.0f\n", pengguna.getSaldo());
        System.out.println("-------------------------");
        System.out.println("1. Lihat Profil & Update Akun");
        System.out.println("2. Order Makanan");
        System.out.println("3. Top Up Saldo");
        System.out.println("4. Lihat Riwayat Pesanan");
        System.out.println("5. Keluar");
        System.out.println("=========================");
    }

    public static void prosesPilihanMenuUtama(int pilihan) throws HandlerInputTidakValid {
        switch (pilihan) {
            case 1:
                lihatProfil();
                break;
            case 2:
                buatPesanan();
                break;
            case 3:
                prosesTopUpSaldo();
                break;
            case 4:
                tampilkanRiwayatPesanan();
                break;
            case 5:
                break;
        }
    }

    public static void lihatProfil() {
        pengguna.tampilkanDetail();
        System.out.print("Ingin memperbarui profil? (ya/tidak): ");
        String jawaban = scan.nextLine();
        if (jawaban.equalsIgnoreCase("ya")) {
            pengguna.perbaruiProfil(scan);
        } else if (!jawaban.equalsIgnoreCase("tidak")) {
            System.out.println("Pilihan tidak valid, kembali ke menu.");
        }
    }

    public static void prosesTopUpSaldo() throws HandlerInputTidakValid {
        System.out.println("\n--- Top Up Saldo ---");
        System.out.println("Pilih nominal top up:");
        System.out.println("1. Rp 10.000");
        System.out.println("2. Rp 20.000");
        System.out.println("3. Rp 50.000");
        System.out.println("4. Rp 100.000");
        System.out.println("0. Batal");
        System.out.println("--------------------");

        int pilihanTopUp;
        System.out.print("Pilihan nominal: ");
        try {
            pilihanTopUp = scan.nextInt();
            scan.nextLine();
        } catch (InputMismatchException e) {
            scan.nextLine();
            throw new HandlerInputTidakValid("Input pilihan nominal tidak valid (harus angka).");
        }

        double jumlahTopUp = 0;
        switch (pilihanTopUp) {
            case 1:
                jumlahTopUp = 10000;
                break;
            case 2:
                jumlahTopUp = 20000;
                break;
            case 3:
                jumlahTopUp = 50000;
                break;
            case 4:
                jumlahTopUp = 100000;
                break;
            case 0:
                System.out.println("Top up dibatalkan.");
                return;
            default:
                throw new HandlerInputTidakValid("Pilihan nominal tidak valid.");
        }
        pengguna.tambahSaldo(jumlahTopUp);
    }

    public static void buatPesanan() throws HandlerInputTidakValid {
        System.out.println("\n--- Pilih Restoran ---");
        if (daftarRestoran == null || daftarRestoran.length == 0) {
            System.out.println("(!) Maaf, belum ada restoran yang terdaftar.");
            return;
        }
        List<Restoran> restoUrut = new ArrayList<>();
        for (Restoran r : daftarRestoran) {
            if (r.isAdaPromo()) {
                restoUrut.add(r);
            }
        }
        for (Restoran r : daftarRestoran) {
            if (!r.isAdaPromo()) {
                restoUrut.add(r);
            }
        }

        System.out.println("Daftar Restoran (Promo didahulukan):");
        for (int i = 0; i < restoUrut.size(); i++) {
            restoUrut.get(i).tampilkanDetail(i + 1);
        }
        System.out.println("0. Kembali ke Menu Utama");
        System.out.println("----------------------");

        int pilihanResto;
        Restoran restoranPilihan = null;

        System.out.print("Pilih nomor restoran: ");
        try {
            pilihanResto = scan.nextInt();
            scan.nextLine();
        } catch (InputMismatchException e) {
            scan.nextLine();
            throw new HandlerInputTidakValid("Input pilihan restoran tidak valid (harus angka).");
        }

        if (pilihanResto == 0) {
            System.out.println("Pemilihan restoran dibatalkan.");
            return;
        }
        if (pilihanResto > 0 && pilihanResto <= restoUrut.size()) {
            restoranPilihan = restoUrut.get(pilihanResto - 1);
        } else {
            throw new HandlerInputTidakValid("Pilihan restoran tidak valid.");
        }

        Pesanan pesananBaru = new Pesanan(pengguna, restoranPilihan);
        pilihMenuUntukPesanan(pesananBaru);

        if (pesananBaru.getTotalAkhir() == 0) {
            System.out.println("Keranjang kosong, pesanan dibatalkan.");
            return;
        }

        JenisPengantaran pengantaranPilihan = pilihPengantaran(restoranPilihan.getJarak());
        if (pengantaranPilihan == null) {
            System.out.println("Pengantaran tidak dipilih, pesanan dibatalkan.");
            return;
        }
        pesananBaru.setJenisPengantaran(pengantaranPilihan);

        double ongkir = Pengantaran.hitungOngkir(pengantaranPilihan, restoranPilihan.getJarak(), waktuAplikasi.getJam());
        if (ongkir < 0) {
            System.out.println("(!) Opsi pengantaran tdk tersedia. Pesanan dibatalkan.");
            return;
        }
        pesananBaru.setBiayaPengantaran(ongkir);
        pesananBaru.hitungTotalAkhir();
        konfirmasiDanBayarPesanan(pesananBaru);
    }

    public static void pilihMenuUntukPesanan(Pesanan pesanan) throws HandlerInputTidakValid {
        Restoran restoran = pesanan.getRestoran();
        List<Map.Entry<String, Double>> menuList = new ArrayList<>(restoran.getDaftarMenuMap().entrySet());
        int pilihanMenu = -1;
        int jumlah;

        System.out.println("\n--- Pilih Menu dari " + restoran.getNama() + " ---");
        do {
            restoran.tampilkanSemuaMenu();
            System.out.println("0. Selesai memilih menu & lanjut ke pengantaran");
            System.out.println("-------------------------------------------------");

            System.out.print("Pilih nomor menu (0 untuk selesai): ");
            try {
                pilihanMenu = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                scan.nextLine();
                System.err.println("(!) Pilihan menu tidak valid (harus angka). Coba lagi.");
                continue;
            }

            if (pilihanMenu > 0 && pilihanMenu <= menuList.size()) {
                Map.Entry<String, Double> menuEntry = menuList.get(pilihanMenu - 1);
                String namaMenuPilihan = menuEntry.getKey();
                double hargaMenuPilihan = menuEntry.getValue();

                System.out.print("Masukkan jumlah '" + namaMenuPilihan + "': ");
                try {
                    jumlah = scan.nextInt();
                    scan.nextLine();
                } catch (InputMismatchException e) {
                    scan.nextLine();
                    System.err.println("(!) Jumlah tidak valid (harus angka). Item tidak ditambahkan.");
                    continue;
                }

                if (jumlah > 0) {
                    pesanan.tambahItem(namaMenuPilihan, hargaMenuPilihan, jumlah);
                    pesanan.tampilkanDetail();
                } else {
                    System.out.println("(!) Jumlah harus > 0.");
                }
            } else if (pilihanMenu != 0) {
                System.err.println("(!) Pilihan menu tidak ada di daftar.");
            }
        } while (pilihanMenu != 0);
    }

    public static JenisPengantaran pilihPengantaran(int jarak) throws HandlerInputTidakValid {
        List<JenisPengantaran> opsiTersedia = Pengantaran.getPilihanTersedia(waktuAplikasi.getJam());
        List<JenisPengantaran> opsiValidDitampilkan = new ArrayList<>();

        System.out.println("\n== Pilih Jenis Pengantaran ==");
        int nomorOpsi = 1;
        for (JenisPengantaran jenis : opsiTersedia) {
            double ongkir = Pengantaran.hitungOngkir(jenis, jarak, waktuAplikasi.getJam());
            if (ongkir >= 0) {
                System.out.printf("%d. %s - Rp %,.0f\n", nomorOpsi++, jenis.getNama(), ongkir);
                opsiValidDitampilkan.add(jenis);
            }
        }
        System.out.println("0. Batal Pesanan");
        System.out.println("==============================");

        int pilihanAntar;
        System.out.print("Pilih nomor pengantaran: ");
        try {
            pilihanAntar = scan.nextInt();
            scan.nextLine();
        } catch (InputMismatchException e) {
            scan.nextLine();
            throw new HandlerInputTidakValid("Input pilihan pengantaran tidak valid (harus angka).");
        }

        if (pilihanAntar == 0) {
            System.out.println("Pemilihan pengantaran dibatalkan.");
            return null;
        }
        if (pilihanAntar > 0 && pilihanAntar <= opsiValidDitampilkan.size()) {
            return opsiValidDitampilkan.get(pilihanAntar - 1);
        } else {
            throw new HandlerInputTidakValid("Pilihan pengantaran tidak valid.");
        }
    }

    public static void konfirmasiDanBayarPesanan(Pesanan pesanan) {
        System.out.println("\n=== Konfirmasi Pesanan ===");
        pesanan.tampilkanDetail();
        System.out.printf("Saldo Anda saat ini : Rp %,.0f\n", pengguna.getSaldo());
        System.out.println("==========================");
        if (!pesanan.isSaldoCukup()) {
            System.out.printf("(!) Saldo Anda (Rp %,.0f) tdk cukup utk bayar Rp %,.0f.\n", pengguna.getSaldo(), pesanan.getTotalAkhir());
            System.out.println("Pesanan dibatalkan.");
            return;
        }
        System.out.print("Konfirmasi pesanan dan bayar? (ya/tidak): ");
        String konfirmasi = scan.nextLine().trim();
        if (konfirmasi.equalsIgnoreCase("ya")) {
            if (pengguna.kurangiSaldo(pesanan.getTotalAkhir())) {
                System.out.println("\n(+) Pembayaran berhasil!");
                System.out.printf("Saldo Anda sekarang: Rp %,.0f\n", pengguna.getSaldo());
                System.out.println("Pesanan #" + pesanan.getIdPesanan() + " berhasil dibuat & akan segera tiba!");
                riwayatPesanan.add(pesanan);
            } else {
                System.err.println("(!) Error: Gagal memproses pembayaran.");
                System.out.println("Pesanan dibatalkan.");
            }
        } else {
            System.out.println("Pesanan dibatalkan.");
        }
    }

    public static void tampilkanRiwayatPesanan() {
        System.out.println("\n--- Riwayat Pesanan Selesai ---");
        riwayatPesanan.sort(Comparator.comparingInt(Pesanan::getIdPesanan).reversed());
        for (Pesanan p : riwayatPesanan) {
            p.tampilkanRingkasanRiwayat();
        }
        System.out.print("\nMasukkan ID Pesanan untuk lihat detail (0 untuk kembali): #");
        int id;
        try {
            id = scan.nextInt();
            scan.nextLine();
        } catch (InputMismatchException e) {
            scan.nextLine();
            System.out.println("Input ID tidak valid (harus angka).");
            id = -1;
        }

        if (id > 0) {
            boolean found = false;
            for (Pesanan p : riwayatPesanan) {
                if (p.getIdPesanan() == id) {
                    p.tampilkanDetail();
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("(!) Pesanan ID #" + id + " tidak ditemukan.");
            }
        } else if (id != 0) {
        }

        System.out.println("-----------------------------");
    }
}
