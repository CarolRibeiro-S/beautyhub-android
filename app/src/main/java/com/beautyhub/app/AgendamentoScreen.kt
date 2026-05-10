package com.beautyhub.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun AgendamentoScreen(
    servicoId: Int = 0,
    onConfirmarClick: () -> Unit = {},
    onVoltarClick: () -> Unit = {}
) {
    val dias = listOf("Seg\n20", "Ter\n21", "Qua\n22", "Qui\n23", "Sex\n24", "Sab\n25", "Dom\n26")
    val horarios = listOf("09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00")

    var diaSelecionado by remember { mutableStateOf("") }
    var horarioSelecionado by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().background(BrancoQuente)) {
        Box(
            modifier = Modifier.fillMaxWidth().height(220.dp)
                .shadow(16.dp, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MarromEscuro, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(stringResource(R.string.dia_horario), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 20.sp,
                textAlign = TextAlign.Center, modifier = Modifier.padding(top = 60.dp, start = 32.dp, end = 32.dp))
        }

        Box(modifier = Modifier.align(Alignment.TopCenter).offset(y = 123.dp).size(150.dp).clip(RoundedCornerShape(75.dp)).background(MarromEscuro)) {
            Image(painter = painterResource(id = R.drawable.logo_beautyhub), contentDescription = "Logo BeautyHub", modifier = Modifier.fillMaxSize())
        }

        Column(modifier = Modifier.fillMaxSize().padding(top = 290.dp).verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(stringResource(R.string.dia_horario), color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Dia:", color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp, modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp))
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                dias.forEach { dia ->
                    val selecionado = diaSelecionado == dia
                    Box(
                        modifier = Modifier.size(40.dp)
                            .background(if (selecionado) MarromEscuro else BegeClaro, RoundedCornerShape(8.dp))
                            .clickable { diaSelecionado = dia },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(dia, color = if (selecionado) BrancoQuente else MarromEscuro, fontSize = 10.sp, textAlign = TextAlign.Center, lineHeight = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Horário:", color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp, modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp))
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                horarios.take(5).forEach { horario ->
                    val selecionado = horarioSelecionado == horario
                    Box(
                        modifier = Modifier.background(if (selecionado) MarromEscuro else BegeClaro, RoundedCornerShape(8.dp))
                            .clickable { horarioSelecionado = horario }.padding(horizontal = 8.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(horario, color = if (selecionado) BrancoQuente else MarromEscuro, fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                horarios.drop(5).forEach { horario ->
                    val selecionado = horarioSelecionado == horario
                    Box(
                        modifier = Modifier.background(if (selecionado) MarromEscuro else BegeClaro, RoundedCornerShape(8.dp))
                            .clickable { horarioSelecionado = horario }.padding(horizontal = 8.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(horario, color = if (selecionado) BrancoQuente else MarromEscuro, fontSize = 11.sp)
                    }
                }
            }

            if (erro.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(erro, color = Vermelho, fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (diaSelecionado.isEmpty() || horarioSelecionado.isEmpty()) {
                        erro = "Selecione um dia e horário!"
                        return@Button
                    }
                    onConfirmarClick()
                },
                modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MarromEscuro),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(stringResource(R.string.confirmar_agendamento), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = onVoltarClick) {
                Text(stringResource(R.string.voltar), color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}