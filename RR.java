import java.util.LinkedList;

public class RR extends Algorithm
{
    /** Constructor */
    RR(int dispatchLatency, int timeQuantum, LinkedList<Process> processes)
    {
        //Base constructor.
        super(dispatchLatency);

        //Loop over all processes.
        for (int size = processes.size(); size > 0; size--)
        {
            //Set time quantum to passed time quantum.
            processes.getFirst().setQuantumTime(timeQuantum);

            //Insert passed processes into imminent processes.
            insert(processes.removeFirst(), iProcesses);
        }
    }

    /** RR scheduling algorithm. */
    protected void schedule(Process process)
    {
        //Add new or expired processed to the end of the ready queue.
        rProcesses.addLast(process);
    }
}
