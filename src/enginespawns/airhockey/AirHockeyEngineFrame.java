package enginespawns.airhockey;

import engine.AnimationPanel;
import engine.EngineFrame;

public class AirHockeyEngineFrame extends EngineFrame {
    int width;
    int height;

    public AirHockeyEngineFrame(AirHockey engine, int width, int height){
        super();
        this.width = width;
        this.height = height;
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);
        animationPanel = new AirHockeyAnimationPanel(engine, width, height);
        add(animationPanel);
        setVisible(true);
    }

}
