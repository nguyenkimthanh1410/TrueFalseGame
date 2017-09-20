package truefalse;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

/*A class to have change in setup (difficulty level, number of rows, number of columns), 
 * Performing error-checking the input*/ 
public class SizeDialog extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String level = Field.DEFAULT_LEVEL;
	private int numOfRows= Field.DEFAULT_ROWS; 
	private int numOfColumns= Field.DEFAULT_COLUMNS;
	
	// Create radio buttons for difficulty level
	private JRadioButton radioEasy = new JRadioButton("Easy");	
	private JRadioButton radioMedium = new JRadioButton("Medium");
	private JRadioButton radioHard = new JRadioButton("Hard");
	
	// Instantiate JLabel and JTextField typed variables	
	JLabel labelRow = new JLabel("Number of rows ("+Field.MIN_ROWS+"-"+Field.DEFAULT_ROWS+"): ");
	JTextField tfNumRows = new JTextField(""+Field.DEFAULT_ROWS,5); // "10": default value of row
	JLabel labelColumn = new JLabel("Number of columns ("+Field.MIN_COLUMNS+"-"+Field.DEFAULT_COLUMNS+"): ");
	JTextField tfNumColumns = new JTextField(""+Field.DEFAULT_COLUMNS, 5); //"10": default value
	
	// Declare an array String[3] to store values user selected
	String[] selectedSetup = new String[3];
	String[] defaultSetup = {level, ""+Field.DEFAULT_ROWS,""+Field.DEFAULT_COLUMNS};

	/*Constructor
	 * Create a panel on which other components reside
	 * Register an ActionListener object to fields needed to get data, implement the method actionPerformed
	 * Show up the Dialog to get the data
	 * Collect the data user selected
	 * */
	public SizeDialog(){
		// Create a panel to hold components				
		JPanel panel = new JPanel(new BorderLayout(3,3));
		
		/*Create pane1, add options for difficult level
		 * Use GridLayout to make sure these component aligned
		 */		
		JPanel pane1 = new JPanel(new GridLayout(3,2,3,3));
		pane1.add(new JLabel("Level of difficulty: "));
		pane1.add(radioEasy);
		pane1.add(new JLabel(""));
		pane1.add(radioMedium);
		pane1.add(new JLabel(""));
		pane1.add(radioHard);		
		
		/* Create pane2, add options for number of rows, columns
		 * Use GridLayout to make sure these component aligned
		 */		
		JPanel pane2 = new JPanel(new GridLayout(3,2,1,3));		
		pane2.add(new JLabel("Board size: "));
		pane2.add(new JLabel("Press ENTER to confirm selection!"));
		pane2.add(labelRow);
		pane2.add(tfNumRows);
		pane2.add(labelColumn);
		pane2.add(tfNumColumns);
		
		JPanel pane3= new JPanel(new BorderLayout());
		pane3.add(new JLabel("	Note: New Game button sometimes haven't released properly."),BorderLayout.NORTH);
		pane3.add(new JLabel("	Please restart program. I'm sorry for the inconvenience caused."),BorderLayout.CENTER);
		pane3.add(new JLabel("	Or you might have to enter more than 1 time to wait button update its status."), BorderLayout.SOUTH);
				
		// Define a button group
		ButtonGroup group = new ButtonGroup();
		radioEasy.setSelected(true); // Easy: default value
		group.add(radioHard);
		group.add(radioMedium);
		group.add(radioEasy);
		
		//Add pane1 and pane2 to the frame
		panel.add(pane1, BorderLayout.WEST);
		panel.add(pane2, BorderLayout.EAST);
		panel.add(pane3,BorderLayout.SOUTH);
		panel.setSize(350,200);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLocationRelativeTo(null);
		//frame.setVisible(true);
		System.out.println("Begin AL");
		
		/* Add ItemListener to options about level, rows, columns
		 * With level is selected, assigned value to instance variables
		 * With row, column, perform error-checking value,
		 * 		if passed, assign selected values to instance variable numOfRows, numOfColumns
		 */
		addActionHandler( new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent ae) {				
				if(ae.getSource()==radioHard){// level Hard is chosen
					level = radioHard.getText().toUpperCase();
					System.out.println("You chose: "+level);
					
				}else if(ae.getSource()== radioMedium){// level Medium is chosen
					level = radioMedium.getText().toUpperCase();
					System.out.println("You chose: "+level);
					
				}else if(ae.getSource() == radioEasy){// level Easy is chosen
					level = radioEasy.getText().toUpperCase();
					System.out.println("You chose: "+level);
					
				}else if(ae.getSource() == tfNumRows){// Check number of rows selected
					try{
						int num1 = Integer.parseInt(tfNumRows.getText());
						if(num1<Field.MIN_ROWS || num1>Field.DEFAULT_ROWS){// check numerical value
							JOptionPane.showMessageDialog(null, "Number of row must be within ("
									+ ""+Field.MIN_ROWS+"-"+Field.DEFAULT_ROWS+")");
						} else numOfRows = num1;	
					}catch (Exception e){// catch non-numerical value
						JOptionPane.showMessageDialog(null,"Number of row can't be a string");					
					}
					System.out.println("You chose "+numOfRows);
					
				}else if(ae.getSource() == tfNumColumns){// Check number of columns selected
					try{
						int num2 = Integer.parseInt(tfNumColumns.getText());
						if(num2<Field.MIN_COLUMNS || num2>Field.DEFAULT_COLUMNS){// check numerical value
							JOptionPane.showMessageDialog(null, "Number of columns must be within ("
									+ ""+Field.MIN_COLUMNS+"-"+Field.DEFAULT_COLUMNS+")");
						} else numOfColumns = num2;	
					}catch (Exception e){// catch non-numerical value
						JOptionPane.showMessageDialog(null,"Number of columns can't be a string");					
					}
					System.out.println("You chose "+numOfColumns);
				}// end of else if	
			}// end method actionPerformed
		});// end addActionHandler	

		// Show up the JOptionPane for selection
		int result = JOptionPane.showConfirmDialog(null,panel ,""
				+ "Setup a new game",JOptionPane.YES_NO_CANCEL_OPTION);
		
		
		/* Collect the result when user confirm the new setup. 
		 * It'll return value user key in as long as all conditions applicable,
		 * otherwise it will return the default values
		*/
		
		if(result == JOptionPane.OK_OPTION){
			selectedSetup[0]= level;
			selectedSetup[1] = ""+numOfRows;
			selectedSetup[2] =""+numOfColumns;
			//System.out.println("in sizeDialog");
			//System.out.println(Arrays.toString(selectedSetup));
			/*System.out.println("You chose level: "+level);
			System.out.println("You chose row: "+numOfRows);
			System.out.println("You chose column: "+numOfColumns);*/
		}else{
			selectedSetup = defaultSetup;
			System.out.println(Arrays.toString(selectedSetup));
			/*System.out.println("You chose level: "+"EASY");
			System.out.println("You chose row: "+10);
			System.out.println("You chose column: "+10);*/
		}
		JOptionPane.showMessageDialog(null, "You setup a new game with:"
				+ "\nLevel of difficulty: \t"+selectedSetup[0]+"\nNumber of rows: \t"+selectedSetup[1]
						+"\nNumber of columns: \t"+selectedSetup[2],""
								+ "Setup confirmed",JOptionPane.INFORMATION_MESSAGE);
	}// end of constructor
	

	// Method to add and ActionHandler object to JTextField objects
	private void addActionHandler(ActionListener listener) {
		radioHard.addActionListener(listener);
		radioMedium.addActionListener(listener);
		radioEasy.addActionListener(listener);
		tfNumRows.addActionListener(listener);
		tfNumColumns.addActionListener(listener);
	}
	
	// Method to get the new setup (level, number of rows, number of columns) 
	public String[] getNewSetup(){		
		return selectedSetup;
	}
	
}// end of class SizeDialog