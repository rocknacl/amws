package dao.report;

import java.io.BufferedInputStream;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import dao.entities.ReportActiveListings;
import helper.IOHelper;
import logic.report.ReportPreprocessor;

public class ReportObjectConverter {
	Properties p;
	Class<?> cls;
	static String storeFieldName = "store";
	static String insertDateFieldName = "insertDate";
	String dateFormat;
	Field compositeID;

	boolean hasStoreField;
	boolean hasInsertDateField;

	public ReportObjectConverter(String filePath, Class<?> c, String dateformat) throws IOException {
		p = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		p.load(in);
		this.cls = c;
		this.dateFormat = dateformat;

		try {
			Field storeField = cls.getDeclaredField(storeFieldName);
			if (storeField != null) {
				hasStoreField = true;
			}
		} catch (NoSuchFieldException e1) {
			hasStoreField = false;
		}

		try {
			Field insertDateField = cls.getDeclaredField(insertDateFieldName);
			if (insertDateField != null) {
				hasInsertDateField = true;
			}
		} catch (NoSuchFieldException e) {
			hasInsertDateField = false;
		}

		try {
			compositeID = cls.getDeclaredField("id");
			boolean hasIntCompositeID = (compositeID != null
					&& compositeID.getType().getSimpleName().equals("Integer"));
			if (hasIntCompositeID) {
				compositeID = null;
			}
		} catch (NoSuchFieldException e) {
		}
		in.close();

	}

	public ArrayList<Object> readObjectsFromFlatFile(String filePath, String merchant, Class<? extends ReportPreprocessor> class1)
			throws SecurityException, IllegalArgumentException, ParseException, InstantiationException,
			IllegalAccessException, NoSuchFieldException, IOException {
		ArrayList<String[]> data = null;
		if(class1!=null){
			data = class1.newInstance().getResult(filePath);
		}else{
			data = IOHelper.readtxt(filePath);
		}
		ArrayList<Object> results = new ArrayList<Object>();
		
		ArrayList<String> head = IOHelper.getHead(data);
		if (head == null) {
			return new ArrayList<Object>();
		}
		ArrayList<String[]> lines = IOHelper.getData(data);

		for (String[] line : lines) {
			Object o = cls.newInstance();
			if (hasStoreField) {
				Field storeField = cls.getDeclaredField(storeFieldName);
				storeField.setAccessible(true);
				storeField.set(o, merchant);
			}

			if (hasInsertDateField) {
				Field insertDateField = cls.getDeclaredField(insertDateFieldName);
				insertDateField.setAccessible(true);
				insertDateField.set(o, new Date());
			}
			if (compositeID != null) {
				Object cid = this.createCompositeID(compositeID.getType(), head, line);
				compositeID.setAccessible(true);
				compositeID.set(o, cid);
			}
			this.setObjectValuesFromStringArray(o, head, line);
			results.add(o);
		}
		return results;
	}

	private Object createCompositeID(Class<?> class1, ArrayList<String> head, String[] line)
			throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException,
			IllegalArgumentException {
		Object cid = class1.newInstance();
		for (Field f : class1.getDeclaredFields()) {
			int lineSize = line.length;
			for (int i = 0; i < (head.size() < lineSize ? head.size() : line.length); i++) {
				String attributeName = (String) p.get(head.get(i));
				if (attributeName != null && attributeName.equals(f.getName())) {
					f.setAccessible(true);
					this.setObjectValueByFlatString(cid, f.getName(), line[i]);
				}

			}
		}
		return cid;
	}

	private void setObjectValueByFlatString(Object target, String attributeName, String value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		if (value == null || value.equals(""))
			return;
		Class<?> cls = target.getClass();
		Field f = cls.getDeclaredField(attributeName);
		f.setAccessible(true);
		Object o = null;
		switch (f.getType().getSimpleName()) {
		case "String":
			o = value;
			break;
		case "Integer":
			o = Integer.parseInt(value);
			break;
		case "Date":
			if (this.dateFormat == null)
				break;
			try {
				o = new SimpleDateFormat(this.dateFormat).parse(value);
				break;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		case "BigDecimal":
			o = new BigDecimal(value);
			break;
		case "Double":
			o = Double.parseDouble(value);
			break;
		case "Float":
			o = Float.parseFloat(value);
			break;
		}
		f.set(target, o);
	}

	private void setObjectValuesFromStringArray(Object target, ArrayList<String> head, String[] line)
			throws SecurityException, IllegalArgumentException, IllegalAccessException {
		int lineSize = line.length;
		for (int i = 0; i < (head.size() < lineSize ? head.size() : line.length); i++) {
			String arributeName = (String) p.get(head.get(i));
			if (arributeName != null)
				try {
					this.setObjectValueByFlatString(target, arributeName, line[i]);
				} catch (NoSuchFieldException e) {
					boolean attributeInID = false;
					for (Field f : compositeID.getType().getDeclaredFields()) {
						if (f.getName().equals(arributeName)) {
							attributeInID = true;
							break;
						}
					}
					if (!attributeInID) {
						e.printStackTrace();
					}
				}
		}

	}

}
