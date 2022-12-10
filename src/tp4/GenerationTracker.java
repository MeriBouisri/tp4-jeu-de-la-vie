/**
 * 
 */
package tp4;

import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Cette classe traite de tout ce qui touche l'évaluation des générations de la grille.
 * 
 * Cette classe faisait originalement partie de la classe {@code Board}. Cependant, {@code GenerationTracker} n'est pas
 * une classe très versatile vu qu'elle sert principalement à répondre aux exigences du TP4 et fut donc écrite de manière à ce
 * qu'elle puisse être modifiée facilement, ou même supprimée sans trop impacter le programme.
 * Veuillez noter que cette classe possède 2 constantes dont les valeurs ont été choisies plus ou moins arbitrairement, mais
 * qui fonctionnent quand même ({@code MAX_OSCILLATOR_PERIOD} et {@code MIN_STABLE_GENERATIONS}). Ces choix proviennent
 * d'observations personnelles et seront expliquées dans les commentaires.
 */
public class GenerationTracker {
	
	// Cette classe obtient ses informations de la liste de cellules du board
	private ArrayList<Cell> activeCellList;
	
	private long[] previousLivingCellCountList;
	
	/**
	 * Dans le jeu de la vie, un oscillateur est un motif qui se répète après un certain
	 * nombre de générations (periode). Le nombre de periodes d'oscillation maximal représente
	 * l'intervalle de generation ou il est possible de detecter la stabilite de la grille.	
	 * {@code MAX_OSCILLATOR_PERIOD} a une valeur de 20.
	 * Cette valeur a ete choisie, car, en testant mon programme, je n'ai pas repéré de motif qui se 
	 * répète sur plus de 20 periodes.
	 */
	private int MAX_OSCILLATOR_PERIOD = 20;
	
	/**
	 * Cette variable représente le nombre de générations consécutives minimales que ça prend pour confirmer
	 * qu'une progression nulle/répétitive soit considérée comme étant permanente, indiquant donc que la
	 * grille a atteint un point stagnant. la valeur de {@code MIN_STABLE_GENERATIONS} est de 100 generations minimales. 
	 * Cela semble être une intervalle raisonnable pour s'assurer qu'il n'y a pas de coincidences.
	 */
	private int MIN_STABLE_GENERATIONS = 100;
	
	// Initialiser les compteurs de la classe
	private static int consecutivePatternCounter = 0;
	private static int generationCounter = 0;
	private static boolean stableGridPattern = false;
	
	/**
	 * Constructeur. {@code GenerationTracker} dépend de la liste de cellules {@code activeCellList}
	 * de la classe {@code Board}.
	 */
	public GenerationTracker(Board board) {
		activeCellList = board.getCellList();
		previousLivingCellCountList = new long[MAX_OSCILLATOR_PERIOD];
	}
	
	/**
	 * @return Nombre de cellules vivantes dans la génération actuelle
	 */
	public long countLivingCells() {
		return activeCellList.stream()
				.filter(Cell::isAlive)
				.count();
	}
	
	/**
	 * Cette methode contruit et actualise un array qui contient le nombre de cellules vivantes des
	 * generations precedentes. Les valeurs dans ce array representent l'intervalle sur laquelle
	 * la stabilité de la grille sera evaluée. Cette intervalle est determinée par {@code MAX_OSCILLATOR_PERIOD}.
	 * @param currentLivingCellCount
	 * @return Un array contenant le nombre de cellules vivantes des generation precedentes
	 */
	private long[] getPreviousCellCount(long currentLivingCellCount) {
		
		// Ce array est construit comme etant une file d'elements (queue)
		// chaque index prend la valeur de l'index precedant lors d'une nouvelle generation
		for (int i = previousLivingCellCountList.length - 1 ; i > 0 ; i--) {
			previousLivingCellCountList[i] = previousLivingCellCountList[i - 1];
			previousLivingCellCountList[0] = currentLivingCellCount;
		}
		
		return previousLivingCellCountList;
	}
	
	/**
	 * Methode pour determiner si la progression de la grille a atteint sa fin.
	 * La progression de la grille peut etre mesurée par la variation de nombre de cellules vivantes
	 * a chaque generation. On considère que la progression stagne lorsque la variation devient nulle 
	 * ou répétitive pendant plusieurs generations consécutives.
	 */

	private void evaluateGridPattern(long currentLivingCellCount) {
		for (long previousCount : getPreviousCellCount(currentLivingCellCount)) {
			stableGridPattern = previousCount == currentLivingCellCount;
		}
		
		// S'il n'y a pas de variation stable consecutive, le compteur est reinitialisé
		consecutivePatternCounter = stableGridPattern ? consecutivePatternCounter + 1 : 0;
	}
	
	/**
	 * Cette méthode garde le compte des generations et analyse
	 * l'etat de stabilité de la grille a chaque evolution.
	 */
	public void trackInfo() {
		long livingCells = countLivingCells();
		evaluateGridPattern(livingCells);
		generationCounter++;	
	}
	
	/**
	 * On considère que la progression devient stable lorsque la variation de la 
	 * grille est nulle ou repetitive pendant au moins 100 generations consecutives.
	 * @return {@code true} si le programme detecte que l'evolution de la grille est finie
	 */
	public boolean hasReachedStability() {
		return consecutivePatternCounter == MIN_STABLE_GENERATIONS;
	}
	
	/**
	 * Cette methode determine la generation a laquelle la grille a commencé a stagner.
	 * Un JOptionPane est affiché pour indiquer le point de stabilité a été atteint.
	 */
	public void showStabilityGeneration() {
		// Vu que MIN_STABLE_GENERATIONS sert juste à tester qu'il existe bien une stabilité constante,
		// le vrai point de stabilité est la génération dans laquelle le pattern a commencé.
		int stabilityGeneration = generationCounter - MIN_STABLE_GENERATIONS;
		JOptionPane.showMessageDialog(null, 
				"La grille stagne à partir de la " + stabilityGeneration + "ème génération",
				"POINT DE STABILITÉ", 1);
	}
	
	/**
	 * @return Nombre de générations depuis le début du programme
	 */
	public int getGenerationCounter() {
		return generationCounter;
	}
}
