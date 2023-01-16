package com.g.bbs.web;

/**
 * Controller for domain model class BbsColumn.
 *
 * @author Hibernate Tools
 * @see com.g.bbs.web.BbsColumn
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.bbs.model.BbsColumn;
import com.g.bbs.model.QBbsColumn;
import com.g.bbs.persistence.BbsColumnRepository;
import com.g.bbs.service.BbsColumnService;
import com.g.commons.enums.Option;
import com.g.commons.exception.EntityNotFoundException;
import com.g.commons.exception.GenericAppException;
import com.g.commons.model.ApiResponse;
import com.g.commons.web.GenericController;
import com.g.sys.prop.SysPropertyCategory;
import com.g.sys.sec.model.QSysMenu;
import com.g.sys.sec.model.SysMenu;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("bbs/column")
public class BbsColumnController
        extends GenericController<BbsColumnService, BbsColumnRepository, BbsColumn, Long> {
    @GetMapping("/options")
    public ApiResponse<List<Option>> getOptions() {
        Iterable<BbsColumn> columns = service.findAll();
        if (columns.iterator().hasNext()) {
            List<Option> result = new ArrayList<>();
            columns.forEach(item -> result.add(new Option(item.getId().toString(), item.getName())));
            return ApiResponse.success(result);
        }
        return ApiResponse.success();
    }
}
