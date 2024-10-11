import java.util.Arrays;

public class World {
    public static void main(String[] args) {
        // start
        System.out.println("system wystartował");

        World.run(args);

        // stop
        System.out.println("system zakończył działanie");
    }

    public static void run(String[] args) {
        System.out.println("WSm idzie do przodu!!!");

        int numOfArgs = args.length;

        System.out.println("Lista argumentów:");

        // wyświetl wszystkie argumenty poza ostatnim
        for (var arg : Arrays.copyOfRange(args, 0, numOfArgs - 1)) {
            System.out.print(arg + ", ");
        }

        // wyświetl ostatni argument
        if (numOfArgs > 0) {
            System.out.println(args[numOfArgs - 1]);
        }
    }
}
