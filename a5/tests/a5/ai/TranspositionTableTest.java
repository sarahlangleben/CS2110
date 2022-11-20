package a5.ai;

import static org.junit.jupiter.api.Assertions.*;

import a5.ai.TranspositionTable.StateInfo;
import a5.logic.Pente;
import a5.logic.Position;
import a5.logic.TicTacToe;
import cms.util.maybe.NoMaybeValue;
import javax.print.attribute.standard.PagesPerMinute;
import org.junit.jupiter.api.Test;

class TranspositionTableTest {

    @Test
    void testConstructor() {
        // TODO 1: write at least 1 test case (done)
        //case: empty table
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();
        assertEquals(0, table.size());
        TicTacToe state = new TicTacToe();
        assertThrows(NoMaybeValue.class, () -> table.getInfo(state).get());
    }

    @Test
    void getInfo() throws NoMaybeValue {
        // test case 1: look for a state that is in the table
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();
        TicTacToe state = new TicTacToe();
        table.add(state, 0, GameModel.WIN);
        StateInfo info = table.getInfo(state).get();
        assertEquals(GameModel.WIN, info.value());
        assertEquals(0, info.depth());

        //test case 2: look for a state not in the table
        TicTacToe state2 = state.applyMove(new Position(0, 0));
        assertThrows(NoMaybeValue.class, () -> table.getInfo(state2).get());

        // TODO 2: write at least 3 more test cases (done, not well)
        //test case 3: empty table, checking for a state not in there --> noMaybeValue gets thrown
        TranspositionTable<TicTacToe> tableEmp = new TranspositionTable<>();
        assertThrows(NoMaybeValue.class, () -> tableEmp.getInfo(state2).get());

        //test case 4: look for a state that applied a move
        TranspositionTable<Pente> case4 = new TranspositionTable<>();
        Pente state4 = new Pente();
        case4.add(state4, 2, GameModel.WIN);

        StateInfo info4 = case4.getInfo(state4).get();
        assertEquals(GameModel.WIN, info4.value());
        assertEquals(2, info4.depth());
        state4.applyMove(new Position(2, 3));
        assertNotEquals(info.hashCode(), info4.hashCode());

        //test case 5: non-empty table, contains states, but not the one getInfo is looking for.
        TranspositionTable<Pente> case5 = new TranspositionTable<>();
        Pente state5 = new Pente();
        Pente state5_1 = new Pente();
        state5.makeMove(new Position(2, 1));
        state5_1.makeMove(new Position(1, 6));
        assertNotEquals(state5_1.hashCode(), state5.hashCode()); //they should have different hashcode now, right?
        case5.add(state5, 1, GameModel.WIN);
        case5.add(state5_1, 5, GameModel.WIN);
//        assertEquals(2, case5.size()); //different state = bigger size, right?
        assertThrows(NoMaybeValue.class, () -> case5.getInfo(state4).get());
    }

    @Test
    void add() throws NoMaybeValue {
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();

        // test case 1: add a state and check it is in there
        TicTacToe state = new TicTacToe();
        table.add(state, 0, GameModel.WIN);

        StateInfo info = table.getInfo(state).get();
        assertEquals(GameModel.WIN, info.value());
        assertEquals(0, info.depth());

        //TODO 3: write at least 3 more test cases (done)
        //case 2: adding to an empty array
        TranspositionTable<TicTacToe> table1 = new TranspositionTable<>();
        TicTacToe state1 = new TicTacToe();
        assertEquals(0, table1.size()); //size before adding
        table1.add(state1, 0, GameModel.WIN);
        assertEquals(1, table1.size()); //size should increase

       //case 3: adding to transposition table that isn't empty to begin with.
        TicTacToe state2 = new TicTacToe();
        table1.add(state2, 1, GameModel.WIN);
        assertEquals(1, table1.size());
        //depth is the same, nothing should be replaced.
        StateInfo info1 = table1.getInfo(state2).get();
        assertEquals(GameModel.WIN, info1.value());
        assertEquals(1, info1.depth());

        //case 4:
        Pente state4 = new Pente();
        TranspositionTable<Pente> penteTable4 = new TranspositionTable<>();
        penteTable4.add(state4, 0, 5);
        assertEquals(penteTable4.size(), 1);
        state4.makeMove(new Position(0,0));
        penteTable4.add(state4, 3, 5);
        assertEquals(penteTable4.size(), 2);

        //case 5: making sure grow works
        Pente state5 = new Pente();
        TranspositionTable<Pente> penteTable5 = new TranspositionTable<>();
        for(int i = 0; i == 75; i++){
            penteTable5.add(state5, 1, i);
        }
    }
}