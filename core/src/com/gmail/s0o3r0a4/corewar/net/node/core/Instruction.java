package com.gmail.s0o3r0a4.corewar.net.node.core;

public class Instruction {
    private OP_CODE operation;
    private MODIFIER modifier;
    private ADDR_MODE modeA;
    private int fieldA;
    private ADDR_MODE modeB;
    private int fieldB;

    public enum OP_CODE {
        DAT,
        MOV,
        ADD, SUB, MUL, DIV, MOD,
        JMP, JMZ, JMN, DJN,
        SPL,
        SEQ, SNE, CMP, SLT,
        NOP,
        IN,
        OUT,
        CON,
        DCN;
    }

    public enum MODIFIER {
        A,
        B,
        AB,
        BA,
        F,
        X,
        I;
    }

    public enum ADDR_MODE {
        IMM,
        DIR,
        IND;
    }

    public Instruction(OP_CODE operation, MODIFIER modifier, ADDR_MODE modeA, int fieldA, ADDR_MODE modeB, int fieldB) {
        this.operation = operation;
        this.modifier = modifier;
        this.modeA = modeA;
        this.fieldA = fieldA;
        this.modeB = modeB;
        this.fieldB = fieldB;
    }

    public Instruction() {
        this.operation = OP_CODE.DAT;
        this.modifier = MODIFIER.F;
        this.modeA = ADDR_MODE.DIR;
        this.fieldA = 0;
        this.modeB = ADDR_MODE.DIR;
        this.fieldB = 0;
    }

    public static Instruction newInstance(Instruction instruction) {
        return new Instruction(instruction.getOpCode(), instruction.getModifier(), instruction.getModeA(),
                instruction.getFieldA(), instruction.getModeB(), instruction.getFieldB());
    }


    // Getter

    public OP_CODE getOpCode() {
        return operation;
    }

    public MODIFIER getModifier() {
        return modifier;
    }

    public ADDR_MODE getModeA() {
        return modeA;
    }

    public int getFieldA() {
        return fieldA;
    }

    public ADDR_MODE getModeB() {
        return modeB;
    }

    public int getFieldB() {
        return fieldB;
    }

    // Setter

    public void setA(ADDR_MODE modeA, int fieldA) {
        this.modeA = modeA;
        this.fieldA = fieldA;
    }

    public void setA(int fieldA) {
        this.fieldA = fieldA;
    }

    public void setB(ADDR_MODE modeB, int fieldB) {
        this.modeB = modeB;
        this.fieldB = fieldB;
    }

    public void setB(int fieldB) {
        this.fieldB = fieldB;
    }
}
