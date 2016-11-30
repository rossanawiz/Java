/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import generator.BackgroundGenerator;
import generator.BackgroundGeneratorThread;
import java.awt.Color;
import java.util.EmptyStackException;
import static org.hamcrest.CoreMatchers.not;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Felipe
 */
public class SudokuPanelTest {
    
    private MainFrame mf;
    SudokuPanel panel;
    SudokuPanel panel2;
    String sudoku;
    
    public SudokuPanelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        mf = new MainFrame(null);
        panel = new SudokuPanel(mf);
        panel2 = new SudokuPanel(mf);
        DifficultyLevel level = new DifficultyLevel(DifficultyType.MEDIUM, 1000, java.util.ResourceBundle.getBundle("intl/MainFrame").getString("MainFrame.medium"), new Color(100, 255, 100), Color.BLACK);
        GameMode mode = GameMode.PLAYING;
        BackgroundGenerator bg = new BackgroundGenerator();
        sudoku = bg.generate(level, mode);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of undo method, of class SudokuPanel.
     * Testa a função de desfazer jogada.
     * Atribui o mesmo sudoku para dois paineis, executa uma jogada e
     * a desfaz logo depois, em seguida compara se os dois estão iguais.
     */
    @Test
    public void testUndo() {
        panel.setSudoku(sudoku);
        panel2.setSudoku(sudoku);
        panel2.setCellFromCellZoomPanel(5);
        panel2.undo();
        assertEquals(panel.getSudokuString(ClipboardMode.PM_GRID), panel2.getSudokuString(ClipboardMode.PM_GRID));
    }

    /**
     * Test of redo method, of class SudokuPanel.
     * Testa a função de refazer jogada.
     * Executa uma ação e faz uma copia do painel
     * Desfaz a ação e a refaz
     * compara com a cópia para testar se o sudoku voltou ao estado após a jogada
     */
    @Test
    public void testRedo() {
        panel2.setSudoku(sudoku);
        panel2.setCellFromCellZoomPanel(5);
        panel.setSudoku(panel2.getSudokuString(ClipboardMode.PM_GRID));
        panel2.undo();
        panel2.redo();
        assertEquals(panel.getSudokuString(ClipboardMode.PM_GRID), panel2.getSudokuString(ClipboardMode.PM_GRID));
    }
        
    /**
     * Test of redo method, of class SudokuPanel.
     * Testa a função de refazer jogada.
     * Testa o método Redo sem desfazer alguma jogada
     * O método não deve tentar retirar o sudoku da pilha que está vazia.
     */
    @Test
    public void testRedo2() {
        panel.setSudoku(sudoku);
        panel.setCellFromCellZoomPanel(5);
        try{
           panel.redo();
        }catch(EmptyStackException ex){
            fail("Redo retirou um valor não existente da pilha.");
        }
        
    }
    
    /**
     * Test of SolveUpTo method, of class SudokuPanel.
     * Testa o método que resolve o jogo
     * Verifica se o método resolveu o jogo de maneira correta,
     * sem valores repetidos nas linhas e colunas.
     */
    @Test
    public void testSolveUpTo() {
        panel.setSudoku(sudoku);
        panel.solveUpTo();
        String grid = panel.getSudokuString(ClipboardMode.VALUES_ONLY);
        char cells[] = grid.toCharArray();
        int lineStart = 0;
        int lineEnd = 8;
        int columnStart = 0;
        int columnEnd = 81;
        //Testa as 9 linhas e 9 colunas da tabela do jogo
        for(int i=0; i<9; i++){
            //Testa linhas da tabela buscando por números repetidos
            for(int j=lineStart; j<=lineEnd; j++){
                for(int h=j; h<lineEnd; h++){
                    if(cells[h]!='.')
                        assertThat(cells[h], not(cells[h+1]));
                }                
            }
            //Testa colunas da tabela buscando por números repetidos
            for(int j=columnStart; j<=columnEnd; j+=9){
                for(int h=j; h<lineEnd; h+=9){
                    if(cells[h]!='.')
                        assertThat(cells[h], not(cells[h+9]));
                }                
            }
            lineStart += 9;
            lineEnd += 9;
            columnStart++;
            columnEnd++;
        }
        
        
    }
    
}
