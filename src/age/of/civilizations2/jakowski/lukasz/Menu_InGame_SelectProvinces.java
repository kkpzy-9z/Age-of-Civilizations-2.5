/*
 * Decompiled with CFR 0.152.
 */
package age.of.civilizations2.jakowski.lukasz;

import age.of.civilizations2.jakowski.lukasz.Button_CivName;
import age.of.civilizations2.jakowski.lukasz.Button_Game;
import age.of.civilizations2.jakowski.lukasz.Button_Game_Checkbox;
import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Dialog;
import age.of.civilizations2.jakowski.lukasz.Game_Render_Province;
import age.of.civilizations2.jakowski.lukasz.Menu;
import age.of.civilizations2.jakowski.lukasz.MenuElement;
import age.of.civilizations2.jakowski.lukasz.SliderMenu;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_SelectProvinces
extends SliderMenu {
    protected static TypeOfAction typeOfAction = TypeOfAction.TRADE_LEFT;

    protected Menu_InGame_SelectProvinces() {
        ArrayList<MenuElement> menuElements = new ArrayList<MenuElement>();
        menuElements.add(new Button_Game(null, -1, CFG.PADDING, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, true){

            @Override
            protected Color getColor(boolean isActive) {
                return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT) : CFG.COLOR_BUTTON_GAME_TEXT_NOT_CLICKABLE);
            }
        });
        menuElements.add(new Button_Game_Checkbox(null, -1, CFG.PADDING, CFG.PADDING, CFG.BUTTON_WIDTH * 2, true, false){

            @Override
            protected boolean getCheckboxState() {
                return CFG.brushTool;
            }
        });
        menuElements.add(new Button_Game_Checkbox(null, -1, CFG.BUTTON_WIDTH * 2 + CFG.PADDING * 2, CFG.PADDING, CFG.BUTTON_WIDTH, true, true){

            @Override
            protected boolean getCheckboxState() {
                return CFG.selectMode;
            }
        });
        menuElements.add(new Button_Game(null, -1, CFG.BUTTON_WIDTH * 3 + CFG.PADDING * 3, CFG.PADDING, CFG.BUTTON_WIDTH, false){

            @Override
            protected boolean getClickable() {
                return CFG.game.getSelectedProvinces().getProvincesSize() > 0;
            }
        });
        menuElements.add(new Button_Game(null, -1, CFG.BUTTON_WIDTH * 3 + CFG.PADDING * 3, CFG.PADDING, CFG.BUTTON_WIDTH, false){

            @Override
            protected boolean getClickable() {
                return CFG.game.getSelectedProvinces().getProvincesSize() > 0;
            }
        });
        menuElements.add(new Button_Game_Checkbox(null, -1, CFG.BUTTON_WIDTH * 2 + CFG.PADDING * 2, CFG.PADDING, CFG.BUTTON_WIDTH, true, true){

            @Override
            protected boolean getCheckboxState() {
                return CFG.VIEW_SHOW_VALUES;
            }
        });
        menuElements.add(new Button_CivName(CFG.SPECTATOR_MODE ? CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() : CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID, 0, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 3 - Math.max(CFG.TEXT_HEIGHT + CFG.PADDING * 4, CFG.CIV_FLAG_HEIGHT + CFG.PADDING * 2), CFG.CIV_INFO_MENU_WIDTH * 3 / 4, Math.max(CFG.TEXT_HEIGHT + CFG.PADDING * 4, CFG.CIV_FLAG_HEIGHT + CFG.PADDING * 2), true));
        this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements);
        this.updateLanguage();
    }

    @Override
    protected void updateLanguage() {
        this.getMenuElement(0).setText(CFG.langManager.get("Save"));
        this.getMenuElement(1).setText(CFG.langManager.get("Brush"));
        this.getMenuElement(2).setText(CFG.langManager.get("Select"));
        this.getMenuElement(3).setText(CFG.langManager.get("DeselectAll"));
        this.getMenuElement(4).setText(CFG.langManager.get("Undo"));
        this.getMenuElement(5).setText(CFG.langManager.get("Map"));
        this.updateButtonWidth(4, CFG.PADDING, CFG.BUTTON_WIDTH * 2);
        for (int i = 2; i < 6; ++i) {
            this.updateButtonWidth(i, CFG.PADDING, CFG.BUTTON_WIDTH);
        }
        this.updateButtonWidth(0, CFG.PADDING, CFG.BUTTON_WIDTH * 2);
        int tempX = CFG.GAME_WIDTH - this.getMenuElement(2).getWidth() - CFG.PADDING;
        this.getMenuElement(2).setPosX(tempX);
        tempX = tempX - this.getMenuElement(1).getWidth() - CFG.PADDING;
        this.getMenuElement(1).setPosX(tempX);
        tempX = tempX - this.getMenuElement(3).getWidth() - CFG.PADDING;
        this.getMenuElement(3).setPosX(tempX);
        tempX = tempX - this.getMenuElement(4).getWidth() - CFG.PADDING;
        this.getMenuElement(4).setPosX(tempX);
        tempX = tempX - this.getMenuElement(5).getWidth() - CFG.PADDING;
        this.getMenuElement(5).setPosX(tempX);
    }

    @Override
    protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
        CFG.drawEditorButtons_Top_Edge_R_Reflected(oSB, this.getMenuElement(5).getPosX() - CFG.PADDING + iTranslateX, this.getMenuPosY() + iTranslateY, CFG.GAME_WIDTH - (this.getMenuElement(5).getPosX() - CFG.PADDING), CFG.BUTTON_HEIGHT + CFG.PADDING * 2);
        CFG.drawEditorButtons_Bot_Edge_R(oSB, this.getMenuElement(0).getPosX() - CFG.PADDING + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - CFG.PADDING + iTranslateY, this.getMenuElement(0).getPosX() + this.getMenuElement(0).getWidth() + CFG.PADDING, this.getMenuElement(0).getHeight() + CFG.PADDING * 2);
        super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
    }

    @Override
    protected final void actionElement(int iID) {
        switch (iID) {
            case 0: {
                this.onBackPressed();
                return;
            }
            case 1: {
                CFG.brushTool = !CFG.brushTool;
                break;
            }
            case 2: {
                CFG.selectMode = !CFG.selectMode;
                break;
            }
            case 3: {
                CFG.setDialogType(Dialog.DESELET_ALL_SELECTED_PROVINCES);
                break;
            }
            case 4: {
                CFG.game.getSelectedProvinces().popProvince();
                if (CFG.game.getSelectedProvinces().getProvincesSize() != 0) break;
                CFG.selectMode = true;
                break;
            }
            case 5: {
                CFG.VIEW_SHOW_VALUES = !CFG.VIEW_SHOW_VALUES;
            }
        }
    }

    @Override
    protected void onBackPressed() {
        CFG.brushTool = false;
        CFG.menuManager.setViewID(Menu.eINGAME);
        Game_Render_Province.updateDrawProvinces();
        if (CFG.SPECTATOR_MODE) {
            if (CFG.game.getSelectedProvinces().getProvincesSize() == 0) {
                CFG.toast.setInView(CFG.langManager.get("Error") + " " + CFG.langManager.get("SelectProvinces") + "!", CFG.COLOR_TEXT_MODIFIER_NEGATIVE);
                CFG.menuManager.setViewID(Menu.eINGAME);
                CFG.viewsManager.setActiveViewID(CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE);
                Game_Render_Province.updateDrawProvinces();
                CFG.updateActiveCivInfo_InGame();
                CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID());
                return;
            }
            int plyCivID = CFG.game.getPlayer((int) CFG.PLAYER_TURNID).getCivID();
            switch (CFG.sandbox_task) {
                case eSETTINGS_PROVINCE:
                    for (int i = 0; i < CFG.game.getSelectedProvinces().getProvincesSize(); ++i) {
                        CFG.game.getCiv(CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getCivID()).removePlunder_ProvinceID(CFG.game.getSelectedProvinces().getProvince(i));
                        int tempArmy0 = CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getArmy(0);
                        int tempCiv0 = CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getCivID();
                        int tempArmyNewOwner = CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getArmyCivID(plyCivID);
                        CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).updateArmy(0);
                        CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).setTrueOwnerOfProvince(plyCivID);
                        CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).setCivID(plyCivID, false);
                        CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).updateArmy(tempCiv0, tempArmy0);
                        CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).updateArmy(plyCivID, tempArmyNewOwner);
                        List<Integer> tempCivsLostAccess = new ArrayList<>();

                        for (int j = 0; j < CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getCivsSize(); ++j) {
                            tempCivsLostAccess.add(CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getCivID(j));
                        }
                        for (int civsLostAccess : tempCivsLostAccess) {
                            if (CFG.game.getCiv(civsLostAccess).getPuppetOfCivID() != plyCivID
                                    && CFG.game.getCiv(plyCivID).getPuppetOfCivID() != civsLostAccess
                                    && (
                                    CFG.game.getCiv(civsLostAccess).getAllianceID() <= 0
                                            || CFG.game.getCiv(civsLostAccess).getAllianceID() != CFG.game.getCiv(plyCivID).getAllianceID()
                            )
                                    && CFG.game.getMilitaryAccess(civsLostAccess, plyCivID) <= 0) {
                                CFG.gameAction.accessLost_MoveArmyToClosetsProvince(civsLostAccess, CFG.game.getSelectedProvinces().getProvince(i));
                            }
                        }
                        if (CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getIsCapital()) {
                            CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).removeCapitalCityIcon();
                        }
                        CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getCore().removeCore(CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getCivID());

                        CFG.game.getCiv(CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getCivID()).buildNumOfUnits();
                        CFG.game.buildCivilizationRegions(tempCiv0);
                        CFG.game.buildCivilizationsRegions_TextOver(tempCiv0);
                    }

                    CFG.toast.setInView(CFG.langManager.get("NewOwner") + "!", CFG.COLOR_TEXT_MODIFIER_POSITIVE);
                    CFG.toast.setTimeInView(4500);

                    CFG.game.getCiv(plyCivID).buildNumOfUnits();
                    CFG.game.buildCivilizationRegions(plyCivID);
                    CFG.game.buildCivilizationsRegions_TextOver(plyCivID);
                    break;
                case eCREATE_SCENARIO_SET_UP_ARMY:
                    for (int i = 0; i < CFG.game.getSelectedProvinces().getProvincesSize(); ++i) {
                        int onCivID = CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getCivID();
                        if (onCivID != plyCivID && CFG.game.getCiv(onCivID).getPuppetOfCivID() != plyCivID && onCivID != CFG.game.getCiv(plyCivID).getPuppetOfCivID() && (CFG.game.getCiv(onCivID).getAllianceID() <= 0 || CFG.game.getCiv(onCivID).getAllianceID() != CFG.game.getCiv(plyCivID).getAllianceID()) && CFG.game.getMilitaryAccess(onCivID, plyCivID) <= 0) {
                            int nArmyNewOwnerArmy = CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getArmyCivID(plyCivID);
                            CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).updateArmy(0);
                            CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).updateArmy(plyCivID, 0);
                            CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).setCivID(plyCivID, false);
                            CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).updateArmy(plyCivID, nArmyNewOwnerArmy);
                            for (int j = CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getCivsSize() - 1; j >= 0; --j) {
                                CFG.gameAction.accessLost_MoveArmyToClosetsProvince(CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getCivID(j), CFG.game.getSelectedProvinces().getProvince(i));
                            }

                            if (CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getIsCapital()) {
                                CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).removeCapitalCityIcon();
                            }
                        }

                        CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).updateArmy(plyCivID, Math.max(CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getArmyCivID(plyCivID) + CFG.DEFAULT_ARMY_NOT_SET_UPED, CFG.DEFAULT_ARMY_NOT_SET_UPED));

                        CFG.game.getCiv(CFG.game.getProvince(CFG.game.getSelectedProvinces().getProvince(i)).getCivID()).buildNumOfUnits();
                        CFG.game.buildCivilizationRegions(onCivID);
                        CFG.game.buildCivilizationsRegions_TextOver(onCivID);
                    }

                    CFG.toast.setInView(CFG.langManager.get("RecruitedArmy") + "!", CFG.COLOR_TEXT_MODIFIER_POSITIVE);
                    CFG.toast.setTimeInView(4500);

                    CFG.game.getCiv(plyCivID).buildNumOfUnits();
                    CFG.game.buildCivilizationRegions(plyCivID);
                    CFG.game.buildCivilizationsRegions_TextOver(plyCivID);
                    break;
            }
        } else if (typeOfAction == TypeOfAction.TRADE_LEFT) {
            CFG.tradeRequest.listLEFT.lProvinces.clear();
            for (int i = 0; i < CFG.game.getSelectedProvinces().getProvincesSize(); ++i) {
                CFG.tradeRequest.listLEFT.lProvinces.add(CFG.game.getSelectedProvinces().getProvince(i));
            }
            CFG.menuManager.rebuildInGame_TradeRequest_Just();
        } else if (typeOfAction == TypeOfAction.TRADE_RIGHT) {
            CFG.tradeRequest.listRight.lProvinces.clear();
            for (int i = 0; i < CFG.game.getSelectedProvinces().getProvincesSize(); ++i) {
                CFG.tradeRequest.listRight.lProvinces.add(CFG.game.getSelectedProvinces().getProvince(i));
            }
            CFG.menuManager.rebuildInGame_TradeRequest_Just();
        } else if (typeOfAction == TypeOfAction.ULTIMATUM) {
            CFG.ultimatum.demandProvinces.clear();
            for (int i = 0; i < CFG.game.getSelectedProvinces().getProvincesSize(); ++i) {
                CFG.ultimatum.demandProvinces.add(CFG.game.getSelectedProvinces().getProvince(i));
            }
        }
        CFG.menuManager.setViewID(Menu.eINGAME);
        CFG.viewsManager.setActiveViewID(CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE);
        Game_Render_Province.updateDrawProvinces();
        CFG.updateActiveCivInfo_InGame();
        CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID());
    }

    protected static enum TypeOfAction {
        TRADE_LEFT,
        TRADE_RIGHT,
        TRADE_LEFT_DECLAREWAR,
        TRADE_RIGHT_DECLAREWAR,
        TRADE_LEFT_COALITION,
        TRADE_RIGHT_COALITION,
        ULTIMATUM;

    }
}

