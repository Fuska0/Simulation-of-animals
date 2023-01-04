package agh.cs.po.Classes;

public class Plants {
    Parameters parameters = new Parameters();
    private final Vector2d position;
    private final int energy= parameters.plantEnergy;
    public Plants(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    public String toString(){
        return "*";
    }

    public String getImagePath(){
        return "glon";
    }
}