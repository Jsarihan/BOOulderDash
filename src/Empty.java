import java.awt.Graphics;

public class Empty extends GameObj {

	public Empty (int x, int y) {
		super(x,y);
		this.isDestroyable = true;
	}

	public void draw(Graphics g) {
		g.drawImage(GameCourt.SPRITE_SHEET.sprites[30], this.getPx() * GameCourt.getBlockSize(), this.getPy() * GameCourt.getBlockSize(), null);
	}

	public int destroy() {
		return 0;
	}

	@Override
	public void onImpact() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "e";
	}
	
	@Override
	public boolean equals(Object that) {
		return true;
	}
}
