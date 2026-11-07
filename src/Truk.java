public class Truk extends Kendaraan implements Struk, Subsidi {
    public static final double BATAS_HARIAN_SOLAR = 200;

    public Truk(String platNomor, String merek) {
        super(platNomor, merek);
    }

    public Truk(String platNomor) {
        super(platNomor);
    }

    @Override
    public boolean isiBBM(BahanBakar bbm, double liter) {
        if (!bbm.isDiesel()) {
            return tolak("Truk hanya boleh mengisi " + BahanBakar.DIESEL);
        } else if (bbm.isBersubsidi() && getLiterSubsidiHariIni() + liter > BATAS_HARIAN_SOLAR) {
            return tolakBatasHarian(bbm, BATAS_HARIAN_SOLAR);
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
        System.out.println("  -> Truk");
    }

    @Override
    public void cetakStruk(BahanBakar bbm, double liter) {
        System.out.println("--- STRUK Truk " + getPlatNomor() + " ---");
        cetakRincianStruk(bbm, liter, hitungSubsidi(bbm, liter));
    }

    @Override
    public double hitungSubsidi(BahanBakar bbm, double liter) {
        return bbm.hitungPotongan(liter);
    }
}
