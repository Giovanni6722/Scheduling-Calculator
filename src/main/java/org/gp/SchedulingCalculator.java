package org.gp;

import java.util.Arrays;
import java.util.Comparator;

public class SchedulingCalculator
{
    static class Process
    {
        int id;
        int burst;
        int arrival;
        int waiting;
        int turnaround;

        Process(int id, int burst, int arrival)
        {
            this.id = id;
            this.burst = burst;
            this.arrival = arrival;
        }

        Process(Process other)
        {
            this.id = other.id;
            this.burst = other.burst;
            this.arrival = other.arrival;
        }
    }

    public static void main(String[] args)
    {
        Process[] processes =
                {
                new Process(1, 2, 0),
                new Process(2, 1, 0),
                new Process(3, 8, 0),
                new Process(4, 4, 0),
                new Process(5, 5, 0)
                };

        runFCFS(processes);
        System.out.println();
        runSJF(processes);
    }

    static void runFCFS(Process[] original)
    {
        Process[] processes = copyProcesses(original);

        int currentTime = 0;
        double totalWaiting = 0;
        double totalTurnaround = 0;

        for (Process p : processes)
        {
            if (currentTime < p.arrival) {currentTime = p.arrival;}

            p.waiting = currentTime - p.arrival;
            currentTime += p.burst;
            p.turnaround = p.waiting + p.burst;

            totalWaiting += p.waiting;
            totalTurnaround += p.turnaround;
        }

        printResults("FCFS", processes, totalWaiting, totalTurnaround);
    }

    static void runSJF(Process[] original)
    {
        Process[] processes = copyProcesses(original);

        Arrays.sort(processes, Comparator.comparingInt((Process p) -> p.burst)
                .thenComparingInt(p -> p.arrival)
                .thenComparingInt(p -> p.id));

        int currentTime = 0;
        double totalWaiting = 0;
        double totalTurnaround = 0;

        for (Process p : processes)
        {
            if (currentTime < p.arrival) {currentTime = p.arrival;}

            p.waiting = currentTime - p.arrival;
            currentTime += p.burst;
            p.turnaround = p.waiting + p.burst;

            totalWaiting += p.waiting;
            totalTurnaround += p.turnaround;
        }

        printResults("SJF", processes, totalWaiting, totalTurnaround);
    }

    static Process[] copyProcesses(Process[] original)
    {
        Process[] copy = new Process[original.length];
        for (int i = 0; i < original.length; i++) {copy[i] = new Process(original[i]);}
        return copy;
    }

    static void printResults(String title, Process[] processes, double totalWaiting, double totalTurnaround)
    {
        System.out.println("----------------- " + title + " -----------------");
        System.out.println("Process ID | Waiting Time | Turnaround Time");

        for (Process p : processes) {System.out.printf("    P%-5d | %-12d | %-15d%n", p.id, p.waiting, p.turnaround);}

        System.out.printf("%nAverage Waiting Time: %.2f%n", totalWaiting / processes.length);
        System.out.printf("Average Turnaround Time: %.2f%n", totalTurnaround / processes.length);
    }
}
