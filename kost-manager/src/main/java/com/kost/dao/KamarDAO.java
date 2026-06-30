package com.kost.dao;

import com.kost.model.Kamar;
import com.kost.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KamarDAO {

    public List<Kamar> getAllKamar() {
        List<Kamar> list = new ArrayList<>();
        String sql = "SELECT * FROM kamar";
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

    public boolean tambahKamar(Kamar kamar) {
        String sql = "INSERT INTO kamar (nomor_kamar, status_kamar, harga, biaya_tambahan) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kamar.getNomorKamar());
            ps.setString(2, kamar.getStatusKamar());
            ps.setInt(3, kamar.getHarga());
            ps.setInt(4, kamar.getBiayaTambahan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateKamar(Kamar kamar) {
        String sql = "UPDATE kamar SET nomor_kamar=?, status_kamar=?, harga=?, biaya_tambahan=? WHERE id_kamar=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kamar.getNomorKamar());
            ps.setString(2, kamar.getStatusKamar());
            ps.setInt(3, kamar.getHarga());
            ps.setInt(4, kamar.getBiayaTambahan());
            ps.setInt(5, kamar.getIdKamar());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hapusKamar(int idKamar) {
        String sql = "DELETE FROM kamar WHERE id_kamar=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idKamar);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatusKamar(int idKamar, String status) {
        String sql = "UPDATE kamar SET status_kamar=? WHERE id_kamar=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, idKamar);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Kamar mapRow(ResultSet rs) throws SQLException {
        Kamar k = new Kamar();
        k.setIdKamar(rs.getInt("id_kamar"));
        k.setNomorKamar(rs.getString("nomor_kamar"));
        k.setStatusKamar(rs.getString("status_kamar"));
        k.setHarga(rs.getInt("harga"));
        k.setBiayaTambahan(rs.getInt("biaya_tambahan"));
        return k;
    }
}