package com.checkers.game.board.actions;

import com.checkers.game.board.piece.Color;
import com.checkers.game.board.piece.Type;
import java.awt.Point;
import java.util.ArrayList;

/**
 * The Jump class extends the GamePieceAction class adds in a few method check
 * the number pieces jumps, their locations and types.
 *
 * @author maass
 */
public class Jump extends GamePieceAction {

    private ArrayList<Point> menJumpedPositions;
    private ArrayList<Point> menPositions;
    private ArrayList<Type> jumpedMenTypes;

    public Jump(Color color) {
        super(color);
        menJumpedPositions = new ArrayList<>();
        menPositions = new ArrayList<>();
        jumpedMenTypes = new ArrayList<>();
    }

    public Jump(GamePieceAction j) {
        super(j);
        menJumpedPositions = new ArrayList<>();
        menPositions = new ArrayList<>();
        jumpedMenTypes = new ArrayList<>();
        for (Point p : ((Jump) j).menJumpedPositions) {
            menJumpedPositions.add(new Point(p));
        }
        for (Point p : ((Jump) j).menPositions) {
            menPositions.add(new Point(p));
        }
        for (Type t : ((Jump) j).jumpedMenTypes) {
            jumpedMenTypes.add(t);
        }
    }

    @Override
    public GamePieceAction copyAction(GamePieceAction ga) {
        super.copyAction(ga);
        for (Point p : ((Jump) ga).menJumpedPositions) {
            menJumpedPositions.add(new Point(p));
        }
        for (Point p : ((Jump) ga).menPositions) {
            menPositions.add(new Point(p));
        }
        for (Type t : ((Jump) ga).jumpedMenTypes) {
            jumpedMenTypes.add(t);
        }
        return this;
    }

    public void setJump(ArrayList<Point> menPos, ArrayList<Point> menJumpedPos, ArrayList<Type> jumpedMenTypes, Type id) {
        setAction(menPos.get(0).x, menPos.get(0).y, menPos.get(menPos.size() - 1).x, menPos.get(menPos.size() - 1).y, id);
        menJumpedPositions.clear();
        menPositions.clear();
        this.jumpedMenTypes.clear();
        this.jumpedMenTypes.addAll(jumpedMenTypes);
        menJumpedPositions.addAll(menJumpedPos);
        menPositions.addAll(menPos);
    }

    public int getTotalMenJumped() {
        return menJumpedPositions.size();
    }

    public ArrayList<Point> getMenJumpedPositions() {
        return menJumpedPositions;
    }

    public ArrayList<Type> getJumpedMenTypes() {
        return jumpedMenTypes;
    }
    
    public ArrayList<Point> getMenPositions(){
        return menPositions;
    }

    @Override
    public String toString() {
        String s = "";
        s = jumpedMenTypes.stream().map((t) -> " " + t).reduce(s, String::concat);
        return getColor() + " " + getPieceID() + " (" + getStartPosition().getX() + ", " + getStartPosition().getY() + ") to ("
                + getEndPosition().getX() + ", " + getEndPosition().getY() + ")" + "Total: " + menJumpedPositions.size() + s;
    }
    
    @Override
    public String getKey(){
        return getNotation()+" "+toString();
    }

}
