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

package tech.digitaldojo.dojoscript.value;

/**
 * dojoscript-java; tech.digitaldojo.dojoscript.value:ValueMap
 *
 * @since 16.11.2022
 */

import tech.digitaldojo.dojoscript.utils.SFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/** Simpler wrapper around a map that goes from {@link String} to {@link Supplier} for {@link Value}. */
public class ValueMap {

    private final Map<String, Supplier<Value>> values = new HashMap<>();

    /** Sets a variable supplier for the provided name. */
    public ValueMap set(String name, Supplier<Value> supplier) {
        values.put(name, supplier);
        return this;
    }

    /** Sets a variable supplier that always returns the same value for the provided name. */
    public ValueMap set(String name, Value value) {
        set(name, () -> value);
        return this;
    }

    /** Sets a boolean variable supplier that always returns the same value for the provided name. */
    public ValueMap set(String name, boolean bool) {
        return set(name, Value.bool(bool));
    }

    /** Sets a number variable supplier that always returns the same value for the provided name. */
    public ValueMap set(String name, double number) {
        return set(name, Value.number(number));
    }

    /** Sets a string variable supplier that always returns the same value for the provided name. */
    public ValueMap set(String name, String string) {
        return set(name, Value.string(string));
    }

    /** Sets a function variable supplier that always returns the same value for the provided name. */
    public ValueMap set(String name, SFunction function) {
        return set(name, Value.function(function));
    }

    /** Sets a map variable supplier that always returns the same value for the provided name. */
    public ValueMap set(String name, ValueMap map) {
        return set(name, Value.map(map));
    }

    /** Gets the variable supplier for the provided name. */
    public Supplier<Value> get(String name) {
        return values.get(name);
    }

    /** Returns a set of all variable names. */
    public Set<String> keys() {
        return values.keySet();
    }

}
