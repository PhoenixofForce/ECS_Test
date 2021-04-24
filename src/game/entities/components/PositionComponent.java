package game.entities.components;

import util.Vec2D;
import game.Game;
import game.entities.Entity;

public class PositionComponent extends Component {

	private Vec2D pos;
	private Vec2D size;

	public PositionComponent(Entity e, double x, double y) {
		this(e, x, y, 16, 16);
	}

	public PositionComponent(Entity e, double x, double y, double r) {
		this(e, x, y, r, r);
	}


	public PositionComponent(Entity e, double x, double y, double w, double h) {
		super(e);
		this.pos = new Vec2D(x, y);
		this.size = new Vec2D(w, h);
	}

	public void setPos(Vec2D newPos) {
		this.pos = newPos;
	}

	public Vec2D getPos() {
		return pos;
	}

	public int getX(){
		return (int) Math.round(getPos().x);
	}

	public int getY(){
		return (int) Math.round(getPos().y);
	}

	public void add(Vec2D toAdd) {
		this.pos.add(toAdd);
	}

	public Vec2D getSize() {
		return size;
	}

	public int getW(){
		return (int) Math.round(size.x);
	}

	public int getH(){
		return (int) Math.round(size.y);
	}

	@Override
	public void update(Game game) {}
}
