package com.kost.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    // Warna tema
    private static final Color SIDEBAR_BG = new Color(30, 41, 59);
    private static final Color SIDEBAR_HOVER = new Color(51, 65, 85);
    private static final Color SIDEBAR_ACTIVE = new Color(99, 102, 241);
    private static final Color ACCENT = new Color(99, 102, 241);
    private static final Color TEXT_LIGHT = new Color(248, 250, 252);
    private static final Color TEXT_MUTED = new Color(148, 163, 184);

    private JButton activeButton = null;

    public MainFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Sistem Manajemen Indekos — Jimbaran");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));

        // Layout utama: sidebar kiri + konten kanan
        setLayout(new BorderLayout());

        add(buildSidebar(), BorderLayout.WEST);
        add(buildContent(), BorderLayout.CENTER);
    }

    // ── SIDEBAR ──────────────────────────────────────────────
    private JPanel buildSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(SIDEBAR_BG);
        sidebarPanel.setPreferredSize(new Dimension(220, getHeight()));
        sidebarPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Logo / judul app
        sidebarPanel.add(buildLogoPanel());

        // Divider
        sidebarPanel.add(buildDivider());

        // Label section
        sidebarPanel.add(buildSectionLabel("MENU UTAMA"));

        // Tombol navigasi
        JButton btnDashboard  = buildNavButton("▪  Dashboard", "dashboard");
        JButton btnKamar      = buildNavButton("▪  Manajemen Kamar", "kamar");
        JButton btnPenghuni   = buildNavButton("▪  Data Penghuni", "penghuni");
        JButton btnPembayaran = buildNavButton("▪  Pembayaran", "pembayaran");

        sidebarPanel.add(btnDashboard);
        sidebarPanel.add(btnKamar);
        sidebarPanel.add(btnPenghuni);
        sidebarPanel.add(btnPembayaran);

        // Spacer dorong tombol ke bawah
        sidebarPanel.add(Box.createVerticalGlue());

        // Divider bawah
        sidebarPanel.add(buildDivider());

        // Info versi
        sidebarPanel.add(buildVersionLabel());

        // Set dashboard aktif by default
        setActiveButton(btnDashboard);

        return sidebarPanel;
    }

    private JPanel buildLogoPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(SIDEBAR_BG);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(28, 20, 20, 20));
        panel.setMaximumSize(new Dimension(220, 90));

        JLabel icon = new JLabel("KOST");
        icon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        icon.setForeground(SIDEBAR_ACTIVE);

        JLabel title = new JLabel("KostManager");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(TEXT_LIGHT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Jimbaran, Bali");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(icon);
        panel.add(Box.createVerticalStrut(4));
        panel.add(title);
        panel.add(subtitle);

        return panel;
    }

    private JButton buildNavButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(220, 44));
        btn.setPreferredSize(new Dimension(220, 44));
        btn.setMinimumSize(new Dimension(220, 44));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(TEXT_MUTED);
        btn.setBackground(SIDEBAR_BG);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn != activeButton) {
                    btn.setBackground(SIDEBAR_HOVER);
                    btn.setForeground(TEXT_LIGHT);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (btn != activeButton) {
                    btn.setBackground(SIDEBAR_BG);
                    btn.setForeground(TEXT_MUTED);
                }
            }
        });

        // Klik → pindah panel
        btn.addActionListener(e -> {
            setActiveButton(btn);
            cardLayout.show(contentPanel, cardName);
        });

        return btn;
    }

    private void setActiveButton(JButton btn) {
        // Reset tombol sebelumnya
        if (activeButton != null) {
            activeButton.setBackground(SIDEBAR_BG);
            activeButton.setForeground(TEXT_MUTED);
            activeButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }
        // Set tombol aktif
        activeButton = btn;
        activeButton.setBackground(SIDEBAR_ACTIVE);
        activeButton.setForeground(Color.WHITE);
        activeButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    private JSeparator buildDivider() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(51, 65, 85));
        sep.setMaximumSize(new Dimension(220, 1));
        return sep;
    }

    private JLabel buildSectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(TEXT_MUTED);
        lbl.setBorder(new EmptyBorder(16, 20, 6, 20));
        lbl.setMaximumSize(new Dimension(220, 36));
        return lbl;
    }

    private JLabel buildVersionLabel() {
        JLabel lbl = new JLabel("v1.0.0 — PBO 2025");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lbl.setForeground(TEXT_MUTED);
        lbl.setBorder(new EmptyBorder(12, 20, 16, 20));
        lbl.setMaximumSize(new Dimension(220, 36));
        return lbl;
    }

    // ── CONTENT AREA ─────────────────────────────────────────
    private JPanel buildContent() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(248, 250, 252));

        // Placeholder panel untuk setiap halaman
        contentPanel.add(buildPlaceholder("[ # ]", "Dashboard", "Selamat datang di KostManager!"), "dashboard");
        contentPanel.add(buildPlaceholder("[ K ]", "Manajemen Kamar", "Kelola data kamar kost di sini."), "kamar");
        contentPanel.add(buildPlaceholder("[ P ]", "Data Penghuni", "Kelola data penghuni kost di sini."), "penghuni");
        contentPanel.add(buildPlaceholder("[ $ ]", "Pembayaran", "Kelola tagihan dan pembayaran di sini."), "pembayaran");

        return contentPanel;
    }

    private JPanel buildPlaceholder(String emoji, String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(248, 250, 252));
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 250, 252));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel titleLabel = new JLabel(emoji + "  " + title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(15, 23, 42));

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 116, 139));

        JPanel titleGroup = new JPanel();
        titleGroup.setLayout(new BoxLayout(titleGroup, BoxLayout.Y_AXIS));
        titleGroup.setBackground(new Color(248, 250, 252));
        titleGroup.add(titleLabel);
        titleGroup.add(Box.createVerticalStrut(4));
        titleGroup.add(subtitleLabel);

        headerPanel.add(titleGroup, BorderLayout.WEST);
        panel.add(headerPanel);
        panel.add(Box.createVerticalStrut(24));

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(226, 232, 240));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        panel.add(sep);
        panel.add(Box.createVerticalStrut(40));

        // Ikon placeholder tengah
        JLabel iconBig = new JLabel(emoji, SwingConstants.CENTER);
        iconBig.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 56));
        iconBig.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel comingSoon = new JLabel("Panel ini akan segera diisi", SwingConstants.CENTER);
        comingSoon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comingSoon.setForeground(new Color(148, 163, 184));
        comingSoon.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(iconBig);
        panel.add(Box.createVerticalStrut(12));
        panel.add(comingSoon);

        return panel;
    }
}