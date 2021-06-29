package com.example.jnp2biggie;

class Flags {
    private boolean nsfw;
    private boolean religious;
    private boolean political;
    private boolean racist;
    private boolean sexist;
    private boolean explicit;

    public boolean isNsfw() {
        return nsfw;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }

    public boolean isReligious() {
        return religious;
    }

    public void setReligious(boolean religious) {
        this.religious = religious;
    }

    public boolean isPolitical() {
        return political;
    }

    public void setPolitical(boolean political) {
        this.political = political;
    }

    public boolean isRacist() {
        return racist;
    }

    public void setRacist(boolean racist) {
        this.racist = racist;
    }

    public boolean isSexist() {
        return sexist;
    }

    public void setSexist(boolean sexist) {
        this.sexist = sexist;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }
}

public class Joke {
    private boolean error;
    private String category;
    private String type;
    private String joke;
    private String setup;
    private String delivery;
    private Flags flags;
    private int id;
    private boolean safe;
    private String lang;

    private static final String deliverySeparator = "\n.\n.\n.\n.\n";
    private static final String unknownJokeType = "\n" +
            "I hate it when Windows 10 resets my default browser...\n" +
            "It puts me on Edge every time\n";
    private static final String youMayLaughNow = "\r\n";

    public static String getDeliverySeparator() {
        return deliverySeparator;
    }

    public static String getYouMayLaughNow() {
        return youMayLaughNow;
    }

    @Override
    public String toString() {
        if (type.equals("single")) {
            return joke + youMayLaughNow;
        } else if (type.equals("twopart")) {
            return setup + deliverySeparator + delivery + youMayLaughNow;
        } else {
            return unknownJokeType + youMayLaughNow;
        }
    }

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
