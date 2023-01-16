// Generated 2022年12月10日 下午10:08:51 by Hibernate Tools 6.0.0.Alpha2

package com.g.bbs.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.g.commons.model.AbstractEntity;
import com.g.sys.att.model.AttachmentFile;

/**
 * BbsArticle generated by hbm2java
 */
@Entity
@Table(name = "bbs_article")
@DynamicInsert
@DynamicUpdate
public class BbsArticle extends AbstractEntity implements java.io.Serializable {

    private Long id;
    private Long columnId;
    private String title;
    private String content;
    private String status;
    private String author;
    private Long operator;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
    private List<AttachmentFile> files;

    public BbsArticle() {
    }

    public BbsArticle(Long id, Long columnId, String title, String content, String status, String author, Long operator, LocalDateTime ctime) {
        this.id = id;
        this.columnId = columnId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.author = author;
        this.operator = operator;
        this.ctime = ctime;
    }

    public BbsArticle(Long id, Long columnId, String title, String content, String status, String author, Long operator, LocalDateTime ctime, LocalDateTime mtime) {
        this.id = id;
        this.columnId = columnId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.author = author;
        this.operator = operator;
        this.ctime = ctime;
        this.mtime = mtime;
    }

    @Id
    @GeneratedValue(
            generator = "snowflake_generator"
    )
    @GenericGenerator(
            name = "snowflake_generator",
            strategy = "com.g.commons.utils.HibernateSnowflakeGenerator"
    )
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "column_id", nullable = false)
    public Long getColumnId() {
        return this.columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    @Column(name = "title", nullable = false, length = 100)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content", nullable = false)
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "status", nullable = false, length = 10)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "author", nullable = false, length = 50)
    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column(name = "operator", nullable = false)
    public Long getOperator() {
        return this.operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    @Column(name = "ctime", nullable = false, length = 19)
    public LocalDateTime getCtime() {
        return this.ctime;
    }

    public void setCtime(LocalDateTime ctime) {
        this.ctime = ctime;
    }

    @Column(name = "mtime", length = 19)
    public LocalDateTime getMtime() {
        return this.mtime;
    }

    public void setMtime(LocalDateTime mtime) {
        this.mtime = mtime;
    }

    @Transient
    @JsonIgnore
    public List<AttachmentFile> getFiles() {
        return files;
    }

    public void setFiles(List<AttachmentFile> files) {
        this.files = files;
    }
}

