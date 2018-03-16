
public enum Direction {
	LEFT, RIGHT, UP, DOWN;
	
	public static Direction CCW (Direction d) {
		switch (d) {
		case LEFT:
			return DOWN;
		case UP:
			return LEFT;
		case RIGHT:
			return UP;
		case DOWN:
			return RIGHT;
		}
		return d;
	}
	
	public static Direction CW (Direction d) {
		switch (d) {
		case LEFT:
			return UP;
		case UP:
			return RIGHT;
		case RIGHT:
			return DOWN;
		case DOWN:
			return LEFT;
		}
		return d;
	}
}

