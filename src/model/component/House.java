package model.component;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class House extends Component {

	private List<Room> rooms = new ArrayList<>();

	public House(JSONObject json) {
		super(json);
		verify(json, "room");
		JSONObject jsonRooms = json.getJSONObject("room");
		for (String key : jsonRooms.keySet()) {
			rooms.add(new Room(jsonRooms.getJSONObject(key)));
		}
	}

	public class Room {
		private JSONObject json;

		public Room(JSONObject json) {
			verify(json, "x");
			verify(json, "y");
			verify(json, "w");
			verify(json, "h");
		}

		private int left() {
			if (getW() < 0)
				return getX() - getW();
			return getX();
		}

		private int right() {
			if (getW() < 0)
				return getX();
			return getX() + getW();
		}
		
		private int top() {
			if (getH() < 0)
				return getY() - getH();
			return getY();
		}
		
		private int bottom() {
			if (getH() < 0)
				return getY();
			return getY() + getH();
		}

		public int getX() {
			return json.getInt("x");
		}

		public int getY() {
			return json.getInt("y");
		}

		public int getW() {
			return json.getInt("w");
		}

		public int getH() {
			return json.getInt("h");
		}
	}
}