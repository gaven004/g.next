package com.g.sys.msg.web;

/**
 * Controller for domain model class SysMessageRecipient.
 * @see com.g.sys.msg.web.SysMessageRecipient
 * @author Hibernate Tools
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.web.GenericController;
import com.g.sys.msg.model.*;
import com.g.sys.msg.persistence.*;
import com.g.sys.msg.service.*;

@RestController
@RequestMapping("sys/messageSysMessageRecipient")
public class SysMessageRecipientController
    extends GenericController<SysMessageRecipientService, SysMessageRecipientRepository, SysMessageRecipient, Long> {
}