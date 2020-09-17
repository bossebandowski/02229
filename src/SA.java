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
    public SA(float alpha, float t_start, float stop_criteria, Solution solution1) {
        this.alpha = alpha;
        this.t_start = t_start;
        stop_Criteria = stop_criteria;
        this.solution = solution1;
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


