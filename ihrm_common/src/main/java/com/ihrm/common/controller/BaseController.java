package com.ihrm.common.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String companyId;
    protected String companyName;

    @ModelAttribute
    public void serResAndReq(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        /**
         * 目前使用companyName=1
         * company Name=“万息科技有限公司”
         */
        this.companyId = "1";

        this.companyName = "万息科技有限公司";
    }
}
