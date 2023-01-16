package com.g.bbs.web;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.g.bbs.model.BbsColumn;
import com.g.bbs.service.BbsColumnService;
import com.g.commons.web.ModelResolver;

@Component
public class BbsColumnModelResolver implements ModelResolver {
    private final BbsColumnService service;

    public BbsColumnModelResolver(BbsColumnService service) {
        this.service = service;
    }

    @Override
    public void resolve(Model model) {
        final Iterable<BbsColumn> columns = service.getTree();
        model.addAttribute("_BBS_COLUMNS", columns);
    }
}
