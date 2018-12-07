import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;

public class MyTest {

    @Test
    public void test() throws InterruptedException, MalformedURLException {

        System.out.println(Arrays.toString(new File(".").list()));

        File knimeDir = new File("knime_3.7.0");
        assertTrue("knime_3.7.0 exists", knimeDir.exists());
//
//            String childPath = knimeDir.listFiles()[0].getName();
//            final String exe = "knime/" + childPath + (SystemUtils.IS_OS_WINDOWS ? "/knime.exe" : "/knime");
//
//            final String director = "-application org.eclipse.equinox.p2.director";
//
//            // Install FSK-Lab
//            ProcessBuilder pb = new ProcessBuilder(exe, "-nosplash", director, "-repository https://update.knime.org/analytics-platform/3.6,https://dl.bintray.com/silebat/fsklab_icpmf", "-installIU de.bund.bfr.knime.fsklab.feature.feature.group");
//            System.out.println("Command: " + String.join(" ", pb.command()));
//            Process p = pb.start();
//            p.waitFor();
//
//            // List installed plugins and look for FSK-Lab
//            pb.command(exe, "-nosplash", director, "-lir");
//            System.out.println("Command: " + String.join(" ", pb.command()));
//            p = pb.start();
//            p.waitFor();
//
//            List<String> outputLines = IOUtils.readLines(p.getInputStream(), "UTF-8");
//            System.out.println(outputLines);
//            assertTrue(outputLines.contains("de.bund.bfr.knime.fsklab.feature.feature.group"));
    }
}
