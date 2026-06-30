package com.kost.model;

import java.time.LocalDate;

public class Pembayaran {
    private int idPembayaran;
    private int idPenghuni;
    private String bulanTagihan;
    private LocalDate tanggalJatuhTempo;
    private LocalDate tanggalBayar;
    private String statusPembayaran; // "BELUM JATUH TEMPO", "BELUM BAYAR", "LUNAS", "MENUNGGAK" dll

    public Pembayaran() {}

    public int getIdPembayaran() { return idPembayaran; }
    public void setIdPembayaran(int id) { this.idPembayaran = id; }

    public int getIdPenghuni() { return idPenghuni; }
    public void setIdPenghuni(int id) { this.idPenghuni = id; }

    public String getBulanTagihan() { return bulanTagihan; }
    public void setBulanTagihan(String bulanTagihan) { this.bulanTagihan = bulanTagihan; }

    public LocalDate getTanggalJatuhTempo() { return tanggalJatuhTempo; }
    public void setTanggalJatuhTempo(LocalDate t) { this.tanggalJatuhTempo = t; }

    public LocalDate getTanggalBayar() { return tanggalBayar; }
    public void setTanggalBayar(LocalDate t) { this.tanggalBayar = t; }

    public String getStatusPembayaran() { return statusPembayaran; }
    public void setStatusPembayaran(String s) { this.statusPembayaran = s; }
}