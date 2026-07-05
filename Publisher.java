package guilibararynew;

import java.sql.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class Publisher extends javax.swing.JFrame {

    public Publisher() {
        initComponents();
        Connect();
        Publisher_Load();
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

    public void Publisher_Load() {
        try {
            pat = con.prepareStatement("select * from Publisher");
            rs = pat.executeQuery();
            DefaultTableModel d = (DefaultTableModel) jTable1.getModel();
            d.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getInt("id"));
                v.add(rs.getString("name"));
                v.add(rs.getString("address"));
                v.add(rs.getString("phone"));
                d.addRow(v);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtname = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtaddress = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        txtphone = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Publisher Management");
        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36));
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("Publisher");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14));
        jLabel3.setForeground(new java.awt.Color(255, 255, 0));
        jLabel3.setText("Name");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14));
        jLabel4.setForeground(new java.awt.Color(255, 255, 0));
        jLabel4.setText("Address");

        jButton1.setBackground(new java.awt.Color(0, 255, 51));
        jButton1.setText("Add");
        jButton1.addActionListener(evt -> {
            String name = txtname.getText().trim();
            String address = txtaddress.getText().trim();
            String phone = txtphone.getText().trim();
            
            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(Publisher.this, "All fields are required!");
                return;
            }
            
            try {
                pat = con.prepareStatement("insert into publisher(name, address, phone) values(?,?,?)");
                pat.setString(1, name);
                pat.setString(2, address);
                pat.setString(3, phone);
                int k = pat.executeUpdate();
                
                if (k == 1) {
                    JOptionPane.showMessageDialog(Publisher.this, "Publisher Created Successfully!");
                    clearFields();
                    Publisher_Load();
                } else {
                    JOptionPane.showMessageDialog(Publisher.this, "Error creating publisher!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Publisher.this, "Database Error: " + ex.getMessage());
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 255, 51));
        jButton2.setText("Update");
        jButton2.addActionListener(evt -> {
            int row = jTable1.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(Publisher.this, "Please select a row to update!");
                return;
            }
            
            int id = Integer.parseInt(jTable1.getValueAt(row, 0).toString());
            String name = txtname.getText().trim();
            String address = txtaddress.getText().trim();
            String phone = txtphone.getText().trim();
            
            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(Publisher.this, "All fields are required!");
                return;
            }
            
            try {
                pat = con.prepareStatement("update publisher set name=?, address=?, phone=? where id=?");
                pat.setString(1, name);
                pat.setString(2, address);
                pat.setString(3, phone);
                pat.setInt(4, id);
                int k = pat.executeUpdate();
                
                if (k == 1) {
                    JOptionPane.showMessageDialog(Publisher.this, "Publisher Updated Successfully!");
                    clearFields();
                    Publisher_Load();
                    jButton1.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(Publisher.this, "Error updating publisher!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Publisher.this, "Database Error: " + ex.getMessage());
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 0, 51));
        jButton3.setText("Delete");
        jButton3.addActionListener(evt -> {
            int row = jTable1.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(Publisher.this, "Please select a row to delete!");
                return;
            }
            
            int id = Integer.parseInt(jTable1.getValueAt(row, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(Publisher.this, 
                "Are you sure you want to delete this publisher?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm != JOptionPane.YES_OPTION) return;
            
            try {
                pat = con.prepareStatement("delete from publisher where id=?");
                pat.setInt(1, id);
                int k = pat.executeUpdate();
                
                if (k == 1) {
                    JOptionPane.showMessageDialog(Publisher.this, "Publisher Deleted Successfully!");
                    clearFields();
                    Publisher_Load();
                    jButton1.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(Publisher.this, "Error deleting publisher!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Publisher.this, "Database Error: " + ex.getMessage());
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 0, 51));
        jButton4.setText("Cancel");
        jButton4.addActionListener(evt -> {
            this.dispose();
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Publisher Name", "Address", "Phone No"}
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.getSelectedRow();
                if (row < 0) return;
                txtname.setText(jTable1.getValueAt(row, 1).toString());
                txtaddress.setText(jTable1.getValueAt(row, 2).toString());
                txtphone.setText(jTable1.getValueAt(row, 3).toString());
                jButton1.setEnabled(false);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        txtaddress.setColumns(20);
        txtaddress.setRows(5);
        jScrollPane2.setViewportView(txtaddress);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel5.setForeground(new java.awt.Color(255, 255, 0));
        jLabel5.setText("Phone");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtphone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(67, 67, 67)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1)
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtphone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addGap(44, 44, 44))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    private void clearFields() {
        txtname.setText("");
        txtaddress.setText("");
        txtphone.setText("");
        jButton1.setEnabled(true);
        txtname.requestFocus();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Publisher().setVisible(true));
    }

    private javax.swing.JButton jButton1, jButton2, jButton3, jButton4;
    private javax.swing.JLabel jLabel1, jLabel3, jLabel4, jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1, jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea txtaddress;
    private javax.swing.JTextField txtname, txtphone;
}