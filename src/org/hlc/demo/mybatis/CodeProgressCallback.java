/*
 * Copyright 2002-2013 the original author or authors.
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
package org.hlc.demo.mybatis;

import org.hlc.demo.mybatis.utils.Log;
import org.mybatis.generator.api.ProgressCallback;

/**
 * 
 * 代码生成器处理回调.
 *
 * @author huanglicong
 * @version V1.0
 */
public class CodeProgressCallback implements ProgressCallback {

	public void introspectionStarted(int totalTasks) {
		Log.print("A1：" + totalTasks);
	}

	public void generationStarted(int totalTasks) {
		Log.print("A2：" + totalTasks);
	}

	public void saveStarted(int totalTasks) {
		Log.print("A3：" + totalTasks);
	}

	public void startTask(String taskName) {
		Log.print("A4：" + taskName);
	}

	public void done() {
		Log.print("执行完成");
	}

	public void checkCancel() throws InterruptedException {
		Log.print("清除任务");
	}

}
