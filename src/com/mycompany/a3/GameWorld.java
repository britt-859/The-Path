package com.mycompany.a3;

import java.util.Observable;
import java.util.Random;

public class GameWorld extends Observable {
	private int WORLD_WIDTH;  //width of game world size
	private int WORLD_HEIGHT;  //height of game world size
	private Random rand;  //used to initialize game world objects
	private int lives;  //How many lives the player has
	private int clock;  //counts up
	private int flagNumber;  //number of flags being initialized 
	private GameObjectCollection theWorld;  //collection of all the game objects in the world
	private boolean sound;  //boolean for if sound is on or not
	private Sound foodStationCollision;
	private Sound spiderCollision;
	private Sound flagCollision;
	private BGSound background;
	private boolean paused;  //if game is paused
	
	/*
	 * Constructor for GameWorld.
	 * Initialized player with 3 lives, clock at 0
	 * sound on and game unpaused.
	 */
	public GameWorld() {
		lives = 3;  //player has 3 lives when the game starts
		clock = 0;
		sound = true;
		rand = new Random();
		theWorld = new GameObjectCollection();
		paused = false;
	}

	/*
	 * Create and initialize in game sounds
	 */
	public void createSounds() {
		foodStationCollision = new Sound("eat.wav");  //ant collides with food station
		spiderCollision = new Sound("antHit.wav");  //ant collides with spider
		flagCollision = new Sound("flag.wav");  //ant collides with flag
		background = new BGSound("bgSound.wav");  //looped background sound
		playBackground();
	}
	
	/*
	 * Returns if the game is paused.
	 * @return boolean of paused game
	 */
	public boolean isPaused() {
		return paused;
	}
	
	/*
	 * loop background sound
	 */
	public void playBackground() {
		if (sound) {
			background.run();
		}
	}
	
	/*
	 * Returns how many lives the ant has left.
	 * @return an int for the number of lives.
	 */
	public int getLives() {
		return lives;
	}
	
	/*
	 * Returns how many ticks the clock has ticked.
	 * @return an int for the number of ticks.
	 */
	public int getClock() {
		return clock;
	}
	
	/*
	 * Returns whether the sound is on or not
	 * @return a boolean of true if sound is on nd
	 * false if sound is off
	 */
	public boolean getSound() {
		return sound;
	}
	
	/*
	 * Returns the food level of the ant.
	 * @return an int for the food level.
	 */
	public int getFoodLevel() {
		IIterator itr = theWorld.getIterator();
		Ant ant = (Ant)itr.getNext();
		return ant.getFoodLevel();
	}
	
	/*
	 * Returns the health level of the ant.
	 * @return an int for the health level.
	 */
	public int getHealthLevel() {
		IIterator itr = theWorld.getIterator();
		Ant ant = (Ant)itr.getNext();
		return ant.getHealth();
	}
	
	/*
	 * Calls notifyObservers()
	 */
	private void changeOccured() {
		setChanged();
		notifyObservers();
	}
	
	/*
	 * Check the ant's last flag number to see if 
	 * player has reached last flag.
	 */
	private void checkFlag() {
		IIterator itr = theWorld.getIterator();
		Ant ant = (Ant)itr.getNext();
		if (ant.getFlag() == flagNumber) {
			System.out.println("Game Over, you win! Total time: " + clock);
			exit();
		}
	}
	
	/*
	 * Checks ant's speed and food level.
	 * If ant's health or food level is 0, lose a life.
	 */
	private void checkAnt() {
		IIterator itr = theWorld.getIterator();
		Ant ant = (Ant)itr.getNext();
		if (ant.getHealth() <= 0 || ant.getFoodLevel() <= 0) {
			lives--;  //decrease a life
			changeOccured();
			if (lives == 0) {  //game over
				System.out.println("Game Over, you failed!");
				exit();
			} else {
				System.out.println("You lost a life.");
				System.out.println();
				init(WORLD_HEIGHT, WORLD_WIDTH);  //Reinitialize the game 
			}
		}
	}
	
	/*
	 * Return the Game Object collection.
	 * @return GameObjectCollection
	 */
	public GameObjectCollection getCollection() {
		return theWorld;
	}
	
