/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2008-2012 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.api.platform;

import org.sonar.api.ServerComponent;

/**
 * @since 2.11
 */
public interface ServerPluginRepository extends PluginRepository, ServerComponent {

  /**
   * Disabled plugins are not loaded by batch, but they are still installed :
   * <ul>
   *   <li>Plugin properties are available in General Settings</li>
   *   <li>Plugin is marked as installed in Update Center</li>
   * </ul>
   */
  void disable(String pluginKey);

  /**
   * @param plugingKey can not be null
   */
  boolean isDisabled(String plugingKey);

}
