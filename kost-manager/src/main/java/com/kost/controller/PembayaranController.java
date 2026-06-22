package com.kost.controller;

import com.kost.dao.PembayaranDAO;
import com.kost.model.Pembayaran;

import java.time.LocalDate;
import java.util.List;

public class PembayaranController {

    private PembayaranDAO pembayaranDAO = new PembayaranDAO();

    // Ambil semua pembayaran
    public List<Pembayaran> getAllPembayaran() {
        return pembayaranDAO.getAllPembayaran();
    }

    // Ambil pembayaran berdasarkan penghuni
    public List<Pembayaran> getPembayaranByPenghuni(int penghuniId) {
        return pembayaranDAO.getPembayaranByPenghuni(penghuniId);
    }

    // Buat tagihan baru untuk bulan ini
    public boolean buatTagihan(int penghuniId, double jumlahBayar) {
        // Bulan bayar = tanggal 1 bulan ini
        LocalDate bulanIni = LocalDate.now().withDayOfMonth(1);

        Pembayaran p = new Pembayaran();
        p.setPenghuniId(penghuniId);
        p.setBulanBayar(bulanIni);
        p.setJumlahBayar(jumlahBayar);
        p.setStatus("MENUNGGAK"); // default belum bayar
        p.setTanggalBayar(null);

        return pembayaranDAO.tambahPembayaran(p);
    }

    // Tandai pembayaran sebagai LUNAS
    public boolean bayarTagihan(int pembayaranId) {
        return pembayaranDAO.bayar(pembayaranId);
    }

    // Cek status tagihan — LUNAS atau MENUNGGAK dengan masa tenggang 7 hari
    public String cekStatusTagihan(Pembayaran pembayaran) {
        if (pembayaran.getStatus().equals("LUNAS")) {
            return "LUNAS";
        }

        // Jatuh tempo = tanggal 1 bulan berikutnya
        LocalDate jatuhTempo = pembayaran.getBulanBayar().plusMonths(1);
        LocalDate batasTenggang = jatuhTempo.plusDays(7);

        if (LocalDate.now().isAfter(batasTenggang)) {
            return "MENUNGGAK";
        } else {
            // Masih dalam masa tenggang
            return "MENUNGGAK (Masa Tenggang hingga " + batasTenggang + ")";
        }
    }
}