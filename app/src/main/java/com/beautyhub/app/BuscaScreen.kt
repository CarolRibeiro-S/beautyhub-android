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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beautyhub.app.ui.theme.*

data class Salao(
    val inicial: String,
    val nome: String,
    val categorias: String,
    val distancia: String,
    val avaliacao: String
)

@Composable
fun BuscaScreen(
    onVoltarClick: () -> Unit = {}
) {
    val categorias = listOf("Todos", "Cabelo", "Unhas", "Estetica", "Massagem")
    var categoriaSelecionada by remember { mutableStateOf("Todos") }
    var busca by remember { mutableStateOf("") }

    val saloes = listOf(
        Salao("B", "Studio Bella", "Cabelo • Unhas", "0,8km", "4.9"),
        Salao("M", "Maquiart Studio", "Maquiagem • Estetica", "1,2km", "4.7"),
        Salao("S", "Spa Serenidade", "Massagem • Relaxamento", "2,1km", "4.8")
    )

    val saloesFiltrados = saloes.filter { salao ->
        (busca.isEmpty() || salao.nome.contains(busca, ignoreCase = true) ||
                salao.categorias.contains(busca, ignoreCase = true)) &&
                (categoriaSelecionada == "Todos" ||
                        salao.categorias.contains(categoriaSelecionada, ignoreCase = true))
    }

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
                text = "Buscar Servico",
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
            // Campo de busca
            OutlinedTextField(
                value = busca,
                onValueChange = { busca = it },
                placeholder = { Text("Buscar salao ou servico...", color = BegeMedio, fontSize = 13.sp) },
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

            // Filtros de categoria
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
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
                text = "Saloes Proximos",
                color = MarromEscuro,
                fontFamily = FrauncesFontFamily,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de saloes
            if (saloesFiltrados.isEmpty()) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Nenhum resultado encontrado",
                    color = MarromMedio,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                saloesFiltrados.forEach { salao ->
                    SalaoCard(salao = salao)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botoes
            Row(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { categoriaSelecionada = "Todos"; busca = "" },
                    modifier = Modifier.weight(1f).height(46.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MarromEscuro)
                ) {
                    Text("Limpar filtros", fontFamily = FrauncesFontFamily, fontSize = 13.sp)
                }
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(46.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MarromEscuro),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Ver todos", color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
                }
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
fun SalaoCard(salao: Salao) {
    Row(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth()
            .background(BegeClaro, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(MarromEscuro, RoundedCornerShape(8.dp)),
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
            Text(text = salao.nome, color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
            Text(text = "${salao.categorias} • ${salao.distancia}", color = MarromMedio, fontSize = 11.sp)
        }
        Text(text = "★ ${salao.avaliacao}", color = Dourado, fontSize = 11.sp)
    }
}