package logic.report;

import java.util.ArrayList;

public abstract class ReportPreprocessor {
	public ReportPreprocessor(){
	}
	abstract public ArrayList<String[]> getResult(String filePath);

}
