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

data class AgendamentoItem(
    val servico: String,
    val profissional: String,
    val data: String,
    val horario: String,
    var status: String
)

@Composable
fun MeusAgendamentosScreen(
    onVoltarClick: () -> Unit = {},
    onReagendarClick: () -> Unit = {}
) {
    var agendamentos by remember {
        mutableStateOf(
            listOf(
                AgendamentoItem("Hidratação capilar", "Ana Souza", "21/02", "14:00", "Confirmado"),
                AgendamentoItem("Manicure", "Ana Souza", "17/02", "17:00", "Concluído"),
                AgendamentoItem("Limpeza de pele", "Ana Souza", "05/02", "09:00", "Concluído")
            )
        )
    }

    var mostrarDialogCancelar by remember { mutableStateOf<AgendamentoItem?>(null) }
    var mostrarSnackbar by remember { mutableStateOf("") }

    // Dialog de confirmação de cancelamento
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
                    "Deseja cancelar ${agendamento.servico} do dia ${agendamento.data} às ${agendamento.horario}?",
                    color = MarromMedio,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        agendamentos = agendamentos.map {
                            if (it == agendamento) it.copy(status = "Cancelado") else it
                        }
                        mostrarDialogCancelar = null
                        mostrarSnackbar = "Agendamento cancelado!"
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Vermelho),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancelar", color = BrancoQuente, fontSize = 13.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogCancelar = null }) {
                    Text("Voltar", color = MarromMedio, fontSize = 13.sp)
                }
            }
        )
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
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                )
                .background(
                    color = MarromEscuro,
                    shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = "Meus Agendamentos",
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
                        text = mostrarSnackbar,
                        color = BrancoQuente,
                        fontSize = 13.sp,
                        fontFamily = FrauncesFontFamily
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = "Veja seus horários marcados e passados",
                color = Dourado,
                fontFamily = FrauncesFontFamily,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            agendamentos.forEach { agendamento ->
                Column(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth()
                        .background(MarromEscuro, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = agendamento.servico,
                        color = BrancoQuente,
                        fontFamily = FrauncesFontFamily,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Com ${agendamento.profissional}",
                        color = BegeMedio,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "${agendamento.data} - ${agendamento.horario}",
                        color = BegeMedio,
                        fontSize = 12.sp
                    )
                    Text(
                        text = agendamento.status,
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
                                Text("CANCELAR", color = BrancoQuente, fontSize = 11.sp)
                            }
                        }

                        if (agendamento.status == "Concluído" || agendamento.status == "Cancelado") {
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { onReagendarClick() },
                                modifier = Modifier.height(32.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MarromMedio),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp)
                            ) {
                                Text("REAGENDAR", color = BrancoQuente, fontSize = 11.sp)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onVoltarClick) {
                Text(
                    text = "Voltar",
                    color = MarromMedio,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}