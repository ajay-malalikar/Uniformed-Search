package ai.pipes.demos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Ajay
 */
public class AIPipesDemos {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        File f = new File("outputInput.txt");
        if (!f.exists()) {
            f.createNewFile();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
            //Clears out existing file contents
            writer.write("");
            if (args.length == 0) {
                parseInputAndProcess("", writer);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void parseInputAndProcess(String fileName, BufferedWriter writer) throws Exception {
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("F:\\Netbeans\\Projects\\AI Homeworks\\HW1\\AI Pipes Demos\\src\\ai\\pipes\\demos\\inputGenerator.txt"));
            int numOfTestCases = Integer.parseInt(reader.readLine());
            GraphProblem gp;
            for (int i = 0; i < numOfTestCases; i++) {
//                counter = i;
//                if(i==18)
//                {
//                    System.out.println("221");
//                }
                String typeOfsearch = reader.readLine().trim();
                String source = reader.readLine().trim();
                String destinations = reader.readLine().trim();
                String[] dest = destinations.split(" ");
                String intermediateNodes = reader.readLine().trim();
                String[] intNodes = intermediateNodes.split(" ");
                int numOfPipes = Integer.parseInt(reader.readLine().trim());
                String[] pipeConfig = new String[numOfPipes];
                for (int j = 0; j < numOfPipes; j++) {
                    pipeConfig[j] = reader.readLine();
                }
                int startTime = Integer.parseInt(reader.readLine().trim());
                gp = new GraphProblem(typeOfsearch, source, dest, intNodes, numOfPipes, pipeConfig, startTime);
                processGraph(gp, writer);
                reader.readLine();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private static void processGraph(GraphProblem gp, BufferedWriter writer) throws Exception {
        Search s;
        PipeInfo output;
        try
        {
            switch (gp.typeOfsearch) {
                case "BFS":
                    s = new BFSearch(gp);
                    output = s.searchProcess();
                    if (output != null) {
                        int totalTime = (output.endTime + gp.startTime) % 24;
                        writer.append(output.destination + " " + totalTime);
                        writer.newLine();
                    } else {
                        writer.append("None");
                        writer.newLine();
                    }
                    break;
                case "DFS":
                    s = new DFSearch(gp);
                    output = s.searchProcess();
                    if (output != null) {
                        int totalTime = (output.endTime + gp.startTime) % 24;
                        writer.append(output.destination + " " + totalTime);
                        writer.newLine();
                    } else {
                        writer.append("None");
                        writer.newLine();
                    }
                    break;
                case "UCS":
                    s = new UCSearch(gp);
                    output = s.searchProcess();
                    if (output != null) {
                        int totalTime = (output.endTime + gp.startTime) % 24;
                        writer.append(output.destination + " " + totalTime);
                        writer.newLine();
                    } else {
                        writer.append("None");
                        writer.newLine();
                    }
                    break;
                default:
                    break;
            }
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}