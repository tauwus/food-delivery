package model;

import interfaces.Showable;
import enums.JenisPengantaran;

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
    private double diskonPromo;
    private double totalAkhir;

    private static final double NILAI_DISKON_PROMO = 5000.0;

    public Pesanan(Pengguna pengguna, Restoran restoran) {
        this.idPesanan = ++counterPesanan;
        this.pengguna = pengguna;
        this.restoran = restoran;
        this.items = new ArrayList<>();
        this.totalHargaMakanan = 0;
        this.biayaPengantaran = 0;
        this.diskonPromo = 0;
        this.totalAkhir = 0;
        this.jenisPengantaran = null;
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

    public double getDiskonPromo() {
        return diskonPromo;
    }

    public double getBiayaPengantaran() {
        return biayaPengantaran;
    }

    public void setJenisPengantaran(JenisPengantaran jenisPengantaran) {
        this.jenisPengantaran = jenisPengantaran;
    }

    public void setBiayaPengantaran(double biayaPengantaran) {
        this.biayaPengantaran = biayaPengantaran;
        this.hitungTotalAkhir();
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
                        System.out.println(">> Jumlah '" + namaMenu + "' diperbarui.");
                        break;
                    }
                }
                if (!found) {
                    this.items.add(new ItemPesanan(namaMenu, hargaSatuan, jumlah));
                    System.out.println(">> " + jumlah + "x " + namaMenu + " ditambahkan.");
                }
                hitungTotalMakanan();
            } else {
                System.out.println("(!) Jumlah harus > 0.");
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
        this.diskonPromo = 0;
        if (this.restoran != null && this.restoran.isAdaPromo() && this.totalHargaMakanan > 0) {
            this.diskonPromo = NILAI_DISKON_PROMO;
            if (this.diskonPromo > this.totalHargaMakanan) {
                this.diskonPromo = this.totalHargaMakanan;
            }
        }
        double totalSebelumDiskon = this.totalHargaMakanan + this.biayaPengantaran;
        this.totalAkhir = totalSebelumDiskon - this.diskonPromo;
        if (this.totalAkhir < 0) {
            this.totalAkhir = 0;
        }
    }

    public boolean isSaldoCukup() {
        return pengguna.getSaldo() >= this.totalAkhir;
    }

    @Override
    public void tampilkanDetail() {
        System.out.println("\n--- Detail Pesanan #" + idPesanan + " ---");
        System.out.println("Restoran       : " + restoran.getNama() + (restoran.isAdaPromo() ? " [PROMO AKTIF!]" : ""));
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
            System.out.printf("Ongkos Kirim (%s): Rp %,.0f\n", jenisPengantaran.getNama(), biayaPengantaran); // <<< Pakai getNama() dari Enum
        } else {
            System.out.println("Ongkos Kirim     : (Belum dipilih)");
        }
        if (this.diskonPromo > 0) {
            System.out.printf("Diskon Promo     : Rp -%,.0f\n", this.diskonPromo);
        }
        System.out.println("------------------------------");
        System.out.printf("Total Pembayaran : Rp %,.0f\n", totalAkhir);
        System.out.println("==============================");
    }

    public void tampilkanRingkasanRiwayat() {
        System.out.printf("ID: #%d | Resto: %-20s | Total: Rp %,.0f %s\n",
                idPesanan,
                restoran.getNama(),
                totalAkhir,
                (diskonPromo > 0 ? "(Promo)" : "")
        );
    }
}
