package exam;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class QuestionsFrame extends AbstractFrame {

    private static int themeNum;
    private static ArrayList<Integer> randomQuestions = new ArrayList<>();
    private static ArrayList<String> userAnswers = new ArrayList<>();
    private static ArrayList<Long> answersTime = new ArrayList<>();
    private static ImagePanel imagePanel;
    private static JTextArea inputPlace;
    private static JLabel timer;
    private static long startTime;
    private static int questionsCount;
    private static int timeLeft;
    private static EnterListener enterListener;
    private static int k = 0;

    public void buildExam(int newThemeNum) {
        themeNum = newThemeNum;
        try {
            questionsCount = new Scanner(new File("src/images/" + themeNum + "/count.txt")).nextInt();
        } catch (FileNotFoundException ex) {
        }
        boolean p = false;
        while (!p) {
            timeLeft = inputTime();
            p = timeLeft != -3;
        }
        if (timeLeft == -1) {
            Exam.resultFrame.resultClose();
            return;
        }
        userAnswers.clear();
        answersTime.clear();
        randomQuestions.clear();
        for (int i = 0; i < questionsCount; i++) {
            randomQuestions.add(i);
        }
        Collections.shuffle(randomQuestions);
        setVisible(true);
        try {
            imagePanel.setImage(ImageIO.read(new File("src/images/" + themeNum + "/" + randomQuestions.get(k) + ".png")));
            k++;
            startTime = System.currentTimeMillis();
            imagePanel.repaint();
        } catch (IOException ex) {
        }
        new Thread() {
            @Override
            public void run() {
                timeLeft *= 60;
                long lastTime = System.currentTimeMillis();
                long currentTime;
                setTimer(timeLeft);
                while (timeLeft > 0 && questionsCount >= 0) {
                    currentTime = System.currentTimeMillis();
                    if (currentTime - lastTime >= 1000) {
                        lastTime = currentTime;
                        timeLeft--;
                        setTimer(timeLeft);
                    }
                }
                if (timeLeft <= 0) {
                    JOptionPane.showMessageDialog(null, "Время вышло, экзамен завершён!");
                    questionsClose();
                }
            }
        }.start();
    }

    private static void setTimer(int timeLeft) {
        timer.setText(formatTime(timeLeft));
    }

    private static String formatTime(int time) {
        return formatTwoNumber(time / 3600 % 60) + ":" + formatTwoNumber(time / 60 % 60) + ":" + formatTwoNumber(time % 60);
    }

    private static String formatTwoNumber(int number) {
        return String.format("%2s", number).replace(' ', '0');
    }

    private static int inputTime() {
        int number = -3;
        try {
            String string = JOptionPane.showInputDialog("Введите время на экзамен в минутах");
            if (string == null) {
                return -1;
            }
            number = Integer.parseInt(string);
            if (number < 0) {
                return -3;
            }
        } catch (HeadlessException | NumberFormatException e) {
        }
        return number;
    }

    @Override
    public void createInterface() {
        enterListener = new EnterListener();
        imagePanel = new ImagePanel();
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(Data.BUTTONS_COLOR, true), 1, true));
        panel.add(imagePanel);
        inputPlace = new JTextArea("");
        inputPlace.setBounds(60, panel.getHeight() - 38, panel.getWidth() - 120, 30);
        inputPlace.setFont(new Font("", 0, 16));
        inputPlace.setLineWrap(true);
        inputPlace.setWrapStyleWord(true);
        inputPlace.setBorder(BorderFactory.createLineBorder(new Color(Data.TEXT_COLOR, true), 1, true));
        inputPlace.addKeyListener(enterListener);
        panel.add(inputPlace);
        timer = new JLabel("");
        timer.setBounds((getWidth() - 70) / 2, 0, 70, 20);
        timer.setForeground(new Color(Data.TIMER_COLOR, true));
        timer.setFont(new Font("", 1, 16));
        add(timer);
    }

    public void check() {
        userAnswers.add(inputPlace.getText());
        answersTime.add(System.currentTimeMillis() - startTime);
        inputPlace.setText("");
        if (k < questionsCount) {
            try {
                imagePanel.setImage(ImageIO.read(new File("src/images/" + themeNum + "/" + randomQuestions.get(k) + ".png")));
                k++;
                startTime = System.currentTimeMillis();
                imagePanel.repaint();

            } catch (IOException ex) {
            }
        } else {
            JOptionPane.showMessageDialog(null, "Вы ответили на все вопросы, экзамен завершён!");
            questionsClose();
        }
    }

    public void questionsClose() {
        setVisible(false);
        Exam.resultFrame.buildResult(userAnswers, randomQuestions, themeNum, answersTime);
    }

    private class EnterListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                e.consume();
                check();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}
