package com.apx8.mongoose.presentation.view.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.apx8.mongoose.R
import com.apx8.mongoose.domain.constants.Stadium


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StadiumBottomSheet(
    currentStadium: Stadium,
    doSelectStadium: (String) -> Unit,
    onDismiss: () -> Unit
) {

    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {

        val stadiums = mutableListOf<StadiumViewDto>().also { list ->
            Stadium.entries.forEach { entry ->
                if (entry != Stadium.NAN) {
                    list.add(
                        StadiumViewDto(
                            color = entry.teamColor,
                            name = entry.signBoard,
                            code = entry.code
                        )
                    )
                }
            }
        }

        LazyColumn {
            items(stadiums) { stadium ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            doSelectStadium.invoke(stadium.code)
                            onDismiss.invoke()
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        /* 경기장 색상 아이콘*/
                        Image(
                            painterResource(id = stadium.color),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                                .padding(end = 8.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                        )
                        /* 경기장 사인보드(이름)*/
                        Text(text = stadium.name)
                    }

                    /* 선택된 경기장*/
                    if (currentStadium.code == stadium.code) {
                        Image(
                            painterResource(id = R.drawable.ic_check),
                            contentDescription = null,
                            modifier = Modifier.size(23.dp)
                        )
                    }
                }
            }
        }
    }
}