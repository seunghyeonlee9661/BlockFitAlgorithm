import java.util.ArrayList;

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

	// Create a copy of the stage
	private static int[][] copyStage(int[][] stage) {
		int[][] newStage = new int[stage.length][];
		for (int i = 0; i < stage.length; i++) {
			newStage[i] = stage[i].clone();
		}
		return newStage;
	}

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

	// Initialize the stage with the given dimensions
	private static int[][] initializeStage(int width, int height) {
		return new int[width][height];
	}

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

	// Print the list of blocks (for debugging purposes)
	private static void printBlocks(ArrayList<int[][]> blocks) {
		System.out.println();
		for (int[][] block : blocks) {
			printBlock(block);
			System.out.println();
		}
	}

	// Print a single block (for debugging purposes)
	private static void printBlock(int[][] block) {
		for (int i = 0; i < block.length; i++) {
			System.out.print("|");
			for (int j = 0; j < block[i].length; j++) {
				if (block[i][j] > 0)
					System.out.printf("%s|", "■");
				else
					System.out.printf("%s|", " ");
			}
			System.out.println();
		}
	}
}
