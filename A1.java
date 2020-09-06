/*
 *  Brandon Hopkins - C3290146
 *  Software Engineering - COMP2240
 *  Assignment 1
 */

import java.io.*;
import java.util.*;

public class A1
{
    public static void main(String[] args)
    {
        if (args.length != 0)
        {
            try
            {
                //First Come First Served.
                Algorithm FCFS = readFile(new File(args[0]), FCFS.class);
                FCFS.simulate();
                FCFS.printStatistics();

                //Round Robin.
                Algorithm RR = readFile(new File(args[0]), RR.class);
                RR.simulate();
                RR.printStatistics();

                //Feedback.
                Algorithm FB = readFile(new File(args[0]), FB.class);
                FB.simulate();
                FB.printStatistics();

                //Narrow Round Robin.
                Algorithm NRR = readFile(new File(args[0]), NRR.class);
                NRR.simulate();
                NRR.printStatistics();

                //Print out summary.
                System.out.println("Summary");
                System.out.print(String.format("%-16s", "Algorithm"));
                System.out.print(String.format("%-26s", "Average Turnaround Time"));
                System.out.println(String.format("%-22s", "Average Waiting Time"));
                FCFS.printAverageStatistics();
                RR.printAverageStatistics();
                FB.printAverageStatistics();
                NRR.printAverageStatistics();
            }

            catch(IOException e)
            {
                //Error reading file.
                System.out.println(e);
            }
        }

        else
        {
            //No file argument provided.
            System.out.println("Please specify a file to read with commands: java A1 filename");
        }
    }

    /** Return a new algorithm of specified type with processes from file. */
    private static Algorithm readFile(File file, Class<? extends Algorithm> classType) throws IOException
    {
        //Algorithm - latency, time quantum, minimum quantum, maximum priority.
        int aDL = 1;
        int aTQ = 4;
        int aMQ = 2;
        int aMP = 5;

        //Algorithm - imminent processes.
        LinkedList<Process> processes = new LinkedList<>();

        //Create a file scanner and pass file.
        Scanner scanner = new Scanner(file);

        ///BEGIN///////////////////////////////
        while(scanner.hasNext())
        {
            //Read until 'BEGIN' found.
            if (scanner.next().equals("BEGIN"))
            {
                break;
            }

            if (!scanner.hasNext())
            {
                //Incorrect file format.
                throw new IOException("File format error - missing 'BEGIN' keyword");
            }
        }

        //Read file word by word.
        while (scanner.hasNext())
        {
            switch (scanner.next())
            {
                ///DISPATCHER/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                case "DISP:":

                    //Assign dispatch latency.
                    aDL = Integer.valueOf(scanner.next());

                    //Dispatcher loop.
                    D_Scanner:while (scanner.hasNext())
                    {
                        switch (scanner.next())
                        {
                            case "END":
                                break D_Scanner;

                            default:
                                //Incorrect file format.
                                throw new IOException("File format error - Expecting 'END' keyword after 'DISP: N'.");
                        }
                    }

                    break;

                ///PROCESS///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                case "ID:":

                    //Process - Identity, arrival, execution.
                    int pID = Integer.valueOf(scanner.next().substring(1));
                    int pAR = -1;
                    int pEX = -1;

                    //Process loop.
                    P_Scanner: while (scanner.hasNext())
                    {
                        switch (scanner.next())
                        {
                            case "Arrive:":
                                pAR = Integer.valueOf(scanner.next());
                                break;

                            case "ExecSize:":
                                pEX = Integer.valueOf(scanner.next());
                                break;

                            case "END":
                                if (pAR == -1)
                                {
                                    //Incorrect file format.
                                    throw new IOException("File format error - missing 'Arrive: N' keyword for process p" + pID + ".");
                                }

                                else if (pEX == -1)
                                {
                                    //Incorrect file format.
                                    throw new IOException("File format error - missing 'ExecSize: N' keyword for process p" + pID + ".");
                                }

                                else
                                {
                                    //Create new process from file.
                                    processes.add(new Process(pID, pAR, pEX));
                                    break P_Scanner;
                                }

                            default:
                                //Incorrect file format.
                                throw new IOException("File format error - expecting 'Arrive: N', 'ExecSize: N' or 'END' keyword for process p" + pID + ".");
                        }
                    }

                    break;

                ///END OF FILE///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                case "EOF":

                    //Close the scanner.
                    scanner.close();

                    //Return a new algorithm.
                    switch (classType.getName())
                    {
                        case "FCFS" :
                            return new FCFS(aDL, processes);
                        case "RR" :
                            return new RR(aDL, aTQ, processes);
                        case "FB" :
                            return new FB(aDL, aTQ, aMP, processes);
                        case "NRR" :
                            return new NRR(aDL, aTQ, aMQ, processes);
                        default:
                            throw new IOException("Invalid Parameter - Unknown algorithm type: " + classType.getName() + ".");
                    }

                ////GENERIC ERROR////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                default:

                    //Incorrect file format.
                    throw new IOException("File format error - Unknown, duplicate or missing symbol.");

            }
        }

        //Incorrect file format.
        throw new IOException("File format error - Missing 'EOF' keyword.");
    }
}