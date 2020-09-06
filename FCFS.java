/*
 *  Brandon Hopkins - C3290146
 *  Software Engineering - COMP2240
 *  Assignment 1
 */

import java.util.LinkedList;

public class FCFS extends Algorithm
{
    /** Constructor */
    FCFS(int dispatchLatency, LinkedList<Process> processes)
    {
        //Base constructor.
        super(dispatchLatency);

        //Loop over all passed processes.
        for (int size = processes.size(); size > 0; size--)
        {
            //Set time quantum to service time.
            processes.getFirst().setQuantumTime(processes.getFirst().getServiceTime());

            //Insert passed processes into imminent processes.
            insert(processes.removeFirst(), iProcesses);
        }
    }

    /** FCFS scheduling algorithm. */
    protected void schedule(Process process)
    {
        //Add new process to the end of the ready queue.
        rProcesses.addLast(process);
    }
}
