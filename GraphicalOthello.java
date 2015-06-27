import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * 
 * 
 * @author Obinna Elobi 100953254 
 * @version Assign5
 */
public class GraphicalOthello extends Othello implements ActionListener
{
    // instance variables - replace the example below with your own
    private JFrame gameFrame;
    private JButton[][] buttons;
    private JPanel gridPanel;
    private JPanel gridPanel2;
    private static final int hght = 600;
    private static final int wth = 600;
   private static boolean NoMovesHum = false;
      private static  boolean NoMovesCom = false;
    /**
     * Constructor for objects of class GraphicalOthello
     */
    public GraphicalOthello()
    {
        
       
       gameFrame = new JFrame("Othello Game");
       gridPanel = new JPanel();
       gridPanel2 = new JPanel();
       
       JButton Random = new JButton("Random");
       Random.setActionCommand("Random");
       Random.addActionListener(this);
       
       JButton FAM = new JButton("FAM");
       FAM.setActionCommand("FAM");
       FAM.addActionListener(this);
       
       JButton Greedy = new JButton("Greedy");
       Greedy.setActionCommand("Greedy");
       Greedy.addActionListener(this);
       
       JButton Hint = new JButton("Hint");
       Hint.setActionCommand("Hint");
       Hint.addActionListener(this);
       
       //ButtonListener listener = new ButtonListener();
       //Container con = gameFrame.getContentPane();
       Start();
       //GridLayout OthLayout = new GridLayout(SIZE,SIZE);
       gameFrame.setLayout(new BorderLayout());
       gridPanel.setLayout(new GridLayout(SIZE,SIZE));
       gridPanel2.setLayout(new GridLayout(1,4));
       buttons = new JButton[SIZE][SIZE];
       gridPanel2.add(Random);
       gridPanel2.add(FAM);
       gridPanel2.add(Greedy); 
       gridPanel2.add(Hint);
       for (int i = 0; i < SIZE; i++) {
          for (int j = 0; j < SIZE; j++){
                String str = "" + grid[i][j];
                buttons[i][j] = new JButton("");
                buttons[i][j].setBackground(Color.green);
               // buttons[i][j].setPreferredSize(new Dimension(100,50));
                buttons[i][j].setBorder(null);
               if(grid[i][j]=='_'){
                            buttons[i][j].setIcon(new ImageIcon("nopiece.jpg"));
                            //buttons[i][j].setText("x");
                        }
                if(grid[i][j]=='x'){
                  buttons[i][j].setIcon(new ImageIcon("blackpiece.png"));
                            //buttons[i][j].setText("x");
                           
                        }
                if(grid[i][j]=='o'){
                  buttons[i][j].setIcon(new ImageIcon("whitepiece.png"));
                            //buttons[i][j].setText("o");
                        }        
                buttons[i][j].addActionListener(this);
                buttons[i][j].setActionCommand("" + i + j);
                gridPanel.add(buttons[i][j]);
               
                
            }
        }
       //con.add(gridPanel,OthLayout);
       gameFrame.getContentPane().add(gridPanel,BorderLayout.NORTH);
       gameFrame.getContentPane().add(gridPanel2,BorderLayout.SOUTH);
       gameFrame.setSize(wth, hght);
       gameFrame.setDefaultCloseOperation(gameFrame.EXIT_ON_CLOSE);
       gameFrame.pack();
       gameFrame.setVisible(true);
    }
 
