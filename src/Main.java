import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final Pompa POMPA = new Pompa(1);
    private static final ArrayList<BahanBakar> DAFTAR_BBM = new ArrayList<>();
    private static final ArrayList<Kendaraan> DAFTAR_KENDARAAN = new ArrayList<>();

    private static Struk strukTerakhir;
    private static BahanBakar bbmTerakhir;
    private static double literTerakhir;

    public static void main(String[] args) {
        siapkanDataBBM();

        int pilihan = -1;
        while (pilihan != 0) {
            tampilkanMenu();
            pilihan = bacaAngka("Pilih");
            switch (pilihan) {
                case 1:
                    isiBBM();
                    break;
                case 2:
                    lihatStok();
                    break;
                case 3:
                    tambahStok();
                    break;
                case 4:
                    ubahHarga();
                    break;
                case 5:
                    lihatPenghasilan();
                    break;
                case 6:
                    cetakStruk();
                    break;
                case 0:
                    System.out.println("Terima kasih.");
                    break;
                default:
                    System.out.println("Pilihan menu tidak tersedia.");
            }
        }
    }

    private static void siapkanDataBBM() {
        BahanBakar pertamax = new BahanBakar("BBM-02", "Pertamax", BahanBakar.BENSIN, 12500, false);
        pertamax.setStok(300);

        DAFTAR_BBM.add(new BahanBakar("BBM-01", "Pertalite", BahanBakar.BENSIN, 10000, 500, true));
        DAFTAR_BBM.add(pertamax);
        DAFTAR_BBM.add(new BahanBakar("BBM-03", "Solar", BahanBakar.DIESEL, 6800, 250, true));
    }

    private static void tampilkanMenu() {
        System.out.println("\n===== SPBU =====");
        System.out.println("1. Isi BBM");
        System.out.println("2. Lihat stok BBM");
        System.out.println("3. Tambah stok BBM");
        System.out.println("4. Ubah harga BBM");
        System.out.println("5. Lihat total penghasilan");
        System.out.println("6. Cetak struk");
        System.out.println("0. Keluar");
    }

    private static void isiBBM() {
        System.out.println("\n--- ISI BBM ---");
        String plat = bacaTeks("Plat nomor").toUpperCase();
        if (plat.isEmpty()) {
            System.out.println("Plat nomor tidak boleh kosong.");
            return;
        }

        Kendaraan kendaraan = cariKendaraan(plat);
        if (kendaraan == null) {
            kendaraan = daftarkanKendaraan(plat);
        }
        if (kendaraan == null) {
            return;
        }
        kendaraan.tampilkanInfo();

        BahanBakar bbm = pilihBBM();
        if (bbm == null) {
            return;
        }
        double liter = bacaDesimal("Jumlah liter");
        if (POMPA.layani(kendaraan, bbm, liter)) {
            strukTerakhir = (Struk) kendaraan;
            bbmTerakhir = bbm;
            literTerakhir = liter;
            System.out.println("Pilih menu 6 untuk mencetak struk.");
        }
    }

    private static Kendaraan cariKendaraan(String plat) {
        for (Kendaraan kendaraan : DAFTAR_KENDARAAN) {
            if (kendaraan.getPlatNomor().equals(plat)) {
                return kendaraan;
            }
        }
        return null;
    }

    private static Kendaraan daftarkanKendaraan(String plat) {
        System.out.println("Kendaraan baru.");
        int jenis = bacaAngka("Jenis kendaraan (1=Motor, 2=Mobil, 3=Truk)");
        if (jenis < 1 || jenis > 3) {
            System.out.println("Jenis kendaraan tidak tersedia.");
            return null;
        }
        String merek = bacaTeks("Merek (Enter jika kosong)");

        Kendaraan kendaraan;
        if (jenis == 1) {
            kendaraan = buatMotor(plat, merek);
        } else if (jenis == 2) {
            kendaraan = buatMobil(plat, merek);
        } else {
            kendaraan = buatTruk(plat, merek);
        }
        DAFTAR_KENDARAAN.add(kendaraan);
        return kendaraan;
    }

    private static Kendaraan buatMotor(String plat, String merek) {
        int cc = bacaAngka("Kapasitas mesin (cc)");
        if (merek.isEmpty()) {
            return new Motor(plat, cc);
        }
        return new Motor(plat, merek, cc);
    }

    private static Kendaraan buatMobil(String plat, String merek) {
        int cc = bacaAngka("Kapasitas mesin (cc)");
        boolean pribadi = !bacaTeks("Mobil umum/angkutan? (y, Enter jika pribadi)").equalsIgnoreCase("y");
        if (merek.isEmpty()) {
            return new Mobil(plat, Kendaraan.MEREK_DEFAULT, cc, pribadi);
        }
        return new Mobil(plat, merek, cc, pribadi);
    }

    private static Kendaraan buatTruk(String plat, String merek) {
        if (merek.isEmpty()) {
            return new Truk(plat);
        }
        return new Truk(plat, merek);
    }

    private static void lihatStok() {
        System.out.println("\n--- STOK BBM ---");
        for (BahanBakar bbm : DAFTAR_BBM) {
            bbm.tampilkanInfo();
        }
        System.out.println("Batas stok minimum: " + BahanBakar.BATAS_STOK_MINIMUM + " L");
        System.out.println("Potongan subsidi per liter: Rp" + (long) BahanBakar.POTONGAN_SUBSIDI_PER_LITER);
    }

    private static void tambahStok() {
        System.out.println("\n--- TAMBAH STOK BBM ---");
        BahanBakar bbm = pilihBBM();
        if (bbm == null) {
            return;
        }
        double liter = bacaDesimal("Jumlah liter yang ditambah");
        if (liter <= 0) {
            System.out.println("Jumlah liter harus lebih dari 0.");
            return;
        }
        bbm.setStok(bbm.getStok() + liter);
        System.out.println("Stok " + bbm.getNama() + " sekarang " + bbm.getStok() + " L");
    }

    private static void ubahHarga() {
        System.out.println("\n--- UBAH HARGA BBM ---");
        BahanBakar bbm = pilihBBM();
        if (bbm == null) {
            return;
        }
        bbm.tampilkanInfo();
        bbm.setHarga(bacaDesimal("Harga baru per liter (Rp)"));
        System.out.println("Harga " + bbm.getNama() + " sekarang Rp" + (long) bbm.getHarga());
    }

    private static void lihatPenghasilan() {
        System.out.println("\n--- TOTAL PENGHASILAN ---");
        System.out.println("Transaksi berhasil : " + POMPA.getJumlahTransaksi());
        System.out.println("Total penghasilan  : Rp" + (long) POMPA.getTotalPenghasilan());
    }

    private static void cetakStruk() {
        System.out.println("\n--- CETAK STRUK ---");
        if (strukTerakhir == null) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        strukTerakhir.cetakStruk(bbmTerakhir, literTerakhir);
    }

    private static BahanBakar pilihBBM() {
        for (int i = 0; i < DAFTAR_BBM.size(); i++) {
            System.out.println((i + 1) + ". " + DAFTAR_BBM.get(i).getNama());
        }
        int nomor = bacaAngka("Pilih BBM");
        if (nomor < 1 || nomor > DAFTAR_BBM.size()) {
            System.out.println("Pilihan BBM tidak tersedia.");
            return null;
        }
        return DAFTAR_BBM.get(nomor - 1);
    }

    private static String bacaTeks(String label) {
        System.out.print(label + ": ");
        return INPUT.nextLine().trim();
    }

    private static int bacaAngka(String label) {
        return Integer.parseInt(bacaTeks(label));
    }

    private static double bacaDesimal(String label) {
        return Double.parseDouble(bacaTeks(label));
    }
}