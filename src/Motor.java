public class Motor extends Kendaraan implements Struk, Subsidi {
    public static final int BATAS_MESIN_PERTALITE = 250;

    private int kapasitasMesin;

    public Motor(String platNomor, String merek, int kapasitasMesin) {
        super(platNomor, merek);
        this.kapasitasMesin = kapasitasMesin;
    }

    public Motor(String platNomor, int kapasitasMesin) {
        this(platNomor, MEREK_DEFAULT, kapasitasMesin);
    }

    @Override
    public boolean isiBBM(BahanBakar bbm, double liter) {
        if (bbm.isDiesel()) {
            return tolak("Motor hanya boleh mengisi " + BahanBakar.BENSIN);
        } else if (bbm.isBensinBersubsidi() && kapasitasMesin > BATAS_MESIN_PERTALITE) {
            return tolak("mesin " + kapasitasMesin + " cc melebihi batas " + BATAS_MESIN_PERTALITE
                    + " cc untuk " + bbm.getNama());
        } else if (!bbm.isStokCukup(liter)) {
            return tolakStokKurang(bbm);
        } else {
            selesaikanPengisian(bbm, liter);
            return true;
        }
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("  -> Motor " + kapasitasMesin + " cc");
    }

    @Override
    public void cetakStruk(BahanBakar bbm, double liter) {
        System.out.println("--- STRUK Motor " + getPlatNomor() + " ---");
        cetakRincianStruk(bbm, liter, hitungSubsidi(bbm, liter));
    }

    @Override
    public double hitungSubsidi(BahanBakar bbm, double liter) {
        return bbm.hitungPotongan(liter);
    }

    public int getKapasitasMesin() {
        return kapasitasMesin;
    }

    public void setKapasitasMesin(int kapasitasMesin) {
        this.kapasitasMesin = kapasitasMesin;
    }
}
