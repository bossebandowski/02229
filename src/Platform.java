import java.util.ArrayList;

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
            }
        }
        return null;
    }

    public void AssignTasks()
    {

    }

}
