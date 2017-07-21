package animation;

import animation.server.SocketServer;

public class Engine {

	private Interpreter interpreter;
	private SocketServer server;
	
	public Engine() {
		interpreter = new Interpreter();
		server = new SocketServer();
		server.setInterpreter(interpreter);
	}
	
	public void play() {
		interpreter.start();
		server.start();
	}

	public static void main(String[] args) {
		new Engine().play();
	}

}
