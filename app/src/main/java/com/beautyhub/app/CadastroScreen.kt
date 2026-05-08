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

            Column(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()) {
                Text("NOME COMPLETO:", color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
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
                Text("TELEFONE (WHATSAPP):", color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = telefone,
                    onValueChange = { telefone = it },
                    placeholder = { Text("(XX) 12345 6789", color = BegeMedio) },
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

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()) {
                Text("DATA DE NASCIMENTO:", color = MarromEscuro, fontFamily = FrauncesFontFamily, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = dataNascimento,
                    onValueChange = { dataNascimento = it },
                    placeholder = { Text("XX/XX/XXXX", color = BegeMedio) },
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
                    if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || telefone.isEmpty()) {
                        erro = "Preencha todos os campos!"
                        return@Button
                    }
                    onCadastroClick()
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