package com.kost.dao;

import com.kost.model.Kamar;
import com.kost.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KamarDAO {

    // Ambil semua kamar
    public List<Kamar> getAllKamar() {
        List<Kamar> list = new ArrayList<>();
        String sql = "SELECT * FROM kamar";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Kamar k = new Kamar(
                    rs.getInt("id"),
                    rs.getString("nomor_kamar"),
                    rs.getString("status"),
                    rs.getInt("kapasitas"),
                    rs.getDouble("tarif")
                );
                list.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Tambah kamar baru
    public boolean tambahKamar(Kamar kamar) {
        String sql = "INSERT INTO kamar (nomor_kamar, status, kapasitas, tarif) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kamar.getNomorKamar());
            ps.setString(2, kamar.getStatus());
            ps.setInt(3, kamar.getKapasitas());
            ps.setDouble(4, kamar.getTarif());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update kamar
    public boolean updateKamar(Kamar kamar) {
        String sql = "UPDATE kamar SET nomor_kamar=?, status=?, kapasitas=?, tarif=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kamar.getNomorKamar());
            ps.setString(2, kamar.getStatus());
            ps.setInt(3, kamar.getKapasitas());
            ps.setDouble(4, kamar.getTarif());
            ps.setInt(5, kamar.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hapus kamar
    public boolean hapusKamar(int id) {
        String sql = "DELETE FROM kamar WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update status kamar saja (kosong/terisi/perbaikan)
    public boolean updateStatusKamar(int id, String status) {
        String sql = "UPDATE kamar SET status=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}