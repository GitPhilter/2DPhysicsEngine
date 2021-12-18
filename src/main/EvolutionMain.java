package main;

import engine.physics.Position;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.evolution.Evolution;
import enginespawns.airhockey.objects.playerstickdiscimplementations.NeuralNetworkPlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.RandomMovementPlayerStickDisc;
import enginespawns.airhockey.team.TeamEnum;

import java.awt.*;

public class EvolutionMain {

    public static void main(String[] args){
        System.out.println("EvolutionMain.");
        //NeuralNetworkPlayerStickDisc bestDisc = Evolution.getBestNeuralNetworkDiscAfterNGenerations(2000, 60, 1, 0.1);
        //Evolution.getRandomChild(bestDisc.getNeuralNetwork());
        NeuralNetworkPlayerStickDisc bestDisc = Evolution.getDiscWithEvalGreaterThanByChance(50);
        bestDisc.setTeamEnum(TeamEnum.HOME);
        bestDisc.setPosition(new Position(100, 200));
        RandomMovementPlayerStickDisc disc_2 = new RandomMovementPlayerStickDisc("RandomDisc", new Position(700,200), 20, Color.RED);
        disc_2.setTeamEnum(TeamEnum.AWAY);
        AirHockey airHockey = new AirHockey(bestDisc, disc_2, true);
        bestDisc.setEngine(airHockey);
        airHockey.run();

    }
}
