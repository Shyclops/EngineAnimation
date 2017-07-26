package animation;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

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
    		
    		if (debug)
    			System.out.println(event);
			
			String[] fields = event.split("[|]");

    		if (fields.length < 8	) {
    			//System.err.println("malformed event");
    			continue;
    		}
    		
    		String sourceName = fields[1];
    		String state = fields[2];
    		String eventSource = fields[3];
    		String eventKind= fields[4];
    		
    		String[] p = fields[7].split(";");
    		Map<String, String> params = new HashMap<String, String> ();
    		
    		for (String param : p) {
    			String[] keyValue = param.split(":");
    			params.put(keyValue[0], keyValue[1]);
    		}
    		String command = params.get("cmd");

    		if (command.equals("update")){
    			System.out.println("updated");
    			view.repaint();
    		}
    		
		}
    	view.repaint();
    	
	}

	public void pushEvent(String event) {
		if (!queue.offer(event))
			System.out.println("Impossible to insert into queue");
	}
}

