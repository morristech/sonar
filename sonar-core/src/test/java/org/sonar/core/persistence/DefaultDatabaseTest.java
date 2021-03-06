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
package org.sonar.core.persistence;

import org.apache.commons.dbcp.BasicDataSource;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.sonar.api.config.Settings;
import org.sonar.core.persistence.dialect.Oracle;
import org.sonar.core.persistence.dialect.PostgreSql;

import java.util.Properties;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class DefaultDatabaseTest {

  static {
    DerbyUtils.fixDerbyLogs();
  }

  @Test
  public void shouldLoadDefaultValues() {
    DefaultDatabase db = new DefaultDatabase(new Settings());
    db.initSettings();

    Properties props = db.getProperties();
    assertThat(props.getProperty("sonar.jdbc.username"), Is.is("sonar"));
    assertThat(props.getProperty("sonar.jdbc.password"), Is.is("sonar"));
    assertThat(props.getProperty("sonar.jdbc.url"), Is.is("jdbc:derby://localhost:1527/sonar"));
    assertThat(props.getProperty("sonar.jdbc.driverClassName"), Is.is("org.apache.derby.jdbc.ClientDriver"));
  }

  @Test
  public void shouldSupportDeprecatedProperties() {
    Settings settings = new Settings();
    settings.setProperty("sonar.jdbc.driver", "my.Driver");
    settings.setProperty("sonar.jdbc.user", "me");

    DefaultDatabase db = new DefaultDatabase(settings);
    db.initSettings();
    Properties props = db.getProperties();

    assertThat(props.getProperty("sonar.jdbc.username"), Is.is("me"));
    assertThat(props.getProperty("sonar.jdbc.driverClassName"), Is.is("my.Driver"));
  }

  @Test
  public void shouldExtractCommonsDbcpProperties() {
    Properties props = new Properties();
    props.setProperty("sonar.jdbc.driverClassName", "my.Driver");
    props.setProperty("sonar.jdbc.username", "me");
    props.setProperty("sonar.jdbc.maxActive", "5");

    Properties commonsDbcpProps = DefaultDatabase.extractCommonsDbcpProperties(props);

    assertThat(commonsDbcpProps.getProperty("username"), Is.is("me"));
    assertThat(commonsDbcpProps.getProperty("driverClassName"), Is.is("my.Driver"));
    assertThat(commonsDbcpProps.getProperty("maxActive"), Is.is("5"));
  }

  @Test
  public void shouldCompleteProperties() {
    Settings settings = new Settings();

    DefaultDatabase db = new DefaultDatabase(settings) {
      @Override
      protected void doCompleteProperties(Properties properties) {
        properties.setProperty("sonar.jdbc.maxActive", "2");
      }
    };
    db.initSettings();

    Properties props = db.getProperties();

    assertThat(props.getProperty("sonar.jdbc.maxActive"), Is.is("2"));
  }

  @Test
  public void shouldStart() {
    Settings settings = new Settings();
    settings.setProperty("sonar.jdbc.url", "jdbc:derby:memory:sonar;create=true;user=sonar;password=sonar");
    settings.setProperty("sonar.jdbc.driverClassName", "org.apache.derby.jdbc.EmbeddedDriver");
    settings.setProperty("sonar.jdbc.username", "sonar");
    settings.setProperty("sonar.jdbc.password", "sonar");
    settings.setProperty("sonar.jdbc.maxActive", "1");

    try {
      DefaultDatabase db = new DefaultDatabase(settings);
      db.start();

      assertThat(db.getDialect().getId(), Is.is("derby"));
      assertThat(((BasicDataSource) db.getDataSource()).getMaxActive(), Is.is(1));
    } finally {
      DerbyUtils.dropInMemoryDatabase();
    }
  }

  @Test
  public void shouldInitSchema() {
    Settings settings = new Settings();
    settings.setProperty("sonar.jdbc.schema", "my_schema");

    DefaultDatabase database = new DefaultDatabase(settings);
    database.initSettings();

    assertThat(database.getSchema(), Is.is("my_schema"));
  }

  @Test
  public void shouldInitPostgresqlSchemaWithDeprecatedProperty() {
    Settings settings = new Settings();
    settings.setProperty("sonar.jdbc.dialect", PostgreSql.ID);
    settings.setProperty("sonar.jdbc.postgreSearchPath", "my_schema");

    DefaultDatabase database = new DefaultDatabase(settings);
    database.initSettings();

    assertThat(database.getSchema(), Is.is("my_schema"));
  }

  @Test
  public void shouldNotInitPostgresqlSchemaByDefault() {
    Settings settings = new Settings();
    settings.setProperty("sonar.jdbc.dialect", PostgreSql.ID);

    DefaultDatabase database = new DefaultDatabase(settings);
    database.initSettings();

    assertThat(database.getSchema(), nullValue());
  }

  @Test
  public void shouldInitOracleSchemaWithDeprecatedProperty() {
    Settings settings = new Settings();
    settings.setProperty("sonar.jdbc.dialect", Oracle.ID);
    settings.setProperty("sonar.hibernate.default_schema", "my_schema");

    DefaultDatabase database = new DefaultDatabase(settings);
    database.initSettings();

    assertThat(database.getSchema(), Is.is("my_schema"));
  }

  @Test
  public void shouldNotInitOracleSchemaByDefault() {
    Settings settings = new Settings();
    settings.setProperty("sonar.jdbc.dialect", Oracle.ID);

    DefaultDatabase database = new DefaultDatabase(settings);
    database.initSettings();

    assertThat(database.getSchema(), nullValue());
  }

  @Test
  public void shouldGuessDialectFromUrl() {
    Settings settings = new Settings();
    settings.setProperty("sonar.jdbc.url", "jdbc:postgresql://localhost/sonar");

    DefaultDatabase database = new DefaultDatabase(settings);
    database.initSettings();

    assertThat(database.getDialect().getId(), Is.is(PostgreSql.ID));
  }

  @Test
  public void shouldGuessDefaultDriver() {
    Settings settings = new Settings();
    settings.setProperty("sonar.jdbc.url", "jdbc:postgresql://localhost/sonar");

    DefaultDatabase database = new DefaultDatabase(settings);
    database.initSettings();

    assertThat(database.getProperties().getProperty("sonar.jdbc.driverClassName"), Is.is("org.postgresql.Driver"));
  }
}
