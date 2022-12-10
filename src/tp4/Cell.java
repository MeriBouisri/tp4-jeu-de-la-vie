package tp4;

/**
 * Cette classe permet de créer une cellule. Un objet de type {@code Cell} possède 2 états possibles : {@code true} (vivante) ou {@code false} (morte). 
 * Lorsque plusieurs objets {@code Cell} existent, il est possible de leur attribuer des coordonnées ({@code row, col}) pour les identifier selon leur position.
 * La propriété {@code borderStatus} permettra de distinguer parmi les cellules actives et les cellules limitantes. Les cellules actives et limitantes seront
 * définies selon leur position dans la grille (voir classe {@code arrayGrid}).
 */
public class Cell {
	
	private boolean state, newState;
	private boolean borderStatus;
	private int row, col;

	/**
	 * Constructeur sans paramètres. Instance d'une cellule morte au point (0, 0).
	 */
	public Cell() {
		this.state = false;
		this.row = 0;
		this.col = 0;
	}
	/**
	 * Constructeur avec paramètres qui initialisent la position de la cellule selon son emplacement dans la grille.
	 * @param row ligne de la cellule
	 * @param col colonne de la cellule
	 */
	public Cell(int row, int col) {
		this.state = false;
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Constructeur avec paramètre qui initialise l'état de la cellule.
	 * @param state état de la cellule 
	 */
	public Cell(boolean state) {
		this.state = state;
	}
	
	
	/**
	 * Méthode d'altération pour {@code newState}. Une cellule morte a un état {@code false}. Une cellule vivante a un état
	 * {@code true}. Le nouvel état est enregistré, mais il sera seulement attribué lorsque {@code updateState()} sera appelé.
	 * @param state État de la cellule
	 */
	public void setState(boolean state) {
		newState = state;
	}
	
	/**
	 * Méthode d'alteration pour {@code newState}. La cellule sera vivante lorsque {@code updateState()} sera appelé.
	 */
	public void setAlive() {
		newState = true;
	}
	
	/**
	 * Méthode d'altération pour {@code newState}. La cellule sera morte lorsque {@code updateState()} sera appelé.
	 */
	public void setDead() {
		newState = false;
	}
	
	/**
	 * Cette méthode actualise le nouvel état de la cellule lors de sa generation.
	 */
	public void updateState() {
		this.state = newState;
	}

	
	/**
	 * Méthode d'accès pour {@code state}. L'état retourné sera celui qui a été attribué au dernier
	 * appel de {@code updateState()}.
	 * @return {@code true} si la cellule est vivante, {@code false} si la cellule est morte.
	 */
	public boolean getState() {
		return state;
	}
	
	/**
	 * Méthode permettant de determiner si une cellule est vivante.
	 * @return {@code true} si la cellule est vivante
	 */
	public boolean isAlive() {
		/* NOTE : isAlive() et getState() agissent de façon identique, mais le nom de ces méthodes
	 	sert à communiquer le contexte de leur utilisation et facilite donc la lecture du code. */
		return state; 
	}
	
	/**
	 * Méthode d'altération de la position de la cellule dans la grille.
	 * @param row ligne de la cellule
	 * @param col colonne de la cellule
	 */
	public void setPosition(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Méthode d'accès pour {@code row}. 
	 * @return La ligne dans laquelle se trouve la cellule
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Méthode d'accès pour {@code col}.
	 * @return La colonne dans laquelle se trouve la cellule
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * Méthode pour definir le {@code borderStatus} d'une cellule. Permet de distinguer parmi les cellules limitantes ({@code borderStatus = true}) 
	 * et les cellules actives ({@code borderStatus = false}).
	 * @param borderStatus Determiner si la cellule se retrouve sur la bordure de la grille
	 */
	public void setBorderCell(boolean borderStatus) {
		this.borderStatus = borderStatus;
	}
	
	/**
	 * Méthode d'accès pour {@code borderStatus}. Le rôle d'une cellule limitante est de former une fausse
	 * bordure autour de la grille. Elles ne participent pas au jeu de la vie, comme les cellules actives,
	 * mais elles forment un espace vide qui peut être franchit sans dépasser réellement les limites
	 * de la grille. Ainsi, il sera possible d'evaluer toutes les positions autour des cellules actives qui touchent la "pseudo-bordure".
	 * @return {@code true} si la cellule a été definie comme étant sur le bord de la grille
	 */
	public boolean isBorderCell() {
		return borderStatus;
	}
}