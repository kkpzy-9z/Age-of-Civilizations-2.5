//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Button_Diplo_Decisions extends Button_Diplo_Actions {
    protected Button_Diplo_Decisions(String sText, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, boolean isClickable) {
        super(sText, iTextPositionX, iPosX, iPosY, iWidth, iHeight, isClickable);
    }

    protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
        if (CFG.menuManager.getVisible_InGame_CivInfo_Stats_Opinions()) {
            oSB.setColor(new Color(CFG.COLOR_GRADIENT_DIPLOMACY.r, CFG.COLOR_GRADIENT_DIPLOMACY.g, CFG.COLOR_GRADIENT_DIPLOMACY.b, 0.625F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth() - 2, this.getHeight() - 2, false, false);
        }

        oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.6F));
        ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth() - 2, CFG.PADDING, false, false);
        ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + this.getHeight() - 2 - CFG.PADDING - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth() - 2, CFG.PADDING, false, true);
        if (isActive || this.getIsHovered()) {
            oSB.setColor(new Color(CFG.COLOR_GRADIENT_DIPLOMACY.r, CFG.COLOR_GRADIENT_DIPLOMACY.g, CFG.COLOR_GRADIENT_DIPLOMACY.b, 0.45F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth(), this.getHeight() - 2, false, false);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, CFG.PADDING, this.getHeight() - 2, false, false);
        }

        oSB.setColor(Color.WHITE);
    }

    protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
        ImageManager.getImage(Images.top_movement_points).draw(oSB, this.getPosX() + this.getWidth() / 2 - (ImageManager.getImage(Images.top_movement_points).getWidth() + CFG.PADDING + (int)((float)this.getTextWidth() * 0.6F)) / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.top_movement_points).getHeight() / 2 + iTranslateY);
        CFG.fontMain.getData().setScale(0.6F);
        CFG.drawText(oSB, this.getText(), this.getPosX() + this.getWidth() / 2 - (ImageManager.getImage(Images.top_movement_points).getWidth() + CFG.PADDING + (int)((float)this.getTextWidth() * 0.6F)) / 2 + CFG.PADDING + ImageManager.getImage(Images.top_movement_points).getWidth() + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.6F / 2.0F) + iTranslateY, this.getColor(isActive));
        CFG.fontMain.getData().setScale(1.0F);
    }
}
