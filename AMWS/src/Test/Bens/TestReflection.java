package Test.Bens;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dao.entities.MerchantAccount;

public class TestReflection {
	public static void main(String[] args) {
		MerchantAccount account = new MerchantAccount();
		Class<?> cls = account.getClass();
		System.out.println(cls.getSimpleName());
		System.out.println(cls.getModifiers());
		for (Field f : cls.getDeclaredFields()) {
			if(f.getName().equals("name")){
				try {
					System.out.println(f.getType());
					f.setAccessible(true);
					f.set(account, "AA");
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(account.getName());

	}

}
