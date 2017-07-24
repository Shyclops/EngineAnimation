package animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import javax.imageio.ImageIO;

import animation.model.Model;
import animation.view.View;


public class Interpreter extends Thread {

	private BlockingQueue<String> queue  = new PriorityBlockingQueue<String>();
	private View view;
	boolean debug = false;
	
	Model model = new Model();
	
	public Interpreter() {
		super();
	}
	
	@Override
	public void run() {
		super.run();	
		
		view = new View(model);
		view.repaint();
			
		Timer timer = new Timer();
    	TimerTask myTask = new TimerTask() {
    	    @Override
    	    public void run() {
    	    	pollingEvents();	
    	    }
    	};
    	
    	timer.schedule(myTask, 200, 200);
	    		
	}
	
	public void pollingEvents() {
		String event;
    	
    	while ((event = queue.poll()) != null) {
    		
    		if (debug){
    			System.out.println(event);
    		}
			String[] fields = event.split("[|]");
			
    		if (fields.length < 7) {
    			System.err.println("malformed event");
    			continue;
    		}
    		
    		String[] p = fields[6].split(";");
    		Map<String, String> params = new HashMap<String, String> ();
    		
    		for (String param : p) {
    			String[] keyValue = param.split(":");
    			params.put(keyValue[0], keyValue[1]);
    		}
    		
		}
    	view.repaint();
    	
	}

	public void pushEvent(String event) {
		if (!queue.offer(event))
			System.out.println("Impossible to insert into queue");
	}
}
