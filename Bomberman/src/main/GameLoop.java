package main;

import javax.swing.JPanel;

import logic.Input;
import logic.StatesMachine;

public class GameLoop implements Runnable {
	
	boolean stoped;
	private Input input;
	
	public GameLoop(JPanel panel) {
		stoped = false;
		input = new Input(panel);
	}

	@Override
	public void run() {
		StatesMachine sm = new StatesMachine(input);
		
		while(!stoped){
			sm.stateMachine();
			// 17 ms => 60 fps
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
