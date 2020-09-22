import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class SA implements MetaHeuristic{

    private float alpha;
    private float t_start;
    private Solution solution;
    // TODO: I suggest it just being a float modelling run time or calls
    private float stop_Criteria;

    /**
     *
     * @param alpha
     * @param t_start
     * @param stop_criteria
     * @param solution1 - initial solution
     */
    public SA(float alpha, float t_start, float stop_criteria) {
        this.alpha = alpha;
        this.t_start = t_start;
        stop_Criteria = stop_criteria;
    }
    public static Solution guess_solution() {
        return new Solution();
    }

    /**
     * TODO: Generates the neighbourhood - method? Random choice? 2-opt
     */
    public void generateNeighbourhood() {
        System.out.println("not implemented");
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
        this.sort(n_tasks, 0, n-1);
        Core curr_core;
        Task curr_task;
        int assigned_core = 0;

        for (Task task : tasks) {
            if (assigned_core == n_cores) {
                assigned_core = 0;
            }
            curr_core = cores.get(assigned_core);
            curr_task = task;
            curr_core.addTask(curr_task);
            assigned_core += 1;
        }
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


