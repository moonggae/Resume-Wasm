package com.ccc.resume.designsystem


import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.ccc.resume.resources.Pretendard_Black
import com.ccc.resume.resources.Pretendard_Bold
import com.ccc.resume.resources.Pretendard_ExtraBold
import com.ccc.resume.resources.Pretendard_ExtraLight
import com.ccc.resume.resources.Pretendard_Light
import com.ccc.resume.resources.Pretendard_Medium
import com.ccc.resume.resources.Pretendard_Regular
import com.ccc.resume.resources.Pretendard_SemiBold
import com.ccc.resume.resources.Pretendard_Thin
import com.ccc.resume.resources.Res
import org.jetbrains.compose.resources.Font

val PretendardFont: FontFamily
    @Composable
    get() = FontFamily(
        Font(
            resource = Res.font.Pretendard_Black,
            weight = FontWeight.Black
        ),
        Font(
            resource = Res.font.Pretendard_Bold,
            weight = FontWeight.Bold
        ),
        Font(
            resource = Res.font.Pretendard_ExtraBold,
            weight = FontWeight.ExtraBold
        ),
        Font(
            resource = Res.font.Pretendard_ExtraLight,
            weight = FontWeight.ExtraLight
        ),
        Font(
            resource = Res.font.Pretendard_Light,
            weight = FontWeight.Light
        ),
        Font(
            resource = Res.font.Pretendard_Medium,
            weight = FontWeight.Medium
        ),
        Font(
            resource = Res.font.Pretendard_Regular,
            weight = FontWeight.Normal
        ),
        Font(
            resource = Res.font.Pretendard_SemiBold,
            weight = FontWeight.SemiBold
        ),
        Font(
            resource = Res.font.Pretendard_Thin,
            weight = FontWeight.Thin
        )
    )