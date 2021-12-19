package engine;

import javax.swing.*;
import java.awt.*;

public class EngineFrame extends JFrame {
    protected AnimationPanel animationPanel;

    public EngineFrame(){
        System.out.println("EngineFrame: empty constructor called!");
        //
    }

    public EngineFrame(PhysicsEngine2D engine){
        super();
        System.out.println("EngineFrame: non-empty constructor called!");
        setSize(engine.getWidth(), engine.getHeight());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);
        animationPanel = new AnimationPanel(engine);
        add(animationPanel);
        setVisible(true);
    }


    @Override
    public void paint(Graphics g){
        super.paint(g);
        animationPanel.repaint();
    }


}
