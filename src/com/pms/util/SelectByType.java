package com.pms.util;

	
	public enum SelectByType {
		VISIBLE_TEXT(1),
		VALUE(2);
		
		private int type;
		
		private SelectByType(int type){
			this.type = type;
		}
		
		public int getType(){
			return type;
		

}}
