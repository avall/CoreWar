package com.gmail.s0o3r0a4.corewar.net.node;

import com.badlogic.gdx.Gdx;
import com.gmail.s0o3r0a4.corewar.maths.Maths;
import com.gmail.s0o3r0a4.corewar.net.node.core.Core;
import com.gmail.s0o3r0a4.corewar.net.node.core.Instruction;
import com.gmail.s0o3r0a4.corewar.net.node.core.Process;
import com.gmail.s0o3r0a4.corewar.net.node.core.Warrior;

import java.util.ArrayList;

import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.ADDR_MODE.DIR;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.ADDR_MODE.IMM;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.MODIFIER.AB;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.MODIFIER.F;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.MODIFIER.I;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.OP_CODE.DAT;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.OP_CODE.JMP;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.OP_CODE.MOV;

public class Node extends Core {
    private final NodeAddr nodeAddr;
    private ArrayList<Port> ports;
    private float startupTime;
    private int maxPorts;

    private boolean receving;
    private boolean sending;

    private int sendingPort;

    private static final Instruction DAT00 = new Instruction(DAT, F, DIR, 0, DIR, 0);

    public Node(int coreSize, NodeAddr nodeAddr, float startupTime) {
        super(coreSize);
        this.currentProcess = new Process(0, coreSize); // TODO: Add new constructor
        this.warriors = new ArrayList<Warrior>();
        this.nodeAddr = nodeAddr;
        this.ports = new ArrayList<Port>();
        this.startupTime = startupTime;
        initCore();
    }

    public Node() {
        super();
        this.currentProcess = new Process(0, coreSize); // TODO: Add new constructor
        this.warriors = new ArrayList<Warrior>();
        this.nodeAddr = new NodeAddr(0, 0);
        this.ports = new ArrayList<Port>();
        initCore();
    }

    @Override
    public void initCore() {
        for (int i = 0; i < coreSize; i++) {
            core[i] = DAT00;
        }

//        core[0] = new Instruction(ADD, AB, IMM, 4, DIR, 3);
//        core[1] = new Instruction(MOV, I, DIR, 2, IND, 2);
//        core[2] = new Instruction(JMP, AB, DIR, -2, DIR, 0);
//        core[3] = new Instruction(DAT, F, IMM, 0, IMM, 0);

        core[0] = new Instruction(MOV, I, DIR, 0, DIR, 2);
        core[1] = new Instruction(MOV, I, DIR, -1, DIR, 2);
        core[2] = new Instruction(JMP, AB, DIR, 1, DIR, 0);
        core[3] = new Instruction(DAT, F, IMM, 1, IMM, 1);

//        core[2] = core[10];
//        core[500] = new Instruction(MOV, I, DIR, 0, DIR, 1);

        warriorID = 0;
        maxWarrior = 1;

        Warrior warrior = new Warrior(0, coreSize);
        // README: Except first one Minus one (temp)
        // TODO: Clean up this restriction
//        Warrior warrior2 = new Warrior(500, coreSize);
        warriors.add(warrior);
//        warriors.add(warrior2);
        currentProcess = warrior.getProcess();
        currentProcess.setAddr(0);
    }

