package com.dds.service;

import java.util.List;

import com.dds.vo.User;

public interface AppService {
	public User queryUser(String userName) throws Exception;
	
	public void insertText(String text) throws Exception;
	public List<String> getAllText() throws Exception;
}
