package com.kost.controller;

import com.kost.dao.KamarDAO;
import com.kost.model.Kamar;

import java.util.List;

public class KamarController {

    private KamarDAO kamarDAO = new KamarDAO();

    public List<Kamar> getAllKamar() {
        return kamarDAO.getAllKamar();
    }

    public boolean tambahKamar(String nomorKamar, int hargaDasar, int biayaTambahan) {
        if (nomorKamar == null || nomorKamar.trim().isEmpty()) {
            throw new IllegalArgumentException("Nomor kamar tidak boleh kosong!");
        }

        Kamar kamar = new Kamar();
        kamar.setNomorKamar(nomorKamar.trim());
        kamar.setStatusKamar("kosong");
        kamar.setHarga(hargaDasar);
        kamar.setBiayaTambahan(biayaTambahan);

        return kamarDAO.tambahKamar(kamar);
    }

    public boolean updateKamar(int idKamar, String nomorKamar, String statusKamar, int harga, int biayaTambahan) {
        if (nomorKamar == null || nomorKamar.trim().isEmpty()) {
            throw new IllegalArgumentException("Nomor kamar tidak boleh kosong!");
        }

        Kamar kamar = new Kamar();
        kamar.setIdKamar(idKamar);
        kamar.setNomorKamar(nomorKamar.trim());
        kamar.setStatusKamar(statusKamar);
        kamar.setHarga(harga);
        kamar.setBiayaTambahan(biayaTambahan);

        return kamarDAO.updateKamar(kamar);
    }

    public boolean hapusKamar(int idKamar) {
        return kamarDAO.hapusKamar(idKamar);
    }

    public boolean updateStatusKamar(int idKamar, String status) {
        return kamarDAO.updateStatusKamar(idKamar, status);
    }
}