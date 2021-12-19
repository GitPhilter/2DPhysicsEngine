package main;

import engine.physics.Position;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.evaluation.EvaluationInfo;
import enginespawns.airhockey.evaluation.shootingrange.ShootingRange;
import enginespawns.airhockey.evolution.Evolution;
import enginespawns.airhockey.evolution.Evolution_1;
import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.NeuralNetworkPlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.RandomMovementPlayerStickDisc;
import enginespawns.airhockey.team.TeamEnum;

import java.awt.*;

public class ShootingRangeMain {

    public static void main(String[] args){
        double start = System.nanoTime();
        System.out.println("ShootingRangeMain.");
        PlayerStickDisc bestDisc = Evolution_1.evolutionAlgorithm_1();
        double finish = System.nanoTime();
        double total = finish - start;
        double totalInSeconds = (int)((total / 1000000000.0) * 100) / 100.0;
        System.out.println("ShootingRangeMain - 'finding the best Disc' terminated in " + totalInSeconds + " seconds.");
        // show the best disc
        Position player2_position = new Position(700,200);
        RandomMovementPlayerStickDisc player2 = new RandomMovementPlayerStickDisc("RandomDisc", player2_position, 20, Color.RED);
        bestDisc.setTeamEnum(TeamEnum.HOME);
        player2.setTeamEnum(TeamEnum.AWAY);
        AirHockey airHockey = new AirHockey(bestDisc, player2, true);
        bestDisc.setEngine(airHockey);
        airHockey.run();
    }


}
