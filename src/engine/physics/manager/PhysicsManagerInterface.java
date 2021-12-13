package engine.physics.manager;

import engine.PhysicsEngine2D;
import engine.objects.PhysicalObject;

public interface PhysicsManagerInterface {

    public void moveObjects(PhysicsEngine2D engine);

    public void moveObject(PhysicalObject object, PhysicsEngine2D engine);
}
