package sudoku;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class SudokuFrame extends JFrame {

	//painéis da interface
	private JPanel buttonSelectionPanel;
	private SudokuPanel sPanel;
	
	//construtor principal
	public SudokuFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setMinimumSize(new Dimension(800,600));
		

		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Game");//menu principal 
		JMenu newGame = new JMenu("New Game"); //menu para novos jogos

		//opções com jogos com diferentes modos
		JMenuItem sixBySixGame = new JMenuItem("6 By 6 Game"); //modo de jogo 6x6
		sixBySixGame.addActionListener(new NewGameListener(SudokuPuzzleType.SIXBYSIX,30));
		JMenuItem nineByNineGame = new JMenuItem("9 By 9 Game");//modo de jogo 9x9
		nineByNineGame.addActionListener(new NewGameListener(SudokuPuzzleType.NINEBYNINE,26));
		JMenuItem twelveByTwelveGame = new JMenuItem("12 By 12 Game");//modo de jogo 12x12
		twelveByTwelveGame.addActionListener(new NewGameListener(SudokuPuzzleType.TWELVEBYTWELVE,20));
		
		//adiciona as opões dos jogos ao menu "New game"
		newGame.add(sixBySixGame);
		newGame.add(nineByNineGame);
		newGame.add(twelveByTwelveGame);
		

		file.add(newGame);
		menuBar.add(file);//adiciona o menu game a barra da janela
		this.setJMenuBar(menuBar);
		

		JPanel windowPanel = new JPanel();
		windowPanel.setLayout(new FlowLayout());//Define o layout para adicionar os componentes 
		windowPanel.setPreferredSize(new Dimension(800,600));//tamanho do painel
		
		buttonSelectionPanel = new JPanel();
		buttonSelectionPanel.setPreferredSize(new Dimension(90,500));

		sPanel = new SudokuPanel(); //criação do painel do tabuleiro 
		
		//adiciona o painel de tabuleiro
		windowPanel.add(sPanel); 
		windowPanel.add(buttonSelectionPanel); //adiciona o painel dos botôes


		this.add(windowPanel);
		
		//abre a janlea já rodando o jogo 9x9
		rebuildInterface(SudokuPuzzleType.NINEBYNINE, 26);
	}
	
	//método para reconstruir a onterface com um jogo novo 
	public void rebuildInterface(SudokuPuzzleType puzzleType,int fontSize) {

		//gera um novo tabuleiro 
		SudokuPuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku(puzzleType);

		//configura o novo tabuleiro no painel 
		sPanel.newSudokuPuzzle(generatedPuzzle);
		sPanel.setFontSize(fontSize); //tamanho da fonte 

		//remove os botões antigos e gera novos com base no jogo escolhido 
		buttonSelectionPanel.removeAll();
		for(String value : generatedPuzzle.getValidValues()) {
			JButton b = new JButton(value);
			b.setPreferredSize(new Dimension(40,40));
			b.addActionListener(sPanel.new NumActionListener());
			buttonSelectionPanel.add(b); //adiciona o botão ao painel 
		}
		sPanel.repaint();
		buttonSelectionPanel.revalidate();
		buttonSelectionPanel.repaint();
	}
	
	//classe privada para lidar com o novo modo de jogo escolhido 
	private class NewGameListener implements ActionListener {

		private SudokuPuzzleType puzzleType; //tipo de tabuleiro 
		private int fontSize; //tamanho da fonte 
		
		public NewGameListener(SudokuPuzzleType puzzleType,int fontSize) {
			this.puzzleType = puzzleType;
			this.fontSize = fontSize;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//reconsgrtou a interface com o tipo de jogo e tamanho da fonte 
			rebuildInterface(puzzleType,fontSize);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//cria uma instância da janela prinvipal e a torna visível
				SudokuFrame frame = new SudokuFrame();
				frame.setVisible(true);
			}
		});
	}
}
