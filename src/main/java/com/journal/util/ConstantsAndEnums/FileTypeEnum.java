package com.journal.util.ConstantsAndEnums;

public enum FileTypeEnum {

	image(GlobalConstants.IMAGE_FILE_KEY, GlobalConstants.IMAGE_FILE_VALUE),
	journal(GlobalConstants.JOURNAL_FILE_KEY, GlobalConstants.JOURNAL_FILE_VALUE);

	private String key;
	private String value;

	private FileTypeEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "FileTypeEnum{" +
				"key='" + key + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
