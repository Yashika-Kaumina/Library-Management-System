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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Category extends javax.swing.JFrame {

    public Category() {
        initComponents();
        Connect();
        Category_Load();
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

    public void Category_Load() {
        if (con == null) {
            return;
        }

        try {
            pat = con.prepareStatement("select * from Category");
            rs = pat.executeQuery();
            DefaultTableModel d = (DefaultTableModel) jTable1.getModel();
            d.setRowCount(0);
            while (rs.next()) {
                Vector<Object> v = new Vector<>();
                v.add(rs.getInt("id"));
                v.add(rs.getString("CatName"));
                v.add(rs.getString("Status"));
                d.addRow(v);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new JPanel(new BorderLayout(24, 24));
        jPanel1.setBorder(BorderFactory.createEmptyBorder(32, 40, 40, 40));
        jLabel1 = UITheme.title("Category");
        jLabel3 = new JLabel("Category Name");
        jLabel4 = new JLabel("Status");
        txtcategory = new JTextField();
        txtstatus = new JComboBox<>(new String[]{"Active", "DeActive"});
        jButton1 = UITheme.button("Add");
        jButton2 = UITheme.button("Update");
        jButton3 = UITheme.button("Delete");
        jButton4 = UITheme.button("Cancel");
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Category Management");
        setMinimumSize(new Dimension(980, 620));

        JLabel subtitle = new JLabel("Create and maintain book and media categories.");
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
        formPanel.add(txtcategory, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 0, 8, 0);
        formPanel.add(jLabel4, gbc);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 26, 0);
        formPanel.add(txtstatus, gbc);

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

        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(actionPanel, gbc);

        jTable1.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Category Name", "Status"}
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.getSelectedRow();
                if (row < 0) {
                    return;
                }
                txtcategory.setText(jTable1.getValueAt(row, 1).toString());
                txtstatus.setSelectedItem(jTable1.getValueAt(row, 2).toString());
                jButton1.setEnabled(false);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        JPanel tablePanel = UITheme.card();
        tablePanel.setLayout(new BorderLayout(0, 12));
        JLabel tableTitle = new JLabel("Category Records");
        tableTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(jScrollPane1, BorderLayout.CENTER);

        JPanel content = new JPanel(new BorderLayout(24, 0));
        content.setOpaque(false);
        formPanel.setPreferredSize(new Dimension(360, 420));
        content.add(formPanel, BorderLayout.WEST);
        content.add(tablePanel, BorderLayout.CENTER);
        jPanel1.add(content, BorderLayout.CENTER);

        jButton1.addActionListener(evt -> addCategory());
        jButton2.addActionListener(evt -> updateCategory());
        jButton3.addActionListener(evt -> deleteCategory());
        jButton4.addActionListener(evt -> this.dispose());

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jPanel1, BorderLayout.CENTER);
        UITheme.applyFrame(this);

        pack();
        setLocationRelativeTo(null);
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    private void addCategory() {
        String cat = txtcategory.getText().trim();
        String status = txtstatus.getSelectedItem().toString();
        if (cat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Category name is required.");
            return;
        }

        try {
            pat = con.prepareStatement("insert into category(CatName,Status) values(?,?)");
            pat.setString(1, cat);
            pat.setString(2, status);
            int k = pat.executeUpdate();
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Category created.");
                clearFields();
                Category_Load();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateCategory() {
        int selectIndex = jTable1.getSelectedRow();
        if (selectIndex < 0) {
            JOptionPane.showMessageDialog(this, "Please select a category to update.");
            return;
        }

        String cat = txtcategory.getText().trim();
        String status = txtstatus.getSelectedItem().toString();
        if (cat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Category name is required.");
            return;
        }

        int id = Integer.parseInt(jTable1.getValueAt(selectIndex, 0).toString());
        try {
            pat = con.prepareStatement("update category set CatName=?, Status=? where id=?");
            pat.setString(1, cat);
            pat.setString(2, status);
            pat.setInt(3, id);
            int k = pat.executeUpdate();
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Category updated.");
                clearFields();
                Category_Load();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteCategory() {
        int selectIndex = jTable1.getSelectedRow();
        if (selectIndex < 0) {
            JOptionPane.showMessageDialog(this, "Please select a category to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Delete the selected category?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int id = Integer.parseInt(jTable1.getValueAt(selectIndex, 0).toString());
        try {
            pat = con.prepareStatement("delete from category where id=?");
            pat.setInt(1, id);
            int k = pat.executeUpdate();
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Category deleted.");
                clearFields();
                Category_Load();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        txtcategory.setText("");
        txtstatus.setSelectedIndex(0);
        jButton1.setEnabled(true);
        txtcategory.requestFocus();
    }

    public static void main(String args[]) {
        UITheme.install();
        java.awt.EventQueue.invokeLater(() -> new Category().setVisible(true));
    }

    private JButton jButton1, jButton2, jButton3, jButton4;
    private JLabel jLabel1, jLabel3, jLabel4;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JTextField txtcategory;
    private JComboBox<String> txtstatus;
}
