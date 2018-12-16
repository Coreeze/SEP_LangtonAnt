
public enum State {

  WHITE("\u001B[47m"), BLACK("\u001B[37;40m"), GREEN("\u001B[42m"), RED("\u001B[41m"), BLUE(
      "\u001B[37;44m"), YELLOW("\u001B[43m"), TURCOAZ("\u001B[46m"), PINK(
          "\u001B[45m"), darkRED("\u001B[36;41m"), dakrBLUE(
              "\u001B[31;44m"), darkYELLOW("\u001B[34;43m"), darkPINK("\u001B[32;45m");

  private String value;

  State(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public State flip(String color) {
    for (State s : State.values()) {
      if (s.getValue().equals(color))
        return s;
    }
    return null;
  }
}
