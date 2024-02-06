package a77777_888.me.t.shoptestapp.ui.components

import a77777_888.me.t.shoptestapp.ui.entities.PhoneVisualTransformation
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PhoneField(
    phone: String,
    modifier: Modifier = Modifier,
    mask: String = "+* *** *** ** **",
    maskNumber: Char = '*',
    isValid: Boolean,
    onPhoneChanged: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? = { Text(
        text = "Телефон",
        style = MaterialTheme.typography.bodyMedium,
    ) },
    clear: () -> Unit,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
) {
    TextField(
        value = phone,
        onValueChange = { it ->
            if (!(it.length == 2 && it[1] == '7'))
                onPhoneChanged(it.take(mask.count { it == maskNumber }))
        },
        trailingIcon = {
            if (phone.length > 1)
                IconButton(
                    onClick = clear,
                    modifier = Modifier.size(28.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
        },
        placeholder = placeholder,
        singleLine = true,
        isError = !isValid,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
        visualTransformation = PhoneVisualTransformation(mask, maskNumber),
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            errorCursorColor = if (phone.length == 1) Color.Transparent else MaterialTheme.colorScheme.error,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = if (phone.isBlank()) Color.Transparent else MaterialTheme.colorScheme.error,
        ),
    )
}