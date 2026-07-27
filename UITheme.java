package guilibararynew;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;

public final class UITheme {
    public static final Color BACKGROUND = new Color(244, 247, 251);
    public static final Color SURFACE = Color.WHITE;
    public static final Color SURFACE_ALT = new Color(232, 238, 247);
    public static final Color TEXT = new Color(22, 33, 46);
    public static final Color MUTED_TEXT = new Color(90, 105, 122);
    public static final Color PRIMARY = new Color(0, 121, 107);
    public static final Color PRIMARY_DARK = new Color(0, 96, 86);
    public static final Color SECONDARY = new Color(42, 82, 152);
    public static final Color DANGER = new Color(190, 48, 64);
    public static final Color BORDER = new Color(205, 214, 226);

    private static final Font FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 32);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    private UITheme() {
    }

    public static void install() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        UIManager.put("OptionPane.messageFont", FONT);
        UIManager.put("OptionPane.buttonFont", BUTTON_FONT);
    }

    public static void applyFrame(JFrame frame) {
        frame.getContentPane().setBackground(BACKGROUND);
        styleTree(frame.getContentPane());
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(TEXT);
        return label;
    }

    public static JButton button(String text) {
        JButton button = new JButton(text);
        styleButton(button);
        return button;
    }

    public static JPanel card() {
        JPanel panel = new JPanel();
        panel.putClientProperty("ui.surface", Boolean.TRUE);
        panel.setBackground(SURFACE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(24, 28, 28, 28)
        ));
        return panel;
    }

    public static void styleButton(JButton button) {
        String text = button.getText() == null ? "" : button.getText().toLowerCase();
        Color bg = PRIMARY;
        if (text.contains("delete") || text.contains("cancel") || text.contains("logout")) {
            bg = DANGER;
        } else if (text.contains("update") || text.contains("return")) {
            bg = SECONDARY;
        }

        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(BUTTON_FONT);
        button.setMargin(new Insets(9, 18, 9, 18));
        button.setPreferredSize(new Dimension(150, 40));
        button.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    public static void styleTree(Component component) {
        if (component instanceof JPanel panel) {
            Object isSurface = panel.getClientProperty("ui.surface");
            panel.setBackground(Boolean.TRUE.equals(isSurface) ? SURFACE : BACKGROUND);
        }

        if (component instanceof JLabel label) {
            label.setForeground(TEXT);
            if (label.getFont() == null || label.getFont().getSize() <= 18) {
                label.setFont(LABEL_FONT);
            }
        } else if (component instanceof JButton button) {
            styleButton(button);
        } else if (component instanceof JTextField field) {
            styleTextField(field);
        } else if (component instanceof JTextArea area) {
            styleTextArea(area);
        } else if (component instanceof JComboBox<?> comboBox) {
            comboBox.setFont(FONT);
            comboBox.setBackground(SURFACE);
            comboBox.setForeground(TEXT);
            comboBox.setBorder(inputBorder());
            comboBox.setPreferredSize(new Dimension(220, 36));
        } else if (component instanceof JTable table) {
            styleTable(table);
        } else if (component instanceof JScrollPane scrollPane) {
            scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
            scrollPane.getViewport().setBackground(SURFACE);
        }

        if (component instanceof Container container) {
            for (Component child : container.getComponents()) {
                styleTree(child);
            }
        }
    }

    private static void styleTextField(JTextField field) {
        field.setFont(FONT);
        field.setForeground(TEXT);
        field.setBackground(SURFACE);
        field.setCaretColor(TEXT);
        field.setBorder(inputBorder());
        field.setPreferredSize(new Dimension(240, 36));
    }

    private static void styleTextArea(JTextArea area) {
        area.setFont(FONT);
        area.setForeground(TEXT);
        area.setBackground(SURFACE);
        area.setCaretColor(TEXT);
        area.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
    }

    private static void styleTable(JTable table) {
        table.setFont(FONT);
        table.setForeground(TEXT);
        table.setBackground(SURFACE);
        table.setSelectionBackground(new Color(208, 232, 228));
        table.setSelectionForeground(TEXT);
        table.setGridColor(new Color(226, 232, 240));
        table.setRowHeight(32);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = table.getTableHeader();
        if (header != null) {
            header.setFont(BUTTON_FONT);
            header.setForeground(TEXT);
            header.setBackground(SURFACE_ALT);
            header.setPreferredSize(new Dimension(header.getPreferredSize().width, 36));
            header.setBorder(BorderFactory.createLineBorder(BORDER));
        }
    }

    private static Border inputBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(7, 10, 7, 10)
        );
    }

    public static void makeSurface(JComponent component) {
        component.setBackground(SURFACE);
        component.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(18, 20, 20, 20)
        ));
    }
}
