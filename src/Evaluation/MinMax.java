
public class MinMax {

	
	double min;
	double max;
	
	
	
	public MinMax() {
		super();
		// TODO Auto-generated constructor stub
		 min=Double.POSITIVE_INFINITY;
		 max=Double.NEGATIVE_INFINITY;

	}

	
	public MinMax(double num) {
		// TODO Auto-generated constructor stub
		 min=Double.POSITIVE_INFINITY;
		 max=Double.NEGATIVE_INFINITY;

		changeMax(num);
		changeMin(num);
	}


	public void changeMax(double num)
	{
		if(max<num)
			max=num;
	}

	public void changeMin(double num)
	{
		if(min>num)
			min=num;
	}
}
