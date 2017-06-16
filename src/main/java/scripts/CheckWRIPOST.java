package scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.AcornPrograms;
import pages.LoginPage;
import util.Driver;
import util.LoginCredentials;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Serguei on 6/15/2017.
 */
public class CheckWRIPOST extends Script {

    private final static String wriCourseCode = "ERMIN1302";

    public static void main(String[] args) {
        new CheckWRIPOST().execute();
    }

    private void execute() {
        driver.visit(LoginPage.getUrl());
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(LoginCredentials.getUsername(), LoginCredentials.getPassword());

        driver.visit("https://acorn.utoronto.ca/sws/welcome.do?welcome.dispatch#/");
        driver.visit("https://acorn.utoronto.ca/sws/welcome.do?welcome.dispatch#/enrol_and_manage");
        driver.visit(AcornPrograms.getUrl());
        AcornPrograms acornPrograms = new AcornPrograms(driver);

        // wait for wri post code to be on page
        driver.waitFor(() -> driver.isElementPresent(By.xpath("//h3[contains(text(), '" + wriCourseCode + "')]")));

        List<WebElement> postSections = driver.waitFor(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='program-listing-section']/div"), 2));
        List<List<String>> courseNamesInSections = postSections.stream()
                .map(section -> section.findElements(By.className("program_info_box")))
                .map(courses -> courses.stream()
                    .map(course -> course.findElement(By.cssSelector("div.program_title h3.code")))
                    .map(course -> course.getText().split(" ")[0])
                    .collect(Collectors.toList()))
                .collect(Collectors.toList());

        List<String> enrolledCourses = courseNamesInSections.get(0);
        List<String> requestedCourses = courseNamesInSections.get(1);

        if (enrolledCourses.contains(wriCourseCode))
            onSuccess();
        else if (requestedCourses.contains(wriCourseCode))
            onFail();
        else
            wtf();
    }

    private void onSuccess() {
        System.out.println("Success!");
        // notify me by email or some kind of phone notification
    }

    private void onFail() {
        System.out.println("Failure.");
    }

    private void wtf() {
        System.out.println("Wtf?");
        onSuccess();
    }

}