	/*
	 * Initializes/reinitializes game objects.
	 */
	public void init(int worldHeight, int worldWidth) {
		WORLD_HEIGHT = worldHeight;  //max height of world
		WORLD_WIDTH = worldWidth;  //max width of world
		flagNumber = 4;  //tells GameWorld how many flags there will be
		theWorld.clearCollection();  //reset collection
		Ant ant = Ant.getAnt(); 
		//reset ant if it loses a life
		if (lives < 3) {
			ant.resetAnt();
			theWorld.add(ant);
		} else {
			theWorld.add(ant);
		}
		theWorld.add(new Flag(1, 50, 50, this));
		theWorld.add(new Flag(2, WORLD_WIDTH / 2 - 100, WORLD_HEIGHT - 200, this));
		theWorld.add(new Flag(3, WORLD_WIDTH - 200, WORLD_HEIGHT / 2 - 100, this));
		theWorld.add(new Flag(4, 500, 200, this));
		theWorld.add(new FoodStation(rand.nextInt(WORLD_WIDTH + 1), rand.nextInt(WORLD_HEIGHT + 1), this));
		theWorld.add(new FoodStation(rand.nextInt(WORLD_WIDTH + 1), rand.nextInt(WORLD_HEIGHT + 1), this));
		theWorld.add(new FoodStation(rand.nextInt(WORLD_WIDTH + 1), rand.nextInt(WORLD_HEIGHT + 1), this));
		theWorld.add(new FoodStation(rand.nextInt(WORLD_WIDTH + 1), rand.nextInt(WORLD_HEIGHT + 1), this));
		theWorld.add(new Spider(rand.nextInt(360), rand.nextInt(6) + 5, rand.nextInt(WORLD_WIDTH + 1), 
				rand.nextInt(WORLD_HEIGHT + 1), WORLD_HEIGHT, WORLD_WIDTH, this));
		theWorld.add(new Spider(rand.nextInt(360), rand.nextInt(6) + 5, rand.nextInt(WORLD_WIDTH + 1), 
				rand.nextInt(WORLD_HEIGHT + 1), WORLD_HEIGHT, WORLD_WIDTH, this));
		theWorld.add(new Spider(rand.nextInt(360), rand.nextInt(6) + 5, rand.nextInt(WORLD_WIDTH + 1), 
				rand.nextInt(WORLD_HEIGHT + 1), WORLD_HEIGHT, WORLD_WIDTH, this));
		changeOccured();
	}
	
	/*
	 * Increase the speed of the ant.
	 */
	public void accelerate() {
		IIterator itr = theWorld.getIterator();
		Ant ant = (Ant)itr.getNext();  //ant is at first element
		if (ant.getSpeed() < ant.getMaxSpeed() && ant.getHealth() == 10) {
			ant.increaseSpeed();  //only increase if ant is not at maxSpeed and has perfect health.
			changeOccured();
			System.out.println("Ant accelerated");
		}
	}
	
	/*
	 * Decrease speed of the ant. 
	 */
	public void brake() {
		IIterator itr = theWorld.getIterator();
		Ant ant = (Ant)itr.getNext();
		ant.decreaseSpeed();
		changeOccured();
		System.out.println("brake applied");
		checkAnt();  //minimum ant speed is 0, game ends if speed is 0
	}
	
	/*
	 * Change heading of the ant 5 degrees to the left
	 */
	public void left() {
		IIterator itr = theWorld.getIterator();
		Ant ant = (Ant)itr.getNext();
		ant.changeHeading(-5);
		changeOccured();
		System.out.println("Ant turned left 5 degrees");
	}
	
	/*
	 * Change heading of the ant 5 degrees to the right
	 */
	public void right() {
		IIterator itr = theWorld.getIterator();
		Ant ant = (Ant)itr.getNext();
		ant.changeHeading(5);
		changeOccured();
		System.out.println("Ant turned right 5 degrees");
	}
	
