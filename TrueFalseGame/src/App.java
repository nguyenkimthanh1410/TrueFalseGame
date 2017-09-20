import truefalse.*;

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
	private int countClicks = 0;// Number of cells clicked
	
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
		mainFrame.add(gameDisplay);
	}// end of setupGame
	
	// Method playGame
	public void playGame(){
		System.out.println("Start playing game: ");
		MainFrame.newGame.setSelected(false);MainFrame.newGame.updateUI();
		System.out.println(MainFrame.newGame.isSelected());
		MainFrame.reset.setSelected(false); MainFrame.reset.updateUI();
		MainFrame.instruction.setSelected(false); MainFrame.instruction.updateUI();
				
	/* Event-handling for options: New Game, Reset, Instruction
	 * Use one ActionListener object 
	 * to handle all ActionEvent objects fired by functional buttons
	 */
		MainFrame.addActionHandlerOptions(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("implement action handler option @App");
			
				if(ae.getActionCommand().equals("New Game")){// New Game is chosen
																			
					// Initialize an SizeDialog object to get input from player
					SizeDialog sd = new SizeDialog();					
					level = sd.getNewSetup()[0];					
					numOfRows = Integer.parseInt(sd.getNewSetup()[1]);
					numOfColumns = Integer.parseInt(sd.getNewSetup()[2]);
					System.out.println("Back to app after sizeDialog");
					
					// Remove all components on GameDisplay object							
					gameDisplay.removeAll();					

					//System.out.println(Arrays.toString(sd.getNewSetup()));
					System.out.println("in App");
					
					// Primarily, instantiate a GameDisplay object. It will be sent to GameDisplay
					field = new Field(level,numOfRows, numOfColumns); 
					 
					// The new object GameDisplay, add to original frame, replace the old one					 					
					gameDisplay = new GameDisplay(field);
					
					mainFrame.add(gameDisplay);

					// Reset countClicks
					countClicks=0;
					
					// Update UI
					gameDisplay.updateUI();
					
					// Playing game from the beginning					
					playGame();
					
					
				} else if(ae.getActionCommand().equals("Reset")){// if "Reset" is selected					
					
					// Remove all components on GameDisplay object
					gameDisplay.removeAll();
					
					// Restore values of instance variables related
					level = Field.DEFAULT_LEVEL;
					numOfRows = Field.DEFAULT_ROWS;
					numOfColumns = Field.DEFAULT_COLUMNS;
					countClicks=0;
					
					// Restore attributes of Field object to default values
					field = new Field(Field.DEFAULT_LEVEL,Field.DEFAULT_ROWS,Field.DEFAULT_COLUMNS);					
					gameDisplay = new GameDisplay(field);
					mainFrame.add(gameDisplay);
					
					// Update UI
					gameDisplay.updateUI();
					
					// Playing game from the beginning
					playGame();
					
				} else if(ae.getActionCommand().equals("Instruction")){// if "Instruction" is selected
					JOptionPane.showMessageDialog(null,"This is a game True/ False.\n\n"
							+ "- There are 3 different level: Easy, Medium, Hard.\n\n"
							+ "- The game is played by revealing the contents of each cell. A cell might be empty or contain a single flag.\n"
							+ "- There are two types of flag: a true-flag, and a falseflag.\n"
							+ "	 A true-flag is a “treasure” the player wants to find, whereas a false-flag must never be revealed.\n\n"
							+ "- Finding a false-flag causes the player to immediately lose the game.\n"
							+ "- The player only wins the game if they reveal all cells except those that contain false-flags.\n\n"
							+ "- When select a new game:\n"
							+ "- If the player click No or Cancel: The game will store to the default setup.\n"
									+ "- After selecting number of columns or number of rows is chosen, "
									+ "it's essential that the player press ENTER to confirm selection.\n"
									+ " In other words, the user presses ENTER "
									+ "when inputting into a text field to make sure that an ActionEvent is generated.\n"
									+ "\nExisting problem of updating:\n+ At the moment, the program haven't found a better solution\n "
				+ "for releasing 2 push buttons @ MenuBar, including: NewGame, Instruction\n"
				+ "+ Therefore, in some cases, the player will have to restart the program.\n"
				+ "+ I'm so sorry for the inconvenience might caused");
				}				
			}// end of actionPerformed			
		});// end of addActionHandlerOptions
		
						
		// Event handling for cells. AddActionListener @ Field class
		field.addActionHandlerCells(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent ae){				
				countClicks++;
				System.out.println("Start actionPerformed  cells @ App");
				
				//Find out which button has just been clicked
				Cell guess = (Cell) ae.getSource();				
				System.out.println("You selected: "+guess);								
				
				// Check the cell's content
				if(guess.getContent().equals("F")){// Cell's content F, immediately lose game
					System.out.println("inside if F");				
					finishGame();
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
					finishGame();
				}				
			}// end of actionPerformed	
		});// end of addActionHandler
		
	}// end of playGame
	
	
	// Handling when game is finished	
	public void finishGame(){
		
		if(!field.listFalseCells.isEmpty()){
			// reveal all cells and disable them as well
			field.revealAllContents();					
			JOptionPane.showMessageDialog(null,"Game Over!\nBetter luck next time."
					+ " "+ "You took "+countClicks+" guesses.");
		
		}else{
			JOptionPane.showMessageDialog(null,"Game Over!\nCongratulation. "
					+ ""+" You took "+countClicks+" guesses.");
		}
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
