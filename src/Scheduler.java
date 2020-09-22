import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private final MetaHeuristic strategy;
    private final Platform platform;
    private List<Task> tasks;

    public Scheduler(MetaHeuristic strategy, Platform platform1, ArrayList<Task> tasks) {
        this.strategy = strategy;
        this.platform = platform1;
        this.tasks = tasks;
    }

    public void runStrategy() {
        this.strategy.initializeSolution(this.platform.getCores(), (ArrayList<Task>) this.tasks);
        this.strategy.run();
    }

    public Solution getSolution() {
        return this.strategy.getSolution();
    }

    public static void run(String pathIn, String pathOut) {
        // instantiate Task priorities (class var)
        Task.priorities = new ArrayList<>();

        // read file
        IOInterface ioHandler = new IOInterface();
        ioHandler.readFile(pathIn);
        Platform platform = ioHandler.getPlatform();
        ArrayList<Task> tasks = ioHandler.getTasks();

        // instantiate missing classes
        // Todo: could move these SA characteristics into arguments of main
        MetaHeuristic strategy = new SA(0.99f, 100, 60f, platform);
        Scheduler scheduler = new Scheduler(strategy, platform, tasks);

        // run assignment strategy
        scheduler.runStrategy();

        // pass solution to io interface and write to file
        ioHandler.writeSolution(scheduler.getSolution(), pathOut);
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
        }

        run(pathIn, pathOut);
    }
}
