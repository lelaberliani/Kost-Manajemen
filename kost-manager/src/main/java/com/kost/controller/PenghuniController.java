package com.kost.controller;

import com.kost.dao.KamarDAO;
import com.kost.dao.PenghuniDAO;
import com.kost.model.Penghuni;

import java.time.LocalDate;
import java.util.List;

public class PenghuniController {

    private PenghuniDAO penghuniDAO = new PenghuniDAO();
    private KamarDAO kamarDAO = new KamarDAO();

    // Ambil semua penghuni aktif
    public List<Penghuni> getAllPenghuni() {
        return penghuniDAO.getAllPenghuni();
    }

    // Tambah penghuni baru + otomatis ubah status kamar jadi "terisi"
    public boolean tambahPenghuni(String nama, String noKtp, String noHp,
                                   int kamarId, int jumlahPenghuni, LocalDate tanggalMasuk) {
        // Validasi nama
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama penghuni tidak boleh kosong!");
        }

        // Validasi jumlah penghuni
        if (jumlahPenghuni < 1 || jumlahPenghuni > 2) {
            throw new IllegalArgumentException("Jumlah penghuni hanya boleh 1 atau 2!");
        }

        // Validasi tanggal masuk
        if (tanggalMasuk == null) {
            throw new IllegalArgumentException("Tanggal masuk tidak boleh kosong!");
        }

        Penghuni p = new Penghuni();
        p.setNama(nama.trim());
        p.setNoKtp(noKtp);
        p.setNoHp(noHp);
        p.setKamarId(kamarId);
        p.setJumlahPenghuni(jumlahPenghuni);
        p.setTanggalMasuk(tanggalMasuk);
        p.setAktif(true);

        boolean berhasil = penghuniDAO.tambahPenghuni(p);

        // Kalau berhasil tambah penghuni, ubah status kamar jadi "terisi"
        if (berhasil) {
            kamarDAO.updateStatusKamar(kamarId, "terisi");
        }

        return berhasil;
    }

    // Update data penghuni
    public boolean updatePenghuni(int id, String nama, String noKtp,
                                   String noHp, int jumlahPenghuni) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama penghuni tidak boleh kosong!");
        }

        Penghuni p = new Penghuni();
        p.setId(id);
        p.setNama(nama.trim());
        p.setNoKtp(noKtp);
        p.setNoHp(noHp);
        p.setJumlahPenghuni(jumlahPenghuni);

        return penghuniDAO.updatePenghuni(p);
    }

    // Penghuni keluar — soft delete + ubah status kamar jadi "kosong"
    public boolean penghuniKeluar(int penghuniId, int kamarId) {
        boolean berhasil = penghuniDAO.nonaktifkanPenghuni(penghuniId);

        // Kalau berhasil nonaktifkan, ubah status kamar jadi "kosong"
        if (berhasil) {
            kamarDAO.updateStatusKamar(kamarId, "kosong");
        }

        return berhasil;
    }
}