package com.g.sys.att.persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.g.NextApplicationTests;
import com.g.sys.att.model.Module;
import com.g.sys.att.model.SysAttachment;

class SysAttachmentRepositoryTest extends NextApplicationTests {
    @Autowired
    SysAttachmentRepository repository;

    @Test
    void findByModuleAndSrcRecode() {
        final Iterable<SysAttachment> atts = repository.findByModuleAndSrcRecode(Module.COMMON, "123");
        assertNotNull(atts);
    }
}
