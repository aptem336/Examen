package exam;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

public class ChoiceFrame extends AbstractFrame {

    @Override
    public void createInterface() {
        JLabel label = new JLabel("Выберите тему");
        label.setFont(new Font("", 1, 20));
        label.setHorizontalAlignment(0);
        label.setForeground(new Color(Data.TEXT_COLOR, true));
        panel.add(label);
        for (int i = 0; i < Data.themeNames.length; i++) {
            panel.add(new Button(Data.themeNames[i], i));
        }
    }

}
