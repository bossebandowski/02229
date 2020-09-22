import java.util.*;

public class Core {
    final String id;
    final String mcpID;
    final String uid;
    static Integer maxPeriod = 0; //Has to be shared by all cores

    ArrayList<Task> tasks = new ArrayList<Task>();
    ArrayList<String> taskIDs = new ArrayList<String>();

    Map<String, ArrayList<Integer>> TaskPeriodsStart = new HashMap<>(); // TaskID -> Starting moment of its periods
    //Map<String, ArrayList<Map<Integer, Integer>>> schedule = new HashMap<>(); // TaskID -> List of {Startmoment, Endmoment}
    Map<Integer, String> momentSchedule = new HashMap<>(); //Which moment is which task(ID) assigned to
    final float wcetFactor;

    public Core(String _id, String _mcpID, float _wcetFactor) {
        id = _id;
        mcpID = _mcpID;
        uid = mcpID + id;
        wcetFactor = _wcetFactor;
        maxPeriod = 0;
    }

    public void addTask(Task newTask) {
        Iterator<Task> taskIterator = tasks.iterator();
        while(taskIterator.hasNext())
        {
            Task currentTask = taskIterator.next();
            if(currentTask.getId().equals(newTask.getId()))
            {
                throw new RuntimeException("The task with this ID has been already assigned to this core. Please call " +
                        "clearCore function before!");
            }
        }
        tasks.add(newTask);
    }

    public void removeTask(Task taskToRemove)
    {
        if(tasks.remove(taskToRemove) == false)
        {
            throw new RuntimeException("The required task is not contained by the core");
        }
    }

    public void addTask(ArrayList<Task> newTasks) {
        Iterator<Task> taskIterator = tasks.iterator();
        Iterator<Task> newTaskIterator = newTasks.iterator();
        while(taskIterator.hasNext())
        {
            Task currentTask = taskIterator.next();
            while(newTaskIterator.hasNext())
            {
                Task currentNewTask = newTaskIterator.next();
                if(currentTask.getId().equals(currentNewTask.getId()))
                {
                    throw new RuntimeException("The task with this ID has been already assigned to this core. Please call " +
                            "clearCore function before!");
                }
            }
        }
        tasks.addAll(newTasks);
    }

    public String getID()
    {
        return id;
    }

    public void scheduleTasks() {
        reInit();
        Integer clockCounter = 0;
        ArrayList<String> tasksToSchedule = new ArrayList<String>();
        while (clockCounter < maxPeriod) {
            for (Map.Entry<String, ArrayList<Integer>> entry : TaskPeriodsStart.entrySet()) {
                //System.out.println(entry.getKey());
                ArrayList<Integer> startingMoments = entry.getValue();
                Iterator<Integer> startingMomentIterator = startingMoments.iterator();
                while (startingMomentIterator.hasNext()) {
                    Integer currentValue = startingMomentIterator.next();
                    if (currentValue == clockCounter) {
                        Task currentTask = getTaskByID(tasks, entry.getKey());
                        if (tasksToSchedule.contains(currentTask) == false) {
                            System.out.println("ADDED");
                            tasksToSchedule.add(entry.getKey());
                        }
                    }
                }
            }
            if (tasksToSchedule.isEmpty() == false) {
                Task highestPriorityTask = getHighestPriority(tasksToSchedule);
                highestPriorityTask.setExecution_stop(highestPriorityTask.getExecution_stop() + 1);
                //System.out.println( "HIGHEST"+highestPriorityTask.getId());
                //System.out.println( "HIGHEST EXECUTION"+highestPriorityTask.getexecutionState());
                momentSchedule.put(clockCounter, highestPriorityTask.getId());
                if (highestPriorityTask.getExecution_stop() == Math.ceil((float) highestPriorityTask.getWcet() * wcetFactor)) {
                    System.out.println("Task finished" + highestPriorityTask.getId());
                    tasksToSchedule.remove(highestPriorityTask.getId());
                    Integer highestWcrt = (int) getTaskByID(tasks, highestPriorityTask.getId()).getWcrt();
                    Integer currentWcrt = clockCounter % (getTaskByID(tasks, highestPriorityTask.getId()).getDeadline());
                    if (highestWcrt <= currentWcrt || highestWcrt == -1) {
                        getTaskByID(tasks, highestPriorityTask.getId()).setWcrt(currentWcrt);
                    }
                }
            }
            System.out.println("Moment:" + clockCounter + "Scheduled");
            clockCounter++;
        }
    }

