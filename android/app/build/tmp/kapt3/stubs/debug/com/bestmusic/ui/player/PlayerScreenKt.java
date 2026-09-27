package com.bestmusic.ui.player;

@kotlin.Metadata(mv = {2, 1, 0}, k = 2, xi = 48, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0004\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a$\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\nH\u0003\u001a,\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u00a8\u0006\u000f"}, d2 = {"PlayerScreen", "", "state", "Lcom/bestmusic/player/PlaybackUiState;", "player", "Lcom/bestmusic/player/PlayerManager;", "onBack", "Lkotlin/Function0;", "SeekBar", "onSeek", "Lkotlin/Function1;", "", "MiniPlayerBar", "onClick", "onToggle", "app_debug"})
public final class PlayerScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void PlayerScreen(@org.jetbrains.annotations.NotNull()
    com.bestmusic.player.PlaybackUiState state, @org.jetbrains.annotations.NotNull()
    com.bestmusic.player.PlayerManager player, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SeekBar(com.bestmusic.player.PlaybackUiState state, kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onSeek) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void MiniPlayerBar(@org.jetbrains.annotations.NotNull()
    com.bestmusic.player.PlaybackUiState state, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggle) {
    }
}