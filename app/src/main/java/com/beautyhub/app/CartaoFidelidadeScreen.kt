package com.beautyhub.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beautyhub.app.ui.theme.*

@Composable
fun CartaoFidelidadeScreen(
    onVoltarClick: () -> Unit = {}
) {
    val metaTotal = 10
    var totalProcedimentos by remember { mutableStateOf(0) }
    var carregando by remember { mutableStateOf(true) }

    val nome = SessionManager.getName()?.split(" ")?.firstOrNull()
        ?: (SessionManager.getEmail() ?: "").substringBefore("@").replaceFirstChar { it.uppercase() }

    LaunchedEffect(Unit) {
        try {
            val token = "Bearer ${SessionManager.getToken()}"
            val response = Api.service.getMyAppointments(token)
            if (response.isSuccessful) {
                val body = response.body() ?: emptyList()
                totalProcedimentos = body.count {
                    it.status == "COMPLETED" || it.status == "SCHEDULED"
                }
            }
        } catch (e: Exception) {
            totalProcedimentos = 0
        }
        carregando = false
    }

    val proximoDesconto = when {
        totalProcedimentos < 3 -> "No 3° procedimento — 20% de desconto"
        totalProcedimentos < 5 -> "No 5° procedimento — 25% de desconto"
        totalProcedimentos < 10 -> "No 10° procedimento — servico gratuito"
        else -> "Voce atingiu a meta! Resgate seu premio"
    }

    val descontoConquistado = when {
        totalProcedimentos >= 5 -> "5° procedimento — 25% de desconto aplicado"
        totalProcedimentos >= 3 -> "3° procedimento — 20% de desconto aplicado"
        else -> "Nenhum desconto conquistado ainda"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BrancoQuente)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .shadow(16.dp, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MarromEscuro, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = "Cartao Fidelidade",
                color = BrancoQuente,
                fontFamily = FrauncesFontFamily,
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 60.dp)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 123.dp)
                .size(150.dp)
                .clip(RoundedCornerShape(75.dp))
                .background(MarromEscuro)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_beautyhub),
                contentDescription = "Logo BeautyHub",
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 290.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bem-vinda, $nome",
                color = Dourado,
                fontFamily = FrauncesFontFamily,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Veja seu progresso de fidelidade",
                color = MarromMedio,
                fontFamily = FrauncesFontFamily,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (carregando) {
                CircularProgressIndicator(color = MarromEscuro)
            } else {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth()
                        .background(MarromEscuro, RoundedCornerShape(16.dp))
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "SEUS PROCEDIMENTOS",
                        color = BegeMedio,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        (1..5).forEach { i ->
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(
                                        color = if (i <= totalProcedimentos) Dourado else MarromMedio.copy(alpha = 0.3f),
                                        shape = RoundedCornerShape(22.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (i <= totalProcedimentos) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .background(BrancoQuente, RoundedCornerShape(5.dp))
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        (6..10).forEach { i ->
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(
                                        color = if (i <= totalProcedimentos) Dourado else MarromMedio.copy(alpha = 0.3f),
                                        shape = RoundedCornerShape(22.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (i <= totalProcedimentos) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .background(BrancoQuente, RoundedCornerShape(5.dp))
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "$totalProcedimentos de $metaTotal procedimentos",
                        color = BegeMedio,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                BeneficioCard(
                    titulo = "Proximo desconto",
                    texto = proximoDesconto,
                    destaque = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                BeneficioCard(
                    titulo = "Desconto ja conquistado",
                    texto = descontoConquistado,
                    destaque = totalProcedimentos >= 3
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth()
                        .background(MarromEscuro, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Meta final",
                        color = Dourado,
                        fontFamily = FrauncesFontFamily,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Complete 10 procedimentos e ganhe um servico gratuito no seu salao favorito!",
                        color = BegeMedio,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            TextButton(onClick = onVoltarClick) {
                Text("Voltar", color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun BeneficioCard(titulo: String, texto: String, destaque: Boolean) {
    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth()
            .background(BegeClaro, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = titulo,
            color = MarromEscuro,
            fontFamily = FrauncesFontFamily,
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = texto,
            color = if (destaque) Dourado else MarromMedio,
            fontSize = 12.sp
        )
    }
}