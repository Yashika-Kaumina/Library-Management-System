package guilibararynew;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main extends javax.swing.JFrame {

    public Main() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel2 = new JPanel(new BorderLayout(0, 28));
        jPanel2.setBorder(BorderFactory.createEmptyBorder(36, 48, 42, 48));

        JPanel headerPanel = new JPanel(new BorderLayout(12, 8));
        headerPanel.setOpaque(false);

        jLabel3 = UITheme.title("Library Management System");
        JLabel subtitle = new JLabel("Choose a module to manage records and lending workflow.");
        subtitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 15));
        subtitle.setForeground(UITheme.MUTED_TEXT);
        headerPanel.add(jLabel3, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.CENTER);

        JPanel dashboard = UITheme.card();
        dashboard.setLayout(new GridBagLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(0, 3, 18, 18));
        buttonPanel.setOpaque(false);
        buttonPanel.add(createButton("Category", () -> new Category().setVisible(true)));
        buttonPanel.add(createButton("Publisher", () -> new Publisher().setVisible(true)));
        buttonPanel.add(createButton("Member", () -> new Member().setVisible(true)));
        buttonPanel.add(createButton("Book", () -> new Book().setVisible(true)));
        buttonPanel.add(createButton("DVD", () -> new DVD().setVisible(true)));
        buttonPanel.add(createButton("Author", () -> new Author().setVisible(true)));
        buttonPanel.add(createButton("Director", () -> new Director().setVisible(true)));
        buttonPanel.add(createButton("Issue Book", () -> new Lendbook().setVisible(true)));
        buttonPanel.add(createButton("Issue DVD", () -> new LendDVD().setVisible(true)));
        buttonPanel.add(createButton("Return Book", () -> new ReturnBook().setVisible(true)));
        buttonPanel.add(createButton("Return DVD", () -> new ReturnDVD().setVisible(true)));

        JButton logoutBtn = UITheme.button("Logout");
        logoutBtn.addActionListener(evt -> {
            new Login().setVisible(true);
            this.dispose();
        });
        buttonPanel.add(logoutBtn);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.insets = new Insets(4, 4, 4, 4);
        dashboard.add(buttonPanel, gbc);

        jPanel2.add(headerPanel, BorderLayout.NORTH);
        jPanel2.add(dashboard, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jPanel2, BorderLayout.CENTER);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Library Management System");
        setMinimumSize(new Dimension(960, 640));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        UITheme.applyFrame(this);

        pack();
        setLocationRelativeTo(null);
    }

    private JButton createButton(String text, Runnable action) {
        JButton btn = UITheme.button(text);
        btn.setPreferredSize(new Dimension(190, 52));
        btn.addActionListener(evt -> action.run());
        return btn;
    }

    public static void main(String args[]) {
        UITheme.install();
        java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }

    private JLabel jLabel3;
    private JPanel jPanel2;
}
