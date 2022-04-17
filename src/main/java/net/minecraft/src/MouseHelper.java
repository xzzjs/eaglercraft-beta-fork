package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.awt.Component;
import java.nio.IntBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

public class MouseHelper
{

    public MouseHelper(Component component)
    {
        field_1115_e = 10;
        field_1117_c = component;
        IntBuffer intbuffer = GLAllocation.createDirectIntBuffer(1);
        intbuffer.put(0);
        intbuffer.flip();
        IntBuffer intbuffer1 = GLAllocation.createDirectIntBuffer(1024);
        try
        {
            field_1116_d = new Cursor(32, 32, 16, 16, 1, intbuffer1, intbuffer);
        }
        catch(LWJGLException lwjglexception)
        {
            lwjglexception.printStackTrace();
        }
    }

    public void func_774_a()
    {
        Mouse.setGrabbed(true);
        deltaX = 0;
        deltaY = 0;
    }

    public void func_773_b()
    {
        Mouse.setCursorPosition(field_1117_c.getWidth() / 2, field_1117_c.getHeight() / 2);
        Mouse.setGrabbed(false);
    }

    public void mouseXYChange()
    {
        deltaX = Mouse.getDX();
        deltaY = Mouse.getDY();
    }

    private Component field_1117_c;
    private Cursor field_1116_d;
    public int deltaX;
    public int deltaY;
    private int field_1115_e;
}
