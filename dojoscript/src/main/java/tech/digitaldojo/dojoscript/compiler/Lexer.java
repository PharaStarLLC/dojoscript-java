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
 * dojoscript-java; tech.digitaldojo.dojoscript.compiler:Lexer
 *
 * @since 16.11.2022
 */

/** Takes dojoscript source code and produces a stream or tokens that are used for parsing. */
public class Lexer {

    private final String source;
    /** The type of the token. */
    public Token token;
    /** The string representation of the token. */
    public String lexeme;
    public int line = 1, character = -1;
    public char ch;
    public int start, current;
    private int expressionDepth;

    public Lexer(String source) {
        this.source = source;
    }

    /** Scans for next token storing it in {@link Lexer#token} and {@link Lexer#lexeme}. Produces {@link Token#EOF} if the end of source code has been reached and {@link Token#Error} if there has been an error. */
    public void next() {
        start = current;

        if (isAtEnd()) {
            createToken(Token.EOF);
            return;
        }

        if (expressionDepth > 0) {
            // Scan expression
            skipWhitespace();
            if (isAtEnd()) {
                createToken(Token.EOF);
                return;
            }

            char c = advance();

            if (isDigit(c) || (c == '-' && isDigit(peek()))) {
                number();
            } else if (isAlpha(c)) {
                identifier();
            } else {
                switch (c) {
                    case '\'':
                    case '"':
                        string();
                        break;

                    case '=':
                        if (match('=')) {
                            createToken(Token.EqualEqual);
                        } else {
                            unexpected();
                        }
                        break;
                    case '!':
                        createToken(match('=') ? Token.BangEqual : Token.Bang);
                        break;
                    case '>':
                        createToken(match('=') ? Token.GreaterEqual : Token.Greater);
                        break;
                    case '<':
                        createToken(match('=') ? Token.LessEqual : Token.Less);
                        break;

                    case '+':
                        createToken(Token.Plus);
                        break;
                    case '-':
                        createToken(Token.Minus);
                        break;
                    case '*':
                        createToken(Token.Star);
                        break;
                    case '/':
                        createToken(Token.Slash);
                        break;
                    case '%':
                        createToken(Token.Percentage);
                        break;
                    case '^':
                        createToken(Token.UpArrow);
                        break;

                    case '.':
                        createToken(Token.Dot);
                        break;
                    case ',':
                        createToken(Token.Comma);
                        break;
                    case '?':
                        createToken(Token.QuestionMark);
                        break;
                    case ':':
                        createToken(Token.Colon);
                        break;
                    case '(':
                        createToken(Token.LeftParen);
                        break;
                    case ')':
                        createToken(Token.RightParen);
                        break;
                    case '{':
                        expressionDepth++;
                        createToken(Token.LeftBrace);
                        break;
                    case '}':
                        expressionDepth--;
                        createToken(Token.RightBrace);
                        break;

                    case '#':
                        while (isDigit(peek())) {
                            advance();
                        }
                        createToken(Token.Section, source.substring(start + 1, current));
                        break;

                    default:
                        unexpected();
                }
            }
        } else {
            // Scan string, start an expression or section
            char c = advance();
            if (c == '\n') {
                line++;
            }

            if (c == '{') {
                expressionDepth++;
                createToken(Token.LeftBrace);
            } else if (c == '#') {
                while (isDigit(peek())) {
                    advance();
                }
                createToken(Token.Section, source.substring(start + 1, current));
            } else {
                while (!isAtEnd() && peek() != '{' && peek() != '#') {
                    if (peek() == '\n') {
                        line++;
                    }
                    advance();
                }

                createToken(Token.String);
            }
        }
    }

    private void string() {
        while (!isAtEnd() && peek() != '"' && peek() != '\'') {
            if (peek() == '\n') {
                line++;
            }
            advance();
        }

        if (isAtEnd()) {
            createToken(Token.Error, "Unterminated expression.");
        } else {
            advance();
            createToken(Token.String, source.substring(start + 1, current - 1));
        }
    }

    private void number() {
        while (isDigit(peek())) {
            advance();
        }

        if (peek() == '.' && isDigit(peekNext())) {
            advance();

            while (isDigit(peek())) {
                advance();
            }
        }

        createToken(Token.Number);
    }

    private void identifier() {
        while (!isAtEnd() && isAlphaNumeric(peek())) {
            advance();
        }

        createToken(Token.Identifier);

        switch (lexeme) {
            case "null":
                token = Token.Null;
                break;
            case "true":
                token = Token.True;
                break;
            case "false":
                token = Token.False;
                break;
            case "and":
                token = Token.And;
                break;
            case "or":
                token = Token.Or;
                break;
        }
    }

    private void skipWhitespace() {
        while (true) {
            if (isAtEnd()) {
                return;
            }
            char c = peek();

            switch (c) {
                case ' ':
                case '\r':
                case '\t':
                    advance();
                    break;
                case '\n':
                    line++;
                    advance();
                    break;
                default:
                    start = current;
                    return;
            }
        }
    }

    // Helpers

    private void unexpected() {
        createToken(Token.Error, "Unexpected character.");
    }

    private void createToken(Token token, String lexeme) {
        this.token = token;
        this.lexeme = lexeme;
    }

    private void createToken(Token token) {
        createToken(token, source.substring(start, current));
    }

    private boolean match(char expected) {
        if (isAtEnd()) {
            return false;
        }
        if (source.charAt(current) != expected) {
            return false;
        }

        advance();
        return true;
    }

    private char advance() {
        character++;
        return ch = source.charAt(current++);
    }

    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) {
            return '\0';
        }
        return source.charAt(current + 1);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

}
