package com.dajiaoyun.community.lowcoding.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class UidGenerator {
	public static String getIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0     
        // 4 代表长度为4     
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
    }
	public static String getIdByUUId2() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0     
        // 4 代表长度为4     
        // d 代表参数为正数型
        return machineId + String.format("%04d", hashCodeV);
    }
	
	public static String getIdByYMD() {
		Random random = new Random();
	    DecimalFormat df = new DecimalFormat("00");
	    String id = new SimpleDateFormat("yyyyMMddHHmmss")
	                .format(new Date()) + df.format(random.nextInt(100));
	    
	    return id;
	}
}
