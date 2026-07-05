package guilibararynew;

import javax.swing.JFrame;
import java.awt.*;

public class Main extends javax.swing.JFrame {

    public Main() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Main Panel
        jPanel2 = new javax.swing.JPanel();
        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new java.awt.BorderLayout());

        // ----- Header Panel (Title) -----
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new java.awt.Color(0, 0, 0));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        jLabel3 = new javax.swing.JLabel();
        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 48));
        jLabel3.setForeground(new java.awt.Color(255, 255, 0));
        jLabel3.setText("📚 Library Management System");
        headerPanel.add(jLabel3);

        jPanel2.add(headerPanel, BorderLayout.NORTH);

        // ----- Buttons Panel (Center) -----
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new java.awt.Color(0, 0, 0));
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // ----- Buttons Setup -----
        // Row 1: Category | Publisher | Member
        gbc.gridy = 0;
        gbc.gridx = 0;
        buttonPanel.add(createButton("Category", () -> new Category().setVisible(true)), gbc);
        gbc.gridx = 1;
        buttonPanel.add(createButton("Publisher", () -> new Publisher().setVisible(true)), gbc);
        gbc.gridx = 2;
        buttonPanel.add(createButton("Member", () -> new Member().setVisible(true)), gbc);

        // Row 2: Book | DVD
        gbc.gridy = 1;
        gbc.gridx = 0;
        buttonPanel.add(createButton("Book", () -> new Book().setVisible(true)), gbc);
        gbc.gridx = 1;
        buttonPanel.add(createButton("DVD", () -> new DVD().setVisible(true)), gbc);
        gbc.gridx = 2;
        buttonPanel.add(new JLabel(), gbc); // Empty spacer

        // Row 3: Author | Director
        gbc.gridy = 2;
        gbc.gridx = 0;
        buttonPanel.add(createButton("Author", () -> new Author().setVisible(true)), gbc);
        gbc.gridx = 1;
        buttonPanel.add(createButton("Director", () -> new Director().setVisible(true)), gbc);
        gbc.gridx = 2;
        buttonPanel.add(new JLabel(), gbc);

        // Row 4: Issue Book | Issue DVD
        gbc.gridy = 3;
        gbc.gridx = 0;
        buttonPanel.add(createButton("Issue Book", () -> new Lendbook().setVisible(true)), gbc);
        gbc.gridx = 1;
        buttonPanel.add(createButton("Issue DVD", () -> new LendDVD().setVisible(true)), gbc);
        gbc.gridx = 2;
        buttonPanel.add(new JLabel(), gbc);

        // Row 5: Return Book | Return DVD
        gbc.gridy = 4;
        gbc.gridx = 0;
        buttonPanel.add(createButton("Return Book", () -> new ReturnBook().setVisible(true)), gbc);
        gbc.gridx = 1;
        buttonPanel.add(createButton("Return DVD", () -> new ReturnDVD().setVisible(true)), gbc);
        gbc.gridx = 2;
        buttonPanel.add(new JLabel(), gbc);

        // Row 6: Logout (Centered)
        gbc.gridy = 5;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        javax.swing.JButton logoutBtn = new javax.swing.JButton("Logout");
        logoutBtn.setBackground(new java.awt.Color(204, 0, 0));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Times New Roman", 1, 18));
        logoutBtn.setPreferredSize(new Dimension(200, 45));
        logoutBtn.addActionListener(evt -> {
            new Login().setVisible(true);
            this.dispose();
        });
        buttonPanel.add(logoutBtn, gbc);

        jPanel2.add(buttonPanel, BorderLayout.CENTER);

        // ----- Final Setup -----
        getContentPane().add(jPanel2);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Library Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
        setLocationRelativeTo(null);
    }

    // Helper method to create buttons
    private javax.swing.JButton createButton(String text, Runnable action) {
        javax.swing.JButton btn = new javax.swing.JButton(text);
        btn.setBackground(new java.awt.Color(51, 255, 0));
        btn.setFont(new java.awt.Font("Times New Roman", 1, 16));
        btn.setPreferredSize(new java.awt.Dimension(180, 45));
        btn.addActionListener(evt -> action.run());
        return btn;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JButton jButton13, jButton14, jButton15, jButton16, jButton17, jButton18, jButton19, jButton20, jButton21, jButton22, jButton23, jButton24;
    private javax.swing.JLabel jLabel3, jLabel4;
    private javax.swing.JPanel jPanel2;
}