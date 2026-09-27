package com.bestmusic.data.local;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0018\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00032\u0006\u0010\u0007\u001a\u00020\bH\'J\u0016\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ&\u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u001e\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u001bJ\u001c\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00040\u00032\u0006\u0010\u0019\u001a\u00020\bH\'J\u0016\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00160\u00042\u0006\u0010\u0019\u001a\u00020\bH\'J\u0016\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0019\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u001e\u0010 \u001a\u00020!2\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u001b\u00a8\u0006\""}, d2 = {"Lcom/bestmusic/data/local/PlaylistDao;", "", "getAllPlaylists", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/bestmusic/data/local/Playlist;", "getPlaylist", "id", "", "insertPlaylist", "playlist", "(Lcom/bestmusic/data/local/Playlist;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePlaylist", "", "name", "", "updatedAt", "(JLjava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deletePlaylist", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addTrackToPlaylist", "playlistTrack", "Lcom/bestmusic/data/local/PlaylistTrack;", "(Lcom/bestmusic/data/local/PlaylistTrack;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeTrackFromPlaylist", "playlistId", "trackId", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlaylistTracks", "getPlaylistTracksSync", "getTrackCount", "", "isTrackInPlaylist", "", "app_debug"})
@androidx.room.Dao()
public abstract interface PlaylistDao {
    
    @androidx.room.Query(value = "SELECT * FROM playlists ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bestmusic.data.local.Playlist>> getAllPlaylists();
    
    @androidx.room.Query(value = "SELECT * FROM playlists WHERE id = :id")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bestmusic.data.local.Playlist> getPlaylist(long id);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPlaylist(@org.jetbrains.annotations.NotNull()
    com.bestmusic.data.local.Playlist playlist, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "UPDATE playlists SET name = :name, updatedAt = :updatedAt WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updatePlaylist(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, long updatedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM playlists WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deletePlaylist(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addTrackToPlaylist(@org.jetbrains.annotations.NotNull()
    com.bestmusic.data.local.PlaylistTrack playlistTrack, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM playlist_tracks WHERE playlistId = :playlistId AND trackId = :trackId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object removeTrackFromPlaylist(long playlistId, @org.jetbrains.annotations.NotNull()
    java.lang.String trackId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM playlist_tracks WHERE playlistId = :playlistId ORDER BY addedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bestmusic.data.local.PlaylistTrack>> getPlaylistTracks(long playlistId);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM playlist_tracks WHERE playlistId = :playlistId ORDER BY addedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.bestmusic.data.local.PlaylistTrack> getPlaylistTracksSync(long playlistId);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM playlist_tracks WHERE playlistId = :playlistId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTrackCount(long playlistId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT EXISTS(SELECT 1 FROM playlist_tracks WHERE playlistId = :playlistId AND trackId = :trackId)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object isTrackInPlaylist(long playlistId, @org.jetbrains.annotations.NotNull()
    java.lang.String trackId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
}