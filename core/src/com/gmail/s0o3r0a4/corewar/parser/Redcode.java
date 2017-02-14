package com.gmail.s0o3r0a4.corewar.parser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;
import com.gmail.s0o3r0a4.corewar.net.node.core.Instruction;

import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.ADDR_MODE.INDA;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.ADDR_MODE.PDI;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.ADDR_MODE.PDIA;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.ADDR_MODE.PII;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.ADDR_MODE.PIIA;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.OP_CODE.DAT;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.ADDR_MODE.IMM;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.ADDR_MODE.DIR;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.ADDR_MODE.IND;
import static com.gmail.s0o3r0a4.corewar.net.node.core.Instruction.MODIFIER.F;

public class Redcode {


    public Redcode() {

    }

    public static Array<Instruction> parse(String path) {
        FileHandle file = Gdx.files.internal(path);
        String content = file.readString();
        Array<String> lines = new Array<String>();
        lines.addAll(content.split("\n"));
        Array<Instruction> core = new Array<Instruction>();

        for (int i = 0; i < lines.size; i++) {
            Array<String> uncomment = new Array<String>();
            uncomment.addAll(lines.get(i).split(";"));

            Array<String> tokens = new Array<String>();
            String delims = "[ \\t,\n]+|^[;].*";

            String label = "";
            String opStr = "";
            String modifierStr = "";
            String firstField = "";
            String secondField = "";

            Instruction.OP_CODE op = null;
            Instruction.MODIFIER modifier = null;
            Instruction.ADDR_MODE mode1 = null;
            int field1;
            Instruction.ADDR_MODE mode2 = null;
            int field2;

//            Pattern reg = Pattern.compile("")
//            Matcher m = reg.matcher(lines.get(i));
//            while (m.find())
//                tokens.add(m.group());

            if (lines.get(i).trim().split("[;]").length != 0) {
                lines.set(i, lines.get(i).trim().split("[;]")[0]);
            }
            tokens.addAll(lines.get(i).split(delims));

            Array<String> opMod = new Array<String>();

            if (tokens.size == 3) {
                opMod.addAll(tokens.get(0).split("\\."));
                firstField = tokens.get(1);
                secondField = tokens.get(2);
            } else if (tokens.size == 4) {
                label = tokens.get(0);
                opMod.addAll(tokens.get(1).split("\\."));
//                opStr = tokens.get(1).toUpperCase();
//                modifierStr = tokens.get(2).toUpperCase();
                firstField = tokens.get(2);
                secondField = tokens.get(3);
            }

            Gdx.app.debug(tokens.toString(), opStr);

            if (opMod.size == 1) {
                opStr = opMod.get(0).toUpperCase();

                switch (Instruction.OP_CODE.valueOf(opStr)) {
                    case MOV:
                    case SEQ:
                    case SNE:
                    case CMP:
                        modifierStr = "I";
                        break;
                    case ADD:
                    case SUB:
                    case MUL:
                    case DIV:
                    case MOD:
                    case NOP:
                    case DAT:
                        modifierStr = "F";
                        break;
                    case JMP:
                    case JMZ:
                    case JMN:
                    case DJN:
                    case SPL:
                    case SLT:
                        modifierStr = "B";
                        break;
                }
//
//                firstField = tokens.get(1);
//                secondField = tokens.get(2);
            } else if (opMod.size == 2) {
                opStr = opMod.get(0).toUpperCase();
                modifierStr = opMod.get(1).toUpperCase();
//                firstField = tokens.get(1);
//                secondField = tokens.get(2);
            }

            try {
                if (tokens.size > 2) {
                    op = Instruction.OP_CODE.valueOf(opStr);
                    modifier = Instruction.MODIFIER.valueOf(modifierStr);

                    switch (firstField.charAt(0)) {
                        case '#':
                            mode1 = IMM;
                            firstField = firstField.replace("#", "");
                            break;

                        case '$':
                            mode1 = DIR;
                            firstField = firstField.replace("$", "");
                            break;

                        case '@':
                            mode1 = IND;
                            firstField = firstField.replace("@", "");
                            break;

                        case '*':
                            mode1 = INDA;
                            firstField = firstField.replace("*", "");
                            break;

                        case '<':
                            mode1 = PDI;
                            firstField = firstField.replace("<", "");
                            break;

                        case '{':
                            mode1 = PDIA;
                            firstField = firstField.replace("{", "");
                            break;

                        case '>':
                            mode1 = PII;
                            firstField = firstField.replace(">", "");
                            break;

                        case '}':
                            mode1 = PIIA;
                            firstField = firstField.replace("}", "");
                            break;

                        default:
                            mode1 = DIR;
                            break;
                    }

                    switch (secondField.charAt(0)) {
                        case '#':
                            mode2 = IMM;
                            secondField = secondField.replace("#", "");
                            break;

                        case '$':
                            mode2 = DIR;
                            secondField = secondField.replace("$", "");
                            break;

                        case '@':
                            mode2 = IND;
                            secondField = secondField.replace("@", "");
                            break;

                        case '*':
                            mode2 = INDA;
                            secondField = secondField.replace("*", "");
                            break;

                        case '<':
                            mode2 = PDI;
                            secondField = secondField.replace("<", "");
                            break;

                        case '{':
                            mode2 = PDIA;
                            secondField = secondField.replace("{", "");
                            break;

                        case '>':
                            mode2 = PII;
                            secondField = secondField.replace(">", "");
                            break;

                        case '}':
                            mode2 = PIIA;
                            secondField = secondField.replace("}", "");
                            break;

                        default:
                            mode2 = DIR;
                            break;
                    }

                    field1 = Integer.valueOf(firstField);
                    field2 = Integer.valueOf(secondField);

                    Instruction instruction = new Instruction(op, modifier, mode1, field1, mode2, field2);
                    core.add(instruction);

                    Gdx.app.debug(op.toString() + "." + modifier.toString(), mode1.toString() + Integer.toString(field1) + " " +
                            mode2.toString() + Integer.toString(field2));
                }
            }
            catch (IllegalArgumentException e) {
                if (op == null){
                    core.add(new Instruction(DAT, F, DIR, 37707, DIR, 1));
                } else if (modifier == null){
                    core.add(new Instruction(DAT, F, DIR, 37707, DIR, 2));
                } else if (mode1 == null) {
                    core.add(new Instruction(DAT, F, DIR, 37707, DIR, 3));
                } else if (mode2 == null) {
                    core.add(new Instruction(DAT, F, DIR, 37707, DIR, 4));
                } else {
                    core.add(new Instruction(DAT, F, DIR, 37707, DIR, 0));
                }
            }
        }
        return core;
    }
}
