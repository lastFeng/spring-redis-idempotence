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
package com.springboot.springredisidempotence.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/24 11:06
 *
 * Serializable工具（JDK）
 */
public class SerializableUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(SerializableUtil.class);

	/**
	 * 序列化
	 *
	 * @param object need serialization object
	 * @return byte array
	 * */
	public static byte[] serializable(Object object) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;

		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			return baos.toByteArray();
		} catch (IOException e) {
			LOGGER.error("serializable exception：" + e.getMessage());
			throw new RuntimeException("serializable exception：" + e.getMessage());
		} finally { // close
			try {
				if (oos != null) {
					oos.close();
				}
				if (baos != null) {
					baos.close();
				}

			} catch (IOException e) {
				LOGGER.error("serializable exception：" + e.getMessage());
				throw new RuntimeException("serializable exception：" + e.getMessage());
			}
		}
	}

	/**
	 * 反序列化
	 * @param bytes serializable bytes array
	 * @return object
	 * */
	public static Object unserializable(byte[] bytes) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;

		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException("unserializable exception: " + e.getMessage());
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if (bais != null) {
					bais.close();
				}
			} catch (IOException e) {
				LOGGER.error("unserializable exception: " + e.getMessage());
				throw new RuntimeException("unserializable exception: " + e.getMessage());

			}
		}
	}
}