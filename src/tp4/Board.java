package tp4;

import java.util.ArrayList;
import java.util.Random;

/**
 * La classe {@code Board} contient toutes les methodes nécessaires au fonctionnement du "Jeu de la vie".
 * Les propriétés de cette classe déterminent l'état de départ de la grille, qui va évoluer à partir de ces informations.
 */
public class Board {
	
	// Reglages du grid board
	private int sideLength;
	private double initialPopulation;
	private int displayRate;
	
	// Réglages par défaut
	private final int DEFAULT_SIDELENGTH = 100;
	private final double DEFAULT_INITIALPOPULATION = 0.5;
	private final int DEFAULT_DISPLAYRATE = 1;
	
	/**
	 * Cette constante est toujours ajoutée à la valeur de {@code sideLength} pour prendre en compte la bordure dans les dimensions de la grille.
	 * Lors de la creation de la grille, les 2 colonnes et 2 lignes additionnelles contiendront les cellules limitantes qui forment la bordure.
	 */
	private final int GRID_BORDER = 2;
	
	// 
	private GridArray gridArray;
	private Cell[][] cellGrid;
	private ArrayList<Cell> activeCellList;

	/**
	 * Constructeur sans parametres. Réglages par défaut. Création d'une grille faisant 100 x 100 cellules,
	 * avec un taux de remplissage initial de 50% et une intervalle d'affichage de 1.
	 */
	public Board() {
		this.sideLength = DEFAULT_SIDELENGTH + GRID_BORDER;
		this.initialPopulation = DEFAULT_INITIALPOPULATION;
		this.displayRate = DEFAULT_DISPLAYRATE;
		
		createBoard();
	}
	
	/**
	 * Constructeur avec parametres. Une nouvelle grille est créée selon
	 * les réglages.
	 * @param sideLength longueur des côtés de la grille 
	 * @param initialPopulation Taux de remplissage initial de la grille
	 * @param displayRate
	 */
	public Board(int sideLength, double initialPopulation, int displayRate) {
		this.sideLength = sideLength + GRID_BORDER;
		this.initialPopulation = initialPopulation;
		this.displayRate = displayRate;
		
		createBoard();
	}
	
	/**
	 * Methode qui crée l'état de la grille avec son état initial
	 */
	private void createBoard() {
		// Construire une nouvelle grille
		gridArray = new GridArray(sideLength);
		cellGrid = gridArray.getCellGrid();
		activeCellList = gridArray.getActiveCellList();
		
		// Créér l'état initial de la grille
		createRandomPopulation();
		updateBoard();
	}

	/**
	 * Méthode qui attribue un état 'vivant' aléatoirement a un nombre fixé de cellules actives.
	 * Le nombre de cellules vivantes au depart depend du parametre {@code initialPopulation}.
	 */
	private void createRandomPopulation() {
		Random random = new Random();
		
		// ArrayList qui contient un nombre donné d'indices aleatoires
		ArrayList<Integer> initialLiveCells = new ArrayList<Integer>();
		
		double population = initialPopulation * activeCellList.size();
		
		do { 
			int randomCell = random.nextInt(activeCellList.size());
			
			// Ajouter un index aleatoire et unique
			if (!initialLiveCells.contains(randomCell)) {
				initialLiveCells.add(randomCell); 
				
				// Activer la cellule qui se retrouve à l'indice aleatoire
				activeCellList.get(randomCell).setAlive();
			}
		} while (initialLiveCells.size() < population);
	}
	
