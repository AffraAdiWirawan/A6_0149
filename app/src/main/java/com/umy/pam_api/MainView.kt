package com.umy.pam_api

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeApp(
    modifier: Modifier = Modifier,
    onHalamanHomeDosen: () -> Unit,
    onHalamanHomeMK: () -> Unit,
    onHalamanHomeVDR: () -> Unit,
    onHalamanHomeLKS: () -> Unit

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 35.dp)
            .background(color = Color.White),
        verticalArrangement = Arrangement.Top
    ) {
        HeaderSection()
        BodySection(onHalamanHomeDosen = onHalamanHomeDosen, onHalamanHomeMK = onHalamanHomeMK, onHalamanHomeLKS = onHalamanHomeLKS, onHalamanHomeVDR = onHalamanHomeVDR,)
    }
}

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 48.dp))
            .background(color = colorResource(R.color.primary))
            .padding(bottom = 25.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 1.dp, top = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.umy),
                    contentDescription = "Home Image",
                    modifier = Modifier
                        .padding(5.dp)
                        .size(90.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.padding(4.dp))
                Text(
                    text = "Selamat Datang",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green
                )
                Text(
                    text = "Universitas Muhammadiyah Yogyakarta",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun BodySection(
    onHalamanHomeDosen: () -> Unit,
    onHalamanHomeMK: () -> Unit,
    onHalamanHomeVDR: () -> Unit,
    onHalamanHomeLKS: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Silahkan pilih menu yang ingin anda kelola",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        // "Dosen" Button
        ManageBox(
            title = "Acara",
            description = "Kelola Acara",
            backgroundColor = Color(0xFF388E3C),
            iconResource = R.drawable.dosen,
            onClick = { onHalamanHomeDosen() }
        )

        // "Mata Kuliah" Button
        ManageBox(
            title = "Lokasi",
            description = "Kelola Lokasi",
            backgroundColor = Color(0xFF388E3C),
            iconResource = R.drawable.lokasi,
            onClick = { onHalamanHomeLKS() }
        )
        ManageBox(
            title = "Client",
            description = "Kelola Client Disini",
            backgroundColor = Color(0xFF388E3C),
            iconResource = R.drawable.client,
            onClick = { onHalamanHomeMK() }
        )
        ManageBox(
            title = "Vendor",
            description = "Kelola Vendor Disini",
            backgroundColor = Color(0xFF388E3C),
            iconResource = R.drawable.vendor,
            onClick = { onHalamanHomeVDR() }
        )
    }
}

@Composable
fun ManageBox(
    title: String,
    description: String,
    backgroundColor: Color,
    iconResource: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
            Image(
                painter = painterResource(id = iconResource),
                contentDescription = "$title Icon",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        }
    }
}