# AGENTS.md - AI Coding Agent Guidelines for 115iot-mobile C Project

## Project Overview
This is a C/C++ learning repository for IoT/mobile programming exercises. Organized by date folders (e.g., `0312/`, `0414/`), containing assignments (`A01.c` to `AXX.c`), homework (`HW1.c`), and compiled executables (`.exe`). Focuses on fundamental C concepts with occasional C++ usage.

## Coding Conventions
- **Entry Point**: Use `int main(void)` for all programs.
- **Headers**: Include `<stdio.h>` and `<stdlib.h>` in every file, even if not fully utilized.
- **Input/Output**: Use `scanf()` for user input and `printf()` for output. Format specifiers: `%d` for int, `%f` for float, `%lf` for double, `%c` for char, `%s` for strings.
- **Console Persistence**: End programs with `system("pause");` to prevent Windows console from closing immediately.
- **Variable Declaration**: Declare variables at function start, e.g., `int n1=8, n2=9; float num1=3.02f;`.
- **String Handling**: Initialize strings as char arrays, e.g., `char str[15] = "Hello world\n";` or manually assign indices.
- **Comments**: Include YouTube video links for reference, e.g., `// https://youtu.be/AiNqE_dva4Q`.
- **File Naming**: Assignments as `AXX.c` (XX is number), homework as `HWX.c` or in `homework/` subfolders.

## Build and Run Workflow
- **Compilation**: Use GCC for C files: `gcc filename.c -o filename.exe`. For C++: `g++ filename.cpp -o filename.exe`.
- **Execution**: Run `.exe` files directly in terminal. No build scripts; compile individually per file.
- **Debugging**: Print intermediate values with `printf()`; no integrated debugger setup observed.
- **Dependencies**: No external libraries; relies on standard C library.

## Key Patterns and Examples
- **Basic Output**: Simple `printf("Hello C!\n");` with `system("pause");` (see `0312/HelloC.c`).
- **Formatted Printing**: Use precision for floats, e.g., `printf("%5.2f\n", num);` (see `0316/A01.c`).
- **User Input**: `scanf("%d", &num);` for integers, `%lf` for doubles (see `0422/A01.c`).
- **Functions**: Define helper functions like `double get_score(void)` for modularity (see `0422/A01.c`).
- **Conditionals**: Graded logic with if-else chains for scoring (see `0422/A01.c`).
- **Strings**: Manual char array manipulation vs. string literals (see `0414/A01.c`).
- **System Calls**: `system("color Ae");` for console coloring (see `0312/homework/EX1.c`).

## Integration Points
- No external services or APIs; standalone console applications.
- Occasional C++ files (`.cpp`) for mixed learning, but primarily C-focused.

Reference: `0312/HelloC.c`, `0414/A01.c`, `0422/A01.c` for core patterns.
