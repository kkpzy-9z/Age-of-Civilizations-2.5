package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_About extends SliderMenu {
   protected Menu_About() {
      List menuElements = new ArrayList();
      int tY = CFG.BUTTON_WIDTH / 2;
      menuElements.add(new Text_Scale("Age of History II", 0, AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, 1.0F) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }

         protected void actionElement(int iID) {
            CFG.GO_TO_LINK = "http://www.AgeofCivilizationsGame.com";
            CFG.setDialogType(Dialog.GO_TO_LINK);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("www.AgeofCivilizationsGame.com", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            if (isActive) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.85F));
            } else if (this.getIsHovered()) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.925F));
            }

            ImageManager.getImage(Images.gameLogo).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
            oSB.setColor(Color.WHITE);
         }

         protected int getWidth() {
            return ImageManager.getImage(Images.gameLogo).getWidth();
         }

         protected int getHeight() {
            return ImageManager.getImage(Images.gameLogo).getHeight();
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Text_Scale("www.AgeofCivilizationsGame.com", 0, AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, 0.9F) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : CFG.COLOR_TEXT_MODIFIER_NEUTRAL) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }

         protected void actionElement(int iID) {
            CFG.GO_TO_LINK = "http://www.AgeofCivilizationsGame.com";
            CFG.setDialogType(Dialog.GO_TO_LINK);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("www.AgeofCivilizationsGame.com", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      tY += CFG.BUTTON_HEIGHT / 4;
      menuElements.add(new Text_Scale("Programmer and Designer", 0, AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, 1.0F) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("Developer", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }

         protected void actionElement(int iID) {
            CFG.GO_TO_LINK = "http://www.LukaszJakowski.pl";
            CFG.setDialogType(Dialog.GO_TO_LINK);
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Text_Scale(CFG.getLukaszJakowski(), 0, AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, 0.9F) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : CFG.COLOR_TEXT_MODIFIER_NEUTRAL) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(1.0F, 1.0F, 1.0F, 1.0F), 0, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(0.8509804F, 0.11764706F, 0.23921569F, 1.0F), 0, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.getLukaszJakowski(), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(1.0F, 1.0F, 1.0F, 1.0F), 0, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(0.8509804F, 0.11764706F, 0.23921569F, 1.0F), 0, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("One (and a half) man army"));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("www.LukaszJakowski.pl", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Contact") + ": jakowskidev@gmail.com", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected void actionElement(int iID) {
            CFG.GO_TO_LINK = "http://www.LukaszJakowski.pl";
            CFG.setDialogType(Dialog.GO_TO_LINK);
         }

         protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
            float fScale = (float)CFG.TEXT_HEIGHT * 0.9F / (float)ImageManager.getImage(Images.flag_rect).getHeight();
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + (int)((float)this.getTextWidth() * 0.9F) + CFG.PADDING + iTranslateX, this.getPosY() + 1 + (int)((float)this.getHeight() / 2.0F - (float)CFG.TEXT_HEIGHT * 0.9F / 2.0F) - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, (int)((float)ImageManager.getImage(Images.flag_rect).getWidth() * fScale), (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * fScale));
            oSB.setColor(new Color(0.8509804F, 0.11764706F, 0.23921569F, 1.0F));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + (int)((float)this.getTextWidth() * 0.9F) + CFG.PADDING + iTranslateX, this.getPosY() + 1 + (int)((float)this.getHeight() / 2.0F - (float)CFG.TEXT_HEIGHT * 0.9F / 2.0F) - ImageManager.getImage(Images.pix255_255_255).getHeight() + (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * fScale) / 2 + iTranslateY, (int)((float)ImageManager.getImage(Images.flag_rect).getWidth() * fScale), (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * fScale) - (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * fScale) / 2);
            oSB.setColor(Color.WHITE);
            ImageManager.getImage(Images.flag_rect).draw(oSB, this.getPosX() + (int)((float)this.getTextWidth() * 0.9F) + CFG.PADDING + iTranslateX, this.getPosY() + 1 + (int)((float)this.getHeight() / 2.0F - (float)CFG.TEXT_HEIGHT * 0.9F / 2.0F) - ImageManager.getImage(Images.flag_rect).getHeight() + iTranslateY, (int)((float)ImageManager.getImage(Images.flag_rect).getWidth() * fScale), (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * fScale));
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      tY += CFG.BUTTON_HEIGHT / 4;
      menuElements.add(new Text_Scale("Publisher", 0, AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, 1.0F) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(1.0F, 1.0F, 1.0F, 1.0F), 0, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(0.8509804F, 0.11764706F, 0.23921569F, 1.0F), 0, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.getLukaszJakowskiGames(), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(1.0F, 1.0F, 1.0F, 1.0F), 0, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(0.8509804F, 0.11764706F, 0.23921569F, 1.0F), 0, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("Poland"));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("www.LukaszJakowski.pl", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Contact") + ": jakowskidev@gmail.com", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }

         protected void actionElement(int iID) {
            CFG.GO_TO_LINK = "http://www.LukaszJakowski.pl";
            CFG.setDialogType(Dialog.GO_TO_LINK);
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Text_Scale(CFG.getLukaszJakowskiGames(), 0, AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, 0.9F) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : CFG.COLOR_TEXT_MODIFIER_NEUTRAL) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(1.0F, 1.0F, 1.0F, 1.0F), 0, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(0.8509804F, 0.11764706F, 0.23921569F, 1.0F), 0, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.getLukaszJakowskiGames(), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(1.0F, 1.0F, 1.0F, 1.0F), 0, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(0.8509804F, 0.11764706F, 0.23921569F, 1.0F), 0, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("Poland"));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("www.LukaszJakowski.pl", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Contact") + ": jakowskidev@gmail.com", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected void actionElement(int iID) {
            CFG.GO_TO_LINK = "http://www.LukaszJakowski.pl";
            CFG.setDialogType(Dialog.GO_TO_LINK);
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      tY += CFG.BUTTON_HEIGHT / 4;
      menuElements.add(new Text_Scale(CFG.langManager.get("Music"), 0, AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, 1.0F) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Music"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Text_Scale("Kevin Macleod", 0, AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, 0.9F) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : CFG.COLOR_TEXT_MODIFIER_NEUTRAL) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(0.23529412F, 0.23137255F, 0.43137255F, 1.0F), 0, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(0.69803923F, 0.13333334F, 0.20392157F, 1.0F), 0, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(1.0F, 1.0F, 1.0F, 1.0F), 0, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("Kevin Macleod", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("https://www.youtube.com/user/kmmusic", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected void actionElement(int iID) {
            CFG.GO_TO_LINK = "https://www.youtube.com/user/kmmusic";
            CFG.setDialogType(Dialog.GO_TO_LINK);
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      tY += CFG.BUTTON_HEIGHT / 4;
      menuElements.add(new Text_Scale(CFG.langManager.get("Contact") + ": jakowskidev@gmail.com", 0, AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, 0.9F) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(1.0F, 1.0F, 1.0F, 1.0F), 0, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(0.8509804F, 0.11764706F, 0.23921569F, 1.0F), 0, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.getLukaszJakowski(), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(1.0F, 1.0F, 1.0F, 1.0F), 0, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(new Color(0.8509804F, 0.11764706F, 0.23921569F, 1.0F), 0, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("One man army"));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("www.LukaszJakowski.pl", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Contact") + ": jakowskidev@gmail.com", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Text_Scale("Twitter: @jakowskidev", 0, AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, 0.9F) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : CFG.COLOR_TEXT_MODIFIER_NEUTRAL) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }

         protected void actionElement(int iID) {
            CFG.GO_TO_LINK = "http://www.twitter.com/jakowskidev";
            CFG.setDialogType(Dialog.GO_TO_LINK);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("www.twitter.com/jakowskidev", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Text_Scale("2016 - 2018", 0, AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, 0.85F) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : CFG.COLOR_TEXT_MODIFIER_NEUTRAL) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      tY += CFG.BUTTON_HEIGHT / 4;
      menuElements.add(new Text_Scale("Special thanks to", 0, AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, 0.8F) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Text_Scrollable(CFG.langManager.get("SpecialThanks"), AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, (CFG.GAME_WIDTH - CFG.BUTTON_WIDTH) / 2, CFG.COLOR_TEXT_MODIFIER_NEUTRAL) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_CIV_NAME_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_CIV_NAME_HOVERED : CFG.COLOR_TEXT_MODIFIER_NEUTRAL) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 5;
      menuElements.add(new Text_Scale("Translators", 0, AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, 0.8F) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Text_Scrollable(CFG.langManager.get("Translators"), AoCGame.LEFT + CFG.BUTTON_WIDTH / 2, tY, (CFG.GAME_WIDTH - CFG.BUTTON_WIDTH) / 2, CFG.COLOR_TEXT_MODIFIER_NEUTRAL) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_CIV_NAME_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_CIV_NAME_HOVERED : CFG.COLOR_TEXT_MODIFIER_NEUTRAL) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 5;
      menuElements.add(new Button_Transparent(0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, true) {
         protected void actionElement(int iID) {
            Menu_About.this.onBackPressed();
         }
      });
      this.initMenu((SliderMenuTitle)null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT, menuElements);
      this.updateLanguage();
   }

   protected void updateLanguage() {
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
      ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, iTranslateY - ImageManager.getImage(Images.gradient).getHeight(), CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT * 3 / 4);
      oSB.setColor(new Color(0.0123F, 0.0123F, 0.0123F, 0.3F));
      ImageManager.getImage(Images.patt_square).draw(oSB, iTranslateX, iTranslateY - ImageManager.getImage(Images.patt_square).getHeight(), CFG.GAME_WIDTH, this.getHeight(), 0.0F, 0);
      oSB.setColor(Color.WHITE);
      CFG.drawLogo_Square(oSB, CFG.GAME_WIDTH - (CFG.BUTTON_HEIGHT * 3 + CFG.PADDING * 2) - CFG.BUTTON_WIDTH / 2 + iTranslateX, CFG.BUTTON_WIDTH / 2 + iTranslateY, CFG.BUTTON_HEIGHT * 3 + CFG.PADDING * 2);
      super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected final void actionElement(int iID) {
      this.getMenuElement(iID).actionElement(iID);
   }

   protected final void onBackPressed() {
      CFG.menuManager.setViewID(Menu.eMAINMENU);
      CFG.menuManager.setBackAnimation(true);
   }
}
