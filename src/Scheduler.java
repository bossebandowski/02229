import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    final MetaHeuristic strategy;
    final Platform platform;
    final IOInterface ioHandler;
    private List<Task> tasks;

    public Scheduler(MetaHeuristic strategy, Platform platform1, IOInterface ioHandler, ArrayList<Task> tasks) {
        this.strategy = strategy;
        this.platform = platform1;
        this.ioHandler = ioHandler;
        this.tasks = tasks;


    }

    public static void main(String[] args) {

        Task.priorities = new ArrayList<>();









    }
}
