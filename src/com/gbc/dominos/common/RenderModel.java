package com.gbc.dominos.common;

import hapax.Template;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import org.apache.log4j.Logger;

/**
 *
 * @author vuln2
 */
public class RenderModel {
	private static final Logger log = Logger.getLogger(RenderModel.class);

    public static Template getCTemplate(String tpl) throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("com/gbc/dominos/email_template/");
        Template template = templateLoader.getTemplate(tpl);
        return template;
    }

}
