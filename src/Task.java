public class Task {
    private final String id;
    private final int deadline;
    private final int period;
    private final int wcet;


    public Task(String taskID, int ddline, int period, int wcet){
        this.id = taskID;
        this.deadline = ddline;
        this.period = period;
        this.wcet = wcet;
    }
}
