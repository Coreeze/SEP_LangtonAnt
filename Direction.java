
public enum Direction {
  NORTH, SOUTH, WEST, EAST;

  private Direction left;
  private Direction right;

  static {
    NORTH.left = WEST;
    NORTH.right = EAST;
    SOUTH.left = EAST;
    SOUTH.right = WEST;
    WEST.left = SOUTH;
    WEST.right = NORTH;
    EAST.left = NORTH;
    EAST.right = SOUTH;
  }

  public Direction getLeft() {
    return left;
  }

  public Direction getRight() {
    return right;
  }
}
