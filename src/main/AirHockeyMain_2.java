package main;

import engine.physics.Position;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.evaluation.PlayerDiscEvaluation;
import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.RandomMovementPlayerStickDisc;
import enginespawns.airhockey.team.TeamEnum;

import java.awt.*;

public class AirHockeyMain_2 {

    public static void main(String[] args){
        Position player1Position = new Position(100, 200);
        RandomMovementPlayerStickDisc disc_1 = new RandomMovementPlayerStickDisc("disc_1", player1Position, 20, Color.BLUE);
        int numberOfGames = 10000;
        double evalValue = PlayerDiscEvaluation.getEvaluationVsRandom(disc_1, numberOfGames);
        System.out.println("evalValue after " + numberOfGames + " games = " + evalValue);
    }
}
