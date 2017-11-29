package com.g.sys;

import com.g.commons.db.generator.BaseGenerator;

public class UsrGenerator extends BaseGenerator {
    public static void main(String[] args) {
        new UsrGenerator().generate();
    }

    @Override
    public String getAuthor() {
        return "Gaven";
    }

    @Override
    public String getBaseDir() {
        return "c:\\temp\\generate";
    }

    @Override
    public String getModuleName() {
        return "sys.sec";
    }

    @Override
    public String[] getIncludeTable() {
        return new String[] {"sys_users", "sys_authorities", "sys_persistent_logins"};
    }

    @Override
    public boolean isGenerateModel() {
        return true;
    }

    @Override
    public boolean isGenerateMapper() {
        return true;
    }

    @Override
    public boolean isGenerateXml() {
        return true;
    }

    @Override
    public boolean isGenerateService() {
        return true;
    }

    @Override
    public boolean isGenerateController() {
        return true;
    }

}
