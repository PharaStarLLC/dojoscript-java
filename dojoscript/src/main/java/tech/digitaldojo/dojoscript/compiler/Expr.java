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

package tech.digitaldojo.dojoscript.compiler;

/**
 * dojoscript-java; tech.digitaldojo.dojoscript.compiler:Expr
 *
 * @since 16.11.2022
 */

import java.util.List;
import java.util.function.Consumer;

/**
 * Expressions that form the AST (abstract syntax tree) of parsed dojoscript code.
 */
public abstract class Expr {

    public final int start, end;

    public Expr(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public abstract void accept(Visitor visitor);

    public java.lang.String getSource(java.lang.String source) {
        return source.substring(start, end);
    }

    public void forEach(Consumer<Expr> consumer) {
    }

    public interface Visitor {

        void visitNull(Null expr);

        void visitString(String expr);

        void visitNumber(Number expr);

        void visitBool(Bool expr);

        void visitBlock(Block expr);

        void visitGroup(Group expr);

        void visitBinary(Binary expr);

        void visitUnary(Unary expr);

        void visitVariable(Variable expr);

        void visitGet(Get expr);

        void visitCall(Call expr);

        void visitLogical(Logical expr);

        void visitConditional(Conditional expr);

        void visitSection(Section expr);

    }

    public static class Null extends Expr {

        public Null(int start, int end) {
            super(start, end);
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitNull(this);
        }

    }

    public static class String extends Expr {

        public final java.lang.String string;

        public String(int start, int end, java.lang.String string) {
            super(start, end);

            this.string = string;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitString(this);
        }

    }

    public static class Number extends Expr {

        public final double number;

        public Number(int start, int end, double number) {
            super(start, end);

            this.number = number;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitNumber(this);
        }

    }

    public static class Bool extends Expr {

        public final boolean bool;

        public Bool(int start, int end, boolean bool) {
            super(start, end);

            this.bool = bool;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitBool(this);
        }

    }

    public static class Block extends Expr {

        public final Expr expr;

        public Block(int start, int end, Expr expr) {
            super(start, end);

            this.expr = expr;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitBlock(this);
        }

        @Override
        public void forEach(Consumer<Expr> consumer) {
            if (expr != null) {
                consumer.accept(expr);
            }
        }

    }

    public static class Group extends Expr {

        public final Expr expr;

        public Group(int start, int end, Expr expr) {
            super(start, end);

            this.expr = expr;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitGroup(this);
        }

        @Override
        public void forEach(Consumer<Expr> consumer) {
            consumer.accept(expr);
        }

    }

    public static class Binary extends Expr {

        public final Expr left;
        public final Token op;
        public final Expr right;

        public Binary(int start, int end, Expr left, Token op, Expr right) {
            super(start, end);

            this.left = left;
            this.op = op;
            this.right = right;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitBinary(this);
        }

        @Override
        public void forEach(Consumer<Expr> consumer) {
            consumer.accept(left);
            consumer.accept(right);
        }

    }

    public static class Unary extends Expr {

        public final Token op;
        public final Expr right;

        public Unary(int start, int end, Token op, Expr right) {
            super(start, end);

            this.op = op;
            this.right = right;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitUnary(this);
        }

        @Override
        public void forEach(Consumer<Expr> consumer) {
            consumer.accept(right);
        }

    }

    public static class Variable extends Expr {

        public final java.lang.String name;

        public Variable(int start, int end, java.lang.String name) {
            super(start, end);

            this.name = name;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitVariable(this);
        }

    }

    public static class Get extends Expr {

        public final Expr object;
        public final java.lang.String name;

        public Get(int start, int end, Expr object, java.lang.String name) {
            super(start, end);

            this.object = object;
            this.name = name;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitGet(this);
        }

        @Override
        public void forEach(Consumer<Expr> consumer) {
            consumer.accept(object);
        }

    }

    public static class Call extends Expr {

        public final Expr callee;
        public final List<Expr> args;

        public Call(int start, int end, Expr callee, List<Expr> args) {
            super(start, end);

            this.callee = callee;
            this.args = args;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitCall(this);
        }

        @Override
        public void forEach(Consumer<Expr> consumer) {
            consumer.accept(callee);

            for (Expr arg : args) {
                consumer.accept(arg);
            }
        }

    }

    public static class Logical extends Expr {

        public final Expr left;
        public final Token op;
        public final Expr right;

        public Logical(int start, int end, Expr left, Token op, Expr right) {
            super(start, end);

            this.left = left;
            this.op = op;
            this.right = right;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLogical(this);
        }

        @Override
        public void forEach(Consumer<Expr> consumer) {
            consumer.accept(left);
            consumer.accept(right);
        }

    }

    public static class Conditional extends Expr {

        public final Expr condition;
        public final Expr trueExpr;
        public final Expr falseExpr;

        public Conditional(int start, int end, Expr condition, Expr trueExpr, Expr falseExpr) {
            super(start, end);

            this.condition = condition;
            this.trueExpr = trueExpr;
            this.falseExpr = falseExpr;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitConditional(this);
        }

        @Override
        public void forEach(Consumer<Expr> consumer) {
            consumer.accept(condition);
            consumer.accept(trueExpr);
            consumer.accept(falseExpr);
        }

    }

    public static class Section extends Expr {

        public final int index;
        public final Expr expr;

        public Section(int start, int end, int index, Expr expr) {
            super(start, end);

            this.index = index;
            this.expr = expr;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitSection(this);
        }

        @Override
        public void forEach(Consumer<Expr> consumer) {
            consumer.accept(expr);
        }

    }

}
