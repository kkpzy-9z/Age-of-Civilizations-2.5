/*
 * Decompiled with CFR 0.152.
 */
package age.of.civilizations2.jakowski.lukasz;

import age.of.civilizations2.jakowski.lukasz.Button_Menu_LR_Line;
import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Menu;
import age.of.civilizations2.jakowski.lukasz.MenuElement;
import age.of.civilizations2.jakowski.lukasz.SliderMenu;
import age.of.civilizations2.jakowski.lukasz.SliderMenuTitle;
import java.util.ArrayList;

class Menu_InGame_CreateAVassal_SelectCiv
extends SliderMenu {
    protected Menu_InGame_CreateAVassal_SelectCiv() {
        ArrayList<MenuElement> menuElements = new ArrayList<MenuElement>();
        menuElements.add(new Button_Menu_LR_Line(null, -1, 0, 0, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
        this.initMenuWithBackButton(new SliderMenuTitle(null, CFG.BUTTON_HEIGHT * 3 / 4, false, false), 0, CFG.BUTTON_HEIGHT * 3 / 4, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT * 3 / 4, menuElements, true, true);
        this.updateLanguage();
    }

    @Override
    protected void updateLanguage() {
        this.getMenuElement(0).setText(CFG.langManager.get("Back"));
        this.getTitle().setText(CFG.langManager.get("SelectCivilization"));
    }

    @Override
    protected final void actionElement(int iID) {
        this.onBackPressed();
    }

    @Override
    protected void onBackPressed() {
        if (CFG.SPECTATOR_MODE && (CFG.sandbox_task != Menu.eINGAME_CREATE_VASSAL && CFG.sandbox_task != Menu.eINGAME_CREATE_VASSAL_SELECT_CIV)) {
            CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
            CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iBefore_ActiveProvince = CFG.game.getActiveProvinceID();
            CFG.viewsManager.disableAllViews();
            CFG.game.resetChooseProvinceData();
            CFG.game.resetRegroupArmyData();
            CFG.game.setActiveProvinceID(-1);
            CFG.game.resetChooseProvinceData_Immediately();
            CFG.gameAction.hideAllProvinceActionViews();
            CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
            CFG.menuManager.setViewID(Menu.eINGAME_FORMABLE_CIV_PROVINCES);
            CFG.map.getMapBG().updateWorldMap_Shaders();
        } else {
            CFG.menuManager.setViewID(Menu.eINGAME_CREATE_VASSAL);
            CFG.menuManager.clearCreateVassal_SelectCivilizations();
            CFG.map.getMapBG().updateWorldMap_Shaders();
        }
    }

    @Override
    protected void actionClose() {
        this.onBackPressed();
    }
}

