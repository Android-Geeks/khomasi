package com.company.khomasi.presentation.ui.screens.SignUpScreen

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyOutlinedButton
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.Shapes
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier
){
    Column (modifier = modifier
        .background(color = MaterialTheme.colorScheme.background)
    )
    {
        Spacer(modifier = modifier.height(177.dp))
        Image(painter = painterResource(id = R.drawable.player) ,
            contentDescription = " ",
            modifier = modifier
                .fillMaxWidth()
                .height(245.dp)
                .padding(11.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = modifier.height(68.dp))

        MyOutlinedButton(
            text = R.string.create_account,
            onClick = {  },
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = modifier.height(16.dp))

        MyButton(text = R.string.login,
            onClick = {  },
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 24.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
        )
        Spacer(modifier = modifier.height(34.dp))

        Row (modifier = modifier.fillMaxWidth()){
            Divider(thickness = 1.dp,
                modifier = modifier
                    .weight(1.5f)
                    .padding(start = 34.dp,end=3.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(text = stringResource(id =R.string.or_register_via),
                modifier = modifier.weight(0.7f)
                    .padding(start = 5.dp))
            Divider(thickness = 1.dp,
                modifier = modifier
                    .weight(1.5f)
                    .padding(start = 3.dp,end=34.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Spacer(modifier = modifier.height(28.dp))
        Box(
            modifier = modifier
                .size(56.dp)
                .background(
                    color = Color.White,
                    shape = Shapes.extraLarge
                )
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center,
        )

        {
            Image(
                painter = painterResource(id = R.drawable.icons_google),
                contentDescription ="null" ,
                modifier = modifier
                    .size(32.dp)
                    .clickable { }
            )
        }
        Spacer(modifier = modifier.height(36.dp))

        Text(text = stringResource(id = R.string.by_registering_you_agree_to),
            textAlign = TextAlign.Center,
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally))
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxWidth()
        ) {

            MyTextButton(
                text = R.string.privacy_policy,
                onClick = {  })
            Text(text = "and",
                color = MaterialTheme.colorScheme.primary)

            MyTextButton(text =R.string.privacy_policy
                , onClick = {  })
        }
    }
}

@Preview(name="dark", uiMode = UI_MODE_NIGHT_YES)
@Preview(name="light", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun SignUpPreview(){
    KhomasiTheme {
        SignUpScreen()
    }
}