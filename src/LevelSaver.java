import java.io.IOException;
import java.io.PrintWriter;

public class LevelSaver {
	
	private GameObj[][] board;
	
	public LevelSaver(Board b) {
		board = b.getBoard();
	}
		
	public void save(String filename) throws IOException {
		PrintWriter writer = new PrintWriter(filename + ".txt", "UTF-8");
	    for (int i = 0; i < board[i].length; i++) {
	    	String currLine = "";
	    	for (int j = 0; j < board.length; j++) {
		   		currLine += board[j][i].toString();
		   	}
		   	writer.println(currLine);
		   }
	    writer.close();
	}
}