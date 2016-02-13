package main;

import logic.Input;
import logic.StatesMachine;

public class GameLoop implements Runnable {
	
	boolean stoped;
	
	public GameLoop() {
		stoped = false;
	}

	@Override
	public void run() {
		Input input = new Input();
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
