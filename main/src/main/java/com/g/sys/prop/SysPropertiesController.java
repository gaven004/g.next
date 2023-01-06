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
@RequestMapping("sys/properties")
public class SysPropertiesController
        extends GenericController<SysPropertiesService, SysPropertiesRepository, SysProperty, Long> {

    @GetMapping("/options")
    ApiResponse<List<Option>> getOptions() {
        Iterable<SysProperty> properties = service.findAll();
        if (properties != null && properties.iterator().hasNext()) {
            List<Option> result = new ArrayList<>();
            properties.forEach(item -> {
                result.add(new Option(item.getName(), item.getValue()));
            });

            return ApiResponse.success(result);
        }
        return ApiResponse.success();
    }

    @PostMapping
    public ApiResponse<SysProperty> save(@RequestBody SysProperty entity) {
        final SecurityUser authUser = WebSecurityHelper.getAuthUser();
        return ApiResponse.success(service.save(authUser.getId(), entity));
    }

    @PutMapping("/{id}")
    public ApiResponse<SysProperty> update(@RequestBody SysProperty entity) {
        final SecurityUser authUser = WebSecurityHelper.getAuthUser();
        return ApiResponse.success(service.update(authUser.getId(), entity));
    }

    @PatchMapping("/{id}")
    public ApiResponse<SysProperty> patch(@RequestBody SysProperty entity) {
        final SecurityUser authUser = WebSecurityHelper.getAuthUser();
        return ApiResponse.success(service.update(authUser.getId(), entity));
    }

    @DeleteMapping
    public ApiResponse<?> delete(@RequestParam(value = "ids[]") Long[] ids) {
        final SecurityUser authUser = WebSecurityHelper.getAuthUser();
        for (Long id : ids) {
            service.delete(authUser.getId(), id);
        }
        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<SysProperty> delete(@PathVariable Long id) {
        final SecurityUser authUser = WebSecurityHelper.getAuthUser();
        service.delete(authUser.getId(), id);
        return ApiResponse.success();
    }
}
