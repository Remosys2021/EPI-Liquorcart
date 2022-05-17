package com.pms.util;

import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;


public class RunTestng {
	
	public static void main(String[] args)

	{
	TestNG runner=new TestNG();

	List<String> suitefiles=new ArrayList<>();

	suitefiles.add("./testng.xml");
	
	runner.setTestSuites(suitefiles);
	
	runner.run();
	}

}
