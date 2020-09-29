import java.util.ArrayList;
import java.util.List;

public class Task {

    static public ArrayList<Float> priorities;


    final String id;
    final int deadline;
    final int period;
    private float priority;
    final int wcet;
    float interference;
    float responseTime;



    private int execution_stop;
    private float wcrt;
    private float laxity;


    public Task(String taskID, int ddline, int period1, int wcet1){

        id = taskID;
        deadline = ddline;
        period = period1;
        wcet = wcet1;
        interference = (float) 0.0;

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

    public int getExecution_stop() {
        return execution_stop;
    }

    public void setExecution_stop(int execution_stop) {
        this.execution_stop = execution_stop;
    }

    public float getWcrt() {
        return wcrt;
    }

    public void setWcrt(float wcrt) {
        this.wcrt = wcrt;
    }

    public void setInterference(float newValue){interference = newValue;}

    public void setResponseTime(float newValue){responseTime = newValue;}

    public float getLaxity() {
        return laxity;
    }

    public void setLaxity(float laxity) {
        this.laxity = laxity;
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

    public float getInterference(){return  interference;}

    public  float getResponseTime(){return  responseTime;}


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

