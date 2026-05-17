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
import kotlinx.coroutines.launch

data class AgendamentoItem(
    val id: Long = 0,
    val servico: String,
    val data: String,
    val horario: String,
    var status: String
)

fun formatarData(dataIso: String): String {
    return try {
        val partes = dataIso.split("-")
        if (partes.size == 3) "${partes[2]}/${partes[1]}/${partes[0]}" else dataIso
    } catch (e: Exception) {
        dataIso
    }
}

@Composable
fun MeusAgendamentosScreen(
    onVoltarClick: () -> Unit = {},
    onReagendarClick: () -> Unit = {},
    onHomeClick: () -> Unit = {}
) {
    var agendamentos by remember { mutableStateOf<List<AgendamentoItem>>(emptyList()) }
    var carregando by remember { mutableStateOf(true) }
    var mostrarDialogCancelar by remember { mutableStateOf<AgendamentoItem?>(null) }
    var mostrarSnackbar by remember { mutableStateOf("") }
    var refreshKey by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(refreshKey) {
        carregando = true
        try {
            val token = "Bearer ${SessionManager.getToken()}"
            val response = Api.service.getMyAppointments(token)
            if (response.isSuccessful) {
                val body = response.body() ?: emptyList()
                agendamentos = body.map { apt ->
                    val partes = apt.dataHoraInicio.split("T")
                    val dataIso = partes.getOrNull(0) ?: ""
                    val horario = partes.getOrNull(1)?.substring(0, 5) ?: ""
                    val nomeServico = apt.servicoNome
                        ?: apt.service?.nome
                        ?: "Serviço"
                    AgendamentoItem(
                        id = apt.id.toLong(),
                        servico = nomeServico,
                        data = formatarData(dataIso),
                        horario = horario,
                        status = when (apt.status) {
                            "SCHEDULED" -> "Confirmado"
                            "CANCELLED" -> "Cancelado"
                            "COMPLETED" -> "Concluído"
                            else -> apt.status
                        }
                    )
                }
            } else {
                mostrarSnackbar = "Erro ao carregar agendamentos (${response.code()})"
            }
        } catch (e: Exception) {
            mostrarSnackbar = "Erro de conexão: ${e.message}"
        }
        carregando = false
    }

    mostrarDialogCancelar?.let { agendamento ->
        AlertDialog(
            onDismissRequest = { mostrarDialogCancelar = null },
            containerColor = BrancoQuente,
            title = {
                Text(
                    "Cancelar agendamento",
                    color = MarromEscuro,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 16.sp
                )
            },
            text = {
                Text(
                    "Deseja cancelar o agendamento do dia ${agendamento.data} às ${agendamento.horario}?",
                    color = MarromMedio,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarDialogCancelar = null
                        scope.launch {
                            try {
                                val token = "Bearer ${SessionManager.getToken()}"
                                val response = Api.service.cancelAppointment(token, agendamento.id)
                                if (response.isSuccessful) {
                                    mostrarSnackbar = "Agendamento cancelado!"
                                    refreshKey++
                                } else {
                                    mostrarSnackbar = "Erro ao cancelar (${response.code()})"
                                }
                            } catch (e: Exception) {
                                mostrarSnackbar = "Erro de conexão ao cancelar"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Vermelho),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(stringResource(R.string.cancelar), color = BrancoQuente, fontSize = 13.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogCancelar = null }) {
                    Text(stringResource(R.string.voltar), color = MarromMedio, fontSize = 13.sp)
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(BrancoQuente)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .shadow(16.dp, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MarromEscuro, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                stringResource(R.string.meus_agendamentos),
                color = BrancoQuente,
                fontFamily = FrauncesFontFamily,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
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
            if (mostrarSnackbar.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth()
                        .background(MarromMedio, RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        mostrarSnackbar,
                        color = BrancoQuente,
                        fontSize = 13.sp,
                        fontFamily = FrauncesFontFamily
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                "Seus agendamentos",
                color = Dourado,
                fontFamily = FrauncesFontFamily,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            when {
                carregando -> CircularProgressIndicator(color = MarromEscuro)
                agendamentos.isEmpty() -> {
                    Text(
                        "Nenhum agendamento encontrado.",
                        color = MarromMedio,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onHomeClick,
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MarromEscuro),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "Fazer um agendamento",
                            color = BrancoQuente,
                            fontFamily = FrauncesFontFamily,
                            fontSize = 14.sp
                        )
                    }
                }
                else -> {
                    agendamentos.forEach { agendamento ->
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .fillMaxWidth()
                                .background(MarromEscuro, RoundedCornerShape(12.dp))
                                .padding(16.dp)
                        ) {
                            Text(
                                agendamento.servico,
                                color = BrancoQuente,
                                fontFamily = FrauncesFontFamily,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "${agendamento.data} às ${agendamento.horario}",
                                color = BegeMedio,
                                fontSize = 12.sp
                            )
                            Text(
                                agendamento.status,
                                color = when (agendamento.status) {
                                    "Confirmado" -> Dourado
                                    "Cancelado" -> Vermelho
                                    else -> BegeMedio
                                },
                                fontFamily = FrauncesFontFamily,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (agendamento.status == "Confirmado") {
                                    Button(
                                        onClick = { mostrarDialogCancelar = agendamento },
                                        modifier = Modifier.height(32.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Vermelho),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 16.dp)
                                    ) {
                                        Text(
                                            stringResource(R.string.cancelar),
                                            color = BrancoQuente,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                                if (agendamento.status == "Concluído" || agendamento.status == "Cancelado") {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = onReagendarClick,
                                        modifier = Modifier.height(32.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = MarromMedio),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 16.dp)
                                    ) {
                                        Text(
                                            stringResource(R.string.reagendar),
                                            color = BrancoQuente,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onHomeClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MarromEscuro)
                ) {
                    Text("Ir para Home", fontFamily = FrauncesFontFamily, fontSize = 13.sp)
                }
                TextButton(onClick = onVoltarClick) {
                    Text(
                        stringResource(R.string.voltar),
                        color = MarromMedio,
                        fontFamily = FrauncesFontFamily,
                        fontSize = 13.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}