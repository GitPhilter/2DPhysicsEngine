package main;

import engine.physics.Position;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.NeuralNetworkPlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.RandomMovementPlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.TrainingPlayerStickDisc;
import enginespawns.airhockey.team.TeamEnum;
import enginespawns.airhockey.training.AirHockeyTraining;
import logger.Logger;
import neuralnetwork.NeuralNetwork;

import java.awt.*;

public class AirHockeyMain {

    public static void main(String[] args){
        Logger.print("AirHockey.");

        // players
        Position player1Position = new Position(800 * (1.0/8.0), 400 / 2.0);
        TrainingPlayerStickDisc player_1 = new TrainingPlayerStickDisc("Player_1", player1Position, 20, Color.BLUE, null);
        player_1.setTeamEnum(TeamEnum.HOME);

        Position player2Position = new Position(800 * (7.0/8.0), 400 / 2.0);
        RandomMovementPlayerStickDisc player_2 = new RandomMovementPlayerStickDisc("Player_2", player2Position, 20, Color.RED);
        player_2.setTeamEnum(TeamEnum.AWAY);
        AirHockeyTraining airHockey = new AirHockeyTraining(false, player_1, player_2);
        player_1.setEngine(airHockey);
        airHockey.run();


        TrainingPlayerStickDisc trainingDisc = (TrainingPlayerStickDisc)airHockey.getPlayer_1();
        NeuralNetwork neuralNetwork = trainingDisc.getNeuralNetwork();
        NeuralNetworkPlayerStickDisc disc_1 = new NeuralNetworkPlayerStickDisc("NeuralNetworkDisc",
                player1Position, 20, Color.BLUE, null,
                neuralNetwork);
        disc_1.setTeamEnum(TeamEnum.HOME);
        AirHockey testGame = new AirHockey(disc_1, player_2, true);
        disc_1.setEngine(testGame);
        testGame.run();


    }
}
