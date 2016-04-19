package com.checkers.game.ai;

import com.checkers.game.board.Board;
import com.checkers.game.board.piece.Color;
import com.checkers.game.board.piece.GamePiece;

public abstract class AbstractFunction {
	protected Color color;
	protected int[][] oppMapBlack;
	protected int[][] oppMapRed;
	protected double blackValue;
	protected double redValue;
	
	protected double[][] redMoveValues = {
			{0,16,0,16,0,16,0,16},
			{14,0,14,0,14,0,14,0},
			{0,12,0,12,0,12,0,12},
			{10,0,10,0,10,0,10,0},
			{0,8,0,8,0,8,0,8},
			{6,0,6,0,6,0,6,0},
			{0,4,0,4,0,4,0,4},
			{2,0,2,0,2,0,2,0}};
	
	protected double[][] blackMoveValues = {
			{0,2,0,2,0,2,0,2},
			{4,0,4,0,4,0,4,0},
			{0,6,0,6,0,6,0,6},
			{8,0,8,0,8,0,8,0},
			{0,10,0,10,0,10,0,10},
			{12,0,12,0,12,0,12,0},
			{0,14,0,14,0,14,0,14},
			{16,0,16,0,16,0,16,0}};
	
	public AbstractFunction(Color color){
		this.color=color;
		oppMapBlack= new int[8][8];
		oppMapRed= new int[8][8];
	}
	
	protected void generateMapRed(Board b){
        int mapComplete=0;
        for(int i =0;i<8;i++){
        	for(int q = 0;q<8;q++){
        		oppMapRed[i][q]=0;
        	}
        }
        //find all enemy men
        GamePiece[][] board = b.getBoard();
        for(int i =0;i<8;i++){
            for(int q = 0;q<8;q++){
                if(board[i][q]!=null && board[i][q].isOppositecolor(Color.RED)){
                	oppMapRed[i][q]=0;
                }
            }
        }
        
        do{
            mapComplete=0;
            for (int i = 0; i < 8; i++) {
                for (int q = (i % 2 == 1 ? 0 : 1); q < 8; q += 2) {
                    if (oppMapRed[i][q]>0) {
                        if(i-1>-1 && q+1<8 &&oppMapRed[i-1][q+1]<oppMapRed[i][q]-2){
                            oppMapRed[i-1][q+1]=oppMapRed[i][q]-2;
                            mapComplete++;
                        }
                        if(i-1>-1 && q-1>-1 &&oppMapRed[i-1][q-1]<oppMapRed[i][q]-2){
                            oppMapRed[i-1][q-1]=oppMapRed[i][q]-2;
                            mapComplete++;
                        }
                        if(i+1<8 && q+1<8 &&oppMapRed[i+1][q+1]<oppMapRed[i][q]-2){
                            oppMapRed[i+1][q+1]=oppMapRed[i][q]-2;
                            mapComplete++;
                        }
                        if(i+1<8 && q-1>-1 &&oppMapRed[i+1][q-1]<oppMapRed[i][q]-2){
                            oppMapRed[i+1][q-1]=oppMapRed[i][q]-2;
                            mapComplete++;
                        }
                        
                    }
                }
                
            }
        }while(mapComplete!=0);
    }
	
	protected void generateMapBlack(Board b){
        int mapComplete=0;
//        int count = 0;
        for(int i =0;i<8;i++){
        	for(int q = 0;q<8;q++){
        		oppMapBlack[i][q]=0;
        	}
        }
        //find all enemy men
        GamePiece[][] board = b.getBoard();
        for(int i =0;i<8;i++){
            for(int q = 0;q<8;q++){
                if(board[i][q]!=null && board[i][q].isOppositecolor(Color.BLACK)){
                    oppMapBlack[i][q]=16;
                }
            }
        }
        
        do{
            mapComplete=0;
            for (int i = 0; i < 8; i++) {
                for (int q = (i % 2 == 1 ? 0 : 1); q < 8; q += 2) {
                    if (oppMapBlack[i][q]>0) {
                        if(i-1>-1 && q+1<8 &&oppMapBlack[i-1][q+1]<oppMapBlack[i][q]-2){
                            oppMapBlack[i-1][q+1]=oppMapBlack[i][q]-2;
                            mapComplete++;
                        }
                        if(i-1>-1 && q-1>-1 &&oppMapBlack[i-1][q-1]<oppMapBlack[i][q]-2){
                            oppMapBlack[i-1][q-1]=oppMapBlack[i][q]-2;
                            mapComplete++;
                        }
                        if(i+1<8 && q+1<8 &&oppMapBlack[i+1][q+1]<oppMapBlack[i][q]-2){
                            oppMapBlack[i+1][q+1]=oppMapBlack[i][q]-2;
                            mapComplete++;
                        }
                        if(i+1<8 && q-1>-1 &&oppMapBlack[i+1][q-1]<oppMapBlack[i][q]-2){
                            oppMapBlack[i+1][q-1]=oppMapBlack[i][q]-2;
                            mapComplete++;
                        }
                        
                    }
                }
                
            }
//            count++;
//            if(count%500==0){
//            	for (int i = 0; i < 8; i++) {
//                    for (int q = (i % 2 == 1 ? 0 : 1); q < 8; q += 2) {
//                        System.out.print(oppMapBlack[i][q]+" ");
//                    }
//                    System.out.println();
//                    
//                }
//            	System.out.println();
//            }
//            if(count==10000){
//            	System.exit(0);
//            }
        }while(mapComplete!=0);
    }
	
	protected double pieceWeight(Board b){
//		System.out.println("pice weight");
		double kingWeight =6.0;
		if(color==Color.BLACK){
			return (b.getNumBlackMen()+kingWeight*b.getNumBlackKing())-(b.getNumRedMen()+kingWeight*b.getNumRedKing());
		}
		else{
			return (b.getNumRedMen()+kingWeight*b.getNumRedKing())-(b.getNumBlackMen()+kingWeight*b.getNumBlackKing());
		}
	}
	
	abstract double manValueMove(Board b);
	abstract double kingValueMove(Board b);
	abstract double jumpValue(Board b);
	abstract double findBoardValue(Board b);
}
