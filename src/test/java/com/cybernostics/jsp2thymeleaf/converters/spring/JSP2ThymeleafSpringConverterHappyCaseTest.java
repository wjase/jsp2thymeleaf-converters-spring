package com.cybernostics.jsp2thymeleaf.converters.spring;

import com.cybernostics.jsp2thymeleaf.JSP2ThymeleafConfiguration;
import com.cybernostics.jsp2thymeleaf.api.common.TokenisedFile;
import com.cybernostics.jsp2thymeleaf.api.exception.JSP2ThymeLeafException;
import com.cybernostics.jsp2thymeleaf.converters.JSP2ThymeleafFileConverter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author jason
 */
@RunWith(Parameterized.class)
public class JSP2ThymeleafSpringConverterHappyCaseTest
{

    private static Logger LOG = Logger.getLogger(JSP2ThymeleafSpringConverterHappyCaseTest.class.getName());

    private final File jspFile;
    final URL rootResource;
    final Path rootPath;

    public JSP2ThymeleafSpringConverterHappyCaseTest(String name, File JSPFile) throws URISyntaxException
    {
        this.jspFile = JSPFile;
        rootResource = JSP2ThymeleafSpringConverterHappyCaseTest.class.getClassLoader().getResource("happy_case_files/");
        rootPath = Paths.get(rootResource.toURI());
    }

    @Test
    public void JSPToThymeleafShouldConvert()
    {
        JSP2ThymeleafConfiguration configuration = JSP2ThymeleafConfiguration.getBuilder()
                .withConverterPackages("com.cybernostics.jsp2thymeleaf.converters.spring")
                .build();
        JSP2ThymeleafFileConverter jSP2Thymeleaf = new JSP2ThymeleafFileConverter(configuration);
        jSP2Thymeleaf.setShowBanner(false);
        try
        {
            File expectedThymeleafFileContent = new File(jspFile.getAbsolutePath().replaceAll(".jsp$", ".html"));
            String expectedContent = FileUtils.readFileToString(expectedThymeleafFileContent, Charset.defaultCharset());

            TokenisedFile jspFileTok = new TokenisedFile(jspFile.toPath(), rootPath);

            File randomOutFile = File.createTempFile("happyCaseTest", ".jsp");

            final List<JSP2ThymeLeafException> errors = jSP2Thymeleaf.convert(jspFileTok, randomOutFile);
            assertThat(errors, is(empty()));
            final String convertedContent = FileUtils.readFileToString(randomOutFile, Charset.defaultCharset());
            LOG.info("\n" + convertedContent);
            assertThat(convertedContent.replaceAll("\\s+", " "), is(expectedContent.replaceAll("\\s+", " ")));
        } catch (IOException ex)
        {
            Logger.getLogger(JSP2ThymeleafSpringConverterHappyCaseTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("exception thrown");
        }
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Object[]> data()
    {
        return getTestFiles().
                map(eachFile -> Arrays.asList((Object) eachFile.getName(), (Object) eachFile).toArray())
                .collect(Collectors.toList());
    }

    @After
    public void shouldTestAllCases()
    {
        final URL resource = JSP2ThymeleafSpringConverterHappyCaseTest.class.getClassLoader().getResource("happy_case_files");
        final File file = new File(resource.getFile());
        assertThat((int) getTestFiles().count(), is(file.list().length / 2));
    }

    public static Stream<File> getTestFiles()
    {
        final URL resource = JSP2ThymeleafSpringConverterHappyCaseTest.class.getClassLoader().getResource("happy_case_files");
        final File file = new File(resource.getFile());
        return Arrays.asList(file.listFiles())
                .stream()
                .filter(it -> it.getName().contains("jsp"))
                //                .filter(it -> it.getName().contains("cif"))
                .sorted();
    }
}
