# AGENTS.md

## Project Overview
This is a collection of simple C programming exercises demonstrating basic concepts like function calls, pass-by-value, and basic I/O. Each `.c` file is a standalone console application.

## Architecture
- **Standalone Programs**: Each `.c` file compiles to an independent `.exe` executable.
- **No Interdependencies**: Programs don't share code or data; they're isolated examples.
- **Standard Libraries Only**: Relies on `<stdio.h>` and `<stdlib.h>` for I/O and utilities.

## Key Patterns
- **Function Declarations**: Declare all functions at the top before `main()`, e.g., `void show(int[]);` in A01.c.
- **Main Function**: Use `void main(void)` signature, as seen in all files.
- **Console Pausing**: End programs with `system("pause");` to keep console window open, e.g., line 14 in A01.c.
- **Simple I/O**: Use `printf()` for output and `scanf()` for input; no advanced formatting beyond basic types.
- **Pass-by-Value**: Functions modify local copies; originals unchanged, demonstrated in A03.c's `add20()` function.

## Developer Workflows
- **Compilation**: Use a C compiler like GCC (via MinGW on Windows) to build: `gcc A01.c -o A01.exe`. Existing `.exe` files are pre-compiled.
- **Execution**: Run executables directly, e.g., `./A01.exe` in terminal.
- **Editing**: Modify `.c` files; recompile to test changes.
- **Incomplete Code**: A04.c has unfinished `scanf()` call (line 8); complete with proper arguments like `scanf("%s", str);`.

## Conventions
- **Includes**: Always include `<stdio.h>` and `<stdlib.h>` at the top.
- **Constants**: Define constants like `const double pi = 3.14;` globally if needed (A01.c).
- **Error Handling**: None implemented; programs assume valid inputs.
- **Code Style**: Simple, procedural; no structs or advanced features.

## Integration Points
- **None**: No external dependencies, APIs, or cross-file communication.
- **Platform**: Windows-specific due to `system("pause")`; may need adjustments for other OS.

Reference files: A01.c (calculations), A03.c (function behavior), A04.c (I/O example).
