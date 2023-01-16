package com.g.sys.att.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhongsh
 * @version 2017/3/11
 */
public class AttachmentFile {

	private Long id;     // sys_attachment.id
	private MultipartFile file;
	private String rename;

	public AttachmentFile() {

	}

	public AttachmentFile(Long id, MultipartFile file) {
		this.id = id;
		this.file = file;
	}

	public AttachmentFile(Long id) {
		this.id = id;
	}

	public AttachmentFile(MultipartFile file) {
		this.file = file;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getRename() {
		return rename;
	}

	public void setRename(String rename) {
		this.rename = rename;
	}

}
