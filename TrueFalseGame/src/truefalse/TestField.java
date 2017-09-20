package truefalse;

import java.util.ArrayList;

// a class used to implement “manual unit testing” for Field and Cell
public class TestField {
	public static void main(String[] args) {
		
		// Part 1: Testing Cell's methods
		System.out.println("\nPart 1: Testing Cell's methods");
		System.out.println("----------------------------------------------");
		//Test 1: Test setContent, getContent
		System.out.println("Test 1: getContent(String newContent)");
		Cell c1 = new Cell();
		c1.setContent("T");
		System.out.println("Expected value: T, actual: "+c1.getContent());
		
		// Test 2: Test findXXCellsNearby
		System.out.println("\nNOTE: Using cell index 2-1: array2D[2][1] for testing Cell class");
		System.out.println("\nTest 2: Cell's method: findXXXCellsNearby(...) ");
		// Create an Cell[][]
		System.out.println("Test 2.1: Create an array2D of Cell objects, setLocation(...)");
		Cell[][] array2D = new Cell[4][4];
		for(int i=0; i<4; ++i){
			for(int j=0; j<4; ++j){
				array2D[i][j] = new Cell();
				array2D[i][j].setLocation(j,i); // create a new cell, and assign its location
			}
		}
		
		// Set content of each element by "T", "F", "E"
		array2D[0][0].setContent("E");
		array2D[0][1].setContent("E");
		array2D[0][2].setContent("E");
		array2D[0][3].setContent("E");
		
		array2D[1][0].setContent("E");
		array2D[1][1].setContent("F");
		array2D[1][2].setContent("F");
		array2D[1][3].setContent("F");
		
		array2D[2][0].setContent("T");
		array2D[2][1].setContent("E");
		array2D[2][2].setContent("E");
		array2D[2][3].setContent("E");
		
		array2D[3][0].setContent("E");
		array2D[3][1].setContent("E");
		array2D[3][2].setContent("E");
		array2D[3][3].setContent("E");
		
		System.out.println("Test 2.2: Assign cell's content:\nExpected value:\nE\tE\tE\tE\n"
				+ "E\tF\tF\tF\nT\tE\tE\tE\nE\tE\tE\tE");
		System.out.println("Actual value is: ");
		for(Cell[] row: array2D){
			for(Cell cell: row){
				System.out.print(cell.getContent()+"\t");
			}
			System.out.println();
		}
		
		/* Cell[2][1] is chosen, Supposed that ArrayList allCellsNearby is given. 
		 * Test Cell's method: ArrayList<Cell> findFalseCellsNearby(ArrayList<Cell> allCellsNearby)   
		 */ 
		// Declare ArrayList nearby Cell[2][1], add cells nearby
		ArrayList<Cell> nearbyC21 = new ArrayList<Cell>();
		nearbyC21.add(array2D[1][0]);
		nearbyC21.add(array2D[1][1]);
		nearbyC21.add(array2D[1][2]);
		nearbyC21.add(array2D[2][0]);
		nearbyC21.add(array2D[2][2]);
		nearbyC21.add(array2D[3][0]);
		nearbyC21.add(array2D[3][1]);
		nearbyC21.add(array2D[3][2]);
		
		// Method findFalseCellsNearby(...)
		System.out.println("\nTest 2.3: Test findFalseCellsNearby(...)");
		System.out.println("Expected findFalseCellsNearby array2D[2][1]:");
		System.out.println("F...(1,1) , F...(1,2)");
		System.out.println("Actual value: ");
		System.out.println(array2D[2][1].findFalseCellsNearby(nearbyC21));
		
		// Method findTrueCellsNearby(..)
		System.out.println("\nTest 2.4: Test findTrueCellsNearby(...)");
		System.out.println("Expected findTrueCellsNearby array2D[2][1]:");
		System.out.println("T...(2,0)");
		System.out.println("Actual value: ");
		System.out.println(array2D[2][1].findTrueCellsNearby(nearbyC21));
		
		// Method findEmptyCellsNearby(...)
		System.out.println("\nTest 2.5: Test findEmptyCellsNearby(...)");
		System.out.println("Expected findEmptyCellsNearby array2D[2][1]");
		System.out.println("E...(1,0) , E...(2,2) , E...(3,0) , E...(3,1) , E...(3,2)");
		ArrayList<Cell> emptyNBC21 = array2D[2][1].findEmptyCellsNearby(nearbyC21);
		System.out.println(emptyNBC21);
		
		// Method find rate false/cell
		System.out.println("\nTest 3: findRateFalseTrueNearby() & getRateFalseTrueNearby()");
		System.out.println("Expected value: 2/1");
		array2D[2][1].findRateFalseTrueNearby(); // run this method to find
		System.out.println("Actual value: "+array2D[2][1].getRateFalseTrueNearby());
		
		// Method getXXXIndex(), note: already setLocation on Test2.1		
		System.out.println("\nTest 4: getXXXIndex(), have setLocation() on Test 2.1");
		System.out.println("Test 4.1: Method getColumnIndex() of array2D[2][1]");
		System.out.println("Expected value: 1");
		System.out.println("Actual value: "+array2D[2][1].getColumnIndex());
		
		System.out.println("Test 4.2: Method getRowIndex() of array2D[2][1]");
		System.out.println("Expected value: 2");
		System.out.println("Actual value: "+array2D[2][1].getRowIndex());
		
		// Method getEmptyCellsNearby()
		System.out.println("\nTest 5: getEmptyCellsNearby(), list created in Test 2.5");
		System.out.println("Expected value: ");
		System.out.println("E...(1,0) , E...(2,2) , E...(3,0) , E...(3,1) , E...(3,2)");
		System.out.println("Actual value: ");
		System.out.println(emptyNBC21);
		
		// Method toString()
		System.out.println("\nTest 6: toString() of cell array2D[2][1]");
		System.out.println("Expected value: E 2/1 (2,1)");
		System.out.println("Actual value: "+array2D[2][1].toString());
		
		// Part 2: Testing Field method
		System.out.println("\n\nPart 2: Testing Field's methods");
		System.out.println("---------------------------------------------");
		
		// Find number of F, T, Empty cells on board
		System.out.println("Test 1: Testing calculate number of False, True, Empty cells based on level and board size");
		System.out.println("Select board: level Hard, number of rows: 4, number of columns: 4");
		System.out.println("Expected value: F, T, E: 3 1 12");
		System.out.print("Actual value: ");
		Field fieldtest = new Field("HARD", 4,4);
		
		// Test findCellsNearby(...)
		System.out.println("\nTest 2: findCellsNearby(...)");		
		System.out.println("Find all cells nearby cell with index[2][1]");
		System.out.println("Expected value: 8 cells with locations as follow: ");
		System.out.println("...(1,0),\t...(1,1),\t...(1,2),\t...(2,0),\t...(2,2),\t...(3,0),\t...(3,1),\t...(3,2) ");
		System.out.println(fieldtest.findCellsNearby(fieldtest.getAllCells()[2][1]));
		
		// Test pickCellsToList
		System.out.println("\nTest 3: pickCellsToList(...)");
		System.out.println("Because cells randomly choose, unpredictable which specific cells will be show up on the list.\n"
				+ "But definitely, number of F, T, E as below:\n"
				+ "Expected value: listFalseCells with 3 elements (3 F)\n"
				+ "listTrueCells with 1 elements (1 T)\n"
				+ "listEmptyCells with 12 elements(12 E)");
		System.out.println("\nActual value:");
		System.out.println("listFalseCells (3 F): "+fieldtest.listFalseCells);
		System.out.println("listTrueCells (1 T): "+fieldtest.listTrueCells);
		System.out.println("listEmptyCells (12 E): "+fieldtest.listEmptyCells);	
		
		// Test revealAllContents
		System.out.println("\nTest 4: revealAllContents.\n"
				+ "Each time running program, contents of cells randomly picked.\n"
				+ "But total number cells F, T, E must be the same: 3 1 12");
		System.out.println("Expected value: array 4*4, with each cell has format: content rateFalseTrue (location)");
		System.out.println("Actual value: ");
		fieldtest.revealAllContents();		
	}
}
