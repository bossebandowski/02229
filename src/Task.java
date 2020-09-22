import java.util.List;

public class Task {

    static public List<Float> priorities;


    final String id;
    final int deadline;
    final int period;
    private float priority;
    final int wcet;


    public Task(String taskID, int ddline, int period1, int wcet1){

        id = taskID;
        deadline = ddline;
        period = period1;
        wcet = wcet1;

        setPriority();



    }

    public void setPriority(){
        float priorityTemp = (float) (1.0/period);
        if(priorities.contains(priorityTemp)){
            priority = (float) (priorityTemp * 1.00001);

        }else{
            priority = priorityTemp;

        }
        priorities.add(priority);
    }

    public String getId() {
        return id;
    }

    public int getDeadline() {
        return deadline;
    }

    public int getPeriod() {
        return period;
    }

    public float getPriority() {
        return priority;
    }

    public int getWcet() {
        return wcet;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", deadline=" + deadline +
                ", period=" + period +
                ", priority=" + priority +
                ", wcet=" + wcet +
                '}';
    }
}

