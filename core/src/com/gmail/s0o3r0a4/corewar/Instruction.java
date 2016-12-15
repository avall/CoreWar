package com.gmail.s0o3r0a4.corewar;

import org.omg.CORBA.BAD_CONTEXT;

import java.util.ArrayList;

import sun.text.normalizer.NormalizerBase;

import static com.gmail.s0o3r0a4.corewar.Instruction.ADDR_MODE.IMM;
import static com.gmail.s0o3r0a4.corewar.Instruction.OP_CODE.DAT;

public class Instruction
{
    private OP_CODE operation;
    private MODIFIER modifier;
    private ADDR_MODE modeA;
    private int fieldA;
    private ADDR_MODE modeB;
    private int fieldB;

    public enum OP_CODE
    {
        DAT,
        MOV;
    }

    public enum MODIFIER
    {
        A,
        B,
        AB,
        BA,
        F,
        X,
        I;
    }

    public enum ADDR_MODE
    {
        IMM,
        DIR,
        IND;
    }

    public Instruction(OP_CODE operation, MODIFIER modifier, ADDR_MODE modeA, int fieldA, ADDR_MODE modeB, int fieldB)
    {
        this.operation  = operation;
        this.modifier = modifier;
        this.modeA = modeA;
        this.fieldA = fieldA;
        this.modeB = modeB;
        this.fieldB = fieldB;
    }

    // Getter

    public OP_CODE getOpCode()
    {
        return operation;
    }

    public MODIFIER getModifier()
    {
        return modifier;
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

    // Setter

    public void setA(int fieldA)
    {
        this.fieldA = fieldA;
    }

    public void setB(int fieldB)
    {
        this.fieldB = fieldB;
    }
}
