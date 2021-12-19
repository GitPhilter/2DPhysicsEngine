package enginespawns.airhockey.evaluation.shootingrange;

import engine.objects.PhysicalObject;
import engine.physics.Position;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.display.AirHockeyEngineFrame;
import enginespawns.airhockey.evaluation.EvaluationInfo;
import enginespawns.airhockey.evaluation.GameResult;
import enginespawns.airhockey.manager.implementations.AirHockeyPhysicsManager;
import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.objects.Puck;
import enginespawns.airhockey.objects.PuckSpawn;
import enginespawns.airhockey.team.TeamEnum;

import java.awt.*;
import java.util.ArrayList;

public class ShootingRange extends AirHockey {
    int puckSpawnCounter = 0;
    private boolean running;
    private int currentTick = 0;
    private final int maxTicksPerShot = 2400;

    public ShootingRange(boolean isVisible){
        super();
        this.isVisible = isVisible;
        this.width = 800;
        this.height = 400;
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
        puck.setXSpeed(0);
        puck.setYSpeed(0);
        addObject(puck);
        // players
        if(player_1 != null){
            this.player_1 = player_1;
            objects.add(player_1);
        }
        if(player_2 != null){
            this.player_2 = player_2;
            objects.add(player_2);
        }
        // game
        homeGoals = 0;
        awayGoals = 0;
        puckIsInRightGoal = false;
        puckIsInLeftGoal = false;
        // prevent game freeze (read comment above)
        currentTicksInSameHalf = 0;
        previousPitchHalf = null;
    }

    public EvaluationInfo getEvaluationOfPlayerDisc(PlayerStickDisc disc){
        //
        puckSpawnCounter = 0;
        homeGoals = 0;
        awayGoals = 0;
        puckIsInLeftGoal = false;
        puckIsInRightGoal = false;
        previousPitchHalf = null;
        // get puck spawns
        PuckSpawn[] puckSpawns = PuckSpawnCreator.getVerticalLinesToPositionEvenDistributed(this, 3,
                10, new Position(0, 200), 16);
        // set positions
        disc.setTeamEnum(TeamEnum.HOME);
        disc.setPosition(new Position(100,200));
        disc.setEngine(this);
        addObject(disc);
        PuckSpawn firstSpawn = puckSpawns[puckSpawnCounter];
        ++puckSpawnCounter;
        puck.setPosition(firstSpawn.getPosition());
        puck.setXSpeed(firstSpawn.getXSpeed());
        puck.setYSpeed(firstSpawn.getYSpeed());
        //
        running = true;
        int fps = 60;
        int nsPerFrame = (int)Math.round(1000000000.0 / fps);
        while(running){
            double start = System.nanoTime();
            // actual computational steps
            tick(puckSpawns);
            //
            if(isVisible){
                engineFrame.repaint();
                double timePassed = System.nanoTime() - start;
                if(timePassed < nsPerFrame){
                    try {
                        Thread.sleep((long)((nsPerFrame - timePassed) / (double)1000000));
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        // compute evaluation
        double score = 0;
        if(disc.getTeamEnum() == TeamEnum.HOME){
            score = homeGoals - awayGoals;
        } else {
            score = awayGoals - homeGoals;
        }
        return new EvaluationInfo(disc, score);
    }


    public void tick(PuckSpawn[] puckSpawns){
        //System.out.println("ShootingRange.tick()");
        ++currentTick;
        // call ticks to apply acceleration
        for(PhysicalObject po : objects){
            if(po.hasTick()){
                po.tick();
            }
        }
        physicsManager.moveObjects(this);
        if(puckIsInLeftGoal) {
            awayGoal(puckSpawns);
            return;
        }
        if(puckIsInRightGoal){
            homeGoal(puckSpawns);
            return;
        }
        // prevent game freeze
        TeamEnum currentPitchHalf = getPitchHalfWithPuck();
        if(currentPitchHalf == previousPitchHalf) {
            ++currentTicksInSameHalf;
            if(currentTicksInSameHalf > maxTicksAllowedInHalf) afterGoalReset(puckSpawns);
        } else {
            currentTicksInSameHalf = 0;
            previousPitchHalf = currentPitchHalf;
        }
        if(currentTick > maxTicksPerShot) afterGoalReset(puckSpawns);
        if(engineFrame != null){
            engineFrame.repaint();
        }
    }

    public void homeGoal(PuckSpawn[] puckSpawns){
        ++homeGoals;
        if(homeGoals > 99) {
            awayGoals = 0;
            homeGoals = 0;
        }
        afterGoalReset(puckSpawns);
    }

    public void awayGoal(PuckSpawn[] puckSpawns){
        ++awayGoals;
        if(awayGoals > 99) {
            homeGoals = 0;
            awayGoals = 0;
        }
        afterGoalReset(puckSpawns);
    }

    public void afterGoalReset(PuckSpawn[] puckSpawns){
        if(puckSpawnCounter >= puckSpawns.length) {
            running = false;
            return;
        }
        PuckSpawn currentSpawn = puckSpawns[puckSpawnCounter];
        ++puckSpawnCounter;
        puck.setPosition(currentSpawn.getPosition());
        puck.setXSpeed(currentSpawn.getXSpeed());
        puck.setYSpeed(currentSpawn.getYSpeed());
        puckIsInLeftGoal = false;
        puckIsInRightGoal = false;
        previousPitchHalf = null;
        currentTick = 0;
    }


    @Override
    public GameResult playSingleMatch(){
        return null;
    }

    @Override
    public void run(){
        System.out.println("Please run a single match on the shooting range, and not use run()!");
    }


}
