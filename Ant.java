import java.util.HashMap;
import java.util.Stack;

public class Ant {

  private int x;
  private int y;
  private int performedSteps;
  private Direction direction;
  HashMap<State, Character> map = new HashMap<>();
  Stack<PointColor> antLocation = new Stack<PointColor>();

  public Ant(int x, int y, Direction direction) {
    this.x = x;
    this.y = y;
    this.direction = direction;
  }

  // creating a map with: state(color) -> rule
  public void initMap(char[] rules) {
    for (int i = 0; i < rules.length; i++) {
      map.put(State.values()[i], rules[i]);
    }
  }

  public void moveNext(Board board, int steps, char[] rules, Stack<PointColor> location) {
    if (steps < 0) {
      movePrevious(board, steps, location);
    } else {
      for (int i = 0; i < steps; i++) {
        State currentColor = board.getState(x, y);
        State nextColor = board.getNextState(direction, board.getWidth(), board.getHeight(), x, y);

        board.switchState(x, y, currentColor, rules);
        move(board.getWidth(), board.getHeight());
        location.push(new PointColor(x, y, nextColor, direction));
        changeDirection(nextColor);

      }
      makeSteps(steps);
    }
  }

  public void movePrevious(Board board, int steps, Stack<PointColor> location) {

    for (int i = 0; i < Math.abs(steps)+1; i++) {
      if (!location.empty()) {
        // remove the element from the stack
        PointColor temp = location.pop();

        // change the color back to the prev. one
        board.changeColBack(temp.getX(), temp.getY(), temp.getState());

        // move the ant to the prev. location
        this.setX(temp.getX());
        this.setY(temp.getY());
        this.setDirection(temp.getDirection().getLeft());
      }
    }
    makeSteps(steps);
  }

  private void changeDirection(State currentColour) {
    if (map.get(currentColour) == 'L') {
      direction = direction.getLeft();
    } else {
      direction = direction.getRight();
    }
  }

  private void move(int maxX, int maxY) {

    switch (direction) {
      case NORTH:
        decreaseY(maxY);
        break;
      case SOUTH:
        increaseY(maxY);
        break;
      case EAST:
        increaseX(maxX);
        break;
      case WEST:
        decreaseX(maxX);
        break;
    }
  }

  private void makeSteps(int steps) {
    if (steps < 0 && Math.abs(steps) > performedSteps) {
      this.performedSteps = 0;
      System.out.println(this.performedSteps);
    } else {
      this.performedSteps = performedSteps + steps;
      System.out.println(this.performedSteps);
    }
  }

  private void locationStack(State state) {
    antLocation.push(new PointColor(x, y, state, direction));
  }

  public void resize(int newX, int newY) {
    // if new size smaller than old size
    this.setX(newX);
    this.setY(newY);
  }

  public void clear() {
    this.performedSteps = 0;
    this.unant();
  }

  public void unant() {
    this.x = -1;
    this.y = -1;
  }

  private void increaseX(int maxX) {
    x = (x < maxX - 1) ? x + 1 : 0;
  }

  private void decreaseX(int maxX) {
    x = (x > 0) ? x - 1 : maxX - 1;
  }

  private void increaseY(int maxY) {
    y = (y < maxY - 1) ? y + 1 : 0;
  }

  private void decreaseY(int maxY) {
    y = (y > 0) ? y - 1 : maxY - 1;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }
}
