/*
 * Decompiled with CFR 0.152.
 */
package age.of.civilizations2.jakowski.lukasz;

import age.of.civilizations2.jakowski.lukasz.Button_FormCivTitle;
import age.of.civilizations2.jakowski.lukasz.Button_Game;
import age.of.civilizations2.jakowski.lukasz.Button_TodayPartOf_Population;
import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Dialog;
import age.of.civilizations2.jakowski.lukasz.Image;
import age.of.civilizations2.jakowski.lukasz.ImageManager;
import age.of.civilizations2.jakowski.lukasz.Images;
import age.of.civilizations2.jakowski.lukasz.Menu;
import age.of.civilizations2.jakowski.lukasz.MenuElement;
import age.of.civilizations2.jakowski.lukasz.MenuElement_Hover_v2;
import age.of.civilizations2.jakowski.lukasz.MenuElement_Hover_v2_Element2;
import age.of.civilizations2.jakowski.lukasz.MenuElement_Hover_v2_Element_Type;
import age.of.civilizations2.jakowski.lukasz.MenuElement_Hover_v2_Element_Type_Flag;
import age.of.civilizations2.jakowski.lukasz.MenuElement_Hover_v2_Element_Type_Image;
import age.of.civilizations2.jakowski.lukasz.MenuElement_Hover_v2_Element_Type_Text;
import age.of.civilizations2.jakowski.lukasz.SliderMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.Objects;

