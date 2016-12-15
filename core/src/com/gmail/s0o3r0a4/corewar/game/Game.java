package com.gmail.s0o3r0a4.corewar.game;

import com.gmail.s0o3r0a4.corewar.Instruction;
import com.gmail.s0o3r0a4.corewar.Process;
import com.gmail.s0o3r0a4.corewar.Warrior;

import java.util.ArrayList;

public abstract class Game
{
    protected int coreSize;
    protected Instruction core[];
    protected ArrayList<Warrior> warriors = new ArrayList<Warrior>();
    protected ArrayList<Instruction> warriorsCode[];

    protected Process currentProcess;

    protected int warriorID;
    protected int maxWarrior;

    public Game(int playerNumber, int coreSize)
    {
        this.coreSize = coreSize;
        this.core = new Instruction[coreSize];
        this.warriorsCode = new ArrayList[playerNumber];
        this.warriors = new ArrayList<Warrior>();
//        this.currentProcess = new Process(0, coreSize); // TODO: Add new constructor
    }

    public void initCore()
    {

    }



}
