import java.util.*;

public class Solution {

    public HashMap<Task,Core> solutionMap = new HashMap<>();

    public float cost;
    public float mcp;
    public float avgLaxity;

    public Solution() {}

    public Solution(HashMap<Task, Core> solutionMap, float cost) {
        this.cost = cost;
        this.solutionMap = solutionMap;
    }

    public ArrayList<Task> getCoreTasks(Core core){

        ArrayList<Task> tasks = new ArrayList<>();
        for (Map.Entry<Task, Core> entry : this.solutionMap.entrySet()) {
            String entryId = entry.getValue().getId();
            String coreId = core.getId();
            if (entry.getValue().getId().equals(core.getId())) {
                tasks.add(entry.getKey());
            }
        }

        return tasks;
    }


    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setLaxity(){
        int num_tasks = solutionMap.size();
        float totalLaxity = 0;
        Collection<Core> cores = getCores();
        Iterator<Core> i = cores.iterator();
        Core c;

        while (i.hasNext()) {
            c = i.next();
            totalLaxity += c.calculateScore(getCoreTasks(c));
        }

        avgLaxity = totalLaxity/num_tasks;
    }

    public float getAvgLaxity() {
        return avgLaxity;
    }

    public void assignTaskToCore(Task task, Core core){



        solutionMap.put(task,core);



    }
    public void changeCore(Task task, Core newCore){

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

    public Set<String> getMappedCoreIds(){
        Set<String> setIds = new HashSet<>();
        for (Core core : this.solutionMap.values() ){
            setIds.add(core.getId());
        }

        return setIds ;
    }

    public Core getRandomCoreFromMap(Set<String> CoreIds){
        int numOfIds = CoreIds.size();
        if (numOfIds == 1) {
            Object[] values = solutionMap.values().toArray();
            return (Core) values[0];
        }
        int rnd_index = new Random().nextInt(numOfIds);
        String[] array = CoreIds.toArray( new String[numOfIds] );

        Core core = getCoreById(array[rnd_index]);

        return core;
    }

    //method invoked when looking for second core
    public Core getRandomCoreFromMap(Core exceptCore){

        Set<String> setIds = getMappedCoreIds();

        setIds.remove(exceptCore.getId());


        Core core =  getRandomCoreFromMap(setIds);
        return core;

    }


    public Core getCoreById(String coreId) {

        Collection<Core> cores = getCores();
        Iterator<Core> i = cores.iterator();
        Core c;

        while (i.hasNext()) {
            c = i.next();


            if (c.getId().equals(coreId)){
                return c;
            }
        }
        return null;
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
