package software.ulpgc;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DivisorsCommand implements Command {

    @Override
    public Output execute(Input input) {
        try {
            int number = Integer.parseInt(input.get(":number"));
            return isOutOfBounds(number) ? isOutOfBoundsOut() : outPutOf(number);
        } catch (NumberFormatException e) {
            return nanOutPut();
        }
    }

    private Output nanOutPut() {
        return new Output() {
            @Override
            public int responseCode() {
                return 405;
            }

            @Override
            public String result() {
                return "Not a number";
            }
        }
    }

    private Output outPutOf(int number) {
        return new Output() {
            @Override
            public int responseCode() {
                return 200;
            }

            @Override
            public String result() {
                return divisorsOf(number);
            }
        }
    }

    private String divisorsOf(int number) {
        return IntStream.rangeClosed(1, Math.abs(number)).filter(i->number%==0).mapToObj(String::valueOf).collect(Collectors.joining(","));
    }

    private Output isOutOfBoundsOut() {
        return new Output() {
            @Override
            public int responseCode() {
                return 404;
            }

            @Override
            public String result() {
                return "Number out of bounds";
            }
        }
    }

    private boolean isOutOfBounds(int number) {
        return number < 0 || number > 50;
    }
}
