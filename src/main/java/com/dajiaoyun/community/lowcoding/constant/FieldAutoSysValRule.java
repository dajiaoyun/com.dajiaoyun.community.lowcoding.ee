package com.dajiaoyun.community.lowcoding.constant;

public enum FieldAutoSysValRule {
	DATEAUTO("10"), SIMPLEAUTO("11"), DATE("20"), DATETIME("21"), LOGINUSER("30");

	private String value;

	private FieldAutoSysValRule(String val) {
		this.value = val;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
