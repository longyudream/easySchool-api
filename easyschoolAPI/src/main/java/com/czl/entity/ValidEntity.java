package com.czl.entity;

public class ValidEntity {

	/** Item ID */
	private String item;

	/** Item 标题 */
	private String itemTitle;

	/** 校验信息 */
	private String message;

	public ValidEntity() {
	}

	public ValidEntity(String item, String itemTitle, String message) {
		this.item = item;
		this.itemTitle = itemTitle;
		this.message = message;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
