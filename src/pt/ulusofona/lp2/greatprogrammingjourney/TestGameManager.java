package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {

    GameManager gm;

    public String[][] jogadoresValidos(){
        String[][] jogadores = new String[3][4];

        jogadores[0][0] = "1";
        jogadores[0][1] = "Ana";
        jogadores[0][2] = "Java;Python";
        jogadores[0][3] = "Brown";

        jogadores[1][0] = "2";
        jogadores[1][1] = "Bruno";
        jogadores[1][2] = "C;C++";
        jogadores[1][3] = "Blue";

        jogadores[2][0] = "3";
        jogadores[2][1] = "Clara";
        jogadores[2][2] = "JavaScript";
        jogadores[2][3] = "Green";

        return jogadores;

    }//3 jogadores

    public String[][] jogadoresValidos2(){
        String[][] jogadores = new String[4][4];

        jogadores[0][0] = "1";
        jogadores[0][1] = "Ana";
        jogadores[0][2] = "Java;Python";
        jogadores[0][3] = "Brown";

        jogadores[1][0] = "2";
        jogadores[1][1] = "Bruno";
        jogadores[1][2] = "C;C++";
        jogadores[1][3] = "Blue";

        jogadores[2][0] = "3";
        jogadores[2][1] = "Clara";
        jogadores[2][2] = "JavaScript";
        jogadores[2][3] = "Green";
        jogadores[3][0] = "4";
        jogadores[3][1] = "John";
        jogadores[3][2] = "Java;Python";
        jogadores[3][3] = "Purple";

        return jogadores;

    }

    @Test
    public void testeCreateInitialBoard1(){
        gm = new GameManager();

        int boardSize = 7;

        boolean x = gm.createInitialBoard(jogadoresValidos(), boardSize);
        assertTrue(x);

    }

    @Test
    public void testeCreateInitialBoard2(){
        gm = new GameManager();

        int boardSize = 5;

        boolean x = gm.createInitialBoard(jogadoresValidos(), boardSize);
        assertFalse(x);

    }

    @Test
    public void testeGetSlotInfo1(){
        gm = new GameManager();
        int boardSize = 7;
        gm.createInitialBoard(jogadoresValidos(), boardSize);
        String[] result = new String[3];

        result[0]="1,2,3";
        result[1]="";
        result[2]="";
        assertArrayEquals(result, gm.getSlotInfo(1));
    }

    @Test
    public void testeGetSlotInfo2(){
        gm = new GameManager();
        int boardSize = 7;
        gm.createInitialBoard(jogadoresValidos(), boardSize);

        String[] result = new String[3];
        result[0]="";
        result[1]="";
        result[2]="";
        assertArrayEquals(result, gm.getSlotInfo(2));
    }

    @Test
    public void testeGetSlotInfo3(){
        gm = new GameManager();
        int boardSize = 7;
        gm.createInitialBoard(jogadoresValidos(), boardSize);

        assertArrayEquals(null, gm.getSlotInfo(999));
    }

    @Test
    public void testeGetCurrentPlayerId(){
        gm = new GameManager();
        int boardSize = 7;
        gm.createInitialBoard(jogadoresValidos(), boardSize);

        assertEquals(1, gm.getCurrentPlayerID());

    }

    @Test
    public void testeGetProgrammerInfo1(){
        gm = new GameManager();
        int boardSize = 7;
        gm.createInitialBoard(jogadoresValidos(), boardSize);

        String[] str = new String[7];
        str[0] = "1";               // ID
        str[1] = "Ana";             // Nome
        str[2] = "Java; Python";    // Linguagens (Nota: verifica se o teu código mete espaço ou não depois do ;)
        str[3] = "Brown";           // Cor
        str[4] = "1";               // Posição Inicial
        str[5] = "";        // Ferramentas
        str[6] = "Em Jogo";         // Estado Inicial

        assertArrayEquals(str, gm.getProgrammerInfo(1));
    }

    @Test
    public void testeGetProgrammerInfo2(){
        gm = new GameManager();
        int boardSize = 7;
        gm.createInitialBoard(jogadoresValidos(), boardSize);

        assertArrayEquals(null, gm.getProgrammerInfo(999));
    }

    @Test
    public void testeGetProgrammerInfoAsStr1(){
        gm = new GameManager();
        int boardSize = 7;
        gm.createInitialBoard(jogadoresValidos(), boardSize);

        String s = "1 | Ana | 1 | No tools | Java; Python | Em Jogo";
        assertEquals(s, gm.getProgrammerInfoAsStr(1));
    }

    @Test
    public void testeGetProgrammerInfoAsStr2(){
        gm = new GameManager();
        int boardSize = 7;
        gm.createInitialBoard(jogadoresValidos(), boardSize);

        assertNull(gm.getProgrammerInfoAsStr(999));
    }

    @Test
    public void testeMoveCurrentPlayer1(){
        gm = new GameManager();
        int boardSize = 7;
        gm.createInitialBoard(jogadoresValidos(), boardSize);

        gm.moveCurrentPlayer(3);

        String s = "1 | Ana | 4 | No tools | Java; Python | Em Jogo";
        assertEquals(s, gm.getProgrammerInfoAsStr(1));

    }

    @Test
    public void testeMoveCurrentPlayer2(){
        gm = new GameManager();
        int boardSize = 7;
        gm.createInitialBoard(jogadoresValidos(), boardSize);

        assertFalse(gm.moveCurrentPlayer(0));

    }

    @Test
    public void testeMoveCurrentPlayer3(){
        gm = new GameManager();
        int boardSize = 7;
        gm.createInitialBoard(jogadoresValidos(), boardSize);

        assertFalse(gm.moveCurrentPlayer(7));

    }

    @Test
    public void testeGameIsOver1(){
        gm = new GameManager();
        int boardSize = 7;
        gm.createInitialBoard(jogadoresValidos(), boardSize);

        assertFalse(gm.gameIsOver());
    }

    @Test
    public void testeGameIsOver2(){
        gm = new GameManager();
        int boardSize = 7;
        gm.createInitialBoard(jogadoresValidos(), boardSize);

        gm.moveCurrentPlayer(6);
        gm.moveCurrentPlayer(6);
        gm.moveCurrentPlayer(6);
        gm.moveCurrentPlayer(6);
        assertTrue(gm.gameIsOver());
    }

    @Test
    public void testeGetGameResults(){
        gm = new GameManager();
        int boardSize = 7;
        gm.createInitialBoard(jogadoresValidos(), boardSize);

        gm.moveCurrentPlayer(5);


        gm.reactToAbyssOrTool();


        gm.gameIsOver();

        gm.moveCurrentPlayer(3);

        gm.reactToAbyssOrTool();

        gm.gameIsOver();

        gm.moveCurrentPlayer(4);

        gm.reactToAbyssOrTool();

        gm.gameIsOver();

        gm.moveCurrentPlayer(6);

        gm.reactToAbyssOrTool();

        gm.gameIsOver();


        ArrayList<String> str = new ArrayList<>();

        str.add("THE GREAT PROGRAMMING JOURNEY");
        str.add("");
        str.add("NR. DE TURNOS");
        str.add("4");
        str.add("");
        str.add("VENCEDOR");
        str.add("Ana");
        str.add("");
        str.add("RESTANTES");
        str.add("Clara 5");
        str.add("Bruno 4");



    assertEquals(str,  gm.getGameResults());

    }

    @Test
    public void testeGetGameResults2(){
        gm = new GameManager();
        int boardSize = 15;
        gm.createInitialBoard(jogadoresValidos2(), boardSize);

        gm.moveCurrentPlayer(5);
        gm.reactToAbyssOrTool();
        gm.gameIsOver();

        gm.moveCurrentPlayer(3);
        gm.reactToAbyssOrTool();
        gm.gameIsOver();

        gm.moveCurrentPlayer(4);
        gm.reactToAbyssOrTool();
        gm.gameIsOver();

        gm.moveCurrentPlayer(3);
        gm.reactToAbyssOrTool();
        gm.gameIsOver();


        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        gm.gameIsOver();

        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        gm.gameIsOver();

        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        gm.gameIsOver();

        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        gm.gameIsOver();

        System.out.println(gm.getProgrammerInfoAsStr(1));
        System.out.println(gm.getProgrammerInfoAsStr(2));
        System.out.println(gm.getProgrammerInfoAsStr(3));

        assertEquals(gm.getProgrammerInfoAsStr(4), "4 | John | 5 | No tools | Java; Python | Em Jogo");


    }

}
