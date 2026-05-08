package com.beautyhub.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beautyhub.app.ui.theme.*

data class Servico(
    val nome: String,
    val descricao: String,
    val duracao: String,
    val preco: String
)

data class Agendamento(
    val servico: String,
    val profissional: String,
    val data: String,
    val horario: String,
    val status: String
)

@Composable
fun ServicoCard(servico: Servico) {
    var selecionado by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth()
            .background(
                color = MarromEscuro,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selecionado,
            onClick = { selecionado = !selecionado },
            colors = RadioButtonDefaults.colors(
                selectedColor = Dourado,
                unselectedColor = BegeMedio
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = servico.nome,
                color = BrancoQuente,
                fontFamily = FrauncesFontFamily,
                fontSize = 15.sp
            )
            Text(
                text = "Descrição: ${servico.descricao}",
                color = BegeMedio,
                fontSize = 12.sp
            )
            Text(
                text = "Info: ${servico.duracao} - ${servico.preco}",
                color = BegeMedio,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun AgendamentoCard(agendamento: Agendamento) {
    val confirmado = agendamento.status == "Confirmado"

    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth()
            .background(
                color = MarromEscuro,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            )
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
            color = if (confirmado) Dourado else BegeMedio,
            fontFamily = FrauncesFontFamily,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .align(Alignment.End)
                .height(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (confirmado) Vermelho else MarromMedio
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            Text(
                text = if (confirmado) "CANCELAR" else "REAGENDAR",
                color = BrancoQuente,
                fontSize = 11.sp
            )
        }
    }
}