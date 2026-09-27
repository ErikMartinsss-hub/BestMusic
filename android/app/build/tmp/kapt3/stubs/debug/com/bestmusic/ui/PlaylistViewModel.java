package com.bestmusic.ui;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\u000e\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cJ\u0006\u0010\u001d\u001a\u00020\u0019J\u000e\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\u0015J\u0016\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\u0015J\u000e\u0010#\u001a\u00020\u00192\u0006\u0010!\u001a\u00020\u001cJ\u0016\u0010$\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&J\u0016\u0010\'\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020\u0015J\u001e\u0010)\u001a\u00020*2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010+R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\n0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0016\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000f\u00a8\u0006,"}, d2 = {"Lcom/bestmusic/ui/PlaylistViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "<init>", "(Landroid/app/Application;)V", "repo", "Lcom/bestmusic/data/local/PlaylistRepository;", "_playlists", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/bestmusic/data/local/Playlist;", "playlists", "Lkotlinx/coroutines/flow/StateFlow;", "getPlaylists", "()Lkotlinx/coroutines/flow/StateFlow;", "_selectedPlaylistTracks", "Lcom/bestmusic/data/local/PlaylistTrack;", "selectedPlaylistTracks", "getSelectedPlaylistTracks", "_error", "", "error", "getError", "loadPlaylists", "", "selectPlaylist", "playlistId", "", "clearSelectedPlaylist", "createPlaylist", "name", "renamePlaylist", "id", "newName", "deletePlaylist", "addTrackToPlaylist", "track", "Lcom/bestmusic/data/model/Track;", "removeTrackFromPlaylist", "trackId", "isTrackInPlaylist", "", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class PlaylistViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bestmusic.data.local.PlaylistRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.bestmusic.data.local.Playlist>> _playlists = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bestmusic.data.local.Playlist>> playlists = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.bestmusic.data.local.PlaylistTrack>> _selectedPlaylistTracks = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bestmusic.data.local.PlaylistTrack>> selectedPlaylistTracks = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _error = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> error = null;
    
    public PlaylistViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bestmusic.data.local.Playlist>> getPlaylists() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bestmusic.data.local.PlaylistTrack>> getSelectedPlaylistTracks() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getError() {
        return null;
    }
    
    private final void loadPlaylists() {
    }
    
    public final void selectPlaylist(long playlistId) {
    }
    
    public final void clearSelectedPlaylist() {
    }
    
    public final void createPlaylist(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void renamePlaylist(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String newName) {
    }
    
    public final void deletePlaylist(long id) {
    }
    
    public final void addTrackToPlaylist(long playlistId, @org.jetbrains.annotations.NotNull()
    com.bestmusic.data.model.Track track) {
    }
    
    public final void removeTrackFromPlaylist(long playlistId, @org.jetbrains.annotations.NotNull()
    java.lang.String trackId) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object isTrackInPlaylist(long playlistId, @org.jetbrains.annotations.NotNull()
    java.lang.String trackId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}