	/*
	 * Ant collides with food station.
	 */
	public void food(FoodStation food) {
		int cap = 0;  //food capacity
		IIterator itr = theWorld.getIterator();
		Ant ant = (Ant)itr.getNext();
		if (food.getCapacity() != 0) {  //non-empty food station
			cap = food.getCapacity();
			if (sound)
				foodStationCollision.play();
			food.collision();  //change color to yellow
			ant.increaseFoodLevel(cap);  //increase the ant's food level by the food capacity
			changeOccured();
			System.out.println("Ant collided with food station");
		}		
	}
	
	/*
	 * Spider collides with ant.
	 */
	public void gotten(Spider o) {
		IIterator itr = theWorld.getIterator();
		Ant ant = (Ant)itr.getNext();
		ant.collision();  //Changes ant's state regarding collision. 
		if (sound)
			spiderCollision.play();
		theWorld.remove(o);  //remove the spider 
		theWorld.add(new Spider(rand.nextInt(360), rand.nextInt(6) + 5, 
				rand.nextInt(WORLD_WIDTH + 1), rand.nextInt(WORLD_HEIGHT + 1), 
				WORLD_HEIGHT, WORLD_WIDTH, this));  //add new spider to the game
		changeOccured();
		System.out.println("Ant collided with spider");
		checkAnt();  //check ant's speed to see if it is 0
	}
	
	/*
	 * Game clock has ticked.
	 */
	public void tick() {
		clock++;
		IIterator itr = theWorld.getIterator();
		while (itr.hasNext()){
			Object o = itr.getNext();
			if (o instanceof Movable) {
				Movable movObj = (Movable)o;
				movObj.move(20);  //all movable objects move according to their move function
				changeOccured();
			}
			if (o instanceof Ant) {
				Ant ant = (Ant)o;
				ant.decreaseFoodLevel();  //decrease ant food level by consumption rate
				changeOccured();
				checkAnt();  //check ant's food level 
			}
		}
		//Collision handling
		IIterator itrCollide = theWorld.getIterator();
		while (itrCollide.hasNext()) {
			ICollider curObj = (ICollider)itrCollide.getNext();
			IIterator itr2 = theWorld.getIterator();
			while(itr2.hasNext()) {
				ICollider otherObj = (ICollider)itr2.getNext();
				if (otherObj!=curObj) {
					if (curObj.collidesWith((GameObject) otherObj)) {
						curObj.handleCollision((GameObject) otherObj, this);
					}
				}
			}
		}
		System.out.println("Clock ticked");
	}
	
	/*
	 * Exit the game.
	 */
	public void exit() {
		System.exit(0);
	}
	
	/*
	 * Toggle sound on or off
	 */
	public void toggleSound() {
		sound = !sound;
		if (sound) {
			turnOnSounds();
		} else {
			turnOffSounds();
		}
		changeOccured();
		System.out.println("Sound toggled");
	}
	
	/*
	 * Game is paused
	 * @boolean p to set paused value
	 */
	public void setPaused(boolean p) {
		paused = p;
		turnOnSounds();
	}
	
	/*
	 * Turn the sounds back on
	 */
	public void turnOnSounds() {
		if (sound && !paused)
			background.play();
	}
	
	/*
	 * Turn the sounds off
	 */
	public void turnOffSounds() {
		background.pause();
	}
	
	/*
	 * Displays information about the game objects.
	 */
	public void map() {
		IIterator itr = theWorld.getIterator();
		System.out.println();
		while (itr.hasNext()){
			System.out.println(itr.getNext().toString());
		}
		System.out.println();
	}
	
	/*
	 * Ant walks over flag numbered n.
	 * @param n is an integer representing the flag walked over.
	 */
	public void collide(int n) {
		IIterator itr = theWorld.getIterator();
		Ant ant = (Ant)itr.getNext();
		//flag walked over is exactly one greater than last reached flag
		if (n == ant.getFlag() + 1) {
			if (sound)
				flagCollision.play();
			ant.setFlag(n);
			changeOccured();
			System.out.println("Ant walks over flag");
			checkFlag();  //check to see if ant has reached last flag
		}
	}
	
	/*
	 * Returns the number of the last flag reached.
	 * @return an int for the last flag.
	 */
	public int getLastFlagReached() {
		IIterator itr = theWorld.getIterator();
		Ant ant = (Ant)itr.getNext();
		return ant.getFlag();
	}
}