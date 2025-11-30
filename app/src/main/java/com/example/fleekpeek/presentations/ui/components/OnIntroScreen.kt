package com.example.fleekpeek.presentations.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.fleekpeek.Dimen
import com.example.fleekpeek.common.NewsButton
import com.example.fleekpeek.common.NewsTextButton
import com.example.fleekpeek.presentations.ui.pages
import kotlinx.coroutines.launch

@Composable
fun OnIntroScreen(events: (OnIntroScreenEvents) -> Unit) {


    Column(modifier = Modifier.fillMaxSize()) {

        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }

        val buttonState = remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Next")
                    1 -> listOf("Previous", "Next")
                    2 -> listOf("Previous", "Get Started")
                    else -> listOf("", "")
                }
            }
        }

        HorizontalPager(state = pagerState) {
            OnIntroPage(modifier = Modifier, page = pages[it])
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimen.MediumPadding1)
                .navigationBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            PageIndicator(
                modifier = Modifier.width(Dimen.IndicatorWidth),
                pageSize = pages.size,
                selectedPage = pagerState.currentPage,
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(verticalAlignment = Alignment.CenterVertically) {
                val scope = rememberCoroutineScope()
                if (buttonState.value[0].isNotEmpty()) {
                    NewsTextButton(
                        text = buttonState.value[0], onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                            }
                        }
                    )
                    NewsButton(text = buttonState.value[1], onClick = {
                        scope.launch {
                            if (pagerState.currentPage == 2) {
                                events(OnIntroScreenEvents.SaveAppEntry)
                            } else {
                                pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                            }
                        }
                    })
                } else {
                    NewsButton(text = buttonState.value[1], onClick = {
                        scope.launch {
                            if (pagerState.currentPage == 2) {
                                events(OnIntroScreenEvents.SaveAppEntry)
                            } else {
                                pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                            }
                        }
                    })
                }
            }
        }
    }

}