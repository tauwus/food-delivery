package model;

public class TokoDessert extends Restoran {

    public TokoDessert(String nama) {
        super(nama);
        daftarMenu(nama);
    }

    private void daftarMenu(String namaToko) {
        if (namaToko.equalsIgnoreCase("Mixue")) {
            tambahMenu("Boba Sundae", 16000);
            tambahMenu("Mi-Shake Strawberry", 16000);
            tambahMenu("Fresh Ice Lemonade", 10000);
            tambahMenu("Ice Cream Cone", 8000);
        } else if (namaToko.equalsIgnoreCase("Retawu Deli")) {
            tambahMenu("Croissant Butter", 15000);
            tambahMenu("Pain au Chocolat", 18000);
            tambahMenu("Canele", 25000);
        } else if (namaToko.equalsIgnoreCase("Beli Kopi")) {
            tambahMenu("Kopi Susu Gula Aren", 18000);
            tambahMenu("Americano Ice", 15000);
            tambahMenu("Coklat Signature", 20000);
        }
    }

    @Override
    public String getTipeRestoran() {
        return "Dessert";
    }
}
