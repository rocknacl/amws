package temp;
import java.io.*;

import dao.report.ReportObjectConverter;
import dao.report.ReportObjectDAO;
import model.report.ReportTypeEnum;

public class InsertHistoryFiles {
	public static void main(String[] args) {
		File d = new File("D:/st/");
		File[] files = d.listFiles();
		for (File f : files) {
//			System.out.println(f.getName());
			String store = f.getName().split("_")[1];
			ReportTypeEnum e = null;
			if (f.getName().contains("FBA")) {
				e = ReportTypeEnum.FBA_Amazon_Fulfilled_Shipments_Report;

			}
			if (f.getName().contains("Mega")) {
				e = ReportTypeEnum.Ads_Mega;
			}
			if (e != null) {
				System.out.println("inserting : "+f.getName());
				try {
					ReportObjectDAO.saveOrUpdateMultipleObjects(
							new ReportObjectConverter(e.propertiesFilePath, e.getClassCorrespond(),e.getDateFormat())
									.readObjectsFromFlatFile(f.getPath(), store, e.getBeforehandProcessor()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
	}

}
