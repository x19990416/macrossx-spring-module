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
package com.github.macrossx.module.user.service.entity;

import com.github.x19990416.macrossx.spring.jdbc.Column;
import com.github.x19990416.macrossx.spring.jdbc.Key;
import com.github.x19990416.macrossx.spring.jdbc.Table;

import lombok.Data;

@Data
@Table("sys_user")
public class SysUser {
	@Column("id")
	@Key
	private Long id;
	@Column("username")
	private String username;
	@Column("token")
	private String token;
	@Column("password")
	private String password;
	@Column("role")
	private String role;
}
