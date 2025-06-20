package age.of.civilizations2.jakowski.lukasz;

import java.io.*;
import java.util.*;

class Leader_Random_GameData implements Serializable
{
    private static final long serialVersionUID = 0L;
    private LeaderOfCiv_Random_GameData leaderOfCiv;
    private List<String> sCivs;
    private List<String> sAITypes;

    Leader_Random_GameData() {
        super();
        this.leaderOfCiv = new LeaderOfCiv_Random_GameData();
        this.sCivs = new ArrayList<>();
        this.sAITypes = new ArrayList<>();
    }
    
    protected final void addCiv(final String nTag) {
        if (this.sCivs == null) {
            this.sCivs = new ArrayList<>();
        } else if (this.sCivs.contains(nTag)) {
            return;
        }

        this.sCivs.add(nTag);
    }
    
    protected final void removeCiv(final int i) {
        this.sCivs.remove(i);
    }
    
    protected final String getCiv(final int i) {
        return this.sCivs.get(i);
    }
    
    protected final int getCivsSize() {
        return this.sCivs.size();
    }

    protected final boolean containsCiv(final String nTag) {
        return this.sCivs.contains(nTag);
    }

    protected final void addAIType(final String nTag) {
        if (this.sAITypes == null) {
            this.sAITypes = new ArrayList<>();
        } else if (this.sAITypes.contains(nTag)) {
            return;
        }

        this.sAITypes.add(nTag);
    }

    protected final void removeAIType(final int i) {
        this.sAITypes.remove(i);
    }

    protected final String getAIType(final int i) {
        return this.sAITypes.get(i);
    }

    protected final int getAITypesSize() {
        return this.sAITypes.size();
    }

    protected final boolean containsAIType(final String nTag) {
        return this.sAITypes.contains(nTag);
    }
    
    protected final LeaderOfCiv_Random_GameData getLeaderOfCiv() {
        return this.leaderOfCiv;
    }
    
    protected final void setLeaderOfCiv(final LeaderOfCiv_Random_GameData leaderOfCiv) {
        this.leaderOfCiv = leaderOfCiv;
    }
}
