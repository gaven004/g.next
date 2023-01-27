package com.g.sys.msg.service;

/**
 * Service for domain model class SysMessageRecipient.
 * @see com.g.sys.msg.service.SysMessageRecipient
 * @author Hibernate Tools
 */

import org.springframework.stereotype.Service;

import com.g.commons.service.GenericService;
import com.g.sys.msg.model.*;
import com.g.sys.msg.persistence.*;

@Service
public class SysMessageRecipientService
    extends GenericService<SysMessageRecipientRepository, SysMessageRecipient, Long> {
}