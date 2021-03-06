/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.tools.analysis.checkstyle.test;

import java.io.File;

import org.junit.Test;
import org.openhab.tools.analysis.checkstyle.JavadocMethodStyleCheck;
import org.openhab.tools.analysis.checkstyle.api.AbstractStaticCheckTest;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Tests for {@link JavadocMethodStyleCheck}
 *
 * @author Kristina Simova - Initial contribution
 *
 */
public class JavadocMethodStyleCheckTest extends AbstractStaticCheckTest {

    private static final String EXPECTED_MESSAGE_CONTAINS_DASH = "There should be no dash between the parameter name and the description in a Javadoc comment of a method or constructor.";
    private static final String EXPECTED_MESSAGE_EMPTY_LINES_BETWEEN_TAGS = "There should be no empty lines between tags in a Javadoc comment of a method or constructor.";
    private static final String EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE = "The parameter description in a Javadoc comment of a method or constructor should not start on a new line.";
    private static final String TEST_DIRECTORY_NAME = "javadocMethodStyleCheckTest";

    private DefaultConfiguration configuration = createCheckConfig(JavadocMethodStyleCheck.class);

    @Test
    public void testMethodJavadocWithDashBetweenParameterNameAndParameterDescription() throws Exception {
        String fileName = "MethodWithDashes.java";
        String[] expectedMessages = generateExpectedMessages(6, EXPECTED_MESSAGE_CONTAINS_DASH, 7,
                EXPECTED_MESSAGE_CONTAINS_DASH, 8, EXPECTED_MESSAGE_CONTAINS_DASH);
        checkFile(fileName, expectedMessages);
    }

    @Test
    public void testMethodJavadocWithEmptyLinesBetweenTags() throws Exception {
        String fileName = "MethodWithEmptyLines.java";
        String[] expectedMessages = generateExpectedMessages(7, EXPECTED_MESSAGE_EMPTY_LINES_BETWEEN_TAGS, 9,
                EXPECTED_MESSAGE_EMPTY_LINES_BETWEEN_TAGS);
        checkFile(fileName, expectedMessages);
    }

    @Test
    public void testMethodJavadocWithWithParameterDescriptionOnNewLine() throws Exception {
        String fileName = "MethodWithParamDescriptionNewLine.java";
        String[] expectedMessages = generateExpectedMessages(9, EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE, 11,
                EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE, 13, EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE);
        checkFile(fileName, expectedMessages);
    }

    @Test
    public void testMethodJavadocWithDashesAndEmptyLinesBetweenTags() throws Exception {
        String fileName = "MethodWithEmptyLinesAndDashes.java";
        String[] expectedMessages = generateExpectedMessages(6, EXPECTED_MESSAGE_CONTAINS_DASH, 7,
                EXPECTED_MESSAGE_EMPTY_LINES_BETWEEN_TAGS, 8, EXPECTED_MESSAGE_CONTAINS_DASH, 9,
                EXPECTED_MESSAGE_EMPTY_LINES_BETWEEN_TAGS, 10, EXPECTED_MESSAGE_CONTAINS_DASH);
        checkFile(fileName, expectedMessages);
    }

    @Test
    public void testMethodJavadocWithDescriptionOnNewLineAndEmptyLinesBetweenTags() throws Exception {
        String fileName = "MethodWithDescriptionAndEmptyLines.java";
        String[] expectedMessages = generateExpectedMessages(7, EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE, 8,
                EXPECTED_MESSAGE_EMPTY_LINES_BETWEEN_TAGS, 10, EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE, 11,
                EXPECTED_MESSAGE_EMPTY_LINES_BETWEEN_TAGS, 13, EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE);
        checkFile(fileName, expectedMessages);
    }

    @Test
    public void testMethodJavadocWithDashesAndDescriptionOnNewLine() throws Exception {
        String fileName = "MethodWithDescriptionAndDashes.java";
        String[] expectedMessages = generateExpectedMessages(6, EXPECTED_MESSAGE_CONTAINS_DASH, 7,
                EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE, 8, EXPECTED_MESSAGE_CONTAINS_DASH, 9,
                EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE, 10, EXPECTED_MESSAGE_CONTAINS_DASH, 11,
                EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE);
        checkFile(fileName, expectedMessages);
    }

    @Test
    public void testMethodJavadocWithTwoEmptyLinesBetweenTags() throws Exception {
        String fileName = "MethodWithTwoEmptyLines.java";
        String[] expectedMessages = generateExpectedMessages(7, EXPECTED_MESSAGE_EMPTY_LINES_BETWEEN_TAGS, 9,
                EXPECTED_MESSAGE_EMPTY_LINES_BETWEEN_TAGS, 10, EXPECTED_MESSAGE_EMPTY_LINES_BETWEEN_TAGS);
        checkFile(fileName, expectedMessages);
    }

    @Test
    public void testMethodJavadocWithParameterNameAndDescriptionNewLine() throws Exception {
        String fileName = "MethodWithParamNameAndDescriptionNewLine.java";
        String[] expectedMessages = generateExpectedMessages(9, EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE, 11,
                EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE, 13, EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE);
        checkFile(fileName, expectedMessages);
    }

    @Test
    public void testMethoJavadocdWithAllWrongDescriptions() throws Exception {
        String fileName = "MethodWithWrongJavadoc.java";
        String[] expectedMessages = generateExpectedMessages(6, EXPECTED_MESSAGE_CONTAINS_DASH, 7,
                EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE, 8, EXPECTED_MESSAGE_EMPTY_LINES_BETWEEN_TAGS, 9,
                EXPECTED_MESSAGE_CONTAINS_DASH, 10, EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE, 11,
                EXPECTED_MESSAGE_EMPTY_LINES_BETWEEN_TAGS, 12, EXPECTED_MESSAGE_CONTAINS_DASH, 13,
                EXPECTED_MESSAGE_PARAMETER_DESCRIPTION_NEWLINE);
        checkFile(fileName, expectedMessages);
    }

    @Test
    public void testMethodJavadocWithParameterNameWithNoDescription() throws Exception {
        String fileName = "MethodWithNoParamNameDescription.java";
        /**
         * methods with missing parameter descriptions should not log anything
         * so we pass empty array
         */
        checkFile(fileName, CommonUtils.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testMethodJavadocWithProperTagParameterNameAndDescription() throws Exception {
        String fileName = "MethodWithProperDescriptions.java";
        /**
         * methods with proper parameter description should not log anything
         * so we pass empty array
         */
        checkFile(fileName, CommonUtils.EMPTY_STRING_ARRAY);
    }

    private void checkFile(String fileName, String[] expectedMessages) throws Exception {
        String filePath = getPath(TEST_DIRECTORY_NAME + File.separator + fileName);
        verify(configuration, filePath, expectedMessages);
    }
}
