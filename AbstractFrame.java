package exam;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class AbstractFrame extends JFrame {

    protected JPanel panel;
    private JLabel title;
    Button exit;

    public void init(String caption) {
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setUndecorated(true);

        getContentPane().setBackground(new Color(Data.FRAME_COLOR));

        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 5, 5));
        panel.setBackground(new Color(Data.FRAME_COLOR + 0x111111));
        panel.setBounds(20, 20, getWidth() - 40, getHeight() - 40);
        panel.setBorder(BorderFactory.createLineBorder(new Color(Data.TEXT_COLOR - Data.D, true), 3, true));
        add(panel);

        exit = new Button("", -1);
        exit.setBounds(getWidth() - 20, 0, 20, 20);
        add(exit);

        title = new JLabel(caption);
        title.setBounds(10, 0, getWidth(), 20);
        title.setForeground(new Color(Data.BUTTONS_COLOR, true));
        title.setFont(new Font("", 1, 14));
        add(title);
    }

    public void invertColor() {
        panel.setBackground(new Color(Data.FRAME_COLOR));
        getContentPane().setBackground(new Color(Data.FRAME_COLOR));
        for (Component component : panel.getComponents()) {
            component.setForeground(new Color(Data.TEXT_COLOR, true));
        }
    }

    public void createInterface() {

    }
}
