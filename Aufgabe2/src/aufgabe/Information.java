package aufgabe;

import java.util.List;

public class Information {
	
	private List<State> states;
	
	public List<State> getStates() {
		return states;
	}
	public void setStates(List<State> states) {
		this.states = states;
	}
	public List<City> getCities() {
		return cities;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	private List<City> cities;
	
	
}
