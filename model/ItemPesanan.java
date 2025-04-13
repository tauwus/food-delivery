package model;

import interfaces.Showable;

public class ItemPesanan implements Showable {

    private String namaMenu;
    private double hargaSatuan;
    private int jumlah;

    public ItemPesanan(String namaMenu, double hargaSatuan, int jumlah) {
        this.namaMenu = namaMenu;
        this.hargaSatuan = hargaSatuan;
        this.jumlah = jumlah;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public double getHargaSatuan() {
        return hargaSatuan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getSubTotal() {
        return hargaSatuan * jumlah;
    }

    @Override
    public void tampilkanDetail() {
        System.out.printf("   - %s (Rp %,.0f) x %d = Rp %,.0f\n",
                namaMenu, hargaSatuan, jumlah, getSubTotal());
    }
}
