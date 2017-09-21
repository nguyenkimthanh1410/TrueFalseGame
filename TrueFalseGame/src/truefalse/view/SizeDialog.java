package truefalse.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import truefalse.model.Field;

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
	// Create a panel to hold components				
	JPanel panel = new JPanel(new BorderLayout(3,3));	
	ButtonGroup buttonGroup = new ButtonGroup();

	public SizeDialog(){
		// Create a panel to hold components				
		panel = new JPanel(new BorderLayout(3,3));
		
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
		
		// Define a button group
		radioEasy.setSelected(true); // Easy: default value
		buttonGroup.add(radioHard);
		buttonGroup.add(radioMedium);
		buttonGroup.add(radioEasy);
		
		//Add pane1 and pane2 to the frame
		panel.add(pane1, BorderLayout.WEST);
		panel.add(pane2, BorderLayout.EAST);	
		panel.setSize(350,200);		
	}// end of constructor

	public String[] getNewSetting() {		
		while(true) {
			int result = JOptionPane.showConfirmDialog(null,panel ,""
				+ "Setup a new game",JOptionPane.YES_NO_CANCEL_OPTION);
				
			if(result == JOptionPane.OK_OPTION){			
				// Selection
				level = getSelectedButtonText(buttonGroup);
				selectedSetup[0]= level;	
				selectedSetup[1] = tfNumRows.getText();
				selectedSetup[2] = tfNumColumns.getText();
				
				// Validation
				try{
					numOfRows = Integer.parseInt(tfNumRows.getText());
					numOfColumns = Integer.parseInt(tfNumColumns.getText());
					
					if(numOfRows <Field.MIN_ROWS || numOfRows>Field.DEFAULT_ROWS){// check numerical value
						JOptionPane.showMessageDialog(null, "Number of row must be within ("
								+ ""+Field.MIN_ROWS+"-"+Field.DEFAULT_ROWS+")");
					}
					if(numOfColumns < Field.MIN_COLUMNS || numOfColumns>Field.DEFAULT_COLUMNS){// check numerical value
						JOptionPane.showMessageDialog(null, "Number of columns must be within ("
								+ ""+Field.MIN_COLUMNS+"-"+Field.DEFAULT_COLUMNS+")");
					}	
					System.out.println("Inside: JOptionPane.OK");
					break;
				}catch(Exception ex){
					System.out.println("Invalid input in SizeDialog's getNewSetting()");
				}	
				System.out.println(Arrays.toString(selectedSetup));
				
			}else{
				selectedSetup = defaultSetup;
				System.out.println("You cancelled: Default setting will be used: " + Arrays.toString(selectedSetup));
				break;
			}
		}		
		JOptionPane.showMessageDialog(null, "You setup a new game with:"
											+ "\nLevel of difficulty: \t"+selectedSetup[0]+"\nNumber of rows: \t"+selectedSetup[1]
											+"\nNumber of columns: \t"+selectedSetup[2], "Setup confirmed",JOptionPane.INFORMATION_MESSAGE);
		return selectedSetup;	
	}		
	

	private String getSelectedButtonText(ButtonGroup buttonGroup) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
	}
	
}// end of class SizeDialog