import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to represent the entire board state of the game.
 * @author flahr
 *
 */
public class GameState {
	
	public CubeState myState;
	public CubeState yourState;
	
	public GameState(int mine, int yours) {
		myState = CubeState.stateWith(mine);
		yourState = CubeState.stateWith(yours);
	}
	
	private GameState(CubeState my, CubeState yours) {
		myState = my;
		yourState = yours;
	}

	public GameState move(Movement m) {
		return new GameState(yourState, transition(myState, m));
	}
	
	public Set<Movement> availableMoves() {
		if (yourState.checking()) {
			if (myState.checking()) {
				return new HashSet<Movement>(Arrays.asList(Movement.FORCEDROLL));
			} else {
				return new HashSet<Movement>(Arrays.asList(Movement.ROTATE));
			}
		} else {
			return new HashSet<Movement>(Arrays.asList(Movement.ROTATE, Movement.FORWARD,
								Movement.LEFT, Movement.RIGHT));
		}
	}
	
	/**
	 * Evaluate if the game is over, indicating that the person whose turn it is has lost.
	 * The game is over if both die are showing a sword at the front and a samurai at the back.
	 * @return
	 */
	public boolean losingPosition() {
		return (myState.description.startsWith("|")
				&& myState.description.charAt(2) == 'O'
				&& yourState.description.startsWith("|")
				&& yourState.description.charAt(2) == 'O');
	}
	
	private static CubeState transition(CubeState state, Movement m) {
		switch (m) {
		case FORCEDROLL:
			int samuraiPosition = state.description.indexOf("O");
			switch (samuraiPosition) {
			case 0: //can't happen since pos 0 has to be sword for forced roll
				throw new RuntimeException();
			case 1:
				return transition(state, Movement.LEFT);
			case 2:
				return transition(state, Movement.FORWARD);
			case 3:
				return transition(state, Movement.RIGHT);
			default:
				throw new RuntimeException();
			}
		case ROTATE:
			return CubeState.stateWith(state.number + (state.number%4==1?3:-1));
		case FORWARD:
			switch (state.number) { 
			case 1: return CubeState.stateWith(5);
			case 2: return CubeState.stateWith(22);
			case 3: return CubeState.stateWith(15);
			case 4: return CubeState.stateWith(20);
			case 5: return CubeState.stateWith(9);
			case 6: return CubeState.stateWith(23);
			case 7: return CubeState.stateWith(3);
			case 8: return CubeState.stateWith(19);
			case 9: return CubeState.stateWith(13);
			case 10: return CubeState.stateWith(24);
			case 11: return CubeState.stateWith(7);
			case 12: return CubeState.stateWith(18);
			case 13: return CubeState.stateWith(1);
			case 14: return CubeState.stateWith(21);
			case 15: return CubeState.stateWith(11);
			case 16: return CubeState.stateWith(17);
			case 17: return CubeState.stateWith(6);
			case 18: return CubeState.stateWith(2);
			case 19: return CubeState.stateWith(14);
			case 20: return CubeState.stateWith(10);
			case 21: return CubeState.stateWith(8);
			case 22: return CubeState.stateWith(12);
			case 23: return CubeState.stateWith(16);
			case 24: return CubeState.stateWith(4);
			default: throw new RuntimeException();
			}
		case LEFT:
			switch (state.number) {
			case 1: return CubeState.stateWith(17);
			case 2: return CubeState.stateWith(6);
			case 3: return CubeState.stateWith(23);
			case 4: return CubeState.stateWith(16);
			case 5: return CubeState.stateWith(20);
			case 6: return CubeState.stateWith(10);
			case 7: return CubeState.stateWith(24);
			case 8: return CubeState.stateWith(4);
			case 9: return CubeState.stateWith(19);
			case 10: return CubeState.stateWith(14);
			case 11: return CubeState.stateWith(21);
			case 12: return CubeState.stateWith(8);
			case 13: return CubeState.stateWith(18);
			case 14: return CubeState.stateWith(2);
			case 15: return CubeState.stateWith(22);
			case 16: return CubeState.stateWith(12);
			case 17: return CubeState.stateWith(11);
			case 18: return CubeState.stateWith(7);
			case 19: return CubeState.stateWith(3);
			case 20: return CubeState.stateWith(15);
			case 21: return CubeState.stateWith(1);
			case 22: return CubeState.stateWith(5);
			case 23: return CubeState.stateWith(9);
			case 24: return CubeState.stateWith(13);
			default: throw new RuntimeException();
			}
		case RIGHT:
			switch (state.number) {
			case 17: return CubeState.stateWith(1);
			case 6: return CubeState.stateWith(2);
			case 23: return CubeState.stateWith(3);
			case 16: return CubeState.stateWith(4);
			case 20: return CubeState.stateWith(5);
			case 10: return CubeState.stateWith(6);
			case 24: return CubeState.stateWith(7);
			case 4: return CubeState.stateWith(8);
			case 19: return CubeState.stateWith(9);
			case 14: return CubeState.stateWith(10);
			case 21: return CubeState.stateWith(11);
			case 8: return CubeState.stateWith(12);
			case 18: return CubeState.stateWith(13);
			case 2: return CubeState.stateWith(14);
			case 22: return CubeState.stateWith(15);
			case 12: return CubeState.stateWith(16);
			case 11: return CubeState.stateWith(17);
			case 7: return CubeState.stateWith(18);
			case 3: return CubeState.stateWith(19);
			case 15: return CubeState.stateWith(20);
			case 1: return CubeState.stateWith(21);
			case 5: return CubeState.stateWith(22);
			case 9: return CubeState.stateWith(23);
			case 13: return CubeState.stateWith(24);
			default: throw new RuntimeException();
			}
		default:
			throw new RuntimeException();
		}
	}

	public String toString() {
		return "<" + myState.number + "," + yourState.number + ">";
	}
	
	@Override
	public int hashCode() {
		return this.myState.number * 25 + this.yourState.number;
	}
	
	public boolean equals(Object o) {
		if (o instanceof GameState) {
			GameState gs = (GameState) o;
			return (gs.myState.number == this.myState.number && gs.yourState.number == this.yourState.number);
		}
		return false;
	}
	
}
