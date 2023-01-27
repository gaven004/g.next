package com.g.bbs.web;

/**
 * Controller for domain model class BbsArticle.
 *
 * @author Hibernate Tools
 * @see com.g.bbs.web.BbsArticle
 */

import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.g.bbs.model.BbsArticle;
import com.g.bbs.model.BbsArticleStatus;
import com.g.bbs.persistence.BbsArticleRepository;
import com.g.bbs.service.BbsArticleService;
import com.g.commons.model.ApiResponse;
import com.g.commons.web.GenericController;
import com.g.sys.sec.model.SecurityUser;
import com.g.sys.sec.web.WebSecurityHelper;

@RestController
@RequestMapping("bbs/article")
public class BbsArticleController
        extends GenericController<BbsArticleService, BbsArticleRepository, BbsArticle, Long> {

    @Override
    @PostMapping
    public ApiResponse<BbsArticle> save(@Valid BbsArticle entity) {
        SecurityUser currentUser = WebSecurityHelper.getAuthUser();
        entity.setOperator(currentUser.getId());
        entity.setAuthor(currentUser.getNickname());
        return ApiResponse.success(service.save(entity));
    }

    @PatchMapping("/{id}/publish")
    public ApiResponse<BbsArticle> publish(@Valid BbsArticle entity) {
        SecurityUser currentUser = WebSecurityHelper.getAuthUser();
        entity.setOperator(currentUser.getId());
        entity.setAuthor(currentUser.getNickname());
        entity.setStatus(BbsArticleStatus.PUBLISH.toString());
        return ApiResponse.success(service.update(entity));
    }

    @PostMapping("/{id}/publish")
    public ApiResponse<BbsArticle> publish(@PathVariable Long id) {
        BbsArticle entity = service.get(id);
        entity.setStatus(BbsArticleStatus.PUBLISH.toString());
        return ApiResponse.success(service.update(entity));
    }

    @PostMapping("/{id}/withdraw")
    public ApiResponse<BbsArticle> withdraw(@PathVariable Long id) {
        BbsArticle entity = service.get(id);
        entity.setStatus(BbsArticleStatus.WITHDRAWAL.toString());
        return ApiResponse.success(service.update(entity));
    }

    @Override
    @PutMapping("/{id}")
    public ApiResponse<BbsArticle> update(@Valid BbsArticle entity) {
        return ApiResponse.success(service.update(entity));
    }

    @Override
    @PatchMapping("/{id}")
    public ApiResponse<BbsArticle> patch(@Valid BbsArticle entity) {
        return ApiResponse.success(service.update(entity));
    }

}
