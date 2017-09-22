package truefalse.controller;
import truefalse.model.Cell;
import truefalse.model.Field;
import truefalse.view.GameDisplay;
import truefalse.view.MainFrame;
import truefalse.view.SizeDialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class App {

	// Declare instance variables
	private int numOfRows, numOfColumns; 
	private String level;	
	private Field field; 
	private GameDisplay gameDisplay;	
	private MainFrame mainFrame = new MainFrame();
	private int countClicks = 0;// Number of times user clicked
	String msgInstruction = "<html>Welcome to True/False Game.<hr><br>" +
			"There are 3 different level: Easy, Medium, Hard.<br>" +
			"The game is played by revealing the contents of each cell. A cell might be empty or contain a single flag.<br>" +
			"There are two types of flag: a true-flag, and a false-flag.<br><br>" +
			"On each button display:'digit/digit':<br>" +
			"The 1st: number of neighbouring Cell objects that contain false-flags.<br>" +
			"The 2nd: number of neighbouring Cell objects that contain true-flags.<br>" +
			"A true-flag is a treasure the player wants to find, whereas a false-flag must never be revealed.<br>" +
			"Finding a false-flag causes the player to immediately lose the game.<br><br>" +
			"The player only wins the game if they reveal all cells except those that contain false-flags.</html>";
	
	// Constructor
	public App(){
		setupGame();
		playGame();		
	}
	
	// Initial setup of game 
	public void setupGame(){
		
		/* Instantiate a Field object starting with 
		 * default setup: EASY, numOfRows = 10, numOfColumns =10
		 * Assign value for 03 instance variables */		
		field = new Field(Field.DEFAULT_LEVEL,Field.DEFAULT_ROWS,Field.DEFAULT_COLUMNS);		
		level = Field.DEFAULT_LEVEL;
		numOfRows = Field.DEFAULT_ROWS;
		numOfColumns = Field.DEFAULT_COLUMNS;
		
		// Instantiate a GameDisplay object and add to initial mainFrame
		gameDisplay = new GameDisplay(field);
		mainFrame.add(gameDisplay, BorderLayout.CENTER);
		
		// General setup
		mainFrame.setSize(650,650);
		mainFrame.setVisible(true);		
				
	}// end of setupGame
	
	// Method playGame
	public void playGame(){
		System.out.println("1. playGame() ");		

		mainFrame.addActionHandlerOptions(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				if(ae.getActionCommand().equals("New Game")){// New Game is chosen
																			
					// Initialize an SizeDialog object to get input from player
					System.out.println("\nNew Game button is clicked");
					SizeDialog sd = new SizeDialog();
					String[] theNewSetting = sd.getNewSetting();
					level = theNewSetting[0];					
					numOfRows = Integer.parseInt(theNewSetting[1]);
					numOfColumns = Integer.parseInt(theNewSetting[2]);					
							
					// replace the old GameDisplay with a new one: 
					// Ref: https://stackoverflow.com/questions/218155/how-do-i-change-jpanel-inside-a-jframe-on-the-fly
					mainFrame.getContentPane().remove(gameDisplay);
					mainFrame.getContentPane().invalidate();
					
					// Primarily, instantiate a GameDisplay object. It will be sent to GameDisplay
					field = new Field(level,numOfRows, numOfColumns); 
					 
					// The new object GameDisplay, add to original frame, replace the old one					 					
					gameDisplay = new GameDisplay(field);
					
					// Add a new one
					mainFrame.getContentPane().add(gameDisplay, BorderLayout.CENTER);
					mainFrame.getContentPane().revalidate();

					// Reset countClicks
					countClicks=0;	
					
					// Add Listener to cells
					addListenerToCells (field);
					
				} else if(ae.getActionCommand().equals("Reset")){// if "Reset" is selected					
					
					// Restore values of instance variables related
					level = Field.DEFAULT_LEVEL;
					numOfRows = Field.DEFAULT_ROWS;
					numOfColumns = Field.DEFAULT_COLUMNS;
					countClicks=0;
					
					// replace the old GameDisplay with a new one
					mainFrame.getContentPane().remove(gameDisplay);
					mainFrame.getContentPane().invalidate();					
					
					// Restore attributes of Field object to default values
					field = new Field(Field.DEFAULT_LEVEL,Field.DEFAULT_ROWS,Field.DEFAULT_COLUMNS);					
					gameDisplay = new GameDisplay(field);
					
					// Add a new one
					mainFrame.getContentPane().add(gameDisplay, BorderLayout.CENTER);
					mainFrame.getContentPane().revalidate();
					
					// Add Listener to cells
					addListenerToCells (field);
					
				} else if(ae.getActionCommand().equals("Instruction")){// if "Instruction" is selected
					JOptionPane.showMessageDialog(null, msgInstruction,"Instruction",JOptionPane.INFORMATION_MESSAGE);
				}				
			}// end of actionPerformed			
		});// end of addActionHandlerOptions
		
		// Add Listener to cells
		addListenerToCells (field);
		
	}// end of playGame
	
	
	protected void addListenerToCells(Field field) {
		// Event handling for cells. AddActionListener @ Field class
		field.addActionHandlerCells(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent ae){				
				countClicks++;
				
				//Find out which button has just been clicked
				Cell guess = (Cell) ae.getSource();				
				System.out.println("You selected: "+guess);								
				
				// Check the cell's content
				if(guess.getContent().equals("F")){// Cell's content F, immediately lose game
					String msgFarewell = 
							"Game Over!\nBetter luck next time. You took "+countClicks+" guesses.";
					finishGame(msgFarewell);
				}// end if "F"
				
				else if(guess.getContent().equals("T")){ // Cell's content T is clicked
					// reveal content
					guess.setText(guess.getContent());
					
					// remove cell from ArrayList True
					field.listTrueCells.remove(guess);
					
					// disable this cell T
					guess.setEnabled(false);													
				}// end if content "T"
				
				else if(guess.getContent().equals("E")){ // Cell's content E is clicked
					
					// reveal content	
					guess.setText(guess.getContent());
					
					// remove cell from ArrayList Empty
					field.listEmptyCells.remove(guess);
					
					 // set disable this cell E
					guess.setEnabled(false);
					
					// doing the same for cells empty nearby this cell
					for(Cell cellTest: guess.getEmptyCellsNearby()){
						cellTest.setText(cellTest.getContent()); // reveal content of cells nearby
						field.listEmptyCells.remove(cellTest);// remove cells nearby from ArrayList Empty
						cellTest.setEnabled(false); // disable cells nearby						
					}// end for loop
				}// end if content E										
				
				/* Check true and false cells left in the list. 
				 * If  both 2 lists are empty, finish the game
				 */
				if(field.listTrueCells.isEmpty() & field.listEmptyCells.isEmpty()){
					String msgCongratulation = 
							"Congratulation. You took "+countClicks+" guesses.";
					finishGame(msgCongratulation);
				}				
			}// end of actionPerformed	
		});// end of addActionHandler		
	}

	// Handling when game is finished	
	public void finishGame(String message){
		field.revealAllContents();
		JOptionPane.showMessageDialog(null, message);		
	}// end finishGame
	
	
	// main method
	public static void main(String[] args){
		/*Create the App on the event dispatching thread
		 * main() is executed on the main thread. So that it cannot instantiate a App object
		 * Instead, it must crate a Runnable object that executes on the event-dispatching thread,
		 * and have this object create the GUI
		 */
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new App();
			}			
		});		
	}
	
}// end of class App
