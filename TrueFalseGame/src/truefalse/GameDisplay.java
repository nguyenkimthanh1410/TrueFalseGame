package truefalse;

import java.awt.GridLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameDisplay extends JPanel {
		 
		
	// Constructor
	public GameDisplay(Field arrayCells){
				
		// Set GridLayout
		setLayout(new GridLayout(arrayCells.numOfRows, arrayCells.numOfColumns,5,5));
				
		// Render cells of Cell[][] on the grid
		Cell[][] buttons = arrayCells.getAllCells();
		for(Cell[] row: buttons){
			for(Cell button: row){
				add(button);				
			}
		}		
					
	}// end of constructor
	
}// end of class GameDisplay
