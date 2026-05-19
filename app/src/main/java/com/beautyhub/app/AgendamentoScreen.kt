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
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

fun gerarProximos7Dias(): List<Pair<String, String>> {
    val hoje = LocalDate.now()
    val diasSemanaAbrev = mapOf(
        "Monday" to "Seg", "Tuesday" to "Ter", "Wednesday" to "Qua",
        "Thursday" to "Qui", "Friday" to "Sex", "Saturday" to "Sáb", "Sunday" to "Dom"
    )
    return (0..6).map { offset ->
        val data = hoje.plusDays(offset.toLong())
        val nomeDia = diasSemanaAbrev[data.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)] ?: ""
        val dia = data.dayOfMonth.toString()
        Pair("$nomeDia\n$dia", data.toString())
    }
}

@Composable
fun AgendamentoScreen(
    servicoId: Int = 0,
    servicoNome: String = "",
    servicoPreco: Double = 0.0,
    servicoDuracao: Int = 0,
    onConfirmarClick: (String, String) -> Unit = { _, _ -> },
    onVoltarClick: () -> Unit = {}
) {
    val proximosDias = remember { gerarProximos7Dias() }
    val todosHorarios = listOf("09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00")

    var diaSelecionado by remember { mutableStateOf<Pair<String, String>?>(null) }
    var horarioSelecionado by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }
    var horariosOcupados by remember { mutableStateOf<List<String>>(emptyList()) }
    var carregandoHorarios by remember { mutableStateOf(false) }

    // Busca horários ocupados quando muda o dia
    LaunchedEffect(diaSelecionado, servicoId) {
        val dia = diaSelecionado ?: return@LaunchedEffect
        carregandoHorarios = true
        horarioSelecionado = ""
        try {
            val response = Api.service.getHorariosOcupados(servicoId, dia.second)
            if (response.isSuccessful) {
                horariosOcupados = response.body() ?: emptyList()
            }
        } catch (e: Exception) {
            horariosOcupados = emptyList()
        }
        carregandoHorarios = false
    }

    // Verifica se o dia tem todos os horários ocupados
    fun diaEstaLotado(iso: String): Boolean {
        if (diaSelecionado?.second != iso) return false
        return todosHorarios.all { it in horariosOcupados }
    }

    Box(modifier = Modifier.fillMaxSize().background(BrancoQuente)) {
        Box(
            modifier = Modifier.fillMaxWidth().height(220.dp)
                .shadow(16.dp, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MarromEscuro, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(stringResource(R.string.dia_horario), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 60.dp, start = 32.dp, end = 32.dp))
        }

        Box(modifier = Modifier.align(Alignment.TopCenter).offset(y = 123.dp).size(150.dp).clip(RoundedCornerShape(75.dp)).background(MarromEscuro)) {
            Image(painter = painterResource(id = R.drawable.logo_beautyhub), contentDescription = "Logo BeautyHub", modifier = Modifier.fillMaxSize())
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 290.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            if (servicoNome.isNotEmpty()) {
                Box(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth().background(BegeClaro, RoundedCornerShape(12.dp)).padding(12.dp)) {
                    Column {
                        Text(servicoNome, color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 15.sp)
                        Text("Duração: $servicoDuracao min | R$ ${"%.2f".format(servicoPreco)}", color = MarromMedio, fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text("Escolha o dia:", color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp, modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp))
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                proximosDias.forEach { (label, iso) ->
                    val selecionado = diaSelecionado?.second == iso
                    val lotado = diaEstaLotado(iso)
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                when {
                                    lotado -> BegeMedio.copy(alpha = 0.4f)
                                    selecionado -> MarromEscuro
                                    else -> BegeClaro
                                },
                                RoundedCornerShape(8.dp)
                            )
                            .clickable(enabled = !lotado) { diaSelecionado = Pair(label, iso) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            label,
                            color = when {
                                lotado -> MarromMedio.copy(alpha = 0.4f)
                                selecionado -> BrancoQuente
                                else -> MarromEscuro
                            },
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Escolha o horário:", color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp, modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp))
            Spacer(modifier = Modifier.height(8.dp))

            if (carregandoHorarios) {
                CircularProgressIndicator(color = MarromEscuro, modifier = Modifier.size(24.dp))
            } else {
                // Linha 1 de horários
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    todosHorarios.take(5).forEach { horario ->
                        val selecionado = horarioSelecionado == horario
                        val ocupado = horario in horariosOcupados
                        Box(
                            modifier = Modifier
                                .background(
                                    when {
                                        ocupado -> BegeMedio.copy(alpha = 0.3f)
                                        selecionado -> MarromEscuro
                                        else -> BegeClaro
                                    },
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable(enabled = !ocupado && diaSelecionado != null) {
                                    horarioSelecionado = horario
                                }
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                horario,
                                color = when {
                                    ocupado -> MarromMedio.copy(alpha = 0.3f)
                                    selecionado -> BrancoQuente
                                    else -> MarromEscuro
                                },
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Linha 2 de horários
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    todosHorarios.drop(5).forEach { horario ->
                        val selecionado = horarioSelecionado == horario
                        val ocupado = horario in horariosOcupados
                        Box(
                            modifier = Modifier
                                .background(
                                    when {
                                        ocupado -> BegeMedio.copy(alpha = 0.3f)
                                        selecionado -> MarromEscuro
                                        else -> BegeClaro
                                    },
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable(enabled = !ocupado && diaSelecionado != null) {
                                    horarioSelecionado = horario
                                }
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                horario,
                                color = when {
                                    ocupado -> MarromMedio.copy(alpha = 0.3f)
                                    selecionado -> BrancoQuente
                                    else -> MarromEscuro
                                },
                                fontSize = 11.sp
                            )
                        }
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
                    val diaEscolhido = diaSelecionado
                    if (diaEscolhido == null || horarioSelecionado.isEmpty()) {
                        erro = "Selecione um dia e horário!"
                        return@Button
                    }
                    val dataHora = "${diaEscolhido.second}T${horarioSelecionado}:00"
                    val dataFormatada = formatarData(diaEscolhido.second) + " às " + horarioSelecionado
                    onConfirmarClick(dataHora, dataFormatada)
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