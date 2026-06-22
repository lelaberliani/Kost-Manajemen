package com.kost.model;

import java.time.LocalDate;

public class Pembayaran {
    private int id;
    private int penghuniId;
    private LocalDate bulanBayar;
    private LocalDate tanggalBayar;
    private double jumlahBayar;
    private String status; // "LUNAS" atau "MENUNGGAK"

    public Pembayaran() {}

    public Pembayaran(int id, int penghuniId, LocalDate bulanBayar,
                      LocalDate tanggalBayar, double jumlahBayar, String status) {
        this.id = id;
        this.penghuniId = penghuniId;
        this.bulanBayar = bulanBayar;
        this.tanggalBayar = tanggalBayar;
        this.jumlahBayar = jumlahBayar;
        this.status = status;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPenghuniId() { return penghuniId; }
    public void setPenghuniId(int penghuniId) { this.penghuniId = penghuniId; }

    public LocalDate getBulanBayar() { return bulanBayar; }
    public void setBulanBayar(LocalDate bulanBayar) { this.bulanBayar = bulanBayar; }

    public LocalDate getTanggalBayar() { return tanggalBayar; }
    public void setTanggalBayar(LocalDate tanggalBayar) { this.tanggalBayar = tanggalBayar; }

    public double getJumlahBayar() { return jumlahBayar; }
    public void setJumlahBayar(double jumlahBayar) { this.jumlahBayar = jumlahBayar; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Cek apakah masih dalam masa tenggang 7 hari
    public boolean isMasaTenggang(LocalDate jatuhTempo) {
        return LocalDate.now().isBefore(jatuhTempo.plusDays(7));
    }

    @Override
    public String toString() {
        return "Pembayaran [" + bulanBayar + "] - " + status;
    }
}