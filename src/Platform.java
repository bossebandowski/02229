import java.util.ArrayList;
import java.util.Iterator;

public class Platform {
    final ArrayList<Core> cores;

    public Platform(ArrayList<Core> Cores)
    {
        cores = Cores;
    }

    public Core getCoreById(String coreId) {

        for (Core core : cores) {
            if (core.getUid().equals(coreId)) {
                return core;
            }
        }

        System.out.println("Core = null");
        return null;
    }

    public void AssignTasks(String coreID, ArrayList<Task> tasks)
    {
        Iterator<Core> coreIterator = cores.iterator();
        while(coreIterator.hasNext())
        {
            Core currentCore = coreIterator.next();
            if(currentCore.getId().equals(coreID))
            {
                currentCore.addTask(tasks);

            }
        }
    }

    /**
     * Return by an ArrayList of the cores
     * @return
     */
    public ArrayList<Core> getCores()
    {
        return cores;
    }

    /**
     * Get the core with the defined id
     * Throw Runtime exception if no Core found with the desired id
     */
    public Core get_core(String core1_id)
    {
        Iterator<Core> coreIterator = cores.iterator();
        while(coreIterator.hasNext())
        {
            Core currentCore = coreIterator.next();
            if(currentCore.getId().equals(core1_id))
            {
                return currentCore;
            }
        }
        throw new RuntimeException("Required Core not found");
    }
}