import java.util.HashMap;
import java.util.Map;


public class CubeState {
	
	public int number;
	/**
	 * Four-character string representing the arrangement of symbols on the face.
	 * " " is a blank space, "O" is a samurai, "|" is a sword.
	 * Clockwise from the symbol facing the opponent's cube.
	 */
	public String description;
	
	private static Map<Integer, CubeState> database = new HashMap<Integer, CubeState>() {
		private static final long serialVersionUID = 1L;

		{
			put(1, new CubeState(1, "||O|"));
			put(2, new CubeState(2, "|O||"));
			put(3, new CubeState(3, "O|||"));
			put(4, new CubeState(4, "|||O"));
			put(5, new CubeState(5, "O | "));
			put(6, new CubeState(6, " | O"));
			put(7, new CubeState(7, "| O "));
			put(8, new CubeState(8, " O |"));
			put(9, new CubeState(9, "O  |"));
			put(10, new CubeState(10, "  |O"));
			put(11, new CubeState(11, " |O "));
			put(12, new CubeState(12, "|O  "));
			put(13, new CubeState(13, " | O"));
			put(14, new CubeState(14, "| O "));
			put(15, new CubeState(15, " O |"));
			put(16, new CubeState(16, "O | "));
			put(17, new CubeState(17, " O| "));
			put(18, new CubeState(18, "O|  "));
			put(19, new CubeState(19, "|  O"));
			put(20, new CubeState(20, "  O|"));
			put(21, new CubeState(21, "|O  "));
			put(22, new CubeState(22, "O  |"));
			put(23, new CubeState(23, "  |O"));
			put(24, new CubeState(24, " |O "));
		}
	};
	
	/**
	 * Gets a cube state by its reference number
	 * @param number
	 * @return
	 */
	public static CubeState stateWith(int number) {
		if (number < 0 || number > 24) {
			throw new RuntimeException();
		}
		CubeState result = database.get(number);
		if (result == null || result.description.length() != 4 || !result.description.contains("O")) {
			throw new RuntimeException();
		} else {
			return result;
		}
	}
	
	private CubeState(int number, String state) {
		this.number = number;
		this.description = state;
	}
	
	public void print() {
		System.out.println("State number " + number + " <" + description + ">");
	}
	
	public String toString() {
		return number + ": (" + description + ")";
	}
	
	/**
	 * @return true if a sword is facing the opponent's cube, false otherwise
	 */
	public boolean checking() {
		return this.description.startsWith("|");
	}

}
