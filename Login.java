package guilibararynew;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = UITheme.card();
        jLabel2 = new JLabel("Username");
        jLabel3 = new JLabel("Password");
        txtusername = new JTextField();
        txtpass = new JPasswordField();
        jButton1 = UITheme.button("Login");
        jButton2 = UITheme.button("Cancel");
        jLabel4 = UITheme.title("Library Login");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Library Management System - Login");
        setMinimumSize(new Dimension(920, 560));

        JLabel subtitle = new JLabel("Sign in to manage books, members, lending, and returns.");
        subtitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        subtitle.setForeground(UITheme.MUTED_TEXT);

        jButton1.addActionListener(this::jButton1ActionPerformed);
        jButton2.addActionListener(this::jButton2ActionPerformed);

        JPanel header = new JPanel(new GridBagLayout());
        header.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        header.add(jLabel4, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(6, 0, 0, 0);
        header.add(subtitle, gbc);

        jPanel1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 28, 0);
        jPanel1.add(header, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 8, 16);
        jPanel1.add(jLabel2, gbc);
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 8, 0);
        jPanel1.add(txtusername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 8, 16);
        jPanel1.add(jLabel3, gbc);
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 0, 8, 0);
        jPanel1.add(txtpass, gbc);

        JPanel actions = new JPanel(new GridBagLayout());
        actions.setOpaque(false);
        GridBagConstraints agbc = new GridBagConstraints();
        agbc.gridx = 0;
        agbc.insets = new Insets(0, 0, 0, 12);
        actions.add(jButton1, agbc);
        agbc.gridx = 1;
        agbc.insets = new Insets(0, 0, 0, 0);
        actions.add(jButton2, agbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(26, 0, 0, 0);
        jPanel1.add(actions, gbc);

        JPanel page = new JPanel(new GridBagLayout());
        page.setBackground(UITheme.BACKGROUND);
        page.setBorder(BorderFactory.createEmptyBorder(48, 48, 48, 48));
        page.add(jPanel1);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(page, BorderLayout.CENTER);
        UITheme.applyFrame(this);

        pack();
        setLocationRelativeTo(null);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String username = txtusername.getText().trim();
        String pass = new String(txtpass.getPassword());

        if (username.equals("Yashika") && pass.equals("123")) {
            Main m = new Main();
            this.dispose();
            m.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Username or password does not match.");
            txtusername.setText("");
            txtpass.setText("");
            txtusername.requestFocus();
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    public static void main(String args[]) {
        UITheme.install();
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    private JButton jButton1;
    private JButton jButton2;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    private JPasswordField txtpass;
    private JTextField txtusername;
}
