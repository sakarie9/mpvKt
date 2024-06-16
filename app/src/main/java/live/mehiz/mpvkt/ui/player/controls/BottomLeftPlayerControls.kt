package live.mehiz.mpvkt.ui.player.controls

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.update
import live.mehiz.mpvkt.preferences.PlayerPreferences
import live.mehiz.mpvkt.ui.player.PlayerViewModel
import live.mehiz.mpvkt.ui.player.Sheets
import live.mehiz.mpvkt.ui.player.controls.components.ControlsButton
import live.mehiz.mpvkt.ui.player.controls.components.CurrentChapter
import org.koin.compose.koinInject

@Composable
fun BottomLeftPlayerControls(viewModel: PlayerViewModel) {
  val playerPreferences = koinInject<PlayerPreferences>()
  val currentChapter by viewModel.currentChapter.collectAsState()
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    ControlsButton(
      Icons.Default.Lock,
      onClick = { viewModel.lockControls() },
    )
    AnimatedVisibility(
      currentChapter != null && playerPreferences.currentChaptersIndicator.get(),
      enter = fadeIn(),
      exit = fadeOut(),
    ) {
      CurrentChapter(
        currentChapter!!,
        onClick = { viewModel.sheetShown.update { Sheets.Chapters } },
      )
    }
  }
}
