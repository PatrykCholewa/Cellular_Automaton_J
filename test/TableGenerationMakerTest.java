import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Patryk on 30.04.2017.
 */
class TableGenerationMakerTest {

    TableGenerationMaker tgm;

    Rules rules;

    @BeforeEach
    void setUp() {

        String []rules = {
                "0/9/1/0",
                "1/9/1/2",
                "2/9/1/3",
                "1/12/1/3",
        };

        tgm = new TableGenerationMaker();
        tgm.setRules( rules );
        tgm.setNeighbourhood( "Moore" );
        tgm.setBoundary( "Void" );
        tgm.setNewBoard( 4 , 5 );

    }

    @Test
    void getRulesTest() {

        String []rules = {
                "0/9/1/0",
                "1/9/1/2",
                "2/9/1/3",
                "1/12/1/3",
        };

        assertArrayEquals( rules , tgm.getRules() );

    }

    @Test
    void getNeighbourhoodTest() {

        assertEquals( "Moore" , tgm.getNeighbourhood() );

    }

    @Test
    void getBoundaryTest() {

        assertEquals( "Void" , tgm.getBoundary() );

    }

}