package guilibararynew;

import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class ReturnBook extends javax.swing.JFrame {

    public ReturnBook() {
        initComponents();
        Connect();
        Return_Load();
    }

    Connection con;
    PreparedStatement pat;
    ResultSet rs;

    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/guilibrarynew", "root", "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void Return_Load() {
        try {
            pat = con.prepareStatement("select * from returnbook");
            rs = pat.executeQuery();
            DefaultTableModel d = (DefaultTableModel) jTable1.getModel();
            d.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getInt("id"));
                v.add(rs.getInt("mid"));
                v.add(rs.getString("mname"));
                v.add(rs.getString("bname"));
                v.add(rs.getString("returndate"));
                v.add(rs.getInt("elp"));
                v.add(rs.getDouble("fine"));
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
        txtmid = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtmname = new javax.swing.JLabel();
        txtbook = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtelp = new javax.swing.JTextField();
        txtfine = new javax.swing.JTextField();
        txtrdate = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36));
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("Return Book");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel3.setForeground(new java.awt.Color(255, 255, 0));
        jLabel3.setText("Member ID");

        txtmid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    String id = txtmid.getText();
                    try {
                        pat = con.prepareStatement("select m.name, b.bname, l.returndate, DATEDIFF(now(), l.returndate) as elap from lendbook l JOIN member m ON l.memberid=m.id JOIN book b ON l.bookid=b.id WHERE l.memberid=?");
                        pat.setInt(1, Integer.parseInt(id));
                        rs = pat.executeQuery();
                        if (rs.next()) {
                            txtmname.setText(rs.getString("m.name"));
                            txtbook.setText(rs.getString("b.bname"));
                            txtrdate.setText(rs.getString("l.returndate"));
                            int elap = rs.getInt("elap");
                            txtelp.setText(String.valueOf(elap));
                            txtfine.setText(String.valueOf(elap * 100));
                        } else {
                            JOptionPane.showMessageDialog(null, "Member ID not found");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 255, 51));
        jButton1.setText("Add");
        jButton1.addActionListener(evt -> {
            try {
                pat = con.prepareStatement("insert into returnbook(mid,mname,bname,returndate,elp,fine) values(?,?,?,?,?,?)");
                pat.setInt(1, Integer.parseInt(txtmid.getText()));
                pat.setString(2, txtmname.getText());
                pat.setString(3, txtbook.getText());
                pat.setString(4, txtrdate.getText());
                pat.setInt(5, Integer.parseInt(txtelp.getText()));
                pat.setDouble(6, Double.parseDouble(txtfine.getText()));
                pat.executeUpdate();
                pat = con.prepareStatement("delete from lendbook where memberid=?");
                pat.setInt(1, Integer.parseInt(txtmid.getText()));
                pat.executeUpdate();
                JOptionPane.showMessageDialog(this, "Book Returned");
                clearFields();
                Return_Load();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 255, 51));
        jButton2.setText("Update");
        jButton2.addActionListener(evt -> {
            int row = jTable1.getSelectedRow();
            if (row < 0) return;
            try {
                pat = con.prepareStatement("update returnbook set mid=?, mname=?, bname=?, returndate=?, elp=?, fine=? where id=?");
                pat.setInt(1, Integer.parseInt(txtmid.getText()));
                pat.setString(2, txtmname.getText());
                pat.setString(3, txtbook.getText());
                pat.setString(4, txtrdate.getText());
                pat.setInt(5, Integer.parseInt(txtelp.getText()));
                pat.setDouble(6, Double.parseDouble(txtfine.getText()));
                pat.setInt(7, Integer.parseInt(jTable1.getValueAt(row, 0).toString()));
                pat.executeUpdate();
                JOptionPane.showMessageDialog(this, "Updated");
                clearFields();
                Return_Load();
                jButton1.setEnabled(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 0, 51));
        jButton3.setText("Delete");
        jButton3.addActionListener(evt -> {
            int row = jTable1.getSelectedRow();
            if (row < 0) return;
            try {
                pat = con.prepareStatement("delete from returnbook where id=?");
                pat.setInt(1, Integer.parseInt(jTable1.getValueAt(row, 0).toString()));
                pat.executeUpdate();
                JOptionPane.showMessageDialog(this, "Deleted");
                Return_Load();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 0, 51));
        jButton4.setText("Cancel");
        jButton4.addActionListener(evt -> this.dispose());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Member ID", "Member Name", "Book", "Return Date", "Days", "Fine"}
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.getSelectedRow();
                txtmid.setText(jTable1.getValueAt(row, 1).toString());
                txtmname.setText(jTable1.getValueAt(row, 2).toString());
                txtbook.setText(jTable1.getValueAt(row, 3).toString());
                txtrdate.setText(jTable1.getValueAt(row, 4).toString());
                txtelp.setText(jTable1.getValueAt(row, 5).toString());
                txtfine.setText(jTable1.getValueAt(row, 6).toString());
                jButton1.setEnabled(false);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel5.setForeground(new java.awt.Color(255, 255, 0));
        jLabel5.setText("Member Name");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel6.setForeground(new java.awt.Color(255, 255, 0));
        jLabel6.setText("Borrow Book");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel9.setForeground(new java.awt.Color(255, 255, 0));
        jLabel9.setText("Return Date");

        txtmname.setForeground(new java.awt.Color(255, 255, 255));
        txtbook.setForeground(new java.awt.Color(255, 255, 255));
        txtrdate.setForeground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel10.setForeground(new java.awt.Color(255, 255, 0));
        jLabel10.setText("Days Elapsed");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel11.setForeground(new java.awt.Color(255, 255, 0));
        jLabel11.setText("Fine");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtmid, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(txtmname, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(txtbook, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(txtrdate, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(txtelp, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(txtfine, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(67, 67, 67)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(txtmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtmname))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtbook))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtrdate))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtfine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addGap(30, 30, 30))
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
    }

    private void clearFields() {
        txtmid.setText("");
        txtmname.setText("");
        txtbook.setText("");
        txtrdate.setText("");
        txtelp.setText("");
        txtfine.setText("");
        jButton1.setEnabled(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new ReturnBook().setVisible(true));
    }

    private javax.swing.JButton jButton1, jButton2, jButton3, jButton4;
    private javax.swing.JLabel jLabel1, jLabel10, jLabel11, jLabel3, jLabel5, jLabel6, jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel txtbook, txtmname, txtrdate;
    private javax.swing.JTextField txtelp, txtfine, txtmid;
}