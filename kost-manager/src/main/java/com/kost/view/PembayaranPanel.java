package com.kost.view;

import com.kost.controller.PembayaranController;
import com.kost.controller.PenghuniController;
import com.kost.model.Pembayaran;
import com.kost.model.Penghuni;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PembayaranPanel extends JPanel {

    private PembayaranController controller = new PembayaranController();
    private PenghuniController penghuniController = new PenghuniController();
    private JTable table;
    private DefaultTableModel tableModel;

    private static final Color BG = new Color(248, 250, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color ACCENT = new Color(99, 102, 241);
    private static final Color DANGER = new Color(239, 68, 68);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color WARNING = new Color(234, 179, 8);

    public PembayaranPanel() {
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

        JLabel title = new JLabel("Pembayaran");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(15, 23, 42));

        JLabel subtitle = new JLabel("Catat dan pantau status pembayaran bulanan penghuni");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(100, 116, 139));

        titleGroup.add(title);
        titleGroup.add(Box.createVerticalStrut(3));
        titleGroup.add(subtitle);

        JButton btnBuat = new JButton("+ Buat Tagihan");
        btnBuat.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBuat.setBackground(ACCENT);
        btnBuat.setForeground(Color.WHITE);
        btnBuat.setBorder(new EmptyBorder(10, 20, 10, 20));
        btnBuat.setFocusPainted(false);
        btnBuat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBuat.addActionListener(e -> showBuatTagihanDialog());

        panel.add(titleGroup, BorderLayout.WEST);
        panel.add(btnBuat, BorderLayout.EAST);
        return panel;
    }

    private JPanel buildTableCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));

        String[] columns = {"ID", "ID Penghuni", "Bulan Tagihan", "Jatuh Tempo", "Tgl Bayar", "Status"};
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

        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                String status = v.toString();
                if (status.equals("LUNAS")) lbl.setForeground(SUCCESS);
                else if (status.startsWith("MASA TENGGANG")) lbl.setForeground(WARNING);
                else lbl.setForeground(DANGER);
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

        JButton btnLunas = buildActionButton("Tandai LUNAS", SUCCESS);
        JButton btnRefresh = buildActionButton("Refresh", new Color(100, 116, 139));

        btnLunas.addActionListener(e -> tandaiLunas());
        btnRefresh.addActionListener(e -> loadData());

        actionBar.add(btnLunas);
        actionBar.add(btnRefresh);
        card.add(actionBar, BorderLayout.SOUTH);

        return card;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Pembayaran> list = controller.getAllPembayaran();
        for (Pembayaran p : list) {
            tableModel.addRow(new Object[]{
                p.getIdPembayaran(),
                p.getIdPenghuni(),
                p.getBulanTagihan(),
                p.getTanggalJatuhTempo().toString(),
                p.getTanggalBayar() != null ? p.getTanggalBayar().toString() : "-",
                controller.cekStatusTagihan(p)
            });
        }
    }

    private void showBuatTagihanDialog() {
        List<Penghuni> list = penghuniController.getAllPenghuni();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Belum ada penghuni aktif!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JComboBox<String> cbPenghuni = new JComboBox<>();
        for (Penghuni p : list) {
            cbPenghuni.addItem(p.getNama() + " (ID: " + p.getId() + ")");
        }
        JTextField tfJatuhTempo = new JTextField(LocalDate.now().toString());

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        form.add(new JLabel("Penghuni:")); form.add(cbPenghuni);
        form.add(new JLabel("Tanggal Jatuh Tempo (YYYY-MM-DD):")); form.add(tfJatuhTempo);

        int result = JOptionPane.showConfirmDialog(this, form,
            "Buat Tagihan Baru", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int idPenghuni = list.get(cbPenghuni.getSelectedIndex()).getId();
                LocalDate jatuhTempo = LocalDate.parse(tfJatuhTempo.getText().trim());
                boolean ok = controller.buatTagihan(idPenghuni, jatuhTempo);
                if (ok) { showSuccess("Tagihan berhasil dibuat!"); loadData(); }
                else showError("Gagal membuat tagihan.");
            } catch (Exception ex) {
                showError("Input tidak valid: " + ex.getMessage());
            }
        }
    }

    private void tandaiLunas() {
        int row = table.getSelectedRow();
        if (row < 0) { showError("Pilih tagihan yang ingin ditandai LUNAS!"); return; }

        String status = tableModel.getValueAt(row, 5).toString();
        if (status.equals("LUNAS")) { showError("Tagihan ini sudah LUNAS!"); return; }

        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Tandai tagihan ini sebagai LUNAS?",
            "Konfirmasi Pembayaran", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.bayarTagihan(id)) {
                showSuccess("Pembayaran berhasil dicatat sebagai LUNAS!");
                loadData();
            } else showError("Gagal memperbarui status pembayaran.");
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