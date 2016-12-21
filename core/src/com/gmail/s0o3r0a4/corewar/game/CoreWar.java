package com.gmail.s0o3r0a4.corewar.game;

import com.badlogic.gdx.Gdx;
import com.gmail.s0o3r0a4.corewar.core.Instruction;
import com.gmail.s0o3r0a4.corewar.maths.Maths;
import com.gmail.s0o3r0a4.corewar.core.Process;
import com.gmail.s0o3r0a4.corewar.core.Warrior;

import java.util.ArrayList;

public abstract class CoreWar
{
    protected int coreSize;
    protected Instruction core[];
    protected ArrayList<Warrior> warriors = new ArrayList<Warrior>();
//    protected ArrayList<Instruction> warriorsCode[];

    protected int currentAddress;
    protected Process currentProcess;
    protected Warrior currentWarrior;

    protected int warriorID;
    protected int maxWarrior;

    protected CoreWar(/*int playerNumber, */int coreSize)
    {
        this.coreSize = coreSize;
        this.core = new Instruction[coreSize];
//        this.warriorsCode = new ArrayList[playerNumber];
//        this.warriors = new ArrayList<Warrior>();
//        this.currentProcess = new Process(0, coreSize); // TODO: Add new constructor
    }

    protected void initCore()
    {

    }

    protected Instruction getInstruction(int address)
    {
        address = Maths.mod(address, coreSize);
        return core[address];
    }

