package game.entities;

import game.Game;
import game.entities.components.LinkPositionComponent;
import game.entities.components.PositionComponent;
import game.entities.components.graphics.ArcGraphicsComponent;
import util.Vec2D;

import java.awt.*;

public class MeleeSpecial extends Entity{

	private static double NEGATIVE_PEAK = -20;
	private static double MAX_ROT = 180.0;
	private static double INITIAL_ANGLE = 10;
	private static double MAX_ANGLE = 180.0;
	private static double LEFT_PEAK = 0.7, RIGHT_PEAK = 0.8;

	private int duration;
	private int ticks;

	private double startingAngle;
	private double radius;

	public MeleeSpecial(Entity attacker, Vec2D dir, int duration, double radius) {
		super(11);

		this.startingAngle = dir.degAngle();
		this.radius = radius;
		this.duration = duration;
		this.ticks = 0;

		PositionComponent pc = attacker.getComponent(PositionComponent.class);
		this.addComponent(new LinkPositionComponent(this, attacker, radius * 2, radius * 2, -radius + pc.getSize().x / 2.0, -radius + pc.getSize().y / 2.0));
		this.addComponent(new ArcGraphicsComponent(this, Color.PINK));
	}

	@Override
	public void update(Game game) {
		super.update(game);

		int t = ticks;

		this.getComponent(ArcGraphicsComponent.class).setAngle(calcAngle(t));
		double inital = startingAngle + calcStartingAnble(t);

		System.out.println(t + " | " + calcAngle(t) + " | " + calcStartingAnble(t));

		this.getComponent(ArcGraphicsComponent.class).setStartingAngle(inital);

		if(t >= duration) game.removeEntity(this);
		ticks++;
	}

	private double calcAngle(int t) {
		if(t < LEFT_PEAK * duration) {
			return (MAX_ANGLE - INITIAL_ANGLE) / (LEFT_PEAK * duration) * t + INITIAL_ANGLE;
		} else if(t > RIGHT_PEAK * duration) {
			return -(MAX_ANGLE - INITIAL_ANGLE) / ((1- RIGHT_PEAK) * duration) * t + (MAX_ANGLE - INITIAL_ANGLE) / (1- RIGHT_PEAK) + INITIAL_ANGLE;
		} else {
			return MAX_ANGLE;
		}
	}

	private double calcStartingAnble(int t) {
		double duration = RIGHT_PEAK * this.duration;
		if(t < duration) {
			double peak = (1 - RIGHT_PEAK);
			double a = NEGATIVE_PEAK - peak * MAX_ROT;
			a = a / ((peak * peak - peak) * duration * duration);

			double b = MAX_ROT - duration * duration * a;
			b /= duration;

			return t * t * a + b * t;
		} else {
			double maxOff = 30;
			double xOff =  (this.duration - duration)/2.0;

			double a = maxOff / (xOff * xOff);

			double offset = -a * Math.pow(((double)t - duration) - xOff, 2) + maxOff;
			return MAX_ROT + (MAX_ANGLE - calcAngle(t)) + offset;
		}
	}
}
