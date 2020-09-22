import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class IOInterface {
    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<Core> cores = new ArrayList<>();
    private Platform platform;
    private Solution solution;
    private String strOut;


    public void readFile(String path) {
        // snippet based on https://www.javatpoint.com/how-to-read-xml-file-in-java
        // Using the DOM API to read and write .xml files
        try {
            // create file object
            File file = new File(path);

            // an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            // create tasks
            NodeList taskNodes = doc.getElementsByTagName("Task");
            generateTasks(taskNodes);

            // create platform
            NodeList coreNodes = doc.getElementsByTagName("Core");
            generatePlatform(coreNodes);
        }
        catch (Exception e) {
            System.err.println("ERR: Could not parse " + path);
            e.printStackTrace();
        }

    }

    private void generateTasks(NodeList taskNodes) {
        for (int i = 0; i < taskNodes.getLength(); i++) {
            Element taskElement = (Element) taskNodes.item(i);
            String taskID = taskElement.getAttribute("Id");
            int deadline = Integer.parseInt(taskElement.getAttribute("Deadline"));
            int period = Integer.parseInt(taskElement.getAttribute("Period"));
            int wcet = Integer.parseInt(taskElement.getAttribute("WCET"));
            this.tasks.add(new Task(taskID, deadline, period, wcet));
        }
    }

    private void generatePlatform(NodeList coreNodes) {
        for (int i = 0; i < coreNodes.getLength(); i++) {
            Element coreElement = (Element) coreNodes.item(i);
            Element parent = (Element) coreElement.getParentNode();
            String parentId = parent.getAttribute("Id");
            String coreId = coreElement.getAttribute("Id");
            float wcetFactor = Float.parseFloat(coreElement.getAttribute("WCETFactor"));
            this.cores.add(new Core(coreId, parentId, wcetFactor));
        }

        this.platform = new Platform(this.cores);
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Platform getPlatform() {
        return platform;
    }

    public static void main(String[] args) throws IOException {
        Task.priorities = new ArrayList<>();
        IOInterface ioHandler = new IOInterface();
        ioHandler.readFile("test/small.xml");


    }
}
