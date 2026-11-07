public abstract class Kendaraan {
    public static final String MEREK_DEFAULT = "Tidak diketahui";

    private final String platNomor;
    private String merek;
    private double literSubsidiHariIni;

    public Kendaraan(String platNomor, String merek) {
        this.platNomor = platNomor;
        this.merek = merek;
    }

    public Kendaraan(String platNomor) {
        this(platNomor, MEREK_DEFAULT);
    }

    public abstract boolean isiBBM(BahanBakar bbm, double liter);

    public void tampilkanInfo() {
        System.out.println("Plat: " + platNomor + " | Merek: " + merek);
    }

    protected boolean tolak(String alasan) {
        System.out.println("   DITOLAK: " + alasan);
        return false;
    }

    protected boolean tolakBatasHarian(BahanBakar bbm, double batasHarian) {
        double sisaJatah = batasHarian - literSubsidiHariIni;
        return tolak("batas harian " + bbm.getNama() + " " + batasHarian + " L terlampaui (sudah "
                + literSubsidiHariIni + " L, sisa jatah " + sisaJatah + " L)");
    }

    protected boolean tolakStokKurang(BahanBakar bbm) {
        return tolak("stok " + bbm.getNama() + " tidak cukup (tersisa " + bbm.getStok() + " L)");
    }

    protected void selesaikanPengisian(BahanBakar bbm, double liter) {
        bbm.kurangiStok(liter);
        if (bbm.isBersubsidi()) {
            literSubsidiHariIni += liter;
        }
    }

    protected void cetakRincianStruk(BahanBakar bbm, double liter, double subsidi) {
        double total = bbm.hitungHarga(liter);
        System.out.println(bbm.getNama() + " " + liter + " L x Rp" + (long) bbm.getHarga() + " = Rp" + (long) total);
        System.out.println("Subsidi : -Rp" + (long) subsidi);
        System.out.println("Bayar   : Rp" + (long) (total - subsidi));
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public double getLiterSubsidiHariIni() {
        return literSubsidiHariIni;
    }

    public void setLiterSubsidiHariIni(double literSubsidiHariIni) {
        this.literSubsidiHariIni = literSubsidiHariIni;
    }
}
