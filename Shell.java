
import java.awt.Point;
import java.util.Scanner;


public class Shell {
  /**
   * This is main and it calls, build, clears etc. the tree.
   *
   * @param args needed in oreder to run this class
   */
  public static void main(String[] args) {

    Board board = null;
    Ant ant = null;
    char[] rules = null;

    boolean quit = false;
    String mode = "ant> ";

    while (!quit) {
      System.out.print(mode);


      Scanner scanner = new Scanner(System.in, "UTF-8");
      String command = scanner.nextLine();
      String[] tokens = command.split(" ");
      tokens[0] = tokens[0].toLowerCase();// commands are not case sensitive

      switch (tokens[0]) {

        case "new": {
          // building the grid with all states WHITE
          if (tokens[3].equals(tokens[3].toUpperCase()) && tokens[3].length() > 1) {
            if (tokens[3].length() <= 12) {
              Board tempBoard =
                  new Board(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), State.WHITE);
              rules = tokens[3].toCharArray();

              board = tempBoard;
            } else {
              System.out.println("Error! Too many rules!");
            }
          } else {
            System.out.println("Error! Minimum 2 UPPERCASE rules required!");
          }

          break;
        }

        case "ant": {
          // setting the position of the ant with direction WEST
          Ant tempAnt =
              new Ant(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Direction.WEST);
          ant = tempAnt;
          ant.initMap(rules);

          break;
        }

        // Entfernt die Ameise. Felder bleiben bestehen.
        case "unant": {
          ant.unant();

          break;
        }

        case "step": {
          int steps;

          if (tokens.length < 2) {
            steps = 1;
            ant.moveNext(board, steps, rules, ant.antLocation);
          } else {
            try {
              steps = Integer.parseInt(tokens[1]);
              ant.moveNext(board, steps, rules, ant.antLocation);
            } catch (Exception e) {
              System.out.println("Error! " + e);
            }
          }

          break;
        }

        case "print": {
          Printer printer = new Printer();
          printer.drawState(board, System.out, ant.getX(), ant.getY(), ant.getDirection());

          break;
        }

        // resettet Schritzähler
        case "clear": {
          board.clear();
          ant.clear();

          break;
        }

        case "resize": {
          int newW = Integer.parseInt(tokens[1]);
          int newH = Integer.parseInt(tokens[2]);

          if (newH == board.getHeight() && newW == board.getWidth()) {
            System.out.println("Err! New size same with old");
          } else {
            Point temp = board.resize(newW, newH, board.getWidth(), board.getHeight(), ant.getX(),
                ant.getY());
            // check if old pos of ant is in the new grid
            if (temp.getX() != -1 && temp.getY() != -1) {
              ant.resize(temp.x, temp.y);
            }
          }

          break;
        }

        case "help": {
          System.out.println("Welcome to help! This is a Langton's Ant program. The available"
              + " commands are: \n-new <x> <y> <c>: x rows, y columns and c rules in type RL; \n-ant "
              + "<i> <j>: these are the x and y coordinates for the ant; \n-unant: eliminates "
              + "the ant, but the states remain; \n-step <n>: the number of steps to perform; the ant "
              + "can also go back; \n-print: shows the performed steps on the console; \n-clear: "
              + "resets everything; \n-resize <x> <y>: makes the grid a new size- bigger or smaller;"
              + " \n-quit: exits the program");

          break;
        }

        case "quit": {
          quit = true;

          break;
        }

        default:
          System.out.println("Error! java.lang.IllegalStateException: No valid command specified");
      }
    }
  }
}
