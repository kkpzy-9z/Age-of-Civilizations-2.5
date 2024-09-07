package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_Assimilate extends SliderMenu {
   private int iProvinceID = -1;
   private ArrayList<Integer> assimOrder;

   protected Menu_InGame_Assimilate() {
      List menuElements = new ArrayList();
      int tempWidth = CFG.CIV_INFO_MENU_WIDTH * 2;
      int tY = CFG.PADDING;
      menuElements.add(new Button_Flag_JustFrame(CFG.PADDING, tY, true));
      int var10000 = tY + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      int tempMenuPosY = ImageManager.getImage(Images.top_left2).getHeight() + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 5;
      this.initMenu(new SliderMenuTitle(CFG.langManager.get("Assimilate"), CFG.BUTTON_HEIGHT * 3 / 5, true, true), CFG.GAME_WIDTH / 2 - tempWidth / 2, tempMenuPosY, tempWidth, ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING + tempMenuPosY > CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 ? Math.max(CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 - tempMenuPosY, (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 6) : ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, menuElements, false, true);
      this.updateLanguage();
   }

   protected Menu_InGame_Assimilate(int nProvinceID) {
      List menuElements = new ArrayList();
      this.iProvinceID = nProvinceID;
      this.assimOrder = new ArrayList<Integer>();
      int tempWidth = CFG.CIV_INFO_MENU_WIDTH * 2;
      int tY = 0;
      int nMax = 1;
      int i;
      if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() >= (long)DiplomacyManager.assimilateCost(this.iProvinceID, 50)) {
         nMax = 50;
      } else {
         for(i = 49; i >= 5; --i) {
            nMax = i;
            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() >= (long)DiplomacyManager.assimilateCost(this.iProvinceID, i)) {
               break;
            }
         }
      }

      menuElements.add(new Button_Build_Assimilate(this.iProvinceID, CFG.langManager.get("Assimilate") + ": ", CFG.game.getProvince(this.iProvinceID).getName().length() > 0 ? CFG.game.getProvince(this.iProvinceID).getName() : CFG.langManager.get("Province"), Game_Calendar.getCurrentDate(), Images.diplo_popstability, DiplomacyManager.assimilateCost(BuildingsManager.iBuildInProvinceID, nMax), 6, 0, tY, CFG.BUTTON_WIDTH * 2) {
         protected int getWidth() {
            return Menu_InGame_Assimilate.this.getElementW() * 2;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PromoteOurTraditionsAndCulturesInThisProvince")));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("APercentageOfTheLocalsWillConvertToOurNationality")));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ProvinceStabilityWillBeIncreased"), CFG.COLOR_TEXT_MODIFIER_POSITIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Slider_FlagAction_Date(CFG.langManager.get("NumberOfTurns"), CFG.PADDING, tY, this.getElementW() * 2 - CFG.PADDING * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 2 + CFG.PADDING * 4, 1, nMax, nMax, 0.65F) {
         protected String getDrawText() {
            return CFG.langManager.get("TurnsX", this.getCurrent());
         }

         protected int getSliderHeight() {
            return CFG.PADDING * 2;
         }

         protected void actionElement(int iID) {
            Menu_InGame_Assimilate.this.updateLimits();
         }

         protected int getWidth() {
            return Menu_InGame_Assimilate.this.getElementW() * 2 - CFG.PADDING * 2;
         }

         protected Color getColorLEFT() {
            return new Color((float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getB() / 255.0F, 0.75F);
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      menuElements.add(new Slider_FlagAction_Clear(CFG.langManager.get("AmountAssimilate"), CFG.PADDING, tY, this.getElementW() * 2 - CFG.PADDING * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 2 + CFG.PADDING * 4, 0, nMax, nMax, 0.65F) {
         protected String getDrawText() {
            return CFG.langManager.get("ProvincesX", this.getCurrent());
         }

         protected int getSliderHeight() {
            return CFG.PADDING * 2;
         }

         protected void actionElement(int iID) {
            Menu_InGame_Assimilate.this.updateLimits();
         }

         protected int getWidth() {
            return Menu_InGame_Assimilate.this.getElementW() * 2 - CFG.PADDING * 2;
         }

         protected Color getColorLEFT() {
            return new Color((float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getB() / 255.0F, 0.75F);
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      tY += CFG.PADDING;
      menuElements.add(new Button_FlagActionSliderStyle(CFG.langManager.get("Cancel"), -1, CFG.PADDING, tY, CFG.BUTTON_WIDTH, true) {
         protected int getWidth() {
            return (Menu_InGame_Assimilate.this.getW() - CFG.PADDING * 3) / 2;
         }

         protected void actionElement(int iID) {
            Menu_InGame_Assimilate.this.clickCancel();
         }
      });
      menuElements.add(new Button_FlagActionSliderStyle(CFG.langManager.get("Confirm"), -1, 2, tY, CFG.BUTTON_WIDTH, !CFG.game.getProvince(this.iProvinceID).isOccupied()) {
         protected int getPosX() {
            return Menu_InGame_Assimilate.this.getW() - (Menu_InGame_Assimilate.this.getW() - CFG.PADDING * 3) / 2 - CFG.PADDING;
         }

         protected int getWidth() {
            return (Menu_InGame_Assimilate.this.getW() - CFG.PADDING * 3) / 2;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            if (!this.getClickable() && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getDiplomacyPoints() >= 6 && CFG.game.getProvince(Menu_InGame_Assimilate.this.iProvinceID).isOccupied()) {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("OccupiedProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(Menu_InGame_Assimilate.this.iProvinceID).getCivID(), CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Space());
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            }

            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Assimilate") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(Menu_InGame_Assimilate.this.iProvinceID).getName().length() > 0 ? CFG.game.getProvince(Menu_InGame_Assimilate.this.iProvinceID).getName() : CFG.langManager.get("Province")));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(Menu_InGame_Assimilate.this.iProvinceID).getCivID(), CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PromoteOurTraditionsAndCulturesInThisProvince")));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("APercentageOfTheLocalsWillConvertToOurNationality")));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ProvinceStabilityWillBeIncreased"), CFG.COLOR_TEXT_MODIFIER_POSITIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            ImageManager.getImage(Images.diplo_popstability).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)(((float)this.getTextWidth() * 0.8F + (float)ImageManager.getImage(Images.diplo_popstability).getWidth() + (float)CFG.PADDING) / 2.0F) + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.diplo_popstability).getHeight() / 2 + iTranslateY);
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawText(oSB, this.getText(), this.getPosX() + (this.getTextPos() < 0 ? this.getWidth() / 2 - (int)(((float)this.getTextWidth() * 0.8F + (float)ImageManager.getImage(Images.diplo_popstability).getWidth() + (float)CFG.PADDING) / 2.0F) + ImageManager.getImage(Images.diplo_popstability).getWidth() + CFG.PADDING : this.getTextPos()) + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.8F / 2.0F) + iTranslateY, this.getColor(isActive));
            CFG.fontMain.getData().setScale(1.0F);
         }

         protected boolean getClickable() {
            return super.getClickable() && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getDiplomacyPoints() >= 6;
         }
      });
      i = ImageManager.getImage(Images.top_left2).getHeight() + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 5;
      this.initMenu(new SliderMenuTitle(CFG.langManager.get("Assimilate"), CFG.BUTTON_HEIGHT * 3 / 5, true, true) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX - 2 + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), nWidth + 4 - ImageManager.getImage(Images.dialog_title).getWidth(), this.getHeight());
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX + nWidth + 2 - ImageManager.getImage(Images.dialog_title).getWidth() + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), ImageManager.getImage(Images.dialog_title).getWidth(), this.getHeight(), true, false);
            oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getB() / 255.0F, 0.165F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() + 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, this.getHeight() - 2, false, true);
            oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getB() / 255.0F, 0.375F));
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() * 2 / 3 - ImageManager.getImage(Images.gradient).getHeight(), nWidth, this.getHeight() * 2 / 3, false, true);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F));
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + iTranslateX, nPosY - CFG.PADDING - ImageManager.getImage(Images.gradient).getHeight(), nWidth, CFG.PADDING, false, true);
            oSB.setColor(CFG.COLOR_NEW_GAME_EDGE_LINE);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.pix255_255_255).getHeight(), nWidth, 1);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, 1);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, 1);
            oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.45F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.slider_gradient).getHeight(), nWidth / 2, 1);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + nWidth - nWidth / 2 + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.slider_gradient).getHeight(), nWidth / 2, 1, true, false);
            oSB.setColor(Color.WHITE);
            CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getFlag().draw(oSB, Menu_InGame_Assimilate.this.getPosX() + CFG.PADDING * 2 + iTranslateX, Menu_InGame_Assimilate.this.getPosY() - this.getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 - CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getFlag().getHeight(), CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, Menu_InGame_Assimilate.this.getPosX() + CFG.PADDING * 2 + iTranslateX, Menu_InGame_Assimilate.this.getPosY() - this.getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2);
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawText(oSB, this.getText(), nPosX + (int)((float)nWidth - (float)this.getTextWidth() * 0.8F) / 2 + iTranslateX, 2 + nPosY - this.getHeight() + (int)((float)this.getHeight() - (float)this.getTextHeight() * 0.8F) / 2, Color.WHITE);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, CFG.GAME_WIDTH / 2 - tempWidth / 2, i, tempWidth, ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING + i > CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 ? Math.max(CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 - i, (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 6) : ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, menuElements, true, true);

      this.updateLimits();
      this.updateLanguage();
      this.getMenuElement(1).setCurrent(this.getMenuElement(1).getCurrent());
      this.getMenuElement(0).setCurrent(DiplomacyManager.assimilateCost(this.iProvinceID, this.getMenuElement(1).getCurrent()));
      Menu_InGame_OfferAlliance.lTime = System.currentTimeMillis();
   }

   protected void updateLanguage() {
   }

   protected void updateLimits() {
      int nTurns = Menu_InGame_Assimilate.this.getMenuElement(1).getCurrent();
      ArrayList<Integer> lowStab = new ArrayList<>();

      for (int i = 0; i < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getNumOfProvinces(); i++) {
         int iProvinceID = CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getProvinceID(i);
         if (CFG.game.getProvince(iProvinceID).getProvinceStability() < 0.85F && !CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isAssimilateOrganized(iProvinceID)) {
            lowStab.add(iProvinceID);
         }
      }

      ArrayList<Integer> sorted = new ArrayList<Integer>();
      if (lowStab.contains(this.iProvinceID)) {
         lowStab.remove((Integer) this.iProvinceID);
      }
      sorted.add(this.iProvinceID);

      int lowStabSize = lowStab.size();
      while (lowStabSize > 0) {

         int tBest = -1;
         for (int j : lowStab) {
            if (tBest < 0) {
               tBest = j;
            } else if (CFG.game.getProvince(tBest).getProvinceStability() > CFG.game.getProvince(j).getProvinceStability()) {
               tBest = j;
            }
         }
         lowStab.remove((Integer) tBest);
         lowStabSize -= 1;

         sorted.add(tBest);
         if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getDiplomacyPoints() < (6 * sorted.size()) || CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() < ((long)DiplomacyManager.assimilateCost(sorted, nTurns))) {
            sorted.remove((Integer) tBest);
            break;
         }
      }


      this.getMenuElement(2).setMax(sorted.size());
      if (((Slider_FlagAction_Clear)this.getMenuElement(2)).getCurrent() > sorted.size()) {
         ((Slider_FlagAction_Clear)this.getMenuElement(2)).setCurrent(sorted.size());
      }
      ((Slider_FlagAction_Clear)this.getMenuElement(2)).updateSlider(-1);

      ((Button_Build_Assimilate) this.getMenuElement(0)).setCurrent(DiplomacyManager.assimilateCost(sorted.subList(0, this.getMenuElement(2).getCurrent()), nTurns), (6 * ((Slider_FlagAction_Clear)this.getMenuElement(2)).getCurrent()));


      this.assimOrder.clear();
      this.assimOrder.addAll(sorted);
      sorted.clear();
      lowStab.clear();
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (Menu_InGame_OfferAlliance.lTime + 200L >= System.currentTimeMillis()) {
         Rectangle clipBounds = new Rectangle((float)(this.getPosX() - 2), (float)(CFG.GAME_HEIGHT - this.getPosY()), (float)(this.getWidth() + 4), (float)(-((int)((float)(this.getHeight() + CFG.PADDING) * ((float)(System.currentTimeMillis() - Menu_InGame_OfferAlliance.lTime) / 200.0F)))));
         oSB.flush();
         ScissorStack.pushScissors(clipBounds);
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + 4, this.getHeight() + CFG.PADDING, false, true);
         ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() + 2 + this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING, true, true);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
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
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
         ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth() - 4, this.getHeight() / 4);
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth() - 4, 1);
         oSB.setColor(Color.WHITE);
         this.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
         this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
         oSB.setColor(Color.WHITE);
         this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected final void clickCancel() {
      this.setVisible(false);
   }

   protected final void clickConfirm() {
      boolean out = true;

      for (int i = 0; i < (int)(this.getMenuElement(2)).getCurrent(); i++) {
         CFG.toast.setInView(String.valueOf((int)(this.getMenuElement(2)).getCurrent()));
         if (!DiplomacyManager.addAssimilate(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), assimOrder.get(i), Menu_InGame_Assimilate.this.getMenuElement(1).getCurrent())) {
            out = false;
         }
         if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_PROVINCE_STABILITY_MODE) {
            CFG.game.getProvince(assimOrder.get(i)).viewBool = true;
         }
      }

      if (out) {
         CFG.toast.setInView(CFG.langManager.get("Assimilate"), CFG.COLOR_TEXT_MODIFIER_POSITIVE);
         CFG.toast.setTimeInView(4500);
         CFG.gameAction.updateInGame_ProvinceInfo();
         if (CFG.menuManager.getInGame_ProvinceBuild_Visible()) {
            CFG.menuManager.setVisible_InGame_ProvinceBuild(true, true);
         }

         if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_PROVINCE_STABILITY_MODE) {
            if (CFG.menuManager.getVisible_InGame_View_Stats()) {
               CFG.menuManager.setVisible_InGame_ViewProvinceStability(true);
            }
         }

         CFG.soundsManager.playSound(SoundsManager.SOUND_ASSIMILATE);


         CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
         this.setVisible(false);
      }
   }

   protected final void actionElement(int iID) {
      switch (iID) {
         case 0: {
            CFG.game.setActiveProvinceID(this.iProvinceID);
            CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getActiveProvinceID());
         }
         case 4: {
            this.clickConfirm();
         }
         default: {
            this.getMenuElement(iID).actionElement(iID);
         }
      }

   }

   protected final int getW() {
      return this.getWidth() - 4;
   }

   protected final int getElementW() {
      return this.getW() / 2;
   }

   protected void setVisible(boolean visible) {
      super.setVisible(visible);
      if (!visible) {
         for(int i = 0; i < this.getMenuElementsSize(); ++i) {
            this.getMenuElement(i).setVisible(false);
         }
      }

   }
}
