import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Solution {

    public HashMap<Task,Core> solutionMap = new HashMap<>();

    public float cost;
    public float mcp;

    public Solution() {}

    public Solution(HashMap<Task, Core> solutionMap, float cost) {
        this.cost = cost;
        this.solutionMap = solutionMap;
    }


    public void setCost(float cost) {
        this.cost = cost;
    }




    public void assignTaskToCore(Task task, Core core){
        core.addTask(task);
        solutionMap.put(task,core);

    }
    public void changeCore(Task task, Core newCore, Core oldCore){
        oldCore.removeTask(task);
        assignTaskToCore(task,newCore);
    }


    protected Solution clone() {
        HashMap<Task, Core> mapCopy = (HashMap<Task, Core>) this.solutionMap.clone();
        return new Solution(mapCopy, this.cost);
    }

    public ArrayList<SolutionOutput> getSolutionOutput(){

        ArrayList<SolutionOutput> out = new ArrayList<>();

        for (Task task : solutionMap.keySet()) {
            String task_id = task.getId();
            Core core = solutionMap.get(task);
            String core_id=core.getId();
            String core_MCP = core.getMcpID();
            float wcet = task.getWcet();
            SolutionOutput solutionOut = new SolutionOutput(task_id,core_id,core_MCP,wcet);
            out.add(solutionOut);


        }
        return out;
    }

    public Collection<Core> getCores(){
        return this.solutionMap.values();
    }

}

class SolutionOutput {
    public String Id;
    public String MCP;
    public String Core;
    public float WCET;

    public SolutionOutput(String id, String MCP, String core, float WCET) {
        this.Id = id;
        this.MCP = MCP;
        this.Core = core;
        this.WCET = WCET;
    }
}