	/**
	 * Cette méthode permet de compter le nombre de voisins (cellule active et vivante) qui entourent la cellule à la position donnée.
	 * Puisque la bordure est composée de cellules limitantes (cellules mortes qui occupent une position réelle dans {@code cellGrid}), 
	 * il est quand même possible d'évaluer le voisinage d'une cellule active qui touche la bordure sans mettre de restrictions et 
	 * sans dépasser les limites réelles de {@code cellGrid}.
	 * @param row ligne de la cellule donnée
	 * @param col colonne de la cellule donnée
	 * @return Nombre de voisins vivants autour de la cellule donnée
	 */
	private int countNeighborCells(int row, int col) {
		
		// Compteur commence à -1 pour ne pas compter la cellule centrale 
		int neighborCellCounter = cellGrid[row][col].isAlive() ? -1 : 0;
		
		// Évaluer seulement les cellules actives
		if (!cellGrid[row][col].isBorderCell()) {
			for (int i = -1; i < 2 ; i++) {
				for (int j = -1; j < 2; j++) {
					
					if (cellGrid[row + i][col + j].isAlive()) neighborCellCounter++;
				}
			}
		}
		return neighborCellCounter;
	}
	
	/**
	 * Cette méthode attribue un nouvel état à chaque cellule active
	 * selon les regles du jeu de la vie de Conway.
	 */
	public void nextGeneration() {
		int neighbors;
	
		for (int i = 1; i < cellGrid.length - 1; i++) {
			for (int j = 1; j < cellGrid.length -1; j++) {
				neighbors = countNeighborCells(i, j);
				
				// Regles du jeu de la vie
				if (neighbors != 2) 
					cellGrid[i][j].setState(neighbors == 3);
			}
		}
	}
	
	/** 
	 * Actualiser l'état de toutes les cellules. 
	 */
	public void updateBoard() {
		activeCellList.forEach((cell) -> cell.updateState());
	}
	
	public Cell[][] getCellGrid() {
		return cellGrid;
	}
	
	public ArrayList<Cell> getCellList() {
		return activeCellList;
	}
	
	/**
	 * Méthode d'accès à {@code sideLength}. Cette valeur représente le nombre de cellules participantes + les cellules limitantes ({@code GRID_BORDER})
	 * qui forment les dimensions de la grille carrée.
	 * @return Longueur reelle des côtés de la grille (en cellules)
	 */
	public int getSideLength() {
		return sideLength;
	}
	
	/**
	 * Méthode d'accès à {@code initialPopulation}. Représente le pourcentage de cellules vivantes au départ.
	 * @return Valeur entre 0.0 et 1.0 qui determine le taux de remplissage de la grille
	 */
	public double getInitialPopulation() {
		return initialPopulation;
	}
	
	/**
	 * Méthode d'accès à {@code displayRate}. Cette valeur indique que l'état de la grille sera affiché
	 * après un nombre donné de générations. Noter que si cette valeur est un multiple de la periode 
	 * d'un oscillateur, celui-ci paraitra immobile. Par exemple, un motif qui se repete a chaque 2 
	 * generations restera fixe si {@code displayRate} est un multiple de 2.
	 * @return Intervalle d'affichage de l'état de la grille
	 */
	public int getDisplayRate() {
		return displayRate;
	}
	
	// Setters
 	
	/**
	 * Méthode d'altération pour {@code sideLength}. La valeur entrée par l'utilisateur détermine le nombre de cellules participantes.
	 * @param sideLength Longueur des dimensions de la grille (nombre de cellules participantes)
	 */
 	public void setSideLength(int sideLength) {
 		this.sideLength = sideLength + GRID_BORDER;
 	}
 	
 	/**
 	 * Méthode d'altération pour {@code initialPopulation}. Cette valeur est le pourcentage de cellules vivantes au début.
 	 * @param initialPopulation Valeur entre 0.0 et 1.0 représentant le taux de remplissage de la grille
 	 */
	public void setInitialPopulation(double initialPopulation) {
		this.initialPopulation = initialPopulation;
	}
	
	/**
	 * Méthode d'altération pour {@code displayRate}. Détermine après quel nombre de générations
	 * l'affichage sera actualisé. Noter que si le nombre est pair, les motifs oscillateurs à 2 périodes
	 * auront l'air immobiles.
	 * @param displayRate Intervalle d'affichage de l'état de la grille
	 */
	public void setDisplayRate(int displayRate) {
		this.displayRate = displayRate;
	}
}