import java.util.ArrayList;
import java.util.HashMap;

public class Solution {

    public HashMap<Task,Core> solutionMap = new HashMap<>();

    public float cost;
    public float mcp;


    public void setCost(float cost) {
        this.cost = cost;
    }


    /**
     * Add a task to solution map
     * @param t (task to add)
     */



    public void assignTaskToCore(Task task, Core core){
        core.addTask(task);
        solutionMap.put(task,core);

    }
    public void changeCore(Task task, Core newCore, Core oldCore){
        oldCore.removeTask(task);
        assignTaskToCore(task,newCore);
    }


    protected Solution clone() throws CloneNotSupportedException{
        return (Solution) super.clone();
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
