package enginespawns.airhockey.training;

import engine.objects.PhysicalObject;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.TrainingPlayerStickDisc;

public class AirHockeyTraining extends AirHockey {
    // game
    int numberOfTicksPerTraining = 20000;
    int numberOfTrainings = 1000;
    int currentTick = 0;
    int currentTraining = 0;
    boolean isOver = false;

    public AirHockeyTraining(PlayerStickDisc player_1, PlayerStickDisc player_2){
        this(true, player_1, player_2);
    }

    public AirHockeyTraining(boolean isVisible, PlayerStickDisc player_1, PlayerStickDisc player_2) {
        super(player_1, player_2, isVisible);
    }

    @Override
    public void tick(){
        // call ticks to apply acceleration
        for(PhysicalObject po : objects){
            if(po.hasTick()){
                po.tick();
            }
        }
        physicsManager.moveObjects(this);
        if(puckIsInLeftGoal) {
            awayGoal();
            return;
        }
        if(puckIsInRightGoal){
            homeGoal();
            return;
        }
        if(engineFrame != null){
            engineFrame.repaint();
        }
        ++currentTick;
        //System.out.println("AirHockeyTraining.tick() no. " + currentTick);

        if(currentTick >= numberOfTicksPerTraining) {
            currentTick = 0;
            applyTraining();
            ++currentTraining;
            System.out.println("AirHockeyTraining.applyTraining() no. " + currentTraining);

            if(currentTraining >= numberOfTrainings) running = false;
        }
    }

    private void applyTraining(){
        TrainingPlayerStickDisc player = (TrainingPlayerStickDisc) player_1;
        player.applyTrainingDataFromDattaGeneratorAndDeleteIt();
    }

    public boolean isOver() {
        return isOver;
    }

    public PlayerStickDisc getPlayer_1(){
        return player_1;
    }
}
