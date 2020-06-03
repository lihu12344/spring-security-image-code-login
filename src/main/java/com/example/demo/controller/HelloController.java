package com.example.demo.controller;

import com.example.demo.util.ImageCode;
import com.example.demo.util.ImageCodeUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
public class HelloController {

    @RequestMapping("/image/code")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ImageCode imageCode= ImageCodeUtil.createImageCode();
        System.out.println(imageCode.getCode());

        request.getSession().setAttribute("image_code",imageCode);
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }

    @RequestMapping("/hello")
    public String hello(Principal principal){
        return "hello "+principal.getName();
    }
}
