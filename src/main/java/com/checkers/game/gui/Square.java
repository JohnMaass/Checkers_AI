/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkers.game.gui;

import com.checkers.game.board.BoardConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author maass
 */
public class Square extends JPanel{
    
    private boolean isRedSquare=false;
    private boolean isRedMan=false,isRedKing=false;
    private boolean isBlackMan=false,isBlackKing=false;
    private boolean isHighlighted=false;
    private String piece = null;
    private int row,col;
    
    public Square(int row, int col){
        
        this.row=row;
        this.col=col;

    }

    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setSize(80, 80);
        //square color
        if (isHighlighted) {
            g.setColor(Color.YELLOW);
            g.fillRect(0, 0,80, 80);
        } else {
            if (isRedSquare) {
                g.setColor(Color.red);
                g.fillRect(0, 0,80, 80);
                
            } else {
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0, 0,80, 80);
                g.setColor(Color.white);
                g.drawString("(" + row + "," + col + ") "+BoardConstants.boardNotation[row][col], 0, 12);
            }
        }
        
        
//        g.setColor(Color.CYAN);
//        g.fillRect(5, 5, 70, 70);
        
        //Adds piece.
        
            if (isRedMan) {
                g.setColor(Color.white);
                g.fillOval(15, 15, 50, 50);
                g.setColor(Color.red);
                g.fillOval(16, 16, 48, 48);
            }
            else if (isBlackMan) {
                g.setColor(Color.white);
                g.fillOval(15, 15, 50, 50);
                g.setColor(Color.black);
                g.fillOval(16, 16, 48, 48);
            }
            else if(isRedKing){
                g.setColor(Color.white);
                g.fillOval(15, 15, 50, 50);
                g.setColor(Color.red);
                g.fillOval(16, 16, 48, 48);
                g.setColor(Color.white);
                g.drawString("RK", 35, 45);
            }
            else if(isBlackKing){
                g.setColor(Color.white);
                g.fillOval(15, 15, 50, 50);
                g.setColor(Color.black);
                g.fillOval(16, 16, 48, 48);
                g.setColor(Color.white);
                g.drawString("BK", 35, 45);
            }   
    }
    
    public void setIsRedSquare(boolean isRedSquare) {
        this.isRedSquare = isRedSquare;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public void setIsHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    public void ResetSquare(){
        
    }

    public void setIsRedMan(boolean isRedMan) {
        this.isRedMan = isRedMan;
    }

    public void setIsRedKing(boolean isRedKing) {
        this.isRedKing = isRedKing;
    }

    public void setIsBlackMan(boolean isBlackMan) {
        this.isBlackMan = isBlackMan;
    }

    public void setIsBlackKing(boolean isBlackKing) {
        this.isBlackKing = isBlackKing;
    }
    
    public void setNoPiece(){
        isRedKing=false;
        isRedMan=false;
        isBlackKing=false;
        isBlackMan=false;
        isHighlighted=false;
    }
}
