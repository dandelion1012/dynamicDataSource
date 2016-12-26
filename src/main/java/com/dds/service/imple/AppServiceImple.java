package com.dds.service.imple;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dds.dao.MainDBDAO;
import com.dds.dao.UserOperatorDAO;
import com.dds.service.AppService;
import com.dds.vo.User;
@Service
public class AppServiceImple implements AppService {
	@Resource
	private MainDBDAO mainDBDAO = null;
	@Resource
	private UserOperatorDAO userOperatorDAO = null;
	@Override
	public User queryUser(String userName) throws Exception {
		User user = mainDBDAO.findUser(userName);
		if(user == null){
			 user = new User();
			 user.setUserName(userName);
			 user.setDbName(userName+"DB");
			 mainDBDAO.insertUser(user);
		}
		return user;
	}
	@Override
	public void insertText(String text) throws Exception {
		userOperatorDAO.insertText(text);
		
	}
	@Override
	public List<String> getAllText() throws Exception {
		return userOperatorDAO.queryAllTexts();
	}

}
