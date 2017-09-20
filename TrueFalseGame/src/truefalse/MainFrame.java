package truefalse;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	// Instantiate functional buttons on the JFrame 
	public static JButton newGame = new JButton("New Game");
	public static JButton reset = new JButton("Reset");
	public static JButton instruction = new JButton("Instruction");
	
	// Constructor
	public MainFrame(){
		
		// General setup
		super("The True/False Game written by JC13138914");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setLayout(new BorderLayout());
		
		// Create JMenuBar to hold functional buttons
		JMenuBar menuBar = new JMenuBar();
		menuBar.setLayout(new FlowLayout());			
		menuBar.add(newGame);				
		menuBar.add(reset);
		menuBar.add(instruction);
		
		// Add menuBar to JFrame
		add(menuBar,BorderLayout.NORTH);	
		
		// General setup
		setLocationRelativeTo(null);
		pack();
		setSize(650,650);
		setVisible(true);		
		
	}// end of constructor
	
	// Method addActionHandler to add listener to functional buttons
	public static void addActionHandlerOptions(ActionListener listener) {
		System.out.println("AddActionHandler @ MainFrame");
		newGame.addActionListener(listener);
		reset.addActionListener(listener);
		instruction.addActionListener(listener);
	}

}// end of class MainFrame
