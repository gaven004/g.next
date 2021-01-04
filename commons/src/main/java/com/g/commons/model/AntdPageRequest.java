package com.g.commons.model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.g.commons.utils.ObjectMapperUtil;

public class AntdPageRequest {
    private Integer current = 0;
    private Integer pageSize = 10;
    private String sorter;
    private String filter;

    public Integer getCurrent() {
        return current;
    }

    /**
     * Antd的页码从1开始
     */
    public void setCurrent(Integer current) {
        this.current = current - 1;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSorter() {
        return sorter;
    }

    public void setSorter(String sorter) {
        try {
            this.sorter = URLDecoder.decode(sorter, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        try {
            this.filter = URLDecoder.decode(filter, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
    }

    public Pageable toPageable() {
        Sort sort = Sort.unsorted();

        if (StringUtils.hasText(sorter)) {
            ObjectMapper mapper = ObjectMapperUtil.getObjectMapper();
            List<Sort.Order> orderList = new ArrayList<>();
            try {
                JsonNode node = mapper.readTree(sorter);
                if (node.isObject()) {
                    Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
                    while (fields.hasNext()) {
                        Map.Entry<String, JsonNode> entry = fields.next();
                        if ("ascend".equals(entry.getValue().asText())) {
                            orderList.add(Sort.Order.asc(entry.getKey()));
                        } else if ("descend".equals(entry.getValue().asText())) {
                            orderList.add(Sort.Order.desc(entry.getKey()));
                        }
                    }
                }

                sort = Sort.by(orderList);
            } catch (JsonProcessingException e) {
                // ignore
            }
        }

        return PageRequest.of(current, pageSize, sort);
    }
}
