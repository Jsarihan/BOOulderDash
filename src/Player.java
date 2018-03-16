import java.awt.Graphics;

public class Player extends MovableObj{

	private int points;
	private int health;
	private static final int BUFFER_MAX = 0;
	private int healthBuffer = BUFFER_MAX;
	private Direction forward;
	private int tickCount;
	
	public Player (int x, int y) {
		super(x,y);
		this.forward = Direction.UP;
		this.health = 10;
		this.points = 0;
	}

	public void draw(Graphics g) {
		g.drawImage(GameCourt.SPRITE_SHEET.sprites[0], this.getPx() * GameCourt.getBlockSize(), this.getPy() * GameCourt.getBlockSize(), null);
	}
	
	public void refreshBuffer() {
		healthBuffer = BUFFER_MAX;
	}
	
	public void takeHit(int dmg) {
		this.health -= dmg;
		System.out.println(this.health);
	}
	
	public void setDir(Direction d) {
		this.forward = d;
	}
	
	public Direction facing() {
		return forward;
	}
	
	public void move(Board b) {
		int x = this.getPx();
		int y = this.getPy();
		GameObj tg = b.objTypeSide(x, y, forward);
		if (tg instanceof Exit && getScore() > b.getMinScore()) {
			b.complete();
			b.move(x, y, this, forward);
		} else if (tg.isDestroyable()) {
			tickCount = 0;
			addScore(tg.destroy());
			refreshBuffer();
			b.move(x, y, this, forward);	
		} else if (tickCount > 5) {
			tickCount = 0;
			b.pushAttempt(x, y, this, tg, forward);
		} 
	}
	
	public boolean isAlive() {
		return (health > 0);
	}
	
	public int getScore() { 
		return this.points;
	}
	
	public void addScore(int gain) {
		points += gain;
	}

	@Override
	public int destroy() {
		this.health = 0;
		return -1;
	}

	@Override
	public void onImpact() {
		this.healthBuffer -=1;
		if (healthBuffer <= 0) {
			this.health -= 10;
		}
		System.out.println(this.health);
	}
	
	@Override
	public String toString() {
		return "P";
	}
}
