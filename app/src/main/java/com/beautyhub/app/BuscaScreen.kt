package com.beautyhub.app

import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beautyhub.app.ui.theme.*

data class Salao(
    val inicial: String,
    val nome: String,
    val categorias: String,
    val distancia: String,
    val avaliacao: String,
    val cidade: String
)

@Composable
fun BuscaScreen(
    onAvancarClick: (String, String) -> Unit = { _, _ -> },
    onVoltarClick: () -> Unit = {}
) {
    val categorias = listOf("Todos", "Cabelo", "Unhas", "Estetica", "Massagem")
    val cidades = listOf("Todas", "São Paulo", "Rio de Janeiro", "Belo Horizonte", "Brasília")

    var categoriaSelecionada by remember { mutableStateOf("Todos") }
    var cidadeSelecionada by remember { mutableStateOf("Todas") }
    var busca by remember { mutableStateOf("") }
    var salaoSelecionado by remember { mutableStateOf<Salao?>(null) }

    val email = SessionManager.getEmail() ?: ""
    val nomeUsuario = email.substringBefore("@").replaceFirstChar { it.uppercase() }

    val saloes = listOf(
        Salao("B", "Studio Bella", "Cabelo • Unhas", "0,8km", "4.9", "São Paulo"),
        Salao("M", "Maquiart Studio", "Maquiagem • Estetica", "1,2km", "4.7", "Rio de Janeiro"),
        Salao("S", "Spa Serenidade", "Massagem • Relaxamento", "2,1km", "4.8", "São Paulo"),
        Salao("G", "Glamour Studio", "Cabelo • Estetica", "3,0km", "4.6", "Belo Horizonte"),
        Salao("N", "Nails & Co", "Unhas • Massagem", "1,5km", "4.5", "Brasília")
    )

    val saloesFiltrados = saloes.filter { salao ->
        (busca.isEmpty() || salao.nome.contains(busca, ignoreCase = true) ||
                salao.categorias.contains(busca, ignoreCase = true)) &&
                (categoriaSelecionada == "Todos" ||
                        salao.categorias.contains(categoriaSelecionada, ignoreCase = true)) &&
                (cidadeSelecionada == "Todas" || salao.cidade == cidadeSelecionada)
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
                text = stringResource(R.string.buscar_estabelecimento),
                color = BrancoQuente,
                fontFamily = FrauncesFontFamily,
                fontSize = 20.sp,
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
                text = "${stringResource(R.string.bem_vindo)} $nomeUsuario!",
                color = Dourado,
                fontFamily = FrauncesFontFamily,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = busca,
                onValueChange = { busca = it },
                placeholder = { Text(stringResource(R.string.buscar_estabelecimento), color = BegeMedio, fontSize = 13.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MarromEscuro,
                    unfocusedBorderColor = BegeMedio,
                    focusedContainerColor = BegeClaro,
                    unfocusedContainerColor = BegeClaro
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.localidade),
                color = MarromEscuro,
                fontFamily = FrauncesFontFamily,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                cidades.forEach { cidade ->
                    val selecionado = cidadeSelecionada == cidade
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (selecionado) Dourado else BegeClaro,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { cidadeSelecionada = cidade }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = cidade,
                            color = if (selecionado) BrancoQuente else MarromMedio,
                            fontSize = 11.sp,
                            fontFamily = FrauncesFontFamily
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.categoria),
                color = MarromEscuro,
                fontFamily = FrauncesFontFamily,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categorias.forEach { categoria ->
                    val selecionado = categoriaSelecionada == categoria
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (selecionado) MarromEscuro else BegeClaro,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { categoriaSelecionada = categoria }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = categoria,
                            color = if (selecionado) BrancoQuente else MarromMedio,
                            fontSize = 11.sp,
                            fontFamily = FrauncesFontFamily
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.estabelecimentos_proximos),
                color = MarromEscuro,
                fontFamily = FrauncesFontFamily,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (saloesFiltrados.isEmpty()) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(R.string.nenhum_resultado),
                    color = MarromMedio,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                saloesFiltrados.forEach { salao ->
                    SalaoCard(
                        salao = salao,
                        selecionado = salaoSelecionado?.nome == salao.nome,
                        onSelecionar = { salaoSelecionado = salao }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
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
                    onClick = {
                        categoriaSelecionada = "Todos"
                        cidadeSelecionada = "Todas"
                        busca = ""
                        salaoSelecionado = null
                    },
                    modifier = Modifier.weight(1f).height(46.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MarromEscuro)
                ) {
                    Text(stringResource(R.string.limpar), fontFamily = FrauncesFontFamily, fontSize = 13.sp)
                }
                Button(
                    onClick = {
                        if (salaoSelecionado != null) {
                            onAvancarClick(
                                salaoSelecionado!!.nome,
                                salaoSelecionado!!.categorias
                            )
                        }
                    },
                    modifier = Modifier.weight(1f).height(46.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (salaoSelecionado != null) MarromEscuro else BegeMedio
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(stringResource(R.string.avancar), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onVoltarClick) {
                Text(stringResource(R.string.voltar), color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SalaoCard(
    salao: Salao,
    selecionado: Boolean = false,
    onSelecionar: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth()
            .background(
                color = if (selecionado) MarromEscuro else BegeClaro,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onSelecionar() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = if (selecionado) BrancoQuente.copy(alpha = 0.2f) else MarromEscuro,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = salao.inicial,
                color = Dourado,
                fontFamily = FrauncesFontFamily,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = salao.nome,
                color = if (selecionado) BrancoQuente else MarromEscuro,
                fontFamily = FrauncesFontFamily,
                fontSize = 13.sp
            )
            Text(
                text = "${salao.categorias} • ${salao.distancia}",
                color = if (selecionado) BegeMedio else MarromMedio,
                fontSize = 11.sp
            )
            Text(
                text = salao.cidade,
                color = if (selecionado) BegeMedio else MarromMedio,
                fontSize = 11.sp
            )
        }
        Text(text = "★ ${salao.avaliacao}", color = Dourado, fontSize = 11.sp)
    }
}