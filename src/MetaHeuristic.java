import java.util.ArrayList;

public interface MetaHeuristic {
    void run();
    Solution getSolution();
    Solution initializeSolution(ArrayList<Core> cores, ArrayList<Task> tasks);

}
