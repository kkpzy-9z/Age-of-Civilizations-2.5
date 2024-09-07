/*
 * Decompiled with CFR 0.152.
 */
package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Menu_CreateCiv_Flag
extends SliderMenu {
    protected Menu_CreateCiv_Flag() {
        int i;
        int tempW = CFG.CIV_INFO_MENU_WIDTH + CFG.CIV_INFO_MENU_WIDTH * 3 / 4;
        int tempH = ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4;
        int tPosY = CFG.PADDING;
        ArrayList<MenuElement> menuElements = new ArrayList<MenuElement>();
        menuElements.add(new Button_NewGameStyle(null, -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6f), true));
        menuElements.add(new Button_NewGameStyle(null, -1, CFG.PADDING, tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, tempW - CFG.PADDING * 2, true));
        menuElements.add(new Button_NewGameStyle(null, -1, CFG.PADDING, tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, tempW - CFG.PADDING * 2, true));
        menuElements.add(new Button_NewGameStyle_Middle("", 0, CFG.PADDING + CFG.BUTTON_HEIGHT, tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, tempW - CFG.PADDING * 2 - CFG.BUTTON_HEIGHT * 2, Math.max(ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6f)), true){

            @Override
            protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                oSB.setColor(new Color(CFG.flagManager.flagEdit.lDivisionColors.get(0).getR(), CFG.flagManager.flagEdit.lDivisionColors.get(0).getG(), CFG.flagManager.flagEdit.lDivisionColors.get(0).getB(), 1.0f));
                ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.top_flag_frame).getWidth() / 2 - CFG.PADDING - 1 + iTranslateX, this.getPosY() + CFG.PADDING - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, 1, (this.getHeight() - CFG.PADDING * 2) / 2, false, true);
                ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.top_flag_frame).getWidth() / 2 - CFG.PADDING - 1 + iTranslateX, this.getPosY() + CFG.PADDING - ImageManager.getImage(Images.gradient).getHeight() + (this.getHeight() - CFG.PADDING * 2) / 2 + iTranslateY, 1, (this.getHeight() - CFG.PADDING * 2) / 2);
                oSB.setColor(Color.WHITE);
                CFG.flagManager.drawDivision_FlagFrameSize(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.top_flag_frame).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.top_flag_frame).getHeight() / 2 + iTranslateY);
            }
        });
        menuElements.add(new Button_NewGameStyle_Left("<<", -1, CFG.PADDING, tPosY, CFG.BUTTON_HEIGHT, Math.max(ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6f)), true){

            @Override
            protected void buildElementHover() {
                ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Previous"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
                this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
        });
        menuElements.add(new Button_NewGameStyle_Right(">>", -1, tempW - CFG.PADDING - CFG.BUTTON_HEIGHT, tPosY, CFG.BUTTON_HEIGHT, Math.max(ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6f)), true){

            @Override
            protected void buildElementHover() {
                ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Next"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
                this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
        });
        tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        for (i = 1; i < CFG.flagManager.lDivisions.get((int)CFG.flagManager.flagEdit.iDivisionID).iLayers; ++i) {
            menuElements.add(new Button_NewGameStyle("", -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, Math.max(ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6f)), true){
                int iCurrent;

                @Override
                protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                    oSB.setColor(new Color(CFG.flagManager.flagEdit.lDivisionColors.get(this.iCurrent).getR(), CFG.flagManager.flagEdit.lDivisionColors.get(this.iCurrent).getG(), CFG.flagManager.flagEdit.lDivisionColors.get(this.iCurrent).getB(), 1.0f));
                    ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.top_flag_frame).getWidth() / 2 - CFG.PADDING - 1 + iTranslateX, this.getPosY() + CFG.PADDING - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, 1, (this.getHeight() - CFG.PADDING * 2) / 2, false, true);
                    ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.top_flag_frame).getWidth() / 2 - CFG.PADDING - 1 + iTranslateX, this.getPosY() + CFG.PADDING - ImageManager.getImage(Images.gradient).getHeight() + (this.getHeight() - CFG.PADDING * 2) / 2 + iTranslateY, 1, (this.getHeight() - CFG.PADDING * 2) / 2);
                    oSB.setColor(Color.WHITE);
                    CFG.flagManager.drawDivision_FlagFrameSize(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.top_flag_frame).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.top_flag_frame).getHeight() / 2 + iTranslateY, this.iCurrent);
                }

                @Override
                protected void setCurrent(int nCurrent) {
                    this.iCurrent = nCurrent;
                }

                @Override
                protected void buildElementHover() {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PickColor"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    this.menuElementHover = new MenuElement_Hover_v2(nElements);
                }
            });
            tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(i);
        }
        for (i = 0; i < CFG.flagManager.flagEdit.lOverlays.size(); ++i) {
            menuElements.add(new Button_NewGameStyle_Left("", 0, CFG.PADDING, tPosY, CFG.BUTTON_HEIGHT, Math.max(ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6f)), true){

                @Override
                protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                    if (isActive || this.getIsHovered()) {
                        oSB.setColor(new Color(1.0f, 1.0f, 1.0f, 0.65f));
                    }
                    ImageManager.getImage(Images.pickeIcon).draw(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.btn_up).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.pickeIcon).getHeight() - ImageManager.getImage(Images.btn_up).getWidth() / 2 + iTranslateY, ImageManager.getImage(Images.btn_up).getWidth(), ImageManager.getImage(Images.btn_up).getWidth());
                    oSB.setColor(Color.WHITE);
                }

                @Override
                protected void buildElementHover() {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PickColor"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    this.menuElementHover = new MenuElement_Hover_v2(nElements);
                }
            });
            menuElements.add(new Button_NewGameStyle_Middle("", 0, CFG.PADDING + CFG.BUTTON_HEIGHT, tPosY, tempW - CFG.PADDING * 2 - CFG.BUTTON_HEIGHT * 3, Math.max(ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6f)), true){
                private int iCurrent;

                @Override
                protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                    CFG.flagManager.drawDivision_FlagFrameSize(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.top_flag_frame).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.top_flag_frame).getHeight() / 2 + iTranslateY);
                    CFG.flagManager.drawOverlay_FlagFrameSize(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.top_flag_frame).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.top_flag_frame).getHeight() / 2 + iTranslateY, this.iCurrent);
                }

                @Override
                protected void setCurrent(int nCurrent) {
                    this.iCurrent = nCurrent;
                }
            });
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(i);
            menuElements.add(new Button_NewGameStyle_Middle("", 0, tempW - CFG.PADDING - CFG.BUTTON_HEIGHT * 2, tPosY, CFG.BUTTON_HEIGHT, Math.max(ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6f)), true){

                @Override
                protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                    if (isActive || this.getIsHovered()) {
                        oSB.setColor(new Color(1.0f, 1.0f, 1.0f, 0.65f));
                    }
                    ImageManager.getImage(Images.btn_up).draw(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.btn_up).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.btn_up).getHeight() / 2 + iTranslateY);
                    oSB.setColor(Color.WHITE);
                }
            });
            menuElements.add(new Button_NewGameStyle_Right("", 0, tempW - CFG.PADDING - CFG.BUTTON_HEIGHT, tPosY, CFG.BUTTON_HEIGHT, Math.max(ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6f)), true){

                @Override
                protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                    if (isActive || this.getIsHovered()) {
                        oSB.setColor(new Color(1.0f, 1.0f, 1.0f, 0.65f));
                    }
                    ImageManager.getImage(Images.btn_remove).draw(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.btn_remove).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.btn_remove).getHeight() / 2 + iTranslateY);
                    oSB.setColor(Color.WHITE);
                }

                @Override
                protected void buildElementHover() {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Remove"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    this.menuElementHover = new MenuElement_Hover_v2(nElements);
                }
            });
            tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        }
        this.initMenu(null, 0 + AoCGame.LEFT, CFG.BUTTON_HEIGHT / 2 + (ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4), tempW, Math.min(tPosY + CFG.PADDING, CFG.GAME_HEIGHT - (CFG.BUTTON_HEIGHT / 2 + (ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4) + CFG.PADDING)), menuElements);
        this.setVisible(false);
        this.updateLanguage();
    }

    @Override
    protected void updateLanguage() {
        this.getMenuElement(0).setText(CFG.langManager.get("Back"));
        this.getMenuElement(1).setText(CFG.langManager.get("Lord") + " " + CFG.langManager.get("Flag"));
        this.getMenuElement(2).setText(CFG.langManager.get("AddNewOverlay"));
    }

    @Override
    protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
        ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight(), this.getWidth() + 2, this.getHeight(), true, false);
        oSB.setColor(new Color(0.011f, 0.014f, 0.019f, 0.25f));
        ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight(), this.getWidth() * 3 / 4, this.getHeight(), false, true);
        oSB.setColor(Color.WHITE);
        super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        super.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        super.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        oSB.setColor(new Color(0.451f, 0.329f, 0.11f, 1.0f));
        ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - ImageManager.getImage(Images.pix255_255_255).getHeight(), this.getWidth());
        oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.4f));
        ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.slider_gradient).getHeight(), this.getWidth(), 1);
        oSB.setColor(Color.WHITE);
    }

    @Override
    protected final void actionElement(int iID) {
        iID -= 1;
        if (iID > 4 && iID <= 4 + CFG.flagManager.lDivisions.get((int)CFG.flagManager.flagEdit.iDivisionID).iLayers - 1) {
            if (CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID == iID - 5 + 1 && CFG.menuManager.getColorPicker().getVisible()) {
                CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = -1;
                CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID = -1;
                CFG.menuManager.getColorPicker().setVisible(false, null);
            } else {
                CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = iID - 5 + 1;
                CFG.menuManager.getColorPicker().setActiveRGBColor(CFG.flagManager.flagEdit.lDivisionColors.get(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).getR(), CFG.flagManager.flagEdit.lDivisionColors.get(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).getG(), CFG.flagManager.flagEdit.lDivisionColors.get(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).getB());
                CFG.menuManager.getColorPicker().setVisible(true, ColorPicker_AoC.PickerAction.EDITOR_CIV_FLAG_DIVISION_COLOR);
            }
        } else if (iID > 4 + CFG.flagManager.lDivisions.get((int)CFG.flagManager.flagEdit.iDivisionID).iLayers - 1) {
            if ((iID - 5 - CFG.flagManager.lDivisions.get((int)CFG.flagManager.flagEdit.iDivisionID).iLayers + 1) % 4 == 0) {
                if (CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID == (iID - 5 - CFG.flagManager.lDivisions.get((int)CFG.flagManager.flagEdit.iDivisionID).iLayers + 1) / 4 && CFG.menuManager.getColorPicker().getVisible()) {
                    CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID = -1;
                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = -1;
                    CFG.menuManager.getColorPicker().setVisible(false, null);
                } else {
                    CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID = (iID - 5 - CFG.flagManager.lDivisions.get((int)CFG.flagManager.flagEdit.iDivisionID).iLayers + 1) / 4;
                    CFG.menuManager.getColorPicker().setActiveRGBColor(CFG.flagManager.flagEdit.lOverlays.get((int)CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID).oColor.getR(), CFG.flagManager.flagEdit.lOverlays.get((int)CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID).oColor.getG(), CFG.flagManager.flagEdit.lOverlays.get((int)CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID).oColor.getB());
                    CFG.menuManager.getColorPicker().setVisible(true, ColorPicker_AoC.PickerAction.EDITOR_CIV_FLAG_OVERLAY_COLOR);
                }
            } else if ((iID - 5 - CFG.flagManager.lDivisions.get((int)CFG.flagManager.flagEdit.iDivisionID).iLayers + 1) % 4 == 1) {
                CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID = (iID - 5 - CFG.flagManager.lDivisions.get((int)CFG.flagManager.flagEdit.iDivisionID).iLayers + 1) / 4;
                CFG.menuManager.getColorPicker().setVisible(false, null);
                CFG.menuManager.setVisibleCreateCiv_Overlay(true);
                CFG.menuManager.setVisibleCreateCiv_Flag(false);
            } else if ((iID - 5 - CFG.flagManager.lDivisions.get((int)CFG.flagManager.flagEdit.iDivisionID).iLayers + 1) % 4 == 2) {
                CFG.flagManager.moveOverlayUp((iID - 5 - CFG.flagManager.lDivisions.get((int)CFG.flagManager.flagEdit.iDivisionID).iLayers + 1) / 4);
                CFG.menuManager.rebuildCreateCiv_Flag();
                CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID = -1;
                CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = -1;
                CFG.menuManager.getColorPicker().setVisible(false, null);
            } else if ((iID - 5 - CFG.flagManager.lDivisions.get((int)CFG.flagManager.flagEdit.iDivisionID).iLayers + 1) % 4 == 3) {
                CFG.flagManager.removeOverlay((iID - 5 - CFG.flagManager.lDivisions.get((int)CFG.flagManager.flagEdit.iDivisionID).iLayers + 1) / 4);
                CFG.menuManager.rebuildCreateCiv_Flag();
                CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID = -1;
                CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = -1;
                CFG.menuManager.getColorPicker().setVisible(false, null);
            }
        }
        switch (iID) {
            case -1: {
                CFG.menuManager.setVisibleCreateCiv_Data(true);
                CFG.menuManager.setVisibleCreateCiv_Flag(false);
                CFG.menuManager.getColorPicker().setVisible(false, null);
                return;
            }
            case 0: {
                CFG.flagManager.toggleLordOverlay(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                CFG.menuManager.rebuildCreateCiv_Flag();
                return;
            }
            case 1: {
                CFG.flagManager.addOverlay();
                CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID = CFG.flagManager.flagEdit.lOverlays.size() - 1;
                CFG.menuManager.setVisibleCreateCiv_Overlay(true);
                CFG.menuManager.setVisibleCreateCiv_Flag(false);
                return;
            }
            case 2: {
                if (CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID == 0 && CFG.menuManager.getColorPicker().getVisible()) {
                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = -1;
                    CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID = -1;
                    CFG.menuManager.getColorPicker().setVisible(false, null);
                } else {
                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = 0;
                    CFG.menuManager.getColorPicker().setActiveRGBColor(CFG.flagManager.flagEdit.lDivisionColors.get(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).getR(), CFG.flagManager.flagEdit.lDivisionColors.get(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).getG(), CFG.flagManager.flagEdit.lDivisionColors.get(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).getB());
                    CFG.menuManager.getColorPicker().setVisible(true, ColorPicker_AoC.PickerAction.EDITOR_CIV_FLAG_DIVISION_COLOR);
                }
                CFG.toast.setInView("ID: [" + CFG.flagManager.flagEdit.iDivisionID + "/" + (CFG.flagManager.lDivisions.size() - 1) + "]", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE);
                return;
            }
            case 3: {
                CFG.flagManager.updateDivision(false);
                CFG.menuManager.rebuildCreateCiv_Flag();
                CFG.toast.setInView("ID: [" + CFG.flagManager.flagEdit.iDivisionID + "/" + (CFG.flagManager.lDivisions.size() - 1) + "]", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE);
                CFG.menuManager.getColorPicker().setVisible(false, null);
                return;
            }
            case 4: {
                CFG.flagManager.updateDivision(true);
                CFG.menuManager.rebuildCreateCiv_Flag();
                CFG.toast.setInView("ID: [" + CFG.flagManager.flagEdit.iDivisionID + "/" + (CFG.flagManager.lDivisions.size() - 1) + "]", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE);
                CFG.menuManager.getColorPicker().setVisible(false, null);
                return;
            }
        }
    }
}

