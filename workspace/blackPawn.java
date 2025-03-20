
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Piece {
    private final boolean color;
    private BufferedImage img;
    
    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;
        
        try {
            if (this.img == null) {
              this.img = ImageIO.read(getClass().getResource(img_file));
            }
          } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
          }
    }
    
    public boolean getColor() {
        return color;
    }
    
    public Image getImage() {
        return img;
    }
    
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        
        g.drawImage(this.img, x, y, null);
    }
    
    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
    //Pre-condition: board must be 2d array. start must be valid square.
    //Post-condition: returns an array list of all the squares the pawn controls. 
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
      //check if piece occupying places are same color or not //let us know what squares it is controlling 
      ArrayList<Square> controlledSquares = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        int direction;
      if(start.getOccupyingPiece().getColor()==true){
        //white pawn
        direction=-1;
      }
      else{
        //black pawn
        direction=1;
      }
      if (row + direction>=0 && row +direction<8 && col - 1>=0) {
        controlledSquares.add(board[row + direction][col - 1]);
    }
    if (row+direction >= 0 && row + direction <8 && col+ 1 <8) {
        controlledSquares.add(board[row + direction][col + 1]);
    }
    return controlledSquares;
    }
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.

    //Pre-condition: there must be a Board and Square objects. start should be valid square. board should be 2d array
    //Post-condition: returns an array list of possible moves the pawn can do.
    //Pawn rules: The black pawn can move one square at a time, forward only. But it can move two squares if it is it's first move (so if the piece is at row 1).
    //to capture a piece, the piece has to be diagonal from the pawn, in front. the pawn can capture the piece if the piece is in front and diagonal.
    public ArrayList<Square> getLegalMoves(Board b, Square start){

      //pawn //if black pawn, row and column num increases
      ArrayList<Square> moves = new ArrayList<Square>();
      
      /* Your legal moves incorrectly allow pieces to capture other pieces of their own type.
       Further your pawns are allowed to capture forwards which is incorrect! Lastly you should ensure that if you are a white pawn (color == true)
        you should be moving the other direction on the board!!! Currently your pieces will error out if they are on the edge of the board as well
        so you should prevent them from trying to access squares that are out of bounds.  */
      int direction;
      if(start.getOccupyingPiece().getColor()==true){
        //white pawn
        direction=-1;
      }
      else{
        //black pawn
        direction=1;
      }
      if(start.getRow()+direction>=0 && start.getRow()+direction<8){
        if(!(b.getSquareArray()[start.getRow() + direction][start.getCol()]).isOccupied()){
          moves.add(b.getSquareArray()[start.getRow() + direction][start.getCol()]);
          if ((start.getOccupyingPiece().getColor()==true && start.getRow()== 6) || (!start.getOccupyingPiece().getColor()==true && start.getRow()== 1)) { 
            if (!(b.getSquareArray()[start.getRow() + 2 * direction][start.getCol()]).isOccupied()) { 
                moves.add(b.getSquareArray()[start.getRow() + 2 * direction][start.getCol()]);
            }
        }
        }
      }
//left capture
      if (start.getRow() + direction >= 0 && start.getRow() + direction < 8 && start.getCol() - 1 >= 0) {
        Square leftCapture = b.getSquareArray()[start.getRow() + direction][start.getCol() - 1];
        if (leftCapture.isOccupied() && leftCapture.getOccupyingPiece().getColor() != start.getOccupyingPiece().getColor()==true) {
            moves.add(leftCapture);
        }
    }
//right capture
      if (start.getRow() + direction >= 0 && start.getRow() + direction < 8 && start.getCol() + 1 < 8) {
        Square rightCapture = b.getSquareArray()[start.getRow() + direction][start.getCol() + 1];
        if (rightCapture.isOccupied() && rightCapture.getOccupyingPiece().getColor() != start.getOccupyingPiece().getColor()==true) {
            moves.add(rightCapture);
        }
      }
      return moves;
    }
}