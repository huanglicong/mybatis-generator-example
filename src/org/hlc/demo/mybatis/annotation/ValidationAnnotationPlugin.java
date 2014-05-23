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
package org.hlc.demo.mybatis.annotation;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 
 * TODO.
 *
 * @author huanglicong
 * @version V1.0
 */
public class ValidationAnnotationPlugin extends PluginAdapter {

	public static String projectName = "";
	public static String version = "";

	private String className;
	private FullyQualifiedJavaType notnull;
	private FullyQualifiedJavaType pattern;
	private FullyQualifiedJavaType email;
	private FullyQualifiedJavaType length;
	private FullyQualifiedJavaType parameterValidater;

	public ValidationAnnotationPlugin() {

		this.notnull = new FullyQualifiedJavaType("javax.validation.constraints.NotNull");
		this.pattern = new FullyQualifiedJavaType("javax.validation.constraints.Pattern");
		this.email = new FullyQualifiedJavaType("org.hibernate.validator.constraints.Email");
		this.length = new FullyQualifiedJavaType("org.hibernate.validator.constraints.Length");
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	@Override
	public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	protected void makeSerializable(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

		topLevelClass.addImportedType(this.notnull);
		topLevelClass.addImportedType(this.pattern);
		topLevelClass.addImportedType(this.email);
		topLevelClass.addImportedType(this.length);
		topLevelClass.addImportedType(this.parameterValidater);
	}

	public boolean isPrimaryKey(IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

		return introspectedTable.getPrimaryKeyColumns().size() == 0 || introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty().equals(introspectedColumn.getJavaProperty());
	}

	private static final String[] CONIFS = {

	"@null#User:spell/registrationtimestamp/persontype#Account:account#Schedule:id#Ratio:id/jnyid#Journey:id/state/organizer/departure#Expenses:creator/modifier/createdtimes/updatetimes#Car:owner#Equipment:owner#Parties:owner#ScheduleActivity:activityplace",

	"@phone#Parties:phone#User:phone",

	"@email#User:email",

	"@ip4#Account:ip/lastip",

	"@Pattern[0-1]#Parties:sex#User:sex",

	"@Pattern[0-5]#Parties:persontype#User:persontype#Ratio:persontype",

	"@Pattern[0-5]#Account:logintype",

	"@Pattern[0-2]#Account:state",

	"@Pattern[0-7]#Schedule:activitytype",

	"@Pattern[1-5]#Ratio:item"

	};

	public void setNotNull(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

		// System.out.println(this.className);
		String[] temps = CONIFS[0].split("#");
		int idx = 0;
		String property = introspectedColumn.getJavaProperty();
		boolean bool = false;
		for (int i = 1; i < temps.length; i++) {
			idx = temps[i].indexOf(this.className + ":");
			if (idx >= 0 && temps[i].indexOf(property) > idx || isPrimaryKey(introspectedTable, introspectedColumn)) {
				bool = true;
				break;
			}

		}

		if (!bool && !introspectedColumn.isNullable()) {
			field.addAnnotation("@NotNull(message = \"" + introspectedColumn.getJavaProperty() + "�ֶ�ֵ����Ϊ��\")");
		}
	}

	public void setPattern(Field field, IntrospectedColumn introspectedColumn) {

		String property = introspectedColumn.getJavaProperty();
		for (int i = 4; i < CONIFS.length; i++) {
			String[] temps = CONIFS[i].split("#");
			String pattern = temps[0].replace("@Pattern", "");

			for (int j = 1; j < temps.length; j++) {
				int idx = temps[j].indexOf(this.className + ":");
				if (idx >= 0 && temps[j].indexOf(property) > idx) {

					String[] tt = temps[j].split("[:,]");
					for (String ttt : tt) {
						if (property.equals(ttt)) {
							field.addAnnotation("@Pattern(regexp = \"" + pattern + "\", message = \"" + property + "�ֶ�ֵ��������������ʽ" + pattern + "\")");
							break;
						}
					}

					return;
				}
			}
		}
	}

	public void setEmial(Field field, IntrospectedColumn introspectedColumn) {

		String[] temps = CONIFS[2].split("#");
		int idx = 0;
		String property = introspectedColumn.getJavaProperty();
		for (int i = 1; i < temps.length; i++) {
			idx = temps[i].indexOf(this.className + ":");
			if (idx >= 0 && temps[i].indexOf(property) > idx) {
				field.addAnnotation("@Email(message = \"email�ֶ�ֵ������Email��ʽ\")");
				return;
			}
		}
	}

	public void setPhone(Field field, IntrospectedColumn introspectedColumn) {
		String[] temps = CONIFS[1].split("#");
		int idx = 0;
		String property = introspectedColumn.getJavaProperty();
		for (int i = 1; i < temps.length; i++) {
			idx = temps[i].indexOf(this.className + ":");
			if (idx >= 0 && temps[i].indexOf(property) > idx) {
				field.addAnnotation("@Pattern(regexp = ParameterValidator.PHONE_PATTERN, message = \"phone�ֶ�ֵ�������ֻ�Ÿ�ʽ\")");
				return;
			}
		}
	}

	public void setIP4(Field field, IntrospectedColumn introspectedColumn) {
		String[] temps = CONIFS[3].split("#");
		int idx = 0;
		String property = introspectedColumn.getJavaProperty();
		for (int i = 1; i < temps.length; i++) {
			idx = temps[i].indexOf(this.className + ":");
			if (idx >= 0 && temps[i].indexOf(property) > idx) {
				field.addAnnotation("@Pattern(regexp = ParameterValidator.IP4_PATTERN, message = \"" + introspectedColumn.getJavaProperty() + "�ֶ�ֵ������IP4��ʽ\")");
				return;
			}
		}
	}

	public void setLength(Field field, IntrospectedColumn introspectedColumn) {

		if (introspectedColumn.getFullyQualifiedJavaType().toString().equals("java.lang.String")) {
			int length = introspectedColumn.getLength();
			field.addAnnotation("@Length(max = " + length + ", message = \"" + introspectedColumn.getJavaProperty() + "�ֶ�ֵ���ܳ���" + length + "\")");
		}
	}

	public void setDate(Field field, IntrospectedColumn introspectedColumn) {

		if (introspectedColumn.getFullyQualifiedJavaType().toString().equals("java.util.Date")) {
			field.addAnnotation("@Pattern(regexp = ParameterValidator.DATE_OR_TIME_PATTERN, message =  \"�ֶ�ֵ������������ʽ\"+ParameterValidator.DATE_OR_TIME_PATTERN)");
		}
	}

	public void setLong(Field field, IntrospectedColumn introspectedColumn) {

		if (introspectedColumn.getFullyQualifiedJavaType().toString().equals("java.lang.Long")) {
			field.addAnnotation("@Pattern(regexp = ParameterValidator.NUMBER_PATTERN, message =  \"�ֶ�ֵ������������ʽ\"+ParameterValidator.NUMBER_PATTERN)");
		}
	}

	public void setBigDecimal(Field field, IntrospectedColumn introspectedColumn) {

		if (introspectedColumn.getFullyQualifiedJavaType().toString().equals("java.math.BigDecimal")) {
			field.addAnnotation("@Pattern(regexp = ParameterValidator.DECIMAL_PATTERN, message =  \"�ֶ�ֵ������������ʽ\"+ParameterValidator.DECIMAL_PATTERN)");
		}
	}

	@Override
	public boolean validate(List<String> warnings) {

		return true;
	}

	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

//		String property = introspectedColumn.getJavaProperty();
		this.className = topLevelClass.getType().getShortName();
		// System.out.println(this.className + ":" + property);

		// @NotNull
		setNotNull(field, introspectedTable, introspectedColumn);

		// @Pattern
		setPattern(field, introspectedColumn);

		// @Email
		setEmial(field, introspectedColumn);

		// Phone
		setPhone(field, introspectedColumn);

		// IP4
		setIP4(field, introspectedColumn);

		// @Length
		setLength(field, introspectedColumn);

		// Date
		// setDate(field, introspectedColumn);

		// Long
		// setLong(field, introspectedColumn);

		// BigDecimal
		// setBigDecimal(field, introspectedColumn);
		return true;
	}

}
