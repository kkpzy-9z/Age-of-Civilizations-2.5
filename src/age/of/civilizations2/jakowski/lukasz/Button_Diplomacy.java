package age.of.civilizations2.jakowski.lukasz;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;

class Button_Diplomacy extends Button
{
    protected static int iDiploWidth = 0;
    private int iDiploImageID;
    protected List<Integer> lCivs;
    private boolean row;
    private boolean moveable;
    private int iButtonsPosX;
    private boolean scrollModeY;
    private int iScrollPosX;
    private int iScrollPosX2;
    private float fScrollNewMenuPosY;
    protected int iHoveredID;
    
    protected static final void setMaxDiploWidth(final int nDiploWidth) {
        if (nDiploWidth + getMaxDiploWidth_ExtraPadding() > Button_Diplomacy.iDiploWidth) {
            Button_Diplomacy.iDiploWidth = nDiploWidth + getMaxDiploWidth_ExtraPadding();
        }
    }
    
    protected static final int getMaxDiploWidth_ExtraPadding() {
        return CFG.PADDING * 4;
    }
    
    protected Button_Diplomacy(final int iDiploImageID, final List<Integer> nCivs, final int iPosX, final int iPosY, final int iWidth) {
        super();
        this.row = false;
        this.moveable = false;
        this.scrollModeY = false;
        this.iScrollPosX = -1;
        this.iScrollPosX2 = -1;
        this.fScrollNewMenuPosY = 0.0f;
        this.iHoveredID = -1;
        this.init("", 0, iPosX, iPosY, iWidth, CFG.CIV_FLAG_HEIGHT + CFG.PADDING * 2, true, true, false, false);
        this.iDiploImageID = iDiploImageID;
        this.lCivs = new ArrayList<Integer>();
        for (int i = 0; i < nCivs.size(); ++i) {
            this.lCivs.add(nCivs.get(i));
        }
        this.updateMoveable();
        this.typeOfElement = TypeOfElement.DIPLOMACY_INFO;
    }
    
