package enginespawns.airhockey;

import engine.AnimationPanel;
import engine.PhysicsEngine2D;
import engine.objects.CircularObject;
import engine.objects.PhysicalObject;
import engine.objects.PhysicalObjectPair;
import engine.physics.Direction;
import enginespawns.airhockey.objects.CircularHockeyObject;
import enginespawns.airhockey.team.TeamEnum;

import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;

public class AirHockeyAnimationPanel extends AnimationPanel {
    Color fieldColor = new Color(5,5, 20);
    Color lineColor = new Color(220,250,220);
    Color borderColor = new Color(0, 0, 0);
    Color borderLinesStartColor = new Color(200, 50, 170);
    Color borderLinesEndColor = new Color(20,0 ,10 );
    Color borderLineColor = new Color(50, 20, 180);
    Color goalRoofColor = new Color(180,230,160);
    Color goalPathwayColor = new Color(40, 40, 40);
    Color transparentColor = new Color(123,124,125);
    Color basicPlayerDiscColor = new Color(30, 0, 40);
    Color basicPlayerDiscHandleColor = new Color(30, 20, 60);
    int width;
    int height;
    double verticalBorderOffset = 1.0/6.0;
    double horizontalBorderOffset = 1.0/10.0;
    double absoluteVerticalBorderPixelOffset;
    double absoluteHorizontalBorderPixelOffset;
    protected BufferedImage goalOverlayImage;

    public AirHockeyAnimationPanel(PhysicsEngine2D engine, int width, int height){
        super();
        this.width = width;
        this.height = height;
        this.absoluteVerticalBorderPixelOffset = width * horizontalBorderOffset;
        this.absoluteHorizontalBorderPixelOffset = height * verticalBorderOffset;
        System.out.println("vertical pixel offset: " + absoluteVerticalBorderPixelOffset);
        System.out.println("horizontal pixel offset: " + absoluteHorizontalBorderPixelOffset);

        this.engine = engine;
        setBackgroundImage();
        setGoalOverlayImage();
        setSize(width, height);
        setVisible(true);

    }

    protected void setGoalOverlayImage(){
        goalOverlayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = goalOverlayImage.getGraphics();
        g.setColor(transparentColor);
        g.fillRect(0,0,width,height);
        goalOverlayImage = toBufferedImage(makeColorTransparent(goalOverlayImage, transparentColor));
        g = goalOverlayImage.getGraphics();
        // goals
        // left goal
        g.setColor(goalRoofColor);
        g.fillRect((int)Math.round(absoluteHorizontalBorderPixelOffset * (3.0/4.0)),
                (int)Math.round(height * (2.0/6.0)),
                (int)Math.round(absoluteHorizontalBorderPixelOffset * (3.0/16.0)),
                (int)Math.round(height * (2.0/6.0)));

        // right goal
        g.setColor(goalRoofColor);
        g.fillRect((int)Math.round(width - absoluteHorizontalBorderPixelOffset * (15.0/16.0)),
                (int)Math.round(height * (2.0/6.0)),
                (int)Math.round(absoluteHorizontalBorderPixelOffset * (3.0/16.0)),
                (int)Math.round(height * (2.0/6.0)));

    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        // Return the buffered image
        return bimage;
    }

