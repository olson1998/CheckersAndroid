package com.game.checkers.board;

public class Pawn {
    private int type; // -1 -> captured; 0 -> default; 1 -> queen
    private int location;

    public Pawn() {
        this.type = 0;
    }


    public void setType(int type) {
        this.type = type;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getLocation() {
        return location;
    }


    public int getType() {
        return type;
    }

    protected void move(int m) {
        int l = location;
        setLocation(l + m);
    }

    protected void move(int m, int q){
        int l = location;
        setLocation(l+(m*q));
    }


    protected void capturingMove(int m) {
        int l = location;
        setLocation(l + 2*m);
    }


    protected void captured(){
        setType(-1);
        setLocation(-1);

    }
}

