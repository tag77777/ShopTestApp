package a77777_888.me.t.shoptestapp.ui.components

import a77777_888.me.t.shoptestapp.R
import a77777_888.me.t.shoptestapp.ui.entities.Validator
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun AccountTextField(
    value: String,
    onValueChange: (String) -> Unit,
    clean: () -> Unit,
    placeholderLabel: String
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        onValueChange = onValueChange,
        trailingIcon = {
            if (value.isNotBlank())
                IconButton(
                    onClick = clean,
                    modifier = Modifier.size(28.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
        },
        placeholder = {
            Text(
                text = placeholderLabel,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        singleLine = true,
        isError = !Validator.validateName(value),
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = if (value.isBlank()) Color.Transparent else MaterialTheme.colorScheme.error,
        ),
        supportingText = {
            if (value.isNotBlank() && !Validator.validateName(value))
                Text(
                    text =  stringResource(R.string.cyrillic_only),
                    color = MaterialTheme.colorScheme.error
                )

        }

    )
}