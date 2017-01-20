package com.gmail.s0o3r0a4.corewar.net.node;

public class NodeAddr {
    public final int addr1;
    public final int addr2;

    public NodeAddr(int addr1, int addr2) {
        this.addr1 = addr1;
        this.addr2 = addr2;
    }

    public NodeAddr() {
        this.addr1 = 0;
        this.addr2 = 0;
    }

    public String toString() {
        return Integer.toString(addr1) + "." + Integer.toString(addr2);
    }
}
