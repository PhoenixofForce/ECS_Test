package game.entities;

import game.Game;
import game.entities.components.Component;
import game.entities.components.graphics.GraphicsComponent;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Entity {

	public List<String> tags;

	private float priority;
	private List<Component> components;
	public Entity() {
		this(0f);
	}

	public Entity(float prio) {
		this.components = new ArrayList<>();
		this.priority = prio;

		tags = new ArrayList<>();
	}

	public void update(Game game) {
		for(int i = 0; i < components.size(); i++) {
			if(!(components.get(i) instanceof GraphicsComponent) && components.get(i).isActivated()) components.get(i).update(game);
			//else if(!components.get(i).isActivated()) System.out.println(components.get(i).getClass().toString());
		}
	}

	public void draw(Game game) {
		for(int i = components.size() - 1; i >= 0; i--) {
			if(components.get(i) instanceof GraphicsComponent && components.get(i).isActivated()) components.get(i).update(game);
		}
	}

	public void onRemove() {
		for(Component c: components) c.onRemove();
	}

	public boolean addComponent(Component c) {
		if(hasComponent(c.getClass())) return false;

		components.add(c);
		components.sort((o1, o2) -> Float.compare(o1.getPriority(), o2.getPriority()));
		return true;
	}

	public boolean removeComponent(Component c) {
		if(!hasComponent(c.getClass())) return false;
		c.onRemove();

		return components.remove(c);
	}

	public boolean hasComponent(Class<? extends Component> t) {
		return components.stream().anyMatch(c -> c.isActivated() && t.isInstance(c));
	}

	public <T extends Component> T getComponent(Class<T> t) {
		for(Component c: components) {
			if (t.isInstance(c)) return (T) c;
		}

		return null;
	}

	public <T extends Component> List<T> getComponents(Class<T> t) {
		return (List<T>) components.stream().filter(c -> t.isInstance(c)).collect(Collectors.toList());
	}

	public float getPriority() {
		return priority;
	}

	public void addTag(String tag) {
		if(!hasTag(tag)) tags.add(tag.toLowerCase());
	}

	public boolean hasTag(String tag) {
		return tags.contains(tag.toLowerCase());
	}

	public boolean removeTag(String tag) {
		return tags.remove(tag.toLowerCase());
	}
}
