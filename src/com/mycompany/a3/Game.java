package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;

public class Game extends Form implements Runnable {
	
	private GameWorld gw;  //GameWorld containing GameObjects
	private MapView mv;  //display information about the game objects
	private ScoreView sv;  //display game state information in gui
	private int time;  //time until next tick in milliseconds
	private boolean paused;  
	private UITimer timer;
	private Button pause;
	private Button position;
	private Button accelerate;
	private Button left;
	private Button right;
	private Button brake;
	private CheckBox myCheck;
	
	/*
	 * Constructs GameWorld and GUI
	 */
	public Game() {
		gw = new GameWorld(); 
		mv = new MapView(gw);
		sv = new ScoreView();
		gw.addObserver(mv);
		gw.addObserver(sv);
		time = 20; 
		paused = false;
		
		this.setLayout(new BorderLayout());  //Form will have border layout
		
		//Style ScoreView and MapView containers
		sv.getAllStyles().setMarginBottom(10);
		mv.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.rgb(255, 0, 0)));
		mv.getAllStyles().setBgTransparency(255);
		mv.getAllStyles().setBgColor(ColorUtil.WHITE);
		
		//Buttons
		pause = new Button();
		position = new Button();
		accelerate = new Button();
		left = new Button();
		brake = new Button();
		right = new Button();
		myCheck = new CheckBox();
		
		buttonStyles();
		
		//Command Objects
		PauseCommand myPauseCommand = new PauseCommand(this);
		PositionCommand myPositionCommand = new PositionCommand(mv);
		AccelerateCommand myAccelerate = new AccelerateCommand(gw);
		LeftCommand myLeftCommand = new LeftCommand(gw);
		BrakeCommand myBrakeCommand = new BrakeCommand(gw);
		RightCommand myRightCommand = new RightCommand(gw);
		ExitCommand myExitCommand = new ExitCommand(gw);
		SoundCommand mySoundCommand = new SoundCommand(gw);
		AboutCommand myAboutCommand = new AboutCommand();
		HelpCommand myHelpCommand = new HelpCommand();
		
		//ToolBar
		Toolbar myToolBar = new Toolbar();
		this.setToolbar(myToolBar);
		this.setTitle("ThePath Game");
		
		//Toolbar commands
		myToolBar.addCommandToRightBar(myHelpCommand);
		myToolBar.addCommandToSideMenu(myExitCommand);
		myToolBar.addCommandToSideMenu(myAboutCommand);
		
		accelerate.setCommand(myAccelerate);
		left.setCommand(myLeftCommand);
		brake.setCommand(myBrakeCommand);
		right.setCommand(myRightCommand);
		myCheck.setCommand(mySoundCommand);
		pause.setCommand(myPauseCommand);
		position.setCommand(myPositionCommand);
		
		myToolBar.addComponentToSideMenu(myCheck);
		
		//Key listeners
		addKeyListener('a', myAccelerate);
		addKeyListener('b', myBrakeCommand);
		addKeyListener('l', myLeftCommand);
		addKeyListener('r', myRightCommand);
		
		//Add buttons to containers
		Container bottomButtons = new Container();
		bottomButtons.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		bottomButtons.add(position);
		bottomButtons.add(pause);
		
		Container leftButtons = new Container();
		leftButtons.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		leftButtons.add(accelerate);
		leftButtons.add(left);
		leftButtons.getAllStyles().setMarginRight(10);
		
		Container rightButtons = new Container();
		rightButtons.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		rightButtons.add(brake);
		rightButtons.add(right);
		rightButtons.getAllStyles().setMarginLeft(20);
		
		Container bottomContainer = new Container();
		bottomContainer.setLayout(new FlowLayout(Component.CENTER));
		bottomContainer.add(bottomButtons);
		bottomContainer.getAllStyles().setMarginTop(10);
		
		//Add containers to border layout
		this.add(BorderLayout.NORTH, sv);
		this.add(BorderLayout.CENTER, mv);
		this.add(BorderLayout.SOUTH, bottomContainer);
		this.add(BorderLayout.WEST, leftButtons);
		this.add(BorderLayout.EAST, rightButtons);
		
		position.setEnabled(false);  //position button is disabled unless game is paused
		
		this.show();
		
		int worldHeight = mv.getHeight();
		int worldWidth = mv.getWidth();
		
		gw.init(worldHeight, worldWidth);  //set the dimensions of the world
		gw.createSounds();
		revalidate();  //show changes after initialization of the game world
		
		//Create a timer for animation
		timer = new UITimer(this);
		timer.schedule(time, true, this);
	}
	
	/*
	 * Style buttons with enabled and disabled styles.
	 */
	private void buttonStyles() {
		//Button styles
		pause.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
		pause.getAllStyles().setBgTransparency(255);
		pause.getAllStyles().setBgColor(ColorUtil.BLUE);
		pause.getAllStyles().setFgColor(ColorUtil.WHITE);
		pause.getDisabledStyle().setBgColor(ColorUtil.WHITE);
		pause.getDisabledStyle().setFgColor(ColorUtil.BLUE);
				
		position.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
		position.getAllStyles().setBgTransparency(255);
		position.getAllStyles().setBgColor(ColorUtil.BLUE);
		position.getAllStyles().setFgColor(ColorUtil.WHITE);
		position.getDisabledStyle().setBgColor(ColorUtil.WHITE);
		position.getDisabledStyle().setFgColor(ColorUtil.BLUE);
				
		accelerate.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
		accelerate.getAllStyles().setBgTransparency(255);
		accelerate.getAllStyles().setBgColor(ColorUtil.BLUE);
		accelerate.getAllStyles().setFgColor(ColorUtil.WHITE);
		accelerate.getDisabledStyle().setBgColor(ColorUtil.WHITE);
		accelerate.getDisabledStyle().setFgColor(ColorUtil.BLUE);
				
		left.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
		left.getAllStyles().setBgTransparency(255);
		left.getAllStyles().setBgColor(ColorUtil.BLUE);
		left.getAllStyles().setFgColor(ColorUtil.WHITE);
		left.getDisabledStyle().setBgColor(ColorUtil.WHITE);
		left.getDisabledStyle().setFgColor(ColorUtil.BLUE);
				
		brake.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
		brake.getAllStyles().setBgTransparency(255);
		brake.getAllStyles().setBgColor(ColorUtil.BLUE);
		brake.getAllStyles().setFgColor(ColorUtil.WHITE);
		brake.getDisabledStyle().setBgColor(ColorUtil.WHITE);
		brake.getDisabledStyle().setFgColor(ColorUtil.BLUE);
				
		right.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
		right.getAllStyles().setBgTransparency(255);
		right.getAllStyles().setBgColor(ColorUtil.BLUE);
		right.getAllStyles().setFgColor(ColorUtil.WHITE);
		right.getDisabledStyle().setBgColor(ColorUtil.WHITE);
		right.getDisabledStyle().setFgColor(ColorUtil.BLUE);
		
		myCheck.getAllStyles().setBgTransparency(255);
		myCheck.getAllStyles().setBgColor(ColorUtil.LTGRAY);
	}
	
	/*
	 * Activated when user clicks pause button.
	 */
	public void pause() {
		//If the game is playing, pause it
		if (!paused) {
			paused = !paused;
			timer.cancel();
			gw.turnOffSounds();
			gw.setPaused(true);
			pause.setText("play");
			position.setEnabled(true);
			disableCommands();
		//If the game is paused, play it
		} else {
			paused = !paused;
			timer.schedule(time, true, this);
			gw.turnOnSounds();
			gw.setPaused(false);
			pause.setText("pause");
			position.setEnabled(false);
			enableCommands();
		}
	}
	
	/*
	 * Set buttons to disabled if paused.
	 */
	private void disableCommands() {
		accelerate.setEnabled(false);
		left.setEnabled(false);
		right.setEnabled(false);
		brake.setEnabled(false);
	}
	
	/*
	 * Set buttons to enabled if paused
	 */
	private void enableCommands() {
		accelerate.setEnabled(true);
		left.setEnabled(true);
		right.setEnabled(true);
		brake.setEnabled(true);
	}

	/*
	 * Calls tick command for Game World clock
	 */
	@Override
	public void run() {
		gw.tick();  
	}
}