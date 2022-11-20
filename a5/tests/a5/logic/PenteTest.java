package a5.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import a5.util.PlayerRole;
import org.junit.jupiter.api.Test;

class PenteTest {
    @Test
    void testConstructor() {
        // TODO 1: write at least 1 test case (done)
        //new game initialized
        Pente game = new Pente();
        assertEquals(8, game.rowSize());
        assertEquals(8, game.colSize());
        assertEquals(5, game.countToWin());
        assertFalse(game.hasEnded());
    }

    @Test
    void testCopyConstructor() {
        // test case 1: can a board state be copied to an equal state
        Pente game1 = new Pente();
        game1.makeMove(new Position(2, 2));
        Pente game2 = new Pente(game1);
        assertTrue(game1.stateEqual(game2));

        // TODO 2: write at least 3 test cases (done)
        //test case 2: copying empty state to another empty state.
        Pente game3 = new Pente();
        Pente game4 = new Pente(game3);
        assertTrue(game3.stateEqual(game4));

        //test case 3: copying board state to another state, then board state changes.
        //making sure that the board isn't stuck in the state it copied.
        Pente game5 = new Pente();
        game5.makeMove(new Position(0, 1));
        Pente game6 = new Pente(game5);
        assertTrue(game5.stateEqual(game6));
        assertEquals(game5.hashCode(), game6.hashCode());
        game6.makeMove(new Position(3, 4));
        assertFalse(game6.stateEqual(game5));

        //test case 4: copying a board state whose game has ended.
        //making sure the hasEnded() value is also copied.
        Pente game7 = new Pente();
        game7.makeMove(new Position(0, 0)); //p1, 1
        game7.makeMove(new Position(1,1)); //p2
        game7.makeMove(new Position(0, 1)); //p1, 2
        game7.makeMove(new Position(1, 2)); //p2
        game7.makeMove(new Position(0, 2)); //p1, 3
        game7.makeMove(new Position(1, 3)); //p2
        game7.makeMove(new Position(0, 3)); //p1, 4
        game7.makeMove(new Position(1, 4)); //p2, 4
        game7.makeMove(new Position(0, 4)); //p1, 5
        assertTrue(game7.hasEnded());
        Pente game8 = new Pente(game7);
        assertTrue(game8.hasEnded());
        //test case 5: board that has had a capture. does it carry over?
    }

    @Test
    void testHashCode() {
        Pente game1 = new Pente();
        Pente game2 = new Pente();
        Pente game3 = new Pente();
        // test case 1: do two equal nonempty board states have the same hash code
        game1.makeMove(new Position(3, 3));
        game2.makeMove(new Position(3, 3));
        assertEquals(game1.hashCode(), game2.hashCode());
        // test case 2: non-equal board states should be very unlikely to have the
        // same hash code.
        game3.makeMove(new Position(0, 0));
        assertNotEquals(game1.hashCode(), game3.hashCode());
        // TODO 3: write at least 3 test cases (done)
        //test case 3: empty boards
        Pente gameEmpt1 = new Pente();
        Pente gameEmpt2 = new Pente();
        assertEquals(gameEmpt2.hashCode(), gameEmpt1.hashCode());

        //test case 4: board whose game are in the same state
        // has ended vs board whose game has not yet ended
        Pente game7 = new Pente();
        game7.makeMove(new Position(0, 0)); //p1, 1
        Pente gameMid = new Pente();
        gameMid.makeMove(new Position(0, 0)); //p1, 1
        assertEquals(game7.hashCode(), gameMid.hashCode());

        //test case 5: one game has ended, the other hasn't yet.
        game7.makeMove(new Position(1,1)); //p2
        game7.makeMove(new Position(0, 1)); //p1, 2
        game7.makeMove(new Position(1, 2)); //p2
        game7.makeMove(new Position(0, 2)); //p1, 3
        game7.makeMove(new Position(1, 3)); //p2
        game7.makeMove(new Position(0, 3)); //p1, 4
        game7.makeMove(new Position(1, 4)); //p2, 4
        game7.makeMove(new Position(0, 4)); //p1, 5
        assertTrue(game7.hasEnded());
        assertNotEquals(game7.hashCode(), gameMid.hashCode());

        //test case 6: boards whose games have both ended
        gameMid.makeMove(new Position(1,1)); //p2
        gameMid.makeMove(new Position(0, 1)); //p1, 2
        gameMid.makeMove(new Position(1, 2)); //p2
        gameMid.makeMove(new Position(0, 2)); //p1, 3
        gameMid.makeMove(new Position(1, 3)); //p2
        gameMid.makeMove(new Position(0, 3)); //p1, 4
        gameMid.makeMove(new Position(1, 4)); //p2, 4
        gameMid.makeMove(new Position(0, 4)); //p1, 5
        assertTrue(gameMid.hasEnded());
        assertEquals(gameMid.hashCode(), game7.hashCode());
    }

