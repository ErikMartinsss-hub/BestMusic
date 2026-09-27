package com.bestmusic.data.local;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class PlaylistDao_Impl implements PlaylistDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Playlist> __insertAdapterOfPlaylist;

  private final EntityInsertAdapter<PlaylistTrack> __insertAdapterOfPlaylistTrack;

  public PlaylistDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfPlaylist = new EntityInsertAdapter<Playlist>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `playlists` (`id`,`name`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final Playlist entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getName());
        }
        statement.bindLong(3, entity.getCreatedAt());
        statement.bindLong(4, entity.getUpdatedAt());
      }
    };
    this.__insertAdapterOfPlaylistTrack = new EntityInsertAdapter<PlaylistTrack>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `playlist_tracks` (`playlistId`,`trackId`,`addedAt`,`title`,`artist`,`thumbnail`,`durationMs`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final PlaylistTrack entity) {
        statement.bindLong(1, entity.getPlaylistId());
        if (entity.getTrackId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTrackId());
        }
        statement.bindLong(3, entity.getAddedAt());
        if (entity.getTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getTitle());
        }
        if (entity.getArtist() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getArtist());
        }
        if (entity.getThumbnail() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getThumbnail());
        }
        statement.bindLong(7, entity.getDurationMs());
      }
    };
  }

  @Override
  public Object insertPlaylist(final Playlist playlist,
      final Continuation<? super Long> $completion) {
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfPlaylist.insertAndReturnId(_connection, playlist);
    }, $completion);
  }

  @Override
  public Object addTrackToPlaylist(final PlaylistTrack playlistTrack,
      final Continuation<? super Unit> $completion) {
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfPlaylistTrack.insert(_connection, playlistTrack);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Flow<List<Playlist>> getAllPlaylists() {
    final String _sql = "SELECT * FROM playlists ORDER BY updatedAt DESC";
    return FlowUtil.createFlow(__db, false, new String[] {"playlists"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _cursorIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _cursorIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _cursorIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _cursorIndexOfUpdatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "updatedAt");
        final List<Playlist> _result = new ArrayList<Playlist>();
        while (_stmt.step()) {
          final Playlist _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_cursorIndexOfId);
          final String _tmpName;
          if (_stmt.isNull(_cursorIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_cursorIndexOfName);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_cursorIndexOfCreatedAt);
          final long _tmpUpdatedAt;
          _tmpUpdatedAt = _stmt.getLong(_cursorIndexOfUpdatedAt);
          _item = new Playlist(_tmpId,_tmpName,_tmpCreatedAt,_tmpUpdatedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<Playlist> getPlaylist(final long id) {
    final String _sql = "SELECT * FROM playlists WHERE id = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"playlists"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _cursorIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _cursorIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _cursorIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _cursorIndexOfUpdatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "updatedAt");
        final Playlist _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_cursorIndexOfId);
          final String _tmpName;
          if (_stmt.isNull(_cursorIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_cursorIndexOfName);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_cursorIndexOfCreatedAt);
          final long _tmpUpdatedAt;
          _tmpUpdatedAt = _stmt.getLong(_cursorIndexOfUpdatedAt);
          _result = new Playlist(_tmpId,_tmpName,_tmpCreatedAt,_tmpUpdatedAt);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<PlaylistTrack>> getPlaylistTracks(final long playlistId) {
    final String _sql = "SELECT * FROM playlist_tracks WHERE playlistId = ? ORDER BY addedAt DESC";
    return FlowUtil.createFlow(__db, false, new String[] {"playlist_tracks"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, playlistId);
        final int _cursorIndexOfPlaylistId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "playlistId");
        final int _cursorIndexOfTrackId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "trackId");
        final int _cursorIndexOfAddedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "addedAt");
        final int _cursorIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _cursorIndexOfArtist = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "artist");
        final int _cursorIndexOfThumbnail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "thumbnail");
        final int _cursorIndexOfDurationMs = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "durationMs");
        final List<PlaylistTrack> _result = new ArrayList<PlaylistTrack>();
        while (_stmt.step()) {
          final PlaylistTrack _item;
          final long _tmpPlaylistId;
          _tmpPlaylistId = _stmt.getLong(_cursorIndexOfPlaylistId);
          final String _tmpTrackId;
          if (_stmt.isNull(_cursorIndexOfTrackId)) {
            _tmpTrackId = null;
          } else {
            _tmpTrackId = _stmt.getText(_cursorIndexOfTrackId);
          }
          final long _tmpAddedAt;
          _tmpAddedAt = _stmt.getLong(_cursorIndexOfAddedAt);
          final String _tmpTitle;
          if (_stmt.isNull(_cursorIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_cursorIndexOfTitle);
          }
          final String _tmpArtist;
          if (_stmt.isNull(_cursorIndexOfArtist)) {
            _tmpArtist = null;
          } else {
            _tmpArtist = _stmt.getText(_cursorIndexOfArtist);
          }
          final String _tmpThumbnail;
          if (_stmt.isNull(_cursorIndexOfThumbnail)) {
            _tmpThumbnail = null;
          } else {
            _tmpThumbnail = _stmt.getText(_cursorIndexOfThumbnail);
          }
          final long _tmpDurationMs;
          _tmpDurationMs = _stmt.getLong(_cursorIndexOfDurationMs);
          _item = new PlaylistTrack(_tmpPlaylistId,_tmpTrackId,_tmpAddedAt,_tmpTitle,_tmpArtist,_tmpThumbnail,_tmpDurationMs);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public List<PlaylistTrack> getPlaylistTracksSync(final long playlistId) {
    final String _sql = "SELECT * FROM playlist_tracks WHERE playlistId = ? ORDER BY addedAt DESC";
    return DBUtil.performBlocking(__db, true, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, playlistId);
        final int _cursorIndexOfPlaylistId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "playlistId");
        final int _cursorIndexOfTrackId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "trackId");
        final int _cursorIndexOfAddedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "addedAt");
        final int _cursorIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _cursorIndexOfArtist = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "artist");
        final int _cursorIndexOfThumbnail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "thumbnail");
        final int _cursorIndexOfDurationMs = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "durationMs");
        final List<PlaylistTrack> _result = new ArrayList<PlaylistTrack>();
        while (_stmt.step()) {
          final PlaylistTrack _item;
          final long _tmpPlaylistId;
          _tmpPlaylistId = _stmt.getLong(_cursorIndexOfPlaylistId);
          final String _tmpTrackId;
          if (_stmt.isNull(_cursorIndexOfTrackId)) {
            _tmpTrackId = null;
          } else {
            _tmpTrackId = _stmt.getText(_cursorIndexOfTrackId);
          }
          final long _tmpAddedAt;
          _tmpAddedAt = _stmt.getLong(_cursorIndexOfAddedAt);
          final String _tmpTitle;
          if (_stmt.isNull(_cursorIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_cursorIndexOfTitle);
          }
          final String _tmpArtist;
          if (_stmt.isNull(_cursorIndexOfArtist)) {
            _tmpArtist = null;
          } else {
            _tmpArtist = _stmt.getText(_cursorIndexOfArtist);
          }
          final String _tmpThumbnail;
          if (_stmt.isNull(_cursorIndexOfThumbnail)) {
            _tmpThumbnail = null;
          } else {
            _tmpThumbnail = _stmt.getText(_cursorIndexOfThumbnail);
          }
          final long _tmpDurationMs;
          _tmpDurationMs = _stmt.getLong(_cursorIndexOfDurationMs);
          _item = new PlaylistTrack(_tmpPlaylistId,_tmpTrackId,_tmpAddedAt,_tmpTitle,_tmpArtist,_tmpThumbnail,_tmpDurationMs);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getTrackCount(final long playlistId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM playlist_tracks WHERE playlistId = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, playlistId);
        final Integer _result;
        if (_stmt.step()) {
          final Integer _tmp;
          if (_stmt.isNull(0)) {
            _tmp = null;
          } else {
            _tmp = (int) (_stmt.getLong(0));
          }
          _result = _tmp;
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object isTrackInPlaylist(final long playlistId, final String trackId,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM playlist_tracks WHERE playlistId = ? AND trackId = ?)";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, playlistId);
        _argIndex = 2;
        if (trackId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, trackId);
        }
        final Boolean _result;
        if (_stmt.step()) {
          final Integer _tmp;
          if (_stmt.isNull(0)) {
            _tmp = null;
          } else {
            _tmp = (int) (_stmt.getLong(0));
          }
          _result = _tmp == null ? null : _tmp != 0;
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updatePlaylist(final long id, final String name, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE playlists SET name = ?, updatedAt = ? WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (name == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, name);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, id);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object deletePlaylist(final long id, final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM playlists WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object removeTrackFromPlaylist(final long playlistId, final String trackId,
      final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM playlist_tracks WHERE playlistId = ? AND trackId = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, playlistId);
        _argIndex = 2;
        if (trackId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, trackId);
        }
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
