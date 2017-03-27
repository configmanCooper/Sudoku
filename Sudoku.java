/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sudoku;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Random;
import java.util.ArrayList;
/**
 *
 * @author Student
 */
public class Sudoku extends JFrame {

    // Class wide variables
    static JTextField[][] tiles;
   static int[][] puzzle = new int[9][9]; //Holds the puzzle values
    
   //This Function Generates and Populates a Sudoku puzzle by generating random numbers
   //and checking at each step that it follows Sudoku rules
 static void GeneratePuzzle(){
          for(int t=0; t<9; t++){
            for(int b=0; b<9; b++){
                puzzle[t][b] = 10;
                tiles[t][b].setText("");
            }}
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
            }}
     return wrong;
 }
  
    
    // Setup the  GUI
    Sudoku(){
        
        
        // Set Look and Feel
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (Exception e) {}

        setSize(500, 500); // Set the initial size of the application window
        setLayout(new BorderLayout()); // Set the layout as border for better apperance
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes the window close when the "X" is pressed
        
        // Create the Sudoku Main Grid
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(9,9));
        tiles = new JTextField[9][9];

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
                tiles[i][j] = new JTextField(2);
                tiles[i][j].setBorder(new MatteBorder(top,left,bottom,right,Color.BLACK));
                grid.add(tiles[i][j]);
                tiles[i][j].setText("");
                tiles[i][j].setHorizontalAlignment(JTextField.CENTER);
            }
        }
        
        // Create the Conrtol Buttons
        JPanel control = new JPanel();
        JButton btnReset = new JButton("Reset");
        JButton btnCheck = new JButton("Check");
        control.add(btnReset);
        control.add(btnCheck);
        
        // Here are the action listeners for the buttons
        btnReset.addActionListener((ActionEvent e) -> {
            // This is where the filling action would go
         GeneratePuzzle();
        });
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
        
        // Add all the panels to the Frame
        add(grid, BorderLayout.CENTER);
        add(control, BorderLayout.SOUTH);
        
        //Make frame visible
        setVisible(true);
    }
    
    // This is the way to launch the GUI
    public static void main (String[] args){
          
        Sudoku GUI = new Sudoku();
        GeneratePuzzle();
    }

    
}
