import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

public class Board {

	private GameObj[][] board;
	private int width;
	private int height;
	private Set<Boulder> boulders;
	private Set<Enemy> enemies;
	private Player player;
	private int tickCount;
	private boolean isComplete;
	private static int minScore = 120;
	
	public Board(int width, int height, Player player) {
		this.width = width;
		this.height = height;
		this.player = player;
		this.board = new GameObj[width][height];
		boulders = new HashSet<Boulder>();
		enemies = new HashSet<Enemy>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j ++) {
				if (i == 0 || j == 0 || i == width - 1 || j == height -1) {
					board[i][j] = new Wall(i,j);
				} else if (i % 3 == 0 && j == 4) {
					Boulder b = new Boulder(i,j);
					boulders.add(b);
					board[i][j] = b;
				} else if (i % 5 ==0 && j == 6) {
					Firefly b = new Firefly(i,j,3);
					enemies.add(b);
					board[i][j] = b;
				} else if (i%6 ==0 && j == 9) {
					Bat b = new Bat(i,j,3);
					enemies.add(b);
					board[i][j] = b;
				} else {
					board[i][j] = new Dirt(i, j);
				}
			}
		}
		board[player.getPx()][player.getPy()] = player;
	}
	
	public Board (char[][] level) {
		this.boulders = new HashSet<Boulder>();
		this.enemies = new HashSet<Enemy>();
		this.width = level.length;
		this.height= level[0].length;
		this.board = new GameObj[width][height];
		for (int px = 0; px < width; px++) {
			for (int py = 0; py < level[px].length; py++) {
				switch (level[px][py]) {
				case 'd': 
					this.board[px][py] = new Diamond(px, py);
					break;
				case '.': 
					board[px][py] = new Dirt(px, py);
					break;
				case 'r': 
					Boulder R = new Boulder(px, py);
					board[px][py] = R;
					boulders.add(R);
					break;
				case 'P': 
					Player p = new Player(px, py);
					this.player = p;
					board[px][py] = p;
					break;
				case 'B': 
					Bat b = new Bat(px, py, 5);
					board[px][py] = b;
					enemies.add(b);
					break;
				case 'e': 
					board[px][py] = new Empty(px, py);
					break;
				case 'W':
					board[px][py] = new Wall(px, py);
					break;
				case 'w':
					board[px][py] = new InternalWall(px, py);
					break;
				case 'q':
					Firefly f = new Firefly(px, py, 5);
					board[px][py] = f;
					enemies.add(f);;
					break;
				case 'X':
					board[px][py] = new Exit(px, py);
					break;
				default:
					board[px][py] = new Empty(px, py);
				}
			}
		}
	}
	
	public GameObj[][] getBoard() {
		return board.clone();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	private boolean validBounds(int x, int y) {
		return (x > 0 && x < width - 1 && y > 0 && y < height - 1);
	}
	
	public GameObj objTypeSide(int x, int y, Direction d) {
		switch(d) {
		case LEFT:
			return board[x - 1][y];
		case RIGHT:
			return board[x + 1][y];
		case UP:
			return board[x][y - 1];
		case DOWN:
			return board[x][y + 1];
		default:
			return null;
		}
	}
	
	public boolean isComplete() {
		return this.isComplete;
	}
	
	public void complete() {
		this.isComplete = true;
	}
	
	
	public void moveAttempt(GameObj g) {
		int px = g.getPx();
		int py = g.getPy();
		if (validBounds(px, py) && g instanceof MovableObj) {
			((MovableObj) g).move(this);
		}
	}
	
	public void move(int x, int y, GameObj g, Direction d) {
		switch(d) {
			case LEFT:
				board[x - 1][y] = g;
				g.setPx(x - 1);
				board[x][y] = new Empty(x,y);
				break;
			case RIGHT:
				board[x + 1][y] = g;
				g.setPx(x + 1);
				board[x][y] = new Empty(x,y);
				break;
			case UP:
				board[x][y - 1] = g;
				g.setPy(y - 1);
				board[x][y] = new Empty(x,y);
				break;
			case DOWN:
				board[x][y + 1] = g;
				g.setPy(y + 1);
				board[x][y] = new Empty(x,y);
				break;
			default:
				//Nothing
			}
	}
	
	public boolean pushAttempt(int x, int y, GameObj g, GameObj tg, Direction d) {
		switch(d) {
		case LEFT:
			if (tg instanceof MovableObj &&
					board[x - 2][y] instanceof Empty) {
				move(x - 1, y, tg, d);
				move(x, y, g, d);
			}
			return true;
		case RIGHT:
			if (tg instanceof MovableObj &&
					board[x + 2][y] instanceof Empty) {
				move(x + 1, y, tg, d);
				move(x, y, g, d);	
			}
			return true;
		default:
			System.out.println("Push failed!");
			return false;
		}
	}
	
	public void update() {
		updateEnemies();
		updateBoulders();
	}
	
	private void updateEnemies() {
		Set<Enemy> toRemove = new HashSet<Enemy>();
		for (Enemy e : enemies) {
			if (e.isAlive()) {
				e.move(this);
			} else {
				toRemove.add(e);
				board[e.getPx()][e.getPy()] = e.onDeath();
			}
		}
		enemies.removeAll(toRemove);
	}
	
	private void updateBoulders() {
		for (Boulder b : boulders) {
			b.move(this);
		}
	}
	
	public boolean playerIsAlive() {
		return player.isAlive();
	}
	
	public int getMinScore() {
		return Board.minScore;
	}
	
	public void draw(Graphics g) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j ++) {
				board[i][j].draw(g);
			}
		}
	}
	
}
