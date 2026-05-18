package com.beautyhub.app

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Cadastro : Screen("cadastro")
    object Dashboard : Screen("dashboard")
    object MeusAgendamentos : Screen("meus_agendamentos")
    object Busca : Screen("busca")
    object SobreNos : Screen("sobre_nos")
    object CartaoFidelidade : Screen("cartao_fidelidade")
}

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onCadastroClick = { navController.navigate(Screen.Cadastro.route) }
            )
        }

        composable(Screen.Cadastro.route) {
            CadastroScreen(
                onCadastroClick = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Cadastro.route) { inclusive = true }
                    }
                },
                onVoltarClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onAgendarClick = { navController.navigate(Screen.Busca.route) },
                onMeusAgendamentosClick = { navController.navigate(Screen.MeusAgendamentos.route) },
                onCartaoFidelidadeClick = { navController.navigate(Screen.CartaoFidelidade.route) },
                onSobreNosClick = { navController.navigate(Screen.SobreNos.route) },
                onSairClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Busca.route) {
            BuscaScreen(
                onAvancarClick = { nome, categorias ->
                    val nomeEnc = Uri.encode(nome)
                    val catEnc = Uri.encode(categorias)
                    navController.navigate("home/$nomeEnc/$catEnc")
                },
                onVoltarClick = { navController.popBackStack() }
            )
        }

        composable("home/{nomeSalao}/{categoriasSalao}") { backStackEntry ->
            val nomeSalao = backStackEntry.arguments?.getString("nomeSalao") ?: ""
            val categoriasSalao = backStackEntry.arguments?.getString("categoriasSalao") ?: ""
            val nome = SessionManager.getName()?.split(" ")?.firstOrNull()
                ?: (SessionManager.getEmail() ?: "").substringBefore("@")
                    .replaceFirstChar { it.uppercase() }
            HomeScreen(
                nomeUsuario = nome,
                nomeSalao = nomeSalao,
                categoriasSalao = categoriasSalao,
                onAvancarClick = { servico ->
                    val nomeEnc = Uri.encode(servico.nome)
                    navController.navigate("agendamento/${servico.id}/$nomeEnc/${servico.preco}/${servico.duracaoMinutos}")
                },
                onMeusAgendamentosClick = { navController.navigate(Screen.MeusAgendamentos.route) },
                onCartaoFidelidadeClick = { navController.navigate(Screen.CartaoFidelidade.route) },
                onBuscaClick = { navController.navigate(Screen.Busca.route) },
                onSobreNosClick = { navController.navigate(Screen.SobreNos.route) },
                onSairClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onVoltarClick = { navController.popBackStack() }
            )
        }

        composable("agendamento/{servicoId}/{servicoNome}/{servicoPreco}/{servicoDuracao}") { backStackEntry ->
            val servicoId = backStackEntry.arguments?.getString("servicoId")?.toIntOrNull() ?: 0
            val servicoNome = backStackEntry.arguments?.getString("servicoNome") ?: ""
            val servicoPreco = backStackEntry.arguments?.getString("servicoPreco")?.toDoubleOrNull() ?: 0.0
            val servicoDuracao = backStackEntry.arguments?.getString("servicoDuracao")?.toIntOrNull() ?: 0
            AgendamentoScreen(
                servicoId = servicoId,
                servicoNome = servicoNome,
                servicoPreco = servicoPreco,
                servicoDuracao = servicoDuracao,
                onConfirmarClick = { dataHora, dataFormatada ->
                    val nomeEnc = Uri.encode(servicoNome)
                    val dataFormEnc = Uri.encode(dataFormatada)
                    val dataHoraEnc = Uri.encode(dataHora)
                    navController.navigate("pagamento/$servicoId/$nomeEnc/$servicoPreco/$servicoDuracao/$dataFormEnc/$dataHoraEnc")
                },
                onVoltarClick = { navController.popBackStack() }
            )
        }

        composable("pagamento/{servicoId}/{servicoNome}/{servicoPreco}/{servicoDuracao}/{dataFormatada}/{dataHoraInicio}") { backStackEntry ->
            PagamentoScreen(
                servicoId = backStackEntry.arguments?.getString("servicoId")?.toIntOrNull() ?: 0,
                servicoNome = backStackEntry.arguments?.getString("servicoNome") ?: "",
                servicoPreco = backStackEntry.arguments?.getString("servicoPreco")?.toDoubleOrNull() ?: 0.0,
                servicoDuracao = backStackEntry.arguments?.getString("servicoDuracao")?.toIntOrNull() ?: 0,
                dataFormatada = backStackEntry.arguments?.getString("dataFormatada") ?: "",
                dataHoraInicio = backStackEntry.arguments?.getString("dataHoraInicio") ?: "",
                onPagarClick = { navController.navigate(Screen.MeusAgendamentos.route) },
                onVoltarClick = { navController.popBackStack() }
            )
        }

        composable(Screen.MeusAgendamentos.route) {
            MeusAgendamentosScreen(
                onVoltarClick = { navController.popBackStack() },
                onReagendarClick = { servicoId, servicoNome, servicoPreco, servicoDuracao ->
                    val nomeEnc = Uri.encode(servicoNome)
                    navController.navigate("agendamento/$servicoId/$nomeEnc/$servicoPreco/$servicoDuracao")
                },
                onHomeClick = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.SobreNos.route) {
            SobreNosScreen(onVoltarClick = { navController.popBackStack() })
        }

        composable(Screen.CartaoFidelidade.route) {
            CartaoFidelidadeScreen(onVoltarClick = { navController.popBackStack() })
        }
    }
}