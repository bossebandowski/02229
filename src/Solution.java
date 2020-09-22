import java.util.ArrayList;
import java.util.HashMap;

public class Solution {

    public HashMap<Task,Core> solutionMap = new HashMap<>();

    public float cost;
    public float mcp;


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


    protected Solution clone() throws CloneNotSupportedException{
        return (Solution) super.clone();
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
