package tp4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Cette classe sert à placer des objets des type {@code Cell} dans un format de grille en 2 dimensions et dans un format de liste.
 * Les cellules contenues dans ces tableaux seront les mêmes tout au long du programme, et leur position est assignée en même temps que 
 * leur placement dans la grille.
 * La pseudo-bordure de la grille est aussi construite dans cette classe par l'attribution du statut de cellules limitantes ({@code borderCell}).
 *
 */
public class GridArray {
	
	private int gridSize;
	
	// Initialiser les limites de la grille
	private final int minBound;
	private int maxBound;
	
	/** 
	 * Array à 2 dimensions permettant d'accéder aux cellules selon leur emplacement dans la grille.
	 * Contient les cellules actives et limitantes.
	 */
	private Cell[][] cellGrid;
	
	/** 
	 * ArrayList contenant les mêmes cellules que {@code cellGrid}, sauf les cellules limitantes.
	 * Permet d'accéder aux cellules actives lorsque leur emplacement dans la grille n'est pas important. 
	 */
	private ArrayList<Cell> activeCellList;
	
	
	/**
	 * Constructeur avec paramètre. Permet d'initialiser la longueur des dimensions de {@code cellGrid}.
	 * @param gridSize Dimensions de la grille (en cellules)
	 */
	public GridArray(int gridSize) {
		this.gridSize = gridSize;
		this.minBound = 0;
		this.maxBound = gridSize - 1;
		
		createGrid();
	}
	
	/**
	 * Cette méthode crée le Array 2d et le ArrayList qui contiennent les cellules.
	 */
	private void createGrid() {
		// initialiser les tableaux
		cellGrid = new Cell[gridSize][gridSize];
		activeCellList = new ArrayList<Cell>();
		
		for (int row = 0; row < gridSize; row++) {
			for (int col = 0; col < gridSize; col++) {
				
				// Créer le ArrayList, la array 2d et initialiser les cellules avec leur position
				activeCellList.add(cellGrid[row][col] = new Cell(row, col));
				assignBorderCellStatus(row, col);
			}
		}
		
		setBorder();
	}
	
	/**
	 * Cette méthode permet d'identifier les cellules se retrouvant sur la bordure de la grille.
	 * @param row ligne de la cellule
	 * @param col colonne de la cellule
	 */
	private void assignBorderCellStatus(int row, int col) {
		
		// Coordonnées des limites de la grille
		Predicate<Integer> gridBounds  = b -> b == minBound || b == maxBound;
		
		// Vérifier si la position correspond aux limites de la grille
		boolean isAtBorder = Arrays.asList(row, col).stream().anyMatch(gridBounds);
		
		// Si oui, la cellule est identifiée comme étant une cellule limitante
		cellGrid[row][col].setBorderCell(isAtBorder);
	}
	
	/**
	 * Méthode qui permet d'ignorer les cellules limitantes ({@code borderCell}) lors de la manipulation de {@code activeCellList}.
	 * Les cellules limitantes agissent en tant que bordure dans la grille. Elles demeurent mortes tout au long de l'exécution du programme.
	 */
	private void setBorder() {
		activeCellList.stream()
			.filter(Cell::isBorderCell)
			.forEach(Cell::setDead);
		
		activeCellList.removeIf(Cell::isBorderCell);
	}
	
	/**
	 * Méthode d'accès pour {@code cellGrid}. 
	 * @return Matrice contenant tous les objets de type {@code Cell} placés selon leur emplacement dans la grille.
	 */
	public Cell[][] getCellGrid() {
		return cellGrid;
	}
	
	/**
	 * Méthode d'accès pour {@code activeCellList}. 
	 * @return Un ArrayList contenant toutes les cellules actives en forme de liste.
	 */
	public ArrayList<Cell> getActiveCellList() {
		return activeCellList;
	}
}
