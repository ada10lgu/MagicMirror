package ui;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.json.JSONObject;

@SuppressWarnings("serial")
public abstract class MirrorComponent extends JPanel {
	public static final Color BACKGROUND = Color.BLACK;
	public static final Color TEXT = Color.WHITE;

	protected JSONObject extra;
	private Worker worker;

	public MirrorComponent(JSONObject extra) {
		this.extra = extra;
	}

	protected void start(long timerDelay) {
		if (worker == null) {
			worker = new Worker(timerDelay);
			worker.execute();
		}
	}

	protected void work() {

	}
	
	protected Font extract(JSONObject object) {
		Font font = new Font(object.getString("font"), Font.PLAIN, object.getInt("size"));
		return font;
	}

	private class Worker extends SwingWorker<Void, Void> {

		private long timerDelay;

		public Worker(long timerDelay) {
			this.timerDelay = timerDelay;
		}

		@Override
		protected Void doInBackground() throws Exception {
			while (true) {
				Thread.sleep(timerDelay);
				publish();
			}
		}

		@Override
		protected void process(List<Void> chunks) {
			work();
		}
	}
}
