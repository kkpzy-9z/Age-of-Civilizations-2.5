package age.of.civilizations2.jakowski.lukasz;

import java.util.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.graphics.*;

class SliderMenu
{
    private List<MenuElement> menuElements;
    private int iMenuElementsSize;
    private int iPosX;
    private int iPosY;
    private int iWidth;
    private int iHeight;
    private boolean visible;
    private boolean closeable;
    private SliderMenuTitle sliderMenuTitle;
    private int iMenuPosX;
    private int iNewMenuPositionX;
    private int iMaxSliderPositionX;
    private boolean scrollableX;
    private int iMenuPosY;
    private int iNewMenuPositionY;
    protected int iMaxSliderPositionY;
    private boolean scrollableY;
    private boolean scrollModeY;
    private int iScrollPosY;
    private int iScrollPosY2;
    private float fScrollNewMenuPosY;
    private boolean scrollModeX;
    private int iScrollPosX;
    private int iScrollPosX2;
    private float fScrollNewMenuPosX;
    private boolean backbutton;
    SliderMenu() {
        super();
        this.menuElements = new ArrayList<MenuElement>();
        this.visible = true;
        this.closeable = false;
        this.sliderMenuTitle = null;
        this.scrollableX = false;
        this.scrollableY = false;
        this.scrollModeY = false;
        this.iScrollPosY = -1;
        this.iScrollPosY2 = -1;
        this.fScrollNewMenuPosY = 0.0f;
        this.scrollModeX = false;
        this.iScrollPosX = -1;
        this.iScrollPosX2 = -1;
        this.fScrollNewMenuPosX = 0.0f;
        this.backbutton = false;
    }
    
    protected final void initMenu(final SliderMenuTitle sliderMenuTitle, final int iPosX, final int iPosY, final int iWidth, final int iHeight, final List<MenuElement> menuElements) {
        this.initMenu(sliderMenuTitle, iPosX, iPosY, iWidth, iHeight, menuElements, true, false, false);
    }
    
    protected final void initMenu(final SliderMenuTitle sliderMenuTitle, final int iPosX, final int iPosY, final int iWidth, final int iHeight, final List<MenuElement> menuElements, final boolean visible, final boolean closeable) {
        this.initMenu(sliderMenuTitle, iPosX, iPosY, iWidth, iHeight, menuElements, visible, false, closeable);
    }
    
    protected final void initMenuWithBackButton(final SliderMenuTitle sliderMenuTitle, final int iPosX, final int iPosY, final int iWidth, final int iHeight, final List<MenuElement> menuElements) {
        this.initMenu(sliderMenuTitle, iPosX, iPosY, iWidth, iHeight, menuElements, true, true, false);
    }
    
    protected final void initMenuWithBackButton(final SliderMenuTitle sliderMenuTitle, final int iPosX, final int iPosY, final int iWidth, final int iHeight, final List<MenuElement> menuElements, final boolean closeable) {
        this.initMenu(sliderMenuTitle, iPosX, iPosY, iWidth, iHeight, menuElements, true, true, closeable);
    }
    
    protected final void initMenuWithBackButton(final SliderMenuTitle sliderMenuTitle, final int iPosX, final int iPosY, final int iWidth, final int iHeight, final List<MenuElement> menuElements, final boolean visible, final boolean closeable) {
        this.initMenu(sliderMenuTitle, iPosX, iPosY, iWidth, iHeight, menuElements, visible, true, closeable);
    }
    
