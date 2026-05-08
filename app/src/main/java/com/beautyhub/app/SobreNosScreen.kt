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
fun SobreNosScreen(
    onVoltarClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BrancoQuente)
    ) {
        // Header
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
                text = "Sobre Nos",
                color = BrancoQuente,
                fontFamily = FrauncesFontFamily,
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 60.dp)
            )
        }

        // Logo
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

        // Conteudo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 290.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Beleza e bem-estar ao seu alcance",
                color = MarromMedio,
                fontFamily = FrauncesFontFamily,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nossa Missao
            InfoCard(
                titulo = "Nossa Missao",
                texto = "Conectar pessoas a servicos de beleza e bem-estar de forma simples, rapida e acessivel, valorizando cada momento de autocuidado."
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Nossa Visao
            InfoCard(
                titulo = "Nossa Visao",
                texto = "Ser a principal plataforma de agendamento de servicos de beleza do Brasil, promovendo conexao entre profissionais e clientes."
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Nossos Valores
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
                    .background(MarromEscuro, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Nossos Valores",
                    color = BrancoQuente,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                listOf(
                    "Acessibilidade e inclusao",
                    "Confianca e transparencia",
                    "Valorizacao do profissional",
                    "Inovacao continua"
                ).forEach { valor ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(Dourado, RoundedCornerShape(3.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = valor, color = BegeMedio, fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botao Fale Conosco
            Button(
                onClick = { },
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MarromEscuro),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "FALE CONOSCO",
                    color = BrancoQuente,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onVoltarClick) {
                Text("Voltar", color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun InfoCard(titulo: String, texto: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth()
            .background(BegeClaro, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = titulo,
            color = MarromEscuro,
            fontFamily = FrauncesFontFamily,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = texto,
            color = MarromMedio,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
    }
}