package com.kost.dao;

import com.kost.model.Pembayaran;
import com.kost.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PembayaranDAO {

    // Ambil semua pembayaran berdasarkan penghuni
    public List<Pembayaran> getPembayaranByPenghuni(int penghuniId) {
        List<Pembayaran> list = new ArrayList<>();
        String sql = "SELECT * FROM pembayaran WHERE penghuni_id=? ORDER BY bulan_bayar DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, penghuniId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Ambil semua pembayaran (untuk tampilan admin)
    public List<Pembayaran> getAllPembayaran() {
        List<Pembayaran> list = new ArrayList<>();
        String sql = "SELECT * FROM pembayaran ORDER BY bulan_bayar DESC";
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

    // Tambah record pembayaran baru
    public boolean tambahPembayaran(Pembayaran p) {
        String sql = "INSERT INTO pembayaran (penghuni_id, bulan_bayar, tanggal_bayar, jumlah_bayar, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getPenghuniId());
            ps.setDate(2, Date.valueOf(p.getBulanBayar()));
            ps.setDate(3, p.getTanggalBayar() != null ? Date.valueOf(p.getTanggalBayar()) : null);
            ps.setDouble(4, p.getJumlahBayar());
            ps.setString(5, p.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tandai pembayaran sebagai LUNAS
    public boolean bayar(int id) {
        String sql = "UPDATE pembayaran SET status='LUNAS', tanggal_bayar=? WHERE id=?";
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

    // Helper: ubah baris ResultSet jadi objek Pembayaran
    private Pembayaran mapRow(ResultSet rs) throws SQLException {
        Pembayaran p = new Pembayaran();
        p.setId(rs.getInt("id"));
        p.setPenghuniId(rs.getInt("penghuni_id"));
        p.setBulanBayar(rs.getDate("bulan_bayar").toLocalDate());
        Date tglBayar = rs.getDate("tanggal_bayar");
        if (tglBayar != null) p.setTanggalBayar(tglBayar.toLocalDate());
        p.setJumlahBayar(rs.getDouble("jumlah_bayar"));
        p.setStatus(rs.getString("status"));
        return p;
    }
}