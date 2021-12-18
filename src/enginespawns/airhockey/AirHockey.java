package enginespawns.airhockey;

import engine.PhysicsEngine2D;
import engine.objects.PhysicalObject;
import engine.physics.Position;
import enginespawns.airhockey.display.AirHockeyEngineFrame;
import enginespawns.airhockey.evaluation.GameResult;
import enginespawns.airhockey.manager.implementations.AirHockeyPhysicsManager;
import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.objects.Puck;
import enginespawns.airhockey.team.TeamEnum;

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
    //
    GameResult previousGameResult;
    // the game sometimes freezes in the invisible mode, when running a large amount of games
    // the assumption is, that maybe the puck gets stuck between a playerDisc and the walls eternally.
    // to prevent this from happening, after 600 ticks (equals 10 seconds in visible mode at 60 fps) in the same
    // half of the pitch, the puck will be reset to kickOff.
    int currentTicksInSameHalf;
    int maxTicksAllowedInHalf = 600;
    TeamEnum previousPitchHalf;

    public AirHockey(PlayerStickDisc player_1, PlayerStickDisc player_2){
        this(player_1, player_2, true);
    }

    public AirHockey(PlayerStickDisc player_1, PlayerStickDisc player_2, boolean isVisible){
        super();
        this.width = 800;
        this.height = 400;
        this.isVisible = isVisible;
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
        // previous game
        previousGameResult = null;
        // prevent game freeze (read comment above)
        currentTicksInSameHalf = 0;
        previousPitchHalf = null;
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
        // prevent game freeze
        TeamEnum currentPitchHalf = getPitchHalfWithPuck();
        if(currentPitchHalf == previousPitchHalf) {
            ++currentTicksInSameHalf;
            //System.out.println("currentTicksInSameHalf: " + currentTicksInSameHalf);
            if(currentTicksInSameHalf > maxTicksAllowedInHalf) afterGoalReset(TeamEnum.UNKNOWN);
        } else {
            currentTicksInSameHalf = 0;
        }
        previousPitchHalf = currentPitchHalf;
        if(engineFrame != null){
            engineFrame.repaint();
        }
    }

    public GameResult playSingleMatch(){
        // reset positions
        if(player_1.getTeamEnum() == player_2.getTeamEnum()) return null;
        if(player_1.getTeamEnum() == TeamEnum.HOME){
            player_1.setPosition(new Position(100,200));
            player_2.setPosition(new Position(700,200));
        } else {
            player_2.setPosition(new Position(100,200));
            player_1.setPosition(new Position(700,200));
        }
        //
        previousGameResult = null;
        running = true;
        int fps = 60;
        int nsPerFrame = (int)Math.round(1000000000.0 / fps);
        while(running){
            double start = System.nanoTime();
            // actual computational steps
            tick();
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
            if(previousGameResult != null){
                running = false;
            }
        }
        return previousGameResult;
    }

    public void homeGoal(){
        ++homeGoals;
        if(homeGoals > 99) {
            previousGameResult = new GameResult(100, awayGoals);
            awayGoals = 0;
            homeGoals = 0;
        }
        afterGoalReset(TeamEnum.AWAY);
    }

    public void awayGoal(){
        ++awayGoals;
        if(awayGoals > 99) {
            previousGameResult = new GameResult(homeGoals, 100);
            homeGoals = 0;
            awayGoals = 0;
        }
        afterGoalReset(TeamEnum.HOME);
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public int getAwayGoals(){
        return awayGoals;
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

    public GameResult getPreviousGameResult() {
        return previousGameResult;
    }

    /**
     * checks in which half of the pitch the puck is in.
     * @return the TeamEnum of the respective half of the pitch
     * left side: HOME
     * right side: AWAY
     */
    public TeamEnum getPitchHalfWithPuck(){
        int midX = (int)Math.round(width / 2.0);
        if(puck != null){
            if(puck.getPosition().getX() < midX) return TeamEnum.HOME;
            if(puck.getPosition().getX() > midX) return TeamEnum.AWAY;
        }
        return TeamEnum.UNKNOWN;
    }
}
