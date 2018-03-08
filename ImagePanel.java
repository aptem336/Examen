package exam;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel() {
        this.image = null;
        setLayout(null);
    }

    public ImagePanel(BufferedImage image) {
        this.image = image;
        setLayout(null);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getImage() != null) {
            g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), null);
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}
