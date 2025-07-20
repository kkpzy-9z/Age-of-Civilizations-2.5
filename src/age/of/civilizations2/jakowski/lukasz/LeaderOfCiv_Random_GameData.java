package age.of.civilizations2.jakowski.lukasz;

import java.io.*;

class LeaderOfCiv_Random_GameData extends LeaderOfCiv_GameData implements Serializable
{
    private static final long serialVersionUID = 0L;
    private String sRiseToPower = "";
    private boolean canAppearNaturally = true;

    protected final String getRiseToPower() {
        return this.sRiseToPower;
    }

    protected final void setRiseToPower(final String riseToPower) {
        this.sRiseToPower = riseToPower;
    }

    protected final boolean canAppearNaturally() {
        return this.canAppearNaturally;
    }

    protected final void setCanAppearNaturally(final boolean canAppearNaturally) {
        this.canAppearNaturally = canAppearNaturally;
    }

    LeaderOfCiv_Random_GameData() {
        super();
        this.setWiki("");
        this.setRandom(true);
    }
}
