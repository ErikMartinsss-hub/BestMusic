package com.bestmusic.ui.search;

@kotlin.Metadata(mv = {2, 1, 0}, k = 2, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a4\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a&\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a*\u0010\u000f\u001a\u00020\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\u0012H\u0003\u001a\u001c\u0010\u0014\u001a\u00020\u00012\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\u0012H\u0003\u001a\u001c\u0010\u0016\u001a\u00020\u00012\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\u0012H\u0003\u00a8\u0006\u0017"}, d2 = {"SearchScreen", "", "viewModel", "Lcom/bestmusic/ui/MainViewModel;", "playlistViewModel", "Lcom/bestmusic/ui/PlaylistViewModel;", "onOpenPlayer", "Lkotlin/Function0;", "onOpenPlaylists", "TrackRow", "track", "Lcom/bestmusic/data/model/Track;", "loading", "", "onClick", "ServerUrlDialog", "onDismiss", "onSave", "Lkotlin/Function1;", "", "QuickSearchChips", "onSearchClick", "GenreChips", "app_debug"})
public final class SearchScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void SearchScreen(@org.jetbrains.annotations.NotNull()
    com.bestmusic.ui.MainViewModel viewModel, @org.jetbrains.annotations.NotNull()
    com.bestmusic.ui.PlaylistViewModel playlistViewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenPlayer, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenPlaylists) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TrackRow(com.bestmusic.data.model.Track track, boolean loading, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ServerUrlDialog(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSave) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void QuickSearchChips(kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSearchClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void GenreChips(kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSearchClick) {
    }
}