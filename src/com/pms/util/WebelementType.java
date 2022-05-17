package com.pms.util;

public enum WebelementType {
	ID(1) ,
	CLASS_NAME(2) ,
	LINK_TEXT(3),
    XPATH(4),
	CSS(5);
    
private int type;

private WebelementType(int type) {
	this.type = type;
}

public int getType() {
	return type;
}}
