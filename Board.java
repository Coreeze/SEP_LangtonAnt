
import static java.util.stream.IntStream.range;
import java.awt.Point;

public class Board {

  private State[][] tiles;
  private int height;
  private int width;
  private static String[] color = new String[] {"\u001B[47m", "\u001B[37;40m", "\u001B[42m",
      "\u001B[41m", "\u001B[37;44m", "\u001B[43m", "\u001B[46m", "\u001B[45m", "\u001B[36;41m",
      "\u001B[31;44m", "\u001B[34;43m", "\u001B[32;45m"};


  public Board(int height, int width, State initialState) {
    this.height = height;
    this.width = width;
    this.tiles = new State[width][height];
    range(0, width).forEach(x -> range(0, height).forEach(y -> setState(x, y, initialState)));
  }

  public void forEachState(StateConsumer consumer) {
    range(0, height)
        .forEach(y -> range(0, width).forEach(x -> consumer.consume(x, y, getState(x, y))));
  }

  private void setState(int x, int y, State state) {
    synchronized (tiles) {
      tiles[x][y] = state;
    }
  }

  void switchState(int x, int y, State currentColour, char[] rules) {
    synchronized (tiles) {
      for (int i = 0; i < rules.length; i++) {
        if (color[i].equals(currentColour.getValue())) {
          if (i == rules.length - 1) {
            tiles[x][y] = tiles[x][y].flip(color[0]);
          } else {
            tiles[x][y] = tiles[x][y].flip(color[i + 1]);
          }
        }
      }
    }
  }

  public void clear() {
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        changeColBack(i, j, State.WHITE);
      }
    }

  }

  void changeColBack(int x, int y, State state) {
    tiles[x][y] = state;
  }

  public Point resize(int newWidth, int newHeight, int oldWidth, int oldHeight, int antX,
      int antY) {
    State[][] tempTiles = this.tiles;
    Point ant = new Point(-1, -1);

    // if new size is smaller than old size
    if (newWidth < oldWidth && newHeight < oldHeight) {

      for (int k = 0; k < newWidth; k++) {
        for (int l = 0; l < newHeight; l++) {
          // calculate the new pos of the ant
          if ((double) antX == Math.floor((oldWidth - newWidth) / 2) + k
              && (double) antY == Math.floor((oldHeight - newHeight) / 2) + l) {

            ant.setLocation(k, l);
          }

          // migrating the States to the new Grid
          tiles[k][l] = tempTiles[(int) Math.floor((oldWidth - newWidth) / 2.0)
              + k][(int) Math.floor((oldHeight - newHeight) / 2.0) + l];
        }
      }
    } else {
      tiles = new State[newWidth][newHeight];
      // initialize new board with the WHITE state
      range(0, newWidth)
          .forEach(x -> range(0, newHeight).forEach(y -> setState(x, y, State.WHITE)));

      for (int k = 0; k < newWidth; k++) {
        for (int l = 0; l < newHeight; l++) {
          // migrate the states to the new grid
          if (k < oldWidth && l < oldHeight) {
            setState((int) Math.floor((newWidth - oldWidth) / 2) + k,
                (int) Math.floor((newHeight - oldHeight) / 2) + l, tempTiles[k][l]);
          }
          // calculate the new pos of the ant
          if ((double) antX + Math.floor((newWidth - oldWidth) / 2) == k
              && (double) antY + Math.floor((newHeight - oldHeight) / 2) == l) {
            ant.setLocation(k, l);
          }
        }
      }
    }
    setHeight(newHeight);
    setWidth(newWidth);

    return ant;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  State getState(int x, int y) {
    synchronized (tiles) {
      return tiles[x][y];
    }
  }

  State getNextState(Direction direction, int maxX, int maxY, int x, int y) {
    switch (direction) {
      case NORTH: {
        if (y > 0) {
          y--;
        } else {
          y = maxY - 1;
        }
        return tiles[x][y];
      }

      case SOUTH:
        if (y < maxY - 1) {
          y++;
        } else {
          y = 0;
        }    
        return tiles[x][y];

      case EAST:
        if (x < maxX - 1) {
          x++;
        } else {
          x = 0;
        }
        return tiles[x][y];

      case WEST:
        if (x > 0) {
          x--;
        } else {
          x = maxX - 1;
        }
        return tiles[x][y];
    }
    return null;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  @FunctionalInterface
  public interface StateConsumer {
    void consume(int x, int y, State state);
  }
}
