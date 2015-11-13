package ai.pipes.demos;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This class hold the graph representation of the input case.
 *
 * @author Ajay
 */
public class Graph {

    public final int nNodes;
    public final int nEdges;
    private Map<String, LinkedList<PipeInfo>> adjDictionary;

    public Graph(int nNodes, int nEdges, String[] pipeConfig, String[] nodes) {
        this.nNodes = nNodes;
        this.nEdges = nEdges;
        this.buildAdjencencyDictionary(pipeConfig);
    }

    /**
     *
     * @param pipeConfigs
     */
    private void buildAdjencencyDictionary(String[] pipeConfigs) {
        adjDictionary = new HashMap<>();
        for (String pipeConfig1 : pipeConfigs) {
            String[] pipeConfigParsed = pipeConfig1.split(" ", 4);
            // For DFS and BFS it may happen that cost and off-periods are not given
            if(pipeConfigParsed.length == 2)
            {
                addPipe(pipeConfigParsed[0], pipeConfigParsed[1], 1, "0");
            }
            else
            {
                addPipe(pipeConfigParsed[0], pipeConfigParsed[1], Integer.parseInt(pipeConfigParsed[2]), pipeConfigParsed[3]);
            }
        }
        sortAdjencencyList();
    }

    private void addPipe(String src, String dest, int cost, String extraInfo) {
        LinkedList<PipeInfo> listPlaceholder = new LinkedList<>();
        if (!adjDictionary.containsKey(src)) {
            listPlaceholder.add(new PipeInfo(dest, cost, 0, extraInfo));
            adjDictionary.put(src, listPlaceholder);
        } else {
            adjDictionary.get(src).add(new PipeInfo(dest, cost, 0, extraInfo));
        }
    }

    private void sortAdjencencyList()
    {
        this.adjDictionary.keySet().stream().forEach((keySet) -> {
            Collections.sort(adjDictionary.get(keySet));
        });
    }
    
    /**
     *
     * @param node
     * @return
     */
    public LinkedList<PipeInfo> GetChildNodes(String node) {
        return this.adjDictionary.get(node);
    }
}

class PipeInfo implements Comparable<PipeInfo> {

    public PipeInfo parent;
    public String destination;
    public int endTime;
    public int startTime;
    public int length;
    public int offPeriodCount;
    public PipeOffPeriod[] offPeriods = null;

    public PipeInfo(String dest, int length, int startTime, String extraInfo) {
        this.destination = dest;
        this.length = length;
        this.startTime = startTime;
        this.endTime = this.startTime + this.length;
        
        String[] extraInfoParsed = extraInfo.split(" ");
        this.offPeriodCount = Integer.parseInt(extraInfoParsed[0]);
        if (offPeriodCount > 0) {
            this.offPeriods = new PipeOffPeriod[this.offPeriodCount];
            for (int i = 0; i < offPeriodCount; i++) {
                String[] times = extraInfoParsed[i+1].split("-");
                this.offPeriods[i] = new PipeOffPeriod(Integer.parseInt(times[0]), Integer.parseInt(times[1]));
            }
        }
    }

    public PipeInfo(String destination, int length, int startTime, int offPeriodCount, PipeOffPeriod[] offPeriods, PipeInfo parent) {
        this.destination = destination;
        this.length = length;
        this.startTime = startTime;
        this.offPeriodCount = offPeriodCount;
        this.offPeriods = offPeriods != null ? offPeriods.clone() : offPeriods;
        this.endTime = this.startTime + this.length;
        this.parent = parent;
    }

    public PipeInfo(PipeInfo p, PipeInfo parent) {
        this(p.destination, p.length, parent.endTime, p.offPeriodCount, p.offPeriods, parent);
    }

    public boolean isPipeOff(int currentTime) {
        if (this.offPeriods != null) {
            for (PipeOffPeriod offPeriod : this.offPeriods) {
                if (offPeriod.startTime <= currentTime && currentTime <= offPeriod.endTime) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int compareTo(PipeInfo o) {
        return this.destination.compareTo(o.destination);
    }
}

class PipeOffPeriod implements Cloneable{

    public int startTime;
    public int endTime;

    public PipeOffPeriod(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{  
        return super.clone();  
    } 
}