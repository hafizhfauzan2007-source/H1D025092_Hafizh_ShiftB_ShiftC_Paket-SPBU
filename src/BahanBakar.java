public class BahanBakar {
    public static final String BENSIN = "Bensin";
    public static final String DIESEL = "Diesel";
    public static final double POTONGAN_SUBSIDI_PER_LITER = 1000;
    public static final double BATAS_STOK_MINIMUM = 100;

    private final String kode;
    private String nama;
    private String jenis;
    private double harga;
    private double stok;
    private boolean bersubsidi;

    public BahanBakar(String kode, String nama, String jenis, double harga, double stok, boolean bersubsidi) {
        this.kode = kode;
        this.nama = nama;
        this.jenis = jenis;
        this.bersubsidi = bersubsidi;
        setHarga(harga);
        setStok(stok);
    }

    public BahanBakar(String kode, String nama, String jenis, double harga, boolean bersubsidi) {
        this(kode, nama, jenis, harga, 0, bersubsidi);
    }

    public double hitungHarga(double liter) {
        return harga * liter;
    }

    public double hitungPotongan(double liter) {
        if (this.bersubsidi && this.jenis.equals(DIESEL)) {
            return POTONGAN_SUBSIDI_PER_LITER * liter;
        }
        return 0;
    }

    public boolean isDiesel() {
        return jenis.equals(DIESEL);
    }

    public boolean isBensinBersubsidi() {
        return bersubsidi && !isDiesel();
    }

    public boolean isStokCukup(double liter) {
        return stok >= liter;
    }

    public boolean isStokMenipis() {
        return stok < BATAS_STOK_MINIMUM;
    }

    public void kurangiStok(double liter) {
        stok -= liter;
    }

    public void tampilkanInfo() {
        String status = bersubsidi ? "bersubsidi" : "non-subsidi";
        String menipis = isStokMenipis() ? " (menipis)" : "";
        System.out.println(nama + " | Rp" + (long) harga + "/L | stok " + stok + " L" + menipis + " | " + status);
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        if (harga <= 0) {
            System.out.println("Harga harus lebih dari 0");
        } else {
            this.harga = harga;
        }
    }

    public double getStok() {
        return stok;
    }

    public void setStok(double stok) {
        if (stok < 0) {
            System.out.println("Stok tidak boleh negatif");
        } else {
            this.stok = stok;
        }
    }

    public boolean isBersubsidi() {
        return bersubsidi;
    }

    public void setBersubsidi(boolean bersubsidi) {
        this.bersubsidi = bersubsidi;
    }
}
