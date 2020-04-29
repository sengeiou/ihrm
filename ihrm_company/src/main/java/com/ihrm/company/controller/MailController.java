package com.ihrm.company.controller;

import com.ihrm.company.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;

@Api(tags = "邮件管理")
@RequestMapping("/mail")
@RestController
//@EnableAutoConfiguration
public class MailController {
    
    private String to = "1269810567@qq.com";

    @Autowired
    private MailService mailService;

//    @Autowired
//    private TemplateEngine templateEngine;

    @PostMapping("/test")
    @ApiOperation("发送简单邮件")
    public void testSimpleMail() throws Exception {
        mailService.sendSimpleMail(to,"简单邮件","这就是个测试：简单邮件发送");
    }

    @PostMapping("/html")
    @ApiOperation("发送html邮件")
    public void testHtmlMail() throws Exception {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>这是一封html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail(to,"html邮件",content);
    }

    @PostMapping("/txt")
    @ApiOperation("发送带附件邮件")
    public void sendAttachmentsMail() {
        String filePath="D:\\WX\\ihrm\\ihrm_parent\\log\\spring.log";
        mailService.sendAttachmentsMail(to, "有附件！", "有附件！有附件！有附件！", filePath);
    }

    @PostMapping("/img")
    @ApiOperation("发送带图片邮件")
    public void sendInlineResourceMail() {
        String rscId = "cid001"; // 注意这个id
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "D:\\WX\\ihrm\\ihrm_parent\\imge\\1.jpg";

        mailService.sendInlineResourceMail(to, "有图片！", content, imgPath, rscId);
    }

//    @PostMapping("/template")
//    @ApiOperation("发送带模板邮件")
//    public void sendTemplateMail() {
//        //创建邮件正文
//        Context context = new Context();
//        context.setVariable("id", "123456");
//        String emailContent = templateEngine.process("emailTemplate", context);
//
//        mailService.sendHtmlMail(to,"这是带模板邮件",emailContent);
//    }
}
