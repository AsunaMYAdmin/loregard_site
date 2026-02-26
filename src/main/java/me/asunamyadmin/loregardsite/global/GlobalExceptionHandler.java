package me.asunamyadmin.loregardsite.global;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler implements ErrorController {
    @RequestMapping("/error")
    public String error(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {

            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "404";
            }

            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "403";
            }
        }

        return "error";
    }

    @RequestMapping("/403")
    public String error403() {
        return "403";
    }
}
