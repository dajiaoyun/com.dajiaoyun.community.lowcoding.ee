package com.dajiaoyun.community.lowcoding.ee.util;

import java.util.Date;

public class RandomFileRenamePolicy {
	public static String rename(String oldfilename) {
	    long body = 0;
	    String ext = "";
	    Date date = new Date();
	    int pot = oldfilename.lastIndexOf(".");
	    if (pot != -1) {
	      body = date.getTime();
	      ext = oldfilename.substring(pot).toLowerCase();
	    } else {
	      body = new Date().getTime();
	      ext = "";
	    }
	    String newName = body + ext;
	    return newName;
	  }
}
