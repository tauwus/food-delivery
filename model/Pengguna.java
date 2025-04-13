package model;

import interfaces.Showable;
import java.util.InputMismatchException;
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

    public void updateProfil(Scanner scan) {
        boolean lanjutUpdate = true;
        while (lanjutUpdate) {
            tampilkanDetail();
            System.out.println("\n--- Perbarui Profil ---");
            System.out.println("Pilih data yang ingin diubah:");
            System.out.println("1. Username");
            System.out.println("2. Nomor HP");
            System.out.println("3. Alamat");
            System.out.println("0. Selesai Update");
            System.out.print("Pilihan: ");

            int pilihan;
            try {
                // Menggunakan nextLine untuk membaca seluruh baris lalu parse
                String line = scan.nextLine().trim();
                if (line.isEmpty()) {
                    System.out.println("Pilihan tidak boleh kosong.");
                    continue;
                }
                pilihan = Integer.parseInt(line);

                switch (pilihan) {
                    case 1:
                        System.out.print("Masukkan Username baru: ");
                        String newUsername = scan.nextLine().trim();
                        if (!newUsername.isEmpty() && !newUsername.equals(this.username)) {
                            this.username = newUsername;
                            System.out.println("Username berhasil diperbarui.");
                        } else if (newUsername.equals(this.username)) {
                            System.out.println("Username sama, tidak ada perubahan.");
                        } else {
                            System.out.println("Username tidak boleh kosong.");
                        }
                        break;
                    case 2:
                        System.out.print("Masukkan No. HP baru: ");
                        String newNomorHp = scan.nextLine().trim();
                        if (!newNomorHp.isEmpty() && newNomorHp.matches("\\d+") && !newNomorHp.equals(this.nomorHp)) {
                            this.nomorHp = newNomorHp;
                            System.out.println("Nomor HP berhasil diperbarui.");
                        } else if (newNomorHp.equals(this.nomorHp)) {
                            System.out.println("Nomor HP sama, tidak ada perubahan.");
                        } else if (newNomorHp.isEmpty()) {
                            System.out.println("Nomor HP tidak boleh kosong.");
                        } else {
                            System.out.println("Format Nomor HP tidak valid (hanya angka).");
                        }
                        break;
                    case 3:
                        System.out.print("Masukkan Alamat baru: ");
                        String newAlamat = scan.nextLine().trim();
                        if (!newAlamat.isEmpty() && !newAlamat.equals(this.alamat)) {
                            this.alamat = newAlamat;
                            System.out.println("Alamat berhasil diperbarui.");
                        } else if (newAlamat.equals(this.alamat)) {
                            System.out.println("Alamat sama, tidak ada perubahan.");
                        } else {
                            System.out.println("Alamat tidak boleh kosong.");
                        }
                        break;
                    case 0:
                        lanjutUpdate = false;
                        System.out.println("Selesai memperbarui profil.");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Input tidak valid. Harap masukkan angka pilihan.");
            } catch (InputMismatchException e) { // Catch jika nextInt() dipakai dan error
                System.err.println("Input tidak valid. Harap masukkan angka.");
                scan.nextLine(); // Membersihkan buffer scanner jika terjadi error Mismatch
            }

            if (lanjutUpdate) {
                System.out.println("\nTekan Enter untuk lanjut mengubah data lain atau pilih 0 untuk selesai...");
            }
        }
    }
}
