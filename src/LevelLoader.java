import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LevelLoader {
	
	public LevelLoader() {}
	
	public char[][] load(File toLoad) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(toLoad));
		String allRows = "";
		String currLine = r.readLine();
		int width = 0; int height = 0;
		while (currLine != null) {
			allRows += (currLine);
			width = currLine.length();
			height++;
			System.out.println(currLine);
			currLine = r.readLine();
		}
		char[][] rtn = new char[width][height];
		for (int i = 0; i < allRows.length(); i++) {
			char c = allRows.charAt(i);
			int px = i % width;
			int py = i / width;
			rtn[px][py] = c;
		}
		r.close();
		return rtn;
	}
	
	public Board createBoard(char[][] charArr) {
		return new Board(charArr);
	}
}