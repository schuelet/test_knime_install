import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

public class MyTest {

    @Test
    public void test() throws InterruptedException, IOException {

        File pluginList = new File("plugins.txt");

        try (FileInputStream stream = new FileInputStream(pluginList)) {
            List<String> lines = IOUtils.readLines(stream);
            for (String line : lines) {
                if (line.startsWith("de.bund.bfr.knime.fsklab.feature.feature.group"))
                    return;
            }
            fail("FSK-Lab not installed");
        }
    }
}
