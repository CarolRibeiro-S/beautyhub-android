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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beautyhub.app.ui.theme.*

fun getServicosPorSalao(categorias: String, todos: Map<String, List<ServicoApi>>): List<ServicoApi> {
    val resultado = mutableListOf<ServicoApi>()
    todos.forEach { (categoria, servicos) ->
        if (categorias.contains(categoria, ignoreCase = true)) resultado.addAll(servicos)
    }
    return if (resultado.isEmpty()) todos.values.flatten() else resultado
}

@Composable
fun HomeScreen(
    nomeUsuario: String = "",
    nomeSalao: String = "",
    categoriasSalao: String = "",
    onAvancarClick: (ServicoApi) -> Unit = {},
    onMeusAgendamentosClick: () -> Unit = {},
    onCartaoFidelidadeClick: () -> Unit = {},
    onBuscaClick: () -> Unit = {},
    onSobreNosClick: () -> Unit = {},
    onSairClick: () -> Unit = {},
    onVoltarClick: () -> Unit = {}
) {
    val servicosPorCategoria = mapOf(
        "Cabelo" to listOf(
            ServicoApi(25, stringResource(R.string.srv_hidratacao), stringResource(R.string.srv_hidratacao_desc), 60, 120.0, null),
            ServicoApi(32, stringResource(R.string.srv_corte), stringResource(R.string.srv_corte_desc), 45, 80.0, null),
            ServicoApi(33, stringResource(R.string.srv_coloracao), stringResource(R.string.srv_coloracao_desc), 120, 200.0, null),
            ServicoApi(34, stringResource(R.string.srv_progressiva), stringResource(R.string.srv_progressiva_desc), 180, 350.0, null)
        ),
        "Unhas" to listOf(
            ServicoApi(26, stringResource(R.string.srv_manicure), stringResource(R.string.srv_manicure_desc), 45, 60.0, null),
            ServicoApi(29, stringResource(R.string.srv_pedicure), stringResource(R.string.srv_pedicure_desc), 60, 70.0, null),
            ServicoApi(30, stringResource(R.string.srv_gel), stringResource(R.string.srv_gel_desc), 90, 150.0, null),
            ServicoApi(31, stringResource(R.string.srv_nailart), stringResource(R.string.srv_nailart_desc), 60, 100.0, null)
        ),
        "Estetica" to listOf(
            ServicoApi(27, stringResource(R.string.srv_limpeza), stringResource(R.string.srv_limpeza_desc), 90, 150.0, null),
            ServicoApi(35, stringResource(R.string.srv_peeling), stringResource(R.string.srv_peeling_desc), 60, 180.0, null),
            ServicoApi(36, stringResource(R.string.srv_drenagem), stringResource(R.string.srv_drenagem_desc), 60, 200.0, null),
            ServicoApi(37, stringResource(R.string.srv_sobrancelha), stringResource(R.string.srv_sobrancelha_desc), 30, 50.0, null)
        ),
        "Massagem" to listOf(
            ServicoApi(28, stringResource(R.string.srv_massagem_relax), stringResource(R.string.srv_massagem_relax_desc), 60, 180.0, null),
            ServicoApi(38, stringResource(R.string.srv_massagem_desp), stringResource(R.string.srv_massagem_desp_desc), 60, 200.0, null),
            ServicoApi(39, stringResource(R.string.srv_pedras), stringResource(R.string.srv_pedras_desc), 90, 250.0, null),
            ServicoApi(40, stringResource(R.string.srv_reflexologia), stringResource(R.string.srv_reflexologia_desc), 45, 150.0, null)
        ),
        "Maquiagem" to listOf(
            ServicoApi(41, stringResource(R.string.srv_make_social), stringResource(R.string.srv_make_social_desc), 60, 150.0, null),
            ServicoApi(42, stringResource(R.string.srv_make_noiva), stringResource(R.string.srv_make_noiva_desc), 120, 400.0, null),
            ServicoApi(43, stringResource(R.string.srv_make_artistica), stringResource(R.string.srv_make_artistica_desc), 90, 250.0, null)
        )
    )

    val servicosApi = remember(categoriasSalao, servicosPorCategoria) {
        getServicosPorSalao(categoriasSalao, servicosPorCategoria)
    }
    var servicoSelecionado by remember { mutableStateOf<ServicoApi?>(null) }

    val nome = nomeUsuario.ifEmpty {
        SessionManager.getName()?.split(" ")?.firstOrNull()
            ?: (SessionManager.getEmail() ?: "").substringBefore("@").replaceFirstChar { it.uppercase() }
    }

    Box(modifier = Modifier.fillMaxSize().background(BrancoQuente)) {
        Box(
            modifier = Modifier.fillMaxWidth().height(220.dp)
                .shadow(16.dp, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MarromEscuro, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 48.dp)) {
                Text(
                    text = if (nomeSalao.isNotEmpty()) "${stringResource(R.string.servicos_do)} $nomeSalao" else stringResource(R.string.escolha_servico),
                    color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 18.sp,
                    textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp)
                )
                if (nomeSalao.isEmpty()) {
                    Text(stringResource(R.string.agendar_horario), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 18.sp, textAlign = TextAlign.Center)
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.TopCenter).offset(y = 123.dp).size(150.dp).clip(RoundedCornerShape(75.dp)).background(MarromEscuro)) {
            Image(painter = painterResource(id = R.drawable.logo_beautyhub), contentDescription = "Logo BeautyHub", modifier = Modifier.fillMaxSize())
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 290.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("${stringResource(R.string.bem_vindo)} $nome", color = Dourado, fontFamily = FrauncesFontFamily, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(24.dp))

            servicosApi.forEach { servico ->
                ServicoApiCard(servico = servico, selecionado = servicoSelecionado?.id == servico.id, onSelecionar = { servicoSelecionado = servico })
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { servicoSelecionado?.let { onAvancarClick(it) } },
                modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (servicoSelecionado != null) MarromEscuro else BegeMedio),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(stringResource(R.string.avancar), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 16.sp)
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
fun ServicoApiCard(servico: ServicoApi, selecionado: Boolean, onSelecionar: () -> Unit) {
    Row(
        modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()
            .background(MarromEscuro, RoundedCornerShape(12.dp)).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selecionado, onClick = onSelecionar, colors = RadioButtonDefaults.colors(selectedColor = Dourado, unselectedColor = BegeMedio))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(servico.nome, color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 15.sp)
            Text(stringResource(R.string.duracao, servico.duracaoMinutos), color = BegeMedio, fontSize = 12.sp)
            Text(stringResource(R.string.valor, "%.2f".format(servico.preco)), color = BegeMedio, fontSize = 12.sp)
        }
    }
}