
public abstract class Enemy extends MovableObj {

	private int dmg;
	private boolean isAlive;
	
	public Enemy(int x, int y, int dmg) {
		super(x, y);
		this.dmg = dmg;
		this.isAlive = true;
		// TODO Auto-generated constructor stub
	}
	
	public abstract GameObj onDeath();
	
	public abstract void move(Board  b);
	
	public int getDmg() {
		return dmg;
	}
	
	public boolean isAlive() {
		return this.isAlive;
	}
	
	public void setDead() {
		this.isAlive = false;
	}
	
}
