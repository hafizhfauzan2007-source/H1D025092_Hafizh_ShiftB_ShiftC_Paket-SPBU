public class Pompa {
    private final int nomorPompa;
    private double totalPenghasilan;
    private int jumlahTransaksi;

    public Pompa(int nomorPompa) {
        this.nomorPompa = nomorPompa;
    }

    public boolean layani(Kendaraan kendaraan, BahanBakar bbm, double liter) {
        System.out.println("\nPompa " + nomorPompa + ": " + kendaraan.getPlatNomor()
                + " isi " + bbm.getNama() + " " + liter + " L");

        if (liter <= 0) {
            System.out.println("   DITOLAK: jumlah liter harus lebih dari 0");
            return false;
        }
        if (!kendaraan.isiBBM(bbm, liter)) {
            return false;
        }

        Subsidi subsidi = (Subsidi) kendaraan;
        totalPenghasilan += bbm.hitungHarga(liter) - subsidi.hitungSubsidi(bbm, liter);
        jumlahTransaksi++;

        System.out.println("   BERHASIL: pengisian selesai");
        if (bbm.isStokMenipis()) {
            System.out.println("PERINGATAN: stok " + bbm.getNama() + " menipis (" + bbm.getStok() + " L)");
        }
        return true;
    }

    public int getNomorPompa() {
        return nomorPompa;
    }

    public double getTotalPenghasilan() {
        return totalPenghasilan;
    }

    public void setTotalPenghasilan(double totalPenghasilan) {
        this.totalPenghasilan = totalPenghasilan;
    }

    public int getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public void setJumlahTransaksi(int jumlahTransaksi) {
        this.jumlahTransaksi = jumlahTransaksi;
    }
}