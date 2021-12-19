package engine;

import engine.objects.CircularObject;
import engine.objects.PhysicalObject;
import engine.objects.PhysicalObjectPair;
import engine.physics.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AnimationPanel extends JPanel{
    protected PhysicsEngine2D engine;
    protected BufferedImage backgroundImage;

    public AnimationPanel(){
        System.out.println("AnimationPanel: empty constructor called!");
        //
    }


    public AnimationPanel(PhysicsEngine2D engine){
        System.out.println("AnimationPanel: non-empty constructor called!");
        this.engine = engine;
        setSize(engine.getWidth(),engine.getHeight());
        setVisible(true);
        setBackgroundImage();
    }

    protected void setBackgroundImage(){
        //System.out.println("Setting background image!");
        backgroundImage = new BufferedImage(engine.getWidth(), engine.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = backgroundImage.getGraphics();
        // use g to draw to the background image
        g.setColor(Color.WHITE);
        g.fillRect(0,0, engine.getWidth(), engine.getHeight());
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        // background image
        g2d.drawImage(backgroundImage, 0,0, backgroundImage.getWidth(), backgroundImage.getHeight(), null);
        // draw objects
        for(PhysicalObject po : engine.getObjects()){
            CircularObject co = (CircularObject) po;
            int xPos = (int)Math.round(co.getPosition().getX() - co.getRadius());
            int yPos = (int)Math.round(co.getPosition().getY() - co.getRadius());
            g2d.setColor(co.getColor());
            g2d.fillOval(xPos, yPos, (int)co.getRadius() * 2, (int)co.getRadius() * 2);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(xPos, yPos, (int)co.getRadius() * 2, (int)co.getRadius() * 2);
            // draw direction vector
            Direction directionVector = co.getDirectionVector();
            double xEndOfVector = co.getPosition().getX() + directionVector.getX() * co.getRadius();
            double yEndOfVector = co.getPosition().getY() + directionVector.getY() * co.getRadius();
            g2d.setColor(Color.BLACK);
            g2d.drawLine((int)Math.round(co.getPosition().getX()), (int)Math.round(co.getPosition().getY()),
                    (int)Math.round(xEndOfVector), (int)Math.round(yEndOfVector));
        }
        // draw collision vectors
        ArrayList<PhysicalObjectPair> collisionPairs = engine.getPhysicsManager().getCurrentCollisionPairs();
        for(PhysicalObjectPair pair : collisionPairs) {
            g2d.setColor(Color.RED);
            g2d.drawLine((int) Math.round(pair.getA().getPosition().getX()), (int) Math.round(pair.getA().getPosition().getY()),
                    (int) Math.round(pair.getB().getPosition().getX()), (int) Math.round(pair.getB().getPosition().getY()));

        }
        Toolkit.getDefaultToolkit().sync();
    }


}