    public static Image makeColorTransparent(BufferedImage im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;
            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };
        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    @Override
    protected void setBackgroundImage(){
        System.out.println("Setting AirHockeyBackground image!");
        backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = backgroundImage.getGraphics();
        //field
        g.setColor(fieldColor);
        g.fillRect(0,0, width, height);
        g.setColor(lineColor);
        g.drawLine((int)(width/ 2), (int)(height / 6), width/ 2, (int)(height * 5/6));
        // borders
        g.setColor(borderColor);
        g.fillRect(0, 0, width, (int)(height / 6));
        g.fillRect(0, 0, width / 10, height);
        g.fillRect(0, height * 5/6, width, height);
        g.fillRect(width * 9/10, 0, width, height);
        // experimental border design
        int numberOfLinesPerBorder = 8;
        double linePixelStep = (int)Math.round(absoluteVerticalBorderPixelOffset / numberOfLinesPerBorder);
        g.setColor(borderLinesStartColor);
        int red = borderLinesStartColor.getRed();
        int green = borderLinesStartColor.getGreen();
        int blue = borderLinesStartColor.getBlue();
        double redValueStep = ((double)borderLinesEndColor.getRed() - (double)red) / numberOfLinesPerBorder;
        double greenValueStep = ((double)borderLinesEndColor.getGreen() - (double)green) / numberOfLinesPerBorder;
        double blueValueStep = ((double)borderLinesEndColor.getBlue() - (double)blue) / numberOfLinesPerBorder;
        for(int y = 0; y < absoluteVerticalBorderPixelOffset; y = (int)Math.round(y + linePixelStep)){
            int x = (int)Math.round((y / absoluteVerticalBorderPixelOffset)* (width * (1.0/10.0)));
            g.drawLine(x, y, width - x, y);
            g.drawLine(x, height - y, width - x, height - y);
            //
            g.drawLine(x, y, x, height - y);
            g.drawLine(width - x, y, width - x, height - y);
            // color
            red = (int)Math.round(red + redValueStep);
            green = (int)Math.round(green + greenValueStep);
            blue = (int)Math.round(blue + blueValueStep);
            g.setColor(new Color(red, green, blue));
        }
        // draw last enclosing lines
        g.setColor(borderLineColor);
        g.drawLine((int)Math.round(absoluteHorizontalBorderPixelOffset),
                (int)Math.round(absoluteVerticalBorderPixelOffset),
                width - (int)Math.round(absoluteHorizontalBorderPixelOffset),
                (int)Math.round(absoluteVerticalBorderPixelOffset));
        g.drawLine((int)Math.round(absoluteHorizontalBorderPixelOffset),
                height - (int)Math.round(absoluteVerticalBorderPixelOffset),
                width - (int)Math.round(absoluteHorizontalBorderPixelOffset),
                height - (int)Math.round(absoluteVerticalBorderPixelOffset));
        //
        g.drawLine((int)Math.round(absoluteHorizontalBorderPixelOffset),
                (int)Math.round(absoluteVerticalBorderPixelOffset),
                (int)Math.round(absoluteHorizontalBorderPixelOffset),
                height - (int)Math.round(absoluteVerticalBorderPixelOffset));
        g.drawLine(width - (int)Math.round(absoluteHorizontalBorderPixelOffset),
                (int)Math.round(absoluteVerticalBorderPixelOffset),
                width - (int)Math.round(absoluteHorizontalBorderPixelOffset),
                height - (int)Math.round(absoluteVerticalBorderPixelOffset));
        //
        g.setColor(goalPathwayColor);
        g.fillRect((int)Math.round(absoluteHorizontalBorderPixelOffset * (15.0/16.0)),
                (int)Math.round(height * (2.0/6.0)),
                (int)Math.round(absoluteHorizontalBorderPixelOffset * (1.0/16.0)),
                (int)Math.round(height * (2.0/6.0)));
        g.setColor(goalPathwayColor);
        g.fillRect((int)Math.round(width - absoluteHorizontalBorderPixelOffset),
                (int)Math.round(height * (2.0/6.0)),
                (int)Math.round(absoluteHorizontalBorderPixelOffset * (1.0/16.0)),
                (int)Math.round(height * (2.0/6.0)));

    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        //
        g2d.drawImage(backgroundImage, 0,0, backgroundImage.getWidth(), backgroundImage.getHeight(), null);
        // draw objects
        for(PhysicalObject po : engine.getObjects()){
            CircularHockeyObject co = (CircularHockeyObject) po;
            int xPos = (int)(Math.round(co.getPosition().getX() - co.getRadius()) + absoluteHorizontalBorderPixelOffset);
            int yPos = (int)(Math.round(co.getPosition().getY() - co.getRadius()) + absoluteVerticalBorderPixelOffset);
            g2d.setColor(co.getColor());
            g2d.fillOval(xPos, yPos, (int)co.getRadius() * 2, (int)co.getRadius() * 2);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(xPos, yPos, (int)co.getRadius() * 2, (int)co.getRadius() * 2);

            if(co.getTeamEnum() != TeamEnum.UNKNOWN){
                // inner circle rim
                double rimRatio = 1.0/10.0;
                int innerRimXPos = (int)Math.round(xPos + co.getRadius() * rimRatio);
                int innerRimYPos = (int)Math.round(yPos + co.getRadius() * rimRatio);
                g2d.setColor(basicPlayerDiscColor);
                g2d.fillOval(innerRimXPos, innerRimYPos,
                        (int)Math.round(co.getRadius() * (2 - (2 * rimRatio))),
                        (int)Math.round(co.getRadius() * (2 - (2 * rimRatio))));
                g2d.setColor(Color.BLACK);
                g2d.drawOval(innerRimXPos, innerRimYPos,
                        (int)Math.round(co.getRadius() * 2 - (2 * rimRatio)),
                        (int)Math.round(co.getRadius() * 2 - (2 * rimRatio)));
                // handle
                double handleRatio = 0.4;
                int handleXPos = (int)Math.round(xPos + co.getRadius() * (1 - handleRatio));
                int handleYPos = (int)Math.round(yPos + co.getRadius() * (1 - handleRatio) );
                g2d.setColor(basicPlayerDiscHandleColor);
                g2d.fillOval(handleXPos, handleYPos,
                        (int)Math.round(co.getRadius() * ((2 * handleRatio))),
                        (int)Math.round(co.getRadius() * ((2 * handleRatio))));
                g2d.setColor(Color.BLACK);
                g2d.drawOval(handleXPos, handleYPos,
                        (int)Math.round(co.getRadius() * ((2 * handleRatio))),
                        (int)Math.round(co.getRadius() * ((2 * handleRatio))));
            }
        }
        // draw goal overlay
        g.drawImage(goalOverlayImage, 0, 0, null);
        Toolkit.getDefaultToolkit().sync();
    }
}
