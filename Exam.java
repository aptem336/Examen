package exam;

import java.awt.Toolkit;

public class Exam {

    public static ChoiceFrame choiseFrame;
    public static QuestionsFrame questionsFrame;
    public static ResultFrame resultFrame;

    public static void main(String[] args) {
        choiseFrame = new ChoiceFrame();
        choiseFrame.setSize(350, (Data.themeNames.length + 2) * 45);
        choiseFrame.init("");
        choiseFrame.createInterface();
        choiseFrame.setVisible(true);
        questionsFrame = new QuestionsFrame();
        questionsFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        questionsFrame.init("› «¿Ã≈Õ");
        questionsFrame.createInterface();
        resultFrame = new ResultFrame();
        resultFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        resultFrame.init("–≈«”À‹“¿“€");
        resultFrame.createInterface();
    }

}
