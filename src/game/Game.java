package game;

import game.entities.*;
import util.Constants;
import util.TimeUtil;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Game {

	private Window window;
	private BufferedImage image;
	private Graphics g;

	private List<Entity> entities;
	private Queue<Entity> toAdd, toRemove;

	public Game(Window window) {
		this.window = window;

		this.entities = new ArrayList<>();
		this.toAdd = new ConcurrentLinkedQueue<>();
		this.toRemove = new ConcurrentLinkedQueue<>();

		this.addEntity(new Player(this));
		this.addEntity(new UI((Player) toAdd.peek()));
		this.addEntity(new Enemy());
		this.addEntity(new MovingPlatform());
		this.addEntity(new Pit(this));
		this.addEntity(new Wall( 0, 0, 800, 16));
		this.addEntity(new Wall( 0, 640-16, 800, 16));
		this.addEntity(new Wall( 0, 0, 16, 640));
		this.addEntity(new Wall( 800-16, 0, 16, 640));


		this.image = new BufferedImage(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		this.g = image.getGraphics();
	}

	public void loop() {
		long time;

		while (window.isRunning()) {
			time = TimeUtil.getTime();

			getGraphics().setColor(Color.WHITE);
			getGraphics().fillRect(0, 0, image.getWidth(), image.getHeight());

			addAllEntities();
			removeAllEntities();

			for(int i = 0; i < entities.size(); i++) entities.get(i).update(this);
			for(int i = entities.size() - 1; i >= 0; i--) entities.get(i).draw(this);

			window.getGraphics().drawImage(image, 0, 0, null);

			long newTime =  TimeUtil.getTime();
			TimeUtil.sleep((int) (1000.0f / Constants.TPS - (newTime - time)));
		}
	}

	public void addEntity(Entity e) {
		toAdd.add(e);
	}

	private void addAllEntities() {
		while(!toAdd.isEmpty()) {
			Entity e = toAdd.poll();
			entities.add(e);
		}

		entities.sort((e1, e2)->Float.compare(e1.getPriority(), e2.getPriority()));
	}

	public void removeEntity(Entity e) {
		toRemove.add(e);
	}

	private void removeAllEntities() {
		while(!toRemove.isEmpty()) {
			Entity e = toRemove.poll();
			e.onRemove();
			entities.remove(e);
		}
	}

	public Window getWindow() {
		return window;
	}

	public Graphics getGraphics() {
		return g;
	}

	public List<Entity> getEntities() {
		return entities;
	}
}