    @Test
    void makeMove() {
        // test case 1: a simple move
        Pente game = new
                Pente();
        Position p = new Position(2, 2);
        game.makeMove(p); // a move by the first player
        assertEquals(game.currentPlayer(), PlayerRole.SECOND_PLAYER);
        assertEquals(game.currentTurn(), 2);
        assertFalse(game.hasEnded());
        assertEquals(game.capturedPairsNo(PlayerRole.FIRST_PLAYER), 0);
        assertEquals(game.capturedPairsNo(PlayerRole.SECOND_PLAYER), 0);
        assertEquals(game.board().get(p), PlayerRole.FIRST_PLAYER.boardValue());

        // test case 2: try a capture
        game.makeMove(new Position(2, 3)); // a move by the second player
        game.makeMove(new Position(3, 2)); // a move by the first player
        game.makeMove(new Position(2, 4)); // a move by the second player
        game.makeMove(new Position(2, 5)); // a move by the first player, which should capture the pair [(2, 3), (2, 4)]
        assertEquals(game.capturedPairsNo(PlayerRole.FIRST_PLAYER), 1);
        assertEquals(game.board().get(new Position(2, 3)), 0); // the stone should be removed
        assertEquals(game.board().get(new Position(2, 4)), 0); // the stone should be removed

        // TODO 4: write at least 3 test cases (done)
        //test case 3: win by placing five consecutive stones
        Pente gameWinConsec = new Pente();
        gameWinConsec.makeMove(new Position(0, 0));
        gameWinConsec.makeMove(new Position(1,1)); //p2
        gameWinConsec.makeMove(new Position(0, 1)); //p1, 2
        gameWinConsec.makeMove(new Position(1, 2)); //p2
        gameWinConsec.makeMove(new Position(0, 2)); //p1, 3
        gameWinConsec.makeMove(new Position(1, 3)); //p2
        gameWinConsec.makeMove(new Position(0, 3)); //p1, 4
        gameWinConsec.makeMove(new Position(1, 4)); //p2, 4
        gameWinConsec.makeMove(new Position(0, 4)); //p1, 5
        assertTrue(gameWinConsec.hasEnded());

        //test case 4: moving a piece onto a previously occupied space on the board
        game.makeMove(new Position(2, 3));
        assertNotEquals(game.board().get(new Position(2, 3)), 0);

        //test case 5: invalid placement
        Pente gameInvalid = new Pente();
        assertFalse(gameInvalid.makeMove(new Position(9, 9))); // should return false

        //test case 6: win by capturing 10 of the opponent's pieces (i have no idea how captures work, come back later if you can).
        Pente gameWinA = new Pente();
        gameWinA.makeMove(new Position(2, 2)); //1
        gameWinA.makeMove(new Position(2, 3)); //2
        gameWinA.makeMove(new Position(3, 2)); //1
        gameWinA.makeMove(new Position(2, 4)); //2
        gameWinA.makeMove(new Position(2, 5)); //1
        assertEquals(gameWinA.capturedPairsNo(PlayerRole.FIRST_PLAYER), 1);
    }

    @Test
    void capturedPairsNo() {
        // test case 1: are captured pairs registered?
        Pente game = new Pente();
        game.makeMove(new Position(3, 2)); // a move by the first player
        game.makeMove(new Position(3, 3)); // a move by the second player
        game.makeMove(new Position(4, 2)); // a move by the first player
        game.makeMove(new Position(3, 4)); // a move by the second player
        game.makeMove(new Position(3, 5)); // a move by the first player, which should capture the pair [(2, 3), (2, 4)]
        assertEquals(game.capturedPairsNo(PlayerRole.FIRST_PLAYER), 1);
        // TODO 5: write at least 3 test cases
        //test case 2: can player 2 capture as well?
        game.makeMove(new Position(2, 2)); // a move by the second player
        game.makeMove(new Position(2, 1)); // a move by the first player
        game.makeMove(new Position(5, 2)); // a move by the second player,
        game.makeMove(new Position(4, 1)); // a move by the first player
        assertEquals(game.capturedPairsNo(PlayerRole.SECOND_PLAYER), 1);

        //test case 3: diagonal capture
        game.makeMove(new Position(6, 3)); // a move by the second player
        game.makeMove(new Position(6, 5)); // a move by the first player
        game.makeMove(new Position(6, 4)); // a move by the second player,
        game.makeMove(new Position(7, 4)); // a move by the first player, captures pair
        assertEquals(game.capturedPairsNo(PlayerRole.FIRST_PLAYER), 2);

        //test case 4: capture in a corner
        game.makeMove(new Position(8, 3)); // a move by the second player
        game.makeMove(new Position(4, 8)); // a move by the first player
        game.makeMove(new Position(5, 6)); // a move by the second player,
        game.makeMove(new Position(8, 6)); // a move by the first player,
        //assertEquals(game.capturedPairsNo(PlayerRole.SECOND_PLAYER), 2);
        game.makeMove(new Position(8, 7)); // a move by the second player
        game.makeMove(new Position(6, 8)); // a move by the first player
        game.makeMove(new Position(8, 8)); // a move by the second player,
        assertEquals(game.capturedPairsNo(PlayerRole.SECOND_PLAYER), 2);
        assertEquals(game.capturedPairsNo(PlayerRole.FIRST_PLAYER), 2);
    }

