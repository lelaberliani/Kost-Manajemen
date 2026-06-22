package com.kost.controller;

import com.kost.dao.KamarDAO;
import com.kost.model.Kamar;

import java.util.List;

public class KamarController {

    private KamarDAO kamarDAO = new KamarDAO();

    // Ambil semua kamar
    public List<Kamar> getAllKamar() {
        return kamarDAO.getAllKamar();
    }

    // Tambah kamar baru
    public boolean tambahKamar(String nomorKamar, int kapasitas) {
        // Validasi nomor kamar tidak boleh kosong
        if (nomorKamar == null || nomorKamar.trim().isEmpty()) {
            throw new IllegalArgumentException("Nomor kamar tidak boleh kosong!");
        }

        // Kapasitas hanya boleh 1 atau 2
        if (kapasitas < 1 || kapasitas > 2) {
            throw new IllegalArgumentException("Kapasitas kamar hanya boleh 1 atau 2!");
        }

        // Tarif otomatis berdasarkan kapasitas
        double tarif = kapasitas == 2 ? 2000000 : 1700000;

        Kamar kamar = new Kamar();
        kamar.setNomorKamar(nomorKamar.trim());
        kamar.setStatus("kosong");
        kamar.setKapasitas(kapasitas);
        kamar.setTarif(tarif);

        return kamarDAO.tambahKamar(kamar);
    }

    // Update kamar
    public boolean updateKamar(int id, String nomorKamar, int kapasitas, String status) {
        if (nomorKamar == null || nomorKamar.trim().isEmpty()) {
            throw new IllegalArgumentException("Nomor kamar tidak boleh kosong!");
        }

        double tarif = kapasitas == 2 ? 2000000 : 1700000;

        Kamar kamar = new Kamar();
        kamar.setId(id);
        kamar.setNomorKamar(nomorKamar.trim());
        kamar.setStatus(status);
        kamar.setKapasitas(kapasitas);
        kamar.setTarif(tarif);

        return kamarDAO.updateKamar(kamar);
    }

    // Hapus kamar
    public boolean hapusKamar(int id) {
        return kamarDAO.hapusKamar(id);
    }

    // Update status kamar saja
    public boolean updateStatusKamar(int id, String status) {
        return kamarDAO.updateStatusKamar(id, status);
    }
}