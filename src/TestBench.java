import java.util.ArrayList;

public class TestBench {
    private ArrayList<String> testFiles = new ArrayList<>();
    private ArrayList<String> solutionFiles = new ArrayList<>();
    private final String outputPrefix = "../testResults/out_";

    private void addTestFile(String path) {
        testFiles.add(path);
        String filename = path.split("/")[path.split("/").length-1];
        solutionFiles.add(outputPrefix + filename);
    }

    public static void main(String[] args) {
        TestBench tb = new TestBench();
        tb.addTestFile("../data/small.xml");
        tb.addTestFile("../data/medium.xml");
        tb.addTestFile("../data/large.xml");

        for (int i = 0; i < tb.testFiles.size(); i++) {
            Scheduler.run(tb.testFiles.get(i), tb.solutionFiles.get(i));
        }
    }


}
