package sese.services.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class TemplateUtil {

    private static TemplateEngine templateEngine;

    @Autowired
    private TemplateEngine templateEngineBean;

    private TemplateUtil() {
    }

    public static String processTemplate(String template, Map<String, Object> variables) {
        Context context = new Context();

        if (!CollectionUtils.isEmpty(variables)) {
            context.setVariables(variables);
        }

        return templateEngine.process(template, context);
    }

    @PostConstruct
    private void initStaticTemplateUtil () {
        templateEngine = this.templateEngineBean;
    }
}
