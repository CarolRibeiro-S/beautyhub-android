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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beautyhub.app.ui.theme.*
import kotlinx.coroutines.launch

fun formatarDataNascimento(input: String): String {
    val digits = input.filter { it.isDigit() }.take(8)
    return buildString {
        digits.forEachIndexed { i, c ->
            if (i == 2 || i == 4) append('/')
            append(c)
        }
    }
}

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
    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    fun telefoneValido(tel: String): Boolean {
        val apenasNumeros = tel.filter { it.isDigit() }
        return apenasNumeros.length in 10..11
    }

    fun dataNascimentoValida(data: String): Boolean {
        return data.matches(Regex("\\d{2}/\\d{2}/\\d{4}"))
    }

    fun emailValido(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Box(modifier = Modifier.fillMaxSize().background(BrancoQuente)) {

        Box(
            modifier = Modifier.fillMaxWidth().height(220.dp)
                .shadow(16.dp, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MarromEscuro, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = stringResource(R.string.crie_conta),
                color = BrancoQuente,
                fontFamily = FrauncesFontFamily,
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 60.dp)
            )

            // Botão voltar no topo esquerdo do header
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 48.dp, start = 8.dp)
            ) {
                TextButton(onClick = onVoltarClick) {
                    Text(
                        "← ${stringResource(R.string.voltar)}",
                        color = BrancoQuente,
                        fontFamily = FrauncesFontFamily,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Box(
            modifier = Modifier.align(Alignment.TopCenter).offset(y = 123.dp).size(150.dp)
                .clip(RoundedCornerShape(75.dp)).background(MarromEscuro)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_beautyhub),
                contentDescription = "Logo BeautyHub",
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 290.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.preencha_dados),
                color = MarromMedio,
                fontFamily = FrauncesFontFamily,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text(stringResource(R.string.nome_completo)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MarromEscuro, unfocusedBorderColor = MarromMedio,
                    focusedLabelColor = MarromEscuro, unfocusedLabelColor = MarromMedio,
                    cursorColor = MarromEscuro
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text(stringResource(R.string.telefone)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MarromEscuro, unfocusedBorderColor = MarromMedio,
                    focusedLabelColor = MarromEscuro, unfocusedLabelColor = MarromMedio,
                    cursorColor = MarromEscuro
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(R.string.email_label)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MarromEscuro, unfocusedBorderColor = MarromMedio,
                    focusedLabelColor = MarromEscuro, unfocusedLabelColor = MarromMedio,
                    cursorColor = MarromEscuro
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                label = { Text(stringResource(R.string.senha_label)) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MarromEscuro, unfocusedBorderColor = MarromMedio,
                    focusedLabelColor = MarromEscuro, unfocusedLabelColor = MarromMedio,
                    cursorColor = MarromEscuro
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = dataNascimento,
                onValueChange = { dataNascimento = formatarDataNascimento(it) },
                label = { Text(stringResource(R.string.data_nascimento)) },
                singleLine = true,
                placeholder = { Text("DD/MM/AAAA", color = BegeMedio) },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MarromEscuro, unfocusedBorderColor = MarromMedio,
                    focusedLabelColor = MarromEscuro, unfocusedLabelColor = MarromMedio,
                    cursorColor = MarromEscuro
                ),
                shape = RoundedCornerShape(8.dp)
            )

            if (erro.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = erro, color = Vermelho, fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    erro = ""
                    when {
                        nome.isEmpty() || email.isEmpty() || senha.isEmpty() || telefone.isEmpty() || dataNascimento.isEmpty() ->
                            erro = "Preencha todos os campos!"
                        nome.trim().length < 3 -> erro = "Nome muito curto!"
                        !emailValido(email) -> erro = "Digite um e-mail válido!"
                        senha.length < 6 -> erro = "A senha deve ter pelo menos 6 caracteres!"
                        !telefoneValido(telefone) -> erro = "Digite um telefone válido com DDD!"
                        !dataNascimentoValida(dataNascimento) -> erro = "Use o formato DD/MM/AAAA!"
                        else -> {
                            isLoading = true
                            coroutineScope.launch {
                                try {
                                    val request = RegisterRequest(
                                        fullName = nome,
                                        email = email,
                                        phone = telefone,
                                        password = senha
                                    )
                                    val response = Api.service.register(request)
                                    if (response.isSuccessful) {
                                        try {
                                            val loginRequest = LoginRequest(email = email, password = senha)
                                            val loginResponse = Api.service.login(loginRequest)
                                            if (loginResponse.isSuccessful) {
                                                val loginBody = loginResponse.body()
                                                SessionManager.saveToken(loginBody?.token ?: "")
                                                SessionManager.saveName(loginBody?.fullName ?: nome)
                                                SessionManager.saveEmail(email)
                                            } else {
                                                SessionManager.saveName(nome)
                                                SessionManager.saveEmail(email)
                                            }
                                        } catch (e: Exception) {
                                            SessionManager.saveName(nome)
                                            SessionManager.saveEmail(email)
                                        }
                                        onCadastroClick()
                                    } else {
                                        val errorBody = response.errorBody()?.string()
                                        erro = "Erro ${response.code()}: ${errorBody ?: "Tente novamente."}"
                                    }
                                } catch (e: Exception) {
                                    erro = "Erro: ${e.javaClass.simpleName} - ${e.localizedMessage ?: "Verifique sua internet."}"
                                } finally {
                                    isLoading = false
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MarromEscuro),
                shape = RoundedCornerShape(8.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = BrancoQuente, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        stringResource(R.string.cadastrar),
                        color = BrancoQuente,
                        fontFamily = FrauncesFontFamily,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onVoltarClick) {
                Text(
                    stringResource(R.string.ja_tem_conta),
                    color = MarromMedio,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}