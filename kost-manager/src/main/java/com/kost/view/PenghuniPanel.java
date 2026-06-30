package com.kost.view;

import com.kost.controller.PenghuniController;
import com.kost.controller.KamarController;
import com.kost.model.Penghuni;
import com.kost.model.Kamar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PenghuniPanel extends JPanel {

    private PenghuniController controller = new PenghuniController();
    private KamarController kamarController = new KamarController();
    private JTable table;
    private DefaultTableModel tableModel;

    private static final Color BG = new Color(248, 250, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color ACCENT = new Color(99, 102, 241);
    private static final Color DANGER = new Color(239, 68, 68);

    public PenghuniPanel() {
        setLayout(new BorderLayout());
        setBackground(BG);
        setBorder(new EmptyBorder(32, 32, 32, 32));
        add(buildHeader(), BorderLayout.NORTH);
        add(buildTableCard(), BorderLayout.CENTER);
        loadData();
    }

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);
        panel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel titleGroup = new JPanel();
        titleGroup.setLayout(new BoxLayout(titleGroup, BoxLayout.Y_AXIS));
        titleGroup.setBackground(BG);

        JLabel title = new JLabel("Data Penghuni");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(15, 23, 42));

        JLabel subtitle = new JLabel("Kelola data penghuni kost aktif");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(100, 116, 139));

        titleGroup.add(title);
        titleGroup.add(Box.createVerticalStrut(3));
        titleGroup.add(subtitle);

        JButton btnTambah = new JButton("+ Tambah Penghuni");
        btnTambah.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnTambah.setBackground(ACCENT);
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setBorder(new EmptyBorder(10, 20, 10, 20));
        btnTambah.setFocusPainted(false);
        btnTambah.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnTambah.addActionListener(e -> showTambahDialog());

        panel.add(titleGroup, BorderLayout.WEST);
        panel.add(btnTambah, BorderLayout.EAST);
        return panel;
    }

    private JPanel buildTableCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));

        String[] columns = {"ID", "Nama", "No. HP", "No. KTP", "Kamar ID", "Jml Penghuni", "Tanggal Masuk"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(44);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(248, 250, 252));
        table.getTableHeader().setForeground(new Color(100, 116, 139));
        table.setSelectionBackground(new Color(238, 242, 255));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Sembunyikan kolom ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        card.add(scroll, BorderLayout.CENTER);

        // Action bar
        JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 12));
        actionBar.setBackground(new Color(248, 250, 252));
        actionBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(226, 232, 240)));

        JButton btnEdit = buildActionButton("Edit", ACCENT);
        JButton btnKeluar = buildActionButton("Penghuni Keluar", DANGER);
        JButton btnRefresh = buildActionButton("Refresh", new Color(100, 116, 139));

        btnEdit.addActionListener(e -> editPenghuni());
        btnKeluar.addActionListener(e -> penghuniKeluar());
        btnRefresh.addActionListener(e -> loadData());

        actionBar.add(btnEdit);
        actionBar.add(btnKeluar);
        actionBar.add(btnRefresh);
        card.add(actionBar, BorderLayout.SOUTH);

        return card;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Penghuni> list = controller.getAllPenghuni();
        for (Penghuni p : list) {
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getNama(),
                p.getNoHp(),
                p.getNoKtp(),
                p.getKamarId(),
                p.getJumlahPenghuni() + " orang",
                p.getTanggalMasuk().toString()
            });
        }
    }

    private void showTambahDialog() {
        // Ambil daftar kamar kosong
        List<Kamar> kamarList = kamarController.getAllKamar();
        List<Kamar> kamarKosong = kamarList.stream()
        .filter(k -> k.getStatusKamar().equals("kosong"))
        .collect(java.util.stream.Collectors.toList());

        if (kamarKosong.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Tidak ada kamar kosong tersedia!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JTextField tfNama = new JTextField();
        JTextField tfKtp = new JTextField();
        JTextField tfHp = new JTextField();
        JTextField tfTanggal = new JTextField(LocalDate.now().toString());
        JComboBox<String> cbKamar = new JComboBox<>();
        JComboBox<Integer> cbJumlah = new JComboBox<>(new Integer[]{1, 2});

        for (Kamar k : kamarKosong) {
        cbKamar.addItem("Kamar " + k.getNomorKamar() + " (ID: " + k.getIdKamar() + ")");
        }

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        form.add(new JLabel("Nama:")); form.add(tfNama);
        form.add(new JLabel("No. KTP:")); form.add(tfKtp);
        form.add(new JLabel("No. HP:")); form.add(tfHp);
        form.add(new JLabel("Pilih Kamar:")); form.add(cbKamar);
        form.add(new JLabel("Jumlah Penghuni:")); form.add(cbJumlah);
        form.add(new JLabel("Tanggal Masuk (YYYY-MM-DD):")); form.add(tfTanggal);

        int result = JOptionPane.showConfirmDialog(this, form,
            "Tambah Penghuni Baru", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int selectedIndex = cbKamar.getSelectedIndex();
                int kamarId = kamarKosong.get(selectedIndex).getIdKamar();
                LocalDate tanggalMasuk = LocalDate.parse(tfTanggal.getText().trim());

                boolean ok = controller.tambahPenghuni(
                    tfNama.getText(), tfKtp.getText(), tfHp.getText(),
                    kamarId, (Integer) cbJumlah.getSelectedItem(), tanggalMasuk
                );

                if (ok) { showSuccess("Penghuni berhasil ditambahkan!"); loadData(); }
                else showError("Gagal menambahkan penghuni.");
            } catch (Exception ex) {
                showError("Input tidak valid: " + ex.getMessage());
            }
        }
    }

    private void editPenghuni() {
        int row = table.getSelectedRow();
        if (row < 0) { showError("Pilih penghuni yang ingin diedit!"); return; }

        int id = (int) tableModel.getValueAt(row, 0);
        JTextField tfNama = new JTextField(tableModel.getValueAt(row, 1).toString());
        JTextField tfHp = new JTextField(tableModel.getValueAt(row, 2).toString());
        JTextField tfKtp = new JTextField(tableModel.getValueAt(row, 3).toString());
        JComboBox<Integer> cbJumlah = new JComboBox<>(new Integer[]{1, 2});

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        form.add(new JLabel("Nama:")); form.add(tfNama);
        form.add(new JLabel("No. KTP:")); form.add(tfKtp);
        form.add(new JLabel("No. HP:")); form.add(tfHp);
        form.add(new JLabel("Jumlah Penghuni:")); form.add(cbJumlah);

        int result = JOptionPane.showConfirmDialog(this, form,
            "Edit Penghuni", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                boolean ok = controller.updatePenghuni(id, tfNama.getText(),
                    tfKtp.getText(), tfHp.getText(), (Integer) cbJumlah.getSelectedItem());
                if (ok) { showSuccess("Data penghuni berhasil diupdate!"); loadData(); }
                else showError("Gagal mengupdate penghuni.");
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        }
    }

    private void penghuniKeluar() {
        int row = table.getSelectedRow();
        if (row < 0) { showError("Pilih penghuni yang ingin dikeluarkan!"); return; }

        int id = (int) tableModel.getValueAt(row, 0);
        int kamarId = (int) tableModel.getValueAt(row, 4);
        String nama = tableModel.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
            nama + " akan ditandai keluar.\nStatus kamar akan berubah jadi 'kosong'.\nLanjutkan?",
            "Konfirmasi Penghuni Keluar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.penghuniKeluar(id, kamarId)) {
                showSuccess(nama + " berhasil dikeluarkan.");
                loadData();
            } else showError("Gagal memproses penghuni keluar.");
        }
    }

    private JButton buildActionButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(color);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 1),
            new EmptyBorder(6, 16, 6, 16)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void showSuccess(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Berhasil", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}