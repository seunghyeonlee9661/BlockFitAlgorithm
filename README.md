# 🧩 Block Fitting Puzzle Solver

![제목을 입력해주세요_-001 (6)](https://github.com/seunghyeonlee9661/BlockFitAlgorithm/assets/101535408/495c38e3-1c99-48c3-a7bf-17c3cbc6e2ee)

This Java project aims to solve a block fitting puzzle where various blocks must be placed on a grid without overlapping. The goal is to place all the blocks on the grid, ensuring that no two blocks occupy the same space.

## 🌟 Overview

The main features of this project include:

1. **Stage Initialization**:
    - Initializes a grid (referred to as the stage) of a given size.
    - Prints the initial empty stage.

2. **Block List**:
    - Creates a list of blocks, each represented as a 2D array.
    - Adds various blocks to the list.

3. **Block Placement Solver**:
    - Recursively attempts to place each block on the stage.
    - Checks all possible rotations (0, 90, 180, 270 degrees) for each block.
    - Verifies if a block can be placed at a given position without overlapping other blocks.

4. **Utility Methods**:
    - Copies the current state of the stage.
    - Places a block on the stage.
    - Rotates a block 90 degrees clockwise.
    - Removes a block from the list once it has been placed.
    - Checks if a block can be placed at a specific position.

5. **Output**:
    - Prints the current state of the stage.
    - Prints the list of blocks (for debugging purposes).
    - Prints a single block (for debugging purposes).

## 📜 Code Explanation
### 🔧 Main Class
Initializes the stage, creates a list of blocks, and attempts to solve the puzzle.
```java
public class Main {
    int[][] answer;

    public static void main(String[] args) {
        // Initialize the stage (4x4 grid) and print it
        int[][] stage = initializeStage(4, 4);
        printStage(stage);

        // Create a list of blocks
        ArrayList<int[][]> blocks = new ArrayList<>();

        // Add various blocks to the list
        blocks.add(new int[][] { { 3, 3 }, { 0, 3 }, { 0, 3 } });
        blocks.add(new int[][] { { 4, 4 }, { 0, 4 }, { 0, 4 } });
        blocks.add(new int[][] { { 2, 2 } });
        blocks.add(new int[][] { { 1, 1 }, { 1, 1 } });
        blocks.add(new int[][] { { 5, 5 } });

        // Attempt to solve the stage with the blocks
        if (!solve(stage, blocks)) {
            System.out.println("Impossible");
        }
    }
}
```

### 🧩 solve Method

Solves the block fitting problem using recursion and backtracking.
```java
    // Solve the block fitting problem using recursion
    private static boolean solve(int[][] stage, ArrayList<int[][]> blocks) {
        if (blocks.size() == 0) {
            for (int[] row : stage)
                for (int i : row)
                    if (i == 0)
                        return false;
            printStage(stage);
            return true;
        }
        for (int h = 0; h < stage.length; h++) {
            for (int w = 0; w < stage[h].length; w++) {
                for (int i = 0; i < blocks.size(); i++) {
                    for (int r = 0; r < 4; r++) {
                        blocks.set(i, rotateBlock(blocks.get(i)));
                        if (canPlaceBlock(h, w, stage, blocks.get(i))) {
                            if (solve(placeBlock(h, w, copyStage(stage), blocks.get(i)), removeBlock(blocks, i))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
```
### 📋 copyStage Method
Creates a copy of the current state of the stage.
```java
    // Create a copy of the stage
    private static int[][] copyStage(int[][] stage) {
        int[][] newStage = new int[stage.length][];
        for (int i = 0; i < stage.length; i++) {
            newStage[i] = stage[i].clone();
        }
        return newStage;
    }
```

### 🏗️ placeBlock Method
Places a block on the stage at a specified position.
```
    // Place the block on the stage
    private static int[][] placeBlock(int h, int w, int[][] stage, int[][] block) {
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                if (block[i][j] > 0)
                    stage[h + i][w + j] = block[i][j];
            }
        }
        return stage;
    }
```

### 🔄 rotateBlock Method
Rotates a block 90 degrees clockwise.
```java
    // Rotate the block 90 degrees clockwise
    private static int[][] rotateBlock(int[][] block) {
        int[][] rotated = new int[block[0].length][block.length];
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                rotated[j][block.length - i - 1] = block[i][j];
            }
        }
        return rotated;
    }
```

### ➖ removeBlock Method
Removes a block from the list after it has been placed on the stage.
```java
    // Remove the block from the list
    private static ArrayList<int[][]> removeBlock(ArrayList<int[][]> blocks, int removeIndex) {
        ArrayList<int[][]> newBlocks = new ArrayList<>();
        int i = 0;
        for (int[][] block : blocks) {
            if (i != removeIndex) {
                newBlocks.add(block);
            }
            i++;
        }
        return newBlocks;
    }
```

### ✅ canPlaceBlock Method
Checks if a block can be placed at a given position on the stage without overlapping other blocks.
```java
    // Check if the block can be placed at the given position
    private static boolean canPlaceBlock(int h, int w, int[][] stage, int[][] block) {
        if (h + block.length > stage.length || w + block[0].length > stage[0].length)
            return false;
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                if (block[i][j] > 0 && stage[h + i][w + j] > 0) {
                    return false;
                }
            }
        }
        return true;
    }
```

### 🛠️ initializeStage Method
Initializes the stage with the given dimensions.
```java
    // Initialize the stage with the given dimensions
    private static int[][] initializeStage(int width, int height) {
        return new int[width][height];
    }
```

### 🖨️ printStage Method
Prints the current state of the stage to the console.
```java
    // Print the current state of the stage
    private static void printStage(int[][] stage) {
        for (int i = 0; i < stage.length; i++) {
            System.out.print("|");
            for (int j = 0; j < stage[i].length; j++) {
                System.out.printf("%2d|", stage[i][j]);
            }
            System.out.println();
        }
    }
```

### 🖨️ printBlocks Method
Prints the list of blocks for debugging purposes.
```java
    // Print the list of blocks (for debugging purposes)
    private static void printBlocks(ArrayList<int[][]> blocks) {
        System.out.println();
        for (int[][] block : blocks) {
            printBlock(block);
            System.out.println();
        }
    }
```

### 🖨️ printBlock Method
Prints a single block for debugging purposes.
```java
    // Print the list of blocks (for debugging purposes)
    private static void printBlocks(ArrayList<int[][]> blocks) {
        System.out.println();
        for (int[][] block : blocks) {
            printBlock(block);
            System.out.println();
        }
    }
```
