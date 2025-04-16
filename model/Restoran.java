package model;

import interfaces.Showable;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Restoran implements Showable {

    protected String nama;
    protected int jarak;
    protected Map<String, Double> daftarMenu;
    protected boolean adaPromo;
    protected String kategoriHarga;
    private String promoDeskripsi;
    public String getPromoDeskripsi() { return promoDeskripsi; }
    public void setPromoDeskripsi(String promo) { this.promoDeskripsi = promo; }

    public Restoran(String nama, int jarak, boolean adaPromo, String kategoriHarga) {
        this.nama = nama;
        this.jarak = jarak;
        this.adaPromo = adaPromo;
        this.kategoriHarga = kategoriHarga;
        this.daftarMenu = new LinkedHashMap<>();
    }

    public String getNama() {
        return nama;
    }

    public int getJarak() {
        return jarak;
    }

    public Map<String, Double> getDaftarMenuMap() {
        return daftarMenu;
    }

    public boolean isAdaPromo() {
        return adaPromo;
    }

    public String getKategoriHarga() {
        return kategoriHarga;
    }

    protected void tambahMenu(String namaMenu, double harga) {
        if (namaMenu != null && !namaMenu.trim().isEmpty() && harga >= 0) {
            this.daftarMenu.put(namaMenu, harga);
        }
    }

    public Double getHargaMenu(String namaMenu) {
        return this.daftarMenu.get(namaMenu);
    }

    public double getRataRataHargaMenu() {
        if (daftarMenu == null || daftarMenu.isEmpty()) return 0;
        double total = 0;
        for (double harga : daftarMenu.values()) {
            total += harga;
        }
        return total / daftarMenu.size();
    }    

    public void tampilkanSemuaMenu() {
        System.out.println("\n--- Menu " + this.nama + " ---");
        int i = 1;
        for (Map.Entry<String, Double> entry : daftarMenu.entrySet()) {
            System.out.printf("%d. %s - Rp %,.0f\n", i++, entry.getKey(), entry.getValue());
        }
        System.out.println("----------------------");
    }

    public abstract String getTipeRestoran();

    @Override
    public void tampilkanDetail() {
        System.out.printf("%s (%s) [%s] - Jarak: %d km %s\n",
                nama, getTipeRestoran(), kategoriHarga, jarak, (adaPromo ? "[PROMO!]" : ""));
    }

    public void tampilkanDetail(int nomor) {
        System.out.printf("%d. %s (%s) [%s] - Jarak: %d km %s\n",
                nomor, nama, getTipeRestoran(), kategoriHarga, jarak, (adaPromo ? "[PROMO!]" : ""));
    }
}
