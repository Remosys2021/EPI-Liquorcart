package com.pms.util;

	public class TestPmsException extends RuntimeException {
		
		private static final long serialVersionUID = 1L;

		public TestPmsException() {
			super();
		}

		public TestPmsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
		}

		public TestPmsException(String message, Throwable cause) {
			super(message, cause);
		}

		public TestPmsException(String message) {
			super(message);
		}

		public TestPmsException(Throwable cause) {
			super(cause);
		}
		

	}

