package com.beautyhub.app

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beautyhub.app.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun PagamentoScreen(
    servicoId: Int = 0,
    servicoNome: String = "",
    servicoPreco: Double = 0.0,
    servicoDuracao: Int = 0,
    dataFormatada: String = "",
    dataHoraInicio: String = "",
    onPagarClick: () -> Unit = {},
    onVoltarClick: () -> Unit = {}
) {
    var metodoPagamento by remember { mutableStateOf("") }
    var numeroCartao by remember { mutableStateOf("") }
    var validade by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var pagamentoConfirmado by remember { mutableStateOf(false) }
    var carregando by remember { mutableStateOf(false) }
    var erro by remember { mutableStateOf("") }
    var pixCopiado by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val cartaoCredito = stringResource(R.string.cartao_credito)
    val cartaoDebito = stringResource(R.string.cartao_debito)
    val pixCodigo = stringResource(R.string.pix_codigo)
    val pixCopiadoMsg = stringResource(R.string.pix_copiado)
    val erroAgendamento = stringResource(R.string.erro_agendamento)
    val erroConexao = stringResource(R.string.erro_conexao)

    Box(modifier = Modifier.fillMaxSize().background(BrancoQuente)) {
        Box(
            modifier = Modifier.fillMaxWidth().height(220.dp)
                .shadow(16.dp, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MarromEscuro, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(stringResource(R.string.pagamento), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 24.sp, modifier = Modifier.padding(top = 60.dp))
        }

        Box(modifier = Modifier.align(Alignment.TopCenter).offset(y = 123.dp).size(150.dp).clip(RoundedCornerShape(75.dp)).background(MarromEscuro)) {
            Image(painter = painterResource(id = R.drawable.logo_beautyhub), contentDescription = "Logo BeautyHub", modifier = Modifier.fillMaxSize())
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 290.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (pagamentoConfirmado) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(stringResource(R.string.pagamento_confirmado), color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 20.sp, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                Text(stringResource(R.string.agendamento_confirmado), color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 14.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp))
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onPagarClick,
                    modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MarromEscuro),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(stringResource(R.string.meus_agendamentos), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
                }
            } else {
                Box(
                    modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()
                        .background(BegeClaro, RoundedCornerShape(12.dp)).padding(16.dp)
                ) {
                    Column {
                        Text(stringResource(R.string.resumo_agendamento), color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.servico_label, servicoNome), color = MarromMedio, fontSize = 13.sp)
                        Text(stringResource(R.string.data_label, dataFormatada), color = MarromMedio, fontSize = 13.sp)
                        Text(stringResource(R.string.duracao_label, servicoDuracao), color = MarromMedio, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.total_label, "%.2f".format(servicoPreco)), color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(stringResource(R.string.escolha_pagamento), color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 14.sp, modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp))
                Spacer(modifier = Modifier.height(12.dp))

                listOf(cartaoCredito, cartaoDebito, "PIX").forEach { metodo ->
                    val selecionado = metodoPagamento == metodo
                    Row(
                        modifier = Modifier.padding(horizontal = 32.dp, vertical = 4.dp).fillMaxWidth()
                            .background(if (selecionado) MarromEscuro else BegeClaro, RoundedCornerShape(8.dp))
                            .clickable { metodoPagamento = metodo }.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = selecionado, onClick = { metodoPagamento = metodo }, colors = RadioButtonDefaults.colors(selectedColor = Dourado, unselectedColor = MarromMedio))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(metodo, color = if (selecionado) BrancoQuente else MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (metodoPagamento == cartaoCredito || metodoPagamento == cartaoDebito) {
                    Column(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()) {
                        Text(stringResource(R.string.numero_cartao), color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = numeroCartao, onValueChange = { numeroCartao = it },
                            placeholder = { Text("0000 0000 0000 0000", color = BegeMedio) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MarromEscuro, unfocusedBorderColor = BegeMedio, focusedContainerColor = BegeClaro, unfocusedContainerColor = BegeClaro),
                            shape = RoundedCornerShape(8.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(stringResource(R.string.validade), color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                OutlinedTextField(
                                    value = validade, onValueChange = { validade = it },
                                    placeholder = { Text("MM/AA", color = BegeMedio) },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MarromEscuro, unfocusedBorderColor = BegeMedio, focusedContainerColor = BegeClaro, unfocusedContainerColor = BegeClaro),
                                    shape = RoundedCornerShape(8.dp)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(stringResource(R.string.cvv), color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                OutlinedTextField(
                                    value = cvv, onValueChange = { cvv = it },
                                    placeholder = { Text("123", color = BegeMedio) },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MarromEscuro, unfocusedBorderColor = BegeMedio, focusedContainerColor = BegeClaro, unfocusedContainerColor = BegeClaro),
                                    shape = RoundedCornerShape(8.dp)
                                )
                            }
                        }
                    }
                }

                if (metodoPagamento == "PIX") {
                    Column(
                        modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()
                            .background(BegeClaro, RoundedCornerShape(12.dp)).padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(stringResource(R.string.chave_pix), color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("beautyhub@pagamento.com", color = Dourado, fontFamily = FrauncesFontFamily, fontSize = 14.sp, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            pixCodigo,
                            color = MarromMedio, fontSize = 10.sp, textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("PIX", pixCodigo)
                                clipboard.setPrimaryClip(clip)
                                pixCopiado = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Dourado),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(if (pixCopiado) pixCopiadoMsg else stringResource(R.string.pix_copiar), color = BrancoQuente, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.pix_instrucao), color = MarromMedio, fontSize = 12.sp, textAlign = TextAlign.Center)
                    }
                }

                if (erro.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(erro, color = Vermelho, fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp))
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (metodoPagamento.isEmpty()) return@Button
                        scope.launch {
                            carregando = true
                            erro = ""
                            try {
                                val token = "Bearer ${SessionManager.getToken()}"
                                val request = AppointmentRequest(serviceId = servicoId, dataHoraInicio = dataHoraInicio)
                                val response = Api.service.createAppointment(token, request)
                                if (response.isSuccessful) {
                                    pagamentoConfirmado = true
                                } else {
                                    erro = "$erroAgendamento (${response.code()})"
                                }
                            } catch (e: Exception) {
                                erro = "$erroConexao: ${e.message}"
                            }
                            carregando = false
                        }
                    },
                    modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (metodoPagamento.isNotEmpty()) MarromEscuro else BegeMedio),
                    shape = RoundedCornerShape(8.dp),
                    enabled = !carregando
                ) {
                    if (carregando) {
                        CircularProgressIndicator(color = BrancoQuente, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Text(stringResource(R.string.confirmar_pagamento), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 14.sp)
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
}