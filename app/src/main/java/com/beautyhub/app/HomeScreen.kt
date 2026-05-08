package com.beautyhub.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
fun HomeScreen(
    nomeUsuario: String = "Sara",
    onAvancarClick: () -> Unit = {},
    onMeusAgendamentosClick: () -> Unit = {},
    onCartaoFidelidadeClick: () -> Unit = {},
    onBuscaClick: () -> Unit = {},
    onSobreNosClick: () -> Unit = {},
    onSairClick: () -> Unit = {}
) {
    val servicosApi = remember {
        listOf(
            ServicoApi(1, "Hidratação capilar", "Tratamento nutritivo para fios ressecados", 60, 120.0, null),
            ServicoApi(2, "Manicure", "Cuidado completo para suas unhas", 45, 60.0, null),
            ServicoApi(3, "Limpeza de pele", "Limpeza profunda e revitalizante", 90, 150.0, null),
            ServicoApi(4, "Massagem relaxante", "Massagem corporal completa", 60, 180.0, null)
        )
    }
    var servicoSelecionado by remember { mutableStateOf<ServicoApi?>(null) }
    var menuAberto by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BrancoQuente)
    ) {
        // Header marrom
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 48.dp)
            ) {
                Text(
                    text = "Escolha um serviço para",
                    color = BrancoQuente,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "agendar seu horário na",
                    color = BrancoQuente,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }

            // Botão menu hamburguer
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 48.dp, end = 16.dp)
            ) {
                IconButton(onClick = { menuAberto = true }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = BrancoQuente,
                        modifier = Modifier.size(28.dp)
                    )
                }

                DropdownMenu(
                    expanded = menuAberto,
                    onDismissRequest = { menuAberto = false },
                    modifier = Modifier.background(BrancoQuente)
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                "Meus Agendamentos",
                                color = MarromEscuro,
                                fontFamily = FrauncesFontFamily,
                                fontSize = 14.sp
                            )
                        },
                        onClick = {
                            menuAberto = false
                            onMeusAgendamentosClick()
                        }
                    )
                    HorizontalDivider(color = BegeMedio)
                    DropdownMenuItem(
                        text = {
                            Text(
                                "Cartão Fidelidade",
                                color = Dourado,
                                fontFamily = FrauncesFontFamily,
                                fontSize = 14.sp
                            )
                        },
                        onClick = {
                            menuAberto = false
                            onCartaoFidelidadeClick()
                        }
                    )
                    HorizontalDivider(color = BegeMedio)
                    DropdownMenuItem(
                        text = {
                            Text(
                                "Buscar Serviços",
                                color = MarromEscuro,
                                fontFamily = FrauncesFontFamily,
                                fontSize = 14.sp
                            )
                        },
                        onClick = {
                            menuAberto = false
                            onBuscaClick()
                        }
                    )
                    HorizontalDivider(color = BegeMedio)
                    DropdownMenuItem(
                        text = {
                            Text(
                                "Sobre Nós",
                                color = MarromEscuro,
                                fontFamily = FrauncesFontFamily,
                                fontSize = 14.sp
                            )
                        },
                        onClick = {
                            menuAberto = false
                            onSobreNosClick()
                        }
                    )
                    HorizontalDivider(color = BegeMedio)
                    DropdownMenuItem(
                        text = {
                            Text(
                                "Sair",
                                color = Vermelho,
                                fontFamily = FrauncesFontFamily,
                                fontSize = 14.sp
                            )
                        },
                        onClick = {
                            menuAberto = false
                            SessionManager.logout()
                            onSairClick()
                        }
                    )
                }
            }
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
                text = "Bem-vinda, $nomeUsuario",
                color = Dourado,
                fontFamily = FrauncesFontFamily,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            servicosApi.forEach { servico ->
                ServicoApiCard(
                    servico = servico,
                    selecionado = servicoSelecionado?.id == servico.id,
                    onSelecionar = { servicoSelecionado = servico }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { if (servicoSelecionado != null) onAvancarClick() },
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (servicoSelecionado != null) MarromEscuro else BegeMedio
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "AVANÇAR",
                    color = BrancoQuente,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ServicoApiCard(
    servico: ServicoApi,
    selecionado: Boolean,
    onSelecionar: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth()
            .background(
                color = MarromEscuro,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selecionado,
            onClick = onSelecionar,
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
                text = "Duração: ${servico.duracaoMinutos} min",
                color = BegeMedio,
                fontSize = 12.sp
            )
            Text(
                text = "Valor: R$ ${"%.2f".format(servico.preco)}",
                color = BegeMedio,
                fontSize = 12.sp
            )
        }
    }
}