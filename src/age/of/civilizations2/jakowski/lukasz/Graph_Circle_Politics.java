//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package age.of.civilizations2.jakowski.lukasz;

import age.of.civilizations2.jakowski.lukasz.MenuElement.TypeOfElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import java.util.ArrayList;
import java.util.List;

class Graph_Circle_Politics extends MenuElement {
    private long lTime = 0L;
    private List<Graph_CircleData> lData = new ArrayList<>();
    private int iDataSize;
    private int iCivID;
    private boolean moveable = false;
    private int iButtonsPosY = 0;
    private int iExtraWidth = 0;
    private static final float TEXT_SCALE = 0.7F;

    protected Graph_Circle_Politics(int iPosX, int iPosY, final int iCivID, MenuElement_Hover menuElementHover, final float loyal, final float rebellious) {
        this.setPosX(iPosX);
        this.setPosY(iPosY);
        this.iCivID = iCivID;
        this.setWidth(CFG.graphCircleDraw.getWidth());
        this.setHeight(CFG.graphCircleDraw.getWidth());
        this.menuElementHover = menuElementHover;
        this.iDataSize = 2;

        this.lData.add(new Graph_CircleData(iCivID, loyal * 100.0F));
        this.lData.add(new Graph_CircleData(-1, rebellious * 100.0F));

        this.updateMoveable();
        this.typeOfElement = TypeOfElement.GRAPH_CIRCLE;

        try {
            float nMaxWidth = 0.0F;

            for(int i = 0; i < this.iDataSize; ++i) {
                CFG.glyphLayout.setText(CFG.fontMain, "" + this.getPercentage(((Graph_CircleData)this.lData.get(i)).getPercentage(), 5) + "%");
                if (nMaxWidth < CFG.glyphLayout.width) {
                    nMaxWidth = CFG.glyphLayout.width;
                }
            }

            this.iExtraWidth = (int)((float)CFG.CIV_FLAG_WIDTH * (float)CFG.TEXT_HEIGHT * 0.7F / (float)CFG.CIV_FLAG_HEIGHT) + CFG.PADDING * 4 + (int)(nMaxWidth * 0.7F);
        } catch (IndexOutOfBoundsException | IllegalArgumentException var11) {
            this.iExtraWidth = super.getWidth();
        }

    }

    protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
        this.drawGraph(oSB, iTranslateX, iTranslateY, isActive, scrollableY, this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight_Perc(), CFG.graphCircleDraw.getWidth());
    }

    protected final void drawGraph(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY, int nPosX, int nPosY, int nWidth, int nHeight, int nWidth_LEFT) {
        CFG.graphCircleDraw.draw(oSB, nPosX + iTranslateX, nPosY + iTranslateY, this.lData, false);
        Rectangle clipBounds = new Rectangle((float) (nPosX + iTranslateX), (float) (CFG.GAME_HEIGHT - nPosY - iTranslateY), (float) nWidth, (float) (-nHeight));
        oSB.flush();
        ScissorStack.pushScissors(clipBounds);
        CFG.fontMain.getData().setScale(0.7F);
        iTranslateY += this.iButtonsPosY;
        float tempFlagScale = (float)CFG.TEXT_HEIGHT * 0.7F / (float)CFG.CIV_FLAG_HEIGHT;

        //loyalists
        try {
            oSB.setColor(new Color((float)CFG.game.getCiv(this.iCivID).getR() / 255.0F, (float)CFG.game.getCiv(this.iCivID).getG() / 255.0F, (float)CFG.game.getCiv(this.iCivID).getB() / 255.0F, 0.45F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX, -ImageManager.getImage(Images.slider_gradient).getHeight() + nPosY + iTranslateY, CFG.CIV_COLOR_WIDTH + nWidth - nWidth_LEFT, (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)));
            oSB.setColor(new Color((float)CFG.game.getCiv(this.iCivID).getR() / 255.0F, (float)CFG.game.getCiv(this.iCivID).getG() / 255.0F, (float)CFG.game.getCiv(this.iCivID).getB() / 255.0F, 0.2F));
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX, -ImageManager.getImage(Images.gradient).getHeight() + nPosY + iTranslateY, CFG.CIV_COLOR_WIDTH + nWidth - nWidth_LEFT, (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)) / 4);
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX, (int) ((float) CFG.TEXT_HEIGHT * 0.7F + (float) (CFG.PADDING * 2)) - (int) ((float) CFG.TEXT_HEIGHT * 0.7F + (float) (CFG.PADDING * 2)) / 4 - ImageManager.getImage(Images.gradient).getHeight() + nPosY + iTranslateY, CFG.CIV_COLOR_WIDTH + nWidth - nWidth_LEFT, (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)) / 4, false, true);
            oSB.setColor(Color.WHITE);
            CFG.game.getCiv(this.iCivID).getFlag().draw(oSB, nPosX + nWidth_LEFT + CFG.PADDING + CFG.PADDING + iTranslateX, (nPosY + (int) ((float) CFG.TEXT_HEIGHT * 0.7F + (float) (CFG.PADDING * 2)) / 2) - (int)((float)CFG.TEXT_HEIGHT * 0.7F / 2.0F) - CFG.game.getCiv(this.iCivID).getFlag().getHeight() + iTranslateY, (int)((float)CFG.CIV_FLAG_WIDTH * tempFlagScale), (int)((float)CFG.CIV_FLAG_HEIGHT * tempFlagScale));
        } catch (IndexOutOfBoundsException var16) {
            oSB.setColor(new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), 0.45F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX, -ImageManager.getImage(Images.slider_gradient).getHeight() + nPosY + iTranslateY, CFG.CIV_COLOR_WIDTH + nWidth - nWidth_LEFT, (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)));
            oSB.setColor(new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), 0.2F));
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX, -ImageManager.getImage(Images.gradient).getHeight() + nPosY + iTranslateY, CFG.CIV_COLOR_WIDTH + nWidth - nWidth_LEFT, (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)) / 4);
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX, (int) ((float) CFG.TEXT_HEIGHT * 0.7F + (float) (CFG.PADDING * 2)) - (int) ((float) CFG.TEXT_HEIGHT * 0.7F + (float) (CFG.PADDING * 2)) / 4 - ImageManager.getImage(Images.gradient).getHeight() + nPosY + iTranslateY, CFG.CIV_COLOR_WIDTH + nWidth - nWidth_LEFT, (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)) / 4, false, true);
            oSB.setColor(Color.WHITE);
            ImageManager.getImage(Images.randomCivilizationFlag).draw(oSB, nPosX + nWidth_LEFT + CFG.PADDING + CFG.PADDING + iTranslateX, (nPosY + (int) ((float) CFG.TEXT_HEIGHT * 0.7F + (float) (CFG.PADDING * 2)) / 2) - (int)((float)CFG.TEXT_HEIGHT * 0.7F / 2.0F) - ImageManager.getImage(Images.randomCivilizationFlag).getHeight() + iTranslateY, (int)((float)CFG.CIV_FLAG_WIDTH * tempFlagScale), (int)((float)CFG.CIV_FLAG_HEIGHT * tempFlagScale));
        }

        ImageManager.getImage(Images.flag_rect).draw(oSB, nPosX + nWidth_LEFT + CFG.PADDING + CFG.PADDING + iTranslateX, (nPosY + (int) ((float) CFG.TEXT_HEIGHT * 0.7F + (float) (CFG.PADDING * 2)) / 2) - (int)((float)CFG.TEXT_HEIGHT * 0.7F / 2.0F) - CFG.CIV_FLAG_HEIGHT + iTranslateY, (int)((float)CFG.CIV_FLAG_WIDTH * tempFlagScale), (int)((float)CFG.CIV_FLAG_HEIGHT * tempFlagScale));
        CFG.drawTextWithShadow(oSB, CFG.langManager.get("Loyal") + ": " + this.getPercentage(((Graph_CircleData)this.lData.get(0)).getPercentage(), 5) + "%", (int)((float)CFG.CIV_FLAG_WIDTH * tempFlagScale) + CFG.PADDING + nPosX + nWidth_LEFT + CFG.PADDING + CFG.PADDING + iTranslateX, nPosY + (int) ((float) CFG.TEXT_HEIGHT * 0.7F + (float) (CFG.PADDING * 2)) / 2 - (int)((float)CFG.TEXT_HEIGHT * 0.7F / 2.0F) + iTranslateY, CFG.COLOR_TEXT_MODIFIER_NEUTRAL);

        //rebellious
        oSB.setColor(new Color(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 0.45F));
        ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX, -ImageManager.getImage(Images.slider_gradient).getHeight() + nPosY + (int) ((float) CFG.TEXT_HEIGHT * 0.7F + (float) (CFG.PADDING * 2)) + CFG.PADDING + iTranslateY, CFG.CIV_COLOR_WIDTH + nWidth - nWidth_LEFT, (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)));
        oSB.setColor(new Color(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 0.2F));
        ImageManager.getImage(Images.gradient).draw(oSB, nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX, -ImageManager.getImage(Images.gradient).getHeight() + nPosY + (int) ((float) CFG.TEXT_HEIGHT * 0.7F + (float) (CFG.PADDING * 2)) + CFG.PADDING + iTranslateY, CFG.CIV_COLOR_WIDTH + nWidth - nWidth_LEFT, (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)) / 4);
        ImageManager.getImage(Images.gradient).draw(oSB, nPosX + nWidth_LEFT + CFG.PADDING + iTranslateX, (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)) - (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)) / 4 - ImageManager.getImage(Images.gradient).getHeight() + nPosY + (int) ((float) CFG.TEXT_HEIGHT * 0.7F + (float) (CFG.PADDING * 2)) + CFG.PADDING + iTranslateY, CFG.CIV_COLOR_WIDTH + nWidth - nWidth_LEFT, (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)) / 4, false, true);
        oSB.setColor(Color.WHITE);
        ImageManager.getImage(Images.randomCivilizationFlag).draw(oSB, nPosX + nWidth_LEFT + CFG.PADDING + CFG.PADDING + iTranslateX, nPosY + (int) ((float) CFG.TEXT_HEIGHT * 0.7F + (float) (CFG.PADDING * 2)) + CFG.PADDING + (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)) / 2 - (int)((float)CFG.TEXT_HEIGHT * 0.7F / 2.0F) - ImageManager.getImage(Images.randomCivilizationFlag).getHeight() + iTranslateY, (int)((float)CFG.CIV_FLAG_WIDTH * tempFlagScale), (int)((float)CFG.CIV_FLAG_HEIGHT * tempFlagScale));


        ImageManager.getImage(Images.flag_rect).draw(oSB, nPosX + nWidth_LEFT + CFG.PADDING + CFG.PADDING + iTranslateX, nPosY + (int) ((float) CFG.TEXT_HEIGHT * 0.7F + (float) (CFG.PADDING * 2)) + CFG.PADDING + (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)) / 2 - (int)((float)CFG.TEXT_HEIGHT * 0.7F / 2.0F) - CFG.CIV_FLAG_HEIGHT + iTranslateY, (int)((float)CFG.CIV_FLAG_WIDTH * tempFlagScale), (int)((float)CFG.CIV_FLAG_HEIGHT * tempFlagScale));
        CFG.drawTextWithShadow(oSB, CFG.langManager.get("Rebellious") + ": " + this.getPercentage(((Graph_CircleData)this.lData.get(1)).getPercentage(), 5) + "%", (int)((float)CFG.CIV_FLAG_WIDTH * tempFlagScale) + CFG.PADDING + nPosX + nWidth_LEFT + CFG.PADDING + CFG.PADDING + iTranslateX, nPosY + (int) ((float) CFG.TEXT_HEIGHT * 0.7F + (float) (CFG.PADDING * 2)) + CFG.PADDING + (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)) / 2 - (int)((float)CFG.TEXT_HEIGHT * 0.7F / 2.0F) + iTranslateY, CFG.COLOR_TEXT_MODIFIER_NEUTRAL);


        CFG.fontMain.getData().setScale(1.0F);

        try {
            oSB.flush();
            ScissorStack.popScissors();
        } catch (IllegalStateException var15) {
        }

    }

    private final void updateMoveable() {
        if (this.getMaxHeight() > this.getHeight_Perc()) {
            this.moveable = true;
        } else {
            this.moveable = false;
            this.iButtonsPosY = 0;
        }

    }

    protected final int getMaxHeight() {
        return (int)((float)CFG.TEXT_HEIGHT * 0.7F + (float)(CFG.PADDING * 2)) * this.iDataSize + CFG.PADDING * (this.iDataSize - 1);
    }

    protected int getCurrent() {
        return this.iButtonsPosY;
    }

    @Override
    protected void setCurrent(int nButtonsPosX) {
        if (nButtonsPosX > 0) {
            nButtonsPosX = 0;
            CFG.menuManager.setUpdateSliderMenuPosY(true);
        }
        else if (nButtonsPosX < -this.getMaxHeight() + this.getHeight_Perc()) {
            nButtonsPosX = -this.getMaxHeight() + this.getHeight_Perc();
            CFG.menuManager.setUpdateSliderMenuPosY(true);
        }
        if (this.iButtonsPosY != nButtonsPosX) {
            this.iButtonsPosY = nButtonsPosX;
            CFG.setRender_3(true);
        }
    }

    protected int getHeight_Perc() {
        return CFG.graphCircleDraw.getWidth();
    }

    protected int getWidth() {
        return this.getWidth_PercStrings(super.getWidth());
    }

    protected int getWidth_PercStrings(int nWidth) {
        return nWidth;
    }

    protected boolean getMoveable() {
        return this.moveable;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    protected void setVisible(boolean isVisible) {
        super.setVisible(isVisible);
    }

    private final String getPercentage(final float nPerc, final int nPrecision) {
        return ("" + nPerc).substring(0, Math.min(nPrecision, ("" + nPerc).length()));
    }

    protected int getSFX() {
        return SoundsManager.SOUND_CLICK2;
    }
}
