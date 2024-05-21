package com.apx8.mongoose.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apx8.mongoose.domain.constants.Stadium
import com.apx8.mongoose.domain.constants.Stadium2

@Composable
fun StadiumListScreen(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val stadiumList = mutableListOf<Stadium2>().also { list ->
        Stadium2.entries.forEach { _entry ->
            if (_entry != Stadium2.UNKNOWN) {
                list.add(_entry)
            }
        }
    }

    Surface(modifier = modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier.verticalScroll(scrollState).height(IntrinsicSize.Max)

        ) {

            items(stadiumList) { stadium ->
                StadiumItemView(stadium)
            }
        }
    }



//    val itemList = List(100) { index -> "Item ${index + 1}" }

//    Surface(modifier = modifier.fillMaxWidth()) {
//        Column(modifier = Modifier.verticalScroll(scrollState)) {
//
//            LazyColumn(modifier = modifier.fillMaxWidth()) {
//                items(stadiumList) { stadium ->
//                    StadiumItemView(stadium)
//                }
//            }
//        }
//    }

//    LazyColumn {
//        items(stadiumList) { stadium ->
//            Surface(
//                modifier = Modifier
//                    .clickable {  }
//                    .fillMaxSize()
//
//            ) {
//                StadiumItemView(stadium)
//            }
//        }
//    }
}

@Composable
fun StadiumItemView(stadium: Stadium2) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = stadium.signBoard)
    }
}



@Preview
@Composable
fun PreviewStadiumListScreen() {

    StadiumListScreen(
    )
}
