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
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun AgendamentoScreen(
    servicoId: Int = 0,
    servicoNome: String = "",
    servicoPreco: Double = 0.0,
    servicoDuracao: Int = 0,
    onConfirmarClick: (String, String) -> Unit = { _, _ -> },
    onVoltarClick: () -> Unit = {}
) {
    val hoje = remember { LocalDate.now() }
    val agora = remember { LocalTime.now() }
    val todosHorarios = listOf("09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00")

    var diaSelecionado by remember { mutableStateOf<LocalDate?>(null) }
    var horarioSelecionado by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }
    var horariosOcupados by remember { mutableStateOf<List<String>>(emptyList()) }
    var carregandoHorarios by remember { mutableStateOf(false) }
    var mesAtual by remember { mutableStateOf(YearMonth.from(hoje)) }

    val diasSemana = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb")

    LaunchedEffect(diaSelecionado, servicoId) {
        val dia = diaSelecionado ?: return@LaunchedEffect
        carregandoHorarios = true
        horarioSelecionado = ""
        try {
            val response = Api.service.getHorariosOcupados(servicoId, dia.toString())
            if (response.isSuccessful) {
                horariosOcupados = response.body() ?: emptyList()
            }
        } catch (e: Exception) {
            horariosOcupados = emptyList()
        }
        carregandoHorarios = false
    }

    // CORREÇÃO: verifica se horário já passou quando o dia selecionado é hoje
    fun horarioPassado(horario: String): Boolean {
        val diaSel = diaSelecionado ?: return false
        if (diaSel != hoje) return false
        val partes = horario.split(":")
        val horaHorario = LocalTime.of(partes[0].toInt(), partes[1].toInt())
        return horaHorario.isBefore(agora)
    }

    Box(modifier = Modifier.fillMaxSize().background(BrancoQuente)) {
        Box(
            modifier = Modifier.fillMaxWidth().height(220.dp)
                .shadow(16.dp, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MarromEscuro, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                stringResource(R.string.dia_horario),
                color = BrancoQuente,
                fontFamily = FrauncesFontFamily,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 60.dp, start = 32.dp, end = 32.dp)
            )
        }

        Box(
            modifier = Modifier.align(Alignment.TopCenter).offset(y = 123.dp).size(150.dp)
                .clip(RoundedCornerShape(75.dp)).background(MarromEscuro)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_beautyhub),
                contentDescription = "Logo BeautyHub",
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 290.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            if (servicoNome.isNotEmpty()) {
                Box(
                    modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()
                        .background(BegeClaro, RoundedCornerShape(12.dp)).padding(12.dp)
                ) {
                    Column {
                        Text(servicoNome, color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 15.sp)
                        Text("Duração: $servicoDuracao min | R$ ${"%.2f".format(servicoPreco)}", color = MarromMedio, fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Calendário
            Box(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                    .background(BegeClaro, RoundedCornerShape(12.dp)).padding(12.dp)
            ) {
                Column {
                    // Navegação de mês
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val mesMinimo = YearMonth.from(hoje)
                        val mesMaximo = mesMinimo.plusMonths(1)

                        TextButton(
                            onClick = { if (mesAtual > mesMinimo) mesAtual = mesAtual.minusMonths(1) },
                            enabled = mesAtual > mesMinimo
                        ) {
                            Text("‹", fontSize = 24.sp, color = if (mesAtual > mesMinimo) MarromEscuro else BegeMedio)
                        }

                        Text(
                            mesAtual.month.getDisplayName(TextStyle.FULL, Locale("pt", "BR"))
                                .replaceFirstChar { it.uppercase() } + " ${mesAtual.year}",
                            color = MarromEscuro,
                            fontFamily = FrauncesFontFamily,
                            fontSize = 14.sp
                        )

                        TextButton(
                            onClick = { if (mesAtual < mesMaximo) mesAtual = mesAtual.plusMonths(1) },
                            enabled = mesAtual < mesMaximo
                        ) {
                            Text("›", fontSize = 24.sp, color = if (mesAtual < mesMaximo) MarromEscuro else BegeMedio)
                        }
                    }

                    // Cabeçalho dias da semana
                    Row(modifier = Modifier.fillMaxWidth()) {
                        diasSemana.forEach { dia ->
                            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                                Text(dia, fontSize = 10.sp, color = MarromMedio, fontFamily = FrauncesFontFamily)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Dias do mês
                    val primeiroDia = mesAtual.atDay(1)
                    val totalDias = mesAtual.lengthOfMonth()
                    val diaSemanaInicio = primeiroDia.dayOfWeek.value % 7
                    val celulas = diaSemanaInicio + totalDias
                    val linhas = (celulas + 6) / 7

                    for (linha in 0 until linhas) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            for (coluna in 0 until 7) {
                                val indice = linha * 7 + coluna
                                val diaNum = indice - diaSemanaInicio + 1
                                Box(
                                    modifier = Modifier.weight(1f).aspectRatio(1f).padding(2.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (diaNum in 1..totalDias) {
                                        val data = mesAtual.atDay(diaNum)
                                        val passado = data.isBefore(hoje)
                                        val selecionado = diaSelecionado == data
                                        val ehHoje = data == hoje

                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(
                                                    when {
                                                        selecionado -> MarromEscuro
                                                        ehHoje -> Dourado.copy(alpha = 0.2f)
                                                        else -> BrancoQuente
                                                    },
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .clickable(enabled = !passado) {
                                                    diaSelecionado = data
                                                    erro = ""
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                "$diaNum",
                                                fontSize = 12.sp,
                                                color = when {
                                                    passado -> BegeMedio
                                                    selecionado -> BrancoQuente
                                                    else -> MarromEscuro
                                                },
                                                fontFamily = FrauncesFontFamily
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Horários
            Text(
                "Escolha o horário:",
                color = MarromMedio,
                fontFamily = FrauncesFontFamily,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (carregandoHorarios) {
                CircularProgressIndicator(color = MarromEscuro, modifier = Modifier.size(24.dp))
            } else {
                // Linha 1 de horários
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    todosHorarios.take(5).forEach { horario ->
                        val selecionado = horarioSelecionado == horario
                        val ocupado = horario in horariosOcupados
                        // CORREÇÃO: bloqueia horários que já passaram no dia de hoje
                        val passado = horarioPassado(horario)
                        val bloqueado = ocupado || passado

                        Box(
                            modifier = Modifier
                                .background(
                                    when {
                                        bloqueado -> BegeMedio.copy(alpha = 0.3f)
                                        selecionado -> MarromEscuro
                                        else -> BegeClaro
                                    },
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable(enabled = !bloqueado && diaSelecionado != null) {
                                    horarioSelecionado = horario
                                }
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                horario,
                                color = when {
                                    bloqueado -> MarromMedio.copy(alpha = 0.3f)
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
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    todosHorarios.drop(5).forEach { horario ->
                        val selecionado = horarioSelecionado == horario
                        val ocupado = horario in horariosOcupados
                        // CORREÇÃO: bloqueia horários que já passaram no dia de hoje
                        val passado = horarioPassado(horario)
                        val bloqueado = ocupado || passado

                        Box(
                            modifier = Modifier
                                .background(
                                    when {
                                        bloqueado -> BegeMedio.copy(alpha = 0.3f)
                                        selecionado -> MarromEscuro
                                        else -> BegeClaro
                                    },
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable(enabled = !bloqueado && diaSelecionado != null) {
                                    horarioSelecionado = horario
                                }
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                horario,
                                color = when {
                                    bloqueado -> MarromMedio.copy(alpha = 0.3f)
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
                Text(
                    erro,
                    color = Vermelho,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val diaEscolhido = diaSelecionado
                    if (diaEscolhido == null || horarioSelecionado.isEmpty()) {
                        erro = "Selecione um dia e horário!"
                        return@Button
                    }
                    val dataHora = "${diaEscolhido}T${horarioSelecionado}:00"
                    val dataFormatada = formatarData(diaEscolhido.toString()) + " às " + horarioSelecionado
                    onConfirmarClick(dataHora, dataFormatada)
                },
                modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MarromEscuro),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    stringResource(R.string.confirmar_agendamento),
                    color = BrancoQuente,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = onVoltarClick) {
                Text(stringResource(R.string.voltar), color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}