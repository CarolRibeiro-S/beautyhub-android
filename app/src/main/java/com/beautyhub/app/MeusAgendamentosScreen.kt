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
fun MeusAgendamentosScreen(
    onVoltarClick: () -> Unit = {}
) {
    val agendamentos = listOf(
        Agendamento("Hidratação capilar", "Ana Souza", "21/02", "14:00", "Confirmado"),
        Agendamento("Hidratação capilar", "Ana Souza", "17/02", "17:00", "Concluído"),
        Agendamento("Hidratação capilar", "Ana Souza", "05/02", "09:00", "Concluído")
    )

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
                AgendamentoCard(agendamento = agendamento)
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