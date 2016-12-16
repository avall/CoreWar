package com.gmail.s0o3r0a4.corewar.game;

import java.util.ArrayList;

import static com.gmail.s0o3r0a4.corewar.Instruction.ADDR_MODE.DIR;
import static com.gmail.s0o3r0a4.corewar.Instruction.ADDR_MODE.IMM;
import static com.gmail.s0o3r0a4.corewar.Instruction.ADDR_MODE.IND;
import static com.gmail.s0o3r0a4.corewar.Instruction.MODIFIER.F;
import static com.gmail.s0o3r0a4.corewar.Instruction.MODIFIER.I;
import static com.gmail.s0o3r0a4.corewar.Instruction.OP_CODE.DAT;
import static com.gmail.s0o3r0a4.corewar.Instruction.OP_CODE.MOV;

import com.badlogic.gdx.Gdx;
import com.gmail.s0o3r0a4.corewar.Instruction;
import com.gmail.s0o3r0a4.corewar.Process;
import com.gmail.s0o3r0a4.corewar.Warrior;

public class DebugGame extends Game
{
//    private int coreSize;
//    private Instruction core[];
//    private ArrayList<Warrior> warriors = new ArrayList<Warrior>();
//    private ArrayList<Instruction> warriorsCode[];
//
//    private Process currentProcess;
//
//    private int warriorID;
//    private int maxWarrior;

    private static final Instruction DAT00 = new Instruction(DAT, F, DIR, 0, DIR, 0);

    public DebugGame(int coreSize)
    {
        super(coreSize);
        this.currentProcess = new Process(0, coreSize); // TODO: Add new constructor

        this.warriors = new ArrayList<Warrior>();
    }

    public void run()
    {
        initCore();

        for(int step = 0; step < 10; step++)
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



//        for(int i = 0; i < 125; i++)
//        {
//            String sentence = "";
            for(int j = 0; j < 16; j++)
            {
                Gdx.app.log("", core[j].getOpCode().toString()+"."+core[j].getModifier().toString()+
                                " "+core[j].getModeA().toString()+Integer.toString(core[j].getFieldA())+
                                " "+core[j].getModeB().toString()+Integer.toString(core[j].getFieldB()));
//                if (core[j].getOpCode() == DAT)
//                {
//                    sentence += ".";
//                }
//                else if ((core[j].getOpCode() == MOV) && (currentProcess.getAddr() == j))
//                {
//                    sentence += "M";
//                }
//                else
//                {
//                    sentence += "m";
//                }
            }
//            Gdx.app.log("", sentence);
//        }
    }

    @Override
    protected void cycle()
    {
        super.cycle();
    }

    @Override
    protected void removeWarrior()
    {
        super.removeWarrior();
        // TODO: Record lost warrior
    }

    @Override
    public void initCore()
    {
        for(int i = 0; i < coreSize; i++)
        {
            core[i] = DAT00;
        }

        core[0] = new Instruction(MOV, I, IND, 1, DIR, 1);
        core[1] = new Instruction(MOV, I, IMM, 1, DIR, 1);
        core[2] = new Instruction(MOV, I, DIR, 1, DIR, 1);

        warriorID = 0;
        maxWarrior = 1;

        Warrior warrior = new Warrior(0, coreSize);
        warriors.add(warrior);

        currentProcess = warrior.getProcess();
        currentProcess.setAddr(0);
    }

}
