package io.github.tahanima.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import io.github.tahanima.report.ExtentReportManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import static io.github.tahanima.config.ConfigurationManager.configuration;

/**
 * @author tahanima
 */
public class TestListener implements ITestListener {
    private static final ExtentReports REPORT = ExtentReportManager.createReport();

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestNGMethod method = result.getMethod();
        String testData = result.getParameters()[0].toString();

        ExtentTest test =
                REPORT.createTest(method.getMethodName())
                        .assignCategory(method.getRealClass().getSimpleName())
                        .pass(String.format("Test Data: %s%n", testData));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestNGMethod method = result.getMethod();
        String testData = result.getParameters()[0].toString();

        ExtentTest test =
                REPORT.createTest(method.getMethodName())
                        .assignCategory(method.getRealClass().getSimpleName())
                        .fail(String.format("Test Data: %s%n", testData))
                        .fail(
                                result.getThrowable(),
                                MediaEntityBuilder.createScreenCaptureFromPath(
                                                String.format(
                                                        "%s%s.png",
                                                        configuration().baseScreenshotPath(),
                                                        method.getMethodName()))
                                        .build());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestNGMethod method = result.getMethod();
        String testData = result.getParameters()[0].toString();

        ExtentTest test =
                REPORT.createTest(method.getMethodName())
                        .assignCategory(method.getRealClass().getSimpleName())
                        .skip(String.format("Test Data: %s%n", testData));
    }

    @Override
    public void onFinish(ITestContext context) {
        REPORT.flush();
    }
}
