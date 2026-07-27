package guilibararynew;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Member extends javax.swing.JFrame {

    public Member() {
        initComponents();
        Connect();
        Member_Load();
    }

    Connection con;
    PreparedStatement pat;
    ResultSet rs;

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/guilibrarynew", "root", "");
        } catch (ClassNotFoundException ex) {
            System.out.println("MySQL Driver not found!");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("Database Connection Failed!");
            ex.printStackTrace();
        }
    }

    public void Member_Load() {
        if (con == null) {
            return;
        }

        try {
            pat = con.prepareStatement("select * from Member");
            rs = pat.executeQuery();

            DefaultTableModel d = (DefaultTableModel) jTable1.getModel();
            d.setRowCount(0);

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("address"));
                row.add(rs.getString("phone"));
                d.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new JPanel(new BorderLayout(24, 24));
        jPanel1.setBorder(BorderFactory.createEmptyBorder(32, 40, 40, 40));
        jLabel1 = UITheme.title("Member");
        jLabel3 = new JLabel("Name");
        jLabel4 = new JLabel("Address");
        jLabel5 = new JLabel("Phone");
        txtname = new JTextField();
        txtaddress = new JTextArea();
        txtphone = new JTextField();
        jButton1 = UITheme.button("Add");
        jButton2 = UITheme.button("Update");
        jButton3 = UITheme.button("Delete");
        jButton4 = UITheme.button("Cancel");
        jScrollPane1 = new JScrollPane();
        jScrollPane2 = new JScrollPane();
        jTable1 = new JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Member Management");
        setMinimumSize(new Dimension(1040, 640));

        JLabel subtitle = new JLabel("Create and maintain library member contact records.");
        subtitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 15));
        subtitle.setForeground(UITheme.MUTED_TEXT);

        JPanel header = new JPanel(new BorderLayout(0, 6));
        header.setOpaque(false);
        header.add(jLabel1, BorderLayout.NORTH);
        header.add(subtitle, BorderLayout.CENTER);
        jPanel1.add(header, BorderLayout.NORTH);

        JPanel formPanel = UITheme.card();
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 8, 0);
        formPanel.add(jLabel3, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 18, 0);
        formPanel.add(txtname, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 0, 8, 0);
        formPanel.add(jLabel4, gbc);

        txtaddress.setColumns(20);
        txtaddress.setRows(4);
        jScrollPane2.setViewportView(txtaddress);
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.4;
        gbc.insets = new Insets(0, 0, 18, 0);
        formPanel.add(jScrollPane2, gbc);

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.insets = new Insets(0, 0, 8, 0);
        formPanel.add(jLabel5, gbc);

        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 26, 0);
        formPanel.add(txtphone, gbc);

        JPanel actionPanel = new JPanel(new GridBagLayout());
        actionPanel.setOpaque(false);
        GridBagConstraints agbc = new GridBagConstraints();
        agbc.gridx = 0;
        agbc.gridy = 0;
        agbc.insets = new Insets(0, 0, 12, 12);
        actionPanel.add(jButton1, agbc);
        agbc.gridx = 1;
        agbc.insets = new Insets(0, 0, 12, 0);
        actionPanel.add(jButton2, agbc);
        agbc.gridx = 0;
        agbc.gridy = 1;
        agbc.insets = new Insets(0, 0, 0, 12);
        actionPanel.add(jButton3, agbc);
        agbc.gridx = 1;
        agbc.insets = new Insets(0, 0, 0, 0);
        actionPanel.add(jButton4, agbc);

        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(actionPanel, gbc);

        jTable1.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Member Name", "Address", "Phone No"}
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        JPanel tablePanel = UITheme.card();
        tablePanel.setLayout(new BorderLayout(0, 12));
        JLabel tableTitle = new JLabel("Member Records");
        tableTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(jScrollPane1, BorderLayout.CENTER);

        JPanel content = new JPanel(new BorderLayout(24, 0));
        content.setOpaque(false);
        formPanel.setPreferredSize(new Dimension(380, 480));
        content.add(formPanel, BorderLayout.WEST);
        content.add(tablePanel, BorderLayout.CENTER);
        jPanel1.add(content, BorderLayout.CENTER);

        jButton1.addActionListener(this::jButton1ActionPerformed);
        jButton2.addActionListener(this::jButton2ActionPerformed);
        jButton3.addActionListener(this::jButton3ActionPerformed);
        jButton4.addActionListener(this::jButton4ActionPerformed);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jPanel1, BorderLayout.CENTER);
        UITheme.applyFrame(this);

        pack();
        setLocationRelativeTo(null);
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel d1 = (DefaultTableModel) jTable1.getModel();
        int selectIndex = jTable1.getSelectedRow();

        if (selectIndex < 0) {
            JOptionPane.showMessageDialog(this, "Please select a member to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Delete the selected member?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int id = Integer.parseInt(d1.getValueAt(selectIndex, 0).toString());

        try {
            pat = con.prepareStatement("delete from member where id = ?");
            pat.setInt(1, id);
            int k = pat.executeUpdate();

            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Member deleted.");
                clearFields();
                Member_Load();
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting member.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String name = txtname.getText().trim();
        String address = txtaddress.getText().trim();
        String phone = txtphone.getText().trim();

        if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        try {
            pat = con.prepareStatement("insert into member(name, address, phone) values(?,?,?)");
            pat.setString(1, name);
            pat.setString(2, address);
            pat.setString(3, phone);
            int k = pat.executeUpdate();

            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Member created.");
                clearFields();
                Member_Load();
            } else {
                JOptionPane.showMessageDialog(this, "Error creating member.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        DefaultTableModel d1 = (DefaultTableModel) jTable1.getModel();
        int selectIndex = jTable1.getSelectedRow();

        if (selectIndex < 0) {
            return;
        }

        txtname.setText(d1.getValueAt(selectIndex, 1).toString());
        txtaddress.setText(d1.getValueAt(selectIndex, 2).toString());
        txtphone.setText(d1.getValueAt(selectIndex, 3).toString());

        jButton1.setEnabled(false);
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel d1 = (DefaultTableModel) jTable1.getModel();
        int selectIndex = jTable1.getSelectedRow();

        if (selectIndex < 0) {
            JOptionPane.showMessageDialog(this, "Please select a member to update.");
            return;
        }

        int id = Integer.parseInt(d1.getValueAt(selectIndex, 0).toString());
        String name = txtname.getText().trim();
        String address = txtaddress.getText().trim();
        String phone = txtphone.getText().trim();

        if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        try {
            pat = con.prepareStatement("update member set name = ?, address = ?, phone = ? where id = ?");
            pat.setString(1, name);
            pat.setString(2, address);
            pat.setString(3, phone);
            pat.setInt(4, id);
            int k = pat.executeUpdate();

            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Member updated.");
                clearFields();
                Member_Load();
            } else {
                JOptionPane.showMessageDialog(this, "Error updating member.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearFields() {
        txtname.setText("");
        txtaddress.setText("");
        txtphone.setText("");
        jButton1.setEnabled(true);
        txtname.requestFocus();
    }

    public static void main(String args[]) {
        UITheme.install();
        java.awt.EventQueue.invokeLater(() -> new Member().setVisible(true));
    }

    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JLabel jLabel1;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTable jTable1;
    private JTextArea txtaddress;
    private JTextField txtname;
    private JTextField txtphone;
}
