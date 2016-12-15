package com.gmail.s0o3r0a4.corewar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import static com.gmail.s0o3r0a4.corewar.Instruction.ADDR_MODE.DIR;
import static com.gmail.s0o3r0a4.corewar.Instruction.ADDR_MODE.IMM;
import static com.gmail.s0o3r0a4.corewar.Instruction.OP_CODE.DAT;
import static com.gmail.s0o3r0a4.corewar.Instruction.OP_CODE.MOV;
import com.badlogic.gdx.Gdx;

public class Game
{
    private int coreSize;
    private Instruction core[];
    private ArrayList<Warrior> warriors = new ArrayList<Warrior>();
    private ArrayList<Instruction> warriorsCode[];

    Process currentProcess;

    private int warriorID;
    private int maxWarrior;

    public static final Instruction DAT00 = new Instruction(DAT, IMM, 0, IMM, 0);

    public Game(int playerNumber, int coreSize)
    {
        this.coreSize = coreSize;
        this.core = new Instruction[coreSize];
        warriorsCode = new ArrayList[playerNumber];

        currentProcess = new Process(0, coreSize); // TODO: Add new constructor
        warriors = new ArrayList<Warrior>();
    }

    public void initCore()
    {
        core[0] = new Instruction(MOV, DIR, 0, DIR, 1);
        for(int i = 1; i < coreSize; i++)
        {
            core[i] = DAT00;
        }

        warriorID = 0;
        maxWarrior = 1;

        Warrior warrior = new Warrior(0, 8000);
        warriors.add(warrior);

        currentProcess = warrior.getProcess();
        currentProcess.setAddr(0);
    }

    public void cycle()
    {
        Warrior currentWarrior;
        int currentAddress;


        // README: This warrior is the next one
        // README: Side effect: ID++

        if (maxWarrior > 0)
        {
            currentWarrior = nextWarrior();

            currentProcess = currentWarrior.nextProcess(); // README: This process is from the next warrior

            currentAddress = currentProcess.nextAddr();

            Instruction currentInstruction = core[currentAddress];

            Instruction.OP_CODE opCode = currentInstruction.getOpCode();
            Instruction.ADDR_MODE modeA = currentInstruction.getModeA();
            int fieldA = currentInstruction.getFieldA();
            Instruction.ADDR_MODE modeB = currentInstruction.getModeB();
            int fieldB = currentInstruction.getFieldB();

            int addressA = 0;
            int addressB = 0;
            int immediateA = 0;
            int immediateB = 0;


            switch (modeA)
            {
                case IMM:
                    immediateA = fieldA;
                    break;

                case DIR:
                    addressA = fieldA;
                    break;

                case IND:
                    addressA = core[Maths.mod(fieldA + currentAddress, coreSize)].getFieldA() + fieldA;
                    break;
            }

            switch (modeB)
            {
                case IMM:
                    immediateB = fieldB;
                    break;

                case DIR:
                    addressB = fieldB;
                    break;

                case IND:
                    addressB = core[Maths.mod(fieldB + currentAddress, coreSize)].getFieldB() + fieldB;
                    break;

                default:
//                System.out.println("error");
            }

            switch (opCode)
            {
                case MOV:
                    core[currentProcess.getAddr(addressB)] = core[currentProcess.getAddr(addressA)];
                    break;

                case DAT:
                default:
                    currentWarrior.killProcess();
                    if (currentWarrior.getMaxProcesses() == 0)
                    {
                        loseWarrior();
                    }
            }
        }
        else
        {
            Gdx.app.log("", "end game");
        }
    }

    public void run()
    {
        initCore();

        for(int step = 0; step < 8000; step++)
        {
            if (maxWarrior > 0)
            {
                cycle();

//            try
//            {
//                System.in.read();
//            } catch (IOException e)
//            {
//                e.printStackTrace();
//
            }
        }

        for(int i = 0; i < 125; i++)
        {
            String sentence = "";
            for(int j = 0; j < 64; j++)
            {
                if (core[j+i*64].getOpCode() == DAT)
                {
                    sentence += ".";
                }
                else if ((core[j+i*64].getOpCode() == MOV) && ((currentProcess.getAddr()) == j+i*64))
                {
                    sentence += "M";
                }
                else
                {
                    sentence += "m";
                }
            }
            Gdx.app.log("", sentence);
        }
    }

    // README: Should only be called once in one cycle
    public Warrior nextWarrior()
    {
        warriorID = (++warriorID) % maxWarrior;
        return warriors.get(warriorID);
    }

    public void loseWarrior()
    {
        // TODO: Record lost warrior
        maxWarrior--;
        warriors.remove(warriorID);
    }
}
