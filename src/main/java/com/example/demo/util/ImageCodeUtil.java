package com.example.demo.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Random;

public class ImageCodeUtil {

    public static ImageCode createImageCode(){
        int width=60;
        int height=30;

        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        Graphics graphics=image.getGraphics();
        graphics.setColor(getRandomColor());     //设置填充色
        graphics.fillRect(0,0,width,height);

        graphics.setColor(getRandomColor());     //设置随机条纹
        Random random=new Random();
        for (int i=0;i<100;i++){
            int x1=random.nextInt(width);
            int y1=random.nextInt(height);

            int x2=x1+random.nextInt(10);
            int y2=y1+random.nextInt(10);

            graphics.drawLine(x1,y1,x2,y2);
        }

        StringBuilder builder=new StringBuilder();  //设置随机数字
        for (int i=0;i<4;i++){
            String s=String.valueOf(random.nextInt(10));
            builder.append(s);

            graphics.setColor(new Color(10+random.nextInt(10*(i+1)),
                    10+random.nextInt(10*(i+1)),10+random.nextInt(10*(i+1))));
            graphics.drawString(s,12*i+5,20);
        }

        graphics.dispose();

        return new ImageCode(image,builder.toString(), LocalDateTime.now());
    }

    private static Color getRandomColor(){
        Random random=new Random();

        int red=Math.abs(random.nextInt())%50+200;
        int green=Math.abs(random.nextInt())%50+200;
        int black=Math.abs(random.nextInt())%50+200;

        return new Color(red,green,black);
    }
}
