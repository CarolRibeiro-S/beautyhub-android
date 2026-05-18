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
    val servicoId: Int = 0,
    val servico: String,
    val servicoPreco: Double = 0.0,
    val servicoDuracao: Int = 60,
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
    onReagendarClick: (Int, String, Double, Int) -> Unit = { _, _, _, _ -> },
    onHomeClick: () -> Unit = {}
) {
    var agendamentos by remember { mutableStateOf<List<AgendamentoItem>>(emptyList()) }
    var carregando by remember { mutableStateOf(true) }
    var mostrarDialogCancelar by remember { mutableStateOf<AgendamentoItem?>(null) }
    var mostrarSnackbar by remember { mutableStateOf("") }
    var refreshKey by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    val statusConfirmado = stringResource(R.string.status_confirmado)
    val statusCancelado = stringResource(R.string.status_cancelado)
    val statusConcluido = stringResource(R.string.status_concluido)
    val erroCancelar = stringResource(R.string.erro_conexao)
    val agendamentoCancelado = stringResource(R.string.agendamento_cancelado)
    val erroCarregar = stringResource(R.string.erro_carregar)
    val erroConexao = stringResource(R.string.erro_conexao)

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
                    val nomeServico = apt.servicoNome ?: apt.service?.nome ?: "Serviço"
                    val preco = apt.servicoPreco ?: apt.service?.preco ?: 0.0
                    val duracao = apt.service?.duracaoMinutos ?: 60
                    val srvId = apt.serviceId ?: apt.service?.id ?: 0
                    AgendamentoItem(
                        id = apt.id.toLong(),
                        servicoId = srvId,
                        servico = nomeServico,
                        servicoPreco = preco,
                        servicoDuracao = duracao,
                        data = formatarData(dataIso),
                        horario = horario,
                        status = when (apt.status) {
                            "SCHEDULED" -> statusConfirmado
                            "CANCELLED" -> statusCancelado
                            "COMPLETED" -> statusConcluido
                            else -> apt.status
                        }
                    )
                }
            } else {
                mostrarSnackbar = erroCarregar
            }
        } catch (e: Exception) {
            mostrarSnackbar = erroConexao
        }
        carregando = false
    }

    mostrarDialogCancelar?.let { agendamento ->
        AlertDialog(
            onDismissRequest = { mostrarDialogCancelar = null },
            containerColor = BrancoQuente,
            title = { Text(stringResource(R.string.cancelar_titulo), color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 16.sp) },
            text = { Text(stringResource(R.string.cancelar_pergunta, agendamento.data, agendamento.horario), color = MarromMedio, fontSize = 13.sp) },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarDialogCancelar = null
                        scope.launch {
                            try {
                                val token = "Bearer ${SessionManager.getToken()}"
                                val response = Api.service.cancelAppointment(token, agendamento.id)
                                if (response.isSuccessful) {
                                    mostrarSnackbar = agendamentoCancelado
                                    refreshKey++
                                } else {
                                    mostrarSnackbar = erroCancelar
                                }
                            } catch (e: Exception) {
                                mostrarSnackbar = erroCancelar
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Vermelho),
                    shape = RoundedCornerShape(8.dp)
                ) { Text(stringResource(R.string.cancelar), color = BrancoQuente, fontSize = 13.sp) }
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
            modifier = Modifier.fillMaxWidth().height(220.dp)
                .shadow(16.dp, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MarromEscuro, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(stringResource(R.string.meus_agendamentos), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 22.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 60.dp))
        }

        Box(modifier = Modifier.align(Alignment.TopCenter).offset(y = 123.dp).size(150.dp).clip(RoundedCornerShape(75.dp)).background(MarromEscuro)) {
            Image(painter = painterResource(id = R.drawable.logo_beautyhub), contentDescription = "Logo BeautyHub", modifier = Modifier.fillMaxSize())
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 290.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (mostrarSnackbar.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth().background(MarromMedio, RoundedCornerShape(8.dp)).padding(12.dp)) {
                    Text(mostrarSnackbar, color = BrancoQuente, fontSize = 13.sp, fontFamily = FrauncesFontFamily)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(stringResource(R.string.seus_agendamentos), color = Dourado, fontFamily = FrauncesFontFamily, fontSize = 14.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp))
            Spacer(modifier = Modifier.height(24.dp))

            when {
                carregando -> CircularProgressIndicator(color = MarromEscuro)
                agendamentos.isEmpty() -> {
                    Text(stringResource(R.string.nenhum_agendamento), color = MarromMedio, fontSize = 13.sp, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onHomeClick, modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = MarromEscuro), shape = RoundedCornerShape(8.dp)) {
                        Text(stringResource(R.string.fazer_agendamento), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 14.sp)
                    }
                }
                else -> {
                    agendamentos.forEach { agendamento ->
                        Column(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth().background(MarromEscuro, RoundedCornerShape(12.dp)).padding(16.dp)) {
                            Text(agendamento.servico, color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 15.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("${agendamento.data} às ${agendamento.horario}", color = BegeMedio, fontSize = 12.sp)
                            Text(
                                agendamento.status,
                                color = when (agendamento.status) {
                                    statusConfirmado -> Dourado
                                    statusCancelado -> Vermelho
                                    else -> BegeMedio
                                },
                                fontFamily = FrauncesFontFamily, fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                                if (agendamento.status == statusConfirmado) {
                                    Button(
                                        onClick = { mostrarDialogCancelar = agendamento },
                                        modifier = Modifier.height(32.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Vermelho),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 16.dp)
                                    ) { Text(stringResource(R.string.cancelar), color = BrancoQuente, fontSize = 11.sp) }
                                }
                                if (agendamento.status == statusConcluido || agendamento.status == statusCancelado) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = { onReagendarClick(agendamento.servicoId, agendamento.servico, agendamento.servicoPreco, agendamento.servicoDuracao) },
                                        modifier = Modifier.height(32.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = MarromMedio),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 16.dp)
                                    ) { Text(stringResource(R.string.reagendar), color = BrancoQuente, fontSize = 11.sp) }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = onHomeClick, modifier = Modifier.weight(1f).height(46.dp), shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.outlinedButtonColors(contentColor = MarromEscuro)) {
                    Text(stringResource(R.string.ir_para_home), fontFamily = FrauncesFontFamily, fontSize = 13.sp)
                }
                TextButton(onClick = onVoltarClick) {
                    Text(stringResource(R.string.voltar), color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}