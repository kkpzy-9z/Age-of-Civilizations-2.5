package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Button_PlayAsVassal extends Button {
   private boolean visibleCheck = true;
   protected Button_PlayAsVassal(String sText, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, boolean isClickable, boolean checkboxState) {
      super.init(sText, iTextPositionX, iPosX, iPosY, iWidth, iHeight, isClickable, true, true, checkboxState, (TypeOfButton)null);
   }

   protected Button_PlayAsVassal(String sText, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, boolean isClickable, boolean checkboxState, boolean visibleCheck) {
      super.init(sText, iTextPositionX, iPosX, iPosY, iWidth, iHeight, isClickable, true, true, checkboxState, (TypeOfButton)null);
      this.visibleCheck = visibleCheck;
   }


   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      CFG.fontMain.getData().setScale(0.7F);
      CFG.drawTextWithShadow(oSB, this.getTextToDraw(), this.getPosX() + this.textPosition.getTextPosition() + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.iTextHeight * 0.7F / 2.0F) + iTranslateY, this.getColor(isActive));
      CFG.fontMain.getData().setScale(1.0F);
   }

   protected Checkbox buildCheckbox() {
      return this.checkbox ? new Checkbox() {
         public void drawCheckBox(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean scrollableY) {
            if (Button_PlayAsVassal.this.getCheckboxState()) {
               oSB.setColor(new Color(0.55F, 0.8F, 0.0F, 0.4F));
            } else {
               oSB.setColor(new Color(0.8F, 0.137F, 0.0F, 0.4F));
            }

            if (Button_PlayAsVassal.this.visibleCheck) {
               ImageManager.getImage(Images.slider_gradient).draw(oSB, Button_PlayAsVassal.this.getPosX() + Button_PlayAsVassal.this.getWidth() - Button_PlayAsVassal.this.getWidth() / 2 + iTranslateX, Button_PlayAsVassal.this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + 1 + iTranslateY, Button_PlayAsVassal.this.getWidth() / 2, Button_PlayAsVassal.this.getHeight() - 2, true, false);
               oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
               ImageManager.getImage(Images.gradient).draw(oSB, Button_PlayAsVassal.this.getPosX() + iTranslateX, Button_PlayAsVassal.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + 1 + iTranslateY, Button_PlayAsVassal.this.getWidth(), Button_PlayAsVassal.this.getHeight() / 4, false, false);
               ImageManager.getImage(Images.gradient).draw(oSB, Button_PlayAsVassal.this.getPosX() + iTranslateX, Button_PlayAsVassal.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + Button_PlayAsVassal.this.getHeight() - 1 + iTranslateY - Button_PlayAsVassal.this.getHeight() / 4, Button_PlayAsVassal.this.getWidth(), Button_PlayAsVassal.this.getHeight() / 4, false, true);
               oSB.setColor(Color.WHITE);
               CFG.drawRect_InfoBox_Left(oSB, Button_PlayAsVassal.this.getPosX() + iTranslateX, Button_PlayAsVassal.this.getPosY() + iTranslateY, Button_PlayAsVassal.this.getWidth(), Button_PlayAsVassal.this.getHeight());
               if (Button_PlayAsVassal.this.getCheckboxState()) {
                  ImageManager.getImage(Images.icon_check_true).draw(oSB, Button_PlayAsVassal.this.getPosX() + Button_PlayAsVassal.this.getWidth() - CFG.PADDING - ImageManager.getImage(Images.icon_check_true).getWidth() + iTranslateX, Button_PlayAsVassal.this.getPosY() + Button_PlayAsVassal.this.getHeight() / 2 - ImageManager.getImage(Images.icon_check_true).getHeight() / 2 + iTranslateY);
               } else {
                  ImageManager.getImage(Images.icon_check_false).draw(oSB, Button_PlayAsVassal.this.getPosX() + Button_PlayAsVassal.this.getWidth() - CFG.PADDING - ImageManager.getImage(Images.icon_check_true).getWidth() + iTranslateX, Button_PlayAsVassal.this.getPosY() + Button_PlayAsVassal.this.getHeight() / 2 - ImageManager.getImage(Images.icon_check_false).getHeight() / 2 + iTranslateY);
               }
            } else {
               oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
               ImageManager.getImage(Images.gradient).draw(oSB, Button_PlayAsVassal.this.getPosX() + iTranslateX, Button_PlayAsVassal.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + 1 + iTranslateY, Button_PlayAsVassal.this.getWidth(), Button_PlayAsVassal.this.getHeight() / 4, false, false);
               ImageManager.getImage(Images.gradient).draw(oSB, Button_PlayAsVassal.this.getPosX() + iTranslateX, Button_PlayAsVassal.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + Button_PlayAsVassal.this.getHeight() - 1 + iTranslateY - Button_PlayAsVassal.this.getHeight() / 4, Button_PlayAsVassal.this.getWidth(), Button_PlayAsVassal.this.getHeight() / 4, false, true);
               oSB.setColor(Color.WHITE);
               CFG.drawRect_InfoBox_Left(oSB, Button_PlayAsVassal.this.getPosX() + iTranslateX, Button_PlayAsVassal.this.getPosY() + iTranslateY, Button_PlayAsVassal.this.getWidth(), Button_PlayAsVassal.this.getHeight());
            }
         }
      } : new Checkbox() {
         public void drawCheckBox(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean scrollableY) {
         }
      };
   }

   protected Color getColor(boolean isActive) {
      return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : CFG.COLOR_TEXT_OPTIONS_NS) : CFG.COLOR_BUTTON_MENU_TEXT_NOT_CLICKABLE);
   }
}
