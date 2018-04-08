package helper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @version 20151211
 * @author Bens
 *
 * Valid Dimension Unit: um, mm, cm, dm, m, km, in, ft, yd, mi, nmi, fm, nmi
 * Valid Weight Unit: g, kg, mg, t, q, lb, oz, ct, gr, lt, st
 */
public class UnitConverter {
	private final double basic = 1.000000000000;
	private final double error = -9999.9999;
	private final String basicunit = "basic";
	public HashMap<String,Double> dimensionmap = new HashMap<String,Double>();
	public HashMap<String,Double> weightmap = new HashMap<String,Double>();
	private ArrayList<HashMap<String,Double>> list = new ArrayList<HashMap<String,Double>>();
	
	public UnitConverter(){
			// basic unit : centimeter
			dimensionmap.put("basic", basic);
			dimensionmap.put("inches", 2.540000000000);
			dimensionmap.put("centimeters", basic);
			dimensionmap.put("cm", basic);
			dimensionmap.put("um", 0.000010000000);
			dimensionmap.put("mm", 0.100000000000);
			dimensionmap.put("dm", 10.000000000000);
			dimensionmap.put("m", 100.000000000000);
			dimensionmap.put("km", 100000.000000000);
			dimensionmap.put("in", 2.540000000000);
			dimensionmap.put("ft", 30.480000000000);
			dimensionmap.put("yd", 91.440000000000);
			dimensionmap.put("mi", 160934.400000000);
			dimensionmap.put("nmi", 185200.00000000);
			dimensionmap.put("fm", 182.880000000000);
			dimensionmap.put("nmi", 20116.800000000);
			list.add(dimensionmap);
			
			// basic unit : gram
			weightmap.put("basic", basic);
			weightmap.put("g", basic);
			weightmap.put("kg", 1000.0000000000);
			weightmap.put("mg", 0.001000000000);
			weightmap.put("t", 1000000.000000000);
			weightmap.put("q", 100000.0000000000);
			weightmap.put("lb", 453.592370000000);
			weightmap.put("oz", 28.349523100000);
			weightmap.put("ct", 0.200000000000);
			weightmap.put("gr", 0.064798900000);
			weightmap.put("lt", 1016046.908800000);
			weightmap.put("st", 907184.7400000000);
			list.add(weightmap);
			
	}
	
	/**
	 * 
	 * @param value
	 * @param paramunit
	 * @param resultunit
	 * @return if unit is valid, will return result value, or return -9999.9999
	 */
	public double Convert(double value, String paramunit, String resultunit){
		for(HashMap<String,Double> map : list){
		if(isUnitValid(map, paramunit, resultunit))
			return Convert(map,value, paramunit, resultunit);
		}
		return error;
	}
	
	/**
	 * 
	 * @param value
	 * @param paramunit
	 * @param resultunit Type: String
	 * @return if unit is valid, will return result value, or return -9999.9999
	 */
	public String ConvertString(double value, String paramunit, String resultunit){
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(Convert(value,paramunit,resultunit));
	}
	
	/**
	 * This function will return the result by using basic result unit
	 * Default basic unit:
	 * Dimension: cm;
	 * Weight: g;
	 * @param value
	 * @param paramunit
	 * @return if unit is valid, will return result value, or return -9999.9999
	 */
	public double Convert(double value, String paramunit){
		for(HashMap<String,Double> map : list){
		if(isUnitValid(map, paramunit, basicunit))
			return Convert(map,value, paramunit, basicunit);
		}
		return error;
	}	

	private boolean isUnitValid(HashMap<String,Double> unitmap, String paramunit, String resultunit){
		return unitmap.containsKey(paramunit) && unitmap.containsKey(resultunit);
	}
	
	private double Convert(HashMap<String,Double> unitmap, double value, String paramunit, String resultunit){
		return value*(unitmap.get(paramunit)/unitmap.get(resultunit));
	}
	
//	public static void main(String[] args) {
//		UnitConverter converter = new UnitConverter();
//		String temp = converter.ConvertString(28.35, "g", "oz");
////		DecimalFormat df = new DecimalFormat("0.00");
//		System.out.println(temp);
//	}

}
