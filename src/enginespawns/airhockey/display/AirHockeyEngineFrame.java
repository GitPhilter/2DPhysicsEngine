package enginespawns.airhockey.display;

import engine.EngineFrame;
import enginespawns.airhockey.AirHockey;

import java.awt.*;

public class AirHockeyEngineFrame extends EngineFrame {
    int width;
    int height;
    AirHockeyGameStatsPanel statsPanel;

    public AirHockeyEngineFrame(AirHockey engine, int width, int height){
        super();
        this.width = width;
        this.height = height;
        setSize(width, height + 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(null);
        GraphicsConfiguration config = getGraphicsConfiguration();
        Rectangle bounds = config.getBounds();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);
        int x = (int)Math.round((bounds.width/2.0) - getWidth()/2.0);
        setLocation(x, 0);
        animationPanel = new AirHockeyAnimationPanel(engine, width, height);
        add(animationPanel);
        statsPanel = new AirHockeyGameStatsPanel(width, engine);
        add(statsPanel);
        statsPanel.setLocation(0, height + 1);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        animationPanel.repaint();
        statsPanel.repaint();
    }
}
