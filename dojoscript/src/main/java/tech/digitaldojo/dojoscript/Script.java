/*
 * Copyright (c) 2022 - present | LuciferMorningstarDev <contact@lucifer-morningstar.dev>
 * Copyright (C) 2022 - present | digitaldojo.tech team and contributors
 * Copyright (C) 2022 - present | Pharaoh & Morningstar LLC team and contributors
 *
 * ▓█████▄  ▒█████   ▄▄▄██▀▀▀▒█████    ██████  ▄████▄   ██▀███   ██▓ ██▓███  ▄▄▄█████▓
 * ▒██▀ ██▌▒██▒  ██▒   ▒██  ▒██▒  ██▒▒██    ▒ ▒██▀ ▀█  ▓██ ▒ ██▒▓██▒▓██░  ██▒▓  ██▒ ▓▒
 * ░██   █▌▒██░  ██▒   ░██  ▒██░  ██▒░ ▓██▄   ▒▓█    ▄ ▓██ ░▄█ ▒▒██▒▓██░ ██▓▒▒ ▓██░ ▒░
 * ░▓█▄   ▌▒██   ██░▓██▄██▓ ▒██   ██░  ▒   ██▒▒▓▓▄ ▄██▒▒██▀▀█▄  ░██░▒██▄█▓▒ ▒░ ▓██▓ ░
 * ░▒████▓ ░ ████▓▒░ ▓███▒  ░ ████▓▒░▒██████▒▒▒ ▓███▀ ░░██▓ ▒██▒░██░▒██▒ ░  ░  ▒██▒ ░
 *  ▒▒▓  ▒ ░ ▒░▒░▒░  ▒▓▒▒░  ░ ▒░▒░▒░ ▒ ▒▓▒ ▒ ░░ ░▒ ▒  ░░ ▒▓ ░▒▓░░▓  ▒▓▒░ ░  ░  ▒ ░░
 *  ░ ▒  ▒   ░ ▒ ▒░  ▒ ░▒░    ░ ▒ ▒░ ░ ░▒  ░ ░  ░  ▒     ░▒ ░ ▒░ ▒ ░░▒ ░         ░
 *  ░ ░  ░ ░ ░ ░ ▒   ░ ░ ░  ░ ░ ░ ▒  ░  ░  ░  ░          ░░   ░  ▒ ░░░         ░
 *    ░        ░ ░   ░   ░      ░ ░        ░  ░ ░         ░      ░
 *  ░                                         ░
 *
 * DojoScript - Developed with ♥ and Published by digitaldojo.tech
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package tech.digitaldojo.dojoscript;

/**
 * dojoscript-java; tech.digitaldojo.dojoscript:Script
 *
 * @since 16.11.2022
 */

import tech.digitaldojo.dojoscript.value.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Compiled representation of dojoscript code that can be run inside {@link DojoScript}.
 */
public class Script {

    public final List<Value> constants = new ArrayList<>();
    public byte[] code = new byte[8];
    private int size;

    private void write(int b) {
        if (size >= code.length) {
            byte[] newCode = new byte[code.length * 2];
            System.arraycopy(code, 0, newCode, 0, code.length);
            code = newCode;
        }

        code[size++] = (byte) b;
    }

    /**
     * Writes instruction to this script.
     */
    public void write(Instruction insn) {
        write(insn.ordinal());
    }

    /**
     * Writes instruction with an additional byte to this script.
     */
    public void write(Instruction insn, int b) {
        write(insn.ordinal());
        write(b);
    }

    /**
     * Writes instruction with an additional constant value to this script.
     */
    public void write(Instruction insn, Value constant) {
        write(insn.ordinal());
        writeConstant(constant);
    }

    /**
     * Writes constant value to this script.
     */
    public void writeConstant(Value constant) {
        int constantI = -1;

        for (int i = 0; i < constants.size(); i++) {
            if (constants.get(i).equals(constant)) {
                constantI = i;
                break;
            }
        }

        if (constantI == -1) {
            constantI = constants.size();
            constants.add(constant);
        }

        write(constantI);
    }

    /**
     * Begins a jump instruction.
     */
    public int writeJump(Instruction insn) {
        write(insn);
        write(0);
        write(0);

        return size - 2;
    }

    /**
     * Ends a jump instruction.
     */
    public void patchJump(int offset) {
        int jump = size - offset - 2;

        code[offset] = (byte) ((jump >> 8) & 0xFF);
        code[offset + 1] = (byte) (jump & 0xFF);
    }

    // Decompilation

    /**
     * Decompiles this script and writes it to {@link System#out}.
     */
    public void decompile() {
        for (int i = 0; i < size; i++) {
            Instruction insn = Instruction.valueOf(code[i]);
            System.out.format("%3d %-18s", i, insn);

            switch (insn) {
                case AddConstant:
                case Variable:
                case VariableAppend:
                case Get:
                case GetAppend:
                case Constant:
                case ConstantAppend:
                    i++;
                    System.out.format("%3d '%s'", code[i], constants.get(code[i]));
                    break;
                case Call:
                case CallAppend:
                    i++;
                    System.out.format("%3d %s", code[i], code[i] == 1 ? "argument" : "arguments");
                    break;
                case Jump:
                case JumpIfTrue:
                case JumpIfFalse:
                    i += 2;
                    System.out.format("%3d -> %d", i - 2, i + 1 + (((code[i - 1] << 8) & 0xFF) | (code[i] & 0xFF)));
                    break;
                case Section:
                    i++;
                    System.out.format("%3d", code[i]);
                    break;
                case VariableGet:
                case VariableGetAppend:
                    i += 2;
                    System.out.format("%3d.%-3d '%s.%s'", code[i - 1], code[i], constants.get(code[i - 1]), constants.get(code[i]));
                    break;
            }

            System.out.println();
        }
    }

}
