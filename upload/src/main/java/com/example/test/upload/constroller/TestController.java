package com.example.test.upload.constroller;

import com.example.test.upload.util.FileUtils;
import com.example.test.upload.util.RSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

/**
 * @author ceshi
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/8/2721:38
 */
@Controller
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    //跳转到上传文件的页面
    @RequestMapping(value = "/gouploadimg", method = RequestMethod.GET)
    public String goUploadImg() {
        //跳转到 templates 目录下的 uploadimg.html
        return "uploadimg";
    }

    private static RSAPrivateKey rsaPrivateKey;

    @ResponseBody
    @RequestMapping(value = "test")
    public String test() {
        return "hello";
    }

    //处理文件上传
    @RequestMapping(value = "/testuploadimg", method = RequestMethod.POST)
    public @ResponseBody
    String uploadImg(@RequestParam("file") MultipartFile file,
                     HttpServletRequest request) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        /*System.out.println("fileName-->" + fileName);
        System.out.println("getContentType-->" + contentType);*/
//        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        String filePath = "C:\\Users\\FuN\\Desktop\\新建文件夹\\";
        try {
            FileUtils.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
        }
        //返回json
        return "uploadimg success";
    }

    /**
     * 　　* @Description: ${todo} login 页面
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author ceshi
     * 　　* @date 2018/8/29 16:07
     */
    @ResponseBody
    @RequestMapping("/login")
    public String login(HttpServletRequest request,
                        @RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "password", required = false) String password) {
        logger.info("----login-------");
        logger.info("username=" + username);
        logger.info("password=" + password);
        String decryptPassword = "";
        if (rsaPrivateKey != null && password != null) {
            try {
                decryptPassword = RSAUtils.decryptByPrivateKey(password, rsaPrivateKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("解密后密码是=" + decryptPassword);
        }
        String referer = request.getHeader("Referer");

        System.out.println("referer=" + referer);
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("username");
        if (user != null) {
            logger.info("have session");
            logger.info("session id=" + session.getId());
            logger.info("session getCreationTime=" + session.getCreationTime());
            logger.info("session getLastAccessedTime=" + session.getLastAccessedTime());
            logger.info("session ContextPath=" + session.getServletContext().getContextPath());
            return "login success";
        } else if (username != null && password != null && !"".equals(username) && !"".equals(password)) {
            logger.info("---------------写入session--------");
            session.setAttribute("username", username);
            return "login success";
        } else {

            return "login failure";
        }
    }

    @ResponseBody
    @RequestMapping("test1")
    public String test1(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        System.out.println("Referer=" + referer);
        return "a";
    }

    @RequestMapping("login-page")
    public ModelAndView loginPage(HttpServletRequest request) {
        logger.info("----------login page---------");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");

        try {
            HashMap<String, Object> map = RSAUtils.getKeys();
            RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
            RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
            logger.info("public key=" + publicKey);
            logger.info("private key =" + privateKey);
            rsaPrivateKey = privateKey;
            HttpSession session = request.getSession();
            session.setAttribute("privateKey", privateKey);
            String publicKeyExponent = publicKey.getPublicExponent().toString(16);
            String publicKeyModulus = publicKey.getModulus().toString(16);
            request.setAttribute("publicKeyExponent", publicKeyExponent);
            request.setAttribute("publicKeyModulus", publicKeyModulus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

}
