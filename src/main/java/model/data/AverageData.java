package model.data;

public class AverageData {
	private Double openAvg = 0.0;
	private Double closeAvg = 0.0;
	private Double lowAvg = 0.0;
	private Double highAvg = 0.0;
	private int numberOfNodesAveraged = 0;

	public AverageData() {
	}

	public int getNumberOfNodesAveraged() {
		return numberOfNodesAveraged;
	}

	public void setNumberOfNodesAveraged(int numberOfNodesAveraged) {
		this.numberOfNodesAveraged = numberOfNodesAveraged;
	}

	public Double getOpenAvg() {
		return openAvg;
	}

	public void setOpenAvg(Double openAvg) {
		this.openAvg = openAvg;
	}

	public Double getCloseAvg() {
		return closeAvg;
	}

	public void setCloseAvg(Double closeAvg) {
		this.closeAvg = closeAvg;
	}

	public Double getLowAvg() {
		return lowAvg;
	}

	public void setLowAvg(Double lowAvg) {
		this.lowAvg = lowAvg;
	}

	public Double getHighAvg() {
		return highAvg;
	}

	public void setHighAvg(Double highAvg) {
		this.highAvg = highAvg;
	}
}
