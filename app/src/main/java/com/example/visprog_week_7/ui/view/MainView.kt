package com.example.visprog_week_7.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.visprog_week_7.ui.viewmodel.ViewModelMain

@Composable
fun MainView(
    ViewModel: ViewModelMain = ViewModelMain()
) {

}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainViewPreview() {
    MainView()
}