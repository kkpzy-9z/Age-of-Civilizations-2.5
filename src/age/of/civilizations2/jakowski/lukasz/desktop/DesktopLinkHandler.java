/*
 * Decompiled with CFR 0.152.
 */
package age.of.civilizations2.jakowski.lukasz.desktop;

import age.of.civilizations2.jakowski.lukasz.LinkHandler;
import com.badlogic.gdx.Gdx;

public class DesktopLinkHandler
implements LinkHandler {
    @Override
    public void openPage(String AppURI, String WebURL) {
        Gdx.net.openURI(WebURL);
    }
}

