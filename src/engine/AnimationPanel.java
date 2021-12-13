package engine;

import engine.objects.CircularObject;
import engine.objects.PhysicalObject;
import engine.objects.PhysicalObjectPair;
import engine.physics.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AnimationPanel extends JPanel implements ActionListener {
    PhysicsEngine2D engine;
    double previousExecutionTime;
    Timer timer;

    public AnimationPanel(PhysicsEngine2D engine){
        this.engine = engine;
        setSize(engine.getWidth(),engine.getHeight());
        setVisible(true);
        previousExecutionTime = 0;
        // delay: 17 ~ 60 fps, 42 ~ 24 fps
        timer = new Timer(17, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        double time = System.nanoTime();
        /*
        if(previousExecutionTime != 0){
            double timeBetweenAnimations = time - previousExecutionTime;
            System.out.println("-> Time between animations: " + timeBetweenAnimations + " ns");
        }
         */
        previousExecutionTime = time;
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
        //System.out.println("Number of collision pairs: " + collisionPairs.size());
        for(PhysicalObjectPair pair : collisionPairs) {
            g2d.setColor(Color.RED);
            g2d.drawLine((int) Math.round(pair.getA().getPosition().getX()), (int) Math.round(pair.getA().getPosition().getY()),
                    (int) Math.round(pair.getB().getPosition().getX()), (int) Math.round(pair.getB().getPosition().getY()));

        }
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        engine.getPhysicsManager().moveObjects(engine);
        repaint();
    }
}