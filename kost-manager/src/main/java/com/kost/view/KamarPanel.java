package com.kost.view;

import com.kost.controller.KamarController;
import com.kost.model.Kamar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class KamarPanel extends JPanel {

    private KamarController controller = new KamarController();
    private JTable table;
    private DefaultTableModel tableModel;

    private static final Color BG = new Color(248, 250, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color ACCENT = new Color(99, 102, 241);
    private static final Color DANGER = new Color(239, 68, 68);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color WARNING = new Color(234, 179, 8);

    public KamarPanel() {
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

        JLabel title = new JLabel("Manajemen Kamar");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(15, 23, 42));

        JLabel subtitle = new JLabel("Kelola data kamar dan status hunian");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(100, 116, 139));

        titleGroup.add(title);
        titleGroup.add(Box.createVerticalStrut(3));
        titleGroup.add(subtitle);

        JButton btnTambah = new JButton("+ Tambah Kamar");
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

        String[] columns = {"ID", "Nomor Kamar", "Status", "Harga Dasar", "Biaya Tambahan", "Total/Bulan"};
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

        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                String status = v.toString();
                switch (status) {
                    case "kosong": lbl.setForeground(SUCCESS); break;
                    case "terisi": lbl.setForeground(DANGER); break;
                    case "perbaikan": lbl.setForeground(WARNING); break;
                }
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                return lbl;
            }
        });

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        card.add(scroll, BorderLayout.CENTER);

        JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 12));
        actionBar.setBackground(new Color(248, 250, 252));
        actionBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(226, 232, 240)));

        JButton btnEdit = buildActionButton("Edit", ACCENT);
        JButton btnHapus = buildActionButton("Hapus", DANGER);
        JButton btnRefresh = buildActionButton("Refresh", new Color(100, 116, 139));

        btnEdit.addActionListener(e -> editKamar());
        btnHapus.addActionListener(e -> hapusKamar());
        btnRefresh.addActionListener(e -> loadData());

        actionBar.add(btnEdit);
        actionBar.add(btnHapus);
        actionBar.add(btnRefresh);
        card.add(actionBar, BorderLayout.SOUTH);

        return card;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Kamar> list = controller.getAllKamar();
        for (Kamar k : list) {
            tableModel.addRow(new Object[]{
                k.getIdKamar(),
                k.getNomorKamar(),
                k.getStatusKamar(),
                "Rp " + String.format("%,d", k.getHarga()),
                "Rp " + String.format("%,d", k.getBiayaTambahan()),
                "Rp " + String.format("%,d", k.getTotalTarif())
            });
        }
    }

    private void showTambahDialog() {
        JTextField tfNomor = new JTextField();
        JTextField tfHarga = new JTextField("1700000");
        JTextField tfBiayaTambahan = new JTextField("0");

        JPanel form = buildForm(
            new String[]{"Nomor Kamar:", "Harga Dasar (Rp):", "Biaya Tambahan (Rp):"},
            new JComponent[]{tfNomor, tfHarga, tfBiayaTambahan}
        );

        int result = JOptionPane.showConfirmDialog(this, form,
                "Tambah Kamar Baru", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                boolean ok = controller.tambahKamar(
                    tfNomor.getText(),
                    Integer.parseInt(tfHarga.getText().trim()),
                    Integer.parseInt(tfBiayaTambahan.getText().trim())
                );
                if (ok) { showSuccess("Kamar berhasil ditambahkan!"); loadData(); }
                else showError("Gagal menambahkan kamar.");
            } catch (NumberFormatException ex) {
                showError("Harga dan biaya tambahan harus berupa angka!");
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        }
    }

    private void editKamar() {
        int row = table.getSelectedRow();
        if (row < 0) { showError("Pilih kamar yang ingin diedit!"); return; }

        int id = (int) tableModel.getValueAt(row, 0);
        String nomorLama = tableModel.getValueAt(row, 1).toString();
        String statusLama = tableModel.getValueAt(row, 2).toString();

        JTextField tfNomor = new JTextField(nomorLama);
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"kosong", "terisi", "perbaikan"});
        cbStatus.setSelectedItem(statusLama);
        JTextField tfHarga = new JTextField("1700000");
        JTextField tfBiayaTambahan = new JTextField("0");

        JPanel form = buildForm(
            new String[]{"Nomor Kamar:", "Status:", "Harga Dasar (Rp):", "Biaya Tambahan (Rp):"},
            new JComponent[]{tfNomor, cbStatus, tfHarga, tfBiayaTambahan}
        );

        int result = JOptionPane.showConfirmDialog(this, form,
                "Edit Kamar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                boolean ok = controller.updateKamar(id, tfNomor.getText(),
                    (String) cbStatus.getSelectedItem(),
                    Integer.parseInt(tfHarga.getText().trim()),
                    Integer.parseInt(tfBiayaTambahan.getText().trim()));
                if (ok) { showSuccess("Kamar berhasil diupdate!"); loadData(); }
                else showError("Gagal mengupdate kamar.");
            } catch (NumberFormatException ex) {
                showError("Harga dan biaya tambahan harus berupa angka!");
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        }
    }

    private void hapusKamar() {
        int row = table.getSelectedRow();
        if (row < 0) { showError("Pilih kamar yang ingin dihapus!"); return; }

        int id = (int) tableModel.getValueAt(row, 0);
        String nomor = tableModel.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus Kamar " + nomor + "?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.hapusKamar(id)) { showSuccess("Kamar berhasil dihapus!"); loadData(); }
            else showError("Gagal menghapus kamar.");
        }
    }

    private JPanel buildForm(String[] labels, JComponent[] fields) {
        JPanel panel = new JPanel(new GridLayout(labels.length, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            panel.add(lbl);
            panel.add(fields[i]);
        }
        return panel;
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