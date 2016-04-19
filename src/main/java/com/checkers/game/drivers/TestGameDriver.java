package com.checkers.game.drivers;

import java.util.Random;
import java.util.Scanner;

import com.checkers.game.ai.MinMaxEvaluator;
import com.checkers.game.board.Board;
import com.checkers.game.board.piece.Color;
import com.checkers.game.board.actions.GamePieceAction;
import com.checkers.game.board.actions.Jump;
import com.checkers.game.board.actions.Move;

public class TestGameDriver {

	public static void main(String[] args) {
		System.out.println("Making the Board...");
//		Stack<String> oldBoards = new Stack<>();
		Boolean didBlackGoFirst = true;
		Random r = new Random();
		GamePieceAction ga = null;
		int count =0;
		String s = "";
		MinMaxEvaluator eval = new MinMaxEvaluator(Color.BLACK,7,didBlackGoFirst,false);
		MinMaxEvaluator eval2 = new MinMaxEvaluator(Color.RED,7,didBlackGoFirst,true);
		Scanner scan = new Scanner(System.in);
		double time, totalTime=0.0;
		Board b = new Board();
		System.out.println(b.toString());
//		System.out.println();
//		while(count<10){

//		time = System.currentTimeMillis();
			while(b.endOfGame() && count<80){
				s = scan.nextLine();
				//oldBoards.push(b.toString());
//				if(s.equals("b"))
//					b.undueLastAction();
//				else 
					if(b.isIsRedTurn()){
					if(b.getRedJumps().size()>0){
//						ga=b.getRedJumps().get(r.nextInt(b.getRedJumps().size()));
						ga=eval2.getBestAction(b);
						System.out.println(((Jump)ga).toString());
						
						b.executeAction(ga);
					}
					else{
//						ga=b.getRedMoves().get(r.nextInt(b.getRedMoves().size()));
						
						ga=eval2.getBestAction(b);
						System.out.println(((Move)ga).toString());
						b.executeAction(ga);
						
					}
				}
				else{
					if(b.getBlackJumps().size()>0){
						ga=b.getBlackJumps().get(r.nextInt(b.getBlackJumps().size()));
//						ga=eval.getBestAction(b);
						System.out.println(((Jump)ga).toString());
						b.executeAction(ga);
					}
					else{
						ga=b.getBlackMoves().get(r.nextInt(b.getBlackMoves().size()));
						
//						ga=eval.getBestAction(b);
						System.out.println(((Move)ga).toString());
						b.executeAction(ga);
						
					}
				}
				
				System.out.println(b.toString());
//				System.out.println();
			}
//			time = System.currentTimeMillis()-time;
//			totalTime+=time;
//			System.out.println(b.toString());
//			System.out.println("Count: "+count+" Time: "+time);
//			count=0;
//		}
//		System.out.println("Average Time: "+(totalTime/(count)));
//			
//			System.out.println("Old: \n"+oldBoards.peek());
//			System.out.println("Going back!");
//			while(b.undueLastAction()){
//				
//				if(!b.toString().equals(oldBoards.peek())){
//					System.out.println("Reverse: \n"+b.toString());
//					System.out.println("Old: \n"+oldBoards.peek());
//					System.out.println(count);
//					System.exit(0);
//				}
//				oldBoards.pop();
//				
////				System.out.println(b.toString()+"\n");
//			}
////			System.out.println(b.toString());
////			System.out.println();
////			System.out.println("Keep checking?  ");
////			s = scan.nextLine();
//			if(count%1000==0)
//				System.out.println(count);
//			count++;
//		}
		
	}

}
