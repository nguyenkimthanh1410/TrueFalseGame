package truefalse.view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import truefalse.model.Cell;
import truefalse.model.Field;

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
