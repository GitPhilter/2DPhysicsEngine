package evolutioninterface;

import pictureloader.PictureLoader;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EvoPanel extends JPanel {
    int width;
    int height;
    BufferedImage backgroundImage;

    public EvoPanel(int width, int height){
        super();
        System.out.println("EvoPanel.constructor called!");
        this.width = width;
        this.height = height;
        setSize(width, height);
        setVisible(true);
        backgroundImage = PictureLoader.loadImageFromRes("evo1.jpg");
        //
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(backgroundImage,0 ,0, width,height, Color.BLACK, null);
    }

}
