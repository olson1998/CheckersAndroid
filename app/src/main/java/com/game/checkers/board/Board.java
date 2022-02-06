package com.game.checkers.board;

import java.util.ArrayList;
import java.util.List;
import com.game.checkers.board.Pawn;

import static java.lang.StrictMath.abs;


public class Board {
    private Pawn[] team1;
    private Pawn[] team2;

    public Board() {
        this.team1 = initiateTeam(1);
        this.team2 = initiateTeam(2);
    }


    private Pawn[] initiateTeam(int t) {
        int[] pL1 = {40, 42, 44, 46, 49, 51, 53, 55, 56, 58, 60, 62};
        int[] pL2 = {1, 3, 5, 7, 8, 10, 12, 14, 17, 19, 21, 23};
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
            error("<< The pawn has not been found... >>");
        }
        return i;
    }


    private void moving(Pawn[] team,int l, int m) {
        int ind =pawnIndexFinder(team, l);
        team[ind].move(m);
    }

    private void moving(Pawn [] team, int l, int m, int q){
        int ind =pawnIndexFinder(team, l);
        team[ind].move(m, q);
    }

    private void capturing(Pawn[] player, Pawn[] enemy, int ppl ,int epl) {
        //ppl = player's pawn location; epl enemy's pawn location
        int ppi = pawnIndexFinder(player, ppl); //player's pawn index
        int epi = pawnIndexFinder(enemy, epl); // enemy's pawn index;
        try {
            epl=enemy[epi].getLocation();
            ppl=player[ppi].getLocation();
        } catch (IndexOutOfBoundsException e) {
            error("<<<The enemy's pawn has not been found...>>>");
        }
        int d = epl-ppl; player[ppi].capturingMove(d);
        enemy[epi].captured();
    }

    protected void capturing(Pawn[] player, Pawn[] enemy, int ppl, int npl ,int epl) {
        //ppl = player's pawn location; epl enemy's pawn location
        int d;
        int ppi = pawnIndexFinder(player, ppl); //player's pawn index
        int epi = pawnIndexFinder(enemy, epl); // enemy's pawn index;
        try {
            epl=enemy[epi].getLocation();
            ppl=player[ppi].getLocation();
        } catch (IndexOutOfBoundsException e) {
            error("<<<The enemy's pawn has not been found...>>>");
        }
        d =0; int q =0;
        int dis = epl - ppl; int f = 1;
        if(dis < 0){dis = dis *-1; f = -1;}
        if(dis % 7 == 0) { d = 7; q = (npl-ppl)/7;}
        if(dis % 9 == 0) { d = 9; q = (npl-ppl)/9;}
        if(f == -1){d = d *-1; q=q*-1;}
        player[ppi].move(d, q);
        enemy[epi].captured();
    }


    private void promoting(Pawn[] team, int pl){
        int i = pawnIndexFinder(team, pl);
        team[i].setType(1);
    }


    protected boolean canCapture(Pawn[] player, Pawn[] enemy, int ppl, int epl) {
        boolean can = false;
        boolean nextTo = false;
        boolean stuck = false;
        boolean out = false;
        int m =0; int pi = pawnIndexFinder(player, ppl);
        int lbc = 0; //location before capturing
        int lac = 0; // location after capturing
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
        }
        int lvl1 = (lbc - lbc%8)/8;
        int lvl2 = (lac - lac%8)/8;
            if ((abs(lvl2 - lvl1) != 2) || lac < 0 || lac >62) {
                out = true;
            }

            for (int i = 0; i < player.length; i++) {
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
            //System.out.println("team: " + t + " ; pawn: " + ind + " ;ppl: " + l +" ;m: " + m + " ;mq: " + mq);
            if( mq < 1){can = false;}
            else{
                List<Integer> pnl = possibleNewLocations(l, m);
                if( pnl.size() > 1){
                    can = true;
                }
                else{can = false;}
            }
        }
        if (out || stuck) {
            can = false;
        }
        //System.out.println("ppl: " + l + "; m=" + m + "; out: " + out + "; stuck: " + stuck + " ;can: "+ can);
        return can;
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

    protected List<Integer> possibleNewLocations(int l, int m){
        List<Integer> pnl = new ArrayList<>(); //possible new locations
        int mq = maxNumberOfMoves(l, m); boolean stop = false; int nl;
        pnl.add(l);
        for(int q = 1; q <= mq; q++){
            nl = l + q*m;
            for(int i = 0; i <12; i++){
                if((team1[i].getLocation() == nl || team2[i].getLocation() == nl) || nl <0 || nl >63){
                    stop = true; break;
                }
            }
            if(stop){break;}
            else{pnl.add(nl);}
        }
        return pnl;
    }



    public Pawn[] getTeam1() {
        return team1;
    }

    public Pawn[] getTeam2() {
        return team2;
    }

    private void error(String message) {
        System.out.println("<<<Error... Please wait, the qualified staff will come shortly>>>");
        System.out.println(message);
    }
}




