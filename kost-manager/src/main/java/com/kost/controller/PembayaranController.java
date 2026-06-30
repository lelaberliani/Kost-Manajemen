package com.kost.controller;

import com.kost.dao.PembayaranDAO;
import com.kost.model.Pembayaran;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class PembayaranController {

    private PembayaranDAO pembayaranDAO = new PembayaranDAO();

    public List<Pembayaran> getAllPembayaran() {
        return pembayaranDAO.getAllPembayaran();
    }

    public List<Pembayaran> getPembayaranByPenghuni(int idPenghuni) {
        return pembayaranDAO.getPembayaranByPenghuni(idPenghuni);
    }

    // Buat tagihan baru. Jatuh tempo = tanggal_masuk_penghuni di bulan ini.
    public boolean buatTagihan(int idPenghuni, LocalDate tanggalJatuhTempo) {
        String namaBulan = tanggalJatuhTempo.getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("id", "ID"))
                + " " + tanggalJatuhTempo.getYear();

        Pembayaran p = new Pembayaran();
        p.setIdPenghuni(idPenghuni);
        p.setBulanTagihan(namaBulan);
        p.setTanggalJatuhTempo(tanggalJatuhTempo);
        p.setStatusPembayaran("BELUM BAYAR");

        return pembayaranDAO.tambahTagihan(p);
    }

    public boolean bayarTagihan(int idPembayaran) {
        return pembayaranDAO.bayar(idPembayaran);
    }

    // Hitung status real-time berdasarkan masa tenggang 7 hari
    public String cekStatusTagihan(Pembayaran p) {
        if (p.getStatusPembayaran().equals("LUNAS")) {
            return "LUNAS";
        }

        LocalDate batasTenggang = p.getTanggalJatuhTempo().plusDays(7);

        if (LocalDate.now().isAfter(batasTenggang)) {
            return "MENUNGGAK";
        } else if (LocalDate.now().isBefore(p.getTanggalJatuhTempo())) {
            return "BELUM JATUH TEMPO";
        } else {
            return "MASA TENGGANG (s.d. " + batasTenggang + ")";
        }
    }
}