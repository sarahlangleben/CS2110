package a5.logic;

import a5.util.PlayerRole;

import java.util.Arrays;

/**
 * A mutable representation of an m-by-n board in which each cell
 * can be occupied by a player or be empty
 */
public class Board {
    /**
     * A byte array representing the state of the board.
     * It represents the state of cells on an m-by-n board
     * with a one-dimensional array of length m*n.
     * So a cell at position (rowNo, colNo) on board is stored at
     * {@code boardState[p.row() * n + p.col()]}. Zero elements
     * represent empty locations; pieces of a player are represented
     * the PlayerRole.boardValue of that player.
     */
    private final byte[] boardState;
    final private int rowSize;
    final private int colSize;

    /**
     * Creates a new {@code rowSize}-by-{@code colSize} board.
     */
    public Board(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.boardState = new byte[rowSize * colSize];
    }

    /**
     * Creates a new board that deep copies a provided board.
     *
     * @param board the board to copy from
     */
    public Board(Board board) {
        this.rowSize = board.rowSize;
        this.colSize = board.colSize;
        this.boardState = Arrays.copyOf(board.boardState, board.boardState.length);
    }

    /**
     * Returns: the board state of a cell.
     * Returns 0 if it is empty; otherwise, the returned value matches
     * the player role's {@code boardValue}.
     * Requires: {@code p} is on board
     * @param p the position of the cell
     */
    public int get(Position p) {
        return boardState[index(p)];
    }

    /**
     * Effect: place a stone as {@code currentPlayer} on board at position {@code p}.
     * Requires: {@code p} is on board and {@code get(p)} == 0
     *
     * @param p the position to place a stone
     * @param currentPlayer role of the current player
     */
    protected void place(Position p, PlayerRole currentPlayer) {
        int value = currentPlayer.boardValue();
        assert 0 < value && value < 127;
        assert onBoard(p);
        boardState[index(p)] = (byte)value;
    }

    /**
     * Effect: set the cell at position {@code p} to empty.
     * Requires: {@code p} is on board
     */
    protected void erase(Position p) {
        boardState[index(p)] = 0;
    }

    /** The array index at which position p is stored in the board array. */
    private int index(Position p) {
        return p.row() * colSize + p.col();
    }

    public int rowSize() {
        return rowSize;
    }

    public int colSize() {
        return colSize;
    }

    /**
     * Board equality is determined using state equality.
     */
    @Override
    public boolean equals(Object o) {
        // TODO 1
//        if(!(o instanceof Board)){
//            return false;
//        }
//        //ensures the type is correct before cast:
//        Board ob = (Board) o;
//        if(o == this){ //if object is compared with itself
//            return true;
//        }
//        if(ob.rowSize == rowSize && ob.colSize == rowSize && !ob.boardState.equals(boardState)){
//            //completely empty boards to please the gods
//            return true;
//        }
//        if(ob.rowSize != rowSize || ob.colSize != colSize || !ob.boardState.equals(boardState)
//        || ob.hashCode() != hashCode()){
//            //if any one of these conditions are fulfilled they can't be equal
//            return false;
//        }
//        if(ob.rowSize == rowSize && ob.colSize == colSize && !ob.boardState.equals(boardState)
//        && ob.hashCode() == hashCode()){
//            return true;
//        }
        if(!o.getClass().equals(getClass())){
            return false;
        }
        Board ob = (Board) o;
        return(colSize == ob.colSize && rowSize == ob.rowSize && Arrays.equals(boardState, ob.boardState));
        //get the class (use getClass, not instanceof)
        //if(getClass!= o.getClass()) { return false;}
        //cast o to Board
        // return colSize == ob.colSize && rowSize == ob.rowSize && Arrays.equals(boardState, ob.boardState))
    }

    @Override
    public int hashCode() {
        // TODO 2
        int hash = 2; //prime number
        //TA soln:
        //random number
        //sum variable
        //go through boardState.length in a for-loop
        //multiply the sum by the random number
        //add to the sum the boardState[i]
        //then return the result
        int randNum = 593289053;
        int sum = 1;
        for(int i = 0; i < boardState.length; i++){
            sum*=randNum;
            sum+=boardState[i];
        }
        return Math.abs(sum);
    }

    /**
     * Returns: true if {@code p} is a valid cell to place stones.
     */
    public boolean validPos(Position p) {
        return (onBoard(p) && boardState[index(p)] == 0);
    }

    /**
     * Returns: true if {@code p} is within the board.
     */
    public boolean onBoard(Position p) {
        return (p.row() >= 0 && p.row() < rowSize &&
                p.col() >= 0 && p.col() < colSize);
    }
}