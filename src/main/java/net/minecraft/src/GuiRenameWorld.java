package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiRenameWorld extends GuiScreen
{

    public GuiRenameWorld(GuiScreen guiscreen, String s)
    {
        field_22112_a = guiscreen;
        field_22113_i = s;
    }

    public void updateScreen()
    {
        field_22114_h.func_22070_b();
    }

    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, stringtranslate.translateKey("selectWorld.renameButton")));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
        ISaveFormat isaveformat = mc.func_22004_c();
        WorldInfo worldinfo = isaveformat.func_22173_b(field_22113_i);
        String s = worldinfo.getWorldName();
        field_22114_h = new GuiDisableButton(fontRenderer, width / 2 - 100, 60, 200, 20, s);
        field_22114_h.field_22082_a = true;
        field_22114_h.func_22066_a(32);
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton.id == 1)
        {
            mc.displayGuiScreen(field_22112_a);
        } else
        if(guibutton.id == 0)
        {
            ISaveFormat isaveformat = mc.func_22004_c();
            isaveformat.func_22170_a(field_22113_i, field_22114_h.func_22071_a().trim());
            mc.displayGuiScreen(field_22112_a);
        }
    }

    protected void keyTyped(char c, int i)
    {
        field_22114_h.func_22072_a(c, i);
        ((GuiButton)controlList.get(0)).enabled = field_22114_h.func_22071_a().trim().length() > 0;
        if(c == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }
    }

    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        field_22114_h.func_22069_a(i, j, k);
    }

    public void drawScreen(int i, int j, float f)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        drawCenteredString(fontRenderer, stringtranslate.translateKey("selectWorld.renameTitle"), width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, stringtranslate.translateKey("selectWorld.enterName"), width / 2 - 100, 47, 0xa0a0a0);
        field_22114_h.func_22067_c();
        super.drawScreen(i, j, f);
    }

    private GuiScreen field_22112_a;
    private GuiDisableButton field_22114_h;
    private final String field_22113_i;
}
