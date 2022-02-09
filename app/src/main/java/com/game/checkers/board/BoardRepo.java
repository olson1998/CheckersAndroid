package com.game.checkers.board;

import static java.lang.StrictMath.abs;

import java.util.ArrayList;
import java.util.List;

public class BoardRepo {
    private Board board;

    private final int[] movable = {
            1, 3, 5, 7,
            8, 10, 12, 14,
            17, 19, 21, 23,
            24, 26, 28, 30,
            33, 35, 37, 39,
            40, 42, 44, 46,
            49, 51, 53, 55,
            56, 58, 60, 62
    };

    public BoardRepo(Board board) {
        this.board = board;
    }

    //CAPTURING

    //check if there are pawn's in player's team that are able to capture
    public boolean isEnemyToCapture(int player){
        int enemy = getEnemy(player);
        boolean yes = false;
        for(Pawn pp : findTeam(player)){
            for(Pawn ep : findTeam(enemy)){
                if((board.canCapture(findTeam(player), findTeam(enemy) ,pp.getLocation(), ep.getLocation())) && pp.getType() != -1 && ep.getType()!= -1){ yes = true; break;}
            }
        }
        return yes;
    }

    //return pawns locations which are able to capture
    public List<Integer> thosePawnsCanCapture(int player){
        List<Integer> coordinates = new ArrayList<>();
        for(int i =0; i < findTeam(player).length; i++){
            if(canPawnCapture(player, findTeam(player)[i].getLocation()) && findTeam(player)[i].getType() !=-1){
                coordinates.add(findTeam(player)[i].getLocation());
            }
        }
        return coordinates;
    }

    //checks if pawn on location ppl of player is able to capture
    public boolean canPawnCapture(int player, int ppl){
        boolean yes = false; int enemy = 2;
        if(player == 2){enemy =1;}
        for(Pawn pawn : findTeam(enemy)){
            if(board.canCapture(findTeam(player), findTeam(enemy) ,ppl, pawn.getLocation())){
                yes = true;
                break;
            }
        }
        return yes;
    }

    //checks if there is a pawn on location
    private boolean isPawn(Pawn[] team, int l){
        boolean yes = true; int i = 0;
        for(Pawn pawn: team){
            if(pawn.getLocation()== l){break;}
            i++;
        }
        if(i == team.length){ yes = false;}
        return yes;
    }

    //returns locations of pawn which would be located on after capturing
    public List<Integer> possibleDirectionsToCapture(int player, int ppl) {
        int enemy = 2;
        if(player == 2){ enemy = 1; }
        List<Integer> directions = new ArrayList<>();
        int ind = board.pawnIndexFinder(findTeam(player), ppl);
        int[] pm = {-9, -7, 7, 9};
        for(int m : pm){
            if ((findTeam(player)[ind].getType() == 0) && board.canCapture(findTeam(player), findTeam(enemy), ppl,ppl +m) && isPawn(findTeam(enemy), ppl+m)) {
                directions.add(2*m+ppl);
            }
            else if (findTeam(player)[ind].getType() == 1) {
                List<Integer> pnl = board.possibleNewLocations(ppl, m);
                int fm = 0;
                if(pnl.size()>0){fm =m+ pnl.get(pnl.size()-1); }
                if(pnl.size() == 0){ fm = ppl + m; }
                if(isPawn(findTeam(enemy), fm)){directions.addAll(board.possibleNewLocations(fm, m));}
            }
        }
        return directions;
    }

    //returns enemy's location after clicking seleted field on biard
    public int getEnemysLocation(int player, int ppl, int sf){
        int d = board.getCapturingDirection(ppl, sf);
        int epl = -1;
        try {
            epl = enemysLocation(
                    player,
                    ppl,
                    d
            );
        }catch (NullPointerException e){
            System.out.println("<< enemy's pawn has not been found >> from getEnemysLocation() ppl= " + ppl +"; selected field= " + sf);
        }
        return epl;
    }

    //returns enemy's location at direction m
    private Integer enemysLocation(int player, int ppl, int m){
        int epl = 0; int nl =ppl;
        List<Integer> list = new ArrayList<>();
        while( nl > 0 && nl < 63){
            list.add(nl);
            nl+=m;
        }
        for(int l: list){
            for(Pawn p: findTeam(getEnemy(player))){
                if(l == p.getLocation()){epl = l; break;}
            }
        }
        if(epl ==0){
            System.out.println("enemy pawn has not been found!!!!! from enemysLocation()");}
        return epl;
    }

    protected void capturing(Pawn[] player, Pawn[] enemy, int ppl ,int epl) {
        //ppl = player's pawn location; epl enemy's pawn location
        try {
            int ppi = pawnIndexFinder(player, ppl); //player's pawn index
            int epi = pawnIndexFinder(enemy, epl); // enemy's pawn index;
            epl=enemy[epi].getLocation();
            ppl=player[ppi].getLocation();
            int d = epl-ppl; player[ppi].capturingMove(d);
            enemy[epi].captured();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("<<< The enemy's pawn has not been found... >>> from capturing() type 0");
        }
    }

