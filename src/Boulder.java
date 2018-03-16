import java.awt.Graphics;

public class Boulder extends MovableObj {
	
	
	private int falling;
	public Boulder (int x, int y) {
		super(x,y);
	}

	public void draw(Graphics g) {
		g.drawImage(GameCourt.SPRITE_SHEET.sprites[29], this.getPx() * GameCourt.getBlockSize(), this.getPy() * GameCourt.getBlockSize(), null);
	}

	@Override
	public void onImpact() {
		// TODO Auto-generated method stub
	}
	
	public void move(Board b) {
		int x = this.getPx();
		int y = this.getPy();
		GameObj tg =  b.objTypeSide(x, y, Direction.DOWN);
		if (tg instanceof Empty) {
			falling++;
			b.move(x, y, this, Direction.DOWN);
		} else if (falling > 1) {
			tg.onImpact();
			falling = 0;
		}
	}
	

	@Override
	public int destroy() {
		// TODO Auto-generated method stub
		return -2;
	}

	@Override
	public String toString() {
		return "r";
	}
	
}
