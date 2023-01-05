package com.g.sys.prop;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.g.commons.enums.Option;
import com.g.commons.model.ApiResponse;
import com.g.commons.web.GenericController;
import com.g.sys.sec.model.SecurityUser;
import com.g.sys.sec.web.WebSecurityHelper;

@RestController
@RequestMapping("sys/property/categories")
public class SysPropertyCategoriesController
        extends GenericController<SysPropertyCategoriesService, SysPropertyCategoriesRepository, SysPropertyCategory, String> {

    @GetMapping("/$options")
    public ApiResponse<List<Option>> getOptions() {
        Iterable<SysPropertyCategory> categories = service.findAll();
        if (categories.iterator().hasNext()) {
            List<Option> result = new ArrayList<>();
            categories.forEach(item -> result.add(new Option(item.getId(), item.getName())));
            return ApiResponse.success(result);
        }
        return ApiResponse.success();
    }

    @Override
    @PostMapping
    public ApiResponse<SysPropertyCategory> save(@RequestBody @Valid SysPropertyCategory entity) {
        final SecurityUser authUser = WebSecurityHelper.getAuthUser();
        return ApiResponse.success(service.save(authUser.getId(), entity));
    }

    @Override
    @PutMapping("/{category}")
    public ApiResponse<SysPropertyCategory> update(@RequestBody @Valid SysPropertyCategory entity) {
        final SecurityUser authUser = WebSecurityHelper.getAuthUser();
        return ApiResponse.success(service.update(authUser.getId(), entity));
    }

    @Override
    @PatchMapping("/{category}")
    public ApiResponse<SysPropertyCategory> patch(@RequestBody @Valid SysPropertyCategory entity) {
        final SecurityUser authUser = WebSecurityHelper.getAuthUser();
        return ApiResponse.success(service.update(authUser.getId(), entity));
    }

    @Override
    @DeleteMapping
    public ApiResponse<?> delete(@RequestParam(value = "ids[]") String[] ids) {
        final SecurityUser authUser = WebSecurityHelper.getAuthUser();
        for (String id : ids) {
            service.delete(authUser.getId(), id);
        }
        return ApiResponse.success();
    }

    @Override
    @DeleteMapping("/{category}")
    public ApiResponse<SysPropertyCategory> delete(@PathVariable String category) {
        final SecurityUser authUser = WebSecurityHelper.getAuthUser();
        service.delete(authUser.getId(), category);
        return ApiResponse.success();
    }
}
