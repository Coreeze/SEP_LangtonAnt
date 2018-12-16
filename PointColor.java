
// class for saving the coordinates and the color of the locations where the ant was
public class PointColor {
  int x;
  int y;
  State state;
  Direction direction;

  public PointColor(int x, int y, State state, Direction direction) {
    this.x = x;
    this.y = y;
    this.state = state;
    this.direction = direction;
  }
  
  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public State getState() {
    return this.state;
  }
  
  public Direction getDirection() {
    return this.direction;
  }
}
