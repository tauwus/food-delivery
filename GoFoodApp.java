
import exceptions.HandlerInputTidakValid;
import enums.JenisPengantaran;
import model.*;
import service.*;
import java.util.*;

public class GoFoodApp {

    // Variabel instance untuk menyimpan state aplikasi
    private Scanner scan;
    private Pengguna pengguna;
    private List<Restoran> daftarRestoran;
    private List<Pesanan> riwayatPesanan;
    private Waktu waktuAplikasi;

    public GoFoodApp() {
        scan = new Scanner(System.in);
        daftarRestoran = new ArrayList<>();
        riwayatPesanan = new ArrayList<>();
        waktuAplikasi = new Waktu(); // Objek waktu dibuat dan thread jam dimulai
        inisialisasiDataRestoran(); // Isi daftar restoran
    }

    public static void main(String[] args) {
        GoFoodApp app = new GoFoodApp();
        app.run();
    }

    public void run() {
        System.out.println("\n+---------------------------------+");
        System.out.println("|   Selamat Datang di GoFood!   |");
        System.out.println("+---------------------------------+");

        registrasiPengguna(); // Lakukan registrasi di awal

        int pilihan;
        do {
            tampilkanMenuUtama();
            pilihan = getInputInteger("Pilihan Anda: ");

            try {
                prosesPilihanMenuUtama(pilihan);
            } catch (HandlerInputTidakValid e) {
                System.err.println("\n(!) Error: " + e.getMessage() + " Silakan coba lagi.");
            } catch (Exception e) { // Menangkap error tak terduga lain
                System.err.println("\n(!) Terjadi kesalahan tak terduga: " + e.getMessage());
                // e.printStackTrace(); // Uncomment untuk debug jika perlu
            }

            // Beri jeda sebelum kembali ke menu, kecuali saat keluar
            if (pilihan != 5) {
                System.out.println("\nTekan Enter untuk kembali ke menu utama...");
                scan.nextLine(); // Menunggu user menekan Enter
            }

        } while (pilihan != 5); // 5 adalah pilihan Keluar

        System.out.println("\nTerima kasih telah menggunakan GoFood!");
        scan.close(); // Tutup Scanner saat aplikasi berakhir
        // waktuAplikasi.stop(); // Opsional: stop thread waktu jika perlu
    }

    private void inisialisasiDataRestoran() {
        daftarRestoran.add(new RestoranCepatSaji("McDonald's", 2, true, "Sedang"));
        daftarRestoran.add(new RestoranCepatSaji("Burger King", 3, false, "Sedang"));
        daftarRestoran.add(new RestoranCepatSaji("KFC", 4, false, "Sedang"));
        daftarRestoran.add(new RestoranCepatSaji("Gacoan", 1, true, "Murah"));
        daftarRestoran.add(new TokoDessert("Mixue", 1, true, "Murah"));
        daftarRestoran.add(new TokoDessert("Retawu Deli", 5, false, "Mahal"));
        daftarRestoran.add(new TokoDessert("Beli Kopi", 2, false, "Sedang"));
    }

    private void registrasiPengguna() {
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

        this.pengguna = new Pengguna(user, no, alamat); // Buat objek Pengguna
        System.out.println("Registrasi berhasil! Selamat datang, " + this.pengguna.getUsername() + "!");
        // Beri saldo awal untuk memudahkan testing
        this.pengguna.tambahSaldo(50000);
        System.out.println("Anda mendapatkan saldo awal Rp 50.000!");
    }

    /**
     * Menampilkan menu utama aplikasi.
     */
    private void tampilkanMenuUtama() {
        System.out.println("\n=========================");
        System.out.println("    GoFood - Menu Utama");
        System.out.println("=========================");
        // Tampilkan informasi penting: waktu, user, saldo
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

    private void prosesPilihanMenuUtama(int pilihan) throws HandlerInputTidakValid {
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
                break; // Keluar (ditangani di loop run())
            default:
                throw new HandlerInputTidakValid("Pilihan menu tidak valid.");
        }
    }

    // --- Fitur Pengguna ---
    /**
     * Menampilkan profil pengguna dan opsi update.
     */
    private void lihatProfil() {
        this.pengguna.tampilkanDetail();
        System.out.print("Ingin memperbarui profil? (ya/tidak): ");
        String jawaban = scan.nextLine().trim();
        if (jawaban.equalsIgnoreCase("ya")) {
            this.pengguna.updateProfil(scan);
        } else if (!jawaban.equalsIgnoreCase("tidak")) {
            System.out.println("Pilihan tidak valid, kembali ke menu.");
        }
    }

