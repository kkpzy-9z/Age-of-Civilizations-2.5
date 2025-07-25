package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.*;
import java.util.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.math.*;

class Graph_CircleDraw {
    private PolygonSpriteBatch oPB;
    private TextureRegion texture;
    private Image textureOver;
    private Image circleFrame;
    private Vector2 center;
    private Vector2 centerTop;
    private Vector2 leftTop;
    private Vector2 leftBottom;
    private Vector2 rightBottom;
    private Vector2 rightTop;
    private float[] fv;
    private IntersectAt intersectAt;
    
    protected Graph_CircleDraw(final String nFileName, final String fileNameOver, final String nFlieNameFrame) {
        super();
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("UI/" + CFG.getRescouresPath() + "graph/" + nFileName), Pixmap.Format.RGBA8888, true));
        this.textureOver = new Image(new Texture(Gdx.files.internal("UI/" + CFG.getRescouresPath() + "graph/" + fileNameOver), Pixmap.Format.RGBA8888, true), Texture.TextureFilter.Linear);
        this.circleFrame = new Image(new Texture(Gdx.files.internal("UI/" + CFG.getRescouresPath() + "graph/" + nFlieNameFrame), Pixmap.Format.RGBA8888, true), Texture.TextureFilter.Linear);
        this.oPB = new PolygonSpriteBatch();
        this.center = new Vector2((float)(this.texture.getRegionWidth() / 2), (float)(this.texture.getRegionHeight() / 2));
        this.centerTop = new Vector2((float)(this.texture.getRegionWidth() / 2), (float)this.texture.getRegionHeight());
        this.leftTop = new Vector2(0.0f, (float)this.texture.getRegionHeight());
        this.leftBottom = new Vector2(0.0f, 0.0f);
        this.rightBottom = new Vector2((float)this.texture.getRegionWidth(), 0.0f);
        this.rightTop = new Vector2((float)this.texture.getRegionWidth(), (float)this.texture.getRegionHeight());
        this.setPercentage(0.0f);
    }

    protected final void draw(final SpriteBatch oSB, final int nPosX, final int nPosY, final List<Graph_CircleData> nData, final boolean isActive) {
        try {
            this.drawCircle100(oSB, nPosX, nPosY, new Color(CFG.game.getCiv(nData.get(0).getDataID()).getR() / 255.0f, CFG.game.getCiv(nData.get(0).getDataID()).getG() / 255.0f, CFG.game.getCiv(nData.get(0).getDataID()).getB() / 255.0f, 1.0f));
        }
        catch (final IndexOutOfBoundsException ex) {
            this.drawCircle100(oSB, nPosX, nPosY, new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), 1.0f));
        }
        oSB.end();
        this.drawGraph(nPosX, nPosY, nData);
        oSB.begin();
        this.textureOver.draw(oSB, nPosX, nPosY);
        if (isActive) {
            oSB.setColor(new Color(1.0f, 1.0f, 1.0f, 0.335f));
            this.textureOver.draw(oSB, nPosX, nPosY);
            oSB.setColor(Color.WHITE);
        }
        this.circleFrame.draw(oSB, nPosX, nPosY);
    }
    
    private final void drawGraph(final int nPosX, final int nPosY, final List<Graph_CircleData> nData) {
        try {
            this.oPB.begin();
            float drawnPercentage = nData.get(0).getPercentage();
            for (int i = 1; i < nData.size(); ++i) {
                this.setPercentage(drawnPercentage);
                try {
                    if (drawnPercentage >= 100.0f) {
                        this.oPB.setColor(new Color(CFG.game.getCiv(nData.get(i).getDataID()).getR() / 255.0f, CFG.game.getCiv(nData.get(i).getDataID()).getG() / 255.0f, CFG.game.getCiv(nData.get(i).getDataID()).getB() / 255.0f, 1.0f));
                        this.oPB.draw(this.texture.getTexture(), (float)nPosX, (float)(-nPosY - this.texture.getRegionHeight()), 0.0F, 0.0F, (float)this.texture.getRegionWidth(), (float)this.texture.getRegionHeight(), 1.0F, 1.0F, 0.0F, 0, 0, this.texture.getRegionWidth(), this.texture.getRegionHeight(), false, false);
                        this.oPB.setColor(Color.WHITE);
                        break;
                    }
                    this.drawCircle(nPosX, nPosY, new Color(CFG.game.getCiv(nData.get(i).getDataID()).getR() / 255.0f, CFG.game.getCiv(nData.get(i).getDataID()).getG() / 255.0f, CFG.game.getCiv(nData.get(i).getDataID()).getB() / 255.0f, 1.0f));
                }
                catch (final IndexOutOfBoundsException ex) {
                    if (drawnPercentage >= 100.0f) {
                        this.oPB.setColor(new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), 1.0f));
                        this.oPB.draw(this.texture.getTexture(), (float)nPosX, (float)(-nPosY - this.texture.getRegionHeight()), 0.0F, 0.0F, (float)this.texture.getRegionWidth(), (float)this.texture.getRegionHeight(), 1.0F, 1.0F, 0.0F, 0, 0, this.texture.getRegionWidth(), this.texture.getRegionHeight(), false, false);
                        this.oPB.setColor(Color.WHITE);
                        break;
                    }
                    this.drawCircle(nPosX, nPosY, new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), 1.0f));
                }
                drawnPercentage += nData.get(i).getPercentage();
            }
            this.oPB.end();
        }
        catch (final IllegalStateException ex2) {}
    }

    private final void drawCircle100(SpriteBatch oSB, int nPosX, int nPosY, Color nColor) {
        oSB.setColor(nColor);
        oSB.draw(this.texture.getTexture(), (float)nPosX, (float)(-nPosY - this.texture.getRegionHeight()), 0.0F, 0.0F, (float)this.texture.getRegionWidth(), (float)this.texture.getRegionHeight(), 1.0F, 1.0F, 0.0F, 0, 0, this.texture.getRegionWidth(), this.texture.getRegionHeight(), false, false);
        oSB.setColor(Color.WHITE);
    }

    private final void drawCircle(final int nPosX, final int nPosY, final Color nColor) {
        if (this.fv == null) {
            return;
        }
        final EarClippingTriangulator e = new EarClippingTriangulator();
        final ShortArray sv = e.computeTriangles(this.fv);
        final PolygonRegion polyReg = new PolygonRegion(this.texture, this.fv, sv.toArray());
        final PolygonSprite poly = new PolygonSprite(polyReg);
        poly.setOrigin(0.0f, 0.0f);
        poly.setPosition((float)nPosX, (float)(CFG.GAME_HEIGHT - this.texture.getRegionHeight() - nPosY));
        poly.setRotation(0.0f);
        poly.setColor(nColor);
        poly.draw(this.oPB);
    }

    protected final void setPercentage(float percent) {
        float angle = this.convertToRadians(90.0F);
        angle -= this.convertToRadians(percent * 360.0F / 100.0F);
        float len = this.texture.getRegionWidth() > this.texture.getRegionHeight() ? (float)this.texture.getRegionWidth() : (float)this.texture.getRegionHeight();
        float dy = (float)(Math.sin((double)angle) * (double)len);
        float dx = (float)(Math.cos((double)angle) * (double)len);
        Vector2 line = new Vector2(this.center.x + dx, this.center.y + dy);
        Vector2 v = this.IntersectPoint(line);
        if (this.intersectAt == Graph_CircleDraw.IntersectAt.TOP) {
            if (v.x >= (float)(this.texture.getRegionWidth() / 2)) {
                this.fv = new float[]{this.center.x, this.center.y, this.centerTop.x, this.centerTop.y, this.leftTop.x, this.leftTop.y, this.leftBottom.x, this.leftBottom.y, this.rightBottom.x, this.rightBottom.y, this.rightTop.x, this.rightTop.y, v.x, v.y};
            } else {
                this.fv = new float[]{this.center.x, this.center.y, this.centerTop.x, this.centerTop.y, v.x, v.y};
            }
        } else if (this.intersectAt == Graph_CircleDraw.IntersectAt.BOTTOM) {
            this.fv = new float[]{this.center.x, this.center.y, this.centerTop.x, this.centerTop.y, this.leftTop.x, this.leftTop.y, this.leftBottom.x, this.leftBottom.y, v.x, v.y};
        } else if (this.intersectAt == Graph_CircleDraw.IntersectAt.LEFT) {
            this.fv = new float[]{this.center.x, this.center.y, this.centerTop.x, this.centerTop.y, this.leftTop.x, this.leftTop.y, v.x, v.y};
        } else if (this.intersectAt == Graph_CircleDraw.IntersectAt.RIGHT) {
            this.fv = new float[]{this.center.x, this.center.y, this.centerTop.x, this.centerTop.y, this.leftTop.x, this.leftTop.y, this.leftBottom.x, this.leftBottom.y, this.rightBottom.x, this.rightBottom.y, v.x, v.y};
        } else {
            this.fv = null;
        }

    }
    
    private final Vector2 IntersectPoint(final Vector2 line) {
        final Vector2 v = new Vector2();
        boolean isIntersect = Intersector.intersectSegments(this.leftTop, this.rightTop, this.center, line, v);
        if (isIntersect) {
            this.intersectAt = IntersectAt.TOP;
            return v;
        }
        isIntersect = Intersector.intersectSegments(this.leftBottom, this.rightBottom, this.center, line, v);
        if (isIntersect) {
            this.intersectAt = IntersectAt.BOTTOM;
            return v;
        }
        isIntersect = Intersector.intersectSegments(this.leftTop, this.leftBottom, this.center, line, v);
        if (isIntersect) {
            this.intersectAt = IntersectAt.LEFT;
            return v;
        }
        isIntersect = Intersector.intersectSegments(this.rightTop, this.rightBottom, this.center, line, v);
        if (isIntersect) {
            this.intersectAt = IntersectAt.RIGHT;
            return v;
        }
        this.intersectAt = IntersectAt.NONE;
        return null;
    }

    private final float convertToRadians(final float angleInDegrees) {
        final float angleInRadians = angleInDegrees * 0.017453292f;
        return angleInRadians;
    }

    protected final int getWidth() {
        return this.circleFrame.getWidth();
    }

    protected static enum IntersectAt {
        NONE,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT;

        // $FF: synthetic method
        private static IntersectAt[] $values() {
            return new IntersectAt[]{NONE, TOP, BOTTOM, LEFT, RIGHT};
        }
    }
}