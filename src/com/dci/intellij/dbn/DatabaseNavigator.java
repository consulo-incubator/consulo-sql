/*
 * Copyright 2012-2014 Dan Cioca
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

package com.dci.intellij.dbn;

import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.common.options.setting.SettingsUtil;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;

public class DatabaseNavigator implements ApplicationComponent, JDOMExternalizable
{
	public static DatabaseNavigator getInstance()
	{
		return ApplicationManager.getApplication().getComponent(DatabaseNavigator.class);
	}

	@Override
	@NotNull
	public String getComponentName()
	{
		return "DBNavigator";
	}

	private boolean debugModeEnabled;

	@Override
	public void initComponent()
	{
		FileTemplateManager templateManager = FileTemplateManager.getInstance();
		if(templateManager.getTemplate("SQL Script") == null)
		{
			templateManager.addTemplate("SQL Script", "sql");
		}
	}

	public boolean isDebugModeEnabled()
	{
		return debugModeEnabled;
	}

	public void setDebugModeEnabled(boolean debugModeEnabled)
	{
		this.debugModeEnabled = debugModeEnabled;
		SettingsUtil.isDebugEnabled = debugModeEnabled;
	}

	@Override
	public void disposeComponent()
	{
	}

	@Override
	public void readExternal(Element element) throws InvalidDataException
	{
		debugModeEnabled = SettingsUtil.getBoolean(element, "enable-debug-mode", false);
		SettingsUtil.isDebugEnabled = debugModeEnabled;
	}

	@Override
	public void writeExternal(Element element) throws WriteExternalException
	{
		SettingsUtil.setBoolean(element, "enable-debug-mode", debugModeEnabled);
	}
}

