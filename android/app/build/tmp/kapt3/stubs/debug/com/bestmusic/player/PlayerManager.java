package com.bestmusic.player;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\u0016\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u0016J\r\u0010\u001d\u001a\u0004\u0018\u00010\u0016\u00a2\u0006\u0002\u0010\u001eJ\r\u0010\u001f\u001a\u0004\u0018\u00010\u0016\u00a2\u0006\u0002\u0010\u001eJ\u0015\u0010 \u001a\u0004\u0018\u00010\u00162\u0006\u0010!\u001a\u00020\"\u00a2\u0006\u0002\u0010#J\u0006\u0010$\u001a\u00020\u0016J\u0006\u0010%\u001a\u00020\u0016J\u0018\u0010&\u001a\u00020\u00162\u0006\u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020\u0016H\u0002J\b\u0010,\u001a\u00020\u0016H\u0002J\b\u0010-\u001a\u00020\u0016H\u0002J\u0018\u0010.\u001a\u00020/2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002R\u0016\u0010\u0006\u001a\n \u0007*\u0004\u0018\u00010\u00030\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u00060"}, d2 = {"Lcom/bestmusic/player/PlayerManager;", "Landroidx/media3/common/Player$Listener;", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "appContext", "kotlin.jvm.PlatformType", "scope", "Lkotlinx/coroutines/CoroutineScope;", "controller", "Landroidx/media3/session/MediaController;", "ticker", "Lkotlinx/coroutines/Job;", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/bestmusic/player/PlaybackUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "connect", "", "play", "track", "Lcom/bestmusic/data/model/Track;", "streamUrl", "", "toggle", "next", "()Lkotlin/Unit;", "previous", "seekTo", "positionMs", "", "(J)Lkotlin/Unit;", "toggleShuffle", "cycleRepeat", "onEvents", "player", "Landroidx/media3/common/Player;", "events", "Landroidx/media3/common/Player$Events;", "startTicker", "stopTicker", "publish", "buildMediaItem", "Landroidx/media3/common/MediaItem;", "app_debug"})
public final class PlayerManager implements androidx.media3.common.Player.Listener {
    private final android.content.Context appContext = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.Nullable()
    private androidx.media3.session.MediaController controller;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job ticker;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bestmusic.player.PlaybackUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bestmusic.player.PlaybackUiState> state = null;
    
    public PlayerManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bestmusic.player.PlaybackUiState> getState() {
        return null;
    }
    
    private final void connect() {
    }
    
    public final void play(@org.jetbrains.annotations.NotNull()
    com.bestmusic.data.model.Track track, @org.jetbrains.annotations.NotNull()
    java.lang.String streamUrl) {
    }
    
    public final void toggle() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final kotlin.Unit next() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final kotlin.Unit previous() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final kotlin.Unit seekTo(long positionMs) {
        return null;
    }
    
    public final void toggleShuffle() {
    }
    
    public final void cycleRepeat() {
    }
    
    @java.lang.Override()
    public void onEvents(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.Player player, @org.jetbrains.annotations.NotNull()
    androidx.media3.common.Player.Events events) {
    }
    
    private final void startTicker() {
    }
    
    private final void stopTicker() {
    }
    
    private final void publish() {
    }
    
    private final androidx.media3.common.MediaItem buildMediaItem(com.bestmusic.data.model.Track track, java.lang.String streamUrl) {
        return null;
    }
}