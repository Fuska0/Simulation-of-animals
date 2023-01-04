package agh.cs.po.Classes;

public class Plants {
    Parameters parameters;
    private final Vector2d position;
    private final int energy;
    public Plants(Vector2d position,Parameters parameters) {
        this.position = position;
        this.parameters = parameters;
        this.energy = parameters.plantEnergy;
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