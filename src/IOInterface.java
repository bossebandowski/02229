import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class IOInterface {
    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<Core> cores = new ArrayList<>();
    private Platform platform;

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
        } catch (Exception e) {
            System.err.println("ERR: Could not parse " + path);
            e.printStackTrace();
        }

    }

    public void writeSolution(Solution solution, String path) {

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("solution");
            document.appendChild(root);

            // add task elements
            for (SolutionOutput assignment : solution.getSolutionOutput()) {
                Element task = document.createElement("Task");
                root.appendChild(task);

                task.setAttribute("Id", String.valueOf(assignment.Id));
                task.setAttribute("MCP", String.valueOf(assignment.MCP));
                task.setAttribute("Core", String.valueOf(assignment.Core));
                task.setAttribute("WCET", String.valueOf(assignment.WCET));
            }

            // add laxity
            Element element = document.getDocumentElement();
            Comment comment = document.createComment(" Total Laxity: " + solution.getAvgLaxity());
            element.getParentNode().insertBefore(comment, element);

            // create the xml file
            // transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(path));

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
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

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Platform getPlatform() {
        return platform;
    }

}