    @Override
    protected void drawButtonBG(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean isActive) {
        if (this.scrollModeY) {
            if (Math.abs(this.fScrollNewMenuPosY) > 1.0f) {
                this.setCurrent(this.iButtonsPosX + (int)this.fScrollNewMenuPosY);
                this.fScrollNewMenuPosY *= 0.97f;
            }
            else {
                this.scrollModeY = false;
            }
            CFG.setRender_3(true);
        }
        if (this.row) {
            oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.1f));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), this.getHeight());
            oSB.setColor(new Color(CFG.COLOR_GRADIENT_DIPLOMACY.r, CFG.COLOR_GRADIENT_DIPLOMACY.g, CFG.COLOR_GRADIENT_DIPLOMACY.b, 0.125f));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth() / 2, this.getHeight());
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() - this.getWidth() / 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth() / 2, this.getHeight(), true, false);
            if (isActive || this.getIsHovered()) {
                oSB.setColor(new Color(CFG.COLOR_INFO_BOX_GRADIENT.r, CFG.COLOR_INFO_BOX_GRADIENT.g, CFG.COLOR_INFO_BOX_GRADIENT.b, 0.75f));
                ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth(), this.getHeight() - 2);
            }
            oSB.setColor(new Color(CFG.COLOR_INFO_BOX_GRADIENT.r, CFG.COLOR_INFO_BOX_GRADIENT.g, CFG.COLOR_INFO_BOX_GRADIENT.b, 0.45f));
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), CFG.PADDING);
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - this.getHeight() / 3 - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), this.getHeight() / 3, false, true);
            oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.6f));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
        }
        else {
            oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.335f));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), this.getHeight());
            oSB.setColor(new Color(CFG.COLOR_GRADIENT_DIPLOMACY.r, CFG.COLOR_GRADIENT_DIPLOMACY.g, CFG.COLOR_GRADIENT_DIPLOMACY.b, 0.075f));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth() / 2, this.getHeight());
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() - this.getWidth() / 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth() / 2, this.getHeight(), true, false);
            if (isActive || this.getIsHovered()) {
                oSB.setColor(new Color(CFG.COLOR_GRADIENT_DIPLOMACY.r, CFG.COLOR_GRADIENT_DIPLOMACY.g, CFG.COLOR_GRADIENT_DIPLOMACY.b, 0.75f));
                ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth(), this.getHeight() - 2);
            }
            oSB.setColor(new Color(0.06f, 0.06f, 0.1f, 0.65f));
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), this.getHeight() / 4);
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - this.getHeight() / 4 - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), this.getHeight() / 4, false, true);
            oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.6f));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
            oSB.setColor(new Color(CFG.COLOR_GRADIENT_DIPLOMACY.r, CFG.COLOR_GRADIENT_DIPLOMACY.g, CFG.COLOR_GRADIENT_DIPLOMACY.b, 0.85f));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
        }
        oSB.setColor(Color.WHITE);
        ImageManager.getImage(this.iDiploImageID).draw(oSB, this.getPosX() + (Button_Diplomacy.iDiploWidth - ImageManager.getImage(this.iDiploImageID).getWidth()) / 2 + iTranslateX, this.getPosY() + (this.getHeight() - ImageManager.getImage(this.iDiploImageID).getHeight()) / 2 + iTranslateY);
        final Rectangle clipBounds = new Rectangle((float)(this.getPosX() + Button_Diplomacy.iDiploWidth + iTranslateX), (float)(CFG.GAME_HEIGHT - this.getPosY() - iTranslateY), (float)(this.getWidth() - Button_Diplomacy.iDiploWidth), (float)(-this.getHeight()));
        oSB.flush();
        ScissorStack.pushScissors(clipBounds);
        for (int i = 0; i < this.lCivs.size(); ++i) {
            if (this.lCivs.get(i) >= 0) {
                //if no flag, load it
                if (CFG.game.getCiv(this.lCivs.get(i)).getFlag_IsNull()) CFG.game.getCiv(this.lCivs.get(i)).loadFlag();
                CFG.game.getCiv(this.lCivs.get(i)).getFlag().draw(oSB, this.getPosX() + this.iButtonsPosX + Button_Diplomacy.iDiploWidth + (CFG.CIV_FLAG_WIDTH + CFG.PADDING) * i + iTranslateX, this.getPosY() + (this.getHeight() - CFG.CIV_FLAG_HEIGHT) / 2 - CFG.game.getCiv(this.lCivs.get(i)).getFlag().getHeight() + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
            }
            else {
                ImageManager.getImage(Images.randomCivilizationFlag).draw(oSB, this.getPosX() + this.iButtonsPosX + Button_Diplomacy.iDiploWidth + (CFG.CIV_FLAG_WIDTH + CFG.PADDING) * i + iTranslateX, this.getPosY() + (this.getHeight() - CFG.CIV_FLAG_HEIGHT) / 2 - ImageManager.getImage(Images.randomCivilizationFlag).getHeight() + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
            }
            ImageManager.getImage(Images.flag_rect).draw(oSB, this.getPosX() + this.iButtonsPosX + Button_Diplomacy.iDiploWidth + (CFG.CIV_FLAG_WIDTH + CFG.PADDING) * i + iTranslateX, this.getPosY() + (this.getHeight() - CFG.CIV_FLAG_HEIGHT) / 2 + iTranslateY);
        }
        if (this.getIsHovered() && this.iHoveredID >= 0) {
            oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.55f));
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + this.iButtonsPosX + Button_Diplomacy.iDiploWidth + (CFG.CIV_FLAG_WIDTH + CFG.PADDING) * this.iHoveredID + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + (this.getHeight() - CFG.CIV_FLAG_HEIGHT) / 2 + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT / 3);
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + this.iButtonsPosX + Button_Diplomacy.iDiploWidth + (CFG.CIV_FLAG_WIDTH + CFG.PADDING) * this.iHoveredID + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + (this.getHeight() - CFG.CIV_FLAG_HEIGHT) / 2 + iTranslateY + CFG.CIV_FLAG_HEIGHT - CFG.CIV_FLAG_HEIGHT / 3, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT / 3, false, true);
            oSB.setColor(Color.WHITE);
        }
        try {
            oSB.flush();
            ScissorStack.popScissors();
        }
        catch (final IllegalStateException ex) {}
    }
    
    @Override
    protected void updateHover(final int nPosX, final int nPosY, final int menuPosX, final int menuPosY) {
        if (nPosX >= menuPosX + this.getPosX() && nPosX <= menuPosX + this.getPosX() + this.getWidth() && nPosY >= menuPosY + this.getPosY() && nPosY <= menuPosY + this.getPosY() + this.getHeight()) {
            for (int i = 0; i < this.lCivs.size(); ++i) {
                if (nPosX >= menuPosX + this.getPosX() + this.iButtonsPosX + Button_Diplomacy.iDiploWidth + (CFG.CIV_FLAG_WIDTH + CFG.PADDING) * i && nPosX <= menuPosX + this.getPosX() + this.iButtonsPosX + Button_Diplomacy.iDiploWidth + (CFG.CIV_FLAG_WIDTH + CFG.PADDING) * i + (CFG.CIV_FLAG_WIDTH + CFG.PADDING)) {
                    this.setHoveredID(i);
                    return;
                }
            }
        }
        this.setHoveredID(-1);
    }
    
    private final void setHoveredID(final int nHoveredID) {
        if (this.iHoveredID != nHoveredID) {
            this.iHoveredID = nHoveredID;
            this.buildElementHover();
        }
    }
    
    @Override
    protected void buildElementHover() {
        try {
            try {
                final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                if (this.iDiploImageID == Images.diplo_alliance) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AlliedWith") + ": "));
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID), CFG.PADDING, 0));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1, CFG.PADDING, 0));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_war) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AtWarWith") + ": "));
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID), CFG.PADDING, 0));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1, CFG.PADDING, 0));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_defensive_pact) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefensivePact"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getDefensivePact(CFG.getActiveCivInfo(), this.lCivs.get(this.iHoveredID))), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getDefensivePact(CFG.getActiveCivInfo(), this.lCivs.get(this.iHoveredID))) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.top_gold2) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WarReparations"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCiv(CFG.getActiveCivInfo()).getWarReparationsPays_TurnsLeft(this.lCivs.get(this.iHoveredID))), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCiv(CFG.getActiveCivInfo()).getWarReparationsPays_TurnsLeft(this.lCivs.get(this.iHoveredID))) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_relations_inc) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ImprovingRelationsWith"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName()));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_relations) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ImprovingRelationsFrom") + ":", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName()));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_relations_dec) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DiplomaticRelationsAreSuspended"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCiv(CFG.getActiveCivInfo()).getCivilization_Diplomacy_GameData().isEmbassyClosed_Turns(this.lCivs.get(this.iHoveredID))), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCiv(CFG.getActiveCivInfo()).getCivilization_Diplomacy_GameData().isEmbassyClosed_Turns(this.lCivs.get(this.iHoveredID))) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_loan) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Loans") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + this.lCivs.size(), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_gift) {
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AGiftFromCivA", CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName()), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AGiftFromCivA", CFG.langManager.get("Undiscovered")), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                }
                else if (this.iDiploImageID == Images.hre_icon) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IsPartOfHRE"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName()));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_truce) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("HasATruceWith"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCivTruce(CFG.getActiveCivInfo(), this.lCivs.get(this.iHoveredID))), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCivTruce(CFG.getActiveCivInfo(), this.lCivs.get(this.iHoveredID))) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_non_aggression) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("NonAggressionPact"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCivNonAggressionPact(CFG.getActiveCivInfo(), this.lCivs.get(this.iHoveredID))), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCivNonAggressionPact(CFG.getActiveCivInfo(), this.lCivs.get(this.iHoveredID))) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_access_has) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("GivesMilitaryAccess"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getMilitaryAccess(this.lCivs.get(this.iHoveredID), CFG.getActiveCivInfo())), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getMilitaryAccess(this.lCivs.get(this.iHoveredID), CFG.getActiveCivInfo())) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_access_gives) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("HaveMilitaryAccess"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getMilitaryAccess(CFG.getActiveCivInfo(), this.lCivs.get(this.iHoveredID))), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getMilitaryAccess(CFG.getActiveCivInfo(), this.lCivs.get(this.iHoveredID))) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_guarantee_gives) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("GuaranteeIndependence"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getGuarantee(CFG.getActiveCivInfo(), this.lCivs.get(this.iHoveredID))), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getGuarantee(CFG.getActiveCivInfo(), this.lCivs.get(this.iHoveredID))) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_guarantee_has) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("GuaranteeTheirIndependence"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getGuarantee(this.lCivs.get(this.iHoveredID), CFG.getActiveCivInfo())), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getGuarantee(this.lCivs.get(this.iHoveredID), CFG.getActiveCivInfo())) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_vassal) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Vassal") + ": "));
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID), CFG.PADDING, 0));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1, CFG.PADDING, 0));
                    }
                    //add new autonomy text
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_vassal));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.getActiveCivInfo()).getVassal_AutonomyStatus(this.lCivs.get(this.iHoveredID)).getName()));

                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_heart) {
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName() + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + (int)(CFG.game.getCivRelation_OfCivB(CFG.getActiveCivInfo(), this.lCivs.get(this.iHoveredID)) * 10.0f) / 10.0f, CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_heart, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else if (this.iDiploImageID == Images.diplo_rivals) {
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName() + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)(CFG.game.getCivRelation_OfCivB(CFG.getActiveCivInfo(), this.lCivs.get(this.iHoveredID)) * 10.0f) / 10.0f, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_rivals, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                else {
                    if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(this.iHoveredID) >= 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(this.iHoveredID)));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                    }
                    else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                    }
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
            catch (final IndexOutOfBoundsException ex) {
                if (this.iDiploImageID == Images.diplo_alliance) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(CFG.game.getAlliance(CFG.game.getCiv(CFG.getActiveCivInfo()).getAllianceID()).getAllianceName()), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_war) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AtWarWith"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_non_aggression) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("HasSignedNonAggressionPactWith"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCivNonAggressionPact(CFG.getActiveCivInfo(), this.lCivs.get(i))), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCivNonAggressionPact(CFG.getActiveCivInfo(), this.lCivs.get(i))) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_truce) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TruceWith"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCivTruce(CFG.getActiveCivInfo(), this.lCivs.get(i))), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCivTruce(CFG.getActiveCivInfo(), this.lCivs.get(i))) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_loan) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Loans") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text("" + this.lCivs.size(), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.top_gold2) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WarReparations"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCiv(CFG.getActiveCivInfo()).getWarReparationsPays_TurnsLeft(this.lCivs.get(i))), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCiv(CFG.getActiveCivInfo()).getWarReparationsPays_TurnsLeft(this.lCivs.get(i))) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_defensive_pact) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefensivePact"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getDefensivePact(CFG.getActiveCivInfo(), this.lCivs.get(i))), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getDefensivePact(CFG.getActiveCivInfo(), this.lCivs.get(i))) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_relations_inc) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ImprovingRelationsWith"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_relations) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ImprovingRelationsFrom"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_relations_dec) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DiplomaticRelationsAreSuspended"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCiv(CFG.getActiveCivInfo()).getCivilization_Diplomacy_GameData().isEmbassyClosed_Turns(this.lCivs.get(i))), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCiv(CFG.getActiveCivInfo()).getCivilization_Diplomacy_GameData().isEmbassyClosed_Turns(this.lCivs.get(i))) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_gift) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Gift"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.hre_icon) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IsPartOfHRE"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_access_has) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("GivesMilitaryAccess"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getMilitaryAccess(this.lCivs.get(i), CFG.getActiveCivInfo())), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getMilitaryAccess(this.lCivs.get(i), CFG.getActiveCivInfo())) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_access_gives) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("HaveMilitaryAccess"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getMilitaryAccess(CFG.getActiveCivInfo(), this.lCivs.get(i))), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getMilitaryAccess(CFG.getActiveCivInfo(), this.lCivs.get(i))) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_guarantee_gives) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("GuaranteeIndependence"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getGuarantee(CFG.getActiveCivInfo(), this.lCivs.get(i))), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getGuarantee(CFG.getActiveCivInfo(), this.lCivs.get(i))) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_guarantee_has) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("GuaranteeTheirIndependence"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getGuarantee(this.lCivs.get(i), CFG.getActiveCivInfo())), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getGuarantee(this.lCivs.get(i), CFG.getActiveCivInfo())) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_vassal) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Vassals"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName()));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        //add new autonomy text
                        nData2.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                        nData2.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_vassal));
                        nData2.add(new MenuElement_Hover_v2_Element_Type_Text((CFG.game.getCiv(CFG.getActiveCivInfo()).getVassal_AutonomyStatus(this.lCivs.get(i))).getName()));

                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_heart) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("FriendlyCivilizations"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName() + ": "));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text("+" + (int)(CFG.game.getCivRelation_OfCivB(CFG.getActiveCivInfo(), this.lCivs.get(i)) * 10.0f) / 10.0f, CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                if (this.iDiploImageID == Images.diplo_rivals) {
                    final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
                    final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Enemies"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                    nData2.add(new MenuElement_Hover_v2_Element_Type_Image(this.iDiploImageID, CFG.PADDING, 0));
                    nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                    nData2.clear();
                    for (int i = 0; i < this.lCivs.size(); ++i) {
                        if (CFG.FOG_OF_WAR < 2 || this.lCivs.get(i) >= 0) {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.lCivs.get(i)));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.lCivs.get(i)).getCivName() + ": "));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)(CFG.game.getCivRelation_OfCivB(CFG.getActiveCivInfo(), this.lCivs.get(i)) * 10.0f) / 10.0f, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                        }
                        else {
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered")));
                        }
                        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
                        nData2.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements2);
                    return;
                }
                this.menuElementHover = null;
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            this.menuElementHover = null;
            throw ex;
        }
    }
    
    private final void updateMoveable() {
        if (this.getButtonsWidth() - CFG.PADDING > this.getWidth() - Button_Diplomacy.iDiploWidth) {
            this.moveable = true;
        }
        else {
            this.moveable = false;
            this.iButtonsPosX = 0;
        }
    }
    
    private final int getButtonsWidth() {
        return (CFG.CIV_FLAG_WIDTH + CFG.PADDING) * this.lCivs.size() + CFG.PADDING;
    }
    
    @Override
    protected boolean getMoveable() {
        return this.moveable;
    }
    
    @Override
    protected int getCurrent() {
        return this.iButtonsPosX;
    }
    
    @Override
    protected void setCurrent(int nButtonsPosX) {
        if (nButtonsPosX > 0) {
            nButtonsPosX = 0;
            CFG.menuManager.setUpdateSliderMenuPosX(true);
            this.scrollModeY = false;
        }
        else if (nButtonsPosX < -(this.getButtonsWidth() - this.getWidth())) {
            nButtonsPosX = -(this.getButtonsWidth() - this.getWidth());
            CFG.menuManager.setUpdateSliderMenuPosX(true);
            this.scrollModeY = false;
        }
        if (this.iButtonsPosX != nButtonsPosX) {
            this.iButtonsPosX = nButtonsPosX;
            CFG.setRender_3(true);
        }
    }
    
    @Override
    protected boolean getIsScrollable() {
        return this.moveable;
    }
    
    @Override
    protected void srollByWheel(final int nScoll) {
        this.scrollModeY = false;
        this.setCurrent(this.getCurrent() + nScoll);
    }
    
    @Override
    protected boolean getAnotherView() {
        return false;
    }
    
    @Override
    protected final void scrollTheMenu() {
        if (this.moveable && this.iScrollPosX > 0 && this.iScrollPosX2 > 0 && Math.abs(this.iScrollPosX - this.iScrollPosX2) > 3.0f * CFG.DENSITY) {
            this.fScrollNewMenuPosY = (this.iScrollPosX - this.iScrollPosX2) * 1.25f;
            this.scrollModeY = true;
        }
    }

    //actionelement, make center to civ on click in civ has capital and is met change//
    @Override
    protected void setAnotherView(final boolean inAnotherClass) {
        //if valid civ with more than one province and met by player already
        if (this.iHoveredID >= 0 && (Integer)this.lCivs.get(this.iHoveredID) >= 0 && CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getNumOfProvinces() > 0 && CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCapitalProvinceID() > 0 && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetCivilization(this.lCivs.get(this.iHoveredID))) {
            CFG.map.getMapScroll().stopScrollingTheMap();
            CFG.map.getMapCoordinates().centerToCapital_OrMetProvinceCivID(this.lCivs.get(this.iHoveredID));

            CFG.toast.setInView(CFG.game.getCiv(this.lCivs.get(this.iHoveredID)).getCivName(), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            CFG.toast.setTimeInView(1500);
            CFG.setActiveCivInfo(this.lCivs.get(this.iHoveredID));

            //change refresh based on if game or not
            if (CFG.menuManager.getInCreateNewGame()) {
                CFG.updateActiveCivInfo_CreateNewGame();
            } else {
                CFG.updateActiveCivInfo_InGame();
            }
        } else if (!CFG.isAndroid()) {
            CFG.toast.setInView(CFG.langManager.get("XDoesNotExist", CFG.langManager.get("Civilization")));
            CFG.toast.setTimeInView(1500);
        }
    }

    @Override
    protected final void setScrollPosY(final int iScrollPosX) {
        this.iScrollPosX2 = this.iScrollPosX;
        this.iScrollPosX = iScrollPosX;
    }
    
    @Override
    protected void setTypeOfButton(final TypeOfButton typeOfButton) {
        final int n = -1;
        this.iScrollPosX2 = n;
        this.iScrollPosX = n;
        this.scrollModeY = false;
    }
    
    @Override
    protected void setMax(final int iMax) {
        this.row = (iMax == 1);
    }
}
