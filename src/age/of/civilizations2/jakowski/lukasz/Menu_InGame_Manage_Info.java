package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Menu_InGame_Manage_Info extends SliderMenu {
   protected static final int ANIMATION_TIME = 175;
   protected static long lTime = 0L;
   protected static boolean hideAnimation = true;
   protected static final float FONT_SIZE_GREAT = 0.7F;
   protected static final float FONT_SIZE_INFO = 0.8F;

   protected Menu_InGame_Manage_Info() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new Text_Scrollable((String)null, CFG.PADDING * 4, CFG.PADDING * 3, CFG.CIV_INFO_MENU_WIDTH - CFG.PADDING * 5, CFG.COLOR_TEXT_CIV_NAME) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_CIV_NAME_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_CIV_NAME_HOVERED : CFG.COLOR_TEXT_CIV_NAME) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }

         protected void buildElementHover() {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("UpdateLeaderName"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected void draw_Element(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            if (CFG.game.getCiv(CFG.getActiveCivInfo()).getCivID() != CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()) {
               super.draw_Element(oSB, iTranslateX + CFG.PADDING + (int)((float)CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getWidth() * Menu_InGame_Manage_Info.this.getImageScale(CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getHeight())), iTranslateY, isActive, scrollableY);
               CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().draw(oSB, this.getPosX() + this.getCurrent() + iTranslateX, this.getPosY() - CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getHeight() + (int)((float)this.getHeight() - (float)CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getHeight() * Menu_InGame_Manage_Info.this.getImageScale(CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getHeight())) / 2 + iTranslateY, (int)((float)CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getWidth() * Menu_InGame_Manage_Info.this.getImageScale(CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getHeight())), (int)((float)CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getHeight() * Menu_InGame_Manage_Info.this.getImageScale(CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getHeight())));
               ImageManager.getImage(Images.flag_rect).draw(oSB, this.getPosX() + this.getCurrent() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.flag_rect).getHeight() + (int)((float)this.getHeight() - (float)CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getHeight() * Menu_InGame_Manage_Info.this.getImageScale(CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getHeight())) / 2 + iTranslateY, (int)((float)CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getWidth() * Menu_InGame_Manage_Info.this.getImageScale(CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getHeight())), (int)((float)CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getHeight() * Menu_InGame_Manage_Info.this.getImageScale(CFG.game.getCiv(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()).getFlag().getHeight())));
            } else {
               super.draw_Element(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
            }

         }

      });
      menuElements.add(new Text((String)null, CFG.PADDING * 4, CFG.PADDING * 4 + CFG.TEXT_HEIGHT) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawTextWithShadow(oSB, this.sText, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getColor(isActive));
            CFG.fontMain.getData().setScale(1.0F);
         }

         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_RANK_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_RANK_HOVER : CFG.COLOR_TEXT_RANK) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }

         protected int getWidth() {
            return CFG.CIV_INFO_MENU_WIDTH - CFG.PADDING * 5 - 2;
         }

         protected void buildElementHover() {
            this.menuElementHover = CFG.game.getHover_LeaderOfCiv(CFG.getActiveCivInfo());
         }
      });
      menuElements.add(new Button_Transparent(0, 0, CFG.CIV_INFO_MENU_WIDTH - 1, ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4 - 1, false));
      this.initMenu(new SliderMenuTitle("", 0, false, false), CFG.GAME_WIDTH - CFG.CIV_INFO_MENU_WIDTH, ImageManager.getImage(Images.top_left).getHeight() + CFG.PADDING * 3, CFG.CIV_INFO_MENU_WIDTH, ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4, menuElements, false, true);
      this.updateLanguage();
   }

   protected void updateLanguage() {
   }

   private final float getImageScale() {
      return (float)CFG.TEXT_HEIGHT * 0.7F / (float)CFG.CIV_FLAG_HEIGHT;
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (lTime + 175L >= System.currentTimeMillis()) {
         if (hideAnimation) {
            iTranslateX += (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - lTime) / 175.0F));
         } else {
            iTranslateX -= -this.getWidth() + (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - lTime) / 175.0F));
         }

         CFG.setRender_3(true);
      } else if (hideAnimation) {
         super.setVisible(false);
         return;
      }

      ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, this.getWidth(), this.getHeight(), false, false);
      oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.275F));
      ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth(), this.getHeight() - 1);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(CFG.COLOR_GRADIENT_LIGHTER_DARK_BLUE.r, CFG.COLOR_GRADIENT_LIGHTER_DARK_BLUE.g, CFG.COLOR_GRADIENT_LIGHTER_DARK_BLUE.b, 1.0F));
      ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getMenuPosY() - ImageManager.getImage(Images.gradient).getHeight() + this.getHeight() - this.getHeight() / 2 + iTranslateY, this.getWidth() - 2, this.getHeight() / 2, true, true);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F));
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getMenuPosY() + this.getHeight() - ImageManager.getImage(Images.pix255_255_255).getHeight() - 2 + iTranslateY, this.getWidth() - 2, 1);
      oSB.setColor(CFG.COLOR_NEW_GAME_EDGE_LINE);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getMenuPosY() + this.getHeight() - ImageManager.getImage(Images.pix255_255_255).getHeight() - 1 + iTranslateY, this.getWidth() - 2, 1);
      oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.65F));
      ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getMenuPosY() + this.getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight() - 1 + iTranslateY, this.getWidth() - 2, 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
      ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getMenuPosY() + this.getHeight() - ImageManager.getImage(Images.slider_gradient).getHeight() - 1 + iTranslateY, this.getWidth() / 4, 1);
      ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() - 2 - this.getWidth() / 4 + iTranslateX, this.getMenuPosY() + this.getHeight() - ImageManager.getImage(Images.slider_gradient).getHeight() - 1 + iTranslateY, this.getWidth() / 4, 1, false, false);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, 1 + iTranslateY, sliderMenuIsActive);
      if (AoCGame.LEFT != 0) {
         oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
         ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, 1, this.getHeight(), true, false);
         oSB.setColor(Color.WHITE);
      }

   }

   protected void drawCloseButton(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      this.getCloseButtonImage(sliderMenuIsActive).draw(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.btn_close).getWidth() * 3 / 5 + iTranslateX, this.getPosY() - this.getTitle().getHeight() - ImageManager.getImage(Images.btn_close).getHeight() + iTranslateY, ImageManager.getImage(Images.btn_close).getWidth() * 3 / 5, ImageManager.getImage(Images.btn_close).getHeight() * 3 / 5);
   }

   private final float getImageScale(int nImageHeight) {
      return (float)CFG.TEXT_HEIGHT / (float)nImageHeight < 1.0F ? (float)CFG.TEXT_HEIGHT / (float)nImageHeight : 1.0F;
   }

   protected void onHovered() {
      CFG.menuManager.setOrderOfMenu_InGame_ManageCiv_Info();
   }

   protected void actionElement(int iID) {
      CFG.menuManager.setOrderOfMenu_InGame_ManageCiv_Info();
      switch (iID) {
         case 0:
            Keyboard.changeLeaderNameMode = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
            CFG.updateKeyboard_Actions();
            CFG.showKeyboard();
            break;
         case 1:
            break;
      }

   }

   protected void setVisible(boolean visible) {
      if (visible) {
         super.setVisible(visible);
         this.setHideAnimation(false);
      } else {
         this.setHideAnimation(true);
      }

   }

   protected final void setHideAnimation(boolean hideAnimation) {
      if (hideAnimation != Menu_InGame_Manage_Info.hideAnimation) {
         if (lTime > System.currentTimeMillis() - 175L) {
            lTime = System.currentTimeMillis() - (175L - (System.currentTimeMillis() - lTime));
         } else {
            lTime = System.currentTimeMillis();
         }

         CFG.setRender_3(true);
      }

      Menu_InGame_Manage_Info.hideAnimation = hideAnimation;
   }
}
