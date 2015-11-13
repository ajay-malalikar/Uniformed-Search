package ai.pipes.demos;

import java.util.LinkedList;

/**
 *
 * @author Ajay
 */
public abstract class Search {

    protected LinkedList<String> closedList;
    protected GraphProblem graphPrb;

    public Search() {
        this.closedList = new LinkedList<>();
    }

    /**
     * This methods searches through the graph and returns ClosedSet for the search process.
     * @return
     */
    public abstract PipeInfo searchProcess();
}