class Menu_InGame_FormableCiv_Sandbox
extends SliderMenu {
    protected static Image lFlag = null;

    protected Menu_InGame_FormableCiv_Sandbox() {
        ArrayList<MenuElement> menuElements = new ArrayList<MenuElement>();
        menuElements.add(new Button_FormCivTitle(CFG.langManager.get("FormX", CFG.langManager.getCiv(CFG.formableCivs_GameData.getFormableCivTag())), 0, 0, CFG.GAME_WIDTH, Math.max(CFG.BUTTON_HEIGHT * 4 / 5, Math.max(CFG.CIV_FLAG_HEIGHT + CFG.PADDING * 4, (CFG.TEXT_HEIGHT + CFG.PADDING) * 2 + CFG.PADDING)), true, !Objects.equals(CFG.formableCivs_GameData.getFormableCivTag(), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivTag())){

            @Override
            protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                super.drawText(oSB, iTranslateX, iTranslateY, isActive);
                try {
                    lFlag.draw(oSB, this.getPosX() + (int)(((float)this.getWidth() - (float)this.getTextWidth() * 0.85f) / 2.0f) - ImageManager.getImage(Images.flag_rect).getWidth() - CFG.PADDING + iTranslateX, this.getPosY() - lFlag.getHeight() + this.getHeight() / 2 - ImageManager.getImage(Images.flag_rect).getHeight() / 2 + iTranslateY, ImageManager.getImage(Images.flag_rect).getWidth(), ImageManager.getImage(Images.flag_rect).getHeight());
                    ImageManager.getImage(Images.flag_rect).draw(oSB, this.getPosX() + (int)(((float)this.getWidth() - (float)this.getTextWidth() * 0.85f) / 2.0f) - ImageManager.getImage(Images.flag_rect).getWidth() - CFG.PADDING + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flag_rect).getHeight() / 2 + iTranslateY);
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
            }

            @Override
            protected void buildElementHover() {
                try {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("CivilizationChangesToX", CFG.langManager.getCiv(CFG.formableCivs_GameData.getFormableCivTag())), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    this.menuElementHover = new MenuElement_Hover_v2(nElements);
                }
                catch (IndexOutOfBoundsException ex) {
                    this.menuElementHover = null;
                }
            }
        });
        menuElements.add(new Button_Game(null, -1, CFG.PADDING, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2));
        menuElements.add(new Button_Game(null, -1, CFG.PADDING * 2 + CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH * 3 + CFG.BUTTON_WIDTH / 2));

        /*int tempPop = 0;
        for (int i = 0; i < CFG.formableCivs_GameData.getProvincesSize(); ++i) {
            tempPop += CFG.game.getProvince(CFG.formableCivs_GameData.getProvinceID(i)).getPopulationData().getPopulation();
        }
        menuElements.add(new Button_TodayPartOf_Population("" + CFG.getNumberWithSpaces("" + tempPop), CFG.getPercentage(tempPop, CFG.game.countWorld_Population(), 4), 0, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 3 - Math.max(CFG.TEXT_HEIGHT + CFG.PADDING * 4, CFG.CIV_FLAG_HEIGHT + CFG.PADDING * 2), CFG.CIV_INFO_MENU_WIDTH * 3 / 4, Math.max(CFG.TEXT_HEIGHT + CFG.PADDING * 4, CFG.CIV_FLAG_HEIGHT + CFG.PADDING * 2), true));*/

        this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements);
        this.updateLanguage();
        this.loadFlag(CFG.formableCivs_GameData.getFormableCivTag());
    }

    @Override
    protected void updateLanguage() {
        this.getMenuElement(1).setText(CFG.langManager.get("Back"));
        this.getMenuElement(2).setText(CFG.langManager.get("SelectAvailableCivilizations"));
    }

    @Override
    protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
        oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.45f));
        ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, -ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT / 2);
        ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, CFG.GAME_HEIGHT - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT / 2, false, true);
        oSB.setColor(Color.WHITE);
        //CFG.drawEditorButtons_Bot_Edge_R(oSB, iTranslateX, this.getMenuElement(2).getPosY() - CFG.PADDING - 1 + iTranslateY, this.getMenuElement(2).getPosX() + this.getMenuElement(2).getWidth() + CFG.PADDING + 1, CFG.BUTTON_HEIGHT + CFG.PADDING * 2 + 1);
        super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
    }

    @Override
    protected final void actionElement(int iID) {
        switch (iID) {
            case 0: {
                if (!getMenuElement(0).getCheckboxState()) {
                    break;
                }

                CFG.setDialogType(Dialog.FORM_A_CIV);
                break;
            }
            case 1: {
                this.onBackPressed();
                break;
            }
            case 2: {
                CFG.sandbox_task = Menu.eINGAME_FORMABLE_CIV_PROVINCES;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.resetChooseProvinceData();
                CFG.game.resetRegroupArmyData();
                CFG.game.setActiveProvinceID(-1);
                CFG.game.resetChooseProvinceData_Immediately();
                CFG.gameAction.hideAllProvinceActionViews();
                CFG.game.getSelectedProvinces().clearSelectedProvinces();
                CFG.createVassal_Data = new CreateVassal_Data();
                CFG.selectMode = true;
                CFG.brushTool = false;
                CFG.VIEW_SHOW_VALUES = false;
                CFG.menuManager.setViewID(Menu.eINGAME_CREATE_VASSAL_SELECT_CIV);
                Game_Render_Province.updateDrawProvinces();
                CFG.map.getMapBG().updateWorldMap_Shaders();
                //CFG.EDITOR_ACTIVE_GAMEDATA_TAG = CFG.formableCivs_GameData.getFormableCivTag();
                //CFG.setDialogType(Dialog.GO_TO_WIKI);
            }
        }
    }

    @Override
    protected final void onBackPressed() {
        this.disposeFlag();
        CFG.menuManager.setViewID(Menu.eINGAME);
        CFG.game.setActiveProvinceID(CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iBefore_ActiveProvince);
        CFG.game.checkProvinceActionMenu();
        CFG.map.getMapBG().updateWorldMap_Shaders();
        CFG.viewsManager.setActiveViewID(CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE);
    }

    @Override
    protected void onMenuPressed() {
        this.onBackPressed();
    }

    private final void loadFlag(String nTag) {
        block10: {
            this.disposeFlag();
            try {
                try {
                    lFlag = new Image(new Texture(Gdx.files.internal("game/flags/" + nTag + ".png")), Texture.TextureFilter.Nearest);
                }
                catch (GdxRuntimeException e) {
                    try {
                        lFlag = new Image(new Texture(Gdx.files.internal("game/flags/" + CFG.ideologiesManager.getRealTag(nTag) + ".png")), Texture.TextureFilter.Nearest);
                    }
                    catch (GdxRuntimeException ex) {
                        if (CFG.isAndroid()) {
                            try {
                                lFlag = new Image(new Texture(Gdx.files.local("game/civilizations_editor/" + nTag + "/" + nTag + "_FL.png")), Texture.TextureFilter.Nearest);
                            }
                            catch (GdxRuntimeException erq) {
                                lFlag = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + nTag + "/" + nTag + "_FL.png")), Texture.TextureFilter.Nearest);
                            }
                            break block10;
                        }
                        lFlag = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + nTag + "/" + nTag + "_FL.png")), Texture.TextureFilter.Nearest);
                    }
                }
            }
            catch (GdxRuntimeException e) {
                lFlag = null;
            }
            catch (OutOfMemoryError ex) {
                lFlag = null;
            }
        }
    }

    private final void disposeFlag() {
        if (lFlag != null) {
            lFlag.getTexture().dispose();
            lFlag = null;
        }
    }
}

