package age.of.civilizations2.jakowski.lukasz;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;

class TextSlider_Line
{
    private List<String> lText;
    private int iHeight;
    private Align align;
    
    protected TextSlider_Line(final String sText, final int nWidth, final int extraHeight, final Align nAlign, final float nFONT_SCALE) {
        super();
        this.lText = new ArrayList<String>();
        this.align = nAlign;
        //if newline, surround it with spaces to make sure it is interpreted as array index seperate from other words
        final String[] tempLine = sText.replace("\n", " \n").split(" ");
        int i = 0;
        int currentW = 0;
        final int iSize = tempLine.length;
        int last = 0;
        while (i < iSize) {
            //if newline
            if (tempLine[i].contains("\n")) {
                tempLine[i] = tempLine[i].replace("\n", "");
                //make newline, getting all words from last line break to word before current word
                String addLine = "";
                for (int j = last; j < i; ++j) {
                    addLine = addLine + tempLine[j] + " ";
                }
                this.lText.add(addLine);
                last = i;

                //reset width
                currentW = (int)(CFG.glyphLayout.width * nFONT_SCALE);

                i++;
                continue;
            }

            CFG.glyphLayout.setText(CFG.fontMain, tempLine[i] + " ");

            currentW += (int)(CFG.glyphLayout.width * nFONT_SCALE);
            if (currentW >= nWidth || (i == iSize - 1 && currentW < nWidth)) {
                String addLine = "";
                for (int j = last; j < ((i == iSize - 1 && currentW < nWidth) ? iSize : i); ++j) {
                    addLine = addLine + tempLine[j] + " ";
                }
                this.lText.add(addLine);
                last = i;
                if (currentW >= nWidth && i == iSize - 1) {
                    this.lText.add(tempLine[i]);
                }
                currentW = (int)(CFG.glyphLayout.width * nFONT_SCALE);
            }
            ++i;
        }
        this.iHeight = (int)(this.lText.size() * (CFG.TEXT_HEIGHT * nFONT_SCALE + CFG.PADDING) + extraHeight);
    }
    
    protected void draw(final SpriteBatch oSB, final int nPosX, final int nPosY, final int nWidth, final Color oColor) {
        for (int i = 0, iSize = this.lText.size(); i < iSize; ++i) {
            CFG.drawText(oSB, this.lText.get(i), nPosX, nPosY + (CFG.TEXT_HEIGHT + CFG.PADDING) * i, oColor);
        }
    }
    
    protected final void setHeight(final int iHeight) {
        this.iHeight = iHeight;
    }
    
    protected final int getHeight() {
        return this.iHeight;
    }

    protected static enum Align {
        LEFT,
        CENTER,
        RIGHT;

        // $FF: synthetic method
        private static Align[] $values() {
            return new Align[]{LEFT, CENTER, RIGHT};
        }
    }
}
