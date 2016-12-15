package com.gmail.s0o3r0a4.corewar;

import static com.gmail.s0o3r0a4.corewar.Instruction.ADDR_MODE.IMM;
import static com.gmail.s0o3r0a4.corewar.Instruction.OP_CODE.DAT;

public class Instruction
{
    private OP_CODE operation;
    private ADDR_MODE modeA;
    private int fieldA;
    private ADDR_MODE modeB;
    private int fieldB;

    public enum OP_CODE
    {
        DAT,
        MOV;
    }

    public enum ADDR_MODE
    {
        IMM,
        DIR,
        IND;
    }

    public Instruction(OP_CODE operation, ADDR_MODE modeA, int fieldA, ADDR_MODE modeB, int fieldB)
    {
        this.operation  = operation;
        this.modeA = modeA;
        this.fieldA = fieldA;
        this.modeB = modeB;
        this.fieldB = fieldB;
    }

    public OP_CODE getOpCode()
    {
        return operation;
    }

    public ADDR_MODE getModeA()
    {
        return modeA;
    }

    public int getFieldA()
    {
        return fieldA;
    }

    public ADDR_MODE getModeB()
    {
        return modeB;
    }

    public int getFieldB()
    {
        return fieldB;
    }


}
