import java.util.ArrayList;
import java.util.Iterator;

public class Platform {
    final ArrayList<Core> cores;

    public Platform(ArrayList<Core> Cores)
    {
        cores = Cores;
    }


    public ArrayList<Core> getCores() {
        return cores;
    }

    public Core get_core(String coreId){
        for (Core core : cores){
            if (core.getId() == coreId){
                return core;

    public void AssignTasks(String coreID, ArrayList<Task> tasks)
    {
        Iterator<Core> coreIterator = cores.iterator();
        while(coreIterator.hasNext())
        {
            Core currentCore = coreIterator.next();
            if(currentCore.getID().equals(coreID))
            {
                currentCore.addTask(tasks);

            }
        }
    }
}