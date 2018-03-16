import java.awt.Graphics;

public abstract class GameObj {
	
	private int px;
	private int py;
	protected boolean isDestroyable;
	
	public GameObj(int x, int y) {
		this.px = x;
		this.py = y;
		this.isDestroyable = false;
	}
	
	public int getPx() {
		return px;
	}

	public int getPy() {
		return py;
	}

	public void setPx(int px) {
		this.px = px;
	}

	public void setPy(int py) {
		this.py = py;
	}
	
	public abstract void draw(Graphics g);
	
	public abstract void onImpact();
	
	public abstract int destroy();

	public boolean isDestroyable() {
		// TODO Auto-generated method stub
		return this.isDestroyable;
	}
}