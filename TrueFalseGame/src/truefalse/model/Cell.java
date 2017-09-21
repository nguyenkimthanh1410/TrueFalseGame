package truefalse.model;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JButton;

/*A class that represents the contents of a single cell in the game.
 * Being given list of adjacent cells, find out and keep info about False, True, Empty neighboring */
@SuppressWarnings("serial")
public class Cell extends JButton {
	
	// Declare instance variables
	private String content;
	private String rateFalseTrueNearby;
	private Point location= new Point(); //(x,y)=(column, row)
	private ArrayList<Cell> falseCellsNearby = new ArrayList<Cell>();
	private ArrayList<Cell> trueCellsNearby = new ArrayList<Cell>();
	private ArrayList<Cell> emptyCellsNearby= new ArrayList<Cell>();
	
	// Default constructor
	public Cell(){		
	}	
	
	// Method to set content of cell within 3 options: false, true, empty
	public void setContent(String newContent){//ok
		this.content = newContent;
	}
	
	// Method to get content of cell
	public String getContent(){
		return this.content;
	}
	
	// Method to find out all False cells nearby
	public ArrayList<Cell> findFalseCellsNearby(ArrayList<Cell> allCellsNearby){//ok
		for (Cell cellTest: allCellsNearby){
			if(cellTest.content.equals("F")){
				falseCellsNearby.add(cellTest);
				//System.out.print(cellTest+" ");
			}
		}
		//System.out.println("False cells nearby "+this); // false cells nearby
		return falseCellsNearby;
	}
	
	// Method to find out all True cells nearby
	public ArrayList<Cell> findTrueCellsNearby(ArrayList<Cell> allCellsNearby){//ok
		for(Cell cellTest: allCellsNearby){
			if(cellTest.content.equals("T")){
				trueCellsNearby.add(cellTest);
			}
		}
		//System.out.println("True cells nearby "+this);// print out true cells nearby
		return trueCellsNearby;
	}
	
	// Method to find out all Empty cells nearby
	public ArrayList<Cell> findEmptyCellsNearby(ArrayList<Cell> allCellsNearby){//ok
		for(Cell cellTest: allCellsNearby){
			if(cellTest.content.equals("E")){
				emptyCellsNearby.add(cellTest);
			}
		}
		//System.out.println("Empty cells nearby "+this);// empty cells nearby
		return emptyCellsNearby;
	}
	
	// Method to find the rate false/true nearby
	public void findRateFalseTrueNearby(){//ok
		 rateFalseTrueNearby = ""+falseCellsNearby.size()+"/"+trueCellsNearby.size();		
	}
	
	// Method to get value of rate false/true nearby
	public String getRateFalseTrueNearby(){//ok
		return this.rateFalseTrueNearby;
	}	
		
	// Method to set location of the cell. Notice order Point(X, Y), but Array(row, column) 
	public void setLocation(int column, int row){//ok
		this.location = new Point(column,row);
	}
	
	// Method to Get column value of the cell position
	public int getColumnIndex(){//ok
		return (int)location.getX();
	}	
	
	// Method to get row value of the cell position
	public int getRowIndex(){//ok
		return (int)location.getY();
	}	
	
	// Method to get ArrayList Empty cells nearby
	public ArrayList<Cell> getEmptyCellsNearby(){
		return this.emptyCellsNearby;
	}
	
	// Method toString to get representation of the cell
	public String toString(){//ok
		return content +" "+ rateFalseTrueNearby +" ("+getRowIndex()+","+getColumnIndex()+")";
	}	
	
}// end class Cell
