/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkers.game.gui;

import com.checkers.game.board.Board;
import com.checkers.game.board.actions.GamePieceAction;
import com.checkers.game.board.actions.Jump;
import com.checkers.game.board.actions.Move;
import com.checkers.game.board.piece.Color;
import com.checkers.game.board.piece.GamePiece;
import com.checkers.game.board.piece.Type;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author maass
 */
public class CheckersBoard extends JPanel{
    
    private Square[][] squares = new Square[8][8];
    
    public CheckersBoard(Board b){

        setBoard(b);
        
    }
   
    
    private void setBoard(Board b) {
        for(int i = 0;i<8;i++){
            for(int q = 0; q<8;q++){
                squares[i][q] = new Square(i,q);
                if(i%2!=0){
                    if(q%2!=0){
                        squares[i][q].setIsRedSquare(true);
                    }
                    else if(b.getBoard()[i][q]!=null){
                        setTypeAndColor(b.getBoard()[i][q], squares[i][q]);
                    }
                }
                else{
                    if(q%2==0)
                        squares[i][q].setIsRedSquare(true);
                    else if(b.getBoard()[i][q]!=null){
                        setTypeAndColor(b.getBoard()[i][q], squares[i][q]);
                    }
                }
                
                
                this.add(squares[i][q]);
                
            }
        }
    }
    
    private void setTypeAndColor(GamePiece ga, Square s){
        if(ga.getColor()==Color.RED){
            if(ga.getType()==Type.KING)
                s.setIsRedKing(true);
            else
                s.setIsRedMan(true);
        }
        else{
            if(ga.getType()==Type.KING)
                s.setIsBlackKing(true);
            else
                s.setIsBlackMan(true);
        }
    }
    
    public void resetBoard(Board b){
        updateBoard(b);
    }
    
    public void highlightAction(GamePieceAction ga){
        undueHighlight();
        if(ga instanceof Jump){
            Jump j = (Jump)ga;
            squares[ga.getStartPosition().x][ga.getStartPosition().y].setIsHighlighted(true);
            squares[ga.getEndPosition().x][ga.getEndPosition().y].setIsHighlighted(true);
            j.getMenJumpedPositions().stream()
                    .forEach(pos -> squares[pos.x][pos.y].setIsHighlighted(true));
            j.getMenPositions().stream()
                    .forEach(pos -> squares[pos.x][pos.y].setIsHighlighted(true));
            
        }
        else{
            Move m = (Move)ga;
            squares[ga.getStartPosition().x][ga.getStartPosition().y].setIsHighlighted(true);
            squares[ga.getEndPosition().x][ga.getEndPosition().y].setIsHighlighted(true);
        }
    }
    
    public void undueHighlight(){
        for(int i = 0;i<8;i++){
            for(int q = 0; q<8;q++){
                squares[i][q].setIsHighlighted(false);
            }
        }
        repaint();
    }
    
    public void updateBoard(Board b){
        for(int i = 0;i<8;i++){
            for(int q = 0; q<8;q++){
                squares[i][q].setNoPiece();
                if(b.getBoard()[i][q]!=null){
                    //System.out.println(i+":"+q);
                    setTypeAndColor(b.getBoard()[i][q], squares[i][q]);
                }
                
            }
        }
        repaint();
    }
}
