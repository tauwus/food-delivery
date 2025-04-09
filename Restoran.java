import java.util.ArrayList; // Butuh untuk List
import java.util.List; // Butuh untuk List

// Deklarasi abstract class, implements Showable
public abstract class Restoran implements Showable {

    // Atribut
    protected String nama;
    private List<MenuItem> daftarMenu;

    // Constructor
    public Restoran(String nama) {
        this.nama = nama;
        this.daftarMenu = new ArrayList<>(); // Penting: inisialisasi listnya!
    }

    // Method biasa (Getter & method tambah)
    public String getNama() {
        return nama;
    }

    public List<MenuItem> getDaftarMenu() {
        return daftarMenu;
    }

    public void tambahMenuItem(MenuItem item) {
        this.daftarMenu.add(item);
    }

    // Method abstract (HANYA deklarasi, tanpa isi {})
    public abstract String getTipeRestoran();

    // Method dari Showable (kasih isi sederhana dulu)
    @Override
    public void tampilkanDetail() {
        System.out.println("Restoran: " + this.nama + " (Tipe: " + this.getTipeRestoran() + ")");
    }

    // Method untuk tampilkan menu (kasih kerangka dulu)
    public void tampilkanMenuLengkap() {
        System.out.println("--- Menu " + this.nama + " ---");
        // Logika untuk loop daftarMenu dan print nanti diisi
        if (daftarMenu.isEmpty()) {
            System.out.println("Menu belum ada.");
        } else {
            for (int i = 0; i < daftarMenu.size(); i++) {
                // Print menu item ke-(i+1) di sini nanti
                System.out.println((i + 1) + ". " + daftarMenu.get(i).toString()); // Pakai toString() dulu
            }
        }
        System.out.println("----------------------");
    }
}