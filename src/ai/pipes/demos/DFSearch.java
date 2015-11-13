package ai.pipes.demos;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Ajay
 */
public class DFSearch extends Search {

    public DFSearch(GraphProblem p) {
        this.graphPrb = p;
    }

    @Override
    public PipeInfo searchProcess() {
        try {
            LinkedList<PipeInfo> frontier = new LinkedList<>();
            LinkedList<PipeInfo> temp = new LinkedList<>();
            this.closedList.clear();
            frontier.add(new PipeInfo(graphPrb.source, 0, 0, "0"));
            do {
                PipeInfo leafNode = frontier.remove();
                if (Arrays.asList(graphPrb.destinations).contains(leafNode.destination)) {
                    return leafNode;
                }
                closedList.add(leafNode.destination);
                LinkedList<PipeInfo> childNodes = graphPrb.graph.GetChildNodes(leafNode.destination);
                if (childNodes != null) {
                    childNodes.stream().filter((childNode) -> (!closedList.contains(childNode.destination))).forEach((childNode) -> {
                        temp.add(new PipeInfo(childNode.destination, 1, leafNode.endTime, "0"));
                    });
                    frontier.addAll(0, temp);
                    temp.clear();
                }
            } while (frontier.size() != 0);
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
