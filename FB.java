/*
 *  Brandon Hopkins - C3290146
 *  Software Engineering - COMP2240
 *  Assignment 1
 */

import java.util.LinkedList;

public class FB extends Algorithm
{
    //Maximum priority for algorithm.
    private int maxPriority;

    /** Constructor */
    FB(int dispatchLatency, int timeQuantum, int maxPriority, LinkedList<Process> processes)
    {
        //Base constructor.
        super(dispatchLatency);

        //Initialise variables.
        this.maxPriority = maxPriority;

        //Loop over all processes.
        for (int size = processes.size(); size > 0; size--)
        {
            //Set time quantum to execution time.
            processes.getFirst().setQuantumTime(timeQuantum);
            processes.getFirst().setPriority(0);

            //Insert passed processes into imminent processes.
            insert(processes.removeFirst(), iProcesses);
        }
    }

    /** FB scheduling algorithm. */
    protected void schedule(Process process)
    {
        //If process is expired and not at max priority.
        if (process.getServicedTime() != 0 && process.getPriority() < maxPriority)
        {
            //Decrement process priority.
            process.setPriority(process.getPriority() + 1);
        }

        if (process.getPriority() < maxPriority)
        {
            //Priority insert.
            insert(process, rProcesses);
        }

        else
        {
            //Round Robin insert.
            rProcesses.addLast(process);
        }
    }

    /** Return algorithm type. */
    public String toString()
    {
        return this.getClass().getName() + " (constant)";
    }
}