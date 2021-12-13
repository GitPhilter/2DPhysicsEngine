package engine.objects;

public class PhysicalObjectPair {
    private final PhysicalObject a;
    private final PhysicalObject b;

    public PhysicalObjectPair(PhysicalObject a, PhysicalObject b){
        this.a = a;
        this.b = b;
    }

    public PhysicalObject getA() {
        return a;
    }

    public PhysicalObject getB() {
        return b;
    }
}
