import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Patryk on 25.04.2017.
 */
class TableTest{

    Table table;

    @BeforeEach
    void setUp() {

        int [][]board = {
                {0,0,1,0,0},
                {0,0,0,1,0},
                {0,1,1,1,0},
                {0,0,0,0,0},
        };

        table = new Table( 4 , 5 , board );

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void setBoardTestError() {

        int [][]board = {{1},{1},{1},{1},{1},{1}};

        assertThrows( Error.class, () -> {
            table.setBoard( board );
        });

    }

    @Test
    void getRowNumberTest() {

        assertEquals( 4 , table.getRowNumber() );

    }

    @Test
    void getColumnNumberTest() {

        assertEquals( 5 , table.getColumnNumber() );

    }

    @Test
    void getCellValueTest() {

        assertEquals( 1 , table.getCellValue( 2 , 3));

    }

    @Test
    void getCellValueTestIndexOutBounds() {

        assertThrows( IndexOutOfBoundsException.class, () -> {
            table.getCellValue( 4 , 4 );
        });

    }

    @Test
    void getBoardTest() {

        int[][] expect = {
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0},
        };

        int[][] result = table.getBoard();

        assertArrayEquals( expect , result );

    }

    //@Test
    void makeNextGenerationTest() {

        int[][] expect = {
                {0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 1, 0},
                {0, 0, 1, 0, 0},
        };

        int[][] result = table.getBoard();

        assertArrayEquals( expect , result );

    }

}