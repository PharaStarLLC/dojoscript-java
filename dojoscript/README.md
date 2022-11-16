#### Java Usage:

```java
package com.mysite.myapp;

import tech.digitaldojo.dojoscript.DojoScript;
import tech.digitaldojo.dojoscript.STDLib;
import tech.digitaldojo.dojoscript.compiler.Parser;
import tech.digitaldojo.dojoscript.utils.Error;

class MyClass {

    public static void main(String[] args) {
        // Parse
        Parser.Result result = Parser.parse("Hello {name}!");
        
        // Check for errors
        if (result.hasErrors()) {
            for (Error error : result.errors) {
                System.out.println(error);
            }
            return;
        }

        // Compile
        Script script = Compiler.compile(result);

        // Create dojoscript instance
        DojoScript ds = new DojoScript();
        STDLib.init(ds); // Adds a few default functions, (not required)

        ds.set("name", "LuciferMorningstarDev");
        // or: ds.set("name", () -> Value.string("LuciferMorningstarDev"));

        // Run
        System.out.println(ds.run(script)); // Hello LuciferMorningstarDev!
    }

}
```
