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
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.github.macrossx.module.user.service.ISysRoleService;
import com.github.macrossx.module.user.service.entity.SysRole;
import com.github.x19990416.macrossx.spring.jdbc.JDBCHelper;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

/**
 * 用戶URL访问控制用，从数据库中抽取类型为URL的角色访问控制规则，存入map中。其中rulpatten 以{@code List<String>}
 * 的json格式存放于数据库 TYPE 为URL-TYPE-ROLE
 *
 */

@Service("module.user.SysUrlRoleService")
public class SysUrlRoleService implements ISysRoleService{
	@Resource
	JdbcTemplate jdbcTemplate;
	private static String type = "URL-TYPE-ROLE";
	private static Map<String, List<String>> patten = Maps.newConcurrentMap();

	public List<String> getRole(String role) {
		if (patten.isEmpty())
			loadRole();
		return patten.get(role);
	}

	public SysRole get(Long id) {
		return JDBCHelper.get(jdbcTemplate, "select * from sys_role", SysRole.class, id);
	}

		
	public int update(SysRole role) {
		int result = JDBCHelper.updateByKey(jdbcTemplate, role);
		if (result == 1) {
			try {
				patten.put(role.getName(), new Gson().getAdapter(new com.google.gson.reflect.TypeToken<List<String>>() {
				}).fromJson(role.getPatten()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	private void loadRole() {
		patten.clear();
		JDBCHelper.listObj(jdbcTemplate, "select * from sys_role where role=? and type=?", SysRole.class, type)
				.forEach(e -> {
					try {
						patten.put(e.getName(),
								new Gson().getAdapter(new com.google.gson.reflect.TypeToken<List<String>>() {
								}).fromJson(e.getPatten()));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
	}
	
public static void main(String...s){
	org.springframework.util.AntPathMatcher a = new org.springframework.util.AntPathMatcher();
	System.out.println(a.match("/index/**","/index/a/b"));
	
}

}
