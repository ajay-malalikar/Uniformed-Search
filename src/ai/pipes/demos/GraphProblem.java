package ai.pipes.demos;

/**
 * GraphProblem class holds all the information of one test case from the input file. It holds variable graph that is the graph representation of the input. 
 * @author Ajay
 */
public class GraphProblem {

    public String typeOfsearch;
    public String source;
    public String[] destinations;
    private String[] intermediateNodes;
    public int startTime;
    public Graph graph;

    public GraphProblem(String search, String src, String[] dest, String[] intrNodes, int nPipes, String[] pipeonfig, int startTime) {
        this.typeOfsearch = search;
        this.source = src;
        this.destinations = dest;
        this.intermediateNodes = intrNodes;
        this.startTime = startTime;
        int nodesCount = destinations.length + intermediateNodes.length + 1;
        graph = new Graph(nodesCount, nPipes, pipeonfig, concatAllNodes(source, intermediateNodes, destinations));
    }

    private String[] concatAllNodes(String src, String[]... nodes) {
        String[] allNodes;
        int length = 1;
        for (String[] node : nodes) {
            length += node.length;
        }
        allNodes = new String[length];
        allNodes[0] = src;
        int curr = 1;
        for (String[] node : nodes) {
            System.arraycopy(node, 0, allNodes, curr, node.length);
            curr += node.length;
        }
        return allNodes;
    }
}
