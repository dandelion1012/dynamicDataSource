package com.dds.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParameterizationString implements Serializable {
	private static final long serialVersionUID = 2447707138970151908L;
	private List<String> textParts = null;
	private List<String> paramParts = null;
	private String text = null;
	public ParameterizationString() {
		super();
	}
	public ParameterizationString(String str) {
		super();
		setText(str);
	} 
	public void setText(String str) {
		this.text = str!= null ? str.trim() : null; 
		try {
			parseString();
		} catch (RuntimeException e) {
			textParts = null;
			paramParts = null;
			throw e;
		}
	}
	public String getOriginalString() {
		return text;
	}
	private void parseString() {
		this.textParts = null;
		this.paramParts = null;
		if (this.text != null) {
			int index = this.text.indexOf("${");
			if (index != -1) {
				this.textParts = new ArrayList<String>();
				this.paramParts = new ArrayList<String>();
				this.textParts.add(this.text.substring(0, index));
				String str = this.text.substring(index);
				char[] chars = str.toCharArray();
				StringBuilder sb = new StringBuilder();
				boolean findParam = false;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] == '$' && chars[i + 1] == '{') {
						if (findParam) {
							throw new RuntimeException("url format error:"
									+ this.text);
						} else {
							if (sb.length() > 0) {
								textParts.add(sb.toString());
								sb.setLength(0);
							}
							findParam = true;
							i++;
							continue;
						}
					} else if (chars[i] == '}') {
						if (findParam) {
							if (sb.length() > 0) {
								paramParts.add(sb.toString());
								textParts.add(null);
								sb.setLength(0);
							}
							findParam = false;
							continue;
						} else {
							sb.append(chars[i]);
						}
					} else {
						sb.append(chars[i]);
					} 
				}
				if (findParam) {
					throw new RuntimeException("url format error:" + this.text);
				}else if(sb.length() > 0){
					textParts.add(sb.toString());
					sb.setLength(0);
				}
			}
		}
	}
	public String getRealString(Map<String, ? extends Object> params) {
		if (this.textParts == null) {
			return getOriginalString();
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0, paraIndex = 0; i < this.textParts.size(); i++) {
				String str = this.textParts.get(i);
				if (str != null) {
					sb.append(str);
				} else {
					String paraName = this.paramParts.get(paraIndex++);
					String paraValue = null;
					if (params.containsKey(paraName)) {
						Object obj = params.get(paraName);
						paraValue = obj != null ? obj.toString() : "${" + paraName + "}";
					} else {
						paraValue = "${" + paraName + "}";
					}
					sb.append(paraValue);
				}
			}
			return sb.toString();
		}
	}
}
