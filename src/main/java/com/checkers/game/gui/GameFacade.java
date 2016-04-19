/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkers.game.gui;

import com.checkers.game.ai.MinMaxEvaluator;
import com.checkers.game.board.Board;
import com.checkers.game.board.actions.GamePieceAction;
import com.checkers.game.board.actions.Jump;
import com.checkers.game.board.actions.Move;
import com.checkers.game.board.piece.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author maass
 */
public class GameFacade {
    PlayerType redType,blackType;
    MinMaxEvaluator redAI,blackAI;
    Board b;
    Random r;
    
    public GameFacade(PlayerType redType, PlayerType blackType, boolean isRedTurn){
        b=new Board();
        if(isRedTurn)
            b.setIsRedTurn(isRedTurn);
        redAI = new MinMaxEvaluator(Color.RED, 7, !isRedTurn, true);
        blackAI = new MinMaxEvaluator(Color.BLACK, 7, !isRedTurn, true);
        this.redType = redType;
        this.blackType = blackType;
        r = new Random();
    }
    
    public ArrayList<String> getRedActionStrings(){
        ArrayList<String> str = new ArrayList<>();
        if(redType==PlayerType.Human){
             
            str=b.getRedActionStrings();
        }
        else{
            str.add(getRedAction().getKey());
        }
        
        return str;
    }
    
    public ArrayList<String> getBlackActionStrings(){
        ArrayList<String> str = new ArrayList<>();
        if(blackType==PlayerType.Human){
            
            str=b.getBlackActionStrings();
        }
        else{
            str.add(getBlackAction().getKey());
        }
        
        return str;
    }
    
    private GamePieceAction getRedAction(){
        if(redType==PlayerType.Random){
            if(b.getRedJumps().isEmpty())
                return b.getRedMoves().get(r.nextInt(b.getRedMoves().size()));
            else
                return b.getRedJumps().get(r.nextInt(b.getRedJumps().size()));
        }
        else{
            if(b.getRedJumps().isEmpty())
                return (redAI.getBestAction(b));
            else
                return (redAI.getBestAction(b));
        }
    }
    
    private GamePieceAction getBlackAction(){
        if(blackType==PlayerType.Random){
            if(b.getBlackJumps().isEmpty())
                return b.getBlackMoves().get(r.nextInt(b.getBlackMoves().size()));
            else
                return b.getBlackJumps().get(r.nextInt(b.getBlackJumps().size()));
        }
        else{
            if(b.getBlackJumps().isEmpty())
                return (blackAI.getBestAction(b));
            else
                return (blackAI.getBestAction(b));
        }
    }
    
    public void executeAction(String play, Color c){
        if(c==Color.RED){
            b.executeRedActionFromString(play);
        }
        else{
            b.executeBlackActionString(play);
        }
    }
    
    
}
