package age.of.civilizations2.jakowski.lukasz;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.GdxRuntimeException;

class Keyboard extends SliderMenu
{
    private List<String> lKeys;
    private List<String> lKeysSHIFT;
    private List<String> lKeysNUM;
    private List<String> lKeys123;
    private int animationStepID;
    private int animationChangePosY;
    private boolean closeMenu;
    private long barTime;
    private boolean drawBar;
    protected static boolean shift;
    protected static boolean numbers;
    private int iTextWidth;
    private int iTextHeight;
    protected static boolean colorPickerMode;
    protected static int activeColor_RGB_ID;
    protected static boolean commandsMode;
    protected static int changeCivilizationNameMode;
    protected static int changeLeaderNameMode;
    protected static int changeProvinceNameMode;
    protected static int changeCityNameIDToo;
    
    protected Keyboard() {
        super();
        this.lKeys = new ArrayList<String>();
        this.lKeysSHIFT = new ArrayList<String>();
        this.lKeysNUM = new ArrayList<String>();
        this.lKeys123 = new ArrayList<String>();
        this.animationStepID = 0;
        this.closeMenu = false;
        final List<MenuElement> menuElements = new ArrayList<MenuElement>();
        this.lKeys.add("q");
        this.lKeys.add("w");
        this.lKeys.add("e");
        this.lKeys.add("r");
        this.lKeys.add("t");
        this.lKeys.add("y");
        this.lKeys.add("u");
        this.lKeys.add("i");
        this.lKeys.add("o");
        this.lKeys.add("p");
        this.lKeys.add("a");
        this.lKeys.add("s");
        this.lKeys.add("d");
        this.lKeys.add("f");
        this.lKeys.add("g");
        this.lKeys.add("h");
        this.lKeys.add("j");
        this.lKeys.add("k");
        this.lKeys.add("l");
        this.lKeys.add("z");
        this.lKeys.add("x");
        this.lKeys.add("c");
        this.lKeys.add("v");
        this.lKeys.add("b");
        this.lKeys.add("n");
        this.lKeys.add("m");
        this.lKeys.add("SH");
        this.lKeys.add("<<");
        this.lKeys.add("123");
        this.lKeys.add("Space");
        this.lKeys.add(",");
        this.lKeys.add(".");
        this.lKeysSHIFT.add("Q");
        this.lKeysSHIFT.add("W");
        this.lKeysSHIFT.add("E");
        this.lKeysSHIFT.add("R");
        this.lKeysSHIFT.add("T");
        this.lKeysSHIFT.add("Y");
        this.lKeysSHIFT.add("U");
        this.lKeysSHIFT.add("I");
        this.lKeysSHIFT.add("O");
        this.lKeysSHIFT.add("P");
        this.lKeysSHIFT.add("A");
        this.lKeysSHIFT.add("S");
        this.lKeysSHIFT.add("D");
        this.lKeysSHIFT.add("F");
        this.lKeysSHIFT.add("G");
        this.lKeysSHIFT.add("H");
        this.lKeysSHIFT.add("J");
        this.lKeysSHIFT.add("K");
        this.lKeysSHIFT.add("L");
        this.lKeysSHIFT.add("Z");
        this.lKeysSHIFT.add("X");
        this.lKeysSHIFT.add("C");
        this.lKeysSHIFT.add("V");
        this.lKeysSHIFT.add("B");
        this.lKeysSHIFT.add("N");
        this.lKeysSHIFT.add("M");
        this.lKeysNUM.add("1");
        this.lKeysNUM.add("2");
        this.lKeysNUM.add("3");
        this.lKeysNUM.add("4");
        this.lKeysNUM.add("5");
        this.lKeysNUM.add("6");
        this.lKeysNUM.add("7");
        this.lKeysNUM.add("8");
        this.lKeysNUM.add("9");
        this.lKeysNUM.add("0");
        this.lKeys123.add("@");
        this.lKeys123.add("*");
        this.lKeys123.add("#");
        this.lKeys123.add(":");
        this.lKeys123.add(";");
        this.lKeys123.add("&");
        this.lKeys123.add("_");
        this.lKeys123.add("(");
        this.lKeys123.add(")");
        this.lKeys123.add("-");
        this.lKeys123.add("+");
        this.lKeys123.add("'");
        this.lKeys123.add("\"");
        this.lKeys123.add("%");
        this.lKeys123.add("!");
        this.lKeys123.add("?");
        for (int i = 0; i < 10; ++i) {
            menuElements.add(new Button_Keyboard(this.lKeys.get(i), (Menu_InGame_FlagAction.getWindowWidth() - CFG.PADDING * 11) / 10 * i + CFG.PADDING * i, CFG.PADDING * 2 + CFG.PADDING * 2 + (int)(CFG.BUTTON_HEIGHT * 0.8f), (Menu_InGame_FlagAction.getWindowWidth() - CFG.PADDING * 11) / 10, CFG.BUTTON_HEIGHT, Button.TypeOfButton.KEYBOARD, true));
        }
        for (int i = 0; i < 10; ++i) {
            menuElements.get(i).setPosX(menuElements.get(i).getPosX() + (Menu_InGame_FlagAction.getWindowWidth() - menuElements.get(menuElements.size() - 1).getPosX() - menuElements.get(menuElements.size() - 1).getWidth()) / 2);
        }
        for (int i = 0; i < 9; ++i) {
            menuElements.add(new Button_Keyboard(this.lKeys.get(i + 10), menuElements.get(0).getWidth() * i + CFG.PADDING * i, CFG.PADDING * 2 + CFG.PADDING * 4 + CFG.BUTTON_HEIGHT + (int)(CFG.BUTTON_HEIGHT * 0.8f), menuElements.get(0).getWidth(), CFG.BUTTON_HEIGHT, Button.TypeOfButton.KEYBOARD, true));
        }
        for (int i = 10; i < 19; ++i) {
            menuElements.get(i).setPosX(menuElements.get(i).getPosX() + (Menu_InGame_FlagAction.getWindowWidth() - menuElements.get(menuElements.size() - 1).getPosX() - menuElements.get(menuElements.size() - 1).getWidth()) / 2);
        }
        for (int i = 0; i < 7; ++i) {
            menuElements.add(new Button_Keyboard(this.lKeys.get(i + 19), menuElements.get(0).getWidth() * i + CFG.PADDING * i, CFG.PADDING * 2 + CFG.PADDING * 6 + CFG.BUTTON_HEIGHT * 2 + (int)(CFG.BUTTON_HEIGHT * 0.8f), menuElements.get(0).getWidth(), CFG.BUTTON_HEIGHT, Button.TypeOfButton.KEYBOARD, true));
        }
        for (int i = 19; i < 26; ++i) {
            menuElements.get(i).setPosX(menuElements.get(i).getPosX() + (Menu_InGame_FlagAction.getWindowWidth() - menuElements.get(menuElements.size() - 1).getPosX() - menuElements.get(menuElements.size() - 1).getWidth()) / 2);
        }
        menuElements.add(new Button_Keyboard(this.lKeys.get(26), CFG.PADDING, CFG.PADDING * 2 + CFG.PADDING * 6 + CFG.BUTTON_HEIGHT * 2 + (int)(CFG.BUTTON_HEIGHT * 0.8f), menuElements.get(19).getPosX() - CFG.PADDING * 2, CFG.BUTTON_HEIGHT, Button.TypeOfButton.KEYBOARD_OPTIONS, true));
        menuElements.add(new Button_Keyboard(this.lKeys.get(27), menuElements.get(25).getPosX() + menuElements.get(25).getWidth() + CFG.PADDING, CFG.PADDING * 2 + CFG.PADDING * 6 + CFG.BUTTON_HEIGHT * 2 + (int)(CFG.BUTTON_HEIGHT * 0.8f), Menu_InGame_FlagAction.getWindowWidth() - menuElements.get(25).getPosX() - menuElements.get(25).getWidth() - CFG.PADDING * 2, CFG.BUTTON_HEIGHT, Button.TypeOfButton.KEYBOARD_OPTIONS, true));
        menuElements.add(new Button_Keyboard(this.lKeys.get(28), CFG.PADDING, CFG.PADDING * 2 + CFG.PADDING * 8 + CFG.BUTTON_HEIGHT * 3 + (int)(CFG.BUTTON_HEIGHT * 0.8f), menuElements.get(0).getWidth() * 2, (int)(CFG.BUTTON_HEIGHT * 0.8f), Button.TypeOfButton.KEYBOARD_OPTIONS, true));
        menuElements.add(new Button_Keyboard(this.lKeys.get(29), CFG.PADDING * 2 + menuElements.get(0).getWidth() * 2, CFG.PADDING * 2 + CFG.PADDING * 8 + CFG.BUTTON_HEIGHT * 3 + (int)(CFG.BUTTON_HEIGHT * 0.8f), Menu_InGame_FlagAction.getWindowWidth() - menuElements.get(0).getWidth() * 4 - CFG.PADDING * 5, (int)(CFG.BUTTON_HEIGHT * 0.8f), Button.TypeOfButton.KEYBOARD, true));
        menuElements.add(new Button_Keyboard(this.lKeys.get(30), CFG.PADDING * 3 + menuElements.get(0).getWidth() * 2 + Menu_InGame_FlagAction.getWindowWidth() - menuElements.get(0).getWidth() * 4 - CFG.PADDING * 5, CFG.PADDING * 2 + CFG.PADDING * 8 + CFG.BUTTON_HEIGHT * 3 + (int)(CFG.BUTTON_HEIGHT * 0.8f), menuElements.get(0).getWidth(), (int)(CFG.BUTTON_HEIGHT * 0.8f), Button.TypeOfButton.KEYBOARD_OPTIONS, true));
        menuElements.add(new Button_Keyboard(this.lKeys.get(31), CFG.PADDING * 4 + menuElements.get(0).getWidth() * 3 + Menu_InGame_FlagAction.getWindowWidth() - menuElements.get(0).getWidth() * 4 - CFG.PADDING * 5, CFG.PADDING * 2 + CFG.PADDING * 8 + CFG.BUTTON_HEIGHT * 3 + (int)(CFG.BUTTON_HEIGHT * 0.8f), menuElements.get(0).getWidth(), (int)(CFG.BUTTON_HEIGHT * 0.8f), Button.TypeOfButton.KEYBOARD_OPTIONS, true));
        menuElements.add(new Button_Keyboard(null, Menu_InGame_FlagAction.getWindowWidth() - menuElements.get(0).getWidth() * 2 - CFG.PADDING, CFG.PADDING, menuElements.get(0).getWidth() * 2, (int)(CFG.BUTTON_HEIGHT * 0.8f), Button.TypeOfButton.KEYBOARD_SAVE, true));
        menuElements.add(new Button_Keyboard(null, Menu_InGame_FlagAction.getWindowWidth() - menuElements.get(0).getWidth() * 3 - CFG.PADDING * 2, CFG.PADDING, menuElements.get(0).getWidth(), (int)(CFG.BUTTON_HEIGHT * 0.8f), Button.TypeOfButton.KEYBOARD_SAVE, true));
        menuElements.add(new Button_Keyboard(null, Menu_InGame_FlagAction.getWindowWidth() - menuElements.get(0).getWidth() * 4 - CFG.PADDING * 3, CFG.PADDING, menuElements.get(0).getWidth(), (int)(CFG.BUTTON_HEIGHT * 0.8f), Button.TypeOfButton.KEYBOARD_SAVE, true));
        menuElements.add(new Button_Keyboard(null, Menu_InGame_FlagAction.getWindowWidth() - menuElements.get(0).getWidth() * 5 - CFG.PADDING * 4, CFG.PADDING, menuElements.get(0).getWidth(), (int)(CFG.BUTTON_HEIGHT * 0.8f), Button.TypeOfButton.KEYBOARD_SAVE, true));
        this.initMenu(null, 0 + AoCGame.LEFT, CFG.GAME_HEIGHT - CFG.PADDING * 2 - menuElements.get(menuElements.size() - 5).getPosY() - menuElements.get(menuElements.size() - 5).getHeight(), Menu_InGame_FlagAction.getWindowWidth(), CFG.PADDING * 2 + menuElements.get(menuElements.size() - 5).getPosY() + menuElements.get(menuElements.size() - 5).getHeight(), menuElements, false, false);
        this.updateLanguage();
        CFG.updateKeyboard_Actions();
    }
    
