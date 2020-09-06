/*
 *  Brandon Hopkins - C3290146
 *  Software Engineering - COMP2240
 *  Assignment 1
 */

import java.util.LinkedList;

public class NRR extends Algorithm
{
    //Minimum time quantum for algorithm.
    private int minTimeQuantum;

    /** Constructor */
    NRR(int dispatchLatency, int timeQuantum, int minTimeQuantum, LinkedList<Process> processes)
    {
        //Base constructor.
        super(dispatchLatency);

        //Initialise variables.
        this.minTimeQuantum = minTimeQuantum;

        //Loop over all processes.
        for (int size = processes.size(); size > 0; size--)
        {
            //Set time quantum to passed time quantum.
            processes.getFirst().setQuantumTime(timeQuantum);

            //Insert passed processes into imminent processes.
            insert(processes.removeFirst(), iProcesses);
        }
    }

    /** NRR scheduling algorithm. */
    protected void schedule(Process process)
    {
        //If process is expired and not at minimum time quantum.
        if (process.getServicedTime() != 0 && process.getQuantumTime() > minTimeQuantum)
        {
            //Decrement quantum time.
            process.setQuantumTime(process.getQuantumTime() - 1);
        }

        //Add new or expired processed to the end of the ready queue.
        rProcesses.addLast(process);
    }
}
