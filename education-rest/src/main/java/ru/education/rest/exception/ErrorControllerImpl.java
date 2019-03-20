package ru.education.rest.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestController
public class ErrorControllerImpl implements ErrorController {

    private static final String PATH = "/error";
    private ErrorAttributes errorAttributes;

    @Autowired
    public ErrorControllerImpl(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = PATH)
    public String error(WebRequest request) {
        Map<String, Object> m = errorAttributes.getErrorAttributes(request, true);
        return m.get("message").toString();
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
