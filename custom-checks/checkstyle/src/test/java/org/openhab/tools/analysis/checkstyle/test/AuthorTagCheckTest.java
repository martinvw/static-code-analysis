package org.openhab.tools.analysis.checkstyle.test;

import java.io.File;

import org.junit.Test;
import org.openhab.tools.analysis.checkstyle.AuthorTagCheck;
import org.openhab.tools.analysis.checkstyle.api.AbstractStaticCheckTest;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Tests for {@link AuthorTagCheck}
 *
 * @author Mihaela Memova
 *
 */
public class AuthorTagCheckTest extends AbstractStaticCheckTest {

    private static final String EXPECTED_WARNING_MESSAGE = "An author tag is missing";

    @Test
    public void testOuterClassWithNoAuthorTag() throws Exception {

        String fileName = "NoAuthorOuterAndInnerClasses.java";
        /*
         * an error is expected at the line where the outer class is declared
         * in the file
         */
        int warningLine = 4;
        boolean checkInnerClasses = false;
        checkFileForAuthorTags(checkInnerClasses, fileName, warningLine);
    }

    @Test
    public void testOuterAndInnerClassesWithNoAuthorTag() throws Exception {

        String fileName = "NoAuthorOuterAndInnerClasses.java";
        /*
         * errors are expected at the lines where the classes are declared in
         * the file
         */
        int firstWarningLine = 4;
        int secondWarningLine = 9;
        boolean checkInnerClasses = true;
        checkFileForAuthorTags(checkInnerClasses, fileName, firstWarningLine, secondWarningLine);
    }

    @Test
    public void testOuterClasWithNoJavaDoc() throws Exception {

        String fileName = "NoJavaDocOuterAndInnerClasses.java";
        /*
         * an error is expected at the line where the outer class is declared
         * in the file
         */
        int warningLine = 1;
        boolean checkInnerClasses = false;
        checkFileForAuthorTags(checkInnerClasses, fileName, warningLine);
    }

    @Test
    public void testOuterAndInnerClassesWithNoJavaDoc() throws Exception {

        String fileName = "NoJavaDocOuterAndInnerClasses.java";
        /*
         * errors are expected at the lines where the classes are declared in
         * the file
         */
        int firstWarningLine = 1;
        int secondWarningLine = 3;
        boolean checkInnerClasses = true;
        checkFileForAuthorTags(checkInnerClasses, fileName, firstWarningLine, secondWarningLine);
    }

    @Test
    public void testOuterAndInnerClassesWithPresentAuthorTag() throws Exception {

        String fileName = "PresentAuthorTagOuterAndInnerClasses.java";
        boolean checkInnerClasses = true;
        // no errors are expected so we don't pass any warning lines
        checkFileForAuthorTags(checkInnerClasses, fileName);
    }

    private void checkFileForAuthorTags(boolean checkInnerUnits, String fileName, Integer... warningLine)
            throws Exception {

        String filePath = getPath("authorTagCheckTest"+ File.separator + fileName);
        String[] expected = null;
        if (warningLine.length > 0) {
            expected = new String[warningLine.length];
            for (int i = 0; i < warningLine.length; i++) {
                expected[i] = warningLine[i] + ": " + EXPECTED_WARNING_MESSAGE;
            }
        } else {
            expected = CommonUtils.EMPTY_STRING_ARRAY;
        }

        DefaultConfiguration configuration = createConfiguration(checkInnerUnits);
        verify(configuration, filePath, expected);
    }

    private DefaultConfiguration createConfiguration(boolean checkInnerUnits) {

        DefaultConfiguration configuration = createCheckConfig(AuthorTagCheck.class);
        /*
         * Modify the configuration with the needed attributes and message. They
         * should be the same as their corresponding properties defined in
         * rulesets.checkstyle/rules.xml file
         */
        configuration.addAttribute("tag", "@author");
        configuration.addAttribute("tagFormat", "\\S");
        configuration.addAttribute("tagSeverity", "ignore");
        configuration.addAttribute("checkInnerUnits", String.valueOf(checkInnerUnits));
        configuration.addMessage("type.missingTag", EXPECTED_WARNING_MESSAGE);

        return configuration;
    }
}
