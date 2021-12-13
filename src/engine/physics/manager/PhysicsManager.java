package engine.physics.manager;

import engine.objects.PhysicalObjectPair;

import java.util.ArrayList;

public abstract class PhysicsManager implements PhysicsManagerInterface{
    protected final String name;
    protected ArrayList<PhysicalObjectPair> currentCollisionPairs;

    public PhysicsManager(String name){
        this.name = name;
        this.currentCollisionPairs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<PhysicalObjectPair> getCurrentCollisionPairs() {
        return currentCollisionPairs;
    }
}
