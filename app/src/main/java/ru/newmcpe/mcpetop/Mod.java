package ru.newmcpe.mcpetop;

/**
 * Created by Newmcpe on 28.12.2017.
 */

public class Mod {
    private String name;
    private String version;
    private String desc;

    public Mod(String name, String version, String desc) {

        this.name = name;
        this.version = version;
        this.desc = desc;
    }

    String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
