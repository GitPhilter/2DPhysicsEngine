package engine.physics;

public class Direction {
    double x;
    double y;

    public Direction(double x, double y){
        this.x = x;
        this.y = y;
        normalize();
    }

    public Direction(Direction direction){
        this.x = direction.getX();
        this.y = direction.getY();
        normalize();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x){
        this.x = x;
        normalize();
    }

    public void setY(double y){
        this.y = y;
        normalize();
    }

    public void addDirection(Direction direction){
        this.x += direction.getX();
        this.y += direction.getY();
        normalize();
    }

    private void normalize(){
        double magnitude = Math.sqrt(x*x + y*y);
        if(magnitude == 0) return;
        x = x/magnitude;
        y = y/magnitude;
        //System.out.println("normalize: x=" + x + ", y=" + y);
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