    protected void capturing(Pawn[] player, Pawn[] enemy, int ppl, int npl ,int epl) {
        //ppl = player's pawn location; epl enemy's pawn location
        int d;
        try {
            int ppi = pawnIndexFinder(player, ppl); //player's pawn index
            int epi = pawnIndexFinder(enemy, epl); // enemy's pawn index;
            d =0; int q =0;
            int dis = epl - ppl; int f = 1; //distance between queen and enemy's pawn
            if(dis % 7 == 0) { d = 7; q = (npl-ppl)/7;}
            if(dis % 9 == 0) { d = 9; q = (npl-ppl)/9;}
            player[ppi].move(d, q);
            enemy[epi].captured();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("<<< The enemy's pawn has not been found... >>> from capturing() type 1");
        }
    }

    //change types and location of pawn after capturing for normal pawn
    public void CAPTURING(int player, int ppl, int epl){
        //ppl -> player's pawn location; epl -> enemy's pawn location
        int enemy = 2;
        if(player == 2){enemy =1; }
        capturing(findTeam(player), findTeam(enemy), ppl, epl);
    }

    //change types and location of pawn after capturing for queen
    public void CAPTURING(int player, int ppl, int npl, int epl){
        //ppl -> player's pawn location; epl -> enemy's pawn location
        int enemy = 2;
        if(player == 2){enemy =1; }
        capturing(findTeam(player), findTeam(enemy),ppl, npl, epl);
    }


    //MOVING

    //returns List of locations of every pawn which is able to move
    public List<Integer> thosePawnsCanMove(int t) {
        List<Integer> coordinates = new ArrayList<>();
        boolean can;
        int m1 = -9; int m2 = -7; //directions
        if (t == 2) {
            m1 = m1 * -1;
            m2 = m2 * -1;
        }
        for (Pawn pawn : findTeam(t)) {
            can = false;
            if (pawn.getType() != -1) {
                int l = pawn.getLocation();
                if (board.canMove(findTeam(t), t, l, m1)) {
                    can = true;
                } else if (board.canMove(findTeam(t), t, l, m2)) { can = true;}
                if (pawn.getType() == 1) { //if pawn is queen check backward direction
                    if (board.canMove(findTeam(t), t, l, m1 * -1)) {
                        can = true;
                    } else if (board.canMove(findTeam(t), t, l, m2 * -1)) { can = true; }
                }
                if (can) { coordinates.add(l); }
            }
        }
        return coordinates;
    }

    //return locations which pawn from location pl are able to move on
    public List<Integer> possibleDirectionsToMove(int team, int pl){ //pl pawn's lcoation
        List<Integer> directions = new ArrayList<>(); int i = board.pawnIndexFinder(findTeam(team), pl);
        int m1 = -9; int m2=-7; //directions
        if(findTeam(team)[i].getType() == 0){
            if(team ==2) { m1 = m1*-1; m2 = m2*-1;}
            if(board.canMove(findTeam(team), team , pl, m1)){ directions.add(pl+m1);}
            if(board.canMove(findTeam(team), team,  pl, m2)){ directions.add(pl+m2);}
        }
        if(findTeam(team)[i].getType() == 1){
            directions.addAll(
                    board.possibleNewLocations(pl, m1)
            );
            directions.addAll(
                    board.possibleNewLocations(pl, m2)
            );
            directions.addAll(
                    board.possibleNewLocations(pl, -1*m1)
            );
            directions.addAll(
                    board.possibleNewLocations(pl, -1*m2)
            );

        }
        return directions;
    }


    public void MOVING(int player, int i, int nl){
        findTeam(player)[i].setLocation(nl);
    }
    //promoting

    //checks for promotion of pawn
    public boolean checkAndPROMOTE(int team){
        boolean yes = false;
        Pawn[] t = findTeam(team);
        int[] prL = new int[4];
        if(team == 1){ prL = new int[]{1, 3, 5, 7};}
        if(team == 2){ prL = new int[]{56,58,60,62};}
        for(int l : prL){
            for(int i =0; i < t.length; i++){
                int pl =t[i].getLocation();
                if(t[i].getType() == 0 && l==pl){
                    board.promoting(t, pl);
                }
            }
        }
        return yes;
    }

    //other methods

    //check if there is win
    public boolean isWin(){
        boolean yes = false;
        for(Pawn pawn : board.getTeam1()){
            yes = true;
            if(pawn.getType() != -1){ yes = false; break;} //checks if all pawns are captured
        }
        if(!yes){
            for(Pawn pawn : board.getTeam2()){
                yes = true;
                if(pawn.getType() != -1){ yes = false; break; }
            }
        }

        return yes;
    }

    //search for pawn index with given location in an team's array
    private int pawnIndexFinder(Pawn[] team, int l) {
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
        }
        return i;
    }

    //return Pawns of given team number
    public Pawn[] findTeam(int t) {
        Pawn[] team = board.getTeam1();
        if (t == 2) {
            team = board.getTeam2();
        }
        return team;
    }

    //return numeric value of enemy
    public int getEnemy(int p){
        int e = 2;
        if(p==2){e=1;}
        return e;
    }

    public Board getBoard() {
        return board;
    }

    public int[] getMovable() {
        return movable;
    }

    public Pawn[] getTeam1(){
        return board.getTeam1();
    }

    public Pawn[] getTeam2(){
        return board.getTeam2();
    }
}
