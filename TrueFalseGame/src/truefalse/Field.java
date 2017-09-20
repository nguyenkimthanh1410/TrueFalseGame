package truefalse;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

// A class that models the game board as a 2D primitive array of Cell objects
public class Field {
	
	// Declare the constant variables 
	public static final String DEFAULT_LEVEL = "EASY";
	public static final int DEFAULT_ROWS =10; // default number of rows: Max 
	public static final int DEFAULT_COLUMNS=10;// default number of columns: Max
	public static final int MIN_ROWS =4; // Min number of rows: 4
	public static final int MIN_COLUMNS =4; // Min number of columns: 4
	
	static final double EASY_FALSE_RATE= 0.05;
	static final double EASY_TRUE_RATE = 0.01;
	static final double MEDIUM_FALSE_RATE = 0.1;
	static final double MEDIUM_TRUE_RATE = 0.05;
	static final double HARD_FALSE_RATE = 0.2;
	static final double HARD_TRUE_RATE = 0.1;
	
	// Declare instance variables
	private Cell[][] cells;
	public String difficultyLevel;
	public int numOfRows, numOfColumns;	
	private int numOfFalseCells, numOfTrueCells, numOfEmptyCells;
	
	private ArrayList<Cell> tempListCells = new ArrayList<Cell>();
	public ArrayList<Cell> listFalseCells= new ArrayList<Cell>();
	public ArrayList<Cell> listTrueCells= new ArrayList<Cell>();	
	public ArrayList<Cell> listEmptyCells= new ArrayList<Cell>();	
	
	// Constructor
	public Field(String level, int numOfRows, int numOfColumns ){
		
		/* Task: Pick cells True, False, Empty randomly. By doing steps:
		 * Step 1: Initialize an array Cell[][] with specific number of rows, columns. Set location for each cell
		 * Step 2: Form an ArrayList (tempListCells) referencing to cells on Cell[][] 
		 * Step 3: Calculate number of false, true, empty cells required on Cell[][]
		 * Step 4: Pick randomly cells from tempListCells, assign it a specific content 
		 * 		   Remove selected cells from tempListCells to prevent it from being picked the second time */
		
		/*Step 1: Initialize an array Cell[][] with specific number of rows, columns. 
		 * Set location for each cell for finding out its nearby cells later
		 */		
		this.numOfRows = numOfRows;
		this.numOfColumns = numOfColumns;
		int sizeBoard = numOfRows* numOfColumns;
		
		cells = new Cell[numOfRows][numOfColumns];
		for (int i = 0; i < numOfRows; ++i){
			for(int j = 0; j < numOfColumns;++j){
				cells[i][j] = new Cell();
				cells[i][j].setLocation(j, i); // Notice that: Point(column, row); but array[row][column]
			}// end of inner for loop
		}// end of outer for loop
		
		// Step 2: Form an ArrayList (tempListCells) referencing to cells on Cell[][]
		for(Cell[] row: cells){
			for(Cell cell: row){
				tempListCells.add(cell);
			}
		}

		// Step 3: Calculate number of false, true, empty cells required on Cell[][]		
		switch (level){		
		case "EASY":
			this.numOfFalseCells = (int)(sizeBoard*EASY_FALSE_RATE);//0.05
			this.numOfTrueCells = (int)(sizeBoard*EASY_TRUE_RATE);//0.01
			break;				
		case "MEDIUM":
			this.numOfFalseCells = (int)(sizeBoard*MEDIUM_FALSE_RATE);//0.1
			this.numOfTrueCells = (int)(sizeBoard*MEDIUM_TRUE_RATE);//0.05
			break;
		case "HARD":
			this.numOfFalseCells =(int)(sizeBoard*HARD_FALSE_RATE);//0.2
			this.numOfTrueCells =(int)(sizeBoard*HARD_TRUE_RATE);//0.1
			break;				
		}		
		if (this.numOfFalseCells <1) this.numOfFalseCells =1;
		if (this.numOfTrueCells <1) this.numOfTrueCells =1;	
		this.numOfEmptyCells = sizeBoard - this.numOfFalseCells - this.numOfTrueCells;
		System.out.println("F, T, E: "+this.numOfFalseCells+" "+this.numOfTrueCells+" "+this.numOfEmptyCells); // print out F, T, E for testing
		
		
		/*Step 4: Pick randomly cells from tempListCells, assign it a specific content 
		 * 		   Remove selected cells from tempListCells to prevent it from being picked the second time */
		pickCellsToList("F", this.numOfFalseCells);
		pickCellsToList("T", this.numOfTrueCells);
		pickCellsToList("E", this.numOfEmptyCells);
		
		
		/*Find all cells (False, True, Empty) nearby a given cell.
		 *Delegate the task of storing all list of cells False, True, Empty to cell itself  
		 */
		for(Cell[] row: cells){
			for(Cell cell: row){
				ArrayList<Cell> cellsNearby = findCellsNearby(cell);
				cell.findFalseCellsNearby(cellsNearby);
				cell.findTrueCellsNearby(cellsNearby);
				cell.findEmptyCellsNearby(cellsNearby);
				cell.findRateFalseTrueNearby();
				cell.setText(cell.getRateFalseTrueNearby()); // setText on the button on grid
			}
		}// end of finding cells nearby				
		
	}// end of constructor	
	
	
	/*Method to find out all cells nearby a given cell.
	 * Adjacent cells is defined by (its location +-1) applying for both X, Y  */ 
	public ArrayList<Cell> findCellsNearby(Cell cellTest) {
		ArrayList<Cell> tempAL = new ArrayList<Cell>();
		int rowIndex = cellTest.getRowIndex();// row index of cellTest(0-9)
		int colIndex = cellTest.getColumnIndex();//column index of cellTest (0-9)
		
		int minR= (rowIndex==0)? 0 :(rowIndex-1); // Lower end of row index of cells nearby, not allowed <0
		int maxR = ((rowIndex+1)==this.numOfRows)? rowIndex: (rowIndex+1);// Higher end of row index of cells nearby, not allowed > rowIndex 
		
		int minC= (colIndex==0)? 0 :(colIndex-1); // Lower end of column index of cells nearby, not allow <0 
		int maxC = ((colIndex+1)==this.numOfColumns)? colIndex: (colIndex+1);// Higher end of column index of cells nearby, not allowed > colIndex
		
		// Keep all cells nearby on an ArrayList (tempAL) 
		for(int k=minR; k<=maxR;k++){//0-1
			for(int h=minC; h<=maxC; h++){//8-9
				//System.out.println(k+" "+l);
				Cell nearbyCell = cells[k][h]; // all cells and includingcellTest itself
				//System.out.println(nearbyCell);
				if (nearbyCell != cellTest){ 
					tempAL.add(nearbyCell);	// add all cells nearby to the temp list excluding cellTest 				
				}				
			}// end for loop h (inner)
		}// end for loop k (outer)		
		return tempAL;			
	}// end of findCellsNearby method
	

