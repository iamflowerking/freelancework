package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		
		features="src/test/resources/features/AllFeatures.feature",
		glue="StepDefs",
		plugin= {"pretty","html:HTML-Reports"},
		monochrome=true
	    )
public class AllFeaturesRunner  extends AbstractTestNGCucumberTests{

}
