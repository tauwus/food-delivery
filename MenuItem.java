public class MenuItem {
  private String namaItem;
  private int hargaItem;
  private String deskripsiItem;

  public MenuItem(String namaMenu, int hargaMenu, String deskripsiItem) {
    this.namaItem = namaMenu;
    this.hargaItem = hargaMenu;
    this.deskripsiItem = deskripsiItem;
  }

  public String getNamaItem() {
    return namaItem;
  }

  public int getHargaItem() {
    return hargaItem;
  }

  public String getDeskripsiItem() {
    return deskripsiItem;
  }

  @Override
  public String toString() {
    return namaItem + " - Rp " + hargaItem + "\n" + deskripsiItem;
  }
}
