package com.kost.dao;

import com.kost.model.Penghuni;
import com.kost.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PenghuniDAO {

    // Ambil semua penghuni yang aktif
    public List<Penghuni> getAllPenghuni() {
        List<Penghuni> list = new ArrayList<>();
        String sql = "SELECT * FROM penghuni WHERE is_aktif = 1";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Tambah penghuni baru
    public boolean tambahPenghuni(Penghuni p) {
        String sql = "INSERT INTO penghuni (nama, no_ktp, no_hp, kamar_id, jumlah_penghuni, tanggal_masuk, is_aktif) VALUES (?, ?, ?, ?, ?, ?, 1)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setString(2, p.getNoKtp());
            ps.setString(3, p.getNoHp());
            ps.setInt(4, p.getKamarId());
            ps.setInt(5, p.getJumlahPenghuni());
            ps.setDate(6, Date.valueOf(p.getTanggalMasuk()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update data penghuni
    public boolean updatePenghuni(Penghuni p) {
        String sql = "UPDATE penghuni SET nama=?, no_ktp=?, no_hp=?, jumlah_penghuni=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setString(2, p.getNoKtp());
            ps.setString(3, p.getNoHp());
            ps.setInt(4, p.getJumlahPenghuni());
            ps.setInt(5, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Soft delete — ubah is_aktif jadi 0, catat tanggal keluar
    public boolean nonaktifkanPenghuni(int id) {
        String sql = "UPDATE penghuni SET is_aktif=0, tanggal_keluar=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper: ubah baris ResultSet jadi objek Penghuni
    private Penghuni mapRow(ResultSet rs) throws SQLException {
        Penghuni p = new Penghuni();
        p.setId(rs.getInt("id"));
        p.setNama(rs.getString("nama"));
        p.setNoKtp(rs.getString("no_ktp"));
        p.setNoHp(rs.getString("no_hp"));
        p.setKamarId(rs.getInt("kamar_id"));
        p.setJumlahPenghuni(rs.getInt("jumlah_penghuni"));
        p.setTanggalMasuk(rs.getDate("tanggal_masuk").toLocalDate());
        Date tglKeluar = rs.getDate("tanggal_keluar");
        if (tglKeluar != null) p.setTanggalKeluar(tglKeluar.toLocalDate());
        p.setAktif(rs.getInt("is_aktif") == 1);
        return p;
    }
}