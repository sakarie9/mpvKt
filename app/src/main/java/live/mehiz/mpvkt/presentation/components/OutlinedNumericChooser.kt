package live.mehiz.mpvkt.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import live.mehiz.mpvkt.R
import live.mehiz.mpvkt.ui.theme.spacing

@Composable
fun OutlinedNumericChooser(
  value: Int,
  onChange: (Int) -> Unit,
  max: Int,
  step: Int,
  modifier: Modifier = Modifier,
  min: Int = 0,
  suffix: (@Composable () -> Unit)? = null,
  label: (@Composable () -> Unit)? = null,
) {
  assert(max > min) { "min can't be larger than max ($min > $max)" }
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.smaller),
  ) {
    RepeatingIconButton(onClick = { onChange(value - step) }) {
      Icon(Icons.Filled.RemoveCircle, null)
    }
    var valueString by remember { mutableStateOf("$value") }
    LaunchedEffect(value) {
      if (valueString.isBlank()) return@LaunchedEffect
      valueString = value.toString()
    }
    OutlinedTextField(
      label = label,
      value = valueString,
      onValueChange = { newValue ->
        if (newValue.isBlank()) {
          valueString = newValue
          onChange(0)
        }
        runCatching {
          val intValue = newValue.toInt()
          onChange(intValue)
          valueString = newValue
        }
      },
      isError = value > max || value < min,
      supportingText = {
        if (value > max) Text(stringResource(R.string.numeric_chooser_value_too_big))
        if (value < min) Text(stringResource(R.string.numeric_chooser_value_too_small))
      },
      suffix = suffix,
      modifier = Modifier.weight(1f),
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    RepeatingIconButton(onClick = { onChange(value + step) }) {
      Icon(Icons.Filled.AddCircle, null)
    }
  }
}

@Composable
fun OutlinedNumericChooser(
  value: Float,
  onChange: (Float) -> Unit,
  max: Float,
  step: Float,
  modifier: Modifier = Modifier,
  min: Float = 0f,
  suffix: (@Composable () -> Unit)? = null,
  label: (@Composable () -> Unit)? = null,
) {
  assert(max > min) { "min can't be larger than max ($min > $max)" }
  Row(
    modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    RepeatingIconButton(onClick = { onChange(value - step) }) {
      Icon(Icons.Filled.RemoveCircle, null)
    }
    var valueString by remember { mutableStateOf("$value") }
    LaunchedEffect(value) {
      if (valueString.isBlank()) return@LaunchedEffect
      valueString = value.toString().dropLastWhile { it == '0' }.dropLastWhile { it == '.' }
    }
    OutlinedTextField(
      value = valueString,
      label = label,
      onValueChange = { newValue ->
        if (newValue.isBlank()) {
          valueString = newValue
          onChange(0f)
        }
        runCatching {
          if (newValue.startsWith('.')) return@runCatching
          val floatValue = newValue.toFloat()
          onChange(floatValue)
          valueString = newValue
        }
      },
      isError = value > max || value < min,
      supportingText = {
        if (value > max) Text(stringResource(R.string.numeric_chooser_value_too_big))
        if (value < min) Text(stringResource(R.string.numeric_chooser_value_too_small))
      },
      modifier = Modifier.weight(1f),
      suffix = suffix,
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    RepeatingIconButton(onClick = { onChange(value + step) }) {
      Icon(Icons.Filled.AddCircle, null)
    }
  }
}
