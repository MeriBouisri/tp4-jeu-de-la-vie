package tp4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainWindow implements ActionListener {
	
	private JFrame boardFrame;
	private JPanel boardPane, rowPane;
	private JPanel sidePane, buttonPane, infoPane;
	
	private JButton stopButton;
	
	private JLabel liveCellCountLabel;
	private JLabel genCountLabel;
	
	private int displayRate;
	private Cell[][] grid;
	
	private JPanel[][] gridPanel;
	
	private boolean activeEvolution = true;
	
	public MainWindow(Board board) {
		displayRate = board.getDisplayRate();
		grid = board.getCellGrid();
		
	}
	
	public void initialize() {
		
		boardFrame = new JFrame();
		boardFrame.setSize(1300, 1050);
		boardFrame.setTitle("JEU DE LA VIE");
		boardFrame.setResizable(false);
		boardFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		boardFrame.setLocationRelativeTo(null);
		
		sidePane = new JPanel();
		
		// Creer la zone du boutton d'arret 
		buttonPane = new JPanel();
		stopButton = new JButton("INTERROMPRE");
		stopButton.setToolTipText("Interrompre l'evolution " );
		stopButton.addActionListener(this);
		buttonPane.add(stopButton);
		
		// Creer la zone d'affichage d'info
		infoPane = new JPanel();
		infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.Y_AXIS));
		
		genCountLabel = new JLabel();
		infoPane.add(genCountLabel);
		
		liveCellCountLabel = new JLabel();
		infoPane.add(liveCellCountLabel);
		
		sidePane.add(buttonPane);
		sidePane.add(infoPane);
		
		
		// Creer la zone qui contiendra le board
		boardPane = new JPanel();
		boardPane.setLayout(new BoxLayout(boardPane, BoxLayout.Y_AXIS));
	
		// Ajouter les composantes au frame
		boardFrame.add(boardPane, BorderLayout.LINE_START);
		boardFrame.add(sidePane);
		
		// Construire la grille graphique
		gridPanel = new JPanel[grid.length][grid.length];
		
		for (int row = 0; row < grid.length; row++) {
			rowPane = new JPanel();
			rowPane.setLayout(new BoxLayout(rowPane, BoxLayout.X_AXIS));
			boardPane.add(rowPane);
			
			for (int col = 0; col < grid.length; col++) {
				gridPanel[row][col] = new JPanel();
				rowPane.add(gridPanel[row][col]);
			}
		}
		
		boardFrame.setVisible(true);
	}
	
	
	/**
	 * Cette methode affiche l'etat de la grille et le nombre de 
	 * cellules vivantes apres un certain nombre de generations déterminé 
	 * par {@code displayRate}.
	 * @param generationCount Nombre de generations
	 * @param liveCellCount Nombre de cellules vivantes
	 */
	public void displayGrid(int generationCount, long liveCellCount) {
		if (generationCount % displayRate == 0) {
			for (int row = 1; row < grid.length - 1; row++) {
				for (int col = 1; col < grid.length - 1; col++) {
					gridPanel[row][col].setBackground(getCellColor(row, col));
					gridPanel[row][col].setToolTipText(getCellInfo(row, col));
				}
			}
			
			// Actualiser les valeurs de la generation actuelle
			genCountLabel.setText("GÉNÉRATION : " + generationCount);
			liveCellCountLabel.setText("CELLULES VIVANTES : " + liveCellCount);
			
		}
	}
	
	/**
	 * Cette methode determine la couleur de la cellule selon son etat
	 * @param row ligne de la cellule
	 * @param col colonne de la cellule
	 * @return Couleur de la cellule
	 */
	private Color getCellColor(int row, int col) {
		return grid[row][col].isAlive() ? Color.black : Color.white;
	}
	
	/**
	 * Cette methode construit une chaine de caractères qui donne des informations
	 * sur une cellule individuelle.
	 * @param row ligne de la cellule
	 * @param col colonne de la cellule
	 * @return État et coordonnées de la cellule
	 */
	private String getCellInfo(int row, int col) {
		String cellState = grid[row][col].isAlive() ? "ALIVE" : "DEAD";
		String cellCoordinates = "(" + col + ", " + row + ")";
		
		return cellState + " " + cellCoordinates;
	}
	
	/**
	 * @return {@code true} tant que l'utilisateur ne clique pas sur le boutton d'interruption
	 */
	public boolean showActiveEvolution() {
		return activeEvolution;
	}
	
	/**
	 * @return {@code true} tant que la fenetre est active.
	 */
	public boolean isRunning() {
		return boardFrame.isActive();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == stopButton) {
			activeEvolution = !activeEvolution;
			stopButton.setText(activeEvolution ? "INTERROMPRE" : "REPRENDRE");
			
		}	
	}
}