package exam;

import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ResultFrame extends AbstractFrame {

    private static Object[][] data;
    private static String[][] answers;
    private static ResultTable table;
    private static JScrollPane scroll;
    private static JLabel result;
    private static TextFrame textFrame;

    @Override
    public void createInterface() {
        result = new JLabel("");
        result.setBounds(0, getHeight() - 22, getWidth(), 20);
        result.setForeground(new Color(Data.BUTTONS_COLOR, true));
        result.setFont(new Font("", 1, 14));
        result.setHorizontalAlignment(0);
        add(result);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }

    public void buildResult(ArrayList<String> userAnswers, ArrayList<Integer> randomQuestions, int themeNum, ArrayList<Long> answersTime) {
        int totalTime = 0;
        int k = 0;
        data = new Object[userAnswers.size()][3];
        answers = new String[userAnswers.size()][2];
        for (int i = 0; i < userAnswers.size(); i++) {
            data[i][0] = i + 1;
            data[i][2] = "-";
            String correctAnswer = "";
            try {
                correctAnswer = new Scanner(new File("src/answers/" + themeNum + "/" + randomQuestions.get(i) + ".txt")).nextLine();
            } catch (FileNotFoundException ex) {
            }
            String userAnswer = getUserAnswer(userAnswers.get(i));
            if (correctAnswer.equals(userAnswer)) {
                k++;
                data[i][2] = "+";
            }
            answers[i][0] = correctAnswer;
            answers[i][1] = userAnswer;
            int answerTime = (int) (answersTime.get(i) / 1000);
            totalTime += answerTime;
            data[i][1] = formatTime(answerTime);
        }
        panel.removeAll();
        textFrame = new TextFrame();
        textFrame.setSize(600, 400);
        textFrame.init("Îòâåòû");
        textFrame.createInterface();

        table = new ResultTable(data, answers);
        table.addMouseListener(new MouseListener());
        scroll = new JScrollPane(table);
        scroll.setMaximumSize(new Dimension(210, panel.getHeight()));
        panel.add(scroll);
        result.setText(formatResult(totalTime, k));
        setVisible(true);
        scroll.setBorder(BorderFactory.createLineBorder(Color.gray));

    }

    private static String formatResult(int totalTime, int k) {
        return "Âðåìÿ âûïîëíåíèÿ: " + formatTime(totalTime) + "    Äàíî îòâåòîâ: " + data.length + "    Ïðàâèëüíî: " + k + "  (" + (int) ((k / (double) data.length) * 100) + "%)";
    }

    private static String formatTime(int time) {
        return formatTwoNumber(time / 3600 % 60) + ":" + formatTwoNumber(time / 60 % 60) + ":" + formatTwoNumber(time % 60);
    }

    private static String formatTwoNumber(int number) {
        return String.format("%2s", number).replace(' ', '0');
    }

    private static String getUserAnswer(String answer) {
        return answer.toLowerCase().trim();
    }

    public void resultClose() {
        setVisible(false);
        Exam.choiseFrame.setVisible(true);
    }

    public static class TextFrame extends AbstractFrame {

        private static JTextArea correctAnswer;
        private static JTextArea userAnswer;

        @Override
        public void createInterface() {
            remove(exit);
            correctAnswer = new JTextArea();
            correctAnswer.setLineWrap(true);
            correctAnswer.setWrapStyleWord(true);
            correctAnswer.setEditable(false);
            userAnswer = new JTextArea();
            userAnswer.setLineWrap(true);
            userAnswer.setWrapStyleWord(true);
            userAnswer.setEditable(false);
            panel.setLayout(new GridLayout(1, 2, 5, 5));
            panel.add(correctAnswer);
            panel.add(userAnswer);

        }

        private void build(int row) {
            correctAnswer.setText("<ÏÐÀÂÈËÜÍÛÉ ÎÒÂÅÒ ÍÀ ÂÎÏÐÎÑ " + (row + 1) + ">\n" + answers[row][0]);
            userAnswer.setText("<ÂÀØ ÎÒÂÅÒ ÍÀ ÂÎÏÐÎÑ " + (row + 1) + ">\n" + answers[row][1]);
            setVisible(true);
        }

    }

    private static class MouseListener implements java.awt.event.MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            textFrame.build(table.rowAtPoint(e.getPoint()));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            textFrame.setVisible(false);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
