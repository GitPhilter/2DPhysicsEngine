package enginespawns.airhockey.evaluation.shootingrange;

import engine.physics.Direction;
import engine.physics.Position;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.objects.PuckSpawn;

public final class PuckSpawnCreator {

    public static PuckSpawn[] getVerticalLinesToPositionEvenDistributed(AirHockey engine, int numberOfLines,
                                                                        int numberOfSpawns, Position targetPosition,
                                                                        double speed){
        PuckSpawn[] result = new PuckSpawn[numberOfSpawns * numberOfLines];
        double xDist = engine.getWidth() / 2.0 - 2 * engine.getPuck().getRadius();
        double xDistBetweenSpawns = xDist / (numberOfLines + 1);
        //System.out.println("xDistBetweenSpawns: " + xDistBetweenSpawns);
        double startX = engine.getPuck().getRadius() + (int)Math.round(engine.getWidth() / 2.0);
        int resultCounter = 0;
        for(int i = 0; i < numberOfLines; ++i){
            PuckSpawn[] lineSpawns = getVerticalLineToPositionEvenDistributed(engine, (int)Math.round(startX + (i + 1)  * xDistBetweenSpawns),
                    numberOfSpawns, targetPosition, speed);
            for(int r = 0; r < lineSpawns.length; ++r){
                result[resultCounter] = lineSpawns[r];
                ++resultCounter;
            }
        }
        return result;
    }

    public static PuckSpawn[] getVerticalLineToPositionEvenDistributed(AirHockey engine, int x, int numberOfSpawns, Position targetPosition, double speed){
        PuckSpawn[] result = new PuckSpawn[numberOfSpawns];
        double yDist = engine.getHeight() - 2 * engine.getPuck().getRadius();
        double yDistBetweenSpawns = yDist / (numberOfSpawns + 1);
        //System.out.println("yDistBetweenSpawns: " + yDistBetweenSpawns);
        double startY = engine.getPuck().getRadius();
        for(int i = 0; i < result.length; ++i){
            Position spawnPosition = new Position(x, startY + (i + 1) * yDistBetweenSpawns);
            Direction targetDirection = spawnPosition.getDirection(targetPosition);
            result[i] = new PuckSpawn(spawnPosition, targetDirection.getX() * speed, targetDirection.getY() * speed);
            //System.out.println(result[i]);
        }
        return result;
    }

}
