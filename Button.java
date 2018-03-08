package exam;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class Button extends JButton {

    private static MouseListener mouseListener = new MouseListener();
    public int themeNum;
    public int textColor;
    public int borderColor;

    public void startExam() {
        Exam.choiseFrame.setVisible(false);
        Exam.questionsFrame.buildExam(themeNum);
    }

    Button(String caption, int themeNum) {
        this.borderColor = Data.BUTTONS_COLOR;
        init(caption, themeNum);
    }

    Button(String caption, int themeNum, int borderColor) {
        this.borderColor = borderColor;
        init(caption, themeNum);
    }

    private void init(String caption, int themeNum) {
        setFont(new Font("", 1, 20));
        setForeground(new Color(Data.TEXT_COLOR, true));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusable(false);
        addMouseListener(mouseListener);
        setText(caption);
        setForeground(new Color(Data.TEXT_COLOR, true));
        this.themeNum = themeNum;
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.setColor(new Color(borderColor, true));
        graphics2D.drawRoundRect(3, 3, getWidth() - 6, getHeight() - 6, 10, 10);
        super.paintBorder(graphics2D);
    }

    private static class MouseListener implements java.awt.event.MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Button button = (Button) e.getComponent();
            switch (button.themeNum) {
                case -1:
                    if (Exam.choiseFrame.isShowing()) {
                        System.exit(0);
                    } else if (Exam.questionsFrame.isShowing()) {
                        Exam.questionsFrame.questionsClose();
                    } else if (Exam.resultFrame.isShowing()) {
                        Exam.resultFrame.resultClose();
                    }
                    break;
                default:
                    button.startExam();
                    break;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            Button button = (Button) e.getComponent();
            button.setForeground(new Color(Data.TEXT_COLOR + Data.D, true));
            button.borderColor += Data.D;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Button button = (Button) e.getComponent();
            button.setForeground(new Color(Data.TEXT_COLOR, true));
            button.borderColor -= Data.D;
        }
    }

}
