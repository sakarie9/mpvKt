package live.mehiz.mpvkt.ui.player.controls.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.vivvvek.seeker.Seeker
import dev.vivvvek.seeker.SeekerDefaults
import dev.vivvvek.seeker.Segment
import `is`.xyz.mpv.Utils
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import live.mehiz.mpvkt.ui.theme.spacing

@Composable
fun SeekbarWithTimers(
  position: Float,
  duration: Float,
  readAheadValue: Float,
  onValueChange: (Float) -> Unit,
  onValueChangeFinished: () -> Unit,
  timersInverted: Pair<Boolean, Boolean>,
  positionTimerOnClick: () -> Unit,
  durationTimerOnCLick: () -> Unit,
  modifier: Modifier = Modifier,
  chapters: ImmutableList<Segment>? = null,
) {
  Row(
    modifier = modifier.height(48.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
  ) {
    VideoTimer(
      value = position,
      timersInverted.first,
      onClick = positionTimerOnClick,
      modifier = Modifier.width(92.dp),
    )
    Seeker(
      value = position,
      range = 0f..duration,
      onValueChange = onValueChange,
      onValueChangeFinished = onValueChangeFinished,
      readAheadValue = readAheadValue,
      segments = chapters ?: persistentListOf(),
      modifier = Modifier.weight(1f),
      colors = SeekerDefaults.seekerColors(
        progressColor = MaterialTheme.colorScheme.primary,
        thumbColor = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.background,
        readAheadColor = MaterialTheme.colorScheme.inversePrimary,
      ),
    )
    VideoTimer(
      value = if (timersInverted.second) position - duration else duration,
      isInverted = timersInverted.second,
      onClick = durationTimerOnCLick,
      modifier = Modifier.width(92.dp),
    )
  }
}

@Composable
fun VideoTimer(
  value: Float,
  isInverted: Boolean,
  modifier: Modifier = Modifier,
  onClick: () -> Unit = {},
) {
  val interactionSource = remember { MutableInteractionSource() }
  Text(
    modifier = modifier
      .fillMaxHeight()
      .clickable(
        interactionSource = interactionSource,
        indication = rememberRipple(),
        onClick = onClick,
      )
      .wrapContentHeight(Alignment.CenterVertically),
    text = Utils.prettyTime(value.toInt(), isInverted),
    color = Color.White,
    textAlign = TextAlign.Center,
  )
}

@Preview
@Composable
private fun PreviewSeekBar() {
  SeekbarWithTimers(
    5f,
    20f,
    4f,
    {},
    {},
    Pair(false, true),
    {},
    {},
  )
}
