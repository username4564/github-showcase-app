package com.example.showcaseapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

// TODO: ET 17.04.2022 replace with original material theme fields?
val MaterialTheme.textColorSecondary
    @Composable
    get() = typography.body2.color.copy(alpha = 0.54F)