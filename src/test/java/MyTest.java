import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class MyTest {

    @Test
    public void test() throws InterruptedException, MalformedURLException {

        System.setProperty("https.proxyHost", "172.27.13.10");
        System.setProperty("https.proxyPort", "3128");

        File knimeZip;
        File knimeDir = new File("knime");
        URL downloadUrl;

        if (SystemUtils.IS_OS_WINDOWS) {
            knimeZip = new File("knime.zip");
            downloadUrl = new URL("https://download.knime.org/analytics-platform/win/knime-latest-win32.win32.x86_64.zip");
        } else {
            knimeZip = new File("knime.tar.gz");
            downloadUrl = new URL("https://download.knime.org/analytics-platform/linux/knime-latest-linux.gtk.x86_64.tar.gz");
        }

        try {
            // Download KNIME
            FileUtils.copyURLToFile(downloadUrl, knimeZip);
            assertTrue(knimeZip.exists());

            // Extract KNIME
            Archiver archiver = ArchiverFactory.createArchiver(knimeZip);
            archiver.extract(knimeZip, knimeDir);
            assertTrue(knimeDir.exists());

            String childPath = knimeDir.listFiles()[0].getName();
            final String exe = "knime/" + childPath + (SystemUtils.IS_OS_WINDOWS ? "/knime.exe" : "/knime");

            final String director = "-application org.eclipse.equinox.p2.director";

            // Install FSK-Lab
            ProcessBuilder pb = new ProcessBuilder(exe, "-nosplash", director, "-repository https://update.knime.org/analytics-platform/3.6,https://dl.bintray.com/silebat/fsklab_icpmf", "-installIU de.bund.bfr.knime.fsklab.feature.feature.group");
            System.out.println("Command: " + String.join(" ", pb.command()));
            Process p = pb.start();
            p.waitFor();

            // List installed plugins and look for FSK-Lab
            pb.command(exe, "-nosplash", director, "-lir");
            System.out.println("Command: " + String.join(" ", pb.command()));
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
