package model;

import interfaces.Showable;
import java.util.Scanner;

public class Pengguna implements Showable {

    private String username;
    private String nomorHp;
    private String alamat;
    private double saldo;

    public Pengguna(String username, String nomorHp, String alamat) {
        this.username = username;
        this.nomorHp = nomorHp;
        this.alamat = alamat;
        this.saldo = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getNomorHp() {
        return nomorHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public double getSaldo() {
        return saldo;
    }

    public void tambahSaldo(double jumlah) {
        if (jumlah > 0) {
            this.saldo += jumlah;
            System.out.printf("Top up berhasil! Saldo Anda sekarang: Rp %,.0f\n", this.saldo);
        } else {
            System.out.println("Jumlah top up tidak valid.");
        }
    }

    public boolean kurangiSaldo(double jumlah) {
        if (jumlah > 0 && this.saldo >= jumlah) {
            this.saldo -= jumlah;
            return true;
        }
        return false;
    }

    @Override
    public void tampilkanDetail() {
        System.out.println("\n--- Detail Akun ---");
        System.out.println("Username: " + username);
        System.out.println("No HP   : " + nomorHp);
        System.out.println("Alamat  : " + alamat);
        System.out.printf("Saldo   : Rp %,.0f\n", saldo);
        System.out.println("--------------------");
    }

    public void perbaruiProfil(Scanner scan) {
        boolean lanjutUpdate = true;
        while (lanjutUpdate) {
            System.out.println("\n--- Perbarui Profil ---");
            System.out.println("Pilih data yang ingin diubah:");
            System.out.println("1. Username");
            System.out.println("2. Nomor HP");
            System.out.println("3. Alamat");
            System.out.println("0. Selesai Update & Kembali");
            System.out.print("Pilihan: ");

            try {
                String line = scan.nextLine().trim();
                if (line.isEmpty()) {
                    System.out.println("(!) Pilihan tidak boleh kosong.");
                    continue;
                }
                int pilihan = Integer.parseInt(line);

                switch (pilihan) {
                    case 1:
                        System.out.print("Masukkan Username baru: ");
                        String newUsername = scan.nextLine().trim();
                        
                        // Validasi Username
                        if (newUsername.isEmpty()) {
                            System.out.println("(!) Username tidak boleh kosong.");
                            System.out.println("Kembali ke menu update profil...");
                        } else if (newUsername.length() > 20) {
                            System.out.println("(!) Username terlalu panjang. Maksimal 20 karakter.");
                            System.out.println("Kembali ke menu update profil...");
                        } else if (newUsername.equals(this.getUsername())) {
                            System.out.println("(-) Username sama, tidak ada perubahan.");
                            System.out.println("Kembali ke menu update profil...");
                        } else {
                            username = newUsername;
                            System.out.println("(+) Username berhasil diperbarui.");
                            tampilkanDetail();
                        }
                        break;
                        
                    case 2:
                        System.out.print("Masukkan No. HP baru: ");
                        String newNomorHp = scan.nextLine().trim();
                        
                        // Validasi Nomor Handphone
                        if (newNomorHp.isEmpty()) {
                            System.out.println("(!) Nomor HP tidak boleh kosong.");
                            System.out.println("Kembali ke menu update profil...");
                        } else if (!newNomorHp.matches("^08\\d{8,12}$")) {
                            System.out.println("(!) Format Nomor HP tidak valid.");
                            System.out.println("Pastikan nomor diawali 08 dan panjangnya 10-14 digit.");
                            System.out.println("Kembali ke menu update profil...");
                        } else if (newNomorHp.equals(this.getNomorHp())) {
                            System.out.println("(-) Nomor HP sama, tidak ada perubahan.");
                            System.out.println("Kembali ke menu update profil...");
                        } else {
                            nomorHp = newNomorHp;
                            System.out.println("(+) Nomor HP berhasil diperbarui.");
                            tampilkanDetail();
                        }
                        break;
                
                    case 3:
                        System.out.print("Masukkan Alamat baru: ");
                        String newAlamat = scan.nextLine().trim();
                        
                        if (newAlamat.isEmpty()) {
                            System.out.println("(!) Alamat tidak boleh kosong.");
                            System.out.println("Kembali ke menu update profil...");
                        } else if (newAlamat.equals(this.getAlamat())) {
                            System.out.println("(-) Alamat sama, tidak ada perubahan.");
                            System.out.println("Kembali ke menu update profil...");
                        } else {
                            alamat = newAlamat;
                            System.out.println("(+) Alamat berhasil diperbarui.");
                            tampilkanDetail();
                        }
                        break;
                                        
                    case 0:
                        lanjutUpdate = false;
                        System.out.println("Selesai memperbarui profil.");
                        break;
                    default:
                        System.out.println("(!) Pilihan tidak valid (antara 0-3).");
                        break;
                }
            } catch (NumberFormatException e) {
                System.err.println("(!) Input tidak valid. Harap masukkan angka pilihan (0-3).");
            }
        }
    }
}
