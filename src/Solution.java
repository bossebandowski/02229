import java.util.ArrayList;
import java.util.Map;

public class Solution {
    public ArrayList<SolutionMap> tasks = new ArrayList<>();
    public float cost;
    public float mcp;


    /**
     * Add a task to solution map
     * @param t (task to add)
     */
    public void addToArray(SolutionMap t) {
        this.tasks.add(t);
    }


}

class SolutionMap {
    public float Id;
    public float MCP;
    public float Core;
    public float WRCT;


    /**
     *
     * @param id task id
     * @param MCP main core platform
     * @param core core id
     * @param WRCT worst case run time
     */
    public SolutionMap(float id, float MCP, float core, float WRCT) {
        this.Id = id;
        this.MCP = MCP;
        this.Core = core;
        this.WRCT = WRCT;
    }
}
