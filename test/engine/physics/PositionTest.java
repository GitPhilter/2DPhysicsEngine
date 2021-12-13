package engine.physics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void PositionTest(){
        // basic x and y
        int testX_1 = 10;
        int testY_1 = 11;
        Position testPosition_1 = new Position(testX_1, testY_1);
        PositionGetXAndYTest(testPosition_1, testX_1, testY_1);
        int testX_2 = 12;
        int testY_2 = 11;
        Position testPosition_2 = new Position(testX_2, testY_2);
        PositionGetXAndYTest(testPosition_2, testX_2, testY_2);
        // distance
        double expectedDistance = 2.0;
        Assertions.assertEquals(expectedDistance, testPosition_1.getDistance(testPosition_2));
        Assertions.assertEquals(expectedDistance, testPosition_2.getDistance(testPosition_1));
        // direction
        Direction expectedDirection = new Direction(1,0);
        testPosition_1 = new Position(0,0);
        testPosition_2 = new Position(2,0);
        Direction actualDirection = testPosition_1.getDirection(testPosition_2);
        Assertions.assertEquals(expectedDirection.getX(), actualDirection.getX());
        Assertions.assertEquals(expectedDirection.getY(),actualDirection.getY());
        expectedDirection = new Direction(-1,0);
        actualDirection = testPosition_2.getDirection(testPosition_1);
        Assertions.assertEquals(expectedDirection.getX(), actualDirection.getX());
        Assertions.assertEquals(expectedDirection.getY(),actualDirection.getY());
    }

    @Test
    private void PositionGetXAndYTest(Position position, double expectedX, double expectedY){
        Assertions.assertEquals(expectedX, position.getX());
        Assertions.assertEquals(expectedY, position.getY());
    }
}
