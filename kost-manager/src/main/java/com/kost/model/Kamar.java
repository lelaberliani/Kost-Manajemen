package com.kost.model;

public class Kamar {
    private int id;
    private String nomorKamar;
    private String status; // "kosong", "terisi", "perbaikan"
    private int kapasitas;
    private double tarif;

    public Kamar() {}

    public Kamar(int id, String nomorKamar, String status, int kapasitas, double tarif) {
        this.id = id;
        this.nomorKamar = nomorKamar;
        this.status = status;
        this.kapasitas = kapasitas;
        this.tarif = tarif;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomorKamar() { return nomorKamar; }
    public void setNomorKamar(String nomorKamar) { this.nomorKamar = nomorKamar; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getKapasitas() { return kapasitas; }
    public void setKapasitas(int kapasitas) { this.kapasitas = kapasitas; }

    public double getTarif() { return tarif; }
    public void setTarif(double tarif) { this.tarif = tarif; }

    // Hitung tarif otomatis berdasarkan jumlah penghuni
    public double hitungTarif(int jumlahPenghuni) {
        if (jumlahPenghuni == 2) return 2000000;
        return 1700000;
    }

    @Override
    public String toString() {
        return "Kamar " + nomorKamar + " [" + status + "]";
    }
}
