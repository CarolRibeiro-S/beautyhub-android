package com.beautyhub.app

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beautyhub.app.ui.theme.*
import kotlinx.coroutines.launch

fun emailValido(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onCadastroClick: () -> Unit = {}
) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }
    var naoEncontrado by remember { mutableStateOf(false) }
    var carregando by remember { mutableStateOf(false) }
    var idiomaAtual by remember { mutableStateOf(LanguageManager.getSavedLanguage(context)) }
    val scope = rememberCoroutineScope()

    val idiomas = listOf(
        Triple("pt", "🇧🇷", "PT"),
        Triple("en", "🇺🇸", "EN"),
        Triple("es", "🇪🇸", "ES")
    )

    Box(modifier = Modifier.fillMaxSize().background(BrancoQuente)) {
        Box(
            modifier = Modifier.fillMaxWidth().height(220.dp)
                .shadow(16.dp, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MarromEscuro, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(text = stringResource(R.string.bem_vinda), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 22.sp, modifier = Modifier.padding(top = 80.dp))
        }

        Box(
            modifier = Modifier.align(Alignment.TopCenter).offset(y = 123.dp).size(150.dp)
                .clip(RoundedCornerShape(75.dp)).background(MarromEscuro)
        ) {
            Image(painter = painterResource(id = R.drawable.logo_beautyhub), contentDescription = "Logo BeautyHub", modifier = Modifier.fillMaxSize())
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 295.dp, bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.faca_login), color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp))

            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()) {
                Text(stringResource(R.string.email_label), color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it; naoEncontrado = false },
                    placeholder = { Text("seuemail@...", color = BegeMedio) },
                    isError = email.isNotEmpty() && !emailValido(email),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarromEscuro, unfocusedBorderColor = BegeMedio,
                        errorBorderColor = Vermelho, focusedContainerColor = BegeClaro, unfocusedContainerColor = BegeClaro
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                if (email.isNotEmpty() && !emailValido(email)) {
                    Text("Digite um e-mail válido", color = Vermelho, fontSize = 11.sp, modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()) {
                Text(stringResource(R.string.senha_label), color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it; naoEncontrado = false },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = senha.isNotEmpty() && senha.length < 6,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarromEscuro, unfocusedBorderColor = BegeMedio,
                        errorBorderColor = Vermelho, focusedContainerColor = BegeClaro, unfocusedContainerColor = BegeClaro
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                if (senha.isNotEmpty() && senha.length < 6) {
                    Text("A senha deve ter pelo menos 6 caracteres", color = Vermelho, fontSize = 11.sp, modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                }
            }

            if (erro.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(erro, color = Vermelho, fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp))
            }

            // Mensagem de não cadastrado
            if (naoEncontrado) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()
                        .background(BegeClaro, RoundedCornerShape(8.dp)).padding(12.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Usuário não encontrado.", color = Vermelho, fontSize = 12.sp, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Não tem conta ainda?", color = MarromMedio, fontSize = 12.sp, textAlign = TextAlign.Center)
                        TextButton(onClick = onCadastroClick) {
                            Text("Cadastre-se aqui!", color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    naoEncontrado = false
                    when {
                        email.isEmpty() || senha.isEmpty() -> erro = "Preencha todos os campos!"
                        !emailValido(email) -> erro = "Digite um e-mail válido!"
                        senha.length < 6 -> erro = "A senha deve ter pelo menos 6 caracteres!"
                        else -> {
                            scope.launch {
                                carregando = true
                                erro = ""
                                try {
                                    val response = Api.service.login(LoginRequest(email, senha))
                                    if (response.isSuccessful) {
                                        response.body()?.let { body ->
                                            SessionManager.saveToken(body.token)
                                            SessionManager.saveEmail(email)
                                            body.fullName?.let { SessionManager.saveName(it) }
                                        }
                                        onLoginClick()
                                    } else {
                                        naoEncontrado = true
                                    }
                                } catch (e: Exception) {
                                    erro = "Erro de conexão. Tente novamente."
                                }
                                carregando = false
                            }
                        }
                    }
                },
                modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MarromEscuro),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (carregando) {
                    CircularProgressIndicator(color = BrancoQuente, modifier = Modifier.size(24.dp))
                } else {
                    Text(stringResource(R.string.entrar), color = BrancoQuente, fontFamily = FrauncesFontFamily, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onCadastroClick) {
                Text(stringResource(R.string.nao_tem_conta), color = MarromMedio, fontFamily = FrauncesFontFamily, fontSize = 13.sp)
            }
        }

        Row(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                .navigationBarsPadding().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            idiomas.forEach { (codigo, bandeira, label) ->
                val selecionado = idiomaAtual == codigo
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 16.dp)) {
                    TextButton(
                        onClick = {
                            if (idiomaAtual != codigo) {
                                idiomaAtual = codigo
                                LanguageManager.saveAndApplyLanguage(context, codigo)
                            }
                        },
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = bandeira, fontSize = 26.sp)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = label, fontSize = 11.sp, color = if (selecionado) MarromEscuro else BegeMedio, fontFamily = FrauncesFontFamily)
                            if (selecionado) {
                                Box(modifier = Modifier.width(20.dp).height(2.dp).background(Dourado, RoundedCornerShape(1.dp)))
                            }
                        }
                    }
                }
            }
        }
    }
}