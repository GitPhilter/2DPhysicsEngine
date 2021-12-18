package enginespawns.airhockey.evaluation;


import engine.physics.Position;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.RandomMovementPlayerStickDisc;
import enginespawns.airhockey.team.TeamEnum;

import java.awt.*;
import java.util.RandomAccess;

/**
 * this class is used to evaluate the performance of a certain given
 * PlayerDisc vs a RandomMovementPlayerDisc over the course of n games.
 * The final evaluation-value is defined as:
 * eval-value = avg(player_1_goals - player_2_goals)
 * the number of goals are taken when one of the players has reached 100 goals;
 */
public class PlayerDiscEvaluation {

    public static double getEvaluationVsRandom(PlayerStickDisc disc_1, int numberOfGames){
        disc_1.setTeamEnum(TeamEnum.HOME);
        Position disc_1_position = new Position(100, 200);
        disc_1.setPosition(disc_1_position);
        Position disc_2_position = new Position(700, 200);
        RandomMovementPlayerStickDisc disc_2 = new RandomMovementPlayerStickDisc("Player_2", disc_2_position, 20, Color.RED);
        disc_2.setTeamEnum(TeamEnum.AWAY);
        AirHockey airHockey = new AirHockey(disc_1, disc_2, true);
        disc_1.setEngine(airHockey);
        //GameResult[] results = new GameResult[100];
        int sum = 0;
        for(int i = 0; i < numberOfGames; ++i){
            //System.out.println("simulating match no. " + i);
            GameResult currentResult = airHockey.playSingleMatch();
            sum += getEvaluationFromSingleMatch(currentResult, disc_1.getTeamEnum());
        }
        return (double)sum/(double)numberOfGames;
    }

    public static int getEvaluationFromSingleMatch(GameResult gameResult, TeamEnum consideredPlayerTeamEnum){
        if(consideredPlayerTeamEnum == TeamEnum.HOME) return gameResult.getHomeGoals() - gameResult.getAwayGoals();
        return gameResult.getAwayGoals() - gameResult.getHomeGoals();
    }
}
