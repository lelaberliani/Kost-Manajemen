package com.kost;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        ///berfungsi untuk menambahkan extension library untuk menghias UI 
        FlatIntelliJLaf.setup();
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}