    /**
     * Menjalankan proses top up saldo pengguna.
     */
    private void prosesTopUpSaldo() throws HandlerInputTidakValid {
        System.out.println("\n--- Top Up Saldo ---");
        System.out.println("Pilih nominal top up:");
        System.out.println("1. Rp 10.000");
        System.out.println("2. Rp 20.000");
        System.out.println("3. Rp 50.000");
        System.out.println("4. Rp 100.000");
        System.out.println("0. Batal");
        System.out.println("--------------------");

        int pilihan = getInputInteger("Pilihan nominal: ");
        double jumlahTopUp = 0;

        switch (pilihan) {
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
                return; // Kembali
            default:
                throw new HandlerInputTidakValid("Pilihan nominal tidak valid.");
        }
        this.pengguna.tambahSaldo(jumlahTopUp); // Panggil method tambah saldo
    }

    // --- Fitur Pemesanan ---
    /**
     * Mengelola alur pembuatan pesanan baru.
     */
    private void buatPesanan() throws HandlerInputTidakValid {
        // 1. Pilih Restoran
        Restoran restoranPilihan = pilihRestoran();
        if (restoranPilihan == null) {
            return; // User batal memilih
        }
        // 2. Buat objek Pesanan baru
        Pesanan pesananBaru = new Pesanan(this.pengguna, restoranPilihan);

        // 3. Pilih Menu (bisa multiple)
        pilihMenuUntukPesanan(pesananBaru);
        // Cek apakah ada item valid yang ditambahkan
        if (pesananBaru.getTotalAkhir() <= 0 && pesananBaru.getBiayaPengantaran() == 0) {
            System.out.println("Keranjang masih kosong, pesanan dibatalkan.");
            return;
        }

        // 4. Pilih Jenis Pengantaran
        JenisPengantaran pengantaranPilihan = pilihPengantaran(restoranPilihan.getJarak());
        if (pengantaranPilihan == null) {
            System.out.println("Pengantaran tidak dipilih, pesanan dibatalkan.");
            return;
        }
        pesananBaru.setJenisPengantaran(pengantaranPilihan);

        // Hitung ongkir berdasarkan pilihan
        double ongkir = Pengantaran.hitungOngkir(pengantaranPilihan, restoranPilihan.getJarak(), waktuAplikasi.getJam());
        if (ongkir < 0) { // Jika ongkir -1 (tidak tersedia)
            System.out.println("(!) Opsi pengantaran '" + pengantaranPilihan.getNama() + "' tidak tersedia pada jam ini. Pesanan dibatalkan.");
            return;
        }
        pesananBaru.setBiayaPengantaran(ongkir);
        pesananBaru.hitungTotalAkhir(); // Hitung ulang total setelah ongkir

        // 5. Konfirmasi dan Proses Pembayaran (Saldo)
        konfirmasiDanBayarPesanan(pesananBaru);
    }

    /**
     * Menampilkan daftar restoran dan meminta pilihan user.
     */
    private Restoran pilihRestoran() throws HandlerInputTidakValid {
        System.out.println("\n--- Pilih Restoran ---");
        // Sorting sederhana: dahulukan yang promo
        List<Restoran> restoPromo = new ArrayList<>();
        List<Restoran> restoBiasa = new ArrayList<>();
        for (Restoran r : this.daftarRestoran) {
            if (r.isAdaPromo()) {
                restoPromo.add(r);
            } else {
                restoBiasa.add(r);
            }
        }
        List<Restoran> restoUrut = new ArrayList<>(restoPromo); // Mulai dengan yg promo
        restoUrut.addAll(restoBiasa); // Tambahkan sisanya

        if (restoUrut.isEmpty()) {
            System.out.println("(!) Maaf, belum ada restoran yang terdaftar.");
            return null;
        }

        System.out.println("Daftar Restoran (Promo didahulukan):");
        for (int i = 0; i < restoUrut.size(); i++) {
            restoUrut.get(i).tampilkanDetail(i + 1); // Method ini sudah menampilkan kategori harga
        }
        System.out.println("0. Kembali ke Menu Utama");
        System.out.println("----------------------");

        int pilihan = getInputInteger("Pilih nomor restoran: ");
        if (pilihan == 0) {
            System.out.println("Pemilihan restoran dibatalkan.");
            return null;
        }
        // Validasi pilihan
        if (pilihan > 0 && pilihan <= restoUrut.size()) {
            return restoUrut.get(pilihan - 1); // Kembalikan restoran yang dipilih
        } else {
            throw new HandlerInputTidakValid("Pilihan restoran tidak valid.");
        }
    }

