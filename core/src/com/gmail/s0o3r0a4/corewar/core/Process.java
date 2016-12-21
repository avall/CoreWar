package com.gmail.s0o3r0a4.corewar.core;

import com.gmail.s0o3r0a4.corewar.maths.Maths;

public class Process
{
    private int address;
    private final int maxAddr;

    public Process(int address, int maxAddr)
    {
        this.maxAddr = maxAddr;
        this.address = Maths.mod(address, maxAddr);
    }

    public void setAddr(int offset)
    {
        address = Maths.mod(address + offset, maxAddr); // README: Offset can be negative
    }

    public int getAddr(int offset)
    {
        return Maths.mod(address + offset, maxAddr); // README: Offset can be negative
    }

    // README: Should only be called once in one cycle
    public int nextAddr()
    {
        return address = ++address % maxAddr;
    }

    public int getAddr()
    {
        return address;
    }
}