    public void cycle() {
        if (maxWarrior > 0) {
            Instruction currentInstruction = core[currentAddress];

            Instruction.OP_CODE opCode = currentInstruction.getOpCode();
            Instruction.MODIFIER modifier = currentInstruction.getModifier();
            Instruction.ADDR_MODE modeA = currentInstruction.getModeA();
            int fieldA = currentInstruction.getFieldA();
            Instruction.ADDR_MODE modeB = currentInstruction.getModeB();
            int fieldB = currentInstruction.getFieldB();

            int addressA = 0;
            int addressB = 0;
            int immediateA = 0;
            int immediateB = 0;

            Gdx.app.debug(opCode.toString(), modeA.toString() + Integer.toString(fieldA) + " " +
                    modeB.toString() + Integer.toString(fieldB));
            Gdx.app.debug("Address:" + Integer.toString(currentProcess.getAddr()), "Warrior ID: " + Integer.toString(warriorID));

            switch (modeA) {
                case IMM:
                    addressA = 0;
                    break;

                case DIR:
                    addressA = fieldA;
                    break;

                case IND:
                    addressA = core[Maths.mod(fieldA + currentAddress, coreSize)].getFieldA() + fieldA;
                    break;
            }

            // Get immediate by address
            immediateA = core[currentProcess.getAddr(addressA)].getFieldA();

            switch (modeB) {
                case IMM:
                    addressB = 0;
                    break;

                case DIR:
                    addressB = fieldB;
                    break;

                case IND:
                    addressB = core[Maths.mod(fieldB + currentAddress, coreSize)].getFieldB() + fieldB;
                    break;
            }

            // Get immediate by address
            immediateB = core[currentProcess.getAddr(addressA)].getFieldB();

            int destination = currentProcess.getAddr(addressB); // TODO: move up for other cases?

            switch (opCode) {
                case IN:
                    if (0 <= addressA && addressA < ports.size())
                        core[destination] = ports.get(addressA).readIn(this);
                    break;

                case OUT:
                    if (0 <= addressA && addressA < ports.size())
                        ports.get(addressA).writeOut(this, core[destination]);
                    // TODO: Write to another node's port
                    break;

                case CON:
                    if (addressA == 0) {
                        core[currentAddress].setA(ports.get(addressB).getConnectedNodeAddr(this).addr1);
                        core[currentAddress].setB(ports.get(addressB).getConnectedNodeAddr(this).addr2);
                    } else if (addressA == 1) {

                    } else {
                        if (nodeAddr.addr1 != 1) {
                            // TODO: Public node
                        } else {
                            // TODO: Private node (No public connection)
                        }

                    }
                    break;

                case DCN:
                    if (addressA == 0) {

                    } else if (addressA == 1) {

                    } else {
                        for (int i = 0; i < ports.size(); i++) {
                            NodeAddr nodeAddr = ports.get(i).getConnectedNodeAddr(this);
                            if (addressA == nodeAddr.addr1 && addressB == nodeAddr.addr2) {
                                ports.set(i, null);
                            }
                        }
                    }
                    break;

                // TODO: Add node code here
                default:
                    super.cycle();
                    return;
            }
            currentProcess.nextAddr();

            // README: This warrior is the next one
            // README: Side effect: ID++
            currentWarrior = nextWarrior();

            if (currentWarrior != null) {
                currentProcess = currentWarrior.nextProcess(); // README: This process is from the next warrior
                currentAddress = currentWarrior.getProcess().getAddr();
            }
        }
        super.cycle();
    }

//    public void sentBy(Port port) {
//        invited = true;
//
//        for (int i = 0; i < ports.size(); i++) {
//            if (ports.get(i) == null) {
//                ports.set(i, port);
//                invited = false;
//            }
//        }
//
//        if (invited && maxPorts < ports.size()) {
//            ports.add(port);
//        }
//        invited = false;
//    }

    public Instruction.OP_CODE getType(int address) {
        return getInstruction(address).getOpCode();
    }

    public int getCurrentAddress() {
        return currentAddress;
    }

    public int getCoreSize() {
        return coreSize;
    }

    public boolean isUnempty(int address) {
        if (core[address].getFieldA() != 0 || core[address].getFieldB() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public NodeAddr getAddr() {
        return nodeAddr;
    }

    public float getStartupTime() {
        return startupTime;
    }

    public boolean isReceving() {
        return receving;
    }

    public boolean isSending() {
        return sending;
    }

    public int getSendingPort() {
        return sendingPort;
    }

    public Port getPort(int portID) {
        return ports.get(portID);
    }

    public int getPortsSize() {
        return ports.size();
    }
}
