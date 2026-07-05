/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package guilibararynew;

import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;   
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;

public class LendDVD extends javax.swing.JFrame {

    public LendDVD() {
        initComponents();
        Connect();
        DVD();
        IssueDVD_Load();
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

    // DVD Items for ComboBox
    public class DVDItem{
        int id;
        String name;
        public DVDItem(int id,String name){
            this.id = id;
            this.name = name;
        }
        public String toString(){
            return name;
        }
    }
    
    public void DVD(){
        try {
            pat = con.prepareStatement("select * from DVD");
            rs = pat.executeQuery();
            txtbook.removeAllItems(); 
            while(rs.next()){
                txtbook.addItem(new DVDItem (rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LendDVD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void IssueDVD_Load(){
        try{
            // Table එකට හරි columns ගේන්න
            pat = con.prepareStatement("select ld.id, m.id as memberid, m.name as membername, d.dname, ld.issuedate, ld.returndate from lenddvd ld JOIN member m ON ld.memberid = m.id JOIN dvd d ON ld.did = d.id");
            rs = pat.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();
            DefaultTableModel d = (DefaultTableModel)jTable1.getModel();
            d.setRowCount(0);
            while(rs.next()){
                Vector<Object> v2 = new Vector<>();
                // එක එක column එකට එක වතාවක් අගය දානවා
                v2.add(rs.getInt("ld.id"));
                v2.add(rs.getInt("memberid"));
                v2.add(rs.getString("membername"));
                v2.add(rs.getString("d.dname"));
                v2.add(rs.getString("ld.issuedate"));
                v2.add(rs.getString("ld.returndate"));
                d.addRow(v2);
            }
        }catch(SQLException ex){
            Logger.getLogger(LendDVD.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    // ---------------- GUI Components Declaration ----------------
    private javax.swing.JButton jButton1, jButton2, jButton3, jButton4;
    private javax.swing.JLabel jLabel1, jLabel3, jLabel5, jLabel6, jLabel8, jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<DVDItem> txtbook;
    private javax.swing.JTextField txtid, txtmember;
    // මෙම දෙක දැන් declare කරලා තියෙනවා
    private javax.swing.JTextField txtissuedate;
    private javax.swing.JTextField txtrdate;

    // ---------------- Helper Method to Clear Fields ----------------
    private void clearFields() {
        txtid.setText("");
        txtmember.setText("");
        txtbook.setSelectedIndex(-1);
        txtissuedate.setText("");
        txtrdate.setText("");
        jButton1.setEnabled(true);
    }

    // ---------------- Event Handlers ----------------
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        DefaultTableModel d1 = (DefaultTableModel)jTable1.getModel();
        int selectIndex = jTable1.getSelectedRow();
        if(selectIndex < 0) return;

        try {
            int memberId = Integer.parseInt(d1.getValueAt(selectIndex, 1).toString());
            String memberName = d1.getValueAt(selectIndex, 2).toString();
            String dvdName = d1.getValueAt(selectIndex, 3).toString();
            String issueDate = d1.getValueAt(selectIndex, 4).toString();
            String returnDate = d1.getValueAt(selectIndex, 5).toString();

            txtid.setText(String.valueOf(memberId));
            txtmember.setText(memberName);
            
            // ComboBox එකේ DVD එක Select කරන්න
            for (int i = 0; i < txtbook.getItemCount(); i++) {
                DVDItem item = txtbook.getItemAt(i);
                if (item.name.equals(dvdName)) {
                    txtbook.setSelectedItem(item);
                    break;
                }
            }
            txtissuedate.setText(issueDate);
            txtrdate.setText(returnDate);
            jButton1.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data to fields");
        }
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel d1 = (DefaultTableModel)jTable1.getModel();
        int selectIndex = jTable1.getSelectedRow();
        if(selectIndex < 0) {
            JOptionPane.showMessageDialog(this, "පළමුව පේළියක් තෝරන්න");
            return;
        }
        int lendId = Integer.parseInt(d1.getValueAt(selectIndex, 0).toString());
        try {
            pat = con.prepareStatement("delete from lenddvd where id = ?");
            pat.setInt(1, lendId);
            int k = pat.executeUpdate();
            if(k == 1){
                JOptionPane.showMessageDialog(this, "වාර්තාව මකා දමන ලදී");
                clearFields();
                IssueDVD_Load();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LendDVD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel d1 = (DefaultTableModel)jTable1.getModel();
        int selectIndex = jTable1.getSelectedRow();
        if(selectIndex < 0) {
            JOptionPane.showMessageDialog(this, "යාවත්කාලීන කිරීමට පේළියක් තෝරන්න");
            return;
        }
        int lendId = Integer.parseInt(d1.getValueAt(selectIndex, 0).toString());
        String mid = txtid.getText();
        DVDItem bitem = (DVDItem) txtbook.getSelectedItem();
        if(bitem == null) {
            JOptionPane.showMessageDialog(this, "DVD එකක් තෝරන්න");
            return;
        }
        String issuedate = txtissuedate.getText().trim();
        String returndate = txtrdate.getText().trim();

        try {
            pat = con.prepareStatement("update lenddvd set memberid = ?, did = ?, issuedate = ?, returndate = ? where id = ?");
            pat.setInt(1, Integer.parseInt(mid));
            pat.setInt(2, bitem.id);
            pat.setString(3, issuedate);
            pat.setString(4, returndate);
            pat.setInt(5, lendId);
            int k = pat.executeUpdate();
            if(k == 1){
                JOptionPane.showMessageDialog(this, "යාවත්කාලීන කරන ලදී");
                clearFields();
                IssueDVD_Load();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LendDVD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Member ID එක අංකයක් විය යුතුය");
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String mid = txtid.getText();
        DVDItem bitem = (DVDItem) txtbook.getSelectedItem();
        if(bitem == null) {
            JOptionPane.showMessageDialog(this, "DVD එකක් තෝරන්න");
            return;
        }
        String issuedate = txtissuedate.getText().trim();
        String returndate = txtrdate.getText().trim();

        try {
            pat = con.prepareStatement("insert into lenddvd(memberid, did, issuedate, returndate) values (?,?,?,?)");
            pat.setInt(1, Integer.parseInt(mid));
            pat.setInt(2, bitem.id);
            pat.setString(3, issuedate);
            pat.setString(4, returndate);
            int k = pat.executeUpdate();
            if(k == 1){
                JOptionPane.showMessageDialog(this, "DVD ලබා දෙන ලදී");
                clearFields();
                IssueDVD_Load();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LendDVD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Member ID එක අංකයක් විය යුතුය");
        }
    }

    private void txtidKeyPressed(java.awt.event.KeyEvent evt) {
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String mid = txtid.getText().trim();
            if(mid.isEmpty()) return;
            try {
                pat = con.prepareStatement("select name from member where id = ?");
                pat.setInt(1, Integer.parseInt(mid));
                rs = pat.executeQuery();
                if(rs.next()){
                    txtmember.setText(rs.getString("name").trim());
                } else {
                    JOptionPane.showMessageDialog(this, "Member ID හමු නොවීය");
                    txtmember.setText("");
                }
            } catch (SQLException ex) {
                Logger.getLogger(LendDVD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "කරුණාකර නිවැරදි ID එකක් ඇතුලත් කරන්න");
            }
        }
    }

    // ---------------- Main and initComponents (Auto Generated but Fixed) ----------------
    // මෙහි initComponents එකේ GUI Layout එක හරිගස්සලා Date fields add කරලා තියෙනවා.
    // ඔබට මෙය පැරණි file එක මකා මෙය පිටපත් කරන්න පුළුවන්.
    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jLabel3 = new JLabel();
        txtid = new JTextField();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jButton4 = new JButton();
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        jLabel8 = new JLabel();
        jLabel9 = new JLabel();
        txtbook = new JComboBox<>();
        txtmember = new JTextField();
        txtissuedate = new JTextField();
        txtrdate = new JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36));
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("Issue DVD");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel3.setForeground(new java.awt.Color(255, 255, 0));
        jLabel3.setText("Member ID");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel5.setForeground(new java.awt.Color(255, 255, 0));
        jLabel5.setText("Member Name");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel6.setForeground(new java.awt.Color(255, 255, 0));
        jLabel6.setText("Borrow DVD");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel8.setForeground(new java.awt.Color(255, 255, 0));
        jLabel8.setText("Borrow Date");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel9.setForeground(new java.awt.Color(255, 255, 0));
        jLabel9.setText("Return Date");

        jButton1.setBackground(new java.awt.Color(0, 255, 51));
        jButton1.setText("Add");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setBackground(new java.awt.Color(0, 255, 51));
        jButton2.setText("Update");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setBackground(new java.awt.Color(255, 0, 51));
        jButton3.setText("Delete");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setBackground(new java.awt.Color(255, 0, 51));
        jButton4.setText("Cancel");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] { {null, null, null, null, null, null} },
            new String [] { "ID", "Member ID", "Member Name", "Borrow DVD", "Borrow Date", "Return Date" }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        txtid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtidKeyPressed(evt);
            }
        });

        // Layout
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtid, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(txtmember, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(txtbook, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(txtissuedate, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(txtrdate, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtmember, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtbook, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtissuedate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtrdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                .addGap(0, 20, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // මෙය add කරන්න
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new LendDVD().setVisible(true);
        });
    }
}