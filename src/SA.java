import java.util.ArrayList;
import java.util.Random;

enum neighborhood_function{
    swap,
    three_opt,
    move;
}

public class SA implements MetaHeuristic{

    private float alpha;
    private float t_start;
    private Solution solution;
    // TODO: I suggest it just being a float modelling run time or calls
    private float stop_Criteria;
    final Platform platform;

    /**
     *
     * @param alpha
     * @param t_start
     * @param stop_criteria
     * @param solution1 - initial solution
     */

    public SA(float alpha, float t_start, float stop_criteria, Solution solution1, Platform platform) {
        /* Todo: Why are we passing a solution here? Instead of doing that, we should maybe call a method here that
            constructs a solution from scratch! */
        this.alpha = alpha;
        this.t_start = t_start;
        stop_Criteria = stop_criteria;
        this.solution = solution1;
        this.platform = platform;
    }

    public static Solution guess_solution() {
        return new Solution();
    }

    /**
     * TODO: Generates the neighbourhood - method? Random choice? 2-opt
     */
    public void generateNeighbourhood(neighborhood_function neighborhood,Solution current_solution) throws CloneNotSupportedException {
        ArrayList<Core> cores = this.platform.getCores();
        int l;
        Solution new_solution =current_solution.clone();
        switch (neighborhood){
            case swap:
                String core1_id = String.valueOf(new Random().nextInt(cores.size()));
                String core2_id = String.valueOf(new Random().nextInt(cores.size()));
                while (core1_id == core2_id){
                    core2_id = String.valueOf(new Random().nextInt(cores.size()));
                };
                Core core1 = platform.get_core(core1_id);
                Core core2 = platform.get_core(core2_id);
                Task task1 = getRandomTask(core1);
                Task task2 = getRandomTask(core2);

                new_solution.changeCore(task1,core2,core1);
                new_solution.changeCore(task2,core1,core2);

            case three_opt:
                break;
            case move:
                break;
        };
    }
    public Task getRandomTask(Core core) {
        ArrayList<Task> tasks =  core.getTasks();
        int rnd = new Random().nextInt(tasks.size());
        return tasks.get(rnd);
    }


    public Solution getSolution() {
        return solution;
    }

    /**
     * Main algorithm loop
     */
    public void run() {
        /**
         * SUDO CODE
         * s_i = inital solution
         * t = t_start
         * N = neighborhood of s_i
         * while not stopping_criteria
         *      take random s in N
         *      delta = f(s) - f(s_i)
         *      if delta > 0 or p(delta, t)
         *          s_i = s
         *          t = t * alpha
     */
        System.out.println("Not implemented");
    }





}


