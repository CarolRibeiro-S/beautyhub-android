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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beautyhub.app.ui.theme.*

@Composable
fun CadastroScreen(
    onCadastroClick: () -> Unit = {},
    onVoltarClick: () -> Unit = {}
) {
    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }

    fun telefoneValido(tel: String): Boolean {
        val apenasNumeros = tel.filter { it.isDigit() }
        return apenasNumeros.length in 10..11
    }

    fun dataNascimentoValida(data: String): Boolean {
        return data.matches(Regex("\\d{2}/\\d{2}/\\d{4}"))
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
                text = "Crie sua conta na",
                color = BrancoQuente,
                fontFamily = FrauncesFontFamily,
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 80.dp)
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
                text = "Preencha seus dados para começar a agendar.",
                color = MarromMedio,
                fontFamily = FrauncesFontFamily,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nome
            Column(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()) {
                Text("NOME COMPLETO:", color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    isError = nome.isNotEmpty() && nome.trim().length < 3,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarromEscuro,
                        unfocusedBorderColor = BegeMedio,
                        errorBorderColor = Vermelho,
                        focusedContainerColor = BegeClaro,
                        unfocusedContainerColor = BegeClaro
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                if (nome.isNotEmpty() && nome.trim().length < 3) {
                    Text("Nome muito curto", color = Vermelho, fontSize = 11.sp,
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Telefone
            Column(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()) {
                Text("TELEFONE (WHATSAPP):", color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = telefone,
                    onValueChange = { telefone = it },
                    placeholder = { Text("(XX) 12345 6789", color = BegeMedio) },
                    isError = telefone.isNotEmpty() && !telefoneValido(telefone),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarromEscuro,
                        unfocusedBorderColor = BegeMedio,
                        errorBorderColor = Vermelho,
                        focusedContainerColor = BegeClaro,
                        unfocusedContainerColor = BegeClaro
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                if (telefone.isNotEmpty() && !telefoneValido(telefone)) {
                    Text("Digite um telefone válido com DDD", color = Vermelho, fontSize = 11.sp,
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            Column(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()) {
                Text("E-MAIL:", color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("seuemail@...", color = BegeMedio) },
                    isError = email.isNotEmpty() && !emailValido(email),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarromEscuro,
                        unfocusedBorderColor = BegeMedio,
                        errorBorderColor = Vermelho,
                        focusedContainerColor = BegeClaro,
                        unfocusedContainerColor = BegeClaro
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                if (email.isNotEmpty() && !emailValido(email)) {
                    Text("Digite um e-mail válido", color = Vermelho, fontSize = 11.sp,
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Senha
            Column(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()) {
                Text("SENHA:", color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = senha.isNotEmpty() && senha.length < 6,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarromEscuro,
                        unfocusedBorderColor = BegeMedio,
                        errorBorderColor = Vermelho,
                        focusedContainerColor = BegeClaro,
                        unfocusedContainerColor = BegeClaro
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                if (senha.isNotEmpty() && senha.length < 6) {
                    Text("A senha deve ter pelo menos 6 caracteres", color = Vermelho, fontSize = 11.sp,
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Data de nascimento
            Column(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()) {
                Text("DATA DE NASCIMENTO:", color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = dataNascimento,
                    onValueChange = { dataNascimento = it },
                    placeholder = { Text("DD/MM/AAAA", color = BegeMedio) },
                    isError = dataNascimento.isNotEmpty() && !dataNascimentoValida(dataNascimento),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarromEscuro,
                        unfocusedBorderColor = BegeMedio,
                        errorBorderColor = Vermelho,
                        focusedContainerColor = BegeClaro,
                        unfocusedContainerColor = BegeClaro
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                if (dataNascimento.isNotEmpty() && !dataNascimentoValida(dataNascimento)) {
                    Text("Use o formato DD/MM/AAAA", color = Vermelho, fontSize = 11.sp,
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                }
            }

            if (erro.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = erro,
                    color = Vermelho,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    when {
                        nome.isEmpty() || email.isEmpty() || senha.isEmpty() || telefone.isEmpty() || dataNascimento.isEmpty() ->
                            erro = "Preencha todos os campos!"
                        nome.trim().length < 3 ->
                            erro = "Nome muito curto!"
                        !emailValido(email) ->
                            erro = "Digite um e-mail válido!"
                        senha.length < 6 ->
                            erro = "A senha deve ter pelo menos 6 caracteres!"
                        !telefoneValido(telefone) ->
                            erro = "Digite um telefone válido com DDD!"
                        !dataNascimentoValida(dataNascimento) ->
                            erro = "Use o formato DD/MM/AAAA!"
                        else -> onCadastroClick()
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MarromEscuro),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "CADASTRAR",
                    color = BrancoQuente,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onVoltarClick) {
                Text(
                    text = "Já tem conta? Faça login",
                    color = MarromMedio,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}