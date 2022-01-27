package com.doan.sigma.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
//changing org.thymeleaf to org.springframework.boot in pom.xml fixed the bean not found error
@Service
@Transactional
public class MailContentBuilder {

	@Autowired
	private TemplateEngine templateEngine;
	
	public String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
	}
}