package battleship.controller;

import java.util.Scanner;

import javax.swing.SwingUtilities;
import javax.swing.text.View;

import battleship.view.ViewManager;

public class HumanPlayer extends Player {
	
	private Networking networking;
	private Thread messageListener;

	public HumanPlayer(ViewManager viewManager){
		super(viewManager);
		networking = new Networking();
	}
	
	/**
	 * Listens on the socket on a separate thread so that the GUI does not freeze if this takes longer than expected
	 *
	 */
	private class MessageListener extends Thread {
		@Override
		public void run() {
			Scanner scanner = networking.getScanner();
			while(networking.isConnected()) {
				String message = scanner.nextLine();
				SwingUtilities.invokeLater(new MessageDispatcher(message));
			}
		}
	}
	
	/**
	 * Notifies responsible classes with the correct messages
	 * EXAMPLE: If the MessageListener receives a win game message then it will notify gamestate
	 * 
	 */
	private class MessageDispatcher extends Thread{	
		String message;
		
		public MessageDispatcher(String message) {
			this.message = message;
		}
		
		@Override
		public void run() {
			//TODO: add if to check messages incoming
			
		}
	}
	
	private void listenForNewMessages() {
		
		messageListener = new MessageListener();
		messageListener.start();
	}
    
    public void disconnect() {
    	networking.cleanUp();
    }
    
    public boolean connectAsClient(String IP) {
    	networking.connect(false, IP);
    	if(networking.isConnected()) {
    		return true;
    	}
    	return false;
    }

    public Networking getNetworking(){
		return networking;
	}

    public void connectAsHost() {
    	networking.connect(true, "");
    }

    @Override
    public void placeShips() throws ShipPlacementException{

    }

    @Override
    public void guess() {

    }

    @Override
    public void sendMessage() {

    }
    
    public void cleanup() {
    	if(messageListener != null) {
        	messageListener.interrupt();
    	}
    }
    
}
