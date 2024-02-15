package a77777_888.me.t.shoptestapp.ui.screens

import a77777_888.me.t.shoptestapp.R
import a77777_888.me.t.shoptestapp.model.User
import a77777_888.me.t.shoptestapp.ui.DataState
import a77777_888.me.t.shoptestapp.ui.MainActivity
import a77777_888.me.t.shoptestapp.ui.components.AccountTextField
import a77777_888.me.t.shoptestapp.ui.components.ErrorMessage
import a77777_888.me.t.shoptestapp.ui.components.LoadingMessage
import a77777_888.me.t.shoptestapp.ui.components.PhoneField
import a77777_888.me.t.shoptestapp.ui.components.Validator
import a77777_888.me.t.shoptestapp.ui.components.isPhoneValid
import a77777_888.me.t.shoptestapp.ui.entities.CATALOG_SCREEN
import a77777_888.me.t.shoptestapp.ui.entities.MAIN_SCREEN
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistrationScreen(
    navigateTo: (String) -> Unit,
    dataStateFlow: Flow<DataState> ,
    getRemoteData: () -> Unit,
    initCurrentUserRepository: (Context, User) -> Unit,
    isCurrentUserInDatabase: Boolean
) {
    val context = LocalContext.current
    val dataState = dataStateFlow.collectAsState(initial = DataState()).value

    when {
        dataState.loading -> LoadingMessage()
        dataState.error -> ErrorMessage(
            tryAgain = getRemoteData,
            cansel = (context as MainActivity)::finish,
            message = stringResource(R.string.data_load_error)
        )
        else -> {
            if (dataState.data != null)
                navigateTo(
                    if (isCurrentUserInDatabase) CATALOG_SCREEN else MAIN_SCREEN
                )
            else {
                var name by remember { mutableStateOf("") }
                var surname by remember { mutableStateOf("") }
                var phone by remember { mutableStateOf("") }

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.weight(1f))

                    AccountTextField(
                        value = name,
                        onValueChange = { name = it },
                        clean = { name = "" },
                        placeholderLabel = stringResource(id = R.string.name)
                    )

                    AccountTextField(
                        value = surname,
                        onValueChange = { surname = it },
                        clean = { surname = "" },
                        placeholderLabel = stringResource(id = R.string.surname)
                    )

                    PhoneField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        phone = phone,
                        onPhoneChanged = { phone = it },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.phone),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        },
                        trailingIcon = {
                            if (phone.length > 1)
                                IconButton(
                                    onClick = { phone = "" },
                                    modifier = Modifier.size(28.dp),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    )
                                ) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                                }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = if (phone.isEmpty()) Color.Transparent else MaterialTheme.colorScheme.error,
                            errorCursorColor = if (phone.isEmpty()) Color.Transparent else MaterialTheme.colorScheme.error,
                            cursorColor = Color.Transparent
                        ),
                    )

                    Spacer(modifier = Modifier.height(16.dp))
// Button
                    Button(
                        onClick = {
                            val user = User(name, surname, phone)
                            initCurrentUserRepository(context, user)
                            getRemoteData()
                        },
                        enabled = Validator.validateName(name) && Validator.validateName(surname) && phone.isPhoneValid(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(48.dp),
                        shape = MaterialTheme.shapes.small,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            disabledBackgroundColor = MaterialTheme.colorScheme.secondary,
                        )
                    ) {
                        Text(text = stringResource(R.string.enter), color = MaterialTheme.colorScheme.onPrimary)
                    }

                    Spacer(modifier = Modifier.weight(1.5f))

                    Text(
                        text = stringResource(R.string.condition_1),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = stringResource(R.string.condition_2),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelSmall,
                        textDecoration = TextDecoration.Underline
                    )

                }

            }

        }

    }

}

//@Preview(showBackground = true)
//@Composable
//fun PreviewRegistrationScreen() {
//    RegistrationScreen(
//        navigateTo = {},
//        dataState = DataState(listOf(), false),
//        getRemoteData = { /*TODO*/ },
//        initCurrentUserRepository = { _, _ -> },
//        isCurrentUserInDatabase = false
//    )
//}