    public void actionPerformed(ActionEvent event){
        print();
       
       JButton button = (JButton) event.getSource();
       
       String command = event.getActionCommand();
       
       if (command == "FAM") setMoveStrategy(new FirstAvailableMove());
       else if (command == "Random")setMoveStrategy(new RandomMove());
       else if (command == "Greedy")setMoveStrategy(new GreedyMove());
       else if (command == "Hint"){
            
            for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid.length; j++) { 
                        Move nmove = new Move(i,j);
                        
                        for(int k = 0; k < generateMoves().size();k++){
                                if(generateMoves().get(k).getRow()== nmove.getRow());
                                else if( generateMoves().get(k).getCol()== nmove.getCol());
                                else if( canPlay(nmove)){
                                    //buttons[i][j] = new JButton();
                                buttons[nmove.getRow()][nmove.getCol()].setIcon(new ImageIcon("hint.png"));
                            }
                        }
                    }
                }
           
        }
       
       else{  
           for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid.length; j++) { 
                        if(grid[i][j]=='_'){
                            buttons[i][j].setIcon(new ImageIcon("nopiece.jpg"));
                            //buttons[i][j].setText("x");
                        }
                        if(grid[i][j]=='x'){
                            buttons[i][j].setIcon(new ImageIcon("blackpiece.png"));
                            //buttons[i][j].setText("x");
                           
                        }
                        if(grid[i][j]=='o'){
                            buttons[i][j].setIcon(new ImageIcon("whitepiece.png"));
                            //buttons[i][j].setText("o");
                        }        
                    }
                }
           
           int i = Integer.parseInt(command.substring(0, 1));
           int j = Integer.parseInt(command.substring(1, 2));
           Move move = new Move(i, j);
           int status;
           if(canPlay(move)){
               status = play(move);
               copyGrid();
               toggleTurn();
            } else{ 
                
                return;}
       
            status = ONGOING;
            while (status == ONGOING){
                if(!(generateMoves().isEmpty())){ 
                    status = machinePlay(); 
                    copyGrid();
                    print();
                    NoMovesCom = false;
                    toggleTurn();
                    
                    
        
                    if(!(generateMoves().isEmpty())){  
                        if(canPlay(move)){  
                            status = play(move);
                            copyGrid();
                            NoMovesHum = false;
                            toggleTurn();
                        }
                        else{ 
                            
                            return;}
                    }
                 /*   else if(!(generateMoves().isEmpty())) { 
                    status = machinePlay();
                    copyGrid();
                    print();
                    toggleTurn();
                    
                }*/
                else { 
                    print();
                       NoMovesHum = true;
                       //NoMovesCom = true;
                status = machinePlay();
                toggleTurn();
                //DisplayMess();
                //reStart();
            }  
         }
               /* else if(!(generateMoves().isEmpty())){ 
                    if(canPlay(move)){  
                        status = play(move);
                        copyGrid();
                        toggleTurn();
                    } else{ 
                         
                        return;}
                }*/
         else{
            print();
             // NoMovesHum = false;
              NoMovesCom = true;
             status = play(move);
             toggleTurn();
           // DisplayMess();
            // reStart();
             
                }  
          
          GameOver();  
        }
          
    }
   
}
    

    

    private void Start(){
          
            //Add your questions to the frame
            
        int output = JOptionPane.showConfirmDialog(gameFrame,"DO YOU WANT TO START(BE BLACK PIECE)?", " :P ", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
        
        
        if(output == JOptionPane.YES_OPTION)return;
        else if(output == JOptionPane.NO_OPTION){
            status = machinePlay();
            toggleTurn();
            return;
        }  
        else System.exit(0);
        
    }
    
    private void reStart(){            
        int output = JOptionPane.showConfirmDialog(gameFrame,"DO YOU WANT TO PLAY AGAIN?", " :P ", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
           
        if(output == JOptionPane.YES_OPTION){
            GraphicalOthello un = new GraphicalOthello();
        }
          else  if(output == JOptionPane.NO_OPTION){
            System.exit(0);
        }  
        else System.exit(0);
    }
    
    private void GameOver(){
       
         for (int t = 0; t < grid.length; t++) {
                    for (int r = 0; r < grid.length; r++) {
                        if(grid[t][r]!='_'){
                         status = GAME_OVER;  
                         
                        }
                    }
                }
         if(NoMovesCom && NoMovesHum == true){
              status = GAME_OVER;
                
            }         
        determineWinner();
         if(status == X_WON)JOptionPane.showMessageDialog(null, "BLACK WON THIS!");
         if(status == O_WON)JOptionPane.showMessageDialog(null, "WHITE WON THIS!");
         if(status == TIE)JOptionPane.showMessageDialog(null, "IT'S A TIE GOOD GAME");
         
         reStart();
        }
        
            public void print(){
                String s = "";
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid.length; j++) {
                        if(grid[i][j]=='x'){
                            buttons[i][j].setIcon(new ImageIcon("blackpiece.png"));
                            //buttons[i][j].setText("x");
                        }
                        if(grid[i][j]=='o'){
                            buttons[i][j].setIcon(new ImageIcon("whitepiece.png"));
                            //buttons[i][j].setText("o");
                        }
                         if(grid[i][j]=='_'){
                            buttons[i][j].setIcon(new ImageIcon("nopiece.jpg"));
                            //buttons[i][j].setText("x");
                        }
                }
            }
            s += "\n";
            
        }
    
         public void copyGrid()
  {
        for(int i = 0; i < SIZE ;i++){
           for(int j=0 ; j < SIZE; j++){
                String place = "" + grid[i][j];
                //buttons[i][j].setText(place);
            }
        }
    }
    
    
            public static void main(String[] args){
      
      GraphicalOthello un = new GraphicalOthello();
      
     
    }
   
}           
