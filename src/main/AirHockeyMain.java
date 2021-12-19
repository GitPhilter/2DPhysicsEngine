package main;

import engine.physics.Position;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.evolution.BasicHelpers;
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

        RandomMovementPlayerStickDisc disc_1 = BasicHelpers.getStandardRandomMovementNetworkDisc();
        disc_1.setTeamEnum(TeamEnum.HOME);
        disc_1.setPosition(new Position(100,200));
        RandomMovementPlayerStickDisc disc_2 = BasicHelpers.getStandardRandomMovementNetworkDisc();
        disc_2.setTeamEnum(TeamEnum.AWAY);
        disc_2.setPosition(new Position(700,200));
        AirHockey airHockey = new AirHockey(disc_1, disc_2, true);
        airHockey.playSingleMatch();

    }
}
