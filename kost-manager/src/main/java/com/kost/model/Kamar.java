package com.kost.model;

public class Kamar {
    private int idKamar;
    private String nomorKamar;
    private String statusKamar; // "kosong", "terisi", "perbaikan"
    private int harga;
    private int biayaTambahan;

    public Kamar() {}

    public Kamar(int idKamar, String nomorKamar, String statusKamar, int harga, int biayaTambahan) {
        this.idKamar = idKamar;
        this.nomorKamar = nomorKamar;
        this.statusKamar = statusKamar;
        this.harga = harga;
        this.biayaTambahan = biayaTambahan;
    }

    public int getIdKamar() { return idKamar; }
    public void setIdKamar(int idKamar) { this.idKamar = idKamar; }

    public String getNomorKamar() { return nomorKamar; }
    public void setNomorKamar(String nomorKamar) { this.nomorKamar = nomorKamar; }

    public String getStatusKamar() { return statusKamar; }
    public void setStatusKamar(String statusKamar) { this.statusKamar = statusKamar; }

    public int getHarga() { return harga; }
    public void setHarga(int harga) { this.harga = harga; }

    public int getBiayaTambahan() { return biayaTambahan; }
    public void setBiayaTambahan(int biayaTambahan) { this.biayaTambahan = biayaTambahan; }

    public int getTotalTarif() { return harga + biayaTambahan; }

    @Override
    public String toString() {
        return "Kamar " + nomorKamar + " [" + statusKamar + "]";
    }
}