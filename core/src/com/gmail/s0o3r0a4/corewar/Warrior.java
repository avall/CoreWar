package com.gmail.s0o3r0a4.corewar;

import java.util.ArrayList;

public class Warrior
{
    private ArrayList<Process> processes;

    private int currentProcess;
    private int maxProcesses;

    public Warrior(int address, int maxAddr)
    {
        this.processes = new ArrayList<Process>();
        this.processes.add(0, new Process(address, maxAddr));
        this.currentProcess = 0;
        this.maxProcesses = 1;
    }

    public Process getProcess()
    {
        if (processes.size() != 0)
        {
            return processes.get(currentProcess);
        }
        else
        {
            return null;
        }
    }

    // README: Should only be called once in one cycle
    public Process nextProcess()
    {
        if (processes.size() != 0)
        {
            currentProcess = (++currentProcess) % maxProcesses;
            return processes.get(currentProcess);
        }
        else
        {
            return null;
        }
    }

    public void killProcess()
    {
        if (processes.size() != 0)
        {
            maxProcesses--;
            processes.remove(currentProcess);
        }
    }

    public void addProcess(int address, int maxAddr)
    {
        maxProcesses++;
        processes.add(currentProcess, new Process(address, maxAddr));
    }

    public int getMaxProcesses()
    {
        return maxProcesses;
    }
}
