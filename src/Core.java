import java.util.ArrayList;
import java.util.Map;

public class Core {
    final String id;
    final String mcpID;
    final String uid;
    ArrayList<Task> tasks;
    Map<Task,ArrayList<Map<Integer,Integer>>> schedule; // Task -> List of {Startmoment, Endmoment}
    final float wcetFactor;

    public Core(String ID, String MCPID,float WCETFACTOR)
    {
        id = ID;
        mcpID = MCPID;
        uid = mcpID+id;
        wcetFactor = WCETFACTOR;
    }
}
