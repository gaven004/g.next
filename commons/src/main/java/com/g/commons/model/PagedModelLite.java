package com.g.commons.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
//import org.springframework.hateoas.PagedModel;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(value = {
        "pageable",
        "totalPages",
        "totalElements",
        "first",
        "last",
        "size",
        "number",
        "sort",
        "numberOfElements",
        "empty"
})
public class PagedModelLite<T> extends PageImpl<T> implements Page<T> {
    public static PagedModelLite<?> NO_PAGE = new PagedModelLite<>();

    private boolean oneIndexedParameters = false;

    @JsonProperty(value = "page")
    private final PageMetadata metadata;

    public PagedModelLite() {
        this(new ArrayList<>());
    }

    public PagedModelLite(List<T> content) {
        this(content, Pageable.unpaged(), null == content ? 0 : content.size(), false);
    }

    public PagedModelLite(List<T> content, Pageable pageable, long total, boolean oneIndexedParameters) {
        super(content, pageable, total);
        this.metadata = new PageMetadata(getSize(), oneIndexedParameters ? getNumber() + 1 : getNumber(), getTotalElements(), getTotalPages());
    }

    public PagedModelLite(PageImpl<T> page, boolean oneIndexedParameters) {
        this(page.getContent(), page.getPageable(), page.getTotalElements(), oneIndexedParameters);
    }

    /**
     * Value object for pagination metadata.
     *
     * @author Oliver Gierke
     */
    public static class PageMetadata {

        @JsonProperty
        private long size;
        @JsonProperty
        private long totalElements;
        @JsonProperty
        private long totalPages;
        @JsonProperty
        private long number;

        protected PageMetadata() {

        }

        /**
         * Creates a new {@link PagedModel.PageMetadata} from the given size, number, total elements and total pages.
         *
         * @param size
         * @param number        zero-indexed, must be less than totalPages
         * @param totalElements
         * @param totalPages
         */
        public PageMetadata(long size, long number, long totalElements, long totalPages) {

            Assert.isTrue(size > -1, "Size must not be negative!");
            Assert.isTrue(number > -1, "Number must not be negative!");
            Assert.isTrue(totalElements > -1, "Total elements must not be negative!");
            Assert.isTrue(totalPages > -1, "Total pages must not be negative!");

            this.size = size;
            this.number = number;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
        }

        /**
         * Creates a new {@link PagedModel.PageMetadata} from the given size, number and total elements.
         *
         * @param size          the size of the page
         * @param number        the number of the page
         * @param totalElements the total number of elements available
         */
        public PageMetadata(long size, long number, long totalElements) {
            this(size, number, totalElements, size == 0 ? 0 : (long) Math.ceil((double) totalElements / (double) size));
        }

        /**
         * Returns the requested size of the page.
         *
         * @return the size a positive long.
         */
        public long getSize() {
            return size;
        }

        /**
         * Returns the total number of elements available.
         *
         * @return the totalElements a positive long.
         */
        public long getTotalElements() {
            return totalElements;
        }

        /**
         * Returns how many pages are available in total.
         *
         * @return the totalPages a positive long.
         */
        public long getTotalPages() {
            return totalPages;
        }

        /**
         * Returns the number of the current page.
         *
         * @return the number a positive long.
         */
        public long getNumber() {
            return number;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return String.format("Metadata { number: %d, total pages: %d, total elements: %d, size: %d }", number, totalPages,
                    totalElements, size);
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(@Nullable Object obj) {

            if (this == obj) {
                return true;
            }

            if (obj == null || !obj.getClass().equals(getClass())) {
                return false;
            }

            PageMetadata that = (PageMetadata) obj;

            return this.number == that.number && this.size == that.size && this.totalElements == that.totalElements
                    && this.totalPages == that.totalPages;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {

            int result = 17;
            result += 31 * (int) (this.number ^ this.number >>> 32);
            result += 31 * (int) (this.size ^ this.size >>> 32);
            result += 31 * (int) (this.totalElements ^ this.totalElements >>> 32);
            result += 31 * (int) (this.totalPages ^ this.totalPages >>> 32);
            return result;
        }
    }
}
