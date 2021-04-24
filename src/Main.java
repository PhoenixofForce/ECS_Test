import game.Game;
import game.Window;

public class Main {

	public static void main(String[] args) {
		Window w = new Window();
		Game g = new Game(w);

		new Thread(g::loop).start();
	}

}
