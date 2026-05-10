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

val servicosPorCategoria = mapOf(
    "Cabelo" to listOf(
        ServicoApi(1, "Hidratação capilar", "Tratamento nutritivo para fios ressecados", 60, 120.0, null),
        ServicoApi(2, "Corte de cabelo", "Corte moderno e personalizado", 45, 80.0, null),
        ServicoApi(3, "Coloração", "Coloração completa com tinta profissional", 120, 200.0, null),
        ServicoApi(4, "Escova progressiva", "Alisamento duradouro", 180, 350.0, null)
    ),
    "Unhas" to listOf(
        ServicoApi(5, "Manicure", "Cuidado completo para suas unhas", 45, 60.0, null),
        ServicoApi(6, "Pedicure", "Cuidado completo para os pés", 60, 70.0, null),
        ServicoApi(7, "Gel nas unhas", "Unhas em gel duradouras", 90, 150.0, null),
        ServicoApi(8, "Nail art", "Decoração artística nas unhas", 60, 100.0, null)
    ),
    "Estetica" to listOf(
        ServicoApi(9, "Limpeza de pele", "Limpeza profunda e revitalizante", 90, 150.0, null),
        ServicoApi(10, "Peeling facial", "Renovação celular da pele", 60, 180.0, null),
        ServicoApi(11, "Drenagem linfática", "Redução de inchaço e toxinas", 60, 200.0, null),
        ServicoApi(12, "Design de sobrancelha", "Modelagem perfeita das sobrancelhas", 30, 50.0, null)
    ),
    "Massagem" to listOf(
        ServicoApi(13, "Massagem relaxante", "Massagem corporal completa", 60, 180.0, null),
        ServicoApi(14, "Massagem desportiva", "Alívio de tensões musculares", 60, 200.0, null),
        ServicoApi(15, "Massagem pedras quentes", "Relaxamento profundo com pedras", 90, 250.0, null),
        ServicoApi(16, "Reflexologia", "Massagem nos pés para equilíbrio do corpo", 45, 150.0, null)
    ),
    "Maquiagem" to listOf(
        ServicoApi(17, "Maquiagem social", "Make completa para eventos", 60, 150.0, null),
        ServicoApi(18, "Maquiagem noiva", "Make especial para noivas", 120, 400.0, null),
        ServicoApi(19, "Maquiagem artística", "Make criativa e temática", 90, 250.0, null)
    )
)

fun getServicosPorSalao(categorias: String): List<ServicoApi> {
    val resultado = mutableListOf<ServicoApi>()
    servicosPorCategoria.forEach { (categoria, servicos) ->
        if (categorias.contains(categoria, ignoreCase = true)) {
            resultado.addAll(servicos)
        }
    }
    return if (resultado.isEmpty()) servicosPorCategoria.values.flatten() else resultado
}

@Composable
fun HomeScreen(
    nomeUsuario: String = "Sara",
    nomeSalao: String = "",
    categoriasSalao: String = "",
    onAvancarClick: () -> Unit = {},
    onMeusAgendamentosClick: () -> Unit = {},
    onCartaoFidelidadeClick: () -> Unit = {},
    onBuscaClick: () -> Unit = {},
    onSobreNosClick: () -> Unit = {},
    onSairClick: () -> Unit = {}
) {
    val servicosApi = remember(categoriasSalao) {
        getServicosPorSalao(categoriasSalao)
    }
    var servicoSelecionado by remember { mutableStateOf<ServicoApi?>(null) }
    var menuAberto by remember { mutableStateOf(false) }

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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 48.dp)
            ) {
                Text(
                    text = if (nomeSalao.isNotEmpty()) "Serviços do $nomeSalao" else "Escolha um serviço para",
                    color = BrancoQuente,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                if (nomeSalao.isEmpty()) {
                    Text(
                        text = "agendar seu horário na",
                        color = BrancoQuente,
                        fontFamily = FrauncesFontFamily,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

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
                        text = { Text("Meus Agendamentos", color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 14.sp) },
                        onClick = { menuAberto = false; onMeusAgendamentosClick() }
                    )
                    HorizontalDivider(color = BegeMedio)
                    DropdownMenuItem(
                        text = { Text("Cartão Fidelidade", color = Dourado, fontFamily = FrauncesFontFamily, fontSize = 14.sp) },
                        onClick = { menuAberto = false; onCartaoFidelidadeClick() }
                    )
                    HorizontalDivider(color = BegeMedio)
                    DropdownMenuItem(
                        text = { Text("Buscar Serviços", color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 14.sp) },
                        onClick = { menuAberto = false; onBuscaClick() }
                    )
                    HorizontalDivider(color = BegeMedio)
                    DropdownMenuItem(
                        text = { Text("Sobre Nós", color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 14.sp) },
                        onClick = { menuAberto = false; onSobreNosClick() }
                    )
                    HorizontalDivider(color = BegeMedio)
                    DropdownMenuItem(
                        text = { Text("Sair", color = Vermelho, fontFamily = FrauncesFontFamily, fontSize = 14.sp) },
                        onClick = { menuAberto = false; SessionManager.logout(); onSairClick() }
                    )
                }
            }
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