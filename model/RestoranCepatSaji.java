package model;

public class RestoranCepatSaji extends Restoran {

    public RestoranCepatSaji(String nama, int jarak, boolean adaPromo, String kategoriHarga) {
        super(nama, jarak, adaPromo, kategoriHarga);
        inisialisasiMenuDefault(nama);
    }

    private void inisialisasiMenuDefault(String namaResto) {
        if (namaResto.equalsIgnoreCase("McDonald's")) {
            tambahMenu("Hot Cakes 3pcs", 33000);
            tambahMenu("Chicken McMuffin", 29000);
            tambahMenu("Big Breakfast", 42000);
            tambahMenu("PaNas Special Krispy", 54000);
            tambahMenu("Ayam Lengkuas Sambal Bajak", 30000);
            tambahMenu("Coke Float", 12000);
            tambahMenu("McFlury Choco", 14000);
        } else if (namaResto.equalsIgnoreCase("Burger King")) {
            tambahMenu("Whopper Jr.", 35000);
            tambahMenu("Cheeseburger", 25000);
            tambahMenu("King Fusion Milo", 15000);
            tambahMenu("Fries Regular", 15000);
        } else if (namaResto.equalsIgnoreCase("KFC")) {
            tambahMenu("Paket Super Besar 1", 38000);
            tambahMenu("Zuper Krunch", 30000);
            tambahMenu("Float Yuzu", 10000);
            tambahMenu("Chicken Strip", 18000);
        } else if (namaResto.equalsIgnoreCase("Gacoan")) {
            tambahMenu("Mie Gacoan Lv.1", 10000);
            tambahMenu("Mie Hompimpa Lv.1", 10000);
            tambahMenu("Udang Keju", 9000);
            tambahMenu("Lumpia Udang", 9000);
            tambahMenu("Es Gobak Sodor", 8500);
        }
    }

    @Override
    public String getTipeRestoran() {
        return "Cepat Saji";
    }
}
