package com.dds.controllers;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dds.imple.DynamicDataSource;
import com.dds.service.AppService;
import com.dds.utils.AppUtils;
import com.dds.vo.User;

@Controller
public class BaseController {
	@Resource
	private AppService appService = null;

	@RequestMapping(value = "/")
	public String loginui() {
		return "index";
	}

	@RequestMapping(value = "/doLogin")
	public String doLogin(@RequestParam("userName") String userName, Model model, HttpSession session) {
		try {
			User user = appService.queryUser(userName);
			session.setAttribute("user", user);
			model.addAttribute("user", user);
			DynamicDataSource.setDSID(user.getDbName());
			List<String> texts = appService.getAllText();
			model.addAttribute("texts", texts);
			return "homePage";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	@RequestMapping(value = "/doLogout")
	public String doLogout(HttpSession session) {
		try {
			session.removeAttribute("user");
			DynamicDataSource.setDSID(null);
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@RequestMapping(value = "/doInsertText")
	public String doInsertTextAndShowAll(@RequestParam("text") String text, Model model, HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			if (user == null) {
				return "index";
			} else {
				DynamicDataSource.setDSID(user.getDbName());
				appService.insertText(text);
				List<String> texts = appService.getAllText();
				model.addAttribute("texts", texts);
				return "homePage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