    /**
     * Memproses pemilihan menu untuk pesanan yang sedang dibuat.
     */
    private void pilihMenuUntukPesanan(Pesanan pesanan) throws HandlerInputTidakValid {
        Restoran restoran = pesanan.getRestoran();
        // Ambil daftar menu sebagai List of Map Entry untuk memudahkan pemilihan by index
        List<Map.Entry<String, Double>> menuList = new ArrayList<>(restoran.getDaftarMenuMap().entrySet());

        int pilihanMenu;
        do {
            restoran.tampilkanSemuaMenu(); // Tampilkan menu restoran saat ini
            System.out.println("0. Selesai memilih menu & lanjut ke pengantaran");
            System.out.println("-------------------------------------------------");

            pilihanMenu = getInputInteger("Pilih nomor menu (0 untuk selesai): ");

            if (pilihanMenu > 0 && pilihanMenu <= menuList.size()) {
                // Ambil detail menu yang dipilih
                Map.Entry<String, Double> menuEntry = menuList.get(pilihanMenu - 1);
                String namaMenuPilihan = menuEntry.getKey();
                double hargaMenuPilihan = menuEntry.getValue();

                int jumlah = getInputInteger("Masukkan jumlah '" + namaMenuPilihan + "': ");
                if (jumlah > 0) {
                    // Tambahkan item ke objek pesanan
                    pesanan.tambahItem(namaMenuPilihan, hargaMenuPilihan, jumlah);
                    pesanan.tampilkanDetail(); // Tampilkan draft keranjang sementara
                } else {
                    System.out.println("(!) Jumlah harus lebih dari 0.");
                }
            } else if (pilihanMenu != 0) {
                // Jangan lempar exception, biarkan user coba lagi
                System.err.println("(!) Pilihan menu tidak valid.");
            }
        } while (pilihanMenu != 0); // Loop sampai user memilih 0
    }

    /**
     * Menampilkan opsi pengantaran dan meminta pilihan user.
     */
    private JenisPengantaran pilihPengantaran(int jarak) throws HandlerInputTidakValid {
        // Dapatkan opsi yang mungkin tersedia berdasarkan jam
        List<JenisPengantaran> opsiTersedia = Pengantaran.getPilihanTersedia(waktuAplikasi.getJam());
        List<JenisPengantaran> opsiValidDitampilkan = new ArrayList<>(); // Simpan yang benar2 bisa dipilih

        if (opsiTersedia.isEmpty()) {
            System.out.println("(!) Maaf, tidak ada opsi pengantaran yang tersedia saat ini.");
            return null;
        }

        // Tampilkan opsi beserta ongkirnya (jika valid)
        System.out.println("\n== Pilih Jenis Pengantaran ==");
        int nomorOpsi = 1;
        for (JenisPengantaran jenis : opsiTersedia) {
            double ongkir = Pengantaran.hitungOngkir(jenis, jarak, waktuAplikasi.getJam());
            if (ongkir >= 0) { // Hanya tampilkan jika ongkir valid (>= 0)
                System.out.printf("%d. %s - Rp %,.0f\n", nomorOpsi++, jenis.getNama(), ongkir);
                opsiValidDitampilkan.add(jenis); // Tambahkan ke list yang bisa dipilih
            }
        }
        System.out.println("0. Batal Pesanan");
        System.out.println("==============================");

        // Cek apakah ada opsi valid yang bisa ditampilkan
        if (opsiValidDitampilkan.isEmpty()) {
            System.out.println("(!) Tidak ada opsi pengantaran yang valid pada jam ini. Pesanan dibatalkan.");
            return null;
        }

        int pilihan = getInputInteger("Pilih nomor pengantaran: ");
        if (pilihan == 0) {
            System.out.println("Pemilihan pengantaran dibatalkan.");
            return null;
        }
        // Validasi pilihan berdasarkan list yang ditampilkan
        if (pilihan > 0 && pilihan <= opsiValidDitampilkan.size()) {
            return opsiValidDitampilkan.get(pilihan - 1); // Kembalikan pilihan yang valid
        } else {
            throw new HandlerInputTidakValid("Pilihan pengantaran tidak valid.");
        }
    }

