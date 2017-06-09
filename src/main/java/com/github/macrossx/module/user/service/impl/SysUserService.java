/**
 * Copyright (C) 2016 X-Forever.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.macrossx.module.user.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.github.macrossx.module.user.service.ISysUserService;
import com.github.macrossx.module.user.service.entity.SysUser;
import com.github.macrossx.module.user.service.entity.UserToken;
import com.github.x19990416.macrossx.spring.jdbc.JDBCHelper;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Service("module.user.SysUserService")
@Slf4j
public class SysUserService implements ISysUserService {
	@Resource
	JdbcTemplate jdbcTemplate;

	public SysUser getUser(Long id) {
		return JDBCHelper.get(jdbcTemplate, "select * from sys_user", SysUser.class, id);
	}

	public Long create(SysUser sysUser) {
		return JDBCHelper.insertAndGetKey(jdbcTemplate, "inser into sys_user (username,password,role) values (?,?,?)",
				sysUser.getUsername(), sysUser.getPassword(), sysUser.getRole());
	}

	public int update(SysUser sysUser) {
		return JDBCHelper.updateByKey(jdbcTemplate, sysUser);
	}

	public UserToken login(String username, String password) {
		List<SysUser> list = JDBCHelper.listObj(jdbcTemplate, "select * from sys_user where username=?", SysUser.class,
				username);
		if (list.size() != 1) {
			return null;
		}
		SysUser user = list.get(0);
		UserToken token = new UserToken();
		token.setId(user.getId());
		token.setTime(System.currentTimeMillis());
		try {
			if (StringUtils.isEmpty(user.getToken()))
				token.setRole(null);
			else
				token.setRole(new Gson().getAdapter(new com.google.gson.reflect.TypeToken<List<String>>() {
				}).fromJson(user.getToken()));
		} catch (IOException e) {
			log.error("{}", e);
			return null;
		}
		return null;
	}

}
