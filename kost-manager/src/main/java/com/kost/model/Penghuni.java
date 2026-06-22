package com.kost.model;

import java.time.LocalDate;

public class Penghuni {
    private int id;
    private String nama;
    private String noKtp;
    private String noHp;
    private int kamarId;
    private int jumlahPenghuni;
    private LocalDate tanggalMasuk;
    private LocalDate tanggalKeluar;
    private boolean isAktif;

    public Penghuni() {}

    public Penghuni(int id, String nama, String noKtp, String noHp,
                    int kamarId, int jumlahPenghuni,
                    LocalDate tanggalMasuk, LocalDate tanggalKeluar, boolean isAktif) {
        this.id = id;
        this.nama = nama;
        this.noKtp = noKtp;
        this.noHp = noHp;
        this.kamarId = kamarId;
        this.jumlahPenghuni = jumlahPenghuni;
        this.tanggalMasuk = tanggalMasuk;
        this.tanggalKeluar = tanggalKeluar;
        this.isAktif = isAktif;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNoKtp() { return noKtp; }
    public void setNoKtp(String noKtp) { this.noKtp = noKtp; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }

    public int getKamarId() { return kamarId; }
    public void setKamarId(int kamarId) { this.kamarId = kamarId; }

    public int getJumlahPenghuni() { return jumlahPenghuni; }
    public void setJumlahPenghuni(int jumlahPenghuni) { this.jumlahPenghuni = jumlahPenghuni; }

    public LocalDate getTanggalMasuk() { return tanggalMasuk; }
    public void setTanggalMasuk(LocalDate tanggalMasuk) { this.tanggalMasuk = tanggalMasuk; }

    public LocalDate getTanggalKeluar() { return tanggalKeluar; }
    public void setTanggalKeluar(LocalDate tanggalKeluar) { this.tanggalKeluar = tanggalKeluar; }

    public boolean isAktif() { return isAktif; }
    public void setAktif(boolean aktif) { isAktif = aktif; }

    @Override
    public String toString() {
        return nama + " (Kamar ID: " + kamarId + ")";
    }
}