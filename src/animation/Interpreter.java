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
    		
    		if (debug)
    			System.out.println(event);
			
			String[] fields = event.split("[|]");

    		if (fields.length < 7) {
    			System.err.println("malformed event");
    			continue;
    		}
    		
    		String sourceName = fields[1];
    		String[] p = fields[6].split(";");
    		Map<String, String> params = new HashMap<String, String> ();
    		
    		for (String param : p) {
    			String[] keyValue = param.split(":");
    			params.put(keyValue[0], keyValue[1]);
    		}
    		
    		String cmd = params.get("cmd");
    		if (cmd == null)
    			continue;
    		
    		int chute = 0, 
    			parcel = 0,
    			stage = 0,
    			bin = 0,
    			door = 0;
    		
    		switch (cmd) {
    		case "chute_occupied":
				chute = Integer.valueOf(sourceName.substring(5));
				parcel = Integer.valueOf(params.get("parcel")) + 1;
				stage = Integer.valueOf(params.get("stage"));
				model.stages[stage].chutes[chute-1] = parcel;
			break;
			case "chute_free":
				chute = Integer.valueOf(sourceName.substring(5));
				stage = Integer.valueOf(params.get("stage"));
				model.stages[stage].chutes[chute-1] = 0;
			break;
			case "switcher_occupied":
				parcel = Integer.valueOf(params.get("parcel")) + 1;
				stage = Integer.valueOf(params.get("stage"));
				model.stages[stage].switcher = parcel;
			break;
			case "switcher_free":
				stage = Integer.valueOf(params.get("stage"));
				model.stages[stage].switcher = 0;
			break;
			case "bin_occupied":
				bin = Integer.valueOf(sourceName.substring(3));
				parcel = Integer.valueOf(params.get("parcel")) + 1;
				stage = Integer.valueOf(params.get("stage"));
				model.bins[bin] = parcel;
			break;
			case "switcher_door":
				stage = Integer.valueOf(params.get("stage"));
				door = Integer.valueOf(params.get("door"));
				model.stages[stage].door = door;
			break;
    		}
		}
    	view.repaint();
    	
	}

	public void pushEvent(String event) {
		if (!queue.offer(event))
			System.out.println("Impossible to insert into queue");
	}
}
