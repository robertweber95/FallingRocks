package ascii;

import java.util.Timer;
import java.util.concurrent.*;

import Model.Person;
import Model.fallRock;
import static java.lang.Math.*;

/**
 * Code for this class currently runs a local main() for testing (shows a ball
 * moving across the screen) or operates with ASCIIGameTemplate main() to allow
 * keyboard input (see allowed chars below) to move the ball right.
 **/

public class ASCIIScreen {

	private static StringBuilder[] screen;
	static StringBuilder line;
	static StringBuilder blank;
	private final static int WIDTH = 80;
	private final static int HEIGHT = 24;

	static Timer timer = new Timer();
	int level = 1;
	public static Person person;
	
	private static boolean isTerminated = true;

	/*******************************************************************
	 * Constructor - initializes screen and line
	 *******************************************************************/
	ASCIIScreen() {

		setScreen(new StringBuilder[getHeight()]);

		blank = new StringBuilder("");
		for (int i = 0; i < getWidth(); i++)
			blank.insert(1, ' ');

		for (int i = 0; i < getHeight(); i++)
			getScreen()[i] = new StringBuilder(blank); // WHY?

		line = new StringBuilder("");
		for (int i = 0; i < getWidth() / 2; i++) {
			line.append('\\');
			line.append('/');
			getScreen()[0] = line;
		}

	}

	/*******************************************************************
	 * Print the current state.
	 *******************************************************************/
	void printScreen() {

		for (int j = 0; j < getHeight(); j++)
			System.out.println(getScreen()[j]);
		System.out.println(line);
	}

	/***************************************************
	 * Getter for person object
	 ***************************************************/

	public static Person getPerson() {
		return person;
	}

	/********************************************************************
	 * Initialize game pieces.
	 ********************************************************************/

	void init() {
		isTerminated = false;
		person = new Person(40);
		person.updatePosition();

		// Create timer to make rocks start to fall
		timer.scheduleAtFixedRate(new fallRock(), System.currentTimeMillis(),
				(long) (400.0 / level));
	}

	/********************************************************************
	 * Have game respond to a single character input.
	 ********************************************************************/
	void processChar(int i) {
		switch (i) {
		case 'l':
			person.moveLeft();
		case 'j':
			person.moveRight();
		case 'q':
			if (isTerminated == true){
				init();
			}
		}

	}

	/********************************************************************
	 * Terminate Game
	 ********************************************************************/

	public static void terminate() {
		isTerminated = true;
		timer.cancel();

		// Change string builder to show just
		// GAME OVER
		// SCORE: *score here*
		// press q to play again
	}

	/********************************************************************
	 * For testing purposes only.
	 ********************************************************************/
	public static void main(String[] a) {

		ASCIIScreen game = new ASCIIScreen();

		try {
			game.init();

			game.printScreen();
			TimeUnit.MILLISECONDS.sleep(100);

			while (isTerminated == false) {

				person.updatePosition();
				
				//Print the updated screen, then wait 100 milliseconds
				game.printScreen();
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Getters and setters for private static variables

	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}

	public static StringBuilder[] getScreen() {
		return screen;
	}

	public static void setScreen(StringBuilder[] screen) {
		ASCIIScreen.screen = screen;
	}
}