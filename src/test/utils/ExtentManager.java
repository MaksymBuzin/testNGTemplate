package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ExtentManager {
    WebDriver driver;
    protected ExtentReports extentReports;
    protected ExtentTest extentTest;

    //Used to create an .html report file with given configurations.
    // After that we can create test report
    public void createReport(){
        extentReports = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("report.html");
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle(""); //the header of the html page
        spark.config().setReportName(""); // the name of the application you are working on
        extentReports.attachReporter(spark);
    }

    //to close/end the report
    public void closeReport(){
        extentReports.flush();
    }

    //to create a Test Report for each Test Case
    public void createTestReport(WebDriver driver, Method method){
        this.driver = driver;
        extentTest = extentReports.createTest(getCustomTestName(method));
        extentTest.assignAuthor(""); // your name or your team's
        extentTest.assignDevice(""); // Device/ OS
    }

    //To log a report message
    public void logMsg(String msg, String resultType){
        switch (resultType.toLowerCase()){
            case "pass": extentTest.pass(msg);
                break;
            case "fail": extentTest.fail(msg);
                break;
            case "skip": extentTest.skip(msg);
            break;
        }
    }

    public void logMsg(String msg){
        extentTest.info(msg);
    }

    public String getCustomTestName(Method method){
        Test testClass = method.getAnnotation(Test.class);

        if(testClass.testName().length() > 0)
            return testClass.testName();
        return method.getName();
    }

    public String getCustomTestDescription(Method method){
        Test testClass = method.getAnnotation(Test.class);

        if(testClass.description().length() > 0)
            return testClass.description();
        return method.getName();
    }

    //should be called in @AfterMethod
    public void closeTestReport(ITestResult result){
        //This will capture the result of the Test and log to extentTest report
        if(result.getStatus() == ITestResult.SUCCESS){
            extentTest.pass("Test PASSED");
        }else if(result.getStatus() == ITestResult.FAILURE){
            extentTest.fail("Test FAILED");
            logScreenshot();
            extentTest.fail(result.getThrowable());
        }else if(result.getStatus() == ITestResult.SKIP){
            extentTest.skip("Test SKIPPED");
            extentTest.fail(result.getThrowable());
        }
    }
    // to create a screenshot with message.just put it in your test as need it.
    public void logScreenshot(String title){
        extentTest.info(title,
                MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.takeScreenshot(driver)).build());
    }

    public void logScreenshot(){
        extentTest.info(MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.takeScreenshot(driver)).build());
    }

}
