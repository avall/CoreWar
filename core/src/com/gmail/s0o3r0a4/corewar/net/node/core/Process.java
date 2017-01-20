package com.gmail.s0o3r0a4.corewar.net.node.core;

import com.gmail.s0o3r0a4.corewar.maths.Maths;

public class Process {
    private int address;
    private final int maxAddr;

    public Process(int address, int maxAddr) {
        this.maxAddr = maxAddr;
        this.address = Maths.mod(address, maxAddr);
    }

    public Process() {
        this.maxAddr = 8000;
        this.address = Maths.mod(0, 8000);
    }

    public void setAddr(int offset) {
        address = Maths.mod(address + offset, maxAddr); // README: Offset can be negative
    }

    public int getAddr(int offset) {
        return Maths.mod(address + offset, maxAddr); // README: Offset can be negative
    }

    // README: Should only be called once in one cycle
    public void nextAddr() {
        address = ++address % maxAddr;
    }

    public int getAddr() {
        return address;
    }
}
