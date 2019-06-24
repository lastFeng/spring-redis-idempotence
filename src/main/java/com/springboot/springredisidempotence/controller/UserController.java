/*
 * Copyright 2001-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springboot.springredisidempotence.controller;

import com.springboot.springredisidempotence.common.ServerResponse;
import com.springboot.springredisidempotence.domain.User;
import com.springboot.springredisidempotence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/24 14:00
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * 通过id获取user
	 * @param id 用户id
	 * @return user string
	 * */
	@GetMapping("{id}")
	public String getOne(@PathVariable("id") Integer id) {
		User user = userService.getOne(id);
		// 为空，输出提示
		if (user == null) {
			return "user not exists";
		}
		return user.toString();
	}

	/**
	 * 新增User
	 * 提示成功即可
	 */
	@PostMapping
	public String add(User user) {
		userService.add(user);
		return "add successfully";
	}

	/**
	 * 更新user信息
	 * */
	@PostMapping
	public String update(User user) {
		userService.update(user);
		return "update successfully";
	}

	/**
	 * 删除User
	 * */
	@DeleteMapping("{id}")
	public String delete(@PathVariable("id") Integer id) {
		userService.delete(id);
		return "delete successfully";
	}

	/**
	 * 通过用户名和密码登录
	 * */
	@PostMapping("login")
	public ServerResponse login(String username, String password) {
		return userService.login(username, password);
	}
}