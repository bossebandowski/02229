import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

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
     */
    public SA(float alpha, float t_start, float stop_criteria, Platform platform1) {
        this.alpha = alpha;
        this.t_start = t_start;
        stop_Criteria = stop_criteria;
        this.platform = platform1;
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




    public int partition(ArrayList<Task> arr, int low, int high)
    {
        float pivot = arr.get(high).getPriority();
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than the pivot
            if (arr.get(j).getPriority() < pivot)
            {
                i++;

                // swap arr[i] and arr[j]
                Task temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        Task temp = arr.get(i + 1);
        arr.set(i + 1, arr.get(high));
        arr.set(high, temp);

        return i+1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    public void sort(ArrayList<Task> arr, int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi-1);
            sort(arr, pi+1, high);
        }
    }
    public Solution initializeSolution(ArrayList<Core> cores, ArrayList<Task> tasks) {
        System.out.println(tasks);
        int n_tasks = tasks.size();
        int n_cores = cores.size();
        this.sort(tasks, 0, n_tasks-1);
        Core curr_core;
        Task curr_task;
        Solution sol = new Solution();
        int assigned_core = 0;

        for (Task task : tasks) {
            if (assigned_core == n_cores) {
                assigned_core = 0;
            }
            curr_core = cores.get(assigned_core);
            curr_task = task;
            sol.assignTaskToCore(curr_task, curr_core);
            assigned_core += 1;
        }
        return sol;
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


