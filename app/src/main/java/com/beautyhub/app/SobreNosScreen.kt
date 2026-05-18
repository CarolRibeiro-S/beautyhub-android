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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.beautyhub.app.ui.theme.*

@Composable
fun SobreNosScreen(
    onVoltarClick: () -> Unit = {}
) {
    var showFaleConoscoModal by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(BrancoQuente)) {
        Box(
            modifier = Modifier.fillMaxWidth().height(220.dp)
                .shadow(16.dp, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MarromEscuro, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = stringResource(R.string.sobre_nos_titulo),
                color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 22.sp,
                modifier = Modifier.padding(top = 60.dp)
            )
        }

        Box(modifier = Modifier.align(Alignment.TopCenter).offset(y = 123.dp).size(150.dp).clip(RoundedCornerShape(75.dp)).background(MarromEscuro)) {
            Image(painter = painterResource(id = R.drawable.logo_beautyhub), contentDescription = "Logo BeautyHub", modifier = Modifier.fillMaxSize())
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 290.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.sobre_nos_slogan),
                color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp,
                textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            InfoCard(
                titulo = stringResource(R.string.nossa_missao_titulo),
                texto = stringResource(R.string.nossa_missao_texto)
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoCard(
                titulo = stringResource(R.string.nossa_visao_titulo),
                texto = stringResource(R.string.nossa_visao_texto)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()
                    .background(MarromEscuro, RoundedCornerShape(12.dp)).padding(16.dp)
            ) {
                Text(text = stringResource(R.string.nossos_valores_titulo), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 15.sp)
                Spacer(modifier = Modifier.height(8.dp))
                listOf(
                    stringResource(R.string.valor_acessibilidade),
                    stringResource(R.string.valor_confianca),
                    stringResource(R.string.valor_valorizacao),
                    stringResource(R.string.valor_inovacao)
                ).forEach { valor ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                        Box(modifier = Modifier.size(6.dp).background(Dourado, RoundedCornerShape(3.dp)))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = valor, color = BegeMedio, fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { showFaleConoscoModal = true }, // Alterado para abrir o modal
                modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MarromEscuro),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(R.string.fale_conosco), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onVoltarClick) {
                Text(stringResource(R.string.voltar), color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Modal Fale Conosco
    if (showFaleConoscoModal) {
        Dialog(
            onDismissRequest = { showFaleConoscoModal = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)) // Fundo semi-transparente
                    .clickable(onClick = { showFaleConoscoModal = false }), // Clicar fora fecha o modal
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.85f) // Largura do modal
                        .padding(16.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = BrancoQuente)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.fale_conosco_titulo_modal), // Você precisará adicionar esta string
                            color = MarromEscuro,
                            fontFamily = FrauncesFontFamily,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Instagram
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_instagram), // Certifique-se de ter este drawable
                                contentDescription = "Instagram",
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "@beautyhub",
                                color = MarromMedio,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        // WhatsApp
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_whatsapp), // Certifique-se de ter este drawable
                                contentDescription = "WhatsApp",
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "+55 (11) 98765-4321", // Número fictício
                                color = MarromMedio,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { showFaleConoscoModal = false },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MarromEscuro),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = stringResource(R.string.fechar), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 16.sp) // Você precisará adicionar esta string
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoCard(titulo: String, texto: String) {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()
            .background(BegeClaro, RoundedCornerShape(12.dp)).padding(16.dp)
    ) {
        Text(text = titulo, color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = texto, color = MarromMedio, fontSize = 13.sp, lineHeight = 18.sp)
    }
}