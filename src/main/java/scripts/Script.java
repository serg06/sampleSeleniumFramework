package scripts;

import org.openqa.selenium.WebDriver;
import util.Driver;

/**
 * Created by Serguei on 6/15/2017.
 */
public abstract class Script {

    Driver driver;

    public Script() {
        driver = new Driver();
    }

    public Script(Driver driver) {
        this.driver = driver;
    }

}
