package com.kost.dao;

import com.kost.model.Pembayaran;
import com.kost.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PembayaranDAO {

    public List<Pembayaran> getAllPembayaran() {
        List<Pembayaran> list = new ArrayList<>();
        String sql = "SELECT * FROM pembayaran ORDER BY tanggal_jatuh_tempo DESC";
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

    public List<Pembayaran> getPembayaranByPenghuni(int idPenghuni) {
        List<Pembayaran> list = new ArrayList<>();
        String sql = "SELECT * FROM pembayaran WHERE id_penghuni=? ORDER BY tanggal_jatuh_tempo DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPenghuni);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean tambahTagihan(Pembayaran p) {
        String sql = "INSERT INTO pembayaran (id_penghuni, bulan_tagihan, tanggal_jatuh_tempo, status_pembayaran) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdPenghuni());
            ps.setString(2, p.getBulanTagihan());
            ps.setDate(3, Date.valueOf(p.getTanggalJatuhTempo()));
            ps.setString(4, p.getStatusPembayaran());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean bayar(int idPembayaran) {
        String sql = "UPDATE pembayaran SET status_pembayaran='LUNAS', tanggal_bayar=? WHERE id_pembayaran=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, idPembayaran);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Pembayaran mapRow(ResultSet rs) throws SQLException {
        Pembayaran p = new Pembayaran();
        p.setIdPembayaran(rs.getInt("id_pembayaran"));
        p.setIdPenghuni(rs.getInt("id_penghuni"));
        p.setBulanTagihan(rs.getString("bulan_tagihan"));
        p.setTanggalJatuhTempo(rs.getDate("tanggal_jatuh_tempo").toLocalDate());
        Date tglBayar = rs.getDate("tanggal_bayar");
        if (tglBayar != null) p.setTanggalBayar(tglBayar.toLocalDate());
        p.setStatusPembayaran(rs.getString("status_pembayaran"));
        return p;
    }
}