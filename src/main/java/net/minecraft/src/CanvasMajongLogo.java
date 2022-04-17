package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class CanvasMajongLogo extends Canvas
{

    public CanvasMajongLogo()
    {
        try
        {
            logo = ImageIO.read(new File("resources/gui/logo.png"));
        }
        catch(IOException ioexception) { }
        byte byte0 = 100;
        setPreferredSize(new Dimension(byte0, byte0));
        setMinimumSize(new Dimension(byte0, byte0));
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawImage(logo, getWidth() / 2 - logo.getWidth() / 2, 32, null);
    }

    private BufferedImage logo;
}
