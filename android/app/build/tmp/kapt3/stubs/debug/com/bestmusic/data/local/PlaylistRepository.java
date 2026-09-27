package com.bestmusic.data.local;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0012\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000bJ\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u000b2\u0006\u0010\u000f\u001a\u00020\u0010J\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\f0\u000b2\u0006\u0010\u0013\u001a\u00020\u0010J\u001a\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\f0\u000b2\u0006\u0010\u0013\u001a\u00020\u0010J\u0016\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u001e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u001e\u0010 \u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010!\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\"J\u001e\u0010#\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010$\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u001e\u0010%\u001a\u00020&2\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010$\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010\'\u001a\u00020(2\u0006\u0010\u0013\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u001fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lcom/bestmusic/data/local/PlaylistRepository;", "", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "db", "Lcom/bestmusic/data/local/AppDatabase;", "dao", "Lcom/bestmusic/data/local/PlaylistDao;", "getAllPlaylists", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/bestmusic/data/local/Playlist;", "getPlaylist", "id", "", "getPlaylistTracks", "Lcom/bestmusic/data/local/PlaylistTrack;", "playlistId", "getPlaylistTracksAsTracks", "Lcom/bestmusic/data/model/Track;", "createPlaylist", "name", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "renamePlaylist", "", "newName", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deletePlaylist", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addTrackToPlaylist", "track", "(JLcom/bestmusic/data/model/Track;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeTrackFromPlaylist", "trackId", "isTrackInPlaylist", "", "getTrackCount", "", "app_debug"})
public final class PlaylistRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bestmusic.data.local.AppDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bestmusic.data.local.PlaylistDao dao = null;
    
    public PlaylistRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bestmusic.data.local.Playlist>> getAllPlaylists() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bestmusic.data.local.Playlist> getPlaylist(long id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bestmusic.data.local.PlaylistTrack>> getPlaylistTracks(long playlistId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bestmusic.data.model.Track>> getPlaylistTracksAsTracks(long playlistId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createPlaylist(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object renamePlaylist(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String newName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deletePlaylist(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addTrackToPlaylist(long playlistId, @org.jetbrains.annotations.NotNull()
    com.bestmusic.data.model.Track track, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeTrackFromPlaylist(long playlistId, @org.jetbrains.annotations.NotNull()
    java.lang.String trackId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object isTrackInPlaylist(long playlistId, @org.jetbrains.annotations.NotNull()
    java.lang.String trackId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getTrackCount(long playlistId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
}