    protected void cycle()
    {
        if (maxWarrior > 0)
        {
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

            switch (modeA)
            {
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

            switch (modeB)
            {
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

            switch (opCode)
            {
                case MOV:
                    switch (modifier)
                    {
                        case A:
                            // SourceA => DestinationA
                            core[destination].setA(immediateA);
                            break;
                        case B:
                            // SourceB => DestinationB
                            core[destination].setB(immediateB);
                            break;
                        case AB:
                            // SourceA => DestinationB
                            core[destination].setB(immediateA);
                            break;
                        case BA:
                            // SourceB => DestinationA
                            core[destination].setA(immediateB);
                            break;
                        case F:
                            // SourceA=>DestinationA, SourceB=>DestinationB
                            core[destination].setA(immediateA);
                            core[destination].setB(immediateB);
                            break;
                        case X:
                            // SourceA=>DestinationB, SourceB=>DestinationA
                            core[destination].setB(immediateA);
                            core[destination].setA(immediateB);
                            break;
                        case I:
                            // Source => Destination
                            core[destination] = core[currentProcess.getAddr(addressA)];
                            break;
                    }
                    break;

                case ADD:
                    switch (modifier)
                    {
                        case A:
                            // SourceA + DestinationA => DestinationA
                            core[destination].setA(Maths.mod(immediateA + core[destination].getFieldA(), coreSize));
                            break;
                        case B:
                            // SourceB + DestinationB => DestinationB
                            core[destination].setB(Maths.mod(immediateB + core[destination].getFieldB(), coreSize));
                            break;
                        case AB:
                            // SourceA + DestinationB => DestinationB
                            core[destination].setB(Maths.mod(immediateA + core[destination].getFieldB(), coreSize));
                            break;
                        case BA:
                            // SourceB + DestinationA => DestinationA
                            core[destination].setA(Maths.mod(immediateB + core[destination].getFieldA(), coreSize));
                            break;
                        case F:
                            // SourceA + DestinationA => DestinationA, SourceB + DestinationB => DestinationB
                            core[destination].setA(Maths.mod(immediateA + core[destination].getFieldA(), coreSize));
                            core[destination].setB(Maths.mod(immediateB + core[destination].getFieldB(), coreSize));
                        case I:
                            break;
                        case X:
                            // SourceA + DestinationB => DestinationB, SourceB + DestinationA => DestinationA
                            core[destination].setB(Maths.mod(immediateA + core[destination].getFieldB(), coreSize));
                            core[destination].setA(Maths.mod(immediateB + core[destination].getFieldA(), coreSize));
                            break;
                    }
                    break;

                case SUB:
                    switch (modifier)
                    {
                        case A:
                            // DestinationA - SourceA => DestinationA
                            core[destination].setA(Maths.mod(core[destination].getFieldA() - immediateA, coreSize));
                            break;
                        case B:
                            // DestinationB - SourceB => DestinationB
                            core[destination].setB(Maths.mod(core[destination].getFieldB() - immediateB, coreSize));
                            break;
                        case AB:
                            // DestinationB - SourceA => DestinationB
                            core[destination].setB(Maths.mod(core[destination].getFieldB() - immediateA, coreSize));
                            break;
                        case BA:
                            // DestinationA - SourceB => DestinationA
                            core[destination].setA(Maths.mod(core[destination].getFieldA() - immediateB, coreSize));
                            break;
                        case F:
                            // DestinationA - SourceA => DestinationA, DestinationB - SourceB => DestinationB
                            core[destination].setA(Maths.mod(core[destination].getFieldA() - immediateA, coreSize));
                            core[destination].setB(Maths.mod(core[destination].getFieldB() - immediateB, coreSize));
                        case I:
                            break;
                        case X:
                            // DestinationB - SourceA => DestinationB, DestinationA - SourceB => DestinationA
                            core[destination].setB(Maths.mod(core[destination].getFieldB() - immediateA, coreSize));
                            core[destination].setA(Maths.mod(core[destination].getFieldA() - immediateB, coreSize));
                            break;
                    }
                    break;

                case MUL:
                    switch (modifier)
                    {
                        case A:
                            // SourceA * DestinationA => DestinationA
                            core[destination].setA(Maths.mod(immediateA * core[destination].getFieldA(), coreSize));
                            break;
                        case B:
                            // SourceB * DestinationB => DestinationB
                            core[destination].setB(Maths.mod(immediateB * core[destination].getFieldB(), coreSize));
                            break;
                        case AB:
                            // SourceA * DestinationB => DestinationB
                            core[destination].setB(Maths.mod(immediateA * core[destination].getFieldB(), coreSize));
                            break;
                        case BA:
                            // SourceB * DestinationA => DestinationA
                            core[destination].setA(Maths.mod(immediateB * core[destination].getFieldA(), coreSize));
                            break;
                        case F:
                            // SourceA * DestinationA => DestinationA, SourceB * DestinationB => DestinationB
                            core[destination].setA(Maths.mod(immediateA * core[destination].getFieldA(), coreSize));
                            core[destination].setB(Maths.mod(immediateB * core[destination].getFieldB(), coreSize));
                        case I:
                            break;
                        case X:
                            // SourceA * DestinationB => DestinationB, SourceB * DestinationA => DestinationA
                            core[destination].setB(Maths.mod(immediateA * core[destination].getFieldB(), coreSize));
                            core[destination].setA(Maths.mod(immediateB * core[destination].getFieldA(), coreSize));
                            break;
                    }
                    break;

                case DIV:
                    switch (modifier)
                    {
                        case A:
                            checkDividedByZero(immediateA);

                            // DestinationA / SourceA => DestinationA
                            core[destination].setA(core[destination].getFieldA() / immediateA);
                            break;
                        case B:
                            checkDividedByZero(immediateB);

                            // DestinationB / SourceB => DestinationB
                            core[destination].setB(core[destination].getFieldB() / immediateB);
                            break;
                        case AB:
                            checkDividedByZero(immediateA);

                            // DestinationB / SourceA => DestinationB
                            core[destination].setB(core[destination].getFieldB() / immediateA);
                            break;
                        case BA:
                            checkDividedByZero(immediateB);

                            // DestinationA / SourceB => DestinationA
                            core[destination].setA(core[destination].getFieldA() / immediateB);
                            break;
                        case F:
                            checkDividedByZero(immediateA);
                            checkDividedByZero(immediateB);

                            // DestinationA / SourceA => DestinationA, DestinationB / SourceB => DestinationB
                            core[destination].setA(core[destination].getFieldA() / immediateA);
                            core[destination].setB(core[destination].getFieldB() / immediateB);
                        case I:
                            break;
                        case X:
                            checkDividedByZero(immediateA);
                            checkDividedByZero(immediateB);

                            // DestinationB / SourceA => DestinationB, DestinationA / SourceB => DestinationA
                            core[destination].setB(core[destination].getFieldB() / immediateA);
                            core[destination].setA(core[destination].getFieldA() / immediateB);
                            break;
                    }
                    break;

                case MOD:
                    switch (modifier)
                    {
                        case A:
                            checkDividedByZero(immediateA);

                            // DestinationA % SourceA => DestinationA
                            core[destination].setA(Maths.mod(core[destination].getFieldA(), immediateA));
                            break;
                        case B:
                            checkDividedByZero(immediateB);

                            // DestinationB / SourceB => DestinationB
                            core[destination].setB(Maths.mod(core[destination].getFieldB(), immediateB));
                            break;
                        case AB:
                            checkDividedByZero(immediateA);

                            // DestinationB / SourceA => DestinationB
                            core[destination].setB(Maths.mod(core[destination].getFieldB(), immediateA));
                            break;
                        case BA:
                            checkDividedByZero(immediateB);

                            // DestinationA / SourceB => DestinationA
                            core[destination].setA(Maths.mod(core[destination].getFieldA(), immediateB));
                            break;
                        case F:
                            checkDividedByZero(immediateA);
                            checkDividedByZero(immediateB);

                            // DestinationA / SourceA => DestinationA, DestinationB / SourceB => DestinationB
                            core[destination].setA(Maths.mod(core[destination].getFieldA(), immediateA));
                            core[destination].setB(Maths.mod(core[destination].getFieldB(), immediateB));
                        case I:
                            break;
                        case X:
                            checkDividedByZero(immediateA);
                            checkDividedByZero(immediateB);

                            // DestinationB / SourceA => DestinationB, DestinationA / SourceB => DestinationA
                            core[destination].setB(Maths.mod(core[destination].getFieldB(), immediateA));
                            core[destination].setA(Maths.mod(core[destination].getFieldA(), immediateB));
                            break;
                    }
                    break;

                case JMP:
                    switch (modifier)
                    {
                        case A:
                        case B:
                        case AB:
                        case BA:
                        case F:
                        case X:
                        case I:
                            currentProcess.setAddr(addressA - 1);
                            break;
                    }
                    break;

                case JMZ:
                    switch (modifier)
                    {
                        case A:
                        case BA:
                            if (core[destination].getFieldA() == 0)
                            {
                                currentProcess.setAddr(addressA - 1); // README: Add
                            }
                            break;
                        case B:
                        case AB:
                            if (core[destination].getFieldB() == 0)
                            {
                                currentProcess.setAddr(addressA - 1);
                            }
                            break;
                        case F:
                        case X:
                        case I:
                            if (core[destination].getFieldA() == 0 && core[destination].getFieldB() == 0)
                            {
                                currentProcess.setAddr(addressA - 1);
                            }
                            break;
                    }
                    break;

                case JMN:
                    switch (modifier)
                    {
                        case A:
                        case BA:
                            if (core[destination].getFieldA() != 0)
                            {
                                currentProcess.setAddr(addressA - 1);
                            }
                            break;
                        case B:
                        case AB:
                            if (core[destination].getFieldB() != 0)
                            {
                                currentProcess.setAddr(addressA - 1);
                            }
                            break;
                        case F:
                        case X:
                        case I:
                            if (core[destination].getFieldA() != 0 ||core[destination].getFieldB() != 0)
                            {
                                currentProcess.setAddr(addressA - 1);
                            }
                            break;
                    }
                    break;

                case DJN:
                    switch (modifier)
                    {
                        case A:
                        case BA:
                            core[destination].setA(core[destination].getFieldA() - 1);
                            if (core[destination].getFieldA() != 0)
                            {
                                currentProcess.setAddr(addressA - 1);
                            }
                            break;
                        case B:
                        case AB:
                            core[destination].setB(core[destination].getFieldB() - 1);
                            if (core[destination].getFieldB() != 0)
                            {
                                currentProcess.setAddr(addressA - 1);
                            }
                            break;
                        case F:
                        case X:
                        case I:
                            core[destination].setB(core[destination].getFieldB() - 1);
                            core[destination].setB(core[destination].getFieldB() - 1);
                            if (core[destination].getFieldA() != 0 ||core[destination].getFieldB() != 0)
                            {
                                currentProcess.setAddr(addressA - 1);
                            }
                            break;
                    }
                    break;

                case DAT:
                default:
                    killProcess();
            }
            // README: This warrior is the next one
            // README: Side effect: ID++
            currentWarrior = nextWarrior();

            if (currentWarrior != null)
            {
                currentProcess = currentWarrior.nextProcess(); // README: This process is from the next warrior
                currentAddress = currentProcess.nextAddr();
            }

        }
    }

    protected void removeWarrior()
    {
        // TODO: Record lost warrior
        maxWarrior--;
        warriors.remove(warriorID);
    }

    protected void killProcess()
    {
        currentWarrior.killProcess();
        Gdx.app.debug("Kill current process", "address: " + Integer.toString(currentAddress));

        if (currentWarrior.getMaxProcesses() == 0)
        {
            removeWarrior();
        }
    }

    protected void checkDividedByZero(int dividend)
    {
        if (dividend == 0)
        {
            killProcess();
        }
    }

    // README: Should only be called once in one cycle
    private Warrior nextWarrior()
    {
        if (maxWarrior != 0)
        {
            warriorID = (++warriorID) % maxWarrior;
            return warriors.get(warriorID);
        }
        else
        {
            return null;
        }
    }
}
