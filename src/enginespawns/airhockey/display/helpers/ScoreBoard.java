package enginespawns.airhockey.display.helpers;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class ScoreBoard {
    static double scale = 1.0;
    static int verticalCorrection = (int)Math.round(4.0 * scale);
    static int scoreDigitWidth = 21;
    static int scoreDigitHeight = 41;
    static int smallGap = 4;
    static int bigGap = 32;
    //
    static int secondDigitXSetOff = scoreDigitWidth + smallGap;
    static int thirdDigitXSetOff = 2 * scoreDigitWidth + smallGap + bigGap;
    static int fourthDigitSetOff = 3 * scoreDigitWidth + 2 * smallGap + bigGap;

    public static void drawEmptyScoreBoard(BufferedImage image, int x, int y,
                                           Color backgroundColor, Color digitColorOff){
        // 21 x 41
        drawScoreDigitToBackground(image, x,10,scale, backgroundColor, digitColorOff);
        drawScoreDigitToBackground(image, x + secondDigitXSetOff,10, scale,
                backgroundColor, digitColorOff);
        drawScoreDigitToBackground(image, x + thirdDigitXSetOff,10, scale,
                backgroundColor, digitColorOff);
        drawScoreDigitToBackground(image, x + fourthDigitSetOff,10, scale,
                backgroundColor, digitColorOff);
    }

    private static void drawScoreDigitToBackground(BufferedImage image, int x, int y, double scale,
                                                   Color backgroundColor, Color digitColorOff ){
        // scaling
        int scoreDigitWidth = (int)Math.round(21.0 * scale);
        int scoreDigitHeight = (int)Math.round(41.0 * scale);
        //
        Graphics2D g = (Graphics2D) image.getGraphics();
        //
        g.setColor(backgroundColor);
        g.fillRect(x,y, scoreDigitWidth, scoreDigitHeight);
        // horizontal elements
        a(g, x, y, digitColorOff);
        d(g, x, y, digitColorOff);
        g(g, x, y, digitColorOff);
        // vertical elements
        b(g,x, y, digitColorOff);
        c(g,x, y, digitColorOff);
        e(g,x, y, digitColorOff);
        f(g,x, y, digitColorOff);
    }

    private static void drawVerticalLED(Graphics2D g, int x, int y, Color digitColorOff){
        g.setColor(digitColorOff);
        g.drawLine(x, (int)Math.round(y + 2.0 * scale), x, (int)Math.round(y + 14.0 * scale));
        g.drawLine((int)Math.round(x + 1.0 * scale), (int)Math.round(y + 1.0 * scale),
                (int)Math.round(x + 1.0 * scale), (int)Math.round(y + 15.0 * scale));
        g.drawLine((int)Math.round(x + 2.0 * scale), y, (int)Math.round(x + 2.0 * scale),
                (int)Math.round(y + 16.0 * scale));
        g.drawLine((int)Math.round(x + 3.0 * scale), (int)Math.round(y + 1.0 * scale),
                (int)Math.round(x + 3.0 * scale), (int)Math.round(y + 15.0 * scale));
        g.drawLine((int)Math.round(x + 4.0 * scale), (int)Math.round(y + 2.0 * scale),
                (int)Math.round(x + 4.0 * scale), (int)Math.round(y + 14.0 * scale));
    }

    private static void a(Graphics2D g, int x, int y, Color LEDColor){
        drawHorizontalLED(g, x, y, LEDColor);
    }

    private static void d(Graphics2D g, int x, int y, Color LEDColor){
        int horizontalLEDYValue = (int)Math.round(y + ((scoreDigitHeight/ 2.0) - 1 - (verticalCorrection * 0.5)));
        drawHorizontalLED(g, x, horizontalLEDYValue, LEDColor);
    }

    private static void g(Graphics2D g, int x, int y, Color LEDColor){
        int horizontalLEDYValue = (int)Math.round(y + (scoreDigitHeight - 1) - verticalCorrection);
        drawHorizontalLED(g, x, horizontalLEDYValue, LEDColor);
    }

    private static void b(Graphics2D g, int x, int y, Color LEDColor){
        int upperLEDsStartingYValue = (int)Math.round(y + 3.0 * scale);
        drawVerticalLED(g, x, upperLEDsStartingYValue, LEDColor);
    }

    private static void c(Graphics2D g, int x, int y, Color LEDColor){
        int upperLEDsStartingYValue = (int)Math.round(y + 3.0 * scale);
        int rightSideStartingX = (int)Math.round(x + (scoreDigitWidth - 1 - (4.0 * scale)));
        drawVerticalLED(g, rightSideStartingX, upperLEDsStartingYValue, LEDColor);
    }

    private static void e(Graphics2D g, int x, int y, Color LEDColor){
        int lowerLEDsStartingYValue = (int)Math.round(y + (scoreDigitHeight / 2.0) - 1 + 1.0 * scale);
        drawVerticalLED(g, x, lowerLEDsStartingYValue, LEDColor);
    }

    private static void f(Graphics2D g, int x, int y, Color LEDColor){
        int rightSideStartingX = (int)Math.round(x + (scoreDigitWidth - 1 - (4.0 * scale)));
        int lowerLEDsStartingYValue = (int)Math.round(y + (scoreDigitHeight / 2.0) - 1 + 1.0 * scale);
        drawVerticalLED(g, rightSideStartingX, lowerLEDsStartingYValue, LEDColor);
    }



    private static void drawHorizontalLED(Graphics2D g, int x, int y, Color LEDColor){
        g.setColor(LEDColor);
        g.drawLine((int)Math.round(x + 4.0 * scale), y,(int)Math.round(x + 16.0 * scale), y);
        g.drawLine((int)Math.round(x + 3.0 * scale), (int)Math.round(y + 1.0 * scale),
                (int)Math.round(x + 17.0 * scale), (int)Math.round(y + 1.0 * scale));
        g.drawLine((int)Math.round(x + 2.0 * scale), (int)Math.round(y + 2.0 * scale),
                (int)Math.round(x + 18.0 * scale), (int)Math.round(y + 2.0 * scale));
        g.drawLine((int)Math.round(x + 3.0 * scale), (int)Math.round(y + 3.0 * scale),
                (int)Math.round(x + 17.0 * scale), (int)Math.round(y + 3.0 * scale));
        g.drawLine((int)Math.round(x + 4.0 * scale), (int)Math.round(y + 4.0 * scale),
                (int)Math.round(x + 16.0 * scale), (int)Math.round(y + 4.0 * scale));
    }

    public static void drawDigitToPanel(Graphics2D g, int x, int y, int panel, int digit, Color LEDColor){
        if(panel == 1) x = x + secondDigitXSetOff;
        if(panel == 2) x = x + thirdDigitXSetOff;
        if(panel == 3) x = x + fourthDigitSetOff;
        drawDigit(g, x, y, digit, LEDColor);
    }

    public static void drawDigitToPanel(BufferedImage image, int x, int y, int panel, int digit, Color LEDColor){
        Graphics2D g = (Graphics2D) image.getGraphics();
        drawDigitToPanel(g, x, y, panel, digit, LEDColor);
    }

    private static void drawDigit(Graphics2D g, int x, int y, int digit, Color LEDColor){
        if(digit == 0){
            a(g, x, y, LEDColor);
            b(g, x, y, LEDColor);
            c(g, x, y, LEDColor);
            e(g, x, y, LEDColor);
            f(g, x, y, LEDColor);
            g(g, x, y, LEDColor);
        } else if(digit == 1){
            c(g, x, y, LEDColor);
            f(g, x, y, LEDColor);
        } else if(digit == 2){
            a(g, x, y, LEDColor);
            c(g, x, y, LEDColor);
            d(g, x, y, LEDColor);
            e(g, x, y, LEDColor);
            g(g, x, y, LEDColor);
        } else if(digit == 3){
            a(g, x, y, LEDColor);
            c(g, x, y, LEDColor);
            d(g, x, y, LEDColor);
            f(g, x, y, LEDColor);
            g(g, x, y, LEDColor);
        } else if(digit == 4){
            b(g,x, y, LEDColor);
            c(g, x, y, LEDColor);
            d(g, x, y, LEDColor);
            f(g, x, y, LEDColor);

        } else if(digit == 5){
            a(g,x, y, LEDColor);
            b(g, x, y, LEDColor);
            d(g, x, y, LEDColor);
            f(g, x, y, LEDColor);
            g(g, x, y, LEDColor);
        } else if(digit == 6){
            a(g,x, y, LEDColor);
            b(g, x, y, LEDColor);
            d(g, x, y, LEDColor);
            e(g, x, y, LEDColor);
            f(g, x, y, LEDColor);
            g(g, x, y, LEDColor);
        } else if(digit == 7){
            a(g, x, y, LEDColor);
            c(g, x, y, LEDColor);
            f(g, x, y, LEDColor);
        } else if(digit == 8){
            a(g,x, y, LEDColor);
            b(g, x, y, LEDColor);
            c(g, x, y, LEDColor);
            d(g, x, y, LEDColor);
            e(g, x, y, LEDColor);
            f(g, x, y, LEDColor);
            g(g, x, y, LEDColor);
        } else if(digit == 9){
            a(g,x, y, LEDColor);
            b(g, x, y, LEDColor);
            c(g, x, y, LEDColor);
            d(g, x, y, LEDColor);
            f(g, x, y, LEDColor);
            g(g, x, y, LEDColor);
        } else {
            d(g, x, y, LEDColor);
        }
    }
}
