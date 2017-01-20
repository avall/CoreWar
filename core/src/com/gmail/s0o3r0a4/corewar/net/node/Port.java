package com.gmail.s0o3r0a4.corewar.net.node;

import com.gmail.s0o3r0a4.corewar.net.node.core.Instruction;

import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.newInstance;

public class Port {
    //    private final int portID;
//    private final int node1Addr;
//    private final int node2Addr;
    private Node node1;
    private Node node2;
    private int node1PortID;
    private int node2PortID;
    private Instruction in;
    private Instruction out;
    private Instruction inBuffer;
    private Instruction outBuffer;

    public Port(Node node1, Node node2) {
//        this.portID = portID;
//        this.node1Addr = node1Addr;
//        this.node2Addr = node2Addr;

        this.node1 = node1;
        this.node2 = node2;
    }

    public NodeAddr getNode1Addr() {
        return this.node1.getAddr();
    }

    public NodeAddr getNode2Addr() {
        return this.node2.getAddr();
    }

    public void flush() {
        out = outBuffer;
        in = inBuffer;
    }

    public Instruction readIn(Node node) {
        if (node.equals(node1)) {
            return newInstance(in);
        } else {
            return newInstance(out);
        }
    }

    public void writeOut(Node node, Instruction instruction) {
        if (node.equals(node1)) {
            outBuffer = instruction;
        } else {
            inBuffer = instruction;
        }
    }

    public Instruction readOut(Node node)
    {
        if (node.equals(node1)) {
            return newInstance(out);
        } else {
            return newInstance(in);
        }
    }

    public NodeAddr getConnectedNodeAddr(Node node) {
        if (node.equals(node1)) {
            return node2.getAddr();
        } else {
            return node1.getAddr();
        }
    }
    public Node getConnectedNode(Node node) {
        if (node.equals(node1)) {
            return node2;
        } else {
            return node1;
        }
    }

    public int getPortID(Node node) {
        if (node.equals(node1)) {
            return node1PortID;
        } else {
            return node2PortID;
        }
    }

}

