public class SA implements MetaHeuristic{

    private float alpha;
    private float t_start;
    private Solution solution;
    // TODO: I suggest it just being a float modelling run time or calls
    private float stop_Criteria;

    public SA(float alpha, float t_start, float stop_criteria, Solution solution1) {
        this.alpha = alpha;
        this.t_start = t_start;
        stop_Criteria = stop_criteria;
        this.solution = solution1;
    }

    /**
     * TODO: Generates the neighbourhood - method? Random choice? 2-opt
     */
    public void generateNeighbourhood() {
        System.out.println("not implemented");
    }
}


