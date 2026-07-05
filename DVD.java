package guilibararynew;

import java.sql.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class DVD extends javax.swing.JFrame {

    public DVD() {
        initComponents();
        Connect();
        Category();
        Director();
        Publisher();
        DVD_Load();
    }

    public class CategoryItem {
        int id;
        String name;
        public CategoryItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        public String toString() { return name; }
    }

    public class DirectorItem {
        int id;
        String name;
        public DirectorItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        public String toString() { return name; }
    }

    public class PublisherItem {
        int id;
        String name;
        public PublisherItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        public String toString() { return name; }
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

    public void Category() {
        try {
            pat = con.prepareStatement("select * from category");
            rs = pat.executeQuery();
            txtcategory.removeAllItems();
            while (rs.next()) {
                txtcategory.addItem(new CategoryItem(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void Director() {
        try {
            pat = con.prepareStatement("select * from director");
            rs = pat.executeQuery();
            txtauthor.removeAllItems();
            while (rs.next()) {
                txtauthor.addItem(new DirectorItem(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void Publisher() {
        try {
            pat = con.prepareStatement("select * from publisher");
            rs = pat.executeQuery();
            txtpublisher.removeAllItems();
            while (rs.next()) {
                txtpublisher.addItem(new PublisherItem(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void DVD_Load() {
        try {
            pat = con.prepareStatement("select d.id, d.dname, c.catname, di.name, p.name, d.contents, d.size, d.edition from dvd d JOIN category c on d.category=c.id JOIN director di on d.director=di.id JOIN publisher p on d.publisher=p.id");
            rs = pat.executeQuery();
            DefaultTableModel d = (DefaultTableModel) jTable1.getModel();
            d.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getInt("d.id"));
                v.add(rs.getString("d.dname"));
                v.add(rs.getString("c.catname"));
                v.add(rs.getString("di.name"));
                v.add(rs.getString("p.name"));
                v.add(rs.getString("d.contents"));
                v.add(rs.getInt("d.size"));
                v.add(rs.getString("d.edition"));
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
        txtname = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtcategory = new javax.swing.JComboBox();
        txtauthor = new javax.swing.JComboBox();
        txtpublisher = new javax.swing.JComboBox();
        txtcontents = new javax.swing.JTextField();
        txtno = new javax.swing.JTextField();
        txtedition = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36));
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("DVD");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14));
        jLabel3.setForeground(new java.awt.Color(255, 255, 0));
        jLabel3.setText("Name");

        jButton1.setBackground(new java.awt.Color(0, 255, 51));
        jButton1.setText("Add");
        jButton1.addActionListener(evt -> {
            try {
                CategoryItem ci = (CategoryItem) txtcategory.getSelectedItem();
                DirectorItem di = (DirectorItem) txtauthor.getSelectedItem();
                PublisherItem pi = (PublisherItem) txtpublisher.getSelectedItem();
                pat = con.prepareStatement("insert into dvd(dname,category,director,publisher,contents,size,edition) values(?,?,?,?,?,?,?)");
                pat.setString(1, txtname.getText());
                pat.setInt(2, ci.id);
                pat.setInt(3, di.id);
                pat.setInt(4, pi.id);
                pat.setString(5, txtcontents.getText());
                pat.setString(6, txtno.getText());
                pat.setString(7, txtedition.getText());
                if (pat.executeUpdate() == 1) {
                    JOptionPane.showMessageDialog(this, "DVD Created");
                    clearFields();
                    DVD_Load();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 255, 51));
        jButton2.setText("Update");
        jButton2.addActionListener(evt -> {
            int row = jTable1.getSelectedRow();
            if (row < 0) return;
            int id = Integer.parseInt(jTable1.getValueAt(row, 0).toString());
            try {
                CategoryItem ci = (CategoryItem) txtcategory.getSelectedItem();
                DirectorItem di = (DirectorItem) txtauthor.getSelectedItem();
                PublisherItem pi = (PublisherItem) txtpublisher.getSelectedItem();
                pat = con.prepareStatement("update dvd set dname=?, category=?, director=?, publisher=?, contents=?, size=?, edition=? where id=?");
                pat.setString(1, txtname.getText());
                pat.setInt(2, ci.id);
                pat.setInt(3, di.id);
                pat.setInt(4, pi.id);
                pat.setString(5, txtcontents.getText());
                pat.setString(6, txtno.getText());
                pat.setString(7, txtedition.getText());
                pat.setInt(8, id);
                if (pat.executeUpdate() == 1) {
                    JOptionPane.showMessageDialog(this, "Updated");
                    clearFields();
                    DVD_Load();
                    jButton1.setEnabled(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 0, 51));
        jButton3.setText("Delete");
        jButton3.addActionListener(evt -> {
            int row = jTable1.getSelectedRow();
            if (row < 0) return;
            int id = Integer.parseInt(jTable1.getValueAt(row, 0).toString());
            try {
                pat = con.prepareStatement("delete from dvd where id=?");
                pat.setInt(1, id);
                if (pat.executeUpdate() == 1) {
                    JOptionPane.showMessageDialog(this, "Deleted");
                    DVD_Load();
                    jButton1.setEnabled(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 0, 51));
        jButton4.setText("Cancel");
        jButton4.addActionListener(evt -> this.dispose());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "DVD Name", "Category", "Director", "Publisher", "Contents", "Size(MB)", "Edition"}
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.getSelectedRow();
                txtname.setText(jTable1.getValueAt(row, 1).toString());
                txtcategory.setSelectedItem(jTable1.getValueAt(row, 2).toString());
                txtauthor.setSelectedItem(jTable1.getValueAt(row, 3).toString());
                txtpublisher.setSelectedItem(jTable1.getValueAt(row, 4).toString());
                txtcontents.setText(jTable1.getValueAt(row, 5).toString());
                txtno.setText(jTable1.getValueAt(row, 6).toString());
                txtedition.setText(jTable1.getValueAt(row, 7).toString());
                jButton1.setEnabled(false);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel5.setForeground(new java.awt.Color(255, 255, 0));
        jLabel5.setText("Category");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel6.setForeground(new java.awt.Color(255, 255, 0));
        jLabel6.setText("Director");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel7.setForeground(new java.awt.Color(255, 255, 0));
        jLabel7.setText("Size(MB)");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel8.setForeground(new java.awt.Color(255, 255, 0));
        jLabel8.setText("Publisher");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel9.setForeground(new java.awt.Color(255, 255, 0));
        jLabel9.setText("Contents");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel10.setForeground(new java.awt.Color(255, 255, 0));
        jLabel10.setText("Edition");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtname, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(txtcategory, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(txtauthor, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(txtpublisher, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(txtcontents, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(txtno, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(txtedition, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)))
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
                    .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtcategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtauthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtpublisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtcontents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtedition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
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
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // මෙය add කරන්න
    }

    private void clearFields() {
        txtname.setText("");
        txtcategory.setSelectedIndex(-1);
        txtauthor.setSelectedIndex(-1);
        txtpublisher.setSelectedIndex(-1);
        txtcontents.setText("");
        txtno.setText("");
        txtedition.setText("");
        jButton1.setEnabled(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new DVD().setVisible(true));
    }

    private javax.swing.JButton jButton1, jButton2, jButton3, jButton4;
    private javax.swing.JLabel jLabel1, jLabel10, jLabel3, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox txtauthor, txtcategory, txtpublisher;
    private javax.swing.JTextField txtcontents, txtedition, txtname, txtno;
}