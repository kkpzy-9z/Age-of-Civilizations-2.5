/*
 * Decompiled with CFR 0.152.
 */
package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

import java.util.ArrayList;
import java.util.List;

class Menu_InGame_AnnexTerritory
extends SliderMenu {
    protected List<Integer> lInnerBorders = new ArrayList<Integer>();
    protected List<Integer> lCores = new ArrayList<Integer>();
    protected List<Integer> lVassals = new ArrayList<Integer>();
    protected List<Integer> lVassalProvinces = new ArrayList<Integer>();
    protected List<Integer> lOccupied = new ArrayList<Integer>();

    protected Menu_InGame_AnnexTerritory() {
        ArrayList<MenuElement> menuElements = new ArrayList<MenuElement>();
        int tempWidth = CFG.CIV_INFO_MENU_WIDTH * 2;
        int tY = CFG.PADDING;
        menuElements.add(new Button_Flag_JustFrame(CFG.PADDING, tY, true));
        tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
        int tempMenuPosY = ImageManager.getImage(Images.top_left2).getHeight() + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 5;
        this.initMenu(new SliderMenuTitle(CFG.langManager.get("TransferControl") + " " + CFG.langManager.get("Menu"), CFG.BUTTON_HEIGHT * 3 / 5, true, true), CFG.GAME_WIDTH / 2 - tempWidth / 2, tempMenuPosY, tempWidth, ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING + tempMenuPosY > CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 ? Math.max(CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 - tempMenuPosY, (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 6) : ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, menuElements, false, true);
        this.updateLanguage();
    }

    protected void safe_Annex(int onCivID) {
        int plyCivID = CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID();
        for (int k2 = 0; k2 < CFG.game.getCiv(onCivID).isAtWarWithCivs.size(); ++k2) {
            CFG.game.whitePeace(onCivID, CFG.game.getCiv(onCivID).isAtWarWithCivs.get(k2));
        }

        ArrayList<Integer> tempProvinces = new ArrayList<Integer>();
        for (int k = 0; k < CFG.game.getCiv(onCivID).getNumOfProvinces(); ++k) {
            tempProvinces.add(CFG.game.getCiv(onCivID).getProvinceID(k));
        }

        CFG.game.getCiv(onCivID).clearMoveUnits();
        CFG.game.getCiv(onCivID).clearMoveUnits_Plunder();
        CFG.game.getCiv(onCivID).clearRegroupArmy();
        CFG.game.getCiv(onCivID).clearRecruitArmy();
        for (int k = 0; k < tempProvinces.size(); ++k) {
            if (CFG.game.getProvince((Integer)tempProvinces.get(k)).getCivID() != onCivID || CFG.game.getProvince((Integer)tempProvinces.get(k)).getTrueOwnerOfProvince() != onCivID) continue;
            int nArmyNewOwnerArmy = CFG.game.getProvince((Integer)tempProvinces.get(k)).getArmyCivID(plyCivID);
            CFG.game.getProvince((Integer)tempProvinces.get(k)).updateArmy(0);
            CFG.game.getProvince((Integer)tempProvinces.get(k)).updateArmy(onCivID, 0);
            CFG.game.getProvince((Integer)tempProvinces.get(k)).updateArmy(plyCivID, 0);
            CFG.game.getProvince((Integer)tempProvinces.get(k)).setTrueOwnerOfProvince(plyCivID);
            CFG.game.getProvince((Integer)tempProvinces.get(k)).setCivID(plyCivID, false);
            CFG.game.getProvince((Integer)tempProvinces.get(k)).updateArmy(plyCivID, nArmyNewOwnerArmy);
            for (int j = CFG.game.getProvince((Integer)tempProvinces.get(k)).getCivsSize() - 1; j >= 0; --j) {
                if (CFG.game.getCiv(CFG.game.getProvince((Integer)tempProvinces.get(k)).getCivID(j)).getPuppetOfCivID() == plyCivID || CFG.game.getCiv(plyCivID).getPuppetOfCivID() == CFG.game.getProvince((Integer)tempProvinces.get(k)).getCivID(j) || CFG.game.getCiv(CFG.game.getProvince((Integer)tempProvinces.get(k)).getCivID(j)).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getProvince((Integer)tempProvinces.get(k)).getCivID(j)).getAllianceID() == CFG.game.getCiv(plyCivID).getAllianceID() || CFG.game.getMilitaryAccess(CFG.game.getProvince((Integer)tempProvinces.get(k)).getCivID(j), plyCivID) > 0) continue;
                CFG.gameAction.accessLost_MoveArmyToClosetsProvince(CFG.game.getProvince((Integer)tempProvinces.get(k)).getCivID(j), (Integer)tempProvinces.get(k));
            }

            CFG.game.buildCivilizationsRegions_TextOver(CFG.game.getProvince((Integer)tempProvinces.get(k)).getCivID());
        }
        if (CFG.game.getCiv(onCivID).getCapitalProvinceID() >= 0) {
            CFG.game.getProvince(CFG.game.getCiv(onCivID).getCapitalProvinceID()).setIsCapital(false);
            for (int i = 0; i < CFG.game.getProvince(CFG.game.getCiv(onCivID).getCapitalProvinceID()).getCitiesSize(); ++i) {
                if (CFG.game.getProvince(CFG.game.getCiv(onCivID).getCapitalProvinceID()).getCity(i).getCityLevel() != CFG.getEditorCityLevel(0)) continue;
                CFG.game.getProvince(CFG.game.getCiv(onCivID).getCapitalProvinceID()).getCity(i).setCityLevel(CFG.getEditorCityLevel(1));
            }
        }
        CFG.game.getCiv(onCivID).buildNumOfUnits();
        tempProvinces.clear();

        CFG.game.getCiv(onCivID).setPuppetOfCivID(onCivID);
        for(int h = 0; h < CFG.game.getCiv(onCivID).civGameData.lVassals.size(); ++h) {
            CFG.game.getCiv(CFG.game.getCiv(onCivID).civGameData.lVassals.get(h).iCivID).setPuppetOfCivID(CFG.game.getCiv(onCivID).civGameData.lVassals.get(h).iCivID);
        }
        if (CFG.game.getCiv(onCivID).getAllianceID() > 0) {
            CFG.game.getAlliance(CFG.game.getCiv(onCivID).getAllianceID()).removeCivilization(onCivID);
            CFG.game.getCiv(onCivID).setAllianceID(0);
        }

        CFG.game.buildCivilizationsRegions_TextOver(onCivID);
        CFG.game.buildCivilizationsRegions_TextOver(plyCivID);

        CFG.updateActiveCivInfo_InGame();
        CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());

        CFG.historyManager.addHistoryLog(new HistoryLog_Annexation(onCivID, plyCivID));
        CFG.menuManager.setVisible_Menu_InGame_CurrentWars(true);
    }

    protected void safe_Transfer(List<Integer> iProvinces) {
        int plyCivID = CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID();

        for (int i = 0; i < iProvinces.size(); ++i) {
            CFG.game.getCiv(CFG.game.getProvince(iProvinces.get(i)).getCivID()).removePlunder_ProvinceID(iProvinces.get(i));
            int tempArmy0 = CFG.game.getProvince(iProvinces.get(i)).getArmy(0);
            int tempCiv0 = CFG.game.getProvince(iProvinces.get(i)).getCivID();
            int tempArmyNewOwner = CFG.game.getProvince(iProvinces.get(i)).getArmyCivID(plyCivID);
            CFG.game.getProvince(iProvinces.get(i)).updateArmy(0);
            CFG.game.getProvince(iProvinces.get(i)).setTrueOwnerOfProvince(plyCivID);
            CFG.game.getProvince(iProvinces.get(i)).setCivID(plyCivID, false);
            CFG.game.getProvince(iProvinces.get(i)).updateArmy(tempCiv0, tempArmy0);
            CFG.game.getProvince(iProvinces.get(i)).updateArmy(plyCivID, tempArmyNewOwner);
            List<Integer> tempCivsLostAccess = new ArrayList<>();

            for(int j = 0; j < CFG.game.getProvince(iProvinces.get(i)).getCivsSize(); ++j) {
                tempCivsLostAccess.add(CFG.game.getProvince(iProvinces.get(i)).getCivID(j));
            }
            for (int civsLostAccess : tempCivsLostAccess) {
                if (CFG.game.getCiv(civsLostAccess).getPuppetOfCivID() != plyCivID
                        && CFG.game.getCiv(plyCivID).getPuppetOfCivID() != civsLostAccess
                        && (
                        CFG.game.getCiv(civsLostAccess).getAllianceID() <= 0
                                || CFG.game.getCiv(civsLostAccess).getAllianceID() != CFG.game.getCiv(plyCivID).getAllianceID()
                )
                        && CFG.game.getMilitaryAccess(civsLostAccess, plyCivID) <= 0) {
                    CFG.gameAction.accessLost_MoveArmyToClosetsProvince(civsLostAccess, iProvinces.get(i));
                }
            }
            if (CFG.game.getProvince(iProvinces.get(i)).getIsCapital()) {
                CFG.game.getProvince(iProvinces.get(i)).removeCapitalCityIcon();
            }
            CFG.game.getProvince(iProvinces.get(i)).getCore().removeCore(CFG.game.getProvince(iProvinces.get(i)).getCivID());

            CFG.game.getCiv(CFG.game.getProvince(iProvinces.get(i)).getCivID()).buildNumOfUnits();
            CFG.game.buildCivilizationRegions(tempCiv0);
            CFG.game.buildCivilizationsRegions_TextOver(CFG.game.getProvince(iProvinces.get(i)).getCivID());
        }

        CFG.toast.setInView(CFG.langManager.get("NewOwner") + "!", CFG.COLOR_TEXT_MODIFIER_POSITIVE);
        CFG.toast.setTimeInView(4500);

        CFG.game.getCiv(plyCivID).buildNumOfUnits();
        CFG.game.buildCivilizationRegions(plyCivID);

        CFG.updateActiveCivInfo_InGame();
        CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
    }

    protected Menu_InGame_AnnexTerritory(int onCivID) {
        ArrayList<MenuElement> menuElements = new ArrayList<MenuElement>();
        int tempWidth = CFG.CIV_INFO_MENU_WIDTH * 2;
        int tY = 0;

        menuElements.add(new Button_CivName(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), 2, tY, CFG.BUTTON_WIDTH * 2, CFG.BUTTON_HEIGHT / 2, true) {

            @Override
            protected int getWidth() {
                return Menu_InGame_AnnexTerritory.this.getElementW() * 2;
            }
        });
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight();

        menuElements.add(new Text_AlliesNotInWar(CFG.langManager.get("Options"), -1, 2, tY, tempWidth - CFG.PADDING * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 3) {

            @Override
            protected int getPosX() {
                return 0;
            }

            @Override
            protected int getWidth() {
                return Menu_InGame_AnnexTerritory.this.getW() + 4;
            }
        });
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight();
        /*for (int i = 0; i < supportData.lMovements.size(); ++i) {
            menuElements.add(new Button_Diplomacy_SupportRebels(i, supportData.lMovements.get(i), supportData.lPopulation.get(i), (int) ((float) supportData.lUnrest.get(i).intValue() / (float) supportData.lProvinces.get(i).intValue()), supportData.lProvinces.get(i), 2, tY, CFG.BUTTON_WIDTH * 2) {

                @Override
                protected int getWidth() {
                    return Menu_InGame_AnnexTerritory.this.getElementW() * 2;
                }
            });
            tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight();
            if (((MenuElement) menuElements.get(menuElements.size() - 1)).getCurrent() != iRebelsID) continue;
            ((MenuElement) menuElements.get(menuElements.size() - 1)).setCheckboxState(true);
        }*/
        menuElements.add(new Button_Diplomacy_AnnexTerritory(1, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.langManager.get("SelectCivilization"), new ArrayList<Integer>(), 2, tY, CFG.BUTTON_WIDTH * 2) {

            @Override
            protected int getWidth() {
                return Menu_InGame_AnnexTerritory.this.getElementW() * 2;
            }
        });
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight();
        menuElements.add(new Button_Diplomacy_AnnexTerritory(1, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.langManager.get("SelectProvinces"), new ArrayList<Integer>(), 2, tY, CFG.BUTTON_WIDTH * 2) {

            @Override
            protected int getWidth() {
                return Menu_InGame_AnnexTerritory.this.getElementW() * 2;
            }
        });
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight();

        lInnerBorders.clear();
        lInnerBorders = new ArrayList<Integer>();
        lCores.clear();
        lCores = new ArrayList<Integer>();
        lVassals.clear();
        lVassals = new ArrayList<Integer>();
        lVassalProvinces.clear();
        lVassalProvinces = new ArrayList<Integer>();
        lOccupied.clear();
        lOccupied = new ArrayList<Integer>();
        for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (CFG.game.getProvince(i).getSeaProvince() || CFG.game.getProvince(i).getWasteland() >= 1) continue;

            if ((CFG.game.getProvince(i).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) && (CFG.game.getProvince(i).getTrueOwnerOfProvince() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                lOccupied.add(i);
            } else if (CFG.game.getProvince(i).getCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                if (CFG.game.getProvince(i).getTrueOwnerOfProvince() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                    lInnerBorders.add(i);
                }
                if (CFG.game.getProvince(i).getCore().getHaveACore(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                    lCores.add(i);
                }
            }
        }
        if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.iVassalsSize > 0) {
            for (int i2 = 0; i2 < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.iVassalsSize; i2++) {
                if (CFG.game.getCiv(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i2).iCivID).getNumOfProvinces() <= 0) continue;
                lVassals.add(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i2).iCivID);
                for (int i4 = 0; i4 < CFG.game.getCiv(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i2).iCivID).getNumOfProvinces(); i4++) {
                    lVassalProvinces.add(CFG.game.getCiv(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i2).iCivID).getProvinceID(i4));
                }
            }
        }

        menuElements.add(new Button_Diplomacy_AnnexTerritory(1, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), "Pre-" + CFG.langManager.get("War") + " " + CFG.langManager.get("Provinces"), lInnerBorders, 2, tY, CFG.BUTTON_WIDTH * 2) {

            @Override
            protected int getWidth() {
                return Menu_InGame_AnnexTerritory.this.getElementW() * 2;
            }
        });
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight();
        menuElements.add(new Button_Diplomacy_AnnexTerritory(0, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.langManager.get("Cores"), lCores, 2, tY, CFG.BUTTON_WIDTH * 2) {

            @Override
            protected int getWidth() {
                return Menu_InGame_AnnexTerritory.this.getElementW() * 2;
            }
        });
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight();
        menuElements.add(new Button_Diplomacy_AnnexTerritory(1, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.langManager.get("Vassals"), lVassalProvinces, 2, tY, CFG.BUTTON_WIDTH * 2) {

            @Override
            protected int getWidth() {
                return Menu_InGame_AnnexTerritory.this.getElementW() * 2;
            }
        });
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight();
        menuElements.add(new Button_Diplomacy_AnnexTerritory(1, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.langManager.get("Occupied"), lOccupied, 2, tY, CFG.BUTTON_WIDTH * 2) {

            @Override
            protected int getWidth() {
                return Menu_InGame_AnnexTerritory.this.getElementW() * 2;
            }
        });
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight();

        menuElements.add(new Button_FlagActionSliderStyle(CFG.langManager.get("Cancel"), -1, 2 + CFG.PADDING, tY += CFG.PADDING, CFG.BUTTON_WIDTH, true) {

            @Override
            protected int getWidth() {
                return Menu_InGame_AnnexTerritory.this.getElementW() - CFG.PADDING - CFG.PADDING / 2;
            }
        });
        menuElements.add(new Button_FlagActionSliderStyle(CFG.langManager.get("TransferControl"), -1, 2, tY, CFG.BUTTON_WIDTH, true) {

            @Override
            protected int getPosX() {
                return Menu_InGame_AnnexTerritory.this.getElementW() + CFG.PADDING / 2;
            }

            @Override
            protected int getWidth() {
                return Menu_InGame_AnnexTerritory.this.getElementW() - CFG.PADDING - CFG.PADDING / 2;
            }

            /*@Override
            protected void buildElementHover() {
                ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TransferControl"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
                this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }*/

            @Override
            protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                ImageManager.getImage(Images.transfer_control).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int) (((float) this.getTextWidth() * 0.8f + (float) ImageManager.getImage(Images.transfer_control).getWidth() + (float) CFG.PADDING) / 2.0f) + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.transfer_control).getHeight() / 2 + iTranslateY);
                CFG.fontMain.getData().setScale(0.8f);
                CFG.drawText(oSB, this.getText(), this.getPosX() + (this.getTextPos() < 0 ? this.getWidth() / 2 - (int) (((float) this.getTextWidth() * 0.8f + (float) ImageManager.getImage(Images.transfer_control).getWidth() + (float) CFG.PADDING) / 2.0f) + ImageManager.getImage(Images.transfer_control).getWidth() + CFG.PADDING : this.getTextPos()) + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int) ((float) this.getTextHeight() * 0.8f / 2.0f) + iTranslateY, this.getColor(isActive));
                CFG.fontMain.getData().setScale(1.0f);
            }

            @Override
            protected boolean getClickable() {
                return CFG.SPECTATOR_MODE && super.getClickable();
            }

            @Override
            protected int getSFX() {
                return SoundsManager.getSend();
            }
        });
        int tempMenuPosY = ImageManager.getImage(Images.top_left2).getHeight() + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 5;
        this.initMenu(new SliderMenuTitle(CFG.langManager.get("TransferControl"), CFG.BUTTON_HEIGHT * 3 / 5, true, true) {

            @Override
            protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
                ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX - 2 + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), nWidth + 4 - ImageManager.getImage(Images.dialog_title).getWidth(), this.getHeight());
                ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX + nWidth + 2 - ImageManager.getImage(Images.dialog_title).getWidth() + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), ImageManager.getImage(Images.dialog_title).getWidth(), this.getHeight(), true, false);
                oSB.setColor(new Color(0.3882353f, 0.11764706f, 0.078431375f, 0.165f));
                ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() + 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, this.getHeight() - 2, false, true);
                oSB.setColor(new Color(0.3882353f, 0.11764706f, 0.078431375f, 0.375f));
                ImageManager.getImage(Images.gradient).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() * 2 / 3 - ImageManager.getImage(Images.gradient).getHeight(), nWidth, this.getHeight() * 2 / 3, false, true);
                oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.65f));
                ImageManager.getImage(Images.gradient).draw(oSB, nPosX + iTranslateX, nPosY - CFG.PADDING - ImageManager.getImage(Images.gradient).getHeight(), nWidth, CFG.PADDING, false, true);
                oSB.setColor(CFG.COLOR_NEW_GAME_EDGE_LINE);
                ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.pix255_255_255).getHeight(), nWidth, 1);
                oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.55f));
                ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, 1);
                oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.8f));
                ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, 1);
                oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.45f));
                ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.slider_gradient).getHeight(), nWidth / 2, 1);
                ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + nWidth - nWidth / 2 + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.slider_gradient).getHeight(), nWidth / 2, 1, true, false);
                oSB.setColor(Color.WHITE);
                CFG.fontMain.getData().setScale(0.8f);
                CFG.drawText(oSB, this.getText(), nPosX + (int) ((float) nWidth - (float) this.getTextWidth() * 0.8f) / 2 + iTranslateX, 2 + nPosY - this.getHeight() + (int) ((float) this.getHeight() - (float) this.getTextHeight() * 0.8f) / 2, Color.WHITE);
                CFG.fontMain.getData().setScale(1.0f);
            }
        }, CFG.GAME_WIDTH / 2 - tempWidth / 2, tempMenuPosY, tempWidth, ((MenuElement) menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING + tempMenuPosY > CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 ? Math.max(CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 - tempMenuPosY, (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 6) : ((MenuElement) menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, menuElements, true, true);
        this.updateLanguage();
        Menu_InGame_OfferAlliance.lTime = System.currentTimeMillis();
    }

    @Override
    protected void updateLanguage() {
    }

    @Override
    protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
        if (Menu_InGame_OfferAlliance.lTime + 200L >= System.currentTimeMillis()) {
            Rectangle clipBounds = new Rectangle(this.getPosX() - 2, CFG.GAME_HEIGHT - this.getPosY(), this.getWidth() + 4, -((int)((float)(this.getHeight() + CFG.PADDING) * ((float)(System.currentTimeMillis() - Menu_InGame_OfferAlliance.lTime) / 200.0f))));
            oSB.flush();
            ScissorStack.pushScissors(clipBounds);
            oSB.setColor(Color.WHITE);
            ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + 4, this.getHeight() + CFG.PADDING, false, true);
            ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() + 2 + this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING, true, true);
            oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.45f));
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth() - 4, this.getHeight() / 4);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth() - 4, 1);
            oSB.setColor(Color.WHITE);
            this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
            oSB.setColor(Color.WHITE);
            CFG.setRender_3(true);
            this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        } else {
            oSB.setColor(Color.WHITE);
            ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + 4, this.getHeight() + CFG.PADDING, false, true);
            ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() + 2 + this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING, true, true);
            oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.45f));
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth() - 4, this.getHeight() / 4);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth() - 4, 1);
            oSB.setColor(Color.WHITE);
            this.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
            this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
            oSB.setColor(Color.WHITE);
            this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        }
    }

    @Override
    protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
        if (sliderMenuIsActive) {
            super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        }
    }

    @Override
    protected final void actionElement(int iID) {
        if (iID == this.getMenuElementsSize() - 1) {
            int iSelectedOption = -1;
            for (int i = 0; i < this.getMenuElementsSize(); ++i) {
                if (!this.getMenuElement(i).getCheckboxState()) continue;
                iSelectedOption = i;
                break;
            }
            if (iSelectedOption < 0) {
                CFG.toast.setInView(CFG.langManager.get("Error") + " " + CFG.langManager.get("SelectProvinces") + "!", CFG.COLOR_TEXT_MODIFIER_NEGATIVE);
                CFG.toast.setTimeInView(4500);
                return;
            }

            switch (iSelectedOption) {
                case 2:
                    CFG.sandbox_task = Menu.eVICTORY_CONDITIONS;
                    CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                    CFG.viewsManager.disableAllViews();
                    CFG.game.setActiveProvinceID(-1);
                    CFG.menuManager.setViewID(Menu.eINGAME_TRADE_SELECT_CIV);
                    CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                    Game_Render_Province.updateDrawProvinces();
                    break;
                case 3:
                    CFG.sandbox_task = Menu.eSETTINGS_PROVINCE;
                    CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                    CFG.viewsManager.disableAllViews();
                    CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iBefore_ActiveProvince = CFG.game.getActiveProvinceID();
                    CFG.game.setActiveProvinceID(-1);
                    CFG.VIEW_SHOW_VALUES = false;
                    CFG.selectMode = true;
                    CFG.game.getSelectedProvinces().clearSelectedProvinces();
                    CFG.menuManager.setViewID(Menu.eINGAME_SELECT_PROVINCES);
                    CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                    Game_Render_Province.updateDrawProvinces();
                    break;
                case 4:
                    safe_Transfer(lInnerBorders);
                    break;
                case 5:
                    safe_Transfer(lCores);
                    break;
                case 6:
                    for (int i3 = 0; i3 < lVassals.size(); i3++) {
                        safe_Annex(lVassals.get(i3));
                    }
                    break;
                case 7:
                    safe_Transfer(lOccupied);
                    break;
            }

            lInnerBorders.clear();
            lInnerBorders = new ArrayList<Integer>();
            lCores.clear();
            lCores = new ArrayList<Integer>();
            lVassals.clear();
            lVassals = new ArrayList<Integer>();
            lVassalProvinces.clear();
            lVassalProvinces = new ArrayList<Integer>();
            lOccupied.clear();
            lOccupied = new ArrayList<Integer>();

            CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
            CFG.menuManager.rebuildInGame_Messages();
            this.setVisible(false);
            return;
        }

        if (iID == this.getMenuElementsSize() - 2) {
            this.setVisible(false);
            return;
        }

        boolean centerToProv = this.getMenuElement(iID).getCheckboxState();
        for (int i = 0; i < this.getMenuElementsSize(); ++i) {
            this.getMenuElement(i).setCheckboxState(false);
        }
        this.getMenuElement(iID).setCheckboxState(true);
        this.getMenuElement(this.getMenuElementsSize() - 3).setCurrent(this.getMenuElement(this.getMenuElementsSize() - 3).getCurrent());

    }

    protected final int getW() {
        return this.getWidth() - 4;
    }

    protected final int getElementW() {
        return this.getW() / 2;
    }

    @Override
    protected void setVisible(boolean visible) {
        super.setVisible(visible);
        if (!visible) {
            for (int i = 0; i < this.getMenuElementsSize(); ++i) {
                this.getMenuElement(i).setVisible(false);
            }
        }
    }
}

