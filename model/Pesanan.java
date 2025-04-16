package model;

import enums.JenisPengantaran;
import interfaces.Showable;
import java.util.ArrayList;
import java.util.List;

public class Pesanan implements Showable {

    private static int counterPesanan = 0;
    private int idPesanan;
    private Pengguna pengguna;
    private Restoran restoran;
    private List<ItemPesanan> items;
    private JenisPengantaran jenisPengantaran;
    private double biayaPengantaran;
    private double totalHargaMakanan;
    private double totalAkhir;

    public Pesanan(Pengguna pengguna, Restoran restoran) {
        this.idPesanan = ++counterPesanan;
        this.pengguna = pengguna;
        this.restoran = restoran;
        this.items = new ArrayList<>();
        this.totalHargaMakanan = 0;
        this.biayaPengantaran = 0;
        this.totalAkhir = 0;
    }

    public int getIdPesanan() {
        return idPesanan;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public double getTotalAkhir() {
        return totalAkhir;
    }

    public double getBiayaPengantaran() {
        return biayaPengantaran;
    }

    public void setJenisPengantaran(JenisPengantaran jenisPengantaran) {
        this.jenisPengantaran = jenisPengantaran;
    }

    public void setBiayaPengantaran(double biayaPengantaran) {
        this.biayaPengantaran = biayaPengantaran;
    }

    public void tambahItem(String namaMenu, double hargaSatuan, int jumlah) {
        if (restoran.getHargaMenu(namaMenu) != null && restoran.getHargaMenu(namaMenu) == hargaSatuan) {
            if (jumlah > 0) {
                boolean found = false;
                for (int i = 0; i < items.size(); i++) {
                    ItemPesanan item = items.get(i);
                    if (item.getNamaMenu().equals(namaMenu)) {
                        items.set(i, new ItemPesanan(namaMenu, hargaSatuan, item.getJumlah() + jumlah));
                        found = true;
                        System.out.println(">> Jumlah '" + namaMenu + "' diperbarui di keranjang.");
                        break;
                    }
                }
                if (!found) {
                    this.items.add(new ItemPesanan(namaMenu, hargaSatuan, jumlah));
                    System.out.println(">> " + jumlah + "x " + namaMenu + " ditambahkan ke keranjang.");
                }
                hitungTotalMakanan();
            } else {
                System.out.println("Jumlah harus lebih dari 0.");
            }
        } else {
            System.err.println("Error internal: Menu '" + namaMenu + "' tidak valid.");
        }
    }

    private void hitungTotalMakanan() {
        this.totalHargaMakanan = 0;
        for (ItemPesanan item : items) {
            this.totalHargaMakanan += item.getSubTotal();
        }
        hitungTotalAkhir();
    }

    public void hitungTotalAkhir() {
        this.totalAkhir = this.totalHargaMakanan + this.biayaPengantaran;
    }

    public boolean isSaldoCukup() {
        return pengguna.getSaldo() >= this.totalAkhir;
    }

    @Override
    public void tampilkanDetail() {
        System.out.println("\n--- Detail Pesanan #" + idPesanan + " ---");
        System.out.println("Restoran       : " + restoran.getNama());
        System.out.println("Alamat Antar   : " + pengguna.getAlamat());
        System.out.println("Item Dipesan   :");
        if (items.isEmpty()) {
            System.out.println("   (Keranjang kosong)");
        } else {
            for (ItemPesanan item : items) {
                item.tampilkanDetail();
            }
        }
        System.out.println("------------------------------");
        System.out.printf("Subtotal Makanan : Rp %,.0f\n", totalHargaMakanan);
        if (jenisPengantaran != null) {
            System.out.printf("Ongkos Kirim (%s): Rp %,.0f\n", jenisPengantaran.getNama(), biayaPengantaran);
        } else {
            System.out.println("Ongkos Kirim     : (Belum dipilih)");
        }
        System.out.printf("Total Pembayaran : Rp %,.0f\n", totalAkhir);
        System.out.println("==============================");
    }

    public void tampilkanRingkasanRiwayat() {
        System.out.printf("ID: #%d | Resto: %-20s | Total: Rp %,.0f\n",
                idPesanan,
                restoran.getNama(),
                totalAkhir);
    }
}
