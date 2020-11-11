# WurMod
Wurm Unlimited modding API and implementation.

# Working With Patches
For this you will need a Java compiler, [Apache Ant](https://ant.apache.org/), [Procyon v0.5.36](http://www.javadecompilers.com/) and git.
1. Decompile `client.jar` and place the resulting directories into `Patches/client`.
2. Execute `apply_patch.bat`. This will generate patched `.java` files in the `client_patched` directory.
3. Make changes to the files, or add your own. Be sure to follow the patch style guide outlined below.
4. Compile WurMod and place the resulting `wurmod.jar` into the `Patches` directory.
5. Copy the `runtime` directory from the root of your Wurm installation into the `Patches` directory.
6. Once you are done, you can execute `ant` to build the patched class files into the `out` directory.
7. Execute `make_patch.bat` to update the `client.patch` file, and commit.

If the patch generated by `make_patch.bat` is gigantic, check the comments in `make_patch.bat`.

### Patch Style Guide
In order to make it clear when looking at the patched code what parts have been updated, all added code should be surrounded by the comment `// WurMod`.

For example;
```java
public SomeConstructor() {
	// WurMod: Optional description of what we are doing.
	this.field = someValue;
	this.field.call();
	// WurMod
}
```
In cases where edits are made in the middle of an existing line, a single comment should be placed above the line with a description of what was changed.

For example;
```java
this.field = new SomeClass();

// Could become...

// WurMod: Pass the value to the class
this.field = new SomeClass(ourValue);
```
