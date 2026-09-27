package com.bestmusic.ui.playlist;

@kotlin.Metadata(mv = {2, 1, 0}, k = 2, xi = 48, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a6\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a:\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a,\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001aF\u0010\u0012\u001a\u00020\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\u0016\u001a\u00020\u00152\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001aN\u0010\u0018\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\u0016\u001a\u00020\u00152\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u00a8\u0006\u0019"}, d2 = {"PlaylistScreen", "", "viewModel", "Lcom/bestmusic/ui/PlaylistViewModel;", "onBack", "Lkotlin/Function0;", "onPlayTrack", "Lkotlin/Function1;", "Lcom/bestmusic/data/local/PlaylistTrack;", "PlaylistRow", "playlist", "Lcom/bestmusic/data/local/Playlist;", "onClick", "onEdit", "onDelete", "PlaylistTrackRow", "track", "onRemove", "CreatePlaylistDialog", "onDismiss", "onSave", "", "name", "onNameChange", "EditPlaylistDialog", "app_debug"})
public final class PlaylistScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void PlaylistScreen(@org.jetbrains.annotations.NotNull()
    com.bestmusic.ui.PlaylistViewModel viewModel, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.bestmusic.data.local.PlaylistTrack, kotlin.Unit> onPlayTrack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PlaylistRow(com.bestmusic.data.local.Playlist playlist, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PlaylistTrackRow(com.bestmusic.data.local.PlaylistTrack track, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CreatePlaylistDialog(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSave, java.lang.String name, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNameChange) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EditPlaylistDialog(com.bestmusic.data.local.Playlist playlist, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSave, java.lang.String name, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNameChange) {
    }
}