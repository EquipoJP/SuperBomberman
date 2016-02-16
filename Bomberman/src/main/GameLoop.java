package main;

import javax.swing.JComponent;

import logic.Input;
import logic.StatesMachine;

public class GameLoop implements Runnable {
	
	boolean stoped;
	private Input input;
	
	public GameLoop(JComponent jc) {
		stoped = false;
		input = new Input(jc);
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
				e.printStackTrace();
			}
		}

	}

}
