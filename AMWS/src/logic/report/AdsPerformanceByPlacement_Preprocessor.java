package logic.report;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AdsPerformanceByPlacement_Preprocessor extends ReportPreprocessor {
	ArrayList<String[]> result = new ArrayList<String[]>();
	@Override
	public ArrayList<String[]> getResult(String filePath) {
		
		String line;

		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath), "UTF-8"));

			while ((line = bf.readLine()) != null) {
				String[] words = line.split(",");
				result.add(words);
			}
			bf.close();
			result.remove(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