    protected final void initMenu(final SliderMenuTitle sliderMenuTitle, final int iPosX, final int iPosY, final int iWidth, final int iHeight, final List<MenuElement> menuElements, final boolean visible, final boolean backButton, final boolean closeable) {
        this.iNewMenuPositionX = iPosX;
        this.iMenuPosX = iPosX;
        this.iPosX = iPosX;
        this.iNewMenuPositionY = iPosY;
        this.iMenuPosY = iPosY;
        this.iPosY = iPosY;
        this.iWidth = iWidth;
        this.iHeight = iHeight;
        this.closeable = closeable;
        this.visible = visible;
        this.sliderMenuTitle = sliderMenuTitle;
        this.iMenuElementsSize = menuElements.size();
        this.backbutton = backButton;
        //if back button and Button_Menu_LR_Line
        if (backButton && menuElements.get(0).getClass().isAssignableFrom(Button_Menu_LR_Line.class)) {
            //make all back buttons non-scrollable
            //if (tempMaxY > iHeight - CFG.PADDING - menuElements.get(0).getHeight()) {
            //    menuElements.get(0).setPosY(tempMaxY + CFG.PADDING);
            //} else {
            menuElements.get(0).setPosY(iHeight - menuElements.get(0).getHeight());
            //
        }
        this.menuElements = menuElements;
        this.updateScrollable();
        this.updateMenuElements_IsInView();
    }
    
    protected final void updateScrollable() {
        this.iMaxSliderPositionY = 0;
        this.iMaxSliderPositionX = 0;

        //already set on init
        //this.iMaxSliderPositionY = 0;
        for (int i = 0; i < this.iMenuElementsSize; ++i) {
            if (this.menuElements.get(i).getPosY() + this.menuElements.get(i).getHeight() > this.iMaxSliderPositionY) {
                this.iMaxSliderPositionY = this.menuElements.get(i).getPosY() + this.menuElements.get(i).getHeight();
            }
            if (this.menuElements.get(i).getPosX() + this.menuElements.get(i).getWidth() > this.iMaxSliderPositionX) {
                this.iMaxSliderPositionX = this.menuElements.get(i).getPosX() + this.menuElements.get(i).getWidth();
            }
        }
        this.scrollableX = (this.iMaxSliderPositionX > this.getWidth());
        this.scrollableY = (this.iMaxSliderPositionY > this.iHeight);
        if (this.scrollableY) {
            //add back button height, change//
            if (this.backbutton && this.sliderMenuTitle != null) {
                iMaxSliderPositionY += this.menuElements.get(0).getHeight() + CFG.PADDING;
            }
            this.updateMenuPosY(this.iPosY);
        }
        if (this.scrollableX) {
            this.updateMenuPosX(this.iPosX);
        }
    }
    
    protected void updateLanguage() {
    }
    
    protected void update() {
        if (this.scrollModeY) {
            if (Math.abs(this.fScrollNewMenuPosY) > 1.0f) {
                this.updateMenuPosY(this.iMenuPosY + (int)this.fScrollNewMenuPosY);
                this.fScrollNewMenuPosY *= 0.97f;
            }
            else {
                this.scrollModeY = false;
            }
            CFG.setRender_3(true);
        }
        if (this.scrollModeX) {
            if (Math.abs(this.fScrollNewMenuPosX) > 1.0f) {
                this.updateMenuPosX(this.iMenuPosX + (int)this.fScrollNewMenuPosX);
                this.fScrollNewMenuPosX *= 0.97f;
            }
            else {
                this.scrollModeX = false;
            }
            CFG.setRender_3(true);
        }
        if (this.scrollableX && this.iNewMenuPositionX != this.iMenuPosX) {
            this.iMenuPosX = this.iNewMenuPositionX;
            this.updateMenuElements_IsInView();
            CFG.setRender_3(true);
        }
        if (this.iNewMenuPositionY != this.iMenuPosY) {
            this.iMenuPosY = this.iNewMenuPositionY;
            this.updateMenuElements_IsInView();
            CFG.setRender_3(true);
        }
    }
    
    protected void extraAction() {
    }
    
    protected void draw(final SpriteBatch oSB, final int iTranslateX, final boolean sliderMenuIsActive) {
        this.draw(oSB, iTranslateX, 0, sliderMenuIsActive);
    }
    
