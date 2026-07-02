# AGENTS.md - P11-12interface Codebase Guide

## Project Overview
This is an educational Kotlin project demonstrating **interface implementation**. It's part of a structured learning curriculum for Kotlin programming (Series 115, lesson KT_4).

## Architecture & Key Patterns

### Core Pattern: Interface with Default Implementation
- **File**: `src/interface.kt`
- **Pattern**: The `IntB` interface defines both properties and a concrete method (`show()`)
- **Implementation**: `Report` class implements the interface using `override` for properties
- **Why This Matters**: Showcases Kotlin's interface flexibility - interfaces can contain both abstract and concrete members (default method implementations)

```kotlin
interface IntB {
    var math: Int      // Abstract properties
    var eng: Int
    
    fun show() {       // Concrete method with default implementation
        println("math = $math, eng = $eng")
    }
}

class Report(override var math: Int = 0, override var eng: Int = 0) : IntB
```

## Running & Testing

### Execute the Program
```bash
# In project root with Kotlin installed
kotlinc src/interface.kt -include-runtime -d interface.jar
java -jar interface.jar

# Output: math = 95, eng = 88
```

### In IntelliJ IDEA
- Click the green play button next to `fun main()` in `interface.kt`
- Or use keyboard shortcut: `Ctrl+Shift+F10`

## Project Structure
- **`src/interface.kt`**: Single file containing interface definition, implementation class, and main function
- **`P11-12interface.iml`**: IntelliJ IDEA project metadata
- **`.idea/`**: IDE configuration

## Key Conventions
1. **Single File Organization**: All code in one file (appropriate for learning projects)
2. **Property Initialization**: Constructor parameters directly initialize overridden properties
3. **Kotlin Conventions**: Uses `var` for mutable properties, backticks for string interpolation in `println()`

## Modification Points for Learning
- Add more properties to `IntB` interface and have `Report` implement them
- Add additional concrete methods to the interface
- Create additional classes implementing `IntB` with different behavior
- Extend the `main()` function to test multiple implementations

## No External Dependencies
This project uses only Kotlin stdlib - no external dependencies to manage.

