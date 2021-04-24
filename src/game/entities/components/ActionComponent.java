package game.entities.components;

import game.Game;
import game.actions.Action;
import game.entities.Entity;

import java.util.Arrays;

public class ActionComponent extends Component {

	private Action[] actions;

	public ActionComponent(Entity e, Action... actions) {
		super(e);
		this.actions = actions;
	}

	public Action getAction(int pos) {
		if(pos < actions.length) return actions[pos];
		return actions[0];
	}

	public boolean hasAction(Class<? extends Action> t) {
		return Arrays.stream(actions).anyMatch(t::isInstance);
	}

	public <T extends Action> T getAction(Class<T> t) {
		if(hasAction(t)) {
			for(Action a: actions) {
				if(t.isInstance(a)) return (T) a;
			}
		}

		return null;
	}

	@Override
	public void update(Game game) {
		for(Action a: actions) a.update(game);
	}
}
