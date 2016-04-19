package com.checkers.game.board;

import com.checkers.game.board.piece.Color;
import com.checkers.game.board.piece.Type;
import com.checkers.game.board.piece.GamePiece;
import com.checkers.game.board.piece.RedPiece;
import com.checkers.game.board.piece.BlackPiece;
import com.checkers.game.board.actions.GamePieceAction;
import com.checkers.game.board.actions.Jump;
import com.checkers.game.board.actions.Move;
import java.awt.Point;
import java.util.ArrayList;

/**
 * The Board class contains the representation of the board, a list of all past
 * actions, piece totals, current turn and more. It also contains methods to
 * update the board based on actions choosen by a user or ai.
 *
 * @author maass
 */
public class Board {

    private ArrayList<GamePieceAction> pastActions;
    private ArrayList<GamePieceAction> pastJumps;
    private ArrayList<GamePieceAction> pastMoves;

    private ArrayList<Move> blackMoves;
    private ArrayList<Move> redMoves;
    private ArrayList<Jump> blackJumps;
    private ArrayList<Jump> redJumps;

    private ArrayList<GamePiece> redPieces;
    private ArrayList<GamePiece> blackPieces;

    private ArrayList<GamePiece> storeAll;

    private GamePiece[][] board;

    private int numBlackMen;
    private int numRedMen;
    private int numBlackKing;
    private int numRedKing;

    private int numTurns;
    private boolean isRedTurn;

    public Board() {
        blackMoves = new ArrayList<>();
        redMoves = new ArrayList<>();
        blackJumps = new ArrayList<>();
        redJumps = new ArrayList<>();
        redPieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        pastActions = new ArrayList<>();
        pastJumps = new ArrayList<>();
        pastMoves = new ArrayList<>();

        storeAll = new ArrayList<>();

        board = new GamePiece[8][8];
        numBlackMen = 12;
        numRedMen = 12;
        numBlackKing = 0;
        numRedKing = 0;
        numTurns = 0;
        isRedTurn = false;

        //Add pieces to board
        boolean addBlack = true;
        for (int i = 0; i < 8; i++) {
            //skips empty rows in middle
            if (i == 3) {
                i++;
                addBlack = false;
                continue;
            }

            for (int q = (i % 2 == 1 ? 0 : 1); q < 8; q += 2) {
                if (addBlack) {
                    board[i][q] = new BlackPiece(Type.MAN, i, q, board);
                    storeAll.add(board[i][q]);
                } else {
                    board[i][q] = new RedPiece(Type.MAN, i, q, board);
                    storeAll.add(board[i][q]);
                }
            }
        }

        //Find all moves
        updateMovesAndJumps();
        countPieces();
    }

    public Board(String representation) {
        String[] piece;

        blackMoves = new ArrayList<>();
        redMoves = new ArrayList<>();
        blackJumps = new ArrayList<>();
        redJumps = new ArrayList<>();
        redPieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        pastActions = new ArrayList<>();
        pastJumps = new ArrayList<>();
        pastMoves = new ArrayList<>();

        storeAll = new ArrayList<>();

        board = new GamePiece[8][8];
        numBlackMen = 0;
        numRedMen = 0;
        numBlackKing = 0;
        numRedKing = 0;
        numTurns = 0;
        isRedTurn = false;

        int numRed = 0, numBlack = 0;
        //Add pieces to board
        for (String s : representation.split("\t")) {
            piece = s.split("-");
            if (piece[3].equals("RED")) {
                numRed++;
                if (piece[2].equals("MAN")) {
                    board[Integer.parseInt(piece[0])][Integer.parseInt(piece[1])] = new RedPiece(Type.MAN, Integer.parseInt(piece[0]), Integer.parseInt(piece[1]), board);
                } else {
                    board[Integer.parseInt(piece[0])][Integer.parseInt(piece[1])] = new RedPiece(Type.KING, Integer.parseInt(piece[0]), Integer.parseInt(piece[1]), board);
                }
            } else {
                numBlack++;
                if (piece[2].equals("MAN")) {
                    board[Integer.parseInt(piece[0])][Integer.parseInt(piece[1])] = new BlackPiece(Type.MAN, Integer.parseInt(piece[0]), Integer.parseInt(piece[1]), board);
                } else {
                    board[Integer.parseInt(piece[0])][Integer.parseInt(piece[1])] = new BlackPiece(Type.KING, Integer.parseInt(piece[0]), Integer.parseInt(piece[1]), board);
                }
            }
        }

        for (int i = 0; i < 12 - numRed; i++) {
            redPieces.add(new RedPiece(Type.MAN, 0, 0, board));
        }

        for (int i = 0; i < 12 - numBlack; i++) {
            blackPieces.add(new BlackPiece(Type.MAN, 0, 0, board));
        }
        //Find all moves
        updateMovesAndJumps();
        countPieces();
    }

