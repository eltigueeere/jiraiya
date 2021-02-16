package com.mx.jiraiya.controller;

import com.mx.jiraiya.common.GenericServiceResponse;
import com.mx.jiraiya.common.dictionary.ResponseCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController {

    private static Logger log = LogManager.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    @ResponseBody
    public String handleError(HttpServletRequest request, HttpServletResponse responseService,Exception ex) {
        GenericServiceResponse response = new GenericServiceResponse();
        try {

            Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

            log.debug("Status Error Response: [" + statusCode + "]");

                if (statusCode.equals(400)) {
                    response.setCode(ResponseCode.ERROR_400.code);
                    response.setMessage(ResponseCode.ERROR_400.desc);
                } else if (statusCode.equals(401)) {
                    response.setCode(ResponseCode.ERROR_401.code);
                    response.setMessage(ResponseCode.ERROR_401.desc);
                } else if (statusCode.equals(403)) {
                    response.setCode(ResponseCode.ERROR_403.code);
                    response.setMessage(ResponseCode.ERROR_403.desc);
                } else if (statusCode.equals(404)) {
                    response.setCode(ResponseCode.ERROR_404.code);
                    response.setMessage(ResponseCode.ERROR_404.desc);
                } else if (statusCode.equals(405)) {
                    response.setCode(ResponseCode.ERROR_405.code);
                    response.setMessage(ResponseCode.ERROR_405.desc);
                } else if (statusCode.equals(500)) {
                    response.setCode(ResponseCode.ERROR_500.code);
                    response.setMessage(ResponseCode.ERROR_500.desc);
                } else {
                    response.setCode(ResponseCode.OTHER_ERROR.code);
                    response.setMessage(ResponseCode.OTHER_ERROR.desc);
                }

        }catch (Exception e){
            log.error("handleError: " + e.getMessage());
            response.setCode(ResponseCode.ERROR_500.code);
            response.setMessage(ResponseCode.ERROR_500.desc);
        }

        return response.toString();

    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}