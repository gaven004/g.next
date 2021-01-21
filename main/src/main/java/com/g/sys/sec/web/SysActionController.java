package com.g.sys.sec.web;

/**
 * Controller for domain model class SysAction.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysAction
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.web.GenericController;
import com.g.sys.sec.model.SysAction;
import com.g.sys.sec.persistence.SysActionRepository;
import com.g.sys.sec.service.SysActionService;

@RestController
@RequestMapping("sys/action")
public class SysActionController
        extends GenericController<SysActionService, SysActionRepository, SysAction, Long> {
}