    @Override
    public String toString() {
        String b = "";
        for (int i = 0; i < 8; i++) {
            for (int q = 0; q < 8; q++) {
                if (board[i][q] == null) {
                    b += " o ";
                } else if (board[i][q].getColor() == Color.RED) {
                    if (board[i][q].getType() == Type.MAN) {
                        b += " r ";
                    } else {
                        b += " R ";
                    }
                } else if (board[i][q].getType() == Type.MAN) {
                    b += " b ";
                } else {
                    b += " B ";
                }
            }
            b += "\n";
        }
        return b;
    }

    private void updateMovesAndJumps() {
        redMoves.clear();
        blackMoves.clear();
        redJumps.clear();
        blackJumps.clear();
        for (GamePiece[] ar : board) {
            for (GamePiece gp : ar) {
                if (gp != null) {
                    if (gp.getColor() == Color.RED) {
                        redMoves.addAll(gp.findMoves());
                        redJumps.addAll(gp.findJumps());
                    } else {
                        blackMoves.addAll(gp.findMoves());
                        blackJumps.addAll(gp.findJumps());
                    }
                }
            }
        }
    }

    private void updateBoard() {
        updateMovesAndJumps();
        countPieces();
    }

    public void executeAction(GamePieceAction action) {

        if (action instanceof Jump) {
            pastActions.add(new Jump(action));
            movePiece(((Jump) action).getStartPosition(), ((Jump) action).getEndPosition(), action);
            removePiece(((Jump) action).getColor(), ((Jump) action).getMenJumpedPositions());
        } else {
            pastActions.add(new Move(action));
            movePiece(((Move) action).getStartPosition(), ((Move) action).getEndPosition(), action);
        }
        updateBoard();
        isRedTurn = !isRedTurn;
    }

    public boolean undueLastAction() {
        if (pastActions.size() > 0) {
            undueAction(pastActions.remove(pastActions.size() - 1));
            return true;
        }
        return false;
    }

    public void undueAction(GamePieceAction action) {
        if (action instanceof Jump) {
            addPiece(((Jump) action).getEndPosition(), ((Jump) action).getStartPosition(), action);
            addPieces(((Jump) action).getColor(), ((Jump) action).getMenJumpedPositions(), ((Jump) action).getJumpedMenTypes());
        } else {
            addPiece(((Move) action).getEndPosition(), ((Move) action).getStartPosition(), action);
        }
        updateBoard();
        isRedTurn = !isRedTurn;
    }

    private void addPieces(Color color, ArrayList<Point> pieces, ArrayList<Type> t) {
        for (int i = 0; i < pieces.size(); i++) {
            if (color == Color.RED) {
                board[pieces.get(i).x][pieces.get(i).y] = blackPieces.remove(0);
                board[pieces.get(i).x][pieces.get(i).y].setPosition(pieces.get(i).x, pieces.get(i).y);
                board[pieces.get(i).x][pieces.get(i).y].setPiece(t.get(i));

            } else {
                board[pieces.get(i).x][pieces.get(i).y] = redPieces.remove(0);
                board[pieces.get(i).x][pieces.get(i).y].setPosition(pieces.get(i).x, pieces.get(i).y);
                board[pieces.get(i).x][pieces.get(i).y].setPiece(t.get(i));
            }
        }
    }

    private void removePiece(Color color, ArrayList<Point> pieces) {
        for (Point p : pieces) {
            if (color == Color.RED) {
                blackPieces.add(board[p.x][p.y]);
            } else {
                redPieces.add(board[p.x][p.y]);
            }
            board[p.x][p.y] = null;
        }
    }

    private void addPiece(Point p1, Point p2, GamePieceAction action) {
        board[p2.x][p2.y] = board[p1.x][p1.y];
        board[p2.x][p2.y].setPosition(p2.x, p2.y);
        board[p2.x][p2.y].checkIfShouldDowngradeToMan(action);
        if (!p1.equals(p2)) {
            board[p1.x][p1.y] = null;
        }

    }

    private void movePiece(Point p1, Point p2, GamePieceAction action) {
        board[p2.x][p2.y] = board[p1.x][p1.y];
        board[p2.x][p2.y].setPosition(p2.x, p2.y);
        if (!p1.equals(p2)) {
            board[p1.x][p1.y] = null;
        }
        board[p2.x][p2.y].checkIfShouldUpgradeToKing(action);
    }

    private void countPieces() {
        numBlackMen = 0;
        numRedMen = 0;
        numBlackKing = 0;
        numRedKing = 0;
        for (GamePiece[] ar : board) {
            for (GamePiece gp : ar) {
                if (gp != null) {
                    if (gp.getColor() == Color.RED) {
                        if (gp.getType() == Type.KING) {
                            numRedKing++;
                        } else {
                            numRedMen++;
                        }
                    } else if (gp.getType() == Type.KING) {
                        numBlackKing++;
                    } else {
                        numBlackMen++;
                    }
                }
            }
        }
    }

