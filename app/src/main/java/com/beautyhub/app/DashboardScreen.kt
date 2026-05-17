package com.beautyhub.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beautyhub.app.ui.theme.*

@Composable
fun DashboardScreen(
    onAgendarClick: () -> Unit = {},
    onMeusAgendamentosClick: () -> Unit = {},
    onCartaoFidelidadeClick: () -> Unit = {},
    onSobreNosClick: () -> Unit = {},
    onSairClick: () -> Unit = {}
) {
    val nome = SessionManager.getName()?.split(" ")?.firstOrNull()
        ?: (SessionManager.getEmail() ?: "").substringBefore("@").replaceFirstChar { it.uppercase() }

    Box(modifier = Modifier.fillMaxSize().background(BrancoQuente)) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .shadow(16.dp, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MarromEscuro, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(45.dp))
                        .background(BrancoQuente)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_beautyhub),
                        contentDescription = "Logo",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "${stringResource(R.string.bem_vindo)} $nome!",
                    color = BrancoQuente,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 20.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 240.dp, start = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.o_que_deseja),
                color = MarromEscuro,
                fontFamily = FrauncesFontFamily,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            DashboardCard(
                icon = Icons.Filled.DateRange,
                titulo = stringResource(R.string.agendar_servico),
                subtitulo = stringResource(R.string.busque_salao),
                onClick = onAgendarClick
            )
            DashboardCard(
                icon = Icons.Filled.List,
                titulo = stringResource(R.string.meus_agendamentos),
                subtitulo = stringResource(R.string.gerencie_agendamentos),
                onClick = onMeusAgendamentosClick
            )
            DashboardCard(
                icon = Icons.Filled.Star,
                titulo = stringResource(R.string.cartao_fidelidade),
                subtitulo = stringResource(R.string.acompanhe_pontos),
                onClick = onCartaoFidelidadeClick
            )
            DashboardCard(
                icon = Icons.Filled.Info,
                titulo = stringResource(R.string.sobre_nos),
                subtitulo = stringResource(R.string.conheca_beautyhub),
                onClick = onSobreNosClick
            )

            Spacer(modifier = Modifier.weight(1f))

            TextButton(onClick = { SessionManager.logout(); onSairClick() }) {
                Text(
                    stringResource(R.string.sair),
                    color = Vermelho,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DashboardCard(
    icon: ImageVector,
    titulo: String,
    subtitulo: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .background(MarromEscuro, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Dourado, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(titulo, color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 15.sp)
            Text(subtitulo, color = BegeMedio, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null, tint = BegeMedio)
    }
}