    protected void draw(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean sliderMenuIsActive) {
        this.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
    }
    
    protected void beginClip(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean sliderMenuIsActive) {
        this.drawBackgroundMode(oSB, sliderMenuIsActive);
        final Rectangle clipBounds = new Rectangle((float)(this.getPosX() + iTranslateX), (float)(CFG.GAME_HEIGHT - this.getPosY() - iTranslateY), (float)this.getWidth(), (float)(-this.getHeight()));
        oSB.flush();
        ScissorStack.pushScissors(clipBounds);
    }
    
    protected final void drawMenu(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean sliderMenuIsActive) {
        this.drawMenuElements(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        this.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
    }
    
    protected final void endClip(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean sliderMenuIsActive) {
        try {
            oSB.flush();
            ScissorStack.popScissors();
        }
        catch (final IllegalStateException ex) {}
        this.drawTitle(oSB, iTranslateX, iTranslateY, sliderMenuIsActive, this.getPosY());
        if (this.getCloseable()) {
            this.drawCloseButton(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        }
    }
    
    protected final void drawHover(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final int nMenuElementID) {
        try {
            this.getMenuElement(nMenuElementID).drawMenuElementHover2(oSB, this.getMenuPosX() + iTranslateX, this.getMenuPosY() + iTranslateY, this.getMenuElementIsActive(true, CFG.menuManager.getActiveMenuElementID()));
        }
        catch (final IndexOutOfBoundsException ex) {}
    }
    
    protected void drawScrollPos(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean sliderMenuIsActive) {
        try {
            if (this.scrollableY && this.getHeight() < this.iMaxSliderPositionY) {
                oSB.setColor(new Color(0.22f, 0.22f, 0.3f, 1.0f));
                ImageManager.getImage(Images.scroll_posiotion).draw2(oSB, this.getPosX() + this.getWidth() - CFG.PADDING * 2 + 1 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.scroll_posiotion).getHeight() + iTranslateY, ImageManager.getImage(Images.scroll_posiotion).getWidth(), this.getHeight() - ImageManager.getImage(Images.scroll_posiotion).getHeight());
                ImageManager.getImage(Images.scroll_posiotion).draw(oSB, this.getPosX() + this.getWidth() - CFG.PADDING * 2 + 1 + iTranslateX, this.getPosY() + this.getHeight() - ImageManager.getImage(Images.scroll_posiotion).getHeight() + iTranslateY, false, true);
                if (CFG.menuManager.getSliderMenuMode()) {
                    oSB.setColor(new Color(0.0f, 0.0f, 0.08f, 1.0f));
                }
                else {
                    oSB.setColor(new Color(0.098f, 0.098f, 0.16f, 1.0f));
                }
                ImageManager.getImage(Images.scroll_posiotion_active).draw2(oSB, this.getPosX() + this.getWidth() - CFG.PADDING * 2 + iTranslateX + 1, this.getPosY() + (this.getHeight() - 100 * this.getHeight() / this.iMaxSliderPositionY * this.getHeight() / 100) * (this.getPosY() - this.getMenuPosY()) / (this.iMaxSliderPositionY - this.getHeight()) - ImageManager.getImage(Images.scroll_posiotion_active).getHeight() + iTranslateY, CFG.PADDING * 2 - 2, this.getHeight() * 100 / this.iMaxSliderPositionY * this.getHeight() / 100 - ImageManager.getImage(Images.scroll_posiotion_active).getHeight());
                ImageManager.getImage(Images.scroll_posiotion_active).draw(oSB, this.getPosX() + this.getWidth() - CFG.PADDING * 2 + iTranslateX + 1, this.getPosY() + (this.getHeight() - 100 * this.getHeight() / this.iMaxSliderPositionY * this.getHeight() / 100) * (this.getPosY() - this.getMenuPosY()) / (this.iMaxSliderPositionY - this.getHeight()) + this.getHeight() * 100 / this.iMaxSliderPositionY * this.getHeight() / 100 - ImageManager.getImage(Images.scroll_posiotion_active).getHeight() + iTranslateY, false, true);
                oSB.setColor(Color.WHITE);
            }
        }
        catch (final ArithmeticException ex) {}
    }
    
    protected final void drawMenuElements(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean sliderMenuIsActive) {
        for (int i = this.iMenuElementsSize - 1; i >= 0; --i) {
            if (this.menuElements.get(i).getVisible() && this.menuElements.get(i).getIsInView()) {
                this.menuElements.get(i).draw(oSB, this.getMenuPosX() + iTranslateX, this.getMenuPosY() + iTranslateY, this.getMenuElementIsActive(sliderMenuIsActive, i), this.scrollableY);
            }
        }
    }
    
    protected void updateMenuElements_IsInView() {
        for (int i = 0; i < this.iMenuElementsSize; ++i) {
            this.menuElements.get(i).setIsInView(this.getMenuElementIsInView(i));
        }
    }
    
    protected void updateMenuElements_IsInView_X() {
        for (int i = 0; i < this.iMenuElementsSize; ++i) {
            this.menuElements.get(i).setIsInView(this.getMenuElementIsInView_X(i));
        }
    }
    
    private final boolean getMenuElementIsInView(final int i) {
        return (this.menuElements.get(i).getPosY() + this.getMenuPosY() > this.getPosY() && this.menuElements.get(i).getPosY() + this.getMenuPosY() < this.getPosY() + this.getHeight()) || (this.menuElements.get(i).getPosY() + this.menuElements.get(i).getHeight() + this.getMenuPosY() > this.getPosY() && this.menuElements.get(i).getPosY() + this.menuElements.get(i).getHeight() + this.getMenuPosY() < this.getPosY() + this.getHeight());
    }
    
    private final boolean getMenuElementIsInView_X(final int i) {
        return (this.menuElements.get(i).getPosX() + this.getMenuPosX() >= this.getPosX() && this.menuElements.get(i).getPosX() + this.getMenuPosX() <= this.getPosX() + this.getWidth()) || (this.menuElements.get(i).getPosX() + this.menuElements.get(i).getWidth() + this.getMenuPosX() >= this.getPosX() && this.menuElements.get(i).getPosX() + this.menuElements.get(i).getWidth() + this.getMenuPosX() <= this.getPosX() + this.getWidth());
    }
    
    protected boolean getMenuElementIsActive(final boolean sliderMenuIsActive, final int i) {
        return sliderMenuIsActive && i == CFG.menuManager.getActiveMenuElementID();
    }
    
    protected void drawTitle(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean sliderMenuIsActive, final int nPosY) {
        if (this.sliderMenuTitle != null) {
            this.sliderMenuTitle.draw(oSB, iTranslateX, this.getPosX(), nPosY + iTranslateY, this.getWidth(), sliderMenuIsActive);
        }
        if (sliderMenuIsActive) {
            if (CFG.menuManager.getSliderMenuResizeMode()) {
                this.drawMenuBorder(oSB);
                this.drawMenuResizeRect(oSB);
            }
            else if (CFG.menuManager.getSliderMenuTitleMode()) {
                this.drawMenuBorder(oSB);
            }
        }
    }
    
    protected final void drawMenuBorder(final SpriteBatch oSB) {
        oSB.setColor(0.196f, 0.196f, 0.196f, 1.0f);
        ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX(), this.getPosY(), 1, this.getHeight());
        ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + this.getWidth() - 1, this.getPosY(), 1, this.getHeight());
        ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX(), this.getPosY(), this.getWidth(), -1);
        ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX(), this.getPosY() + this.getHeight() - 1, this.getWidth(), -1);
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawMenuResizeRect(final SpriteBatch oSB) {
        oSB.setColor(0.196f, 0.196f, 0.196f, 0.95f);
        if (CFG.menuManager.getSliderMenuResizeLEFT()) {
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX(), this.getPosY() + this.getHeight() - 1 - CFG.PADDING * 6, CFG.PADDING * 6, CFG.PADDING * 6);
            oSB.setColor(new Color(1.0f, 1.0f, 1.0f, 0.35f));
            ImageManager.getImage(Images.pickerEdge).draw(oSB, this.getPosX(), this.getPosY() + this.getHeight() - ImageManager.getImage(Images.pickerEdge).getHeight() * 2, ImageManager.getImage(Images.pickerEdge).getWidth(), ImageManager.getImage(Images.pickerEdge).getHeight(), true, false);
        }
        else {
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + this.getWidth() - 1 - CFG.PADDING * 6, this.getPosY() + this.getHeight() - 1 - CFG.PADDING * 6, CFG.PADDING * 6, CFG.PADDING * 6);
            oSB.setColor(new Color(1.0f, 1.0f, 1.0f, 0.35f));
            ImageManager.getImage(Images.pickerEdge).draw(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.pickerEdge).getWidth(), this.getPosY() + this.getHeight() - ImageManager.getImage(Images.pickerEdge).getHeight() * 2, ImageManager.getImage(Images.pickerEdge).getWidth(), ImageManager.getImage(Images.pickerEdge).getHeight(), false, false);
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawBackgroundMode(final SpriteBatch oSB, final boolean sliderMenuIsActive) {
        if (sliderMenuIsActive && (CFG.menuManager.getSliderMenuResizeMode() || CFG.menuManager.getSliderMenuTitleMode())) {
            oSB.setColor(new Color(0.1f, 0.1f, 0.1f, 0.5f));
            ImageManager.getImage(Images.patt).draw2(oSB, 0, -ImageManager.getImage(Images.patt).getHeight(), CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected void drawCloseButton(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean sliderMenuIsActive) {
        this.getCloseButtonImage(sliderMenuIsActive).draw(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.btn_close).getWidth() + iTranslateX, this.getPosY() - this.sliderMenuTitle.getHeight() + iTranslateY);
    }
    
    protected final Image getCloseButtonImage(final boolean sliderMenuIsActive) {
        if (CFG.menuManager.getSliderMenuCloseMode() && sliderMenuIsActive) {
            return ImageManager.getImage(Images.btnh_close);
        }
        return ImageManager.getImage(Images.btn_close);
    }
    
    protected void actionElement(final int nMenuElementID) {
    }
    
    protected void onBackPressed() {
    }
    
    protected void onMenuPressed() {
    }
    
    protected void actionClose() {
        this.setVisible(false);
    }
    
    protected void onHovered() {
    }
    
    protected final void updateMenuPosX(final int nMenuPosX) {
        try {
            if (nMenuPosX > this.getPosX()) {
                this.iNewMenuPositionX = this.getPosX();
                CFG.menuManager.setUpdateSliderMenuPosX(true);
            }
            else if (nMenuPosX < this.getWidth() + this.getPosX() - this.iMaxSliderPositionX) {
                this.iNewMenuPositionX = this.getWidth() + this.getPosX() - this.iMaxSliderPositionX;
                CFG.menuManager.setUpdateSliderMenuPosX(true);
            }
            else {
                this.iNewMenuPositionX = nMenuPosX;
            }
        }
        catch (final NullPointerException ex) {}
    }
    
    protected final void updateMenuPosY(final int nMenuPosY) {
        try {
            if (nMenuPosY > this.getPosY()) {
                this.iNewMenuPositionY = this.getPosY();
                CFG.menuManager.setUpdateSliderMenuPosY(true);
                this.scrollModeY = false;
            }
            else if (nMenuPosY < this.getHeight() + this.getPosY() - this.iMaxSliderPositionY) {
                this.iNewMenuPositionY = this.getHeight() + this.getPosY() - this.iMaxSliderPositionY;
                CFG.menuManager.setUpdateSliderMenuPosY(true);
                this.scrollModeY = false;
            }
            else {
                this.iNewMenuPositionY = nMenuPosY;
            }
            if (this.backbutton) {
                menuElements.get(0).setPosY(this.getHeight() - this.getNewMenuPosY() - menuElements.get(0).getHeight() + (CFG.GAME_HEIGHT - this.getHeight()));
            }
        }
        catch (final NullPointerException ex) {}
    }
    
    protected final void scrollTheMenu() {
        if (this.scrollableY && this.iScrollPosY > 0 && this.iScrollPosY2 > 0 && Math.abs(this.iScrollPosY - this.iScrollPosY2) > 3.0f * CFG.DENSITY) {
            this.fScrollNewMenuPosY = (this.iScrollPosY - this.iScrollPosY2) * 1.45f;
            this.scrollModeY = true;
        }
        if (this.scrollableX && this.iScrollPosX > 0 && this.iScrollPosX2 > 0 && Math.abs(this.iScrollPosX - this.iScrollPosX2) > 3) {
            this.fScrollNewMenuPosX = (this.iScrollPosX - this.iScrollPosX2) * 1.45f;
            this.scrollModeX = true;
        }
        this.resetScrollINFO();
    }
    
    private final void resetScrollINFO() {
        final int n = -1;
        this.iScrollPosX2 = n;
        this.iScrollPosX = n;
        this.iScrollPosY2 = n;
        this.iScrollPosY = n;
    }
    
    protected final void stopScrolling() {
        this.resetScrollINFO();
        final boolean b = false;
        this.scrollModeX = b;
        this.scrollModeY = b;
    }
    
    protected final void updatedButtonsWidth(int iStartPosX, final int iMinWidth) {
        for (int i = 0; i < this.getMenuElementsSize(); ++i) {
            iStartPosX += this.updateButtonWidth(i, iStartPosX, iMinWidth) + CFG.PADDING;
        }
        this.updateScrollable();
    }
    
    protected final void updatedButtonsWidth_Padding(int iStartPosX, final int iMinWidth, final int iPadding) {
        for (int i = 0; i < this.getMenuElementsSize(); ++i) {
            iStartPosX += this.updateButtonWidth(i, iStartPosX, iMinWidth) + iPadding;
        }
        this.updateScrollable();
    }
    
    protected final void updatedButtonsWidthFromToID(final int iStartButtonID, final int iEndButtonID, int iStartPosX, final int iMinWidth) {
        for (int i = iStartButtonID; i < iEndButtonID; ++i) {
            iStartPosX += this.updateButtonWidth(i, iStartPosX, iMinWidth) + CFG.PADDING;
        }
        this.updateScrollable();
    }
    
    protected final int updateButtonWidth(final int iButtonID, final int iStartPosX, final int iMinWidth) {
        if (this.getMenuElement(iButtonID).getTextWidth() + CFG.PADDING * 4 > iMinWidth) {
            this.getMenuElement(iButtonID).setWidth(this.getMenuElement(iButtonID).getTextWidth() + CFG.PADDING * 4);
        }
        else {
            this.getMenuElement(iButtonID).setWidth(iMinWidth);
        }
        this.getMenuElement(iButtonID).setPosX(iStartPosX);
        this.updateScrollable();
        return this.getMenuElement(iButtonID).getWidth();
    }
    
    protected final int getMenuElementsSize() {
        return this.iMenuElementsSize;
    }
    
    protected final MenuElement getMenuElement(final int iID) {
        return this.menuElements.get(iID);
    }
    
    protected final void setMenuElement(final int iID, final MenuElement nMenuElement) {
        this.menuElements.set(iID, null);
        this.menuElements.set(iID, nMenuElement);
    }
    
    protected int getPosX() {
        return this.iPosX;
    }
    
    protected void setPosX(final int iPosX) {
        this.iPosX = iPosX;
        this.updateMenuPosX(this.iMenuPosX = iPosX);
    }
    
    protected final void setPosX_Force(final int iPosX) {
        this.iPosX = iPosX;
        this.iMenuPosX = iPosX;
        this.iNewMenuPositionX = iPosX;
        CFG.menuManager.setUpdateSliderMenuPosX(false);
    }
    
    protected int getPosY() {
        return this.iPosY;
    }
    
    protected void setPosY(final int iPosY) {
        this.iPosY = iPosY;
        this.updateMenuPosY(this.iMenuPosY = iPosY);
    }
    
    protected int getWidth() {
        return this.iWidth;
    }
    
    protected boolean setWidth(final int iWidth) {
        if (iWidth >= CFG.GAME_WIDTH) {
            this.iWidth = CFG.GAME_WIDTH;
            return true;
        }
        if (iWidth < this.getMinWidth()) {
            this.iWidth = this.getMinWidth();
            return false;
        }
        this.iWidth = iWidth;
        return true;
    }
    
    protected final int getMinWidth() {
        try {
            return CFG.PADDING * 2;
        }
        catch (final NullPointerException ex) {
            return CFG.PADDING * 2;
        }
    }
    
    protected int getHeight() {
        return this.iHeight;
    }
    
    protected void setHeight(final int iHeight) {
        this.iHeight = iHeight;
        if (iHeight < this.getMinHeight()) {
            this.iHeight = this.getMinHeight();
        }
        if (iHeight + this.getPosY() + ((this.sliderMenuTitle != null) ? this.sliderMenuTitle.getHeight() : 0) >= CFG.GAME_HEIGHT) {
            this.iHeight = CFG.GAME_HEIGHT - (this.getPosY() + ((this.sliderMenuTitle != null) ? this.sliderMenuTitle.getHeight() : 0));
        }
        this.updateScrollable();
    }
    
    protected final int getMinHeight() {
        return CFG.PADDING + CFG.BUTTON_HEIGHT;
    }
    
    protected final SliderMenuTitle getTitle() {
        return this.sliderMenuTitle;
    }
    
    protected final boolean getScrollableY() {
        return this.scrollableY;
    }
    
    protected final void setMenuPosY(final int iMenuPosY) {
        this.updateMenuPosY(iMenuPosY);
    }
    
    protected int getMenuPosY() {
        return this.iMenuPosY;
    }
    
    protected final int getNewMenuPosY() {
        return this.iNewMenuPositionY;
    }
    
    protected final int getNewMenuPosX() {
        return this.iNewMenuPositionX;
    }
    
    protected final boolean getScrollableX() {
        return this.scrollableX;
    }
    
    protected final void setMenuPosX(final int iMenuPosX) {
        this.updateMenuPosX(iMenuPosX);
    }
    
    protected int getMenuPosX() {
        return this.iMenuPosX;
    }
    
    protected boolean getVisible() {
        return this.visible;
    }
    
    protected void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    protected final boolean getCloseable() {
        return this.closeable;
    }
    
    protected final boolean getMoveable() {
        return this.sliderMenuTitle != null && this.sliderMenuTitle.getMoveable();
    }
    
    protected final boolean getResizable() {
        return this.sliderMenuTitle != null && this.sliderMenuTitle.getResizable();
    }
    
    protected final void setScrollPosY(final int iScrollPosY) {
        this.iScrollPosY2 = this.iScrollPosY;
        this.iScrollPosY = iScrollPosY;
    }
    
    protected final int getScrollPosY() {
        return this.iScrollPosY;
    }
    
    protected final void setScrollPosX(final int iScrollPosX) {
        this.iScrollPosX2 = this.iScrollPosX;
        this.iScrollPosX = iScrollPosX;
    }
    
    protected final boolean getScrollModeY() {
        return this.scrollModeY;
    }
}
