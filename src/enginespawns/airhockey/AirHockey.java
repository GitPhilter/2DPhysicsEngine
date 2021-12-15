package enginespawns.airhockey;

import engine.EngineFrame;
import engine.PhysicsEngine2D;
import engine.objects.PhysicalObject;
import engine.physics.Position;
import engine.physics.manager.implementations.PhysicsManager_2;
import enginespawns.airhockey.manager.helpers.AirHockeyBoundaryCheck;
import enginespawns.airhockey.manager.implementations.AirHockeyPhysicsManager;
import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.objects.Puck;
import enginespawns.airhockey.objects.playerstickdiscimplementations.NeuralNetworkPlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.RandomMovementPlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.TrainingPlayerStickDisc;
import enginespawns.airhockey.team.TeamEnum;
import logger.Logger;

import java.awt.*;
import java.util.ArrayList;

public class AirHockey extends PhysicsEngine2D {
    // game
    protected Puck puck;
    protected int homeGoals;
    protected int awayGoals;
    protected boolean puckIsInRightGoal;
    protected boolean puckIsInLeftGoal;
    protected PlayerStickDisc player_1;
    protected PlayerStickDisc player_2;


    public AirHockey(PlayerStickDisc player_1, PlayerStickDisc player_2){
        this(player_1, player_2, true);
    }

    public AirHockey(PlayerStickDisc player_1, PlayerStickDisc player_2, boolean isVisible){
        super(800, 400, isVisible);
        objects = new ArrayList<>();
        physicsManager = new AirHockeyPhysicsManager();
        if (isVisible) {
            engineFrame = new AirHockeyEngineFrame(this, 1000, 600);
        } else {
            engineFrame = null;
            //System.out.println("AirHockey: engine = null");
            //engineFrame = new AirHockeyEngineFrame(this, 1000, 600);
        }
        puck = new Puck(new Position((int)(width/2), (int)(height/2)), 10, Color.WHITE);
        //puck = new Puck(new Position(720, 200), 10, Color.WHITE);
        double randomXSpeed = Math.random() * 10 - 5;
        double randomYSpeed = Math.random() * 10 - 5;
        puck.setXSpeed(1);
        puck.setYSpeed(0);
        addObject(puck);
        // players
        this.player_1 = player_1;
        objects.add(player_1);
        this.player_2 = player_2;
        objects.add(player_2);
        // game
        homeGoals = 0;
        awayGoals = 0;
        puckIsInRightGoal = false;
        puckIsInLeftGoal = false;
        System.out.println("Yo");
    }

    public void tick(){
        //Logger.print("This is AirHockey.tick()!");
        // call ticks to apply acceleration
        for(PhysicalObject po : objects){
            if(po.hasTick()){
                po.tick();
            }
        }
        physicsManager.moveObjects(this);
        if(puckIsInLeftGoal) {
            awayGoal();
            //System.out.println("AirHockey.tick(): Puck is in left Goal!");
            return;
        }
        if(puckIsInRightGoal){
            homeGoal();
            //System.out.println("AirHockey.tick(): Puck is in right Goal!");
            return;
        }
        if(engineFrame != null){
            engineFrame.repaint();
        }
    }

    public void homeGoal(){
        ++homeGoals;
        afterGoalReset(TeamEnum.AWAY);
    }

    public void awayGoal(){
        ++awayGoals;
        afterGoalReset(TeamEnum.HOME);
    }

    public void afterGoalReset(TeamEnum teamThatReceivesPuck){
        // time break?
        puck.setPosition(new Position((int)Math.round(width/ 2.0), (int)Math.round(height / 2.0)));
        double randomXSpeed = Math.random() * 10 - 5;
        double randomYSpeed = Math.random() * 10 - 5;
        puck.setXSpeed(randomXSpeed);
        puck.setYSpeed(randomYSpeed);
        puckIsInLeftGoal = false;
        puckIsInRightGoal = false;
    }

    public void setPuckIsInLeftGoal(boolean b){
        this.puckIsInLeftGoal = b;
    }

    public void setPuckIsInRightGoal(boolean b){
        this.puckIsInRightGoal = b;
    }
}
