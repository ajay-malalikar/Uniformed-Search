package ai.pipes.demos;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Ajay
 */
public class BFSearch extends Search {

    public BFSearch(GraphProblem p) {
        this.graphPrb = p;
    }

    @Override
    public PipeInfo searchProcess() {
        try {
            LinkedList<PipeInfo> frontier = new LinkedList<>();
            this.closedList.clear();
            
            frontier.add(new PipeInfo(graphPrb.source, 0, 0, "0"));
            do {
                PipeInfo leafNode = frontier.remove();
                closedList.add(leafNode.destination);
                LinkedList<PipeInfo> childNodes = graphPrb.graph.GetChildNodes(leafNode.destination);
                if (childNodes != null) {
                    for (PipeInfo childNode : childNodes) {
                            if(!closedList.contains(childNode.destination) && !frontier.stream().anyMatch(i -> (i.destination == null ? childNode.destination == null : i.destination.equals(childNode.destination))))
                            {
                                frontier.add(new PipeInfo(childNode.destination, 1, leafNode.endTime, "0"));
                                if(Arrays.asList(graphPrb.destinations).contains(childNode.destination)) {
                                    return frontier.peekLast();
                            }
                        }
                    }
                }
            } while (frontier.size() != 0);
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
