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
package org.sonar.core.dashboard;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public final class WidgetDto {
  private Long id;
  private Long dashboardId;
  private String key;
  private String name;
  private String description;
  private Integer columnIndex;
  private Integer rowIndex;
  private boolean configured;
  private Date createdAt;
  private Date updatedAt;
  private Integer resourceId;
  private List<WidgetPropertyDto> widgetPropertyDtos = Lists.newArrayList();

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public WidgetDto setId(Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return the dashboardId
   */
  public Long getDashboardId() {
    return dashboardId;
  }

  /**
   * @param dashboardId the dashboardId to set
   */
  public WidgetDto setDashboardId(Long dashboardId) {
    this.dashboardId = dashboardId;
    return this;
  }

  /**
   * @return the key
   */
  public String getKey() {
    return key;
  }

  /**
   * @param key the key to set
   */
  public WidgetDto setKey(String key) {
    this.key = key;
    return this;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public WidgetDto setName(String name) {
    this.name = name;
    return this;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public WidgetDto setDescription(String description) {
    this.description = description;
    return this;
  }

  /**
   * @return the columnIndex
   */
  public Integer getColumnIndex() {
    return columnIndex;
  }

  /**
   * @param columnIndex the columnIndex to set
   */
  public WidgetDto setColumnIndex(Integer columnIndex) {
    this.columnIndex = columnIndex;
    return this;
  }

  /**
   * @return the rowIndex
   */
  public Integer getRowIndex() {
    return rowIndex;
  }

  /**
   * @param rowIndex the rowIndex to set
   */
  public WidgetDto setRowIndex(Integer rowIndex) {
    this.rowIndex = rowIndex;
    return this;
  }

  /**
   * @return the configured
   */
  public boolean getConfigured() {
    return configured;
  }

  /**
   * @param configured the configured to set
   */
  public WidgetDto setConfigured(boolean configured) {
    this.configured = configured;
    return this;
  }

  /**
   * @return the createdAt
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * @param createdAt the createdAt to set
   */
  public WidgetDto setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * @return the updatedAt
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * @param updatedAt the updatedAt to set
   */
  public WidgetDto setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * @return the widgetProperties
   */
  public Collection<WidgetPropertyDto> getWidgetProperties() {
    return widgetPropertyDtos;
  }

  /**
   * @param widgetPropertyDto the widgetProperty to set
   */
  public WidgetDto addWidgetProperty(WidgetPropertyDto widgetPropertyDto) {
    widgetPropertyDtos.add(widgetPropertyDto);
    return this;
  }

  /**
   * @return the resourceId
   * @since 3.1
   */
  public Integer getResourceId() {
    return resourceId;
  }

  /**
   * @param resourceId the resourceId to set
   * @since 3.1
   */
  public WidgetDto setResourceId(Integer resourceId) {
    this.resourceId = resourceId;
    return this;
  }
}
