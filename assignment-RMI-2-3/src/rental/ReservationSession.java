package rental;

import java.io.Serializable;

public class ReservationSession implements Serializable {
	
	private String name;

	ReservationSession(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
