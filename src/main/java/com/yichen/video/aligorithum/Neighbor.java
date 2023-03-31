package com.yichen.video.aligorithum;

public class Neighbor implements Comparable{
	private int id;
	private double value;
	public Neighbor(int id, double value) {
		this.id=id;
		this.value=value;
	}
	public int getID(){
		return id;
	}
	public double getValue(){
		return value;
	}
	
	
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof Neighbor){
			Integer ID=((Neighbor) o).id;
			Double VALUE=((Neighbor) o).value;
			return VALUE.compareTo(value);
		}
		else{
			return 2;
		}
	}
}