	// Pick randomly given number of cells from tempListCells and assign specific content (False, or True, or Empty)
	private void pickCellsToList(String givenContent, int numOfElements) {
		for(int i=0; i< numOfElements; ++i){
			Cell randomCell = tempListCells.get(new Random().nextInt(tempListCells.size()));			
			if (givenContent =="F"){
				randomCell.setContent(givenContent);
				listFalseCells.add(randomCell);
			}else if(givenContent =="T"){
				randomCell.setContent("T");
				listTrueCells.add(randomCell);
			}else if (givenContent =="E"){
				randomCell.setContent("E");
				listEmptyCells.add(randomCell);
			}			
			tempListCells.remove(randomCell);
		}// end of for loop
	}// end of method pickCellsToList
	
	
	// Method to get Cell[][] as a mean of accessing its elements in App class 
	public Cell[][] getAllCells(){
		//for testing
		/*for (Cell[] row: cells){
			for(Cell cell: row){
				System.out.println(cell);
			}   
		}// end outer loop */ // for testing	
		return cells;		
	}// end of method
	
	
	// Method to add ActionListener to each cell on Cell[][]
	public void addActionHandlerCells(ActionListener listener){
		System.out.println("Add actionHandlerCells @ class Field");
		for (Cell[] row: cells){
			for(Cell cell: row){
				cell.addActionListener(listener);		
			}
		}	
	}// end of method

	// Method to reveal all contents of cells when game is over
	public void revealAllContents() {
		for (Cell[] row: cells){
			for(Cell cell: row){
				cell.setText(cell.getContent());
				cell.setEnabled(false); // disable each cell to prevent user from clicking on it
				System.out.print(cell +" ");// for testing only
			}
			System.out.println();
		}		
	}// end method showAllContents

			
}// end of class Field
