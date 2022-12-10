package tp4;

/**
 * TRAVAIL PRATIQUE 4 - JEU DE LA VIE : 
 * Ce programme permet de suivre l'évolution des cellules à l'intérieur d'une grille selon les règles du jeu de la vie de Conway.
 * Chaque nouvelle execution du programme génère un état initial différent selon les paramètres de la classe {@code Board}. 
 * A côté de la grille, l'utilisateur peut voir le nombre de génération et de cellules vivantes actuelles.
 * À n'importe quel moment de l'execution du programme, l'utilisateur peut interrompre l'évolution de la grille
 * pour observer son état à l'instant.
 * Lorsque la grille ne contient plus que des motifs immobiles/repetitifs, l'utilisateur est
 * informé qu'un état de stabilité a été atteint. L'évolution finit seulement lorsque l'utilisateur 
 * ferme la fenetre.
 * 
 * @version 2.0
 * @author MeriemeBouisri
 * 
 */
public class Main {
	
	public static Board board;
	public static MainWindow window;
	public static GenerationTracker generation;

	public static int genCounter;
	public static long liveCellCounter;
	
	public static void main(String[] args) throws InterruptedException {
		
		// Construire le board avec des reglages par défaut
		board = new Board();
		
		// Initialiser les classes dépendantes du board
		generation = new GenerationTracker(board);
		window = new MainWindow(board);
		
		window.initialize();
		
		// Commencer l'evolution de la grille
		while (window.isRunning()) {
			
			// récupérer les info de la génération actuelle
			genCounter = generation.getGenerationCounter();
			liveCellCounter = generation.countLivingCells();
			
			// Permet de ralentir la vitesse d'affichage pour mieux voir l'evolution
			Thread.sleep(50);
			
			// Afficher l'état de la grille et nombre de cellules vivantes à l'intervalle donnée
			window.displayGrid(genCounter, liveCellCounter);
			
			
			if (window.showActiveEvolution()) {
				
				// Nouvelle génération
				board.nextGeneration();
				board.updateBoard();
				
				// Enregistrement les infos de la génération actuelle
				generation.trackInfo();
				
				// Analyse de la stabilité de la grille
				if (generation.hasReachedStability()) generation.showStabilityGeneration();
	
			}
		}
	}
}
