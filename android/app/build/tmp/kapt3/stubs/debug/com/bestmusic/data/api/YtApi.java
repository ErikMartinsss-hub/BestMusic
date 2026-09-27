package com.bestmusic.data.api;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006\u000b"}, d2 = {"Lcom/bestmusic/data/api/YtApi;", "", "search", "", "Lcom/bestmusic/data/model/Track;", "query", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "stream", "Lcom/bestmusic/data/model/StreamInfo;", "videoId", "app_debug"})
public abstract interface YtApi {
    
    @retrofit2.http.GET(value = "api/search")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object search(@retrofit2.http.Query(value = "q")
    @org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.bestmusic.data.model.Track>> $completion);
    
    @retrofit2.http.GET(value = "api/stream/{video_id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object stream(@retrofit2.http.Path(value = "video_id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String videoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bestmusic.data.model.StreamInfo> $completion);
}