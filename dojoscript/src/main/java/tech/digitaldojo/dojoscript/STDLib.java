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

import tech.digitaldojo.dojoscript.value.Value;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * dojoscript-java; tech.digitaldojo.dojoscript:STDLib
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 16.11.2022
 */
public class STDLib {

    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static final Random rand = new Random();

    /**
     * Adds the functions and variables to the provided {@link DojoScript} instance.
     */
    public static void init(DojoScript ds) {

        // Variables
        ds.set("PI", Math.PI);
        ds.set("time", () -> Value.string(timeFormat.format(new Date())));
        ds.set("date", () -> Value.string(dateFormat.format(new Date())));

        // Numbers
        ds.set("round", STDLib::round);
        ds.set("roundToString", STDLib::roundToString);
        ds.set("floor", STDLib::floor);
        ds.set("ceil", STDLib::ceil);
        ds.set("abs", STDLib::abs);
        ds.set("random", STDLib::random);

        // Strings
        ds.set("string", STDLib::string);
        ds.set("toUpper", STDLib::toUpper);
        ds.set("toLower", STDLib::toLower);
        ds.set("contains", STDLib::contains);
        ds.set("replace", STDLib::replace);
        ds.set("pad", STDLib::pad);

    }

    // Numbers

    public static Value round(DojoScript ds, int argCount) {
        if (argCount == 1) {
            double a = ds.popNumber("Argument to round() needs to be a number.");
            return Value.number(Math.round(a));
        } else if (argCount == 2) {
            double b = ds.popNumber("Second argument to round() needs to be a number.");
            double a = ds.popNumber("First argument to round() needs to be a number.");

            double x = Math.pow(10, (int) b);
            return Value.number(Math.round(a * x) / x);
        } else {
            ds.error("round() requires 1 or 2 arguments, got %d.", argCount);
            return null;
        }
    }

    public static Value roundToString(DojoScript ds, int argCount) {
        if (argCount == 1) {
            double a = ds.popNumber("Argument to round() needs to be a number.");
            return Value.string(Double.toString(Math.round(a)));
        } else if (argCount == 2) {
            double b = ds.popNumber("Second argument to round() needs to be a number.");
            double a = ds.popNumber("First argument to round() needs to be a number.");

            double x = Math.pow(10, (int) b);
            return Value.string(Double.toString(Math.round(a * x) / x));
        } else {
            ds.error("round() requires 1 or 2 arguments, got %d.", argCount);
            return null;
        }
    }

    public static Value floor(DojoScript ds, int argCount) {
        if (argCount != 1) {
            ds.error("floor() requires 1 argument, got %d.", argCount);
        }
        double a = ds.popNumber("Argument to floor() needs to be a number.");
        return Value.number(Math.floor(a));
    }

    public static Value ceil(DojoScript ds, int argCount) {
        if (argCount != 1) {
            ds.error("ceil() requires 1 argument, got %d.", argCount);
        }
        double a = ds.popNumber("Argument to ceil() needs to be a number.");
        return Value.number(Math.ceil(a));
    }

    public static Value abs(DojoScript ds, int argCount) {
        if (argCount != 1) {
            ds.error("abs() requires 1 argument, got %d.", argCount);
        }
        double a = ds.popNumber("Argument to abs() needs to be a number.");
        return Value.number(Math.abs(a));
    }

    public static Value random(DojoScript ds, int argCount) {
        if (argCount == 0) {
            return Value.number(rand.nextDouble());
        } else if (argCount == 2) {
            double max = ds.popNumber("Second argument to random() needs to be a number.");
            double min = ds.popNumber("First argument to random() needs to be a number.");

            return Value.number(min + (max - min) * rand.nextDouble());
        }

        ds.error("random() requires 0 or 2 arguments, got %d.", argCount);
        return Value.null_();
    }

    // Strings

    private static Value string(DojoScript ds, int argCount) {
        if (argCount != 1) {
            ds.error("string() requires 1 argument, got %d.", argCount);
        }
        return Value.string(ds.pop().toString());
    }

    public static Value toUpper(DojoScript ds, int argCount) {
        if (argCount != 1) {
            ds.error("toUpper() requires 1 argument, got %d.", argCount);
        }
        String a = ds.popString("Argument to toUpper() needs to be a string.");
        return Value.string(a.toUpperCase());
    }

    public static Value toLower(DojoScript ds, int argCount) {
        if (argCount != 1) {
            ds.error("toLower() requires 1 argument, got %d.", argCount);
        }
        String a = ds.popString("Argument to toLower() needs to be a string.");
        return Value.string(a.toLowerCase());
    }

    public static Value contains(DojoScript ds, int argCount) {
        if (argCount != 2) {
            ds.error("replace() requires 2 arguments, got %d.", argCount);
        }

        String search = ds.popString("Second argument to contains() needs to be a string.");
        String string = ds.popString("First argument to contains() needs to be a string.");

        return Value.bool(string.contains(search));
    }

    public static Value replace(DojoScript ds, int argCount) {
        if (argCount != 3) {
            ds.error("replace() requires 3 arguments, got %d.", argCount);
        }

        String to = ds.popString("Third argument to replace() needs to be a string.");
        String from = ds.popString("Second argument to replace() needs to be a string.");
        String string = ds.popString("First argument to replace() needs to be a string.");

        return Value.string(string.replace(from, to));
    }

    public static Value pad(DojoScript ds, int argCount) {
        if (argCount != 2) {
            ds.error("pad() requires 2 arguments, got %d.", argCount);
        }

        int width = (int) ds.popNumber("Second argument to pad() needs to be a number.");
        String text = ds.pop().toString();

        if (text.length() >= Math.abs(width)) {
            return Value.string(text);
        }

        char[] padded = new char[Math.max(text.length(), Math.abs(width))];

        if (width >= 0) {
            int padLength = width - text.length();
            for (int i = 0; i < padLength; i++) {
                padded[i] = ' ';
            }
            for (int i = 0; i < text.length(); i++) {
                padded[padLength + i] = text.charAt(i);
            }
        } else {
            for (int i = 0; i < text.length(); i++) {
                padded[i] = text.charAt(i);
            }
            for (int i = 0; i < Math.abs(width) - text.length(); i++) {
                padded[text.length() + i] = ' ';
            }
        }

        return Value.string(new String(padded));
    }

}