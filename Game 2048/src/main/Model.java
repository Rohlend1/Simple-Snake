package main;

import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    public Tile[][] gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
    int score = 0;
    int maxTile = 0;
    private final Stack<Tile[][]> previousStates = new Stack<>();
    private final Stack<Integer> previousScores = new Stack<>();
    private boolean isSaveNeeded = true;

    public Model() {
        resetGameTiles();
    }
    private void addTile(){
        List<Tile> arr = getEmptyTiles();
        if(arr.isEmpty()) return;
        Tile tile = arr.get((int)(Math.random()*arr.size()));
        tile.value = Math.random() < 0.9? 2:4;
    }
    private List<Tile> getEmptyTiles(){
        ArrayList<Tile> arr = new ArrayList<>();
        for(int i = 0; i < FIELD_WIDTH; i++){
            for(int j = 0; j < FIELD_WIDTH; j++){
                if(gameTiles[i][j].isEmpty()) arr.add(gameTiles[i][j]);
            }
        }
        return arr;
    }
    private void saveState(Tile[][] tiles){
        Tile[][] nt = new Tile[tiles.length][tiles.length];
        for(int i = 0; i < nt.length;i++){
            for(int j = 0; j < nt[0].length;j++){
                nt[i][j] = new Tile(tiles[i][j].value);
            }
        }
        previousStates.push(nt);
        previousScores.push(score);
        isSaveNeeded = false;
    }
    public void rollback(){

        if(!previousStates.isEmpty()) {
            gameTiles = previousStates.pop();
        }
         if(!previousScores.isEmpty()) {
             score = previousScores.pop();
        }

    }
    void resetGameTiles(){
        for(int i = 0; i < FIELD_WIDTH; i++){
            for(int j = 0; j < FIELD_WIDTH; j++){
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }
    public boolean compressTiles(Tile[] tiles){
        boolean flag = false;
            for(int j = 1; j < tiles.length; j++){
                if(tiles[j-1].value == 0 && tiles[j].value !=0) {
                    tiles[j-1].value = tiles[j].value;
                    tiles[j].value = 0;
                    flag = true;
                }
                for(int i = 1; i < tiles.length; i ++){
                    if(tiles[i-1].value == 0 && tiles[i].value !=0) {
                        tiles[i-1].value = tiles[i].value;
                        tiles[i].value = 0;
                        flag = true;
                    }
                }
            }
            return flag;
    }
    private boolean mergeTiles(Tile[] tiles){
        boolean flag = false;
        for(int i = 1; i < tiles.length; i++){
            if(tiles[i].value == tiles[i-1].value && tiles[i].value !=0) {
                tiles[i].value += tiles[i-1].value;
                score += tiles[i].value;
                maxTile = Math.max(maxTile,tiles[i].value);
                flag = true;
                tiles[i-1].value = 0;
            }
            compressTiles(tiles);
        }
        return flag;
    }
    public void left(){
        if(isSaveNeeded) saveState(gameTiles);
        boolean flag = false;
        for (Tile[] gameTile : gameTiles) {
            if (compressTiles(gameTile) | mergeTiles(gameTile)) flag = true;

        }
        if(flag) addTile();
        isSaveNeeded = true;
    }
    public void right(){
        saveState(gameTiles);
        move();
        move();
        left();
        move();
        move();
    }
    public void up(){
        saveState(gameTiles);
        move();
        move();
        move();
        left();
        move();
    }
    public void down(){
        saveState(gameTiles);
        move();
        left();
        move();
        move();
        move();
    }
    public void move(){
        Tile[][] tiles = new Tile[gameTiles.length][gameTiles.length];
        for(int i = 0; i < gameTiles.length; i++){
            for(int j =0; j < gameTiles[0].length;j++){
                tiles[j][gameTiles.length-1-i] = gameTiles[i][j];
            }
        }
        gameTiles = tiles;
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }
    public boolean canMove(){
        for(int i = 0; i < gameTiles.length;i++){
            for(int j = 0; j < gameTiles.length;j++){
                if(j-1 >= 0 && gameTiles[i][j-1].value == gameTiles[i][j].value && gameTiles[i][j].value !=0) return true;
                if(i-1 >= 0 && gameTiles[i-1][j].value == gameTiles[i][j].value && gameTiles[i][j].value !=0) return true;
                if(gameTiles[i][j].value == 0) return true;
            }
        }
        return false;
    }
    public void randomMove(){
        int n = ((int)(Math.random()*100)%4);
        switch (n) {
            case 0 -> left();
            case 1 -> right();
            case 2 -> up();
            case 3 -> down();
        }
    }
//    public boolean hasBoardChanged(){
//        Tile[][] currentTiles = previousStates.peek();
//        for (int i = 0; i < currentTiles.length;i++){
//            for(int j = 0;j < currentTiles.length;j++){
//                if(currentTiles[i][j].value != gameTiles[i][j].value) return true;
//            }
//        }
//        return false;
//    }
//    public MoveEfficiency getMoveEfficiency(Move move){
//        move.move();
//        if(!hasBoardChanged()) {
//            rollback();
//            return new MoveEfficiency(-1,0,move);
//        }
//        rollback();
//        return new MoveEfficiency(getEmptyTiles().size(),score,move);
//    }
    public void autoMove(){
        PriorityQueue<MoveEfficiency> moveEfficiencies = new PriorityQueue<>(4,Collections.reverseOrder());
        moveEfficiencies.add(new MoveEfficiency(getEmptyTiles().size(),score,this::left));
        moveEfficiencies.add(new MoveEfficiency(getEmptyTiles().size(),score,this::right));
        moveEfficiencies.add(new MoveEfficiency(getEmptyTiles().size(),score,this::up));
        moveEfficiencies.add(new MoveEfficiency(getEmptyTiles().size(),score,this::down));
        assert moveEfficiencies.peek() != null;
        moveEfficiencies.peek().getMove().move();
    }
}
