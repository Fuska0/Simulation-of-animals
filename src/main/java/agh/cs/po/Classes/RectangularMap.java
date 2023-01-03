package agh.cs.po.Classes;

public class RectangularMap extends AbstractWorldMap{

    Parameters parameters = new Parameters();
    public RectangularMap() {
        this.width = parameters.mapWidth;
        this.height = parameters.mapHeight;
    }


    private final int width;
    private final int height;

}
