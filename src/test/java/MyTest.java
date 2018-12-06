import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MyTest {

    @Test
    public void test() throws InterruptedException {

        System.setProperty("https.proxyHost", "172.27.13.10");
        System.setProperty("https.proxyPort", "3128");

        File knimeZip = new File("knime.tar.gz");
        File knimeDir = new File("knime");

        try {
            // Download KNIME
            URL url = new URL("https://download.knime.org/analytics-platform/linux/knime-latest-linux.gtk.x86_64.tar.gz");
            FileUtils.copyURLToFile(url, knimeZip);
            assertTrue(knimeZip.exists());

            // Extract KNIME
            Archiver archiver = ArchiverFactory.createArchiver(knimeZip);
            archiver.extract(knimeZip, knimeDir);
            assertTrue(knimeDir.exists());

            String childPath = knimeDir.listFiles()[0].getName();
            final String exe = "knime/" + childPath + "/knime";
            final String director = "-application org.eclipse.equinox.p2.director";

            // Install FSK-Lab
            ProcessBuilder pb = new ProcessBuilder(exe, "-nosplash", director, "-repository https://dl.bintray.com/silebat/fsklab_icpmf", "-installIU de.bund.bfr.knime.fsklab.feature.feature.group");
            Process p = pb.start();
            p.waitFor();
            String errors = IOUtils.toString(p.getErrorStream());
            System.out.println("Errors: " + errors);
            String output = IOUtils.toString(p.getInputStream());
            System.out.println("Output: " + output);

            // List installed plugins and look for FSK-Lab
            pb.command(exe, "-nosplash", director, "-list");
            p = pb.start();
            p.waitFor();

            List<String> outputLines = IOUtils.readLines(p.getInputStream(), "UTF-8");
            System.out.println(outputLines);
            assertTrue(outputLines.contains("de.bund.bfr.knime.fsklab.feature.feature.group"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtils.deleteQuietly(knimeZip);
            FileUtils.deleteQuietly(knimeDir);
        }
    }
}
