package com.beautyhub.app

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beautyhub.app.ui.theme.*

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onCadastroClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }

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
                text = "Bem-vinda ao",
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
                .padding(top = 295.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Faça login para agendar seu momento de cuidado",
                color = MarromMedio,
                fontFamily = FrauncesFontFamily,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()) {
                Text("E-MAIL:", color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("seuemail@...", color = BegeMedio) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarromEscuro,
                        unfocusedBorderColor = BegeMedio,
                        focusedContainerColor = BegeClaro,
                        unfocusedContainerColor = BegeClaro
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()) {
                Text("SENHA:", color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarromEscuro,
                        unfocusedBorderColor = BegeMedio,
                        focusedContainerColor = BegeClaro,
                        unfocusedContainerColor = BegeClaro
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "esqueci a senha",
                color = MarromMedio,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 32.dp)
            )

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

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isEmpty() || senha.isEmpty()) {
                        erro = "Preencha todos os campos!"
                        return@Button
                    }
                    onLoginClick()
                },
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MarromEscuro),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "ENTRAR",
                    color = BrancoQuente,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onCadastroClick) {
                Text(
                    text = "Não tem conta? Cadastre-se",
                    color = MarromMedio,
                    fontFamily = FrauncesFontFamily,
                    fontSize = 13.sp
                )
            }
        }
    }
}