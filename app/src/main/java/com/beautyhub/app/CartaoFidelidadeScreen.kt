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
import androidx.compose.ui.res.stringResource
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

    val desc1 = stringResource(R.string.desc_1_proc)
    val desc3 = stringResource(R.string.desc_3_proc)
    val desc5 = stringResource(R.string.desc_5_proc)
    val desc10 = stringResource(R.string.desc_10_proc)
    val metaAtingida = stringResource(R.string.meta_atingida)
    val desconto1 = stringResource(R.string.desconto_1_aplicado)
    val desconto3 = stringResource(R.string.desconto_3_aplicado)
    val desconto5 = stringResource(R.string.desconto_5_aplicado)
    val nenhumDesconto = stringResource(R.string.nenhum_desconto)

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

    // Próximo desconto: mostra o que ainda não foi alcançado
    val proximoDesconto = when {
        totalProcedimentos < 1 -> desc1
        totalProcedimentos < 3 -> desc3
        totalProcedimentos < 5 -> desc5
        totalProcedimentos < 10 -> desc10
        else -> metaAtingida
    }

    // Desconto conquistado: mostra o maior desconto já ativado
    val descontoConquistado = when {
        totalProcedimentos >= 5 -> desconto5
        totalProcedimentos >= 3 -> desconto3
        totalProcedimentos >= 1 -> desconto1   // ← CORREÇÃO: 1° agendamento conta
        else -> nenhumDesconto
    }

    Box(modifier = Modifier.fillMaxSize().background(BrancoQuente)) {
        Box(
            modifier = Modifier.fillMaxWidth().height(220.dp)
                .shadow(16.dp, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MarromEscuro, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = stringResource(R.string.cartao_fidelidade),
                color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 22.sp,
                modifier = Modifier.padding(top = 60.dp)
            )
        }

        Box(modifier = Modifier.align(Alignment.TopCenter).offset(y = 123.dp).size(150.dp).clip(RoundedCornerShape(75.dp)).background(MarromEscuro)) {
            Image(painter = painterResource(id = R.drawable.logo_beautyhub), contentDescription = "Logo BeautyHub", modifier = Modifier.fillMaxSize())
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 290.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.bem_vinda_nome, nome),
                color = Dourado, fontFamily = FrauncesFontFamily, fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.progresso_fidelidade),
                color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (carregando) {
                CircularProgressIndicator(color = MarromEscuro)
            } else {
                Column(
                    modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()
                        .background(MarromEscuro, RoundedCornerShape(16.dp)).padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(R.string.seus_procedimentos), color = BegeMedio, fontSize = 11.sp)
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        (1..5).forEach { i ->
                            Box(
                                modifier = Modifier.size(44.dp).background(
                                    color = if (i <= totalProcedimentos) Dourado else MarromMedio.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(22.dp)
                                ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (i <= totalProcedimentos) {
                                    Box(modifier = Modifier.size(10.dp).background(BrancoQuente, RoundedCornerShape(5.dp)))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        (6..10).forEach { i ->
                            Box(
                                modifier = Modifier.size(44.dp).background(
                                    color = if (i <= totalProcedimentos) Dourado else MarromMedio.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(22.dp)
                                ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (i <= totalProcedimentos) {
                                    Box(modifier = Modifier.size(10.dp).background(BrancoQuente, RoundedCornerShape(5.dp)))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.de_procedimentos, totalProcedimentos, metaTotal),
                        color = BegeMedio, fontSize = 12.sp, textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                BeneficioCard(titulo = stringResource(R.string.proximo_desconto), texto = proximoDesconto, destaque = true)
                Spacer(modifier = Modifier.height(12.dp))
                BeneficioCard(titulo = stringResource(R.string.desconto_conquistado), texto = descontoConquistado, destaque = totalProcedimentos >= 1)
                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()
                        .background(MarromEscuro, RoundedCornerShape(12.dp)).padding(16.dp)
                ) {
                    Text(text = stringResource(R.string.meta_final_titulo), color = Dourado, fontFamily = FrauncesFontFamily, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = stringResource(R.string.meta_final_texto), color = BegeMedio, fontSize = 12.sp, lineHeight = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            TextButton(onClick = onVoltarClick) {
                Text(stringResource(R.string.voltar), color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun BeneficioCard(titulo: String, texto: String, destaque: Boolean) {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()
            .background(BegeClaro, RoundedCornerShape(12.dp)).padding(16.dp)
    ) {
        Text(text = titulo, color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = texto, color = if (destaque) Dourado else MarromMedio, fontSize = 12.sp)
    }
}