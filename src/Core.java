import java.util.ArrayList;

import java.util.Iterator;
import java.util.Map;

enum ScheduleMethod
{
    EDF, //Earliest Deadline first
    EM //Rate monotonic
    // TODO: Add further methods
}

public class Core {
    final String id;
    final String mcpID;
    final String uid;
    ArrayList<Task> tasks;

    Map<Task, ArrayList<Integer>> TaskPeriodsStart; // Task -> Starting moment of periods
    Map<Task,ArrayList<Map<Integer,Integer>>> schedule; // Task -> List of {Startmoment, Endmoment}
    final float wcetFactor;

    public Core(String ID, String MCPID,float WCETFACTOR)
    {
        id = ID;
        mcpID = MCPID;
        uid = mcpID + id;
        wcetFactor = WCETFACTOR;
        Iterator<Task> tasksIterator = tasks.iterator();
        while(tasksIterator.hasNext())
        {

        }
    }
    public void addTask(Task newTask)
    {
        tasks.add(newTask);
    }
    public void addTask(ArrayList<Task> newTasks)
    {
        tasks.addAll(newTasks);
    }
    public void scheduleTasks(ScheduleMethod requiredMethod)
    {
        switch (requiredMethod)
        {
            case EDF:
                Iterator<Task> tasksIterator = tasks.iterator();
                Integer clockCounter = 0;
                Integer maxClock = getHighestPeriod();
                while(clockCounter < maxClock)
                {

                }
                break;
        }
    }
    public ArrayList<Task> getTasks()
    {
        return tasks;
    }
   /*public Map<Task,ArrayList<Map<Integer,Integer>>> getSchedule ()
    {

    }*/
    public boolean checkFeasibility()
    {
        boolean result = false;

        return result;
    }
    private Integer getHighestPeriod()
    {
        Integer result = 0;
        Iterator<Task> tasksIterator = tasks.iterator();
        while(tasksIterator.hasNext())
        {
            Integer period = 0;//tasksIterator.next().getPeriod();
            if(period > result)
            {
                result = period;
            }
        }
        return result;
    }
}
