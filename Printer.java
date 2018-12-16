// import java.io.InputStream;
import java.io.PrintStream;
// import java.util.Scanner;

public class Printer {
  private static final String ANSI_RESET = "\u001B[0m";
  private static final String COLOR_0 = "\u001B[47m"; // white
  private static final String COLOR_1 = "\u001B[37;40m"; // black
  private static final String COLOR_2 = "\u001B[42m"; // green
  private static final String COLOR_3 = "\u001B[41m"; // red
  private static final String COLOR_4 = "\u001B[37;44m"; // blue
  private static final String COLOR_5 = "\u001B[43m"; // yellow
  private static final String COLOR_6 = "\u001B[46m"; // turcoaz
  private static final String COLOR_7 = "\u001B[45m"; // pink
  private static final String COLOR_8 = "\u001B[36;41m"; // dark red
  private static final String COLOR_9 = "\u001B[31;44m"; // dark blue
  private static final String COLOR_10 = "\u001B[34;43m"; // dark yellow
  private static final String COLOR_11 = "\u001B[32;45m"; // dark pink

  private int previousY = 0;

  private void setPreviousY(int previousY) {
    this.previousY = previousY;
  }

  public void drawState(Board board, PrintStream out, int antX, int antY, Direction direction) {
    StringBuilder sb = new StringBuilder();

    board.forEachState((x, y, state) -> {
      if (previousY != y) {
        sb.append("\n");
      }
//      System.out.println(s);
      if (x == antX && y == antY) {
        switch(direction) {
          case NORTH:
            sb.append(state.getValue() + "^" + ANSI_RESET);
            break;
          case EAST:
            sb.append(state.getValue() + ">" + ANSI_RESET);
            break;
          case SOUTH:
            sb.append(state.getValue() + "v" + ANSI_RESET);
            break;
          case WEST:
            sb.append(state.getValue() + "<" + ANSI_RESET);
            break;
        }
      } else
        switch (state) {
          case WHITE:
            sb.append(COLOR_0 + "0" + ANSI_RESET);
            break;
          case BLACK:
            sb.append(COLOR_1 + "1" + ANSI_RESET);
            break;
          case GREEN:
            sb.append(COLOR_2 + "2" + ANSI_RESET);
            break;
          case RED:
            sb.append(COLOR_3 + "3" + ANSI_RESET);
            break;
          case BLUE:
            sb.append(COLOR_4 + "4" + ANSI_RESET);
            break;
          case YELLOW:
            sb.append(COLOR_5 + "5" + ANSI_RESET);
            break;
          case TURCOAZ:
            sb.append(COLOR_6 + "6" + ANSI_RESET);
            break;
          case PINK:
            sb.append(COLOR_7 + "7" + ANSI_RESET);
            break;
          case darkRED:
            sb.append(COLOR_8 + "8" + ANSI_RESET);
            break;
          case dakrBLUE:
            sb.append(COLOR_9 + "9" + ANSI_RESET);
            break;
          case darkYELLOW:
            sb.append(COLOR_10 + "A" + ANSI_RESET);
            break;
          case darkPINK:
            sb.append(COLOR_11 + "B" + ANSI_RESET);
            break;
        }
      setPreviousY(y);
    });
    out.println(sb.toString());
    setPreviousY(0);
  }

  // public void waitForInput(InputStream in) {
  // try (Scanner scanner = new Scanner(in)) {
  // String s = scanner.nextLine();
  // if (s.trim().toLowerCase().equals("q")) {
  // throw new ProgramInterruptedException();
  // }
  // } catch (ProgramInterruptedException e) {
  // System.exit(0);
  // }
  // }


  // private static class ProgramInterruptedException extends RuntimeException {
  //
  // /**
  // *
  // */
  // private static final long serialVersionUID = 1L;
  //
  // }
}
