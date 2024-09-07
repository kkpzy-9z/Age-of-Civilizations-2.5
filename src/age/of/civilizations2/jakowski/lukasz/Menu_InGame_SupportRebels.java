/*
 * Decompiled with CFR 0.152.
 */
package age.of.civilizations2.jakowski.lukasz;

import age.of.civilizations2.jakowski.lukasz.Button_Diplomacy_SupportRebels;
import age.of.civilizations2.jakowski.lukasz.Button_FlagActionSliderStyle;
import age.of.civilizations2.jakowski.lukasz.Button_Flag_JustFrame;
import age.of.civilizations2.jakowski.lukasz.Button_NS_Opinion;
import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.DiplomacyManager;
import age.of.civilizations2.jakowski.lukasz.ImageManager;
import age.of.civilizations2.jakowski.lukasz.Images;
import age.of.civilizations2.jakowski.lukasz.MenuElement;
import age.of.civilizations2.jakowski.lukasz.MenuElement_Hover_v2;
import age.of.civilizations2.jakowski.lukasz.MenuElement_Hover_v2_Element2;
import age.of.civilizations2.jakowski.lukasz.MenuElement_Hover_v2_Element_Type;
import age.of.civilizations2.jakowski.lukasz.MenuElement_Hover_v2_Element_Type_Image;
import age.of.civilizations2.jakowski.lukasz.MenuElement_Hover_v2_Element_Type_Text;
import age.of.civilizations2.jakowski.lukasz.Menu_InGame_OfferAlliance;
import age.of.civilizations2.jakowski.lukasz.SliderMenu;
import age.of.civilizations2.jakowski.lukasz.SliderMenuTitle;
import age.of.civilizations2.jakowski.lukasz.Slider_FlagAction_Gold;
import age.of.civilizations2.jakowski.lukasz.SoundsManager;
import age.of.civilizations2.jakowski.lukasz.SupportRebels_Data;
import age.of.civilizations2.jakowski.lukasz.Text_AlliesNotInWar;
import age.of.civilizations2.jakowski.lukasz.Text_Scale;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_SupportRebels
extends SliderMenu {
    protected static int iOnCivID = -1;

    protected Menu_InGame_SupportRebels() {
        ArrayList<MenuElement> menuElements = new ArrayList<MenuElement>();
        int tempWidth = CFG.CIV_INFO_MENU_WIDTH * 2;
        int tY = CFG.PADDING;
        menuElements.add(new Button_Flag_JustFrame(CFG.PADDING, tY, true));
        tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
        int tempMenuPosY = ImageManager.getImage(Images.top_left2).getHeight() + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 5;
        this.initMenu(new SliderMenuTitle(CFG.langManager.get("SupportRebels"), CFG.BUTTON_HEIGHT * 3 / 5, true, true), CFG.GAME_WIDTH / 2 - tempWidth / 2, tempMenuPosY, tempWidth, ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING + tempMenuPosY > CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 ? Math.max(CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 - tempMenuPosY, (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 6) : ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, menuElements, false, true);
        this.updateLanguage();
    }

    protected Menu_InGame_SupportRebels(int onCivID, int iRebelsID) {
        ArrayList<MenuElement> menuElements = new ArrayList<MenuElement>();
        iOnCivID = onCivID;
        int tempWidth = CFG.CIV_INFO_MENU_WIDTH * 2;
        int tY = 0;
        menuElements.add(new Button_NS_Opinion(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), onCivID, Images.diplo_revolution, 0, 34, 2, tY, CFG.BUTTON_WIDTH * 2) {

            @Override
            protected int getWidth() {
                return Menu_InGame_SupportRebels.this.getElementW() * 2;
            }
        });
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight();
        SupportRebels_Data supportData = DiplomacyManager.supportRebels(iOnCivID);
        if (supportData.lMovements.size() > 0) {
            menuElements.add(new Text_AlliesNotInWar(CFG.langManager.get("SelectMovement"), -1, CFG.PADDING, tY += CFG.PADDING, tempWidth - CFG.PADDING * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 3) {

                @Override
                protected int getPosX() {
                    return 0;
                }

                @Override
                protected int getWidth() {
                    return Menu_InGame_SupportRebels.this.getW() + 4;
                }
            });
            tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
            for (int i = 0; i < supportData.lMovements.size(); ++i) {
                menuElements.add(new Button_Diplomacy_SupportRebels(i, supportData.lMovements.get(i), supportData.lPopulation.get(i), (int) ((float) supportData.lUnrest.get(i).intValue() / (float) supportData.lProvinces.get(i).intValue()), supportData.lProvinces.get(i), 2, tY, CFG.BUTTON_WIDTH * 2) {

                    @Override
                    protected int getWidth() {
                        return Menu_InGame_SupportRebels.this.getElementW() * 2;
                    }
                });
                tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight();
                if (((MenuElement) menuElements.get(menuElements.size() - 1)).getCurrent() != iRebelsID) continue;
                ((MenuElement) menuElements.get(menuElements.size() - 1)).setCheckboxState(true);
            }
            long moneyCount = CFG.SPECTATOR_MODE ? (long)10000000 : CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney();
            menuElements.add(new Slider_FlagAction_Gold(CFG.langManager.get("SelectTheAmountOfMoneyToSend"), CFG.PADDING * 2, ((MenuElement) menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, tempWidth - CFG.PADDING * 3 - CFG.BUTTON_WIDTH, CFG.TEXT_HEIGHT + CFG.PADDING * 2 + CFG.PADDING * 4, CFG.SPECTATOR_MODE ? -(int)moneyCount : 0, moneyCount > 0L ? (int)moneyCount : 0, 0, 0.65f) {

                @Override
                protected int getWidth() {
                    return Menu_InGame_SupportRebels.this.getElementW() * 2 - CFG.PADDING * 4;
                }

                @Override
                protected int getSliderHeight() {
                    return CFG.PADDING * 2;
                }

                @Override
                protected Color getColorLEFT() {
                    return new Color(CFG.COLOR_INGAME_GOLD.r, CFG.COLOR_INGAME_GOLD.g, CFG.COLOR_INGAME_GOLD.b, 0.65f);
                }
            });
            tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        } else {
            menuElements.add(new Text_Scale(CFG.langManager.get("NoMovements"), -1, 2, tY, tempWidth - 4, CFG.BUTTON_HEIGHT * 3 / 4, 0.75f) {

                @Override
                protected int getWidth() {
                    return Menu_InGame_SupportRebels.this.getElementW() * 2;
                }
            });
            ((MenuElement) menuElements.get(menuElements.size() - 1)).setClickable(false);
            tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight();
        }
        menuElements.add(new Button_FlagActionSliderStyle(CFG.langManager.get("Cancel"), -1, 2 + CFG.PADDING, tY += CFG.PADDING, CFG.BUTTON_WIDTH, true) {

            @Override
            protected int getWidth() {
                return Menu_InGame_SupportRebels.this.getElementW() - CFG.PADDING - CFG.PADDING / 2;
            }
        });
        menuElements.add(new Button_FlagActionSliderStyle(CFG.langManager.get("SupportRebels"), -1, 2, tY, CFG.BUTTON_WIDTH, supportData.lMovements.size() > 0) {

            @Override
            protected int getPosX() {
                return Menu_InGame_SupportRebels.this.getElementW() + CFG.PADDING / 2;
            }

            @Override
            protected int getWidth() {
                return Menu_InGame_SupportRebels.this.getElementW() - CFG.PADDING - CFG.PADDING / 2;
            }

            @Override
            protected void buildElementHover() {
                ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SupportRebels"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DiplomacyPoints") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text("-3.4", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_diplomacy_points, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
                this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }

            @Override
            protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                ImageManager.getImage(Images.diplo_revolution).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int) (((float) this.getTextWidth() * 0.8f + (float) ImageManager.getImage(Images.diplo_revolution).getWidth() + (float) CFG.PADDING) / 2.0f) + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.diplo_revolution).getHeight() / 2 + iTranslateY);
                CFG.fontMain.getData().setScale(0.8f);
                CFG.drawText(oSB, this.getText(), this.getPosX() + (this.getTextPos() < 0 ? this.getWidth() / 2 - (int) (((float) this.getTextWidth() * 0.8f + (float) ImageManager.getImage(Images.diplo_revolution).getWidth() + (float) CFG.PADDING) / 2.0f) + ImageManager.getImage(Images.diplo_revolution).getWidth() + CFG.PADDING : this.getTextPos()) + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int) ((float) this.getTextHeight() * 0.8f / 2.0f) + iTranslateY, this.getColor(isActive));
                CFG.fontMain.getData().setScale(1.0f);
            }

            @Override
            protected boolean getClickable() {
                return CFG.SPECTATOR_MODE || CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getDiplomacyPoints() >= 34 && super.getClickable();
            }

            @Override
            protected int getSFX() {
                return SoundsManager.getSend();
            }
        });
        int tempMenuPosY = ImageManager.getImage(Images.top_left2).getHeight() + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 5;
        this.initMenu(new SliderMenuTitle(CFG.langManager.get("RevolutionaryMovements"), CFG.BUTTON_HEIGHT * 3 / 5, true, true) {

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
            int iSelectedCivID = -1;
            for (int i = 0; i < this.getMenuElementsSize(); ++i) {
                if (!this.getMenuElement(i).getCheckboxState()) continue;
                iSelectedCivID = this.getMenuElement(i).getCurrent();
            }
            if (iSelectedCivID < 0) {
                CFG.toast.setInView(CFG.langManager.get("SelectCivilization") + "!", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                CFG.toast.setTimeInView(4500);
                return;
            }
            if (!CFG.SPECTATOR_MODE && this.getMenuElement(this.getMenuElementsSize() - 3).getCurrent() <= 0) {
                CFG.toast.setInView(CFG.langManager.get("SelectTheAmountOfMoneyToSend") + "!", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                CFG.toast.setTimeInView(4500);
                return;
            }

            if (CFG.SPECTATOR_MODE) {
                int nMoney = this.getMenuElement(this.getMenuElementsSize() - 3).getCurrent();
                List<Integer> supportedProvinces = new ArrayList();
                List<Integer> supportedPopulation = new ArrayList();
                List<Integer> supportCostPerTurn = new ArrayList();
                int supportedPopulationTotal = 0;

                int i;
                for(i = 0; i < CFG.game.getCiv(iOnCivID).getNumOfProvinces(); ++i) {
                    if (CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getCore().getHaveACore(iSelectedCivID)) {
                        supportedProvinces.add(CFG.game.getCiv(iOnCivID).getProvinceID(i));
                        supportedPopulation.add(CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getPopulationData().getPopulationOfCivID(iSelectedCivID) + 1);
                        supportCostPerTurn.add((int)((float)DiplomacyManager.assimilateCost(CFG.game.getCiv(iOnCivID).getProvinceID(i), 1) * 1.6275F));
                        supportedPopulationTotal += CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getPopulationData().getPopulationOfCivID(iSelectedCivID) + 1;
                    }
                }

                try {
                    CFG.game.getCiv(iOnCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_RebelsSupported(iSelectedCivID, (Integer)supportedProvinces.get(0)));
                } catch (IndexOutOfBoundsException var13) {
                }

                int i2;
                while(supportedProvinces.size() > 0 && nMoney > 0) {
                    i2 = CFG.oR.nextInt(supportedPopulationTotal);
                    i2 = 0;
                    int bestSuppProvID = 0;

                    int numOfTunrs;
                    for(numOfTunrs = 0; numOfTunrs < supportedProvinces.size(); ++numOfTunrs) {
                        if (i2 >= i2 && i2 <= i2 + (Integer)supportedPopulation.get(numOfTunrs)) {
                            bestSuppProvID = numOfTunrs;
                            break;
                        }
                    }

                    if (!(Math.floor((double)(nMoney / (Integer)supportCostPerTurn.get(bestSuppProvID))) > 0.0)) {
                        break;
                    }

                    numOfTunrs = (int)Math.floor((double)(nMoney / (Integer)supportCostPerTurn.get(bestSuppProvID)));
                    if (numOfTunrs <= 1) {
                        break;
                    }

                    numOfTunrs = 1 + CFG.oR.nextInt(numOfTunrs);
                    if (numOfTunrs > 35) {
                        numOfTunrs = 35;
                    }

                    Province_SupportRebels_Help outHelp = CFG.game.getProvince((Integer)supportedProvinces.get(bestSuppProvID)).addSupportRebels(new Province_SupportRebels(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), iSelectedCivID, numOfTunrs));
                    nMoney -= (Integer)supportCostPerTurn.get(bestSuppProvID) * outHelp.iTurns;
                    if (outHelp.max) {
                        supportedPopulationTotal -= (Integer)supportedPopulation.get(bestSuppProvID);
                        supportedProvinces.remove(bestSuppProvID);
                        supportedPopulation.remove(bestSuppProvID);
                        supportCostPerTurn.remove(bestSuppProvID);
                    }
                }

                supportedProvinces.clear();
                supportedPopulation.clear();
                supportedPopulationTotal = 0;

                for(i = 0; i < CFG.game.getCiv(iOnCivID).getNumOfProvinces(); ++i) {
                    if (CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getPopulationData().getPopulationOfCivID(iSelectedCivID) > 0) {
                        supportedProvinces.add(CFG.game.getCiv(iOnCivID).getProvinceID(i));
                        supportedPopulation.add(CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getPopulationData().getPopulationOfCivID(iSelectedCivID));
                        supportedPopulationTotal += (Integer)supportedPopulation.get(supportedPopulation.size() - 1);
                    }
                }

                float efficiency = (float)nMoney / ((float)supportedPopulationTotal * 0.11625F * 5.0F);

                for(i = 0; i < supportedProvinces.size(); ++i) {
                    float tempPercOfPopulation = (float)(Integer)supportedPopulation.get(i) / (float)CFG.game.getProvince((Integer)supportedProvinces.get(i)).getPopulationData().getPopulation();
                    CFG.game.getProvince((Integer)supportedProvinces.get(i)).setRevolutionaryRisk(CFG.gameAges.getAge_RevolutionaryRiskModifier(Game_Calendar.CURRENT_AGEID) * CFG.game.getProvince((Integer)supportedProvinces.get(i)).getRevolutionaryRisk() + 0.2F * efficiency * tempPercOfPopulation * (1.01F - CFG.game.getProvince((Integer)supportedProvinces.get(i)).getHappiness()));
                }

                CFG.toast.setInView(CFG.langManager.get("Sent") + "!", CFG.COLOR_TEXT_MODIFIER_POSITIVE);
                CFG.toast.setTimeInView(4500);
                int tOldActive = CFG.game.getActiveProvinceID();
                CFG.game.setActiveProvinceID(-1);
                CFG.game.setActiveProvinceID(tOldActive);

                CFG.soundsManager.playSound(SoundsManager.SOUND_PLUNDER);
            } else if (DiplomacyManager.supportRebels(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), iOnCivID, iSelectedCivID, this.getMenuElement(this.getMenuElementsSize() - 3).getCurrent())) {
                CFG.toast.setInView(CFG.langManager.get("Sent") + "!", CFG.COLOR_TEXT_MODIFIER_POSITIVE);
                CFG.toast.setTimeInView(4500);
                int tOldActive = CFG.game.getActiveProvinceID();
                CFG.game.setActiveProvinceID(-1);
                CFG.game.setActiveProvinceID(tOldActive);

                CFG.soundsManager.playSound(SoundsManager.SOUND_PLUNDER);
            }
            CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
            CFG.menuManager.rebuildInGame_Messages();
            this.setVisible(false);
            return;
        }
        if (iID == this.getMenuElementsSize() - 2) {
            this.setVisible(false);
            return;
        }
        if (iID == this.getMenuElementsSize() - 3 || iID <= 1) {
            return;
        }
        boolean centerToProv = this.getMenuElement(iID).getCheckboxState();
        for (int i = 0; i < this.getMenuElementsSize(); ++i) {
            this.getMenuElement(i).setCheckboxState(false);
        }
        this.getMenuElement(iID).setCheckboxState(true);
        List<Integer> rebelsProvinces = DiplomacyManager.supportRebels_Provinces(iOnCivID, this.getMenuElement(iID).getCurrent());
        this.getMenuElement(this.getMenuElementsSize() - 3).setCurrent(this.getMenuElement(this.getMenuElementsSize() - 3).getCurrent());
        if (centerToProv && rebelsProvinces.size() > 0) {
            int tBest = 0;
            for (int i = 1; i < rebelsProvinces.size(); ++i) {
                if (!CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(rebelsProvinces.get(i)) || CFG.game.getProvince(rebelsProvinces.get(tBest)).getPopulationData().getPopulationOfCivID(this.getMenuElement(iID).getCurrent()) >= CFG.game.getProvince(rebelsProvinces.get(i)).getPopulationData().getPopulationOfCivID(this.getMenuElement(iID).getCurrent())) continue;
                tBest = i;
            }
            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(rebelsProvinces.get(tBest))) {
                CFG.game.setActiveProvinceID(rebelsProvinces.get(tBest));
                CFG.map.getMapCoordinates().centerToProvinceID(rebelsProvinces.get(tBest));
                CFG.toast.setInView(CFG.game.getProvince(rebelsProvinces.get(tBest)).getName(), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            }
        }
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

