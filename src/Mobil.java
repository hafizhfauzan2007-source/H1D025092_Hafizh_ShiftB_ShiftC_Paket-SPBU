public class Mobil extends Kendaraan implements Struk, Subsidi {
    public static final int BATAS_MESIN_PERTALITE = 1400;
    public static final double BATAS_HARIAN_BENSIN = 50;
    public static final double BATAS_HARIAN_SOLAR_PRIBADI = 60;
    public static final double BATAS_HARIAN_SOLAR_UMUM = 80;
    public static final double PERSEN_SUBSIDI = 0.5;

    private int kapasitasMesin;
    private boolean pribadi;

    public Mobil(String platNomor, String merek, int kapasitasMesin, boolean pribadi) {
        super(platNomor, merek);
        this.kapasitasMesin = kapasitasMesin;
        this.pribadi = pribadi;
    }

    public Mobil(String platNomor, int kapasitasMesin) {
        this(platNomor, MEREK_DEFAULT, kapasitasMesin, true);
    }

    @Override
    public boolean isiBBM(BahanBakar bbm, double liter) {
        double batasHarian = tentukanBatasHarian(bbm);

        if (bbm.isBensinBersubsidi() && kapasitasMesin > BATAS_MESIN_PERTALITE) {
            return tolak("mesin " + kapasitasMesin + " cc melebihi batas " + BATAS_MESIN_PERTALITE
                    + " cc untuk " + bbm.getNama());
        } else if (bbm.isBersubsidi() && getLiterSubsidiHariIni() + liter > batasHarian) {
            return tolakBatasHarian(bbm, batasHarian);
        } else if (!bbm.isStokCukup(liter)) {
            return tolakStokKurang(bbm);
        } else {
            selesaikanPengisian(bbm, liter);
            return true;
        }
    }

    private double tentukanBatasHarian(BahanBakar bbm) {
        if (!bbm.isDiesel()) {
            return BATAS_HARIAN_BENSIN;
        }
        if (pribadi) {
            return BATAS_HARIAN_SOLAR_PRIBADI;
        }
        return BATAS_HARIAN_SOLAR_UMUM;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("  -> Mobil " + (pribadi ? "pribadi" : "umum") + " " + kapasitasMesin + " cc");
    }

    @Override
    public void cetakStruk(BahanBakar bbm, double liter) {
        System.out.println("--- STRUK Mobil " + getPlatNomor() + " ---");
        cetakRincianStruk(bbm, liter, hitungSubsidi(bbm, liter));
    }

    @Override
    public double hitungSubsidi(BahanBakar bbm, double liter) {
        return bbm.hitungPotongan(liter) * PERSEN_SUBSIDI;
    }

    public int getKapasitasMesin() {
        return kapasitasMesin;
    }

    public void setKapasitasMesin(int kapasitasMesin) {
        this.kapasitasMesin = kapasitasMesin;
    }

    public boolean isPribadi() {
        return pribadi;
    }

    public void setPribadi(boolean pribadi) {
        this.pribadi = pribadi;
    }
}
