import java.sql.Array;
import java.util.*;

public class Core {
    static int count = 0;
    final String id;
    final String mcpID;
    final String uid;
    boolean feasible;
    ArrayList<Task> tasks = new ArrayList<Task>();
    final float wcetFactor;
    ArrayList<Task> unfeasibleTasks = new ArrayList<Task>();

    public Core(String _id, String _mcpID, float _wcetFactor) {
        id = _id;
        mcpID = _mcpID;
        uid = String.valueOf(count);
        count++;
        wcetFactor = _wcetFactor;
        feasible = true;
    }

    /**
     * Add a new task to the Core, check if a task with the same ID has already added to the core
     * @param newTask
     */
    public void addTask(Task newTask) {
        Iterator<Task> taskIterator = tasks.iterator();
        while (taskIterator.hasNext()) {
            Task currentTask = taskIterator.next();
            if (currentTask.getId().equals(newTask.getId())) {
                throw new RuntimeException("The task with this ID has been already assigned to this core. Please call " +
                        "clearCore function before!");
            }
        }
        tasks.add(newTask);
    }

    /**
     * Remove a task from the Core
     * @param taskToRemove
     */
    public void removeTask(Task taskToRemove) {
        if (tasks.remove(taskToRemove) == false) {
            throw new RuntimeException("The required task is not contained by the core");
        }
    }

    /**
     * Add an ArrayList of Tasks to the Core (check if a task with the same ID is already assigned to core
     * @param newTasks
     */
    public void addTask(ArrayList<Task> newTasks) {
        Iterator<Task> taskIterator = tasks.iterator();
        Iterator<Task> newTaskIterator = newTasks.iterator();
        while (taskIterator.hasNext()) {
            Task currentTask = taskIterator.next();
            while (newTaskIterator.hasNext()) {
                Task currentNewTask = newTaskIterator.next();
                if (currentTask.getId().equals(currentNewTask.getId())) {
                    throw new RuntimeException("The task with this ID has been already assigned to this core. Please call " +
                            "clearCore function before!");
                }
            }
        }
        tasks.addAll(newTasks);
    }

    public String getUid() {
        return uid;
    }

    public String getId() {
        return id;
    }

    public String getMcpID()
    {
        return mcpID;
    }

    /**
     * Get all the tasks assigned to the core
     * @return
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Main task scheduler algorithm
     */
    public void scheduleTasks() {
        //calculateResponseTime();
        calculateVCRT();
    }

    /*public void calculateResponseTime()
    {
        Iterator<Task> taskIterator = tasks.iterator();
        while(taskIterator.hasNext())
        {
            Task currentTask = taskIterator.next();
            Iterator<Task> taskIterator2 = tasks.iterator();
            float interference = (float) 0.0;
            while(taskIterator2.hasNext())
            {
                Task currentTask2 = taskIterator2.next();
                if(currentTask2.getPriority() > currentTask.getPriority())
                {
                    interference += currentTask2.getWcet() * wcetFactor;
                }
            }
            currentTask.setResponseTime(currentTask.getWcet() * wcetFactor + interference);
        }
    }*/

    /**
     * Calculate the Worst-case Response time for each tasks
     */
    public void calculateVCRT()
    {
       ArrayList<Task> prioritySequence = prioritySequence(tasks);
        Iterator<Task> prioritySequenceIterator = prioritySequence.iterator();
        while(prioritySequenceIterator.hasNext() == true)
        {
            Task currentTask1 = prioritySequenceIterator.next();
            float Ri = currentTask1.getWcet()* wcetFactor;
            float sum = (float) 0.0;
            Iterator<Task> prioritySequenceIterator2 = prioritySequence.iterator();
            while(prioritySequenceIterator2.hasNext())
            {
                Task currentTask2 = prioritySequenceIterator2.next();
                if(currentTask2.equals(currentTask1) || currentTask2.getPriority() <= currentTask1.getPriority())
                {
                    break;
                }
                sum += Math.ceil(Ri/currentTask2.getPeriod()) * currentTask2.getWcet() * wcetFactor;
                if(currentTask1.getWcet() * wcetFactor + sum <= Ri)
                {
                    break;
                }
                else
                {
                    Ri = currentTask1.getWcet() * wcetFactor + sum;
                }
            }
            currentTask1.setWcrt(Ri);
        }
    }

    /**
     * Return by an ArrayList of tasks which contains the Tasks from the input parameter in
     * priority order, from the highest to the lowest
     * @param _tasks
     * @return
     */
    ArrayList<Task> prioritySequence(ArrayList<Task> _tasks)
    {
        ArrayList<Task> buffer = (ArrayList<Task>)_tasks.clone();
        ArrayList<Task> outputArray = new ArrayList<Task>();
        while(buffer.size() > 0)
        {
            Iterator<Task> bufferIterator = buffer.iterator();
            float maximum = -1;
            Task result = buffer.get(0);
            while (bufferIterator.hasNext())
            {
                Task currentTask = bufferIterator.next();
                if(maximum == (float)-1.0)
                {
                    maximum = currentTask.getPriority();
                    result = currentTask;
                }
                else if(maximum < currentTask.getPriority())
                {
                    maximum = currentTask.getPriority();
                    result = currentTask;
                }
            }
            outputArray.add(result);
            buffer.remove(result);

        }
        return outputArray;
    }

    /**
     * Remove all the tasks from the core and clear the other inner variables as well
     */
    public void clearCore() {
        tasks.clear();
    }

    /**
     * Returns by the tasks from the input ArrayList of tasks which has the same ID as the input
     * Throw runtime exception if no Task found with the input id
     * @param _tasks
     * @param id
     * @return
     */
    public Task getTaskByID(ArrayList<Task> _tasks, String id) {
        Iterator<Task> taskIterator = _tasks.iterator();
        while (taskIterator.hasNext()) {
            Task currentTask = taskIterator.next();
            if (currentTask.getId().equals(id)) {
                return currentTask;
            }
        }
        throw new RuntimeException("Required task not found in the ArrayList");
    }

    /**
     * Calculate and return by the cost function, call after Scheduling
     * @return
     */
    public float calculateCostFunction() {
        unfeasibleTasks.clear();
        float result = (float) 0.0;
        Iterator<Task> taskIterator = tasks.iterator();
        while (taskIterator.hasNext()) {
            Task currentTask = taskIterator.next();
            float currentWcrt = currentTask.getWcrt();
            float currentDeadline = (float) currentTask.getDeadline();
            if(currentWcrt > currentDeadline)
            {
                unfeasibleTasks.add(currentTask);
                feasible = false;
            }
            result += currentDeadline - currentWcrt;
        }
        return result;
    }

    public boolean isFeasible()
    {
        return feasible;
    }

    public ArrayList<Task> getUnfeasibleTasks()
    {
        return unfeasibleTasks;
    }
}