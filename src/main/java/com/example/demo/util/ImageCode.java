package com.example.demo.util;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Data
public class ImageCode {

    private BufferedImage image;
    private String code;
    private LocalDateTime expireTime;

    public ImageCode(){

    }

    public ImageCode(BufferedImage image,String code,LocalDateTime expireTime){
        this.image=image;
        this.code=code;
        this.expireTime=expireTime;
    }

    public boolean isExpire(){
        return LocalDateTime.now().isAfter(expireTime.plusMinutes(1));
    }
}
