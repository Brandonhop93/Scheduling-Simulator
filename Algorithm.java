/*
 *  Brandon Hopkins - C3290146
 *  Software Engineering - COMP2240
 *  Assignment 1
 */

import java.util.*;

public abstract class Algorithm
{
    //Simulation time.
    private int simulationTime;

    //Time to run the dispatcher.
    private int dispatchLatency;

    //Process Queues: Immindent, Ready, Exit.
    protected LinkedList<Process> iProcesses;
    protected LinkedList<Process> rProcesses;
    protected LinkedList<Process> eProcesses;

    /** Constructor. */
    Algorithm(int dispatchLatency)
    {
        //Initialise variables.
        this.simulationTime = 0;
        this.dispatchLatency = dispatchLatency;
        this.iProcesses = new LinkedList<Process>();
        this.rProcesses = new LinkedList<Process>();
        this.eProcesses = new LinkedList<Process>();
    }

    /** Discrete time event simulator. */
    public void simulate()
    {
        //Console Printing.
        System.out.println(this.toString() + ":");
        int lastPrinted = -1;

        //Run until all processes are completed.
        while (iProcesses.size() + rProcesses.size() != 0)
        {
            //Current running process.
            Process process;

            //Run next ready processes.
            if (!rProcesses.isEmpty())
            {
                //Increment simulation time.
                simulationTime += dispatchLatency;

                //Assign new process.
                process = rProcesses.removeFirst();
            }

            //Run next imminent processes.
            else
            {
                //Advance to the next imminent process.
                simulationTime = iProcesses.getFirst().getArrivalTime() + dispatchLatency;

                //Assign new process and mark as arrived.
                process = iProcesses.removeFirst();
                process.setArrived(true);
            }

            //Don't print reran processes.
            if (process.getId() != lastPrinted)
            {
                //Console Printing.
                System.out.println("T" + simulationTime + ": p" + process.getId());
                lastPrinted = process.getId();
            }

            while (true) //Breaks via process finished or another process waiting.
            {
                //Update process statistics and simulation time.
                simulationTime += process.runProcess(simulationTime);

                //Loop over imminent processes.
                for (int i = iProcesses.size(); i > 0; i--)
                {
                    //Another process has arrived while running this process.
                    if (iProcesses.getFirst().getArrivalTime() <= simulationTime)
                    {
                        //Schedule into ready queue and mark as arrived.
                        iProcesses.getFirst().setArrived(true);
                        schedule(iProcesses.removeFirst());
                    }

                    else
                    {
                        //No more arrived processes.
                        break;
                    }
                }

                //Running processed finished.
                if (process.getServicedTime() == process.getServiceTime())
                {
                    //Add to exit queue.
                    eProcesses.addLast(process);
                    break;
                }

                //Other processes waiting.
                if (rProcesses.size() != 0)
                {
                    //Schedule into ready queue.
                    schedule(process);
                    break;
                }
            }
        }
    }

    /** Abstract scheduling algorithm. */
    protected abstract void schedule(Process process);

    /** Insert process into specified list with respect to comparator. */
    protected void insert(Process process, LinkedList<Process> list)
    {
        //Add to processes if list is empty.
        if (list.size() == 0)
        {
            list.addFirst(process);
        }

        else
        {
            //Loop over all processes.
            for (int i = 0; i < list.size(); i++)
            {
                //Compare processes by priority, arrival time, id.
                if (process.compareTo(list.get(i)) < 0)
                {
                    list.add(i, process);
                    return;
                }
            }

            //Add process to end of the list.
            list.addLast(process);
        }
    }

    /** Print out statistsics for each finished process. */
    public void printStatistics()
    {
        //Print out statistics.
        System.out.print("\n");
        System.out.print(String.format("%-8s", "Process"));
        System.out.print(String.format("%-16s", "Turnaround Time"));
        System.out.println(String.format("%-13s", "Waiting Time"));

        //Loop over all exited processes in order of id.
        for (int i = 0; i < eProcesses.size(); i++)
        {
            for (int o = 0; o < eProcesses.size(); o++)
            {
                if (eProcesses.get(o).getId() == i + 1)
                {
                    Process process =  eProcesses.get(o);
                    System.out.print(String.format("%-8s", "p" + process.getId()));
                    System.out.print(String.format("%-16s", process.getServicedTime() + process.getWaitingTime()));
                    System.out.print(String.format("%-13s", process.getWaitingTime()));
                    System.out.print("\n");
                }
            }
        }

        System.out.print("\n");
    }

    /** Print out average statistics for all finished processes. */
    public void printAverageStatistics()
    {
        //Print out average statistics.
        System.out.print(String.format("%-16s", this.getClass().getName()));

        double averageTT = 0;
        double averageWT = 0;

        //Loop over all exited processes.
        for (int i = 0; i < eProcesses.size(); i++)
        {
            averageTT += eProcesses.get(i).getServicedTime();
            averageWT += eProcesses.get(i).getWaitingTime();
        }

        averageTT += averageWT;
        averageTT /= eProcesses.size();
        averageWT /= eProcesses.size();

        System.out.print(String.format("%-26s", String.format("%.02f", averageTT)));
        System.out.println(String.format("%-22s", String.format("%.02f", averageWT)));
    }

    /** Return algorithm type. */
    public String toString()
    {
        return this.getClass().getName();
    }
}