    public String getId(){

        return this.id;
    }

    public String getMcpID() {

        return mcpID;
    }


    public ArrayList<Task> getTasks(){
        return tasks;
    }

    public boolean checkFeasibility() {
        Iterator<Task> taskIterator = tasks.iterator();
        while(taskIterator.hasNext())
        {
            Task currentTask = taskIterator.next();
            System.out.println(currentTask.getWcrt() + "____" +currentTask.getDeadline());
            if(currentTask.getWcrt() > currentTask.getDeadline() || currentTask.getWcrt() == -1)
            {
                return false;
            }
        }
        return true;

    }

    private Integer getHighestPeriod() {

        Integer result = 0;
        Iterator<Task> tasksIterator = tasks.iterator();
        while (tasksIterator.hasNext()) {
            Integer period = tasksIterator.next().getPeriod();
            if (period > result) {
                result = period;
            }
        }
        return result;
    }

    /*private void calculatePriorities() {
        Iterator<Task> tasksIterator = tasks.iterator();
        while (tasksIterator.hasNext()) {

        }
    }*/

    private Task getHighestPriority(ArrayList<String> inputTaskIDs) {
        if(inputTaskIDs.isEmpty())
        {
            throw new RuntimeException("The Arraylist is empty");
        }
        Iterator<String> taskIDIterator = inputTaskIDs.iterator();
        float maxPriority = (float) 0.0;
        Task result = null;
        while (taskIDIterator.hasNext()) {
            String currentTask = taskIDIterator.next();
            if (getTaskByID(tasks,currentTask).getPriority() >= maxPriority) {
                maxPriority = getTaskByID(tasks,currentTask).getPriority();
                result = getTaskByID(tasks,currentTask);
            }
        }
        return result;
    }

    //Must be called after a new Task has been added to the core
    private void reInit()
    {
        taskIDs.clear();
        TaskPeriodsStart.clear();
        momentSchedule.clear();
        if(getHighestPeriod() > maxPeriod)
        {
            maxPeriod = getHighestPeriod();
        }
        for (int i = 0; i < maxPeriod; i++) {
            momentSchedule.put(i, null);
        }
        Iterator<Task> tasksIterator = tasks.iterator();
        ArrayList<Integer> timeArray = new ArrayList<Integer>();
        while (tasksIterator.hasNext()) {
            timeArray.clear();
            timeArray.add(0);
            Task task = tasksIterator.next();
            taskIDs.add(task.getId());
            Integer period = task.getPeriod();
            boolean finished = false;
            Integer counter = 0;
            while (finished == false) {
                if (counter + period <= maxPeriod) {
                    counter += period;
                    timeArray.add(counter);
                } else {
                    finished = true;
                }
            }
            TaskPeriodsStart.put(task.getId(), timeArray);
        }
    }

    public void clearCore()
    {
        tasks.clear();
        taskIDs.clear();
        TaskPeriodsStart.clear();
        momentSchedule.clear();
        maxPeriod = 0;
    }

    public Task getTaskByID(ArrayList<Task> _tasks, String id)
    {
        Iterator<Task> taskIterator = _tasks.iterator();
        while(taskIterator.hasNext())
        {
            Task currentTask = taskIterator.next();
            if(currentTask.getId().equals(id))
            {
                return currentTask;
            }
        }
        throw new RuntimeException("Required task not found");
    }

    public Map<Integer, String> getSchedule()
    {
        return momentSchedule;
    }

    public float calculateCostFunction()
    {
        float result = (float) 0.0;
        Iterator<Task> taskIterator = tasks.iterator();
        while(taskIterator.hasNext())
        {
            Task currentTask = taskIterator.next();
            float currentWcrt = (float) currentTask.getWcrt();
            float currentDeadline = (float) currentTask.getDeadline();
            result += Math.abs(currentDeadline - currentWcrt);
        }
        return result;
    }
}