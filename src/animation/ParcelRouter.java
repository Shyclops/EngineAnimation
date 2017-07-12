package animation;

import animation.server.SocketServer;

public class ParcelRouter {

	private Interpreter interpreter;
	private SocketServer server;
	
	public ParcelRouter() {
		interpreter = new Interpreter();
		server = new SocketServer();
		server.setInterpreter(interpreter);
	}
	
	public void play() {
		interpreter.start();
		server.start();
	}

	public static void main(String[] args) {
		new ParcelRouter().play();
	}

}
