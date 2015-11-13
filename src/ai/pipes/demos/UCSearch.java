package ai.pipes.demos;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.stream.Stream;

/**
 *
 * @author Ajay
 */
public class UCSearch extends Search {

    public UCSearch(GraphProblem p) {
        this.graphPrb = p;
    }

    @Override
    public PipeInfo searchProcess() {
        try {
            PriorityQueue<PipeInfo> frontier = new PriorityQueue<>(new PriorityComparator());
            int timer;
            this.closedList.clear();
            frontier.offer(new PipeInfo(graphPrb.source, 0, 0, "0"));
            do {
                PipeInfo leafNode = frontier.poll();
                if (Arrays.asList(graphPrb.destinations).contains(leafNode.destination)) {
                    return leafNode;
                }
                
                closedList.add(leafNode.destination);
                LinkedList<PipeInfo> childNodes = graphPrb.graph.GetChildNodes(leafNode.destination);
                if (childNodes != null) {
                    for (PipeInfo childNode : childNodes) {
                        timer = (leafNode.endTime + graphPrb.startTime) % 24;
                        if(!childNode.isPipeOff(timer))
                        {
                            Stream temp = frontier.stream().filter(i -> (i.destination.equals(childNode.destination)));
                            long count = temp.count();
                            if (!closedList.contains(childNode.destination) && count == 0) {
                                frontier.offer(new PipeInfo(childNode, leafNode));
                            }
                            else if(count == 1)
                            {
                                temp = frontier.stream().filter(i -> (i.destination.equals(childNode.destination)));
                                PipeInfo elem = (PipeInfo) temp.findFirst().get();
                                if(elem.endTime > (leafNode.endTime + childNode.length))
                                {
                                    frontier.remove(elem) ;
                                    frontier.offer(new PipeInfo(childNode, leafNode));
                                }
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

class UCSearchNew extends Search {

    public UCSearchNew(GraphProblem p) {
        this.graphPrb = p;
    }

    @Override
    public PipeInfo searchProcess() {
        try {
            PriorityQueue<PipeInfo> frontier = new PriorityQueue<>(new PriorityComparator());
            this.closedList.clear();
            frontier.offer(new PipeInfo(graphPrb.source, 0, 0, "0"));
            do {
                if (frontier.size() == 0) {
                    return null;
                }

                PipeInfo leafNode = frontier.poll();
                
                if (Arrays.asList(graphPrb.destinations).contains(leafNode.destination)) {
                    return leafNode;
                }
                
                // Are all the children accessible. If yes add them to frontier. If no dont add leafnode to Explored
                if(this.expandLeaf(leafNode, frontier))
                    closedList.add(leafNode.destination);
                
            } while (frontier.size() != 0);
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    private boolean expandLeaf(PipeInfo leafNode, PriorityQueue<PipeInfo> frontier)
    {
        LinkedList<PipeInfo> childNodes = graphPrb.graph.GetChildNodes(leafNode.destination);
        boolean allAccessible = true;
        if (childNodes != null) {
            int timer;
            for (PipeInfo childNode : childNodes) {
                
                timer = (leafNode.endTime + graphPrb.startTime) % 24;
                if(childNode.isPipeOff(timer))
                {
                    allAccessible = false;
                }
                else
                {  
                    if (!closedList.contains(childNode.destination)) {
                        frontier.add(new PipeInfo(childNode, leafNode));
                    }
                }
            }
        }
        return allAccessible;
    }
}

class PriorityComparator implements Comparator<PipeInfo> {

    @Override
    public int compare(PipeInfo o1, PipeInfo o2) {
        return (o1.endTime < o2.endTime) ? -1 : (o1.endTime > o2.endTime) ? 1 : o1.destination.compareTo(o2.destination);
    }
}