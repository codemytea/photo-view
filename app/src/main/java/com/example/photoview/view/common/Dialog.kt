package com.example.photoview.view.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Allows for easy showing of dialogs.
 * */
class Dialog(
    private val stringError: (DialogParams) -> Unit,
) {
    class ButtonParams(
        val positiveText: String = "Ok",
        val positiveAction: () -> Unit = {},
        val negativeText: String? = null,
        val negativeAction: () -> Unit = { },
    )

    class DialogParams(
        val title: String,
        val message: String,
        val buttonParams: ButtonParams
    )

    private fun show(
        title: String,
        message: String,
        buttons: ButtonParams = ButtonParams(),
    ) = stringError(
        DialogParams(title, message, buttons),
    )

    fun genericError(err: Throwable) {
        err.printStackTrace()
        return err.message?.let {
                show(title = "Error", message = it)
            } ?: show(title = "Error", message = "An unexpected error occurred.")
        
    }
}

/**
 * Provides a dialog object for the composable to use.
 * */
@Composable
fun rememberDialog(): Dialog {

    var params by remember {
        mutableStateOf<Dialog.DialogParams?>(null)
    }

    params?.let {
        AlertDialog(
            {
                params = null
                it.buttonParams.negativeAction()
            },
            title = { Text(it.title) },
            text = { Text(it.message) },
            confirmButton = {
                TextButton(onClick = {
                    it.buttonParams.positiveAction()
                    params = null
                }) { Text(it.buttonParams.positiveText) }
            },
            dismissButton =
            it.buttonParams.negativeText?.let { text ->
                {
                    TextButton(onClick = {
                        it.buttonParams.negativeAction()
                        params = null
                    }) { Text(text) }
                }
            },
            modifier = Modifier.padding(16.dp),
        )
    }

    return Dialog { params = it }
}

