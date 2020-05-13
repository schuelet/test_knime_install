FOLDER="$TRAVIS_BUILD_DIR/de.bund.bfr.knime.update/target/repository"
KNIME_FILE="knime_3.7.2.win32.win32.x86_64.zip"

wget -q "http://download.knime.org/analytics-platform/win/$KNIME_FILE" 
unzip -q "$KNIME_FILE" 
echo $KNIME_FILE
rm $KNIME_FILE

WF_ZIP="wf.zip"
wget -q https://dl.bintray.com/silebat/build_pipeline_test_wf/$WF_ZIP
unzip $WF_ZIP -d wf
rm $WF_ZIP


KNIME37="https://update.knime.org/analytics-platform/3.7"
#$OLD_FSK="https://dl.bintray.com/silebat/fsklab_test"
#$NEW_FSK="file:$TRAVIS_BUILD_DIR/de.bund.bfr.knime.update/target/repository"
#$NEW_FSK="file:/C:/Arbeit/git/FSK-Lab_290420/de.bund.bfr.knime.update/target/repository"
NEW_FSK=$OLD_FSK

echo "INSTALL NEW FSK-LAB INTO FRESH KNIME"
knime_3.7.2/knime -nosplash -application org.eclipse.equinox.p2.director -repository "$KNIME37,$NEW_FSK" -installIU org.knime.features.testingapplication.feature.group,de.bund.bfr.knime.fsklab.feature.feature.group,de.bund.bfr.knime.r.x64.feature.feature.group
#Start-Process -filepath "knime_3.7.2/knime.exe" -ArgumentList "-nosplash -application org.eclipse.equinox.p2.director -repository $KNIME37,$NEW_FSK -installIU de.bund.bfr.knime.fsklab.feature.feature.group,org.knime.features.testingapplication.feature.group" -passthru -wait -RedirectStandardOutput stdOut.tmp -RedirectStandardError stdErr.tmp
#Start-Process -filepath "knime_3.7.2/knime.exe" -ArgumentList "-nosplash -application org.eclipse.equinox.p2.director -repository $KNIME37,$NEW_FSK -installIU org.knime.features.testingapplication.feature.group,de.bund.bfr.knime.fsklab.feature.feature.group,de.bund.bfr.knime.r.x64.feature.feature.group" -passthru -wait -RedirectStandardOutput stdOut.tmp -RedirectStandardError stdErr.tmp
echo "ERRORS"
#Get-Content stdErr.tmp
echo "OUTPUT"
#Get-Content stdOut.tmp


#knime_3.7.2/knime --launcher.suppressErrors -nosplash -consoleLog -noexit -application org.eclipse.equinox.p2.director -r https://update.knime.org/analytics-platform/3.7 -installIU org.knime.features.testingapplication.feature.group 
knime_3.7.2/knime -nosplash -reset -nosave -application org.knime.product.KNIME_BATCH_APPLICATION -workflowFile=wf/fsk_lab_test_wf.knwf --launcher.suppressErrors
#Start-Process -filepath "knime_3.7.2/knime.exe" -ArgumentList "-nosplash -reset -nosave -application org.knime.product.KNIME_BATCH_APPLICATION -workflowFile=wf/fsk_lab_test_wf.knwf --launcher.suppressErrors" -passthru -wait -RedirectStandardOutput stdOut.tmp -RedirectStandardError stdErr.tmp
echo "ERRORS"
#Get-Content stdErr.tmp
echo "OUTPUT"
#Get-Content stdOut.tmp

#knime_3.7.2/knime -nosplash -reset -nosave -application org.knime.product.KNIME_BATCH_APPLICATION -workflowFile="knime_batch_test.knwf" --launcher.suppressErrors


echo "REMOVE NEW FSK TO TEST UPDATE FROM OLDER VERSION"
#knime_3.7.2/knime -nosplash -application org.eclipse.equinox.p2.director -uninstallIU de.bund.bfr.knime.fsklab.feature.feature.group

 
rm -r knime_3.7.2 
rm -r wf 
