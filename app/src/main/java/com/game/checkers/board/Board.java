package com.game.checkers.board;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.abs;


public class Board {
    private Pawn[] team1;
    private Pawn[] team2;

    public Board() {
        this.team1 = initiateTeam(1);
        this.team2 = initiateTeam(2);
    }


    private Pawn[] initiateTeam(int t) {
        //int[] pL1 = {40, 42, 44, 46, 49, 51, 53, 55, 56, 58, 60, 62};
        //int[] pL2 = {1, 3, 5, 7, 8, 10, 12, 14, 17, 19, 21, 23};
        int[] pL1 = {62, 3, 56,  -1,  -1,  -1,  -1,  -1,  -1, -1,  -1,  -1, };
        int[] pL2 = {14, 24, -1, 44, -1,  -1,  -1,  -1, 17,-1 , -1,  -1, };
        int[] pL;
        if (t == 1) {
            pL = pL1;
        } else {
            pL = pL2;
        }
        Pawn[] team = new Pawn[12];
        for (int i = 0; i < 12; i++) {
            Pawn pawn = new Pawn();
            pawn.setLocation(pL[i]);
            pawn.setType(0);
            team[i] = pawn;

        }
        return team;
    }


    protected int pawnIndexFinder(Pawn[] team, int l) {
        int i = 0;
        for (Pawn p : team) {
            if (p.getLocation() == l) {
                break;
            }
            i++;
        }
        try{
            team[i].getLocation();
        }catch (IndexOutOfBoundsException e){
            i = -1;
            error("<< The pawn has not been found... >> location?= " + l);
        }
        return i;
    }

    protected void promoting(Pawn[] team, int pl){
        int i = pawnIndexFinder(team, pl);
        team[i].setType(1);
    }


    protected boolean canCapture(Pawn[] player, Pawn[] enemy, int ppl, int epl) {
        boolean can = false;
        boolean nextTo = false;
        boolean stuck = false;
        boolean out = false;
        int m =0; int pi = pawnIndexFinder(player, ppl);
        int lbc = -99; //location before capturing
        int lac = -99; // location after capturing
        if(player[pi].getType()==0) {
            if (((ppl + 7 == epl) || (ppl - 7 == epl) || (ppl + 9 == epl) || (ppl - 9 == epl))) {
                nextTo = true;
            }
            if (nextTo) {
                m = (epl-ppl) * 2;
                lac = ppl + m;
                lbc = ppl;
            }
        }
        else if(player[pi].getType()==1){
            int dis = epl-ppl; int f = 1;
            if(dis<0){ dis = dis*-1; f = -1;}
            if(dis%7==0){m = 7; nextTo = true;}
            else if(dis%9==0){m = 9; nextTo = true;}
            if(nextTo) {
                if (f == -1) { m = m * -1; }
                lbc = epl - m; //location before capturing
                lac = epl + m; // location after capturing
            }
            System.out.println("lac: " + lac);
            System.out.println("lbc: " + lbc);
        }
        int lvl1 = (lbc - lbc%8)/8;
        int lvl2 = (lac - lac%8)/8;
        if ((abs(lvl2 - lvl1) != 2) || lac < 0 || lac >62) {
            out = true;
        }

        for (int i = 0; i < 12; i++) {
            if (player[i].getLocation() == lac) {
                stuck = true;
                break;
            } else if (enemy[i].getLocation() == lac) {
                stuck = true;
                break;
            }
        }

        if (nextTo && !stuck && !out) {
            can = true;
        }
        //if(nextTo){System.out.println("ppl: " + ppl+ "; epl: " + epl + "; move= " + m +"; isNext: " + nextTo  + "; stuck: " + stuck +"; out: " + out+"; can: " + can);}
        return can;
    }


    protected boolean canMove(Pawn[] team, int t, int l, int m) {
        boolean can = true;
        boolean out = false;
        boolean stuck = false;
        int ind = pawnIndexFinder(team, l);
        if(team[ind].getType() == 0) {
            int lvl1 = (l - l % 8) / 8;
            int lvl2 = ((l + m) - (l + m) % 8) / 8;
            if (abs(lvl2 - lvl1) != 1 || (l + m) < 0 || (l + m) > 62) {
                out = true;
            }
            if(!out) {
                int pi = pawnIndexFinder(team, l);
                Pawn[] enemy;
                if (t == 1) {
                    enemy = team2;
                } else {
                    enemy = team1;
                }
                for (int i = 0; i < team.length; i++) {
                    if (((l + m == team[i].getLocation()) || (l + m == enemy[i].getLocation()))) {
                        if (i != pi) {
                            stuck = true;
                        }
                    }
                }
            }
            else{can = false;}
        }
        else if(team[ind].getType() == 1){
            int mq = maxNumberOfMoves(l, m);
            if( mq < 1){can = false;}
            else{
                List<Integer> pnl = possibleNewLocations(l, m);
                if( pnl.size() > 0){ can = true;}
                else{can = false;}
            }
        }
        if (out || stuck) {
            can = false;
        }
        //System.out.println("ppl: " + l + "; m=" + m + "; out: " + out + "; stuck: " + stuck + " ;can: "+ can);
        return can;
    }

    protected List<Integer> possibleNewLocations(int l, int m){
        List<Integer> pnl = new ArrayList<>(); //possible new locations
        List<Integer> apl = getAllPawnsLocation(); // all pawns location
        int nl = l+m;
        int lvl =(l - l % 8) / 8;
        int nlvl =(nl - nl % 8) / 8;
        while((lvl-nlvl) == 1 || (lvl-nlvl) == -1 ){
            if(!apl.contains(nl)){pnl.add(nl);}
            else{break;}
            lvl =(nl - nl % 8) / 8;
            nl+=m;
            nlvl =(nl - nl % 8) / 8;
        }
        return pnl;
    }

    protected List<Integer> getAllPawnsLocation(){
        List<Integer> apl = new ArrayList<>(); //apl pawns locations
        for(int i = 0; i < 12; i++){
            apl.add(team1[i].getLocation());
            apl.add(team2[i].getLocation());
        }
        return apl;
    }

    private int maxNumberOfMoves(int l, int m){
        int mq = 0;
        int nl = l+m; // new location
        int lvl1 = (l - l % 8) / 8;
        int lvl2 = (nl - (nl) % 8) / 8;
        if(abs(lvl2 - lvl1) == 1 && (nl >0 || nl < 63)){
            boolean over = false;
            if((m>0 && nl > 63) || (m<0 && nl <0)){over = true;}
            while(!over){
                l=nl; nl=l+m;
                lvl1 = (l - l % 8) / 8;
                lvl2 = (nl - (nl) % 8) / 8;
                if(abs((lvl2 - lvl1)) != 1){ mq++; over = true;}
                else if((m>0 && l > 63) || (m<0 && l <0)){mq++; over = true;}
                else{mq++;}
            }
        }
        return mq;
    }

    protected int getCapturingDirection(int ppl, int sf){
        int d = 0;
        int r = sf - ppl;
        if( r % 7 ==0){d = 7;}
        if( r % 9 ==0){d = 9;}
        if(r < 0){ d=d*-1;}
        return d;
    }

    public Pawn[] getTeam1() {
        return team1;
    }

    public Pawn[] getTeam2() {
        return team2;
    }

    private void error(String message) {
        System.out.println("<<< Error... Please wait, the qualified staff will come shortly >>>");
        System.out.println(message);
    }
}




