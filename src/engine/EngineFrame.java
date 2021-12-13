package engine;

import javax.swing.*;
import java.awt.*;

public class EngineFrame extends JFrame {
    protected AnimationPanel animationPanel;

    public EngineFrame(){
        //
    }

    public EngineFrame(PhysicsEngine2D engine){
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