    @Override
    protected void updateLanguage() {
        this.lKeys.set(26, CFG.langManager.get("Shift"));
        this.getMenuElement(26).setText(this.lKeys.get(26));
        this.getMenuElement(32).setText(CFG.langManager.get("Save"));
        this.getMenuElement(33).setText(CFG.langManager.get("Cut"));
        this.getMenuElement(34).setText(CFG.langManager.get("Paste"));
        this.getMenuElement(35).setText(CFG.langManager.get("Copy"));
    }
    
    @Override
    protected final void draw(final SpriteBatch oSB, final int iTranslateX, final boolean sliderMenuIsActive) {
        this.updateChangePosY();
        oSB.setColor(Color.WHITE);
        ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX(), this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + this.animationChangePosY, this.getWidth() - ImageManager.getImage(Images.new_game_top_edge_line).getWidth(), this.getHeight());
        ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.new_game_top_edge_line).getWidth(), this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + this.animationChangePosY, ImageManager.getImage(Images.new_game_top_edge_line).getWidth(), this.getHeight(), true, false);
        oSB.setColor(new Color(0.025f, 0.03f, 0.092f, 0.4f));
        ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX(), this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + this.animationChangePosY + this.getMenuElement(32).getHeight() + CFG.PADDING * 2, this.getWidth(), this.getHeight() - this.getMenuElement(32).getHeight() - CFG.PADDING * 2);
        oSB.setColor(new Color(0.14901961f, 0.1764706f, 0.21568628f, 0.65f));
        ImageManager.getImage(Images.patt).draw2(oSB, this.getPosX(), this.getPosY() - ImageManager.getImage(Images.patt).getHeight() + this.animationChangePosY + this.getMenuElement(32).getHeight() + CFG.PADDING * 2, this.getWidth(), this.getHeight() - this.getMenuElement(32).getHeight() - CFG.PADDING * 2);
        oSB.setColor(Color.WHITE);
        oSB.setColor(new Color(CFG.COLOR_INFO_BOX_GRADIENT.r, CFG.COLOR_INFO_BOX_GRADIENT.g, CFG.COLOR_INFO_BOX_GRADIENT.b, 0.28f));
        ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX(), this.getPosY() + this.animationChangePosY + 2 - ImageManager.getImage(Images.slider_gradient).getHeight(), this.getWidth(), this.getMenuElement(32).getHeight() + CFG.PADDING * 2 - 4);
        oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.425f));
        ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + 2, this.getPosY() + this.animationChangePosY + 2 - ImageManager.getImage(Images.gradient).getHeight(), this.getWidth() - 4, CFG.PADDING * 2);
        ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + 2, this.getPosY() + this.getMenuElement(32).getHeight() + CFG.PADDING * 2 - 4 - CFG.PADDING * 2 + this.animationChangePosY + 2 - ImageManager.getImage(Images.gradient).getHeight(), this.getWidth() - 4, CFG.PADDING * 2, false, true);
        oSB.setColor(new Color(CFG.COLOR_NEW_GAME_EDGE_LINE.r, CFG.COLOR_NEW_GAME_EDGE_LINE.g, CFG.COLOR_NEW_GAME_EDGE_LINE.b, 1.0f));
        ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX(), this.getPosY() + this.animationChangePosY - ImageManager.getImage(Images.pix255_255_255).getHeight() + 1, this.getWidth(), 1);
        ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + 2, this.getPosY() + this.animationChangePosY - ImageManager.getImage(Images.pix255_255_255).getHeight() + this.getMenuElement(32).getHeight() + CFG.PADDING * 2 - 2, this.getWidth() - 4, 1);
        oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.65f));
        ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX(), this.getPosY() + this.animationChangePosY - ImageManager.getImage(Images.line_32_off1).getHeight() + 1, this.getWidth(), 1);
        oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.75f));
        ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX(), this.getPosY() + this.animationChangePosY - ImageManager.getImage(Images.line_32_off1).getHeight() + this.getMenuElement(32).getHeight() + CFG.PADDING * 2 - 2, this.getWidth(), 1);
        oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.4f));
        ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + this.getWidth() / 4, this.getPosY() + this.animationChangePosY - ImageManager.getImage(Images.line_32_off1).getHeight() + this.getMenuElement(32).getHeight() + CFG.PADDING * 2 - 2, this.getWidth() / 2, 1);
        oSB.setColor(Color.WHITE);
        this.drawMenuElements(oSB, 0, this.animationChangePosY, sliderMenuIsActive);
        CFG.drawText(oSB, CFG.keyboardMessage, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getMenuElement(this.getMenuElementsSize() - 4).getPosY() + this.getMenuElement(this.getMenuElementsSize() - 4).getHeight() / 2 - this.iTextHeight / 2 + this.animationChangePosY + this.getPosY(), new Color(0.8156863f, 0.67058825f, 0.44313726f, 1.0f));
        if (this.barTime + (this.drawBar ? 700 : 650) < System.currentTimeMillis()) {
            this.drawBar = !this.drawBar;
            this.barTime = System.currentTimeMillis();
            CFG.setRender_3(true);
        }
        if (this.drawBar) {
            CFG.drawText(oSB, "|", this.getPosX() + CFG.PADDING * 2 + this.iTextWidth + iTranslateX, this.getMenuElement(this.getMenuElementsSize() - 4).getPosY() + this.getMenuElement(this.getMenuElementsSize() - 4).getHeight() / 2 - this.iTextHeight / 2 + this.animationChangePosY + this.getPosY(), Color.WHITE);
        }
    }
    
    @Override
    protected final void actionElement(final int iID) {
        switch (iID) {
            case 26: {
                this.shiftAction();
                return;
            }
            case 27: {
                CFG.keyboardDelete.action();
                break;
            }
            case 28: {
                if (Keyboard.activeColor_RGB_ID < 0) {
                    Keyboard.numbers = !Keyboard.numbers;
                    this.actionClose();
                }
                return;
            }
            case 29: {
                if (!CFG.keyboardMessage.isEmpty() && CFG.keyboardMessage.charAt(CFG.keyboardMessage.length() - 1) != ' ') {
                    CFG.keyboardWrite.action(" ");
                    break;
                }
                break;
            }
            case 32: {
                CFG.keyboardSave.action();
                this.closeMenu();
                Keyboard.activeColor_RGB_ID = -1;
                return;
            }
            case 33: {
                try {
                    Gdx.app.log("AoC", "Cut from clipboard: " + CFG.keyboardMessage);
                    Gdx.app.getClipboard().setContents(CFG.keyboardMessage);
                    CFG.keyboardMessage = "";
                    CFG.keyboardDelete.action();
                    CFG.menuManager.getKeyboard().onMenuPressed();
                    CFG.toast.setInView(CFG.langManager.get("CutToClipboard") + "!");
                } catch (GdxRuntimeException ex) {

                }
                return;
            }
            case 34: {
                try {
                    Gdx.app.log("AoC", "Pasted from clipboard: " + Gdx.app.getClipboard().getContents());
                    CFG.keyboardWrite.action("" + Gdx.app.getClipboard().getContents());
                    CFG.menuManager.getKeyboard().onMenuPressed();
                    CFG.toast.setInView(CFG.langManager.get("PastedFromClipboard") + "!");
                } catch (GdxRuntimeException ex) {

                }
                return;
            }
            case 35: {
                try {
                    Gdx.app.log("AoC", "Copied from clipboard: " + CFG.keyboardMessage);
                    Gdx.app.getClipboard().setContents(CFG.keyboardMessage);
                    CFG.toast.setInView(CFG.langManager.get("CopiedToClipboard") + "!");
                } catch (GdxRuntimeException ex) {

                }
                return;
            }
            default: {
                if (Keyboard.shift && iID < 26) {
                    if (Keyboard.numbers) {
                        if (iID < 10) {
                            this.writeNumber(iID);
                            break;
                        }
                        if (Keyboard.activeColor_RGB_ID < 0) {
                            CFG.keyboardWrite.action(this.lKeys123.get(iID - 10));
                            break;
                        }
                    }
                    else {
                        if (Keyboard.activeColor_RGB_ID >= 0) {
                            break;
                        }
                        CFG.keyboardWrite.action(this.lKeysSHIFT.get(iID));
                        if (Keyboard.shift) {
                            this.shiftAction();
                            break;
                        }
                    }
                    break;
                }
                else if (Keyboard.numbers) {
                    if (iID < 10) {
                        this.writeNumber(iID);
                        break;
                    }
                    if (Keyboard.activeColor_RGB_ID < 0) {
                        CFG.keyboardWrite.action(this.lKeys123.get(iID - 10));
                        break;
                    }
                    break;
                }
                else {
                    if (Keyboard.activeColor_RGB_ID >= 0) {
                        break;
                    }
                    CFG.keyboardWrite.action(this.lKeys.get(iID));
                    if (Keyboard.shift) {
                        this.shiftAction();
                        break;
                    }
                    break;
                }
            }
        }
        CFG.glyphLayout.setText(CFG.fontMain, CFG.keyboardMessage);
        this.iTextWidth = (int)CFG.glyphLayout.width;
        this.iTextHeight = (int)CFG.glyphLayout.height;
        this.barTime = System.currentTimeMillis();
        this.drawBar = true;
    }
    
    protected final void shiftAction() {
        Keyboard.shift = !Keyboard.shift;
        if (Keyboard.numbers) {
            Keyboard.numbers = false;
            this.actionClose();
        }
        this.getMenuElement(26).setTypeOfButton(Keyboard.shift ? Button.TypeOfButton.KEYBOARD_ACTIVE : Button.TypeOfButton.KEYBOARD_OPTIONS);
        if (Keyboard.shift) {
            for (int i = Keyboard.numbers ? this.lKeysNUM.size() : 0; i < this.lKeysSHIFT.size(); ++i) {
                this.getMenuElement(i).setText(this.lKeysSHIFT.get(i));
            }
        }
        else {
            for (int i = Keyboard.numbers ? this.lKeysNUM.size() : 0; i < this.lKeysSHIFT.size(); ++i) {
                this.getMenuElement(i).setText(this.lKeys.get(i));
            }
        }
    }
    
    private final void writeNumber(final int iID) {
        CFG.keyboardWrite.action(this.lKeysNUM.get(iID));
    }
    
    @Override
    protected final void onBackPressed() {
        this.closeMenu();
    }
    
    @Override
    protected void actionClose() {
        this.getMenuElement(28).setTypeOfButton(Keyboard.numbers ? Button.TypeOfButton.KEYBOARD_ACTIVE : Button.TypeOfButton.KEYBOARD_OPTIONS);
        if (Keyboard.numbers) {
            for (int i = 0; i < this.lKeysNUM.size(); ++i) {
                this.getMenuElement(i).setText(this.lKeysNUM.get(i));
                this.getMenuElement(i).setTypeOfButton(Button.TypeOfButton.KEYBOARD_NUM);
            }
            int i = 0;
            final int keysNum = this.lKeysNUM.size();
            while (i < this.lKeys123.size()) {
                this.getMenuElement(keysNum + i).setText(this.lKeys123.get(i));
                ++i;
            }
        }
        else {
            for (int i = 0; i < this.lKeysNUM.size(); ++i) {
                this.getMenuElement(i).setText(Keyboard.shift ? this.lKeysSHIFT.get(i) : this.lKeys.get(i));
                this.getMenuElement(i).setTypeOfButton(Button.TypeOfButton.KEYBOARD);
            }
            for (int i = this.lKeysNUM.size(); i < this.lKeysNUM.size() + this.lKeys123.size(); ++i) {
                this.getMenuElement(i).setText(Keyboard.shift ? this.lKeysSHIFT.get(i) : this.lKeys.get(i));
                this.getMenuElement(i).setTypeOfButton(Button.TypeOfButton.KEYBOARD);
            }
        }
    }
    
    private final void updateChangePosY() {
        switch (this.animationStepID) {
            case 0:
            case 1:
            case 12: {
                this.animationChangePosY -= (int)(this.getHeight() * 2.5f / 100.0f * (this.closeMenu ? -1 : 1));
                break;
            }
            case 2:
            case 3:
            case 10:
            case 11: {
                this.animationChangePosY -= (int)(this.getHeight() * 5.0f / 100.0f * (this.closeMenu ? -1 : 1));
                break;
            }
            case 4:
            case 5:
            case 8:
            case 9: {
                this.animationChangePosY -= (int)(this.getHeight() * 10.0f / 100.0f * (this.closeMenu ? -1 : 1));
                break;
            }
            case 6:
            case 7: {
                this.animationChangePosY -= (int)(this.getHeight() * 15.0f / 100.0f * (this.closeMenu ? -1 : 1));
                break;
            }
            case 13: {
                this.animationChangePosY = 0;
                break;
            }
        }
        if (CFG.iNumOfFPS < 22) {
            this.animationStepID = 13;
            this.animationChangePosY = 0;
        }
        if (this.closeMenu && this.animationStepID == 13) {
            this.animationChangePosY = this.getHeight();
            super.setVisible(false);
        }
        ++this.animationStepID;
        CFG.setRender_3(true);
    }
    
    protected final void closeMenu() {
        this.closeMenu = true;
        this.resetAnimation();
    }
    
    @Override
    protected void onMenuPressed() {
        CFG.glyphLayout.setText(CFG.fontMain, CFG.keyboardMessage);
        this.iTextWidth = (int)CFG.glyphLayout.width;
    }
    
    @Override
    protected final void setVisible(final boolean visible) {
        if (visible) {
            CFG.glyphLayout.setText(CFG.fontMain, CFG.keyboardMessage);
            this.iTextWidth = (int)CFG.glyphLayout.width;
            this.iTextHeight = (int)CFG.glyphLayout.height;
            this.barTime = System.currentTimeMillis();
            this.drawBar = true;
            super.setVisible(visible);
        }
        this.closeMenu = !visible;
        this.resetAnimation();
    }
    
    private final void resetAnimation() {
        this.animationStepID = 0;
        if (!this.closeMenu) {
            this.animationChangePosY = this.getHeight();
        }
    }
    
    static {
        Keyboard.shift = false;
        Keyboard.numbers = false;
        Keyboard.colorPickerMode = false;
        Keyboard.activeColor_RGB_ID = -1;
        Keyboard.commandsMode = false;
        Keyboard.changeCivilizationNameMode = 0;
        Keyboard.changeLeaderNameMode = -1;
        Keyboard.changeProvinceNameMode = -1;
        Keyboard.changeCityNameIDToo = -1;
    }
}
