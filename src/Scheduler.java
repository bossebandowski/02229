import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private final MetaHeuristic strategy;
    private final Platform platform;
    private List<Task> tasks;

    static float alpha = 0.99f;
    static float t_start = 100;
    static float runtime_s = 10f;


    public Scheduler(MetaHeuristic strategy, Platform platform1, ArrayList<Task> tasks) {
        this.strategy = strategy;
        this.platform = platform1;
        this.tasks = tasks;
    }

    public void runStrategy() {
        System.out.println("Creating initial solution...");
        this.strategy.initializeSolution(this.platform.getCores(), (ArrayList<Task>) this.tasks);
        System.out.println("Optimising via SA...");
        this.strategy.run();
    }

    public Solution getSolution() {
        return this.strategy.getSolution();
    }

    public static void run(String pathIn, String pathOut) {
        // instantiate Task priorities (class var)
        Task.priorities = new ArrayList<>();

        System.out.println("Reading file...");
        // read file
        IOInterface ioHandler = new IOInterface();
        ioHandler.readFile(pathIn);
        Platform platform = ioHandler.getPlatform();
        ArrayList<Task> tasks = ioHandler.getTasks();

        // instantiate missing classes
        // Todo: could move these SA characteristics into arguments of main
        MetaHeuristic strategy = new SA(alpha, t_start, runtime_s, platform);
        Scheduler scheduler = new Scheduler(strategy, platform, tasks);

        System.out.println("Invoking scheduling strategy with the following parameters:");
        System.out.println("\talpha:\t\t" + alpha);
        System.out.println("\tt_start:\t" + t_start);
        System.out.println("\truntime:\t" + runtime_s + "s");

        // run assignment strategy
        scheduler.runStrategy();

        System.out.println("Writing to output file...");
        // pass solution to io interface and write to file
        ioHandler.writeSolution(scheduler.getSolution(), pathOut);

        System.out.println("Done.");
    }

    public static void parseArgs(String[] args) {
        int i = 0;
        while (i < args.length) {
            /* Check for options */
            if (args[i].equals("--a")) {
                try {
                    i++;
                    alpha = Float.parseFloat(args[i]);
                    System.out.println("alpha set to " + alpha);
                } catch (Exception e) {
                    System.out.println("Invalid alpha assignment. Running with standard value " + alpha);
                }
            } else if (args[i].equals("--t")) {
                try {
                    i++;
                    t_start = Float.parseFloat(args[i]);
                    System.out.println("t_start set to " + t_start);

                } catch (Exception e) {
                    System.out.println("Invalid t_start assignment. Running with standard value " + t_start);
                }
            } else if (args[i].equals("--rt")) {
                try {
                    i++;
                    runtime_s = Float.parseFloat(args[i]);
                    System.out.println("runtime set to " + runtime_s + "s");
                } catch (Exception e) {
                    System.out.println("Invalid runtime assignment. Running with standard value " + runtime_s);
                }
            }

            i++;
        }
    }

    public static void main(String[] args) {
        String pathIn = null;
        String pathOut = null;

        if (args.length < 2) {
            System.err.println("ERR: TOO FEW ARGUMENTS");
            System.exit(1);
        } else {
            // get file path from argument
            pathIn = args[0];
            pathOut = args[1];
            parseArgs(args);
        }

        run(pathIn, pathOut);
    }
}
