package a5.logic;

import a5.util.PlayerRole;
import a5.util.GameType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * A Pente game, where players take turns to place stones on board.
 * When consecutive two stones are surrounded by the opponent's stones on two ends,
 * these two stones are removed (captured).
 * A player wins by placing 5 consecutive stones or capturing stones 5 times.
 */
public class Pente extends MNKGame {

    /**
     * Create an 8-by-8 Pente game.
     */

    public Map<PlayerRole, Integer> m = new HashMap<>();
    public Pente() {
        super(8, 8, 5);
        // TODO 1
        m.put(PlayerRole.FIRST_PLAYER, 0);
        m.put(PlayerRole.SECOND_PLAYER, 0);
    }

    /**
     * Creates: a copy of the game state.
     */
    public Pente(Pente game) {
        super(game);
        // TODO 2
        m = game.m;
    }

    @Override
    public boolean makeMove(Position p) {
        // TODO 3
        if (!board().validPos(p)) {
            return false;
        }
        board().place(p, currentPlayer());
        //--above this line: all is from TicTacToe.java--//
        int[][] vect = new int[8][2];
        vect[0][0] = 0;
        vect[2][0] = 1;
        vect[3][0] = 1;
        vect[4][0] = 0;
        vect[5][0] = -1;
        vect[6][0] = -1;
        vect[7][0] = -1;
        vect[0][1] = 1;
        vect[1][1] = 1;
        vect[2][1] = 0;
        vect[3][1] = -1;
        vect[4][1] = -1;
        vect[5][1] = -1;
        vect[6][1] = 0;
        vect[7][1] = 1;
        int p_c = p.col();
        int p_r = p.row();
        for (int i = 0; i <= 7; i++) {
            int x_temp = vect[i][0];
            int y_temp = vect[i][1];
            // first postion from v
            Position p_d = new Position(p_r + x_temp, p_c + y_temp);
            // first position from p
            if (board().onBoard(p_d) && board().get(p) != board().get(p_d) &&
                    board().get(p_d) > 0) {
                // third and last position from p
                Position p_ddd = new Position(p_r + 3 * x_temp, p_c + 3 * y_temp);
                if (board().onBoard(p_ddd) && board().get(p) == board().get(p_ddd)) {
                    // second position from p
                    Position p_dd = new Position(p_r + 2 * x_temp, p_c + 2 * y_temp);
                    if (board().onBoard(p_dd) && board().get(p_d) == board().get(p_dd) &&
                            board().get(p) != board().get(p_dd)) {
                        // if the pattern is confirmed to be player a - player b - player b- and
                        // player a in a direction
                        // and on the board
                        board().erase(p_dd);
                        board().erase(p_d);
                        // NEED TO ADD A CHANGE CAPTURED NUMBER FOR PAIR
                        if (board().get(p) == 1) {
                            int a = m.get(PlayerRole.FIRST_PLAYER) + 1;
                            m.replace(PlayerRole.FIRST_PLAYER, a);
                        }
                        if (board().get(p) == 2){
                            int b = m.get(PlayerRole.SECOND_PLAYER) + 1;
                            m.replace(PlayerRole.SECOND_PLAYER, b);
                        }

                    }
                }
            }
        }
        changePlayer();
        advanceTurn();
        return true;
    }
    /**
     * Returns: a new game state representing the state of the game after the current player takes a
     * move {@code p}.
     */
    public Pente applyMove(Position p) {
        Pente newGame = new Pente(this);
        newGame.makeMove(p);
        return newGame;
    }
    /**
     * Returns: the number of captured pairs by {@code playerRole}.
     */
    public int capturedPairsNo(PlayerRole playerRole) {
        // TODO 4
        return m.get(playerRole);
    }

    @Override
    public boolean hasEnded() {
        // TODO 5
        if(m.get(PlayerRole.FIRST_PLAYER)==5){
            return true;}
        if(m.get(PlayerRole.SECOND_PLAYER)==5){
            return true;}

        return super.hasEnded();
    }

    @Override
    public GameType gameType() {
        return GameType.PENTE;
    }

    @Override
    public String toString() {
        String board = super.toString();
        return board + System.lineSeparator() + "Captured pairs: " +
                "first: " + capturedPairsNo(PlayerRole.FIRST_PLAYER) + ", " +
                "second: " + capturedPairsNo(PlayerRole.SECOND_PLAYER);
    }

    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass()) {
            return false;
        }
        Pente p = (Pente) o;
        return stateEqual(p);
    }

    /**
     * Returns: true if the two games have the same state.
     */
    protected boolean stateEqual(Pente p) {
        // TODO 6
        return(super.stateEqual(p) || m.get(PlayerRole.FIRST_PLAYER) != this.m.get(PlayerRole.FIRST_PLAYER) ||
                m.get(PlayerRole.SECOND_PLAYER)!= this.m.get(PlayerRole.SECOND_PLAYER));
    }

    @Override
    public int hashCode() {
        //TODO 7: this looks ok
        return Arrays.hashCode(new int[]{
            super.hashCode(),
                m.hashCode()
        });
    }
}
