package RestAssured.RestAssuredDemo;

public class State
{
	private int id;
	private String stateName;
	private String stateAbb;
	private String largestCity;
	private String capital;
	
	public State(String stateAbb, String name, String largestCity, String capital)
	{
//		this.id = id;
		this.stateName = name;
		this.stateAbb = stateAbb;
		this.largestCity =largestCity;
		this.capital = capital;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getStateAbb() {
		return stateAbb;
	}
	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}
	public String getLargestCity() {
		return largestCity;
	}
	public void setLargestCity(String largestCity) {
		this.largestCity = largestCity;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	
	

}
