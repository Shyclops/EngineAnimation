package animation;

import animation.server.SocketClient;

public class Engine {

	private Interpreter interpreter;
	private SocketClient server;
	
	public Engine() {
		interpreter = new Interpreter();
		server = new SocketClient();
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
