package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ScreenShotHelper
{

    public static String saveScreenshot(File file, int i, int j)
    {
        try
        {
            File file1 = new File(file, "screenshots");
            file1.mkdir();
            if(buffer == null || buffer.capacity() < i * j)
            {
                buffer = BufferUtils.createByteBuffer(i * j * 3);
            }
            if(imageData == null || imageData.length < i * j * 3)
            {
                pixelData = new byte[i * j * 3];
                imageData = new int[i * j];
            }
            GL11.glPixelStorei(3333 /*GL_PACK_ALIGNMENT*/, 1);
            GL11.glPixelStorei(3317 /*GL_UNPACK_ALIGNMENT*/, 1);
            buffer.clear();
            GL11.glReadPixels(0, 0, i, j, 6407 /*GL_RGB*/, 5121 /*GL_UNSIGNED_BYTE*/, buffer);
            buffer.clear();
            String s = (new StringBuilder()).append("").append(dateFormat.format(new Date())).toString();
            File file2;
            for(int k = 1; (file2 = new File(file1, (new StringBuilder()).append(s).append(k != 1 ? (new StringBuilder()).append("_").append(k).toString() : "").append(".png").toString())).exists(); k++) { }
            buffer.get(pixelData);
            for(int l = 0; l < i; l++)
            {
                for(int i1 = 0; i1 < j; i1++)
                {
                    int j1 = l + (j - i1 - 1) * i;
                    int k1 = pixelData[j1 * 3 + 0] & 0xff;
                    int l1 = pixelData[j1 * 3 + 1] & 0xff;
                    int i2 = pixelData[j1 * 3 + 2] & 0xff;
                    int j2 = 0xff000000 | k1 << 16 | l1 << 8 | i2;
                    imageData[l + i1 * i] = j2;
                }

            }

            BufferedImage bufferedimage = new BufferedImage(i, j, 1);
            bufferedimage.setRGB(0, 0, i, j, imageData, 0, i);
            ImageIO.write(bufferedimage, "png", file2);
            return (new StringBuilder()).append("Saved screenshot as ").append(file2.getName()).toString();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return (new StringBuilder()).append("Failed to save: ").append(exception).toString();
        }
    }

    public ScreenShotHelper(File file, int i, int j, int k)
    {
        field_21194_h = i;
        field_21193_i = j;
        field_21197_e = k;
        File file1 = new File(file, "screenshots");
        file1.mkdir();
        String s = (new StringBuilder()).append("huge_").append(dateFormat.format(new Date())).toString();
        for(int l = 1; (field_21192_j = new File(file1, (new StringBuilder()).append(s).append(l != 1 ? (new StringBuilder()).append("_").append(l).toString() : "").append(".tga").toString())).exists(); l++) { }
        byte abyte0[] = new byte[18];
        abyte0[2] = 2;
        abyte0[12] = (byte)(i % 256);
        abyte0[13] = (byte)(i / 256);
        abyte0[14] = (byte)(j % 256);
        abyte0[15] = (byte)(j / 256);
        abyte0[16] = 24;
        field_21195_g = new byte[i * k * 3];
        try {
        	field_21196_f = new DataOutputStream(new FileOutputStream(field_21192_j));
        	field_21196_f.write(abyte0);
        }catch(IOException e) {
        	e.printStackTrace();
        }
    }

    public void func_21189_a(ByteBuffer bytebuffer, int i, int j, int k, int l)
    {
        int i1 = k;
        int j1 = l;
        if(i1 > field_21194_h - i)
        {
            i1 = field_21194_h - i;
        }
        if(j1 > field_21193_i - j)
        {
            j1 = field_21193_i - j;
        }
        field_21197_e = j1;
        for(int k1 = 0; k1 < j1; k1++)
        {
            bytebuffer.position((l - j1) * k * 3 + k1 * k * 3);
            int l1 = (i + k1 * field_21194_h) * 3;
            bytebuffer.get(field_21195_g, l1, i1 * 3);
        }

    }

    public void func_21191_a()
    {
    	try {
    		field_21196_f.write(field_21195_g, 0, field_21194_h * 3 * field_21197_e);
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }

    public String func_21190_b()
    {
    	try {
    		field_21196_f.close();
    		return (new StringBuilder()).append("Saved screenshot as ").append(field_21192_j.getName()).toString();
    	}catch(IOException e) {
    		e.printStackTrace();
    		return "Failed to save screenshot: " + e.toString();
    	}
    }

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private static ByteBuffer buffer;
    private static byte pixelData[];
    private static int imageData[];
    private int field_21197_e;
    private DataOutputStream field_21196_f;
    private byte field_21195_g[];
    private int field_21194_h;
    private int field_21193_i;
    private File field_21192_j;

}
