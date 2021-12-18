package enginespawns.airhockey.display;

import engine.objects.PhysicalObject;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.display.helpers.ScoreBoard;
import enginespawns.airhockey.objects.CircularHockeyObject;
import enginespawns.airhockey.team.TeamEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AirHockeyGameStatsPanel extends JPanel {
    int width;
    int height = 100;
    BufferedImage backgroundImage;
    AirHockey engine;
    int scoreBoardX;
    int scoreBoardY = 10;
    // colors
    // basic
    Color basicBackgroundColorUpper = new Color(20,0 ,10 );
    Color basicBackgroundColorLower = new Color(20, 0, 65);
    // score board
    Color scoreBoardBackgroundColor = new Color(10,10,10);
    Color scoreBoardLEDOffColor = new Color(15,20,15);
    Color scoreBoardLEDOnColor = new Color(150, 20, 20);

    public AirHockeyGameStatsPanel(int width, AirHockey engine){
        super();
        this.width = width;
        scoreBoardX = (int)Math.round(width / 2.0) - 62;
        this.engine = engine;
        setBackgroundImage();

        setSize(width, height);
        setVisible(true);
    }

    private void setBackgroundImage(){
        backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = backgroundImage.getGraphics();
        // background
        g.setColor(basicBackgroundColorUpper);
        int pixelDistance = height;
        double trueRed = basicBackgroundColorUpper.getRed();
        double trueGreen = basicBackgroundColorUpper.getGreen();
        double trueBlue = basicBackgroundColorUpper.getBlue();
        double redStep = (basicBackgroundColorUpper.getRed() - basicBackgroundColorLower.getRed()) / (double)pixelDistance;
        double greenStep = (basicBackgroundColorUpper.getGreen() - basicBackgroundColorLower.getGreen()) / (double)pixelDistance;
        double blueStep = (basicBackgroundColorUpper.getBlue() - basicBackgroundColorLower.getBlue()) / (double)pixelDistance;
        for(int i = 0; i < height; ++i){
            g.drawLine(0,i, width, i);
            trueRed = trueRed - redStep;
            trueGreen = trueGreen - greenStep;
            trueBlue = trueBlue - blueStep;
            g.setColor(new Color((int)Math.round(trueRed),
                    (int)Math.round(trueGreen),
                    (int)Math.round(trueBlue)));
        }
        // score
        ScoreBoard.drawEmptyScoreBoard(backgroundImage, scoreBoardX,scoreBoardY, scoreBoardBackgroundColor, scoreBoardLEDOffColor);

    }





    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        //
        g2d.drawImage(backgroundImage, 0,0, backgroundImage.getWidth(), backgroundImage.getHeight(), null);
        // draw current score
        int homeScore = engine.getHomeGoals();
        int firstDigit = (int)(homeScore / 10.0);
        int secondDigit = homeScore - firstDigit * 10;
        ScoreBoard.drawDigitToPanel(g2d, scoreBoardX, scoreBoardY, 0, firstDigit, scoreBoardLEDOnColor);
        ScoreBoard.drawDigitToPanel(g2d, scoreBoardX, scoreBoardY, 1, secondDigit, scoreBoardLEDOnColor);
        int awayScore = engine.getAwayGoals();
        firstDigit = (int)(awayScore / 10.0);
        secondDigit = awayScore - firstDigit * 10;
        ScoreBoard.drawDigitToPanel(g2d, scoreBoardX, scoreBoardY, 2, firstDigit, scoreBoardLEDOnColor);
        ScoreBoard.drawDigitToPanel(g2d, scoreBoardX, scoreBoardY, 3, secondDigit, scoreBoardLEDOnColor);
        Toolkit.getDefaultToolkit().sync();
    }
}
