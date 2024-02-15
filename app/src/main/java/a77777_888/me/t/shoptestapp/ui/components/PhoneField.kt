package a77777_888.me.t.shoptestapp.ui.components

import android.util.Log
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.text.isDigitsOnly

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PhoneField(
    phone: String,
    onPhoneChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    mask: String = "+7 ... ... .. ..",
    maskChar: Char = '.',
    maskColor: Color = MaterialTheme.colorScheme.error,
    isValid: Boolean = true,
    placeholder: @Composable (() -> Unit)? = {},
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
) {
    var focused by remember { mutableStateOf(false) }

    TextField(
        value = phone,
        onValueChange = {
            if (it.isInputPhoneValid()) {
                onPhoneChanged(it)
            }
        },
        modifier = modifier.onFocusChanged {
                                focused = it.hasFocus
            Log.e("TAG", "PhoneField_V0: focused = $focused    phone = $phone")
                            },
        isError = !isValid || phone.length != 10,
        placeholder = placeholder,
        trailingIcon = trailingIcon,
        colors = colors,
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
        visualTransformation = PhoneVisualTransformation(mask, maskChar, maskColor, focused)

    )

}

fun String.isPhoneValid() = length == 10

private fun String.isInputPhoneValid(): Boolean =
    isDigitsOnly() &&
            length <= 10 &&
            !(length == 1 && this[0] == '7')


class PhoneVisualTransformation(
    val mask: String,
    val maskChar: Char,
    private val maskColor: Color,
    private val focused: Boolean
) : VisualTransformation {

    private val maxLength = mask.count { it == maskChar }

    override fun filter(text: AnnotatedString): TransformedText {

        if (text.isEmpty())
            return if (focused) TransformedText(AnnotatedString(mask), PhoneOffsetMapper(mask, maskChar,""))
                   else TransformedText(AnnotatedString(""), OffsetMapping.Identity)

        val trimmedText = if (text.length > maxLength) text.take(maxLength) else text
        var maskIndex = 0

        val annotatedString = buildAnnotatedString {
            var textIndex = 0

            while (textIndex < trimmedText.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskChar) {
                    val nextDigitIndex = mask.indexOf(maskChar, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmedText[textIndex++])
                maskIndex++
            }
            append(mask.substring(startIndex = maskIndex))
            addStyle(SpanStyle(color = maskColor), maskIndex, mask.length)
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskChar, text = text.text))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneVisualTransformation) return false
        if (mask != other.mask) return false
        if (maskChar != other.maskChar) return false
        return false
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }

    private class PhoneOffsetMapper(val mask: String, val numberChar: Char, private val text: String) : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int {
//            Log.e("TAG", "originalToTransformed: offset = $offset")
            var noneDigitCount = 0
            var i = 0
            while (i < offset + noneDigitCount) {
                if (mask[i++] != numberChar) noneDigitCount++
            }

            return  offset + noneDigitCount
        }

        override fun transformedToOriginal(offset: Int): Int = text.length

    }

}