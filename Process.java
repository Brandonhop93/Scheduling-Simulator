/*
 *  Brandon Hopkins - C3290146
 *  Software Engineering - COMP2240
 *  Assignment 1
 */

public class Process implements Comparable<Process>
{
    //Unique process identification.
    private int id;
    private int priority;

    //Time the process arrived.
    private int arrivalTime;
    private boolean arrived;

    //Time the process has waiting.
    private int waitingTime;

    //Time required to complete the process.
    private int serviceTime;

    //Time the process has been in service.
    private int servicedTime;

    //Time allowed to be occupying the processor.
    private int quantumTime;

    /** Constructor. **/
    Process(int id, int arrivalTime, int serviceTime)
    {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    /** Simulate running a portion or the complete process: returns the time spent on the processor. */
    public int runProcess(int simulationTime)
    {
        int usedQuantum;

        //Process finished.
        if (servicedTime + quantumTime > serviceTime)
        {
            //Set used used quantum to remaining quantum.
            usedQuantum = quantumTime - Math.abs(servicedTime + quantumTime - serviceTime);

            //Update serviced time.
            servicedTime = serviceTime;
        }

        //Process not finished.
        else
        {
            //Set used used quantum to quantumTime length.
            usedQuantum = quantumTime;

            //Update serviced time.
            servicedTime += quantumTime;
        }

        //Update waiting time.
        waitingTime = simulationTime + usedQuantum - arrivalTime - servicedTime;

        //Time used.
        return usedQuantum;
    }

    /** Comparable method for process ordering. */
    public int compareTo(Process other)
    {
        //Compare process by arrival.
        if (!arrived)
        {
            if (arrivalTime < other.arrivalTime)
            {
                return -1;
            }

            if (arrivalTime > other.arrivalTime)
            {
                return 1;
            }
        }

        //Compare processes by priority.
        if (priority < other.priority)
        {
            return -1;
        }

        else if (priority > other.priority)
        {
            return 1;
        }

        //Compare by id.
        else
        {
            //Clamp difference between id's from -1 to 1.
            return Math.max(-1, Math.min(1, id - other.id));
        }
    }

    /** Get id for process. */
    public int getId()
    {
        return id;
    }

    /** Set id for process. */
    public void setId(int id)
    {
        this.id = id;
    }

    /** Get priority for process. */
    public int getPriority()
    {
        return priority;
    }

    /** Set id for process. */
    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    /** Get service time for process. */
    public int getServiceTime()
    {
        return serviceTime;
    }

    /** Set service time for process. */
    public void setServiceTime(int time)
    {
        this.serviceTime = time;
    }

    /** Get arrival time for process. */
    public int getArrivalTime()
    {
        return arrivalTime;
    }

    /** Set arrival time for process. */
    public void setArrivalTime(int time)
    {
        this.arrivalTime = time;
    }

    /** Get if the process has arrived. */
    public boolean getArrived()
    {
        return arrived;
    }

    /** Set arrival time for process. */
    public void setArrived(boolean arrived)
    {
        this.arrived = arrived;
    }

    /** Get serviced time for process. */
    public int getServicedTime()
    {
        return servicedTime;
    }

    /** Set serviced time for process. */
    public void setServicedTime(int time)
    {
        this.servicedTime = time;
    }

    /** Get waiting time for process. */
    public int getWaitingTime()
    {
        return waitingTime;
    }

    /** Set waiting time for process. */
    public void setWaitingTime(int time)
    {
        this.waitingTime = time;
    }

    /** Get quantum time for process. */
    public int getQuantumTime()
    {
        return quantumTime;
    }

    /** Set quantum time for process. */
    public void setQuantumTime(int quantumTime)
    {
        this.quantumTime = quantumTime;
    }
}
