package com.gmail.s0o3r0a4.corewar.core;

import java.util.ArrayList;

public class Warrior
{
    private ArrayList<com.gmail.s0o3r0a4.corewar.core.Process> processes;

    private int currentProcess;
    private int maxProcesses;

    public Warrior(int address, int maxAddr)
    {
        this.processes = new ArrayList<com.gmail.s0o3r0a4.corewar.core.Process>();
        this.processes.add(0, new com.gmail.s0o3r0a4.corewar.core.Process(address, maxAddr));
        this.currentProcess = 0;
        this.maxProcesses = 1;
    }

    public com.gmail.s0o3r0a4.corewar.core.Process getProcess()
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
    public com.gmail.s0o3r0a4.corewar.core.Process nextProcess()
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
        processes.add(currentProcess, new com.gmail.s0o3r0a4.corewar.core.Process(address, maxAddr));
    }

    public int getMaxProcesses()
    {
        return maxProcesses;
    }
}
