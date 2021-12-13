package engine.physics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AngleCalculatorTest {

    @Test
    public void getAngleFromDirectionTest(){
        Direction referenceDirection = new Direction(1, 0);
        Direction direction = new Direction(1, 1);
        double expectedAngle = 315;
        Assertions.assertEquals(expectedAngle, AngleCalculator.getAngleFromDirection(direction));
        Assertions.assertEquals(expectedAngle, AngleCalculator.getAngleFromDirection(referenceDirection, direction));
        direction = new Direction(0, -1);
        expectedAngle = 90;
        Assertions.assertEquals(expectedAngle, AngleCalculator.getAngleFromDirection(direction));
        Assertions.assertEquals(expectedAngle, AngleCalculator.getAngleFromDirection(referenceDirection, direction));
        direction = new Direction(-1, 0);
        expectedAngle = 180;
        Assertions.assertEquals(expectedAngle, AngleCalculator.getAngleFromDirection(direction));
        Assertions.assertEquals(expectedAngle, AngleCalculator.getAngleFromDirection(referenceDirection, direction));
    }

    @Test
    public void getHalfCircleAngleBetweenDirectionsTest(){
        Direction direction_1 = new Direction(0, -1);
        Direction direction_2 = new Direction(1, 1);
        double expectedAngle = 135;
        Assertions.assertEquals(expectedAngle, AngleCalculator.getHalfCircleAngleBetweenDirections(direction_1, direction_2));
    }

    @Test
    public void getDirectionScalarProductTest(){
        // trivial
    }

    @Test
    public void getDirectionAbsoluteTest(){
        Direction direction = new Direction(0, 1);
        double expectedResult = 1;
        Assertions.assertEquals(expectedResult, AngleCalculator.getDirectionAbsolute(direction));
    }
}
