package a77777_888.me.t.shoptestapp.ui.components

import a77777_888.me.t.shoptestapp.R
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyPager(
    modifier: Modifier = Modifier,
    list: List<Int> = images,
    indicatorColor: Color,
    indicatorHeight: Dp = 8.dp,
    imageContentScale: ContentScale = ContentScale.FillWidth,
) {
    val pagerState = rememberPagerState { list.size }

    Box(modifier = modifier){
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
//                .size(LocalConfiguration.current.screenHeightDp.dp)
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                Image(
                modifier = Modifier
                    .fillMaxSize().align(Alignment.Center),
//                    .padding(16.dp),
                    painter = painterResource(id = list[page]),
                    contentDescription = null,
                    contentScale = imageContentScale,
                )
            }

        }

        Row(
            Modifier
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
//                .padding(bottom = 8.dp)
            ,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) indicatorColor else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(indicatorHeight)
                )

            }

          }
        }
}

val images = listOf( R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6)
