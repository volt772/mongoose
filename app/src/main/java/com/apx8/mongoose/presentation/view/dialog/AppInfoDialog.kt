package com.apx8.mongoose.presentation.view.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AppInfoDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    buttonCancelLabel: String?= null,
    buttonConfirmLabel: String?= null,
) {
    /**
     * @box Dialog
     */
    AlertDialog(
        /**
         * @view 아이콘
         */
        icon = { Icon(icon, contentDescription = "") },

        /**
         * @view 타이틀
         */
        title = { Text(text = dialogTitle) },

        /**
         * @view 내용
         */
        text = { Text(text = dialogText) },

        /**
         * @view `취소` 이벤트
         */
        onDismissRequest = { onDismissRequest() },

        /**
         * @view `확인` 버튼클릭 이벤트
         */
        confirmButton = {
            if (!buttonConfirmLabel.isNullOrEmpty()) {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text(buttonConfirmLabel)
                }
            }
        },

        /**
         * @view `취소` 버튼클릭 이벤트
         */
        dismissButton = {
            if (!buttonCancelLabel.isNullOrEmpty()) {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(buttonCancelLabel)
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewAppInitialDialog() {
    AppInfoDialog(
        onDismissRequest = { },
        onConfirmation = { },
        dialogTitle = "환영합니다!",
        dialogText = "데이터 제공사의 상황에 따라 일부 날씨 정보가 부정확할 수 있으며, 날씨 표기가 다소 부자연스러울 수 있습니다. 모든 내용은 참고용도로만 이용하시기 바랍니다.",
        icon = Icons.Default.Face,
        buttonCancelLabel = "",
        buttonConfirmLabel = "확인하였습니다"
    )
}