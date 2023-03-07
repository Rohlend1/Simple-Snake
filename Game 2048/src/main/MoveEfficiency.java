package main;

public class MoveEfficiency implements Comparable<MoveEfficiency>{
    private final int numberOfEmptyTiles;
    private final int score;
    private final Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency o) {
        if(o.numberOfEmptyTiles < this.numberOfEmptyTiles) return 1;

        if(o.numberOfEmptyTiles == this.numberOfEmptyTiles){
            if(o.score < score) return 1;
            else if(o.score == this.score) return 0;
            else return -1;
        }
        else return -1;
    }

}
