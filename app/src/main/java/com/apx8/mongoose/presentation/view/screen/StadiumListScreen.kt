package com.apx8.mongoose.presentation.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.apx8.mongoose.domain.constants.Stadium

@Composable
fun StadiumListScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = " StadiumListScreen !!")
    }
//    val scrollState = rememberScrollState()
//
//    val stadiumList = mutableListOf<Stadium2>().also { list ->
//        Stadium2.entries.forEach { _entry ->
//            if (_entry != Stadium2.UNKNOWN) {
//                list.add(_entry)
//            }
//        }
//    }
//
//    println("probe :: stadiumList : ${stadiumList}")
//
//    Surface(modifier = modifier.height(IntrinsicSize.Max)) {
//        LazyColumn(
//            modifier = Modifier
//                .verticalScroll(scrollState)
//                .height(IntrinsicSize.Max)
//        ) {
//
//            item {
//                Header()
//            }
//
//            items(stadiumList) { stadium ->
//                StadiumItemView(stadium)
//            }
//            item {
//                Footer()
//            }
//        }
//    }



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
fun Header() {
    Box() {
       Text(text = "this is header")
    }
}

@Composable
fun Footer() {
    Box() {
        Text(text = "this is footer")
    }
}

@Composable
fun StadiumItemView(stadium: Stadium) {
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

//    StadiumListScreen(
//    )
}
