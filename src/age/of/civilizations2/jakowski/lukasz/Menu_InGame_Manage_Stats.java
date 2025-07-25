package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_Manage_Stats extends SliderMenu {
   protected Menu_InGame_Manage_Stats() {
      try {
         CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getDecisionsCount();
         CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(0);
      } catch (NullPointerException e) {
         DynamicEventManager_Leader.safeReplaceLeader(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
      }

      final List<MenuElement> menuElements = new ArrayList<MenuElement>();
      int offsetY = CFG.PADDING * 2;
      menuElements.add(new Text_CivInfo((String)null, CFG.PADDING * 2, offsetY));
      offsetY += CFG.TEXT_HEIGHT + CFG.PADDING;
      menuElements.add(new Graph_Circle_Politics(CFG.PADDING * 2, offsetY, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), (MenuElement_Hover)null, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(0), 1.0F - CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(0)) {
         protected void buildElementHover() {
            this.menuElementHover = CFG.game.getHover_Class(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), 0);
         }
      });
      ((Graph_Circle_Politics)menuElements.get(menuElements.size() - 1)).setWidth((CFG.CIV_INFO_MENU_WIDTH - CFG.PADDING * 4));
      offsetY += ((Graph_Circle_Politics)menuElements.get(menuElements.size() - 1)).getHeight_Perc() + CFG.PADDING * 2;

      menuElements.add(new Text_CivInfo((String)null, CFG.PADDING * 2, offsetY));
      offsetY += CFG.TEXT_HEIGHT + CFG.PADDING;
      menuElements.add(new Graph_Circle_Politics(CFG.PADDING * 2, offsetY, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), (MenuElement_Hover)null, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(1), 1.0F - CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(1)) {
         protected void buildElementHover() {
            this.menuElementHover = CFG.game.getHover_Class(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), 1);
         }
      });
      ((Graph_Circle_Politics)menuElements.get(menuElements.size() - 1)).setWidth((CFG.CIV_INFO_MENU_WIDTH - CFG.PADDING * 4));
      offsetY += ((Graph_Circle_Politics)menuElements.get(menuElements.size() - 1)).getHeight_Perc() + CFG.PADDING * 2;

      menuElements.add(new Text_CivInfo((String)null, CFG.PADDING * 2, offsetY));
      offsetY += CFG.TEXT_HEIGHT + CFG.PADDING;
      menuElements.add(new Graph_Circle_Politics(CFG.PADDING * 2, offsetY, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), (MenuElement_Hover)null, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(2), 1.0F - CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(2)) {
         protected void buildElementHover() {
            this.menuElementHover = CFG.game.getHover_Class(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), 2);
         }
      });
      ((Graph_Circle_Politics)menuElements.get(menuElements.size() - 1)).setWidth((CFG.CIV_INFO_MENU_WIDTH - CFG.PADDING * 4));
      offsetY += ((Graph_Circle_Politics)menuElements.get(menuElements.size() - 1)).getHeight_Perc() + CFG.PADDING * 2;

      int tempPosY = CFG.TEXT_HEIGHT + CFG.PADDING + (ImageManager.getImage(Images.top_left).getHeight() + CFG.PADDING * 3) + (ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4);
      this.initMenu(new SliderMenuTitle((String)null, CFG.TEXT_HEIGHT + CFG.PADDING * 2, false, false) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, Menu_InGame_Manage_Stats.this.getPosX() + iTranslateX, Menu_InGame_Manage_Stats.this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() - this.getHeight(), Menu_InGame_Manage_Stats.this.getWidth(), this.getHeight(), false, false);
            CFG.drawRect_InfoBox_Left_Title(oSB, Menu_InGame_Manage_Stats.this.getPosX() + iTranslateX - ImageManager.getImage(Images.new_game_top_edge_line_horizontal).getWidth(), Menu_InGame_Manage_Stats.this.getPosY() - this.getHeight(), Menu_InGame_Manage_Stats.this.getWidth(), this.getHeight());
            CFG.fontMain.getData().setScale(0.7F);
            CFG.drawTextWithShadow(oSB, this.getText(), nPosX + nWidth / 2 - (int)((float)this.getTextWidth() * 0.7F) / 2 + iTranslateX, nPosY - this.getHeight() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.7F) / 2, CFG.COLOR_TEXT_CIV_INFO_TITLE);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, CFG.GAME_WIDTH - CFG.CIV_INFO_MENU_WIDTH + ImageManager.getImage(Images.new_game_top_edge_line_horizontal).getWidth(), tempPosY, CFG.CIV_INFO_MENU_WIDTH, offsetY, menuElements, false, false);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.getTitle().setText(CFG.langManager.get("LeaderViews"));
      this.getMenuElement(0).setText(CFG.langManager.get("UpperClass"));
      this.getMenuElement(2).setText(CFG.langManager.get("MiddleClass"));
      this.getMenuElement(4).setText(CFG.langManager.get("LowerClass"));
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (Menu_InGame_Manage_Info.lTime + 175L >= System.currentTimeMillis()) {
         if (Menu_InGame_Manage_Info.hideAnimation) {
            iTranslateX += (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - Menu_InGame_Manage_Info.lTime) / 175.0F));
         } else {
            iTranslateX -= -this.getWidth() + (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - Menu_InGame_Manage_Info.lTime) / 175.0F));
         }

         CFG.setRender_3(true);
      } else if (Menu_InGame_Manage_Info.hideAnimation) {
         super.setVisible(false);
         return;
      }

      ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() + iTranslateX - ImageManager.getImage(Images.new_game_top_edge_line_horizontal).getWidth(), this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + iTranslateY, this.getWidth(), this.getHeight() + 2, false, true);
      this.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(Color.WHITE);
      CFG.drawRect_InfoBox_Left(oSB, this.getPosX() + iTranslateX + CFG.PADDING, this.getPosY() + CFG.PADDING + iTranslateY, this.getWidth() - CFG.PADDING * 2, this.getHeight() - CFG.PADDING * 2);

      this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);


   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected void onHovered() {
      CFG.menuManager.setOrderOfMenu_InGame_ManageCiv_Info();
   }

   protected void actionElement(int iID) {
      switch (iID) {
      }

   }

   protected void setVisible(boolean visible) {
      if (visible) {
         super.setVisible(visible);
      }

   }
}
