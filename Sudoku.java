package Sudoku;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class Sudoku extends JFrame {

    // Class wide variables
    static Tile[][] tiles;
    static int[][] puzzle = new int[9][9]; //Holds the puzzle values
    
    //This Function Generates and Populates a Sudoku puzzle by generating random numbers
    //and checking at each step that it follows Sudoku rules
    static void GeneratePuzzle(){
        for(int t=0; t<9; t++){
            for(int b=0; b<9; b++){
                puzzle[t][b] = 10;
                tiles[t][b].setText("");
            }
        }
        for(int i =0;i < 9; i= i + 3){
            for(int k=0; k < 9; k = k + 3){
                //This arraylist is used to check each grid for the same number
                //If the number is not in this list, it is not in the curent 3x3 grid
                ArrayList<Integer> list = new ArrayList<Integer>();  
                for(int j = 0; j < 9; j++){
                    Random rand = new Random();
                    int n = rand.nextInt(9) + 1;
                    int a = 0;
                    int row = i + j / 3;
                    int col = 0;
                    if(j<=2)
                        col = j + k;
                    if(j >2 && j <= 5 )
                        col = j - 3 + k;
                    if(j > 5)
                        col = j - 6 + k;
                    int tries = 0;
                    while(list.contains(n) || a == 0){
                        if(tries>20){
                            i = 0;
                            k = 0;
                            j = 0;
                            row = 0;
                            a = 1;
                            list = new ArrayList<Integer>();
                            for(int t=0; t<9; t++){
                                for(int b=0; b<9; b++){
                                    puzzle[t][b] = 10;
                                }
                            }
                        }
                        a=1;
                        tries++;
                        n = rand.nextInt(9) + 1;
                        
                        for(int y = 0;y<8; y++){
                            if(puzzle[row][y] == n)
                                a= 0;
                        }
                        for(int y = 0;y<8; y++){
                            if(puzzle[y][col] == n)
                                a= 0;
                        }
                    }
                    
                    list.add(n);
                    int z = rand.nextInt(100);
                    if(j <= 2){
                        tiles[i][j+k].setText(Integer.toString(n));
                        if(z>30)
                            tiles[i][j+k].setText("");
                        puzzle[i][j+k] = n;
                    }
                    if(j >2 && j <= 5 ){
                        tiles[i+1][j-3+k].setText(Integer.toString(n));
                        if(z>30)
                            tiles[i+1][j-3+k].setText("");
                        puzzle[i+1][j-3+k] = n;
                    }
                    if(j >5 )  {
                        tiles[i+2][j-6+k].setText(Integer.toString(n));
                        if(z>30)
                            tiles[i+2][j-6+k].setText("");
                        puzzle[i+2][j-6+k] = n;
                    }
                }
            }
        }
    }
 
    //Checks each text field to see if it has the correct value, if it does not , it highlights them red
    //It returns the amount of incorrect textfields, so if it returns 0 they have won
    static int checkPuzzle(){
        int wrong = 0;
        for(int a=0; a<9; a++){
            for(int b=0; b<9; b++){
                int z = 0;
                try{
                    z = Integer.parseInt(tiles[a][b].getText());
                }
                catch (Exception e) {tiles[a][b].setBackground(Color.red);}
                if( z != puzzle[a][b] ){
                    tiles[a][b].setBackground(Color.red);
                    wrong++;
                }
            }
        }
        return wrong;
    }
 
    // Tile class for the individual puzzle elements
    private class Tile extends JTextField implements FocusListener{
        Tile(){
            setText("");
            setHorizontalAlignment(JTextField.CENTER);
            this.addFocusListener(this);
        }
        
        @Override
        public void focusGained(FocusEvent e) {
            this.selectAll();
        }
        
        @Override
        public void focusLost(FocusEvent e) {
            this.select(0,0);
        }
    }
    
    // Setup the  GUI
    Sudoku(){
        // Set Look and Feel
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (Exception e) {}

        setTitle("Sudoku");
        setSize(500, 500); // Set the initial size of the application window
        setLayout(new BorderLayout()); // Set the layout as border for better apperance
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes the window close when the "X" is pressed
        
        // Create the Sudoku Main Grid
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(9,9));
        tiles = new Tile[9][9];

        // Create the 81 tiles and add them to the grid
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                // Intialize values for the borders to make the 9 sub-grids
                int top=1,left=1,bottom=1,right=1;
                switch(i){
                    case 0: 
                    case 3:
                    case 6: top = 3; break;
                    case 8: bottom = 3; break;
                    default: break;
                }
                switch(j){
                    case 0: 
                    case 3:
                    case 6: left = 3; break;
                    case 8: right = 3; break;
                    default: break;
                }
                // Intialize the text field and put it in the grid
                tiles[i][j] = new Tile();
                tiles[i][j].setBorder(new MatteBorder(top,left,bottom,right,Color.BLACK));
                grid.add(tiles[i][j]);
            }
        }
        
        // Create the Conrtol Buttons
        JPanel control = new JPanel();
        JButton btnNew = new JButton("New");
        JButton btnCheck = new JButton("Check");
        JButton btnSave = new JButton("Save");
        JButton btnLoad = new JButton("Load");
        control.add(btnNew);
        control.add(btnCheck);
        control.add(btnSave);
        control.add(btnLoad);
        
        // Here are the action listeners for the buttons
        // Generate a new puzzle
        btnNew.addActionListener((ActionEvent e) -> {
            GeneratePuzzle();
        });
        // Check if the puzzle was completed corectly
        btnCheck.addActionListener((ActionEvent e) -> {
            int a = checkPuzzle();
            if(a == 0)
            JOptionPane.showMessageDialog(this,"You win!");
            else
                JOptionPane.showMessageDialog(this,"You have " + Integer.toString(a) + " blocks wrong.");
             for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                tiles[i][j].setBackground(Color.white);
            }
            }
        });
        // Save the current puzzle to a file
        btnSave.addActionListener((ActionEvent e) -> {
            // Saving
        });
        // Load a saved puzzle from a file
        btnLoad.addActionListener((ActionEvent e) -> {
            // Loading
         GeneratePuzzle();
        });
        
        // Add all the panels to the Frame
        add(grid, BorderLayout.CENTER);
        add(control, BorderLayout.SOUTH);
        
        //Make frame visible
        setVisible(true);
    }
    
    // This is the way to launch the GUI
    public static void main (String[] args){
        // Create the GUI to start the game
        Sudoku GUI = new Sudoku();
        // Start of the game with a new puzzle
        GeneratePuzzle();
    }
}
