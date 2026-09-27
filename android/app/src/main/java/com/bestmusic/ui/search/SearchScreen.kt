package com.bestmusic.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bestmusic.Settings
import com.bestmusic.data.model.Track
import com.bestmusic.ui.MainViewModel
import com.bestmusic.ui.PlaylistViewModel
import com.bestmusic.util.formatDuration

@Composable
fun SearchScreen(
    viewModel: MainViewModel,
    playlistViewModel: PlaylistViewModel,
    onOpenPlayer: () -> Unit,
    onOpenPlaylists: () -> Unit,
) {
    val query by viewModel.query.collectAsState()
    val results by viewModel.results.collectAsState()
    val searching by viewModel.searching.collectAsState()
    val error by viewModel.error.collectAsState()
    val loadingId by viewModel.loadingId.collectAsState()
    var showSettings by rememberSaveable { mutableStateOf(false) }

    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = viewModel::onQueryChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Buscar música...") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { viewModel.search() }),
            )
            IconButton(onClick = viewModel::search) {
                Icon(Icons.Filled.MusicNote, contentDescription = "Buscar")
            }
            IconButton(onClick = { showSettings = true }) {
                Icon(Icons.Filled.Settings, contentDescription = "Configurações")
            }
            IconButton(onClick = onOpenPlaylists) {
                Icon(Icons.Filled.LibraryMusic, contentDescription = "Playlists")
            }
        }

        when {
            searching -> Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator()
            }

            error != null -> Column(
                Modifier.fillMaxSize().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(error!!, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
                TextButton(onClick = viewModel::search) { Text("Tentar novamente") }
                TextButton(onClick = { showSettings = true }) { Text("Configurar servidor") }
            }

            results.isEmpty() -> Column(
                Modifier.fillMaxSize().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text("Pesquise uma música, artista ou álbum.", color = MaterialTheme.colorScheme.onSurfaceVariant)

                // Brazilian playlists / quick searches
                Text("🇧🇷 Top Brasil", style = MaterialTheme.typography.titleMedium, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                QuickSearchChips(onSearchClick = { q ->
                    viewModel.onQueryChange(q)
                    viewModel.search()
                })

                Text("🎵 Por Gênero", style = MaterialTheme.typography.titleMedium, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 16.dp))
                GenreChips(onSearchClick = { q ->
                    viewModel.onQueryChange(q)
                    viewModel.search()
                })
            }

            else -> LazyColumn(
                Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 4.dp),
            ) {
                items(results, key = { it.id }) { track ->
                    TrackRow(
                        track = track,
                        loading = loadingId == track.id,
                        onClick = { viewModel.play(track) },
                    )
                }
            }
        }
    }

    if (showSettings) {
        ServerUrlDialog(
            onDismiss = { showSettings = false },
            onSave = { url ->
                viewModel.setServerUrl(url)
                showSettings = false
            },
        )
    }
}

@Composable
private fun TrackRow(
    track: Track,
    loading: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = track.thumbnail,
            contentDescription = null,
            modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                track.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                track.artist ?: "Desconhecido",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(Modifier.width(8.dp))
        if (loading) {
            CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
        } else {
            Text(
                formatDuration(track.durationMs),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun ServerUrlDialog(
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
) {
    val context = LocalContext.current
    var url by remember { mutableStateOf(Settings.baseUrl(context)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Servidor") },
        text = {
            Column {
                Text(
                    "URL do servidor BestMusic (que roda o server/main.py com yt-dlp). " +
                        "No emulador use http://10.0.2.2:8000. Em aparelho físico use o IP da sua máquina.",
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    singleLine = true,
                    label = { Text("Base URL") },
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onSave(url) }) { Text("Salvar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        },
    )
}

@Composable
private fun QuickSearchChips(onSearchClick: (String) -> Unit) {
    val searches = listOf(
        "Mais tocadas no Brasil",
        "Top 50 Brasil",
        "Viral Brasil",
        "Lançamentos Brasil",
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(searches) { search ->
            FilterChip(
                selected = false,
                onClick = { onSearchClick(search) },
                label = { Text(search, style = MaterialTheme.typography.labelLarge) },
                modifier = Modifier.height(36.dp),
                colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    }
}

@Composable
private fun GenreChips(onSearchClick: (String) -> Unit) {
    val genres = listOf(
        "Sertanejo 2024",
        "Funk 2024",
        "Pop Brasil",
        "Rock Brasil",
        "Pagode",
        "Forró",
        "Trap Brasil",
        "Lo-fi Brasil",
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(genres) { genre ->
            FilterChip(
                selected = false,
                onClick = { onSearchClick(genre) },
                label = { Text(genre, style = MaterialTheme.typography.labelLarge) },
                modifier = Modifier.height(36.dp),
                colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    }
}