package software.ulpgc;

public interface Command {

    Output execute(Input input);

    interface Input {
        String get(String parameter);
    }

    interface Output {
        int responseCode();
        String result();
    }
}
