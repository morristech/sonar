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
package org.sonar.plugins.dbcleaner;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.sonar.api.utils.DateUtils;
import org.sonar.core.purge.PurgeableSnapshotDto;

public final class DbCleanerTestUtils {

  private DbCleanerTestUtils() {
  }

  public static PurgeableSnapshotDto createSnapshotWithDate(long snapshotId, String date) {
    PurgeableSnapshotDto snapshot = new PurgeableSnapshotDto();
    snapshot.setSnapshotId(snapshotId);
    snapshot.setDate(DateUtils.parseDate(date));
    return snapshot;
  }

  public static PurgeableSnapshotDto createSnapshotWithDateTime(long snapshotId, String datetime) {
      PurgeableSnapshotDto snapshot = new PurgeableSnapshotDto();
      snapshot.setSnapshotId(snapshotId);
      snapshot.setDate(DateUtils.parseDateTime(datetime));
      return snapshot;
    }

  public static final class SnapshotMatcher extends BaseMatcher<PurgeableSnapshotDto> {
    long snapshotId;

    public SnapshotMatcher(long snapshotId) {
      this.snapshotId = snapshotId;
    }

    public boolean matches(Object o) {
      return ((PurgeableSnapshotDto) o).getSnapshotId() == snapshotId;
    }

    public void describeTo(Description description) {
      description.appendText("snapshotId").appendValue(snapshotId);
    }
  }
}
