# Test script for FSK-Lab update site [![Build Status](https://travis-ci.org/silebat/testInstall.svg?branch=master)](https://travis-ci.org/silebat/testInstall)

Test the installation of FSK-Lab from the update site https://db.bintray.com/silebat/fsklab_icpmf

Steps:
1. Download KNIME 3.6.2 for Linux
2. Extract KNIME
3. Run p2 director to install FSK-Lab: `knime -application org.eclipse.equinox.p2.director -repository https://update.knime.org/analytics-platform/3.6,https://dl.bintray.com/silebat/fsklab_icpmf -installIU de.bund.bfr.knime.fsklab.feature.feature.group`
4. List installed plugins
5. A unittest checks the list of installed plugins and fails if FSK-Lab is not included
