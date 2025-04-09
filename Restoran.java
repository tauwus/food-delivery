import java.util.ArrayList; 
import java.util.List; 

public abstract class Restoran implements Showable {
    protected String nama;
    protected String tipe;
    private List<MenuItem> daftarMenu;

    public Restoran(String nama) {
        this.nama = nama;
        this.daftarMenu = new ArrayList<>(); 
    }

    public String getNama() {
        return nama;
    }

    public List<MenuItem> getDaftarMenu() {
        return daftarMenu;
    }

    public void tambahMenuItem(MenuItem item) {
        this.daftarMenu.add(item);
    }

    public abstract String getTipeRestoran();

    @Override
    public void tampilkanDetail() {
        System.out.println("Restoran: " + this.nama + " (Tipe: " + this.getTipeRestoran() + ")");
    }
    
    public void tampilkanMenuLengkap() {
        System.out.println("--- Menu " + this.nama + " ---");
        if (daftarMenu.isEmpty()) {
            System.out.println("Menu belum ada.");
        } else {
            for (int i = 0; i < daftarMenu.size(); i++) {
                System.out.println((i + 1) + ". " + daftarMenu.get(i).toString()); 
            }
        }
        System.out.println("----------------------");
    }
}
