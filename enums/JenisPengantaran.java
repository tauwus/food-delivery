package enums;

public enum JenisPengantaran {
    EXPRESS("Express", 2500),
    REGULER("Reguler", 1500),
    EKONOMIS("Ekonomis", 1000);

    private final String nama;
    private final int biayaDasarPerKm;

    JenisPengantaran(String nama, int biayaDasarPerKm) {
        this.nama = nama;
        this.biayaDasarPerKm = biayaDasarPerKm;
    }

    public String getNama() {
        return nama;
    }

    public int getBiayaDasarPerKm() {
        return biayaDasarPerKm;
    }
}
