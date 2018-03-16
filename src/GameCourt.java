import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	//We will use a 2d array of integers to draw 
	//0 = wall, 1= player, 2 = dirt, 3 = boulder, 4 = bat, 5 = wallhugger, 6 = green growth
	
	private long timePlayed;
	private long timeRemaining;
	
	public boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."
    private JLabel time;
    private JLabel score;
    private Board board;
    private Player player;
    private int currLevel;
    public static SpriteLoader SPRITE_SHEET;
    
    // Game constants
    private static int blockSize = 64;
    public static final int COURT_WIDTH = getBlockSize() * 40;
    public static final int COURT_HEIGHT = getBlockSize() * 22;
    public static final int W_BOUNDS = COURT_WIDTH - getBlockSize();
    public static final int H_BOUNDS = COURT_HEIGHT- getBlockSize();

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 50;
    public static final int LEVEL_COUNT = 12;
	
	public GameCourt(JLabel status, JLabel time, JLabel score, boolean isHD) {
		// creates border around the court area, JComponent method
		if (isHD) {
			GameCourt.setBlockSize(128);
		} else {
			GameCourt.setBlockSize(64);
		}
		
		
		SPRITE_SHEET = new SpriteLoader(isHD);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        
        // The timer is an object which triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!
        
        Timer boardTimer = new Timer(INTERVAL * 3, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boardTick();
            }
        });
        boardTimer.start();

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key is pressed, by
        // changing the square's velocity accordingly. (The tick method below actually moves the
        // square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                	player.setDir(Direction.LEFT);
                	if (player.facing() == Direction.LEFT) {
                		board.moveAttempt(player);
                	}
                	
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                	player.setDir(Direction.RIGHT);
                	if (player.facing() == Direction.RIGHT){
                		board.moveAttempt(player);
                	}
                	
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                	player.setDir(Direction.DOWN);
                	if (player.facing() == Direction.DOWN){
                		board.moveAttempt(player);
                	}
                	
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                	player.setDir(Direction.UP);
                	if (player.facing() == Direction.UP){
                		board.moveAttempt(player);
                	}
                	
                }
            }
        });

        this.time = time;
        this.status = status;
        this.timePlayed = 0;
        this.score = score;
        this.currLevel = 1;
        this.timeRemaining = 20 * 120;
    }
	
	public void gameOver() {
		JFrame errorFrame = new JFrame("You've Won!");
      	// prompt the user to enter their name
      	JOptionPane.showMessageDialog(errorFrame, "Game Over!", 
      			null, JOptionPane.INFORMATION_MESSAGE);
	}

	
	public void reset() {
		File lvl = getCurrLevel();
        try {
        	load(lvl);
        } catch (IOException e) {
        	System.out.println("Level not found");
        }
		
    }
	
	private void resetPlayer(Player p) {
		this.player = p;
		playing = true;
	    status.setText("Currently Playing: bOOulderDash     ");
	    score.setText("Points: 0     ");
	 // Make sure that this component has the keyboard focus
	    requestFocusInWindow();
	}
	
	
	/**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
            timePlayed += 1;
            timeRemaining -=1;
            //20 ticks per second given the interval
            time.setText("Seconds Remaining " + timeRemaining/20 + "    ");
            score.setText("Points " + player.getScore() + "     ");

            // check for the game end conditions
            
            repaint();
            // update the display
        }
    }
    
    void boardTick() {
    	if (playing) {
    		board.update();
    	}
    	if (!player.isAlive() || timeRemaining <= 0) {
    		reset();
    	}
    	if (board.isComplete()) {
    		loadNext();
    	}
    	if (currLevel == 8) {
    		gameOver();
    	}
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
    
    public void save (String filename) throws IOException {
    	LevelSaver s = new LevelSaver(board);
    	s.save(filename);
    }
    
    private File getNextLevel() {
    	currLevel++;
    	return new File("levels\\" + "level" + currLevel + ".txt");
    }
    
    private File getCurrLevel() {
    	return new File("levels\\" + "level" + currLevel + ".txt");
    }
    
    private void loadNext() {
    	try {
    	LevelLoader l = new LevelLoader();
    	char[][] charArr = l.load(getNextLevel());
    	this.board = l.createBoard(charArr);
    	resetPlayer(board.getPlayer());
    	} catch (IOException e) {
    		System.out.println("Level not found");
    	}
    }
    
    public void load (File file) throws IOException, NullPointerException {
    	LevelLoader l = new LevelLoader();
    	char[][] charArr = l.load(file);
    	this.board = l.createBoard(charArr);
    	resetPlayer(board.getPlayer());
    }


	public static int getBlockSize() {
		return blockSize;
	}


	public static void setBlockSize(int blockSize) {
		GameCourt.blockSize = blockSize;
	}
}