    /**
     * Menampilkan draft akhir, cek saldo, konfirmasi, dan proses pembayaran.
     */
    private void konfirmasiDanBayarPesanan(Pesanan pesanan) throws HandlerInputTidakValid {
        System.out.println("\n=== Konfirmasi Pesanan ===");
        pesanan.tampilkanDetail(); // Tampilkan detail final sebelum bayar
        System.out.printf("Saldo Anda saat ini : Rp %,.0f\n", pengguna.getSaldo());
        System.out.println("==========================");

        // 1. Cek Saldo
        if (!pesanan.isSaldoCukup()) {
            System.out.printf("(!) Maaf, saldo Anda (Rp %,.0f) tidak cukup untuk membayar pesanan ini (Rp %,.0f).\n",
                    pengguna.getSaldo(), pesanan.getTotalAkhir());
            System.out.println("Silakan top up saldo terlebih dahulu.");
            System.out.println("Pesanan dibatalkan.");
            return; // Batalkan proses
        }

        // 2. Minta Konfirmasi
        System.out.print("Konfirmasi pesanan dan bayar? (ya/tidak): ");
        String konfirmasi = scan.nextLine().trim();

        if (konfirmasi.equalsIgnoreCase("ya")) {
            // 3. Proses Pembayaran (Kurangi Saldo)
            if (this.pengguna.kurangiSaldo(pesanan.getTotalAkhir())) {
                System.out.println("\n(+) Pembayaran berhasil!");
                System.out.printf("Saldo Anda sekarang: Rp %,.0f\n", pengguna.getSaldo());
                System.out.println("Pesanan #" + pesanan.getIdPesanan() + " berhasil dibuat.");
                System.out.println("Mohon ditunggu, pesanan Anda akan segera tiba! (simulasi langsung sampai)");
                // 4. Tambahkan ke Riwayat
                this.riwayatPesanan.add(pesanan);
            } else {
                // Seharusnya tidak terjadi karena sudah dicek saldo, tapi jaga-jaga
                System.err.println("(!) Error: Gagal memproses pembayaran meskipun saldo tampak cukup.");
                System.out.println("Pesanan dibatalkan.");
            }
        } else {
            System.out.println("Pesanan dibatalkan.");
            // Tidak ditambahkan ke riwayat jika dibatalkan
        }
    }

    // --- Fitur Riwayat ---
    /**
     * Menampilkan daftar riwayat pesanan yang telah selesai.
     */
    private void tampilkanRiwayatPesanan() {
        System.out.println("\n--- Riwayat Pesanan Selesai ---");

        if (this.riwayatPesanan.isEmpty()) {
            System.out.println("(Belum ada riwayat pesanan)");
        } else {
            // Urutkan riwayat berdasarkan ID terbaru di atas (descending)
            this.riwayatPesanan.sort(Comparator.comparingInt(Pesanan::getIdPesanan).reversed());
            // Tampilkan ringkasan
            for (Pesanan p : this.riwayatPesanan) {
                p.tampilkanRingkasanRiwayat();
            }

            // Opsi untuk melihat detail
            System.out.print("\nMasukkan ID Pesanan untuk lihat detail (0 untuk kembali): #");
            int id = getInputInteger(""); // Minta ID
            if (id > 0) {
                boolean found = false;
                for (Pesanan p : this.riwayatPesanan) { // Cari pesanan by ID
                    if (p.getIdPesanan() == id) {
                        p.tampilkanDetail(); // Tampilkan detail lengkap jika ketemu
                        found = true;
                        break;
                    }
                }
                if (!found) { // Jika ID tidak ditemukan
                    System.out.println("(!) Pesanan dengan ID #" + id + " tidak ditemukan dalam riwayat.");
                }
            } else if (id != 0) {
                // Jika input bukan 0 tapi tidak valid (misal negatif dari getInt)
                System.out.println("Input ID tidak valid.");
            }
        }
        System.out.println("-----------------------------");
    }

    // --- Metode Helper ---
    /**
     * Helper untuk membaca input integer dari pengguna dengan aman. Terus
     * meminta input hingga integer valid dimasukkan.
     *
     * @param prompt Pesan yang ditampilkan ke pengguna sebelum input.
     * @return Integer yang valid dari pengguna.
     */
    private int getInputInteger(String prompt) {
        int input = -1; // Nilai default jika terjadi error tak terduga
        while (true) {
            System.out.print(prompt);
            try {
                String line = scan.nextLine().trim(); // Baca seluruh baris
                // Cek jika input kosong padahal ada prompt
                if (line.isEmpty() && !prompt.isEmpty()) {
                    System.err.println("(!) Input tidak boleh kosong.");
                    continue; // Minta input lagi
                } else if (line.isEmpty() && prompt.isEmpty()) {
                    // Jika prompt kosong (misal setelah pesan error) dan user enter,
                    // mungkin lebih baik loop lagi atau return nilai khusus
                    continue; // Minta input lagi saja
                }
                input = Integer.parseInt(line); // Coba ubah ke integer
                break; // Keluar loop jika berhasil
            } catch (NumberFormatException e) {
                System.err.println("(!) Input tidak valid. Harap masukkan angka.");
                // Loop akan berlanjut
            } catch (NoSuchElementException e) {
                // Error jika input stream ditutup (jarang terjadi di console biasa)
                System.err.println("(!) Input stream error. Aplikasi akan keluar.");
                System.exit(1); // Keluar paksa
            }
        }
        return input;
    }

}
