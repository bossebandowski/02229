import java.util.ArrayList;
import java.util.Map;

public class Solution {
    public ArrayList<SolutionMap> tasks = new ArrayList<>();
    public float cost;
    public float mcp;


    public void setCost(float cost) {
        this.cost = cost;
    }


    /**
     * Add a task to solution map
     * @param t (task to add)
     */
    public void addToArray(SolutionMap t) {
        this.tasks.add(t);
    }


}

class SolutionMap {
    public int Id;
    public int MCP;
    public int Core;
    public float WCET;

    public SolutionMap(int id, int MCP, int core, float WCET) {
        this.Id = id;
        this.MCP = MCP;
        this.Core = core;
        this.WCET = WCET;
    }
}
