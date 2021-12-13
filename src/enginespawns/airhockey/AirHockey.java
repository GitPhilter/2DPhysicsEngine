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
import enginespawns.airhockey.objects.playerstickdiscimplementations.RandomMovementPlayerStickDisc;
import enginespawns.airhockey.team.TeamEnum;
import logger.Logger;

import java.awt.*;
import java.util.ArrayList;

public class AirHockey extends PhysicsEngine2D {
    // game
    Puck puck;
    int homeGoals;
    int awayGoals;
    boolean puckIsInRightGoal;
    boolean puckIsInLeftGoal;

    public AirHockey(){
        super();
        this.width = 800;
        this.height = 400;
        objects = new ArrayList<>();
        physicsManager = new AirHockeyPhysicsManager();
        engineFrame = new AirHockeyEngineFrame(this, 1000, 600);
        //Puck puck = new Puck(new Position((int)(width/2), (int)(height/2)), 10, Color.WHITE);
        puck = new Puck(new Position(720, 200), 10, Color.WHITE);
        double randomXSpeed = Math.random() * 10 - 5;
        double randomYSpeed = Math.random() * 10 - 5;
        puck.setXSpeed(1);
        puck.setYSpeed(0);
        addObject(puck);
        // players
        Position player1Position = new Position(width * (1.0/8.0), height / 2.0);
        RandomMovementPlayerStickDisc player_1 = new RandomMovementPlayerStickDisc("Player_1", player1Position, 20, Color.BLUE);
        player_1.setTeamEnum(TeamEnum.HOME);
        objects.add(player_1);
        Position player2Position = new Position(width * (7.0/8.0), height / 2.0);
        RandomMovementPlayerStickDisc player_2 = new RandomMovementPlayerStickDisc("Player_2", player2Position, 20, Color.RED);
        player_2.setTeamEnum(TeamEnum.AWAY);
        objects.add(player_2);
        // game
        homeGoals = 0;
        awayGoals = 0;
        puckIsInRightGoal = false;
        puckIsInLeftGoal = false;
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
            System.out.println("AirHockey.tick(): Puck is in left Goal!");
            return;
        }
        if(puckIsInRightGoal){
            homeGoal();
            System.out.println("AirHockey.tick(): Puck is in right Goal!");

            return;
        }
        engineFrame.repaint();
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
