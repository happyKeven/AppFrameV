package com.keven.library.database;

import java.util.UUID;

public class UUIDUtil {
	
	public static String getGid(){
		
		UUID deviceUuid = UUID.randomUUID();
		return deviceUuid.toString();
	}
	
}
