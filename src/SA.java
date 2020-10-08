import java.util.*;
import java.lang.Math;
import java.util.Random;
import java.util.concurrent.TimeUnit;

enum neighborhood_function{
    swap,
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


    public Solution generateNeighbourhood(neighborhood_function neighborhood, Solution currentSolution)  {


        Solution new_solution = currentSolution.clone();
        int maxCoreId = platform.getMaxCoreID();
        switch (neighborhood){
            case swap:
                // get a random core from map
                Core core1 = new_solution.getRandomCoreFromMap(new_solution.getMappedCoreIds());


                // new implementation
                // check if the core has all tasks assigned to it. If yes, then return current solution

                if (new_solution.getCoreTasks(core1).size() == Task.priorities.size()) {
                    return currentSolution;
                }
                // get second core to swap tasks with
                Core core2 = new_solution.getRandomCoreFromMap(core1);

                // choose random tasks from both coresv
                Task task1 = getRandomTask(new_solution, core1);
                Task task2 = getRandomTask(new_solution, core2);

                new_solution.changeCore(task1,core2);
                new_solution.changeCore(task2,core1);

                break;

            case move:

                Core coreFrom = new_solution.getRandomCoreFromMap(new_solution.getMappedCoreIds());

                Task task = getRandomTask(new_solution, coreFrom);
                String core2_id = String.valueOf(new Random().nextInt(maxCoreId));
                while (coreFrom.getId().equals(core2_id)){
                    core2_id = String.valueOf(new Random().nextInt(maxCoreId));
                };


                core2 = platform.getCoreById(core2_id);

                new_solution.changeCore(task,core2);

                break;

        };
        return new_solution;
    }

    public Task getRandomTask(Solution solution, Core core) {
        ArrayList<Task> tasks =  solution.getCoreTasks(core);

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

        float total_score = 0f;
        while (i.hasNext()) {
            c = i.next();

            c.scheduleTasks(s.getCoreTasks(c));

            total_score += c.calculateScore(s.getCoreTasks(c));

            // punish for unfeasible
            if (!c.feasible) {
                total_score = (float) (total_score * 0.5);
            }

        }
        return total_score;
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
    public void run() {
        Solution s_i = getSolution();

        float t = t_start;
        Solution next;

        long t0 = System.currentTimeMillis();
        while ((System.currentTimeMillis() - t0)/1000f < stop_Criteria) {

            if (Math.random() < 0.5) {
                next = generateNeighbourhood(neighborhood_function.swap, s_i);
            } else {
                next = generateNeighbourhood(neighborhood_function.move, s_i);
            }
            float scoreCurrent = f(s_i);
            float scoreNext = f(next);

            System.out.println("Current solution score: " + scoreCurrent);


            float delta = scoreNext - scoreCurrent;

            if (delta >= 0 || p(delta, t)) {
                System.out.println("updating s_i");
                s_i = next;
            }
            t = t*alpha;
        }

        this.solution = s_i;
        this.solution.setLaxity();
    }

    private boolean p(float delta, float t) {
        boolean out = Math.exp(-(Math.abs(delta) / t)) > Math.random();
        return out;
    }
}


