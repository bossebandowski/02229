import java.util.*;
import java.lang.Math;
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
    private float stop_Criteria;
    final Platform platform;


    /**
     *  @param alpha
     * @param t_start
     * @param stop_criteria
     */

    public SA(float alpha, float t_start, float stop_criteria, Platform platform) {

        this.alpha = alpha;
        this.t_start = t_start;
        this.stop_Criteria = stop_criteria;
        this.platform = platform;

    }


    public Solution generateNeighbourhood(neighborhood_function neighborhood)  {
        ArrayList<Core> cores = this.platform.getCores();
        Solution current_solution = getSolution();
        Solution new_solution = current_solution.clone();
        switch (neighborhood){
            case swap:
                String core1_id = String.valueOf(new Random().nextInt(cores.size()));
                String core2_id = String.valueOf(new Random().nextInt(cores.size()));

                while (core1_id.equals(core2_id)){
                    core2_id = String.valueOf(new Random().nextInt(cores.size()));
                }

                Core core1 = platform.getCoreById(core1_id);
                Core core2 = platform.getCoreById(core2_id);

                Task task1 = getRandomTask(core1);
                Task task2 = getRandomTask(core2);

                new_solution.changeCore(task1,core2,core1);
                new_solution.changeCore(task2,core1,core2);

            case three_opt:
                break;
            case move:
                core1_id = String.valueOf(new Random().nextInt(cores.size()));
                core1 = platform.getCoreById(core1_id);
                Task task = getRandomTask(core1);
                core2_id = String.valueOf(new Random().nextInt(cores.size()));
                while (core1_id.equals(core2_id)){
                    core2_id = String.valueOf(new Random().nextInt(cores.size()));
                };
                core2 = platform.getCoreById(core2_id);
                new_solution.changeCore(task,core2,core1);



        };
        return new_solution;
    }
    public Task getRandomTask(Core core) {
        ArrayList<Task> tasks =  core.getTasks();
        int rnd = new Random().nextInt(tasks.size());
        return tasks.get(rnd);
    }




    public Solution getSolution() {
        return solution;
    }

    public float f(Solution s) {
        Collection<Core> cores = s.getCores();
        Iterator<Core> i = cores.iterator();
        Core c;

        float total_cost = 0f;
        while (i.hasNext()) {
            c = i.next();
            c.scheduleTasks();
            total_cost += c.calculateCostFunction();

        }
        return total_cost;
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

    @Override
    public Solution initializeSolution(ArrayList<Core> cores, ArrayList<Task> tasks) {
        //System.out.println(tasks);
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
        this.solution = sol;
        return sol;
    }



    @Override
    public void run()  {
        Solution s_i = getSolution();
        float t = t_start;
        Solution next = null;
        System.out.println("Running for " + stop_Criteria + " seconds...");
        long t0 = System.currentTimeMillis();
        while ((System.currentTimeMillis() - t0)/1000f < stop_Criteria) {
            next = generateNeighbourhood(neighborhood_function.swap);
            float delta = f(s_i) - f(next);
            if (delta > 0 || p(delta, t)) {
                s_i = next;
                t = t*alpha;
            }
        }
    }

    private boolean p(float delta, float t) {
        double random = Math.random();
        return Math.exp(delta / t) > random;
    }
}


