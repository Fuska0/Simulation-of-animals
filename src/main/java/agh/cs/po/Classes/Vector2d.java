package agh.cs.po.Classes;



import java.util.Objects;

public class Vector2d {

    Parameters parameters = new Parameters();
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public final int x;
    public final int y;

    @Override
    public String toString() {
        return  "(" + x + "," + y +")";
    }

    public boolean precedes(Vector2d other){
        return x <= other.x && y <= other.y;
    }
    public boolean follows(Vector2d other){
        return x >= other.x && y >= other.y;
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(x + other.x, y + other.y);
    }

    public Vector2d addWithModulo(Vector2d other){
        int newX =(x + other.x)%( parameters.mapWidth +1);
        int newY =(y + other.y)%(parameters.mapHeight +1);
        if (newX < 0) {newX = parameters.mapWidth;}
        if (newY < 0) {newY = parameters.mapHeight;}
        return new Vector2d(newX,newY);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(x - other.x, y - other.y);
    }

    public Vector2d upperRight(Vector2d other){
        int max1, max2;
        max1 = Math.max(x, other.x);
        max2 = Math.max(y, other.y);
        return new Vector2d(max1,max2);
    }
    public Vector2d lowerLeft(Vector2d other){
        int min1, min2;
        min1= Math.min(x,other.x);
        min2 = Math.min(y,other.y);
        return new Vector2d(min1,min2);
    }

    public Vector2d opposite(){
        return new Vector2d(-1*x,-1*y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }



}
