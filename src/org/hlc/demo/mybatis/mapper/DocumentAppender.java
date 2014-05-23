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
package org.hlc.demo.mybatis.mapper;

import java.io.File;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hlc.demo.mybatis.CodeGeneratorRuner;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * 
 * TODO.
 *
 * @author huanglicong
 * @version V1.0
 */
public class DocumentAppender {

	private String path;
	private Document document;

	public DocumentAppender(String path, Document document) {
		this.path = path;
		this.document = document;
	}

	@SuppressWarnings("unchecked")
	public void send(Element xml) {

		XmlElement element = new XmlElement(xml.getName());
		List<org.dom4j.Attribute> attributes = xml.attributes();
		for (org.dom4j.Attribute temp : attributes) {
			Attribute att = new Attribute(temp.getName(), temp.getValue());
			element.addAttribute(att);
		}
		// System.out.println(xml.getText());
		element.addElement(new TextElement(xml.asXML()));
		this.document.getRootElement().addElement(element);
	}

	public void execute() {

		File extFile = new File(CodeGeneratorRuner.class.getResource("/" + this.path).getFile());
		try {
			SAXReader reader = new SAXReader();
			org.dom4j.Document source = DocumentHelper.parseText(reader.read(extFile).asXML());
			@SuppressWarnings("unchecked")
			List<Element> elements = source.getRootElement().elements();
			for (Element temp : elements) {
				this.document.getRootElement().addElement(new TextElement(temp.asXML()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
