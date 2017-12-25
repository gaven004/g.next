package com.g.sys;

import com.g.commons.db.generator.BaseGenerator;

public class McGenerator extends BaseGenerator {
    public static void main(String[] args) {
        new McGenerator().generate();
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
        return "sys.mc";
    }

    @Override
    public String[] getIncludeTable() {
        return new String[] {"sys_column", "sys_article"};
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