    public String boardNotation() {
        String s = "";

        for (int i = 0; i < 8; i++) {
            for (int q = 0; q < 8; q++) {
                if (board[i][q] != null) {
                    s += BoardConstants.boardNotation[i][q] + "-" + board[i][q].getType() + "-" + board[i][q].getColor() + " ";
                }
            }
        }

        return s.trim();
    }

    public String boardNotationInversion() {
        String s = "";

        for (int i = 7; i > -1; i--) {
            for (int q = 7; q > -1; q--) {
                if (board[i][q] != null) {
                    s += BoardConstants.boardNotationInversion[i][q] + "-" + board[i][q].getType() + "-" + board[i][q].getColor().getOppositeColor() + " ";
                }
            }
        }

        return s.trim();
    }

    public String boardRepresentation() {
        String s = "";

        for (GamePiece[] g : board) {
            for (GamePiece ga : g) {
                if (ga != null) {
                    s += ga.getPosition().x + "-" + ga.getPosition().y + "-" + ga.getType() + "-" + ga.getColor() + "\t";
                }
            }
        }

        return s;
    }

    public boolean endOfGame() {
        return ((blackMoves.size() + blackJumps.size()) > 0 && !isRedTurn)
                || ((redMoves.size() + redJumps.size()) > 0 && isRedTurn);
    }
    
    public boolean didBlackWin(){
        return redMoves.size() + redJumps.size() == 0 && isRedTurn;
    }

    public ArrayList<Move> getBlackMoves() {
        return blackMoves;
    }

    public ArrayList<Move> getRedMoves() {
        return redMoves;
    }

    public ArrayList<Jump> getBlackJumps() {
        return blackJumps;
    }

    public ArrayList<Jump> getRedJumps() {
        return redJumps;
    }

    public int getNumBlackMen() {
        return numBlackMen;
    }

    public int getNumRedMen() {
        return numRedMen;
    }

    public int getNumBlackKing() {
        return numBlackKing;
    }

    public int getNumRedKing() {
        return numRedKing;
    }

    public void setIsRedTurn(boolean b) {
        isRedTurn = b;
    }

    public boolean isIsRedTurn() {
        return isRedTurn;
    }

    public GamePiece[][] getBoard() {
        return board;
    }

    public int numberOfTurns() {
        return pastActions.size();
    }

    public void executeBlackActionString(String str) {
        if (blackJumps.isEmpty()) {
            executeAction(blackMoves.stream()
                    .filter(move -> (move.getNotation() + " " + move.toString()).equals(str))
                    .findFirst()
                    .get()
            );
        } else {
            executeAction(blackJumps.stream()
                    .filter(jump -> (jump.getNotation() + " " + jump.toString()).equals(str))
                    .findFirst()
                    .get());
        }
    }

    public void executeRedActionFromString(String str) {
        if (redJumps.isEmpty()) {
            executeAction(redMoves.stream()
                    .filter(move -> (move.getNotation() + " " + move.toString()).equals(str))
                    .findFirst()
                    .get());

        } else {
            executeAction(redJumps.stream()
                    .filter(jump -> (jump.getNotation() + " " + jump.toString()).equals(str))
                    .findFirst()
                    .get());
        }
    }

    public GamePieceAction getBlackActionFromString(String str){
        if (blackJumps.isEmpty()) {
            return blackMoves.stream()
                    .filter(move -> (move.getNotation() + " " + move.toString()).equals(str))
                    .findFirst()
                    .get();
        } else {
            return blackJumps.stream()
                    .filter(jump -> (jump.getNotation() + " " + jump.toString()).equals(str))
                    .findFirst()
                    .get();
        }
    }
    
    public GamePieceAction getRedActionFromString(String str){
        if (redJumps.isEmpty()) {
            return redMoves.stream()
                    .filter(move -> (move.getNotation() + " " + move.toString()).equals(str))
                    .findFirst()
                    .get();

        } else {
            return redJumps.stream()
                    .filter(jump -> (jump.getNotation() + " " + jump.toString()).equals(str))
                    .findFirst()
                    .get();
        }
    }
    
    public ArrayList<String> getRedActionStrings() {
        ArrayList<String> actions = new ArrayList<>();
        if (redJumps.isEmpty()) {
            redMoves.stream().forEach(move -> actions.add(move.getNotation() + " " + move.toString()));
        } else {
            redJumps.stream().forEach(jump -> actions.add(jump.getNotation() + " " + jump.toString()));
        }
        return actions;
    }

    public ArrayList<String> getBlackActionStrings() {
        ArrayList<String> actions = new ArrayList<>();
        if (blackJumps.isEmpty()) {
            blackMoves.stream().forEach(move -> actions.add(move.getNotation() + " " + move.toString()));
        } else {
            blackJumps.stream().forEach(jump -> actions.add(jump.getNotation() + " " + jump.toString()));
        }
        return actions;
    }
}
