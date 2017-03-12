/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.converters.spring;

import com.cybernostics.jsp2thymeleaf.api.common.AvailableConverters;
import static com.cybernostics.jsp2thymeleaf.api.common.Namespaces.TH;
import static com.cybernostics.jsp2thymeleaf.api.common.Namespaces.XMLNS;
import com.cybernostics.jsp2thymeleaf.api.common.taglib.ConverterRegistration;
import static com.cybernostics.jsp2thymeleaf.api.elements.JspTagElementConverter.converterFor;
import com.cybernostics.jsp2thymeleaf.api.elements.NewAttributeBuilder;
import com.cybernostics.jsp2thymeleaf.api.elements.TagConverterSource;

/**
 *
 * @author jason
 */
public class SpringTagsConverterRegistration implements ConverterRegistration
{

    @Override
    public void run()
    {
        final TagConverterSource jstlCoreTaglibConverterSource = new TagConverterSource()
                //form
                //button
                //checkbox
                //checkboxes
                //errors
                //hidden
                //input
                //label
                //option
                //options
                //password
                //radiobutton
                //radiobuttons
                //select
                //textarea
                .withConverters(
                        converterFor("form")
                                .withNewName("form", XMLNS)
                                .removesAtributes("commandName")
                                .addsAttributes(NewAttributeBuilder.attributeNamed("object", TH)
                                        .withValue("\\${${commandName}}"))//,
                //                        converterFor("out")
                //                                .withNewName("span", XMLNS)
                //                                .renamesAttribute("value", "text", TH)
                //                                .withNewTextContent("%{value!humanReadable}"),
                //                        converterFor("foreach")
                //                                .withNewName("block", TH)
                //                                .removesAtributes("var", "begin", "end", "step", "varStatus", "items", "step")
                //                                .addsAttributes(
                //                                        attributeNamed("each", TH)
                //                                                .withValue(fromFormats(
                //                                                        "%{var}%{varStatus|!addCommaPrefix} : %{items}",
                //                                                        "%{var}%{varStatus|!addCommaPrefix} : ${#numbers.sequence(%{begin},%{end}%{step|!addCommaPrefix})}"))),
                //                        converterFor("set")
                //                                .withNewName("block", TH)
                //                                .removesAtributes("var", "scope", "value")
                //                                .addsAttributes(
                //                                        attributeNamed("expr", CN)
                //                                                .withValue(
                //                                                        fromFormats("${#CNPageParams.put%{scope|page!ucFirst}(%{var},%{value!stripEL})}"))
                //                                ),
                //                        converterFor("url")
                //                                .withNewName("span", XMLNS)
                //                                .removesAtributes("value", "var", "scope", "context")
                //                                .addsAttributes(
                //                                        attributeNamed("text", TH)
                //                                                .withValue(fromFormats("@{${value}}"))
                );

        AvailableConverters.addConverter("http://www.springframework.org/tags/form", jstlCoreTaglibConverterSource);

    }

}