    @Test
    void hasEnded() {
        // test case 1: is a board with 5 in a row an ended game?
        Pente game = new Pente();
        assertFalse(game.hasEnded());
        game.makeMove(new Position(1, 1)); // a move by the first player
        game.makeMove(new Position(2, 1)); // a move by the second player
        game.makeMove(new Position(1, 2)); // a move by the first player
        game.makeMove(new Position(2, 2)); // a move by the second player
        game.makeMove(new Position(1, 3)); // a move by the first player
        game.makeMove(new Position(2, 3)); // a move by the second player
        game.makeMove(new Position(1, 4)); // a move by the first player
        game.makeMove(new Position(2, 4)); // a move by the second player
        game.makeMove(new Position(1, 5)); // a move by the first player
        assertTrue(game.hasEnded());

        // TODO 6: write at least 3 test cases
        //test case 2: one win via consecutive pieces
        Pente gameWinConsec = new Pente();
        gameWinConsec.makeMove(new Position(0, 0));
        gameWinConsec.makeMove(new Position(1,1)); //p2
        gameWinConsec.makeMove(new Position(0, 1)); //p1, 2
        gameWinConsec.makeMove(new Position(1, 2)); //p2
        gameWinConsec.makeMove(new Position(0, 2)); //p1, 3
        gameWinConsec.makeMove(new Position(1, 3)); //p2
        gameWinConsec.makeMove(new Position(0, 3)); //p1, 4
        gameWinConsec.makeMove(new Position(1, 4)); //p2, 4
        gameWinConsec.makeMove(new Position(0, 4)); //p1, 5
        assertTrue(gameWinConsec.hasEnded());

        //test case 3: captured pair, but not enough to win the game.
        Pente game1 = new Pente();
        game1.makeMove(new Position(3, 2)); // a move by the first player
        game1.makeMove(new Position(3, 3)); // a move by the second player
        game1.makeMove(new Position(4, 2)); // a move by the first player
        game1.makeMove(new Position(3, 4)); // a move by the second player
        game1.makeMove(new Position(3, 5)); // a move by the first player, which should capture the pair [(2, 3), (2, 4)]
        assertEquals(game1.capturedPairsNo(PlayerRole.FIRST_PLAYER), 1);
        assertFalse(game1.hasEnded());

        //test case 4
        Pente almostConsec = new Pente();
        almostConsec.makeMove(new Position(0, 0));
        almostConsec.makeMove(new Position(1,1)); //p2
        almostConsec.makeMove(new Position(0, 1)); //p1, 2
        almostConsec.makeMove(new Position(1, 2)); //p2
        almostConsec.makeMove(new Position(0, 2)); //p1, 3
        almostConsec.makeMove(new Position(1, 3)); //p2
        almostConsec.makeMove(new Position(0, 3)); //p1, 4
        almostConsec.makeMove(new Position(1, 4)); //p2, 4
        almostConsec.makeMove(new Position(0, 5)); //p1, 5
        assertFalse(almostConsec.hasEnded());
    }

    @Test
    void stateEqual() {
        Pente game1 = new Pente();
        Pente game2 = new Pente();
        Pente game3 = new Pente();

        // test 1: games with equal board states should be stateEqual()
        game1.makeMove(new Position(3, 3));
        game2.makeMove(new Position(3, 3));
        assertTrue(game1.stateEqual(game2));
        assertTrue(game2.stateEqual(game1));
        // test 2: games with unequal board states should not be stateEqual()
        game3.makeMove(new Position(0, 0));
        assertFalse(game1.stateEqual(game3));
        // TODO 7: write at least 3 test cases
        game1.makeMove(new Position(3, 2));

        game1.makeMove(new Position(3, 1));

        game2.makeMove(new Position(4, 3));

        game2.makeMove(new Position(2, 3));

        assertFalse(game2.stateEqual(game1));

        assertFalse(game1.stateEqual(game2));

        game3.makeMove(new Position(0, 0));

        game3.makeMove(new Position(1, 0));

        game3.makeMove(new Position(3, 0));

        game3.makeMove(new Position(2, 0));

        Pente game4= new Pente();// same score dif value

        game4.makeMove(new Position(3, 3));

        game4.makeMove(new Position(3, 2));

        game4.makeMove(new Position(3, 4));

        game4.makeMove(new Position(3, 3));

        assertFalse(game4.stateEqual(game3));

        assertFalse(game3.stateEqual(game4));

        // create tst case 3

        // same players same score different board value

        Pente game5= new Pente();

        Pente game6= new Pente();

        game5.makeMove(new Position(0, 0));

        game6.makeMove(new Position(3, 0));

        game5.makeMove(new Position(0, 1));

        game6.makeMove(new Position(3, 1));

        game5.makeMove(new Position(0, 3));

        game6.makeMove(new Position(3, 3));

        game5.makeMove(new Position(0, 2));

        game6.makeMove(new Position(3, 2));

        assertFalse(game5.stateEqual(game6));

        assertFalse(game6.stateEqual(game5));




    }
}