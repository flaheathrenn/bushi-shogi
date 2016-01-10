import java.util.HashMap;
import java.util.Map;



public class BushiShogi {

	public static void main(String[] args) {
		for (int i = 1; i <= 24; i++) {
			for (int j = 1; j <= 24; j++) {
				GameState position = new GameState(i, j);
				if (position.losingPosition()) {
					database.put(position, new EnhancedPosition(Position.LOSING, null));
				}
			}
		}

		printDatabase();

		for (int count = 0; count < 12; count++) {
			for (int i = 1; i <= 24; i++) {
				for (int j = 1; j <= 24; j++) {
					GameState position = new GameState(i, j);
					for (Movement m : position.availableMoves()) {
						GameState nextPosition = position.move(m);
						if (database.containsKey(nextPosition)) {
							if (database.get(nextPosition).position == Position.LOSING) {
								database.put(position, new EnhancedPosition(Position.WINNING, m));
							}
						}
					}
				}
			}

			printDatabase();

			for (int i = 1; i <= 24; i++) {
				for (int j = 1; j <= 24; j++) {
					GameState position = new GameState(i, j);
					boolean canNotLose = false;
					for (Movement m : position.availableMoves()) {
						GameState nextPosition = position.move(m);
						if (database.containsKey(nextPosition) && database.get(nextPosition).position == Position.WINNING) {
						} else {
							canNotLose = true;
						}
					}
					if (!canNotLose) {
						database.put(position, new EnhancedPosition(Position.LOSING, null));
					}
				}
			}

			printDatabase();
		}
		
		printDatabaseVocally();
		
		for (int i = 1; i <= 24; i++) {
			for (int j = 1; j <= 24; j++) {
				GameState position = new GameState(i, j);
				if (!isUnloseableFrom(position)) {
					if (database.get(position) == null || database.get(position).position != Position.LOSING) {
						System.out.println(position + " is not unlosable from, but is not a known losing position");
					}
				}
			}
		}
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("-----------------------------------------------------------");
		
		for (int i = 1; i <= 24; i++) {
			printReplies(i);
		}
		
		for (int i = 1; i <= 24; i++) {
			for (int j = 1; j <= 24; j++) {
				GameState position = new GameState(i, j);
				dontLose(position);
			}
		}
		
	}

	public static Map<GameState, EnhancedPosition> database = new HashMap<GameState, EnhancedPosition>();

	public static void printDatabase() {
		System.out.println("Database size is " + database.size());
	}
	
	public static void printDatabaseVocally() {
		for (GameState gs : database.keySet()) {
			System.out.println(gs + " is a " + database.get(gs) + " position");
		}
		System.out.println("Database size is " + database.size());
	}
	
	public static void printReplies(int theirMove) {
		if (CubeState.stateWith(theirMove).checking()) {
			return; //illegal opening move by them
		}
		for (int i = 1; i <= 24; i++) {
			if (CubeState.stateWith(i).checking()) {
				continue; //illegal opening move by you
			}
			if (database.get(new GameState(theirMove, i)) != null && database.get(new GameState(theirMove, i)).position == Position.LOSING) {
				System.out.println("Reply to " + theirMove + " is " + i);
				return;
			}
		}
	}

	public static boolean isUnloseableFrom(GameState gs) {
		for (Movement m : gs.availableMoves()) {
			GameState nextPosition = gs.move(m);
			boolean unloseable = true;
			for (Movement theirM : nextPosition.availableMoves()) {
				if (database.get(nextPosition.move(theirM)) != null && database.get(nextPosition.move(theirM)).position == Position.LOSING) {
					unloseable = false;
					break;
				}
			}
			if (unloseable) {
				return true; //you can avoid losing by making the movement m
			}
		}
		return false; //tested all branches and none are safe
	}
	
	public static void dontLose(GameState gs) {
		if (gs.losingPosition()) {
			System.out.println(gs + " is an endgame state");
			return;
		}
		if (database.get(gs) != null && database.get(gs).position == Position.WINNING) {
			System.out.println("Win from position " + gs + " by making the move "
					+ database.get(gs).winningMove);
			return;
		}
		for (Movement m : gs.availableMoves()) {
			GameState nextPosition = gs.move(m);
			boolean unloseable = true;
			for (Movement theirM : nextPosition.availableMoves()) {
				if (database.get(nextPosition.move(theirM)) != null && database.get(nextPosition.move(theirM)).position == Position.LOSING) {
					unloseable = false;
					break;
				}
			}
			if (unloseable) {
				System.out.println("Avoid losing from position " + gs + " by making the move " + m);
				return; //you can avoid losing by making the movement m
			}
		}
		System.out.println("If you're in position " + gs + " you've lost :(");
		return; //tested all branches and none are safe
	}

	public enum Position {
		WINNING, LOSING, DRAWING, UNKNOWN;
	}
	
		public static class EnhancedPosition {
			public Position position;
			public Movement winningMove;
			
			public EnhancedPosition(Position position, Movement winningMove) {
				this.position = position;
				this.winningMove = winningMove;
			}
			
			public String toString() {
				if (this.position != Position.WINNING) {
					return this.position.name();
				} else {
					return "WINNING (via " + winningMove + ")";
				}
			}
		}

}
