import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ExtentReportsTests {

    private static ExtentReports extent;
    private static ExtentHtmlReporter htmlReporter;
    private static ExtentTest test;
    private static ExtentTest step;

    @BeforeTest
    public void startReport(){
        extent = new ExtentReports ();
        extent.setSystemInfo("Environment","Testing");
        extent.setSystemInfo("Host Name", "AtanasBogev_PC");
        extent.setSystemInfo("Developer", "Atanas Bogev");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/reports/" + new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss").format(new Date()) + "_report.html");
        htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir")+"\\\\src\\test\\resources\\extentconfig.xml"));
        //htmlReporter.setAppendExisting(true); //helps to append the new run results to the existing report
        extent.attachReporter(htmlReporter);
    }

    @Test
    public void simpleTest(){
        test = extent.createTest("This is a simple test.");
        test.pass("passed");

        step = test.createNode("Name of this step1 (node) - skipped.", "This is description1.");
        step.skip("skipped");

        step = test.createNode("Name of this step2 (node) - failed.", "This is description2.");
        try {
            step.fail("details", MediaEntityBuilder.createScreenCaptureFromPath("C:\\Users\\atanas.bogev\\Desktop\\IMG_0174.JPG").build());
        }catch (Exception e){}

        step = test.createNode("Name of this step3 (node) - passed.", "This is description3.");
        step.pass("passed");
    }

    @AfterTest
    public void endReport(){
        extent.flush();
    }
}