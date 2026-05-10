package com.beautyhub.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Cadastro : Screen("cadastro")
    object Agendamento : Screen("agendamento")
    object MeusAgendamentos : Screen("meus_agendamentos")
    object Pagamento : Screen("pagamento")
    object Busca : Screen("busca")
    object SobreNos : Screen("sobre_nos")
    object CartaoFidelidade : Screen("cartao_fidelidade")
}

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = { navController.navigate(Screen.Busca.route) },
                onCadastroClick = { navController.navigate(Screen.Cadastro.route) }
            )
        }
        composable(Screen.Cadastro.route) {
            CadastroScreen(
                onCadastroClick = { navController.navigate(Screen.Busca.route) },
                onVoltarClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Busca.route) {
            BuscaScreen(
                onAvancarClick = { nome, categorias ->
                    navController.navigate("home/$nome/$categorias")
                },
                onVoltarClick = { navController.popBackStack() }
            )
        }
        composable("home/{nomeSalao}/{categoriasSalao}") { backStackEntry ->
            val email = SessionManager.getEmail() ?: ""
            val nomeUsuario = email.substringBefore("@").replaceFirstChar { it.uppercase() }
            HomeScreen(
                nomeUsuario = nomeUsuario,
                nomeSalao = backStackEntry.arguments?.getString("nomeSalao") ?: "",
                categoriasSalao = backStackEntry.arguments?.getString("categoriasSalao") ?: "",
                onAvancarClick = { navController.navigate(Screen.Agendamento.route) },
                onMeusAgendamentosClick = { navController.navigate(Screen.MeusAgendamentos.route) },
                onCartaoFidelidadeClick = { navController.navigate(Screen.CartaoFidelidade.route) },
                onBuscaClick = { navController.navigate(Screen.Busca.route) },
                onSobreNosClick = { navController.navigate(Screen.SobreNos.route) },
                onSairClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Agendamento.route) {
            AgendamentoScreen(
                onConfirmarClick = { navController.navigate(Screen.Pagamento.route) },
                onVoltarClick = { navController.popBackStack() }
            )
        }
        composable(Screen.MeusAgendamentos.route) {
            MeusAgendamentosScreen(
                onVoltarClick = { navController.popBackStack() },
                onReagendarClick = { navController.navigate(Screen.Busca.route) }
            )
        }
        composable(Screen.Pagamento.route) {
            PagamentoScreen(
                onPagarClick = { navController.navigate(Screen.MeusAgendamentos.route) },
                onVoltarClick = { navController.popBackStack() }
            )
        }
        composable(Screen.SobreNos.route) {
            SobreNosScreen(
                onVoltarClick = { navController.popBackStack() }
            )
        }
        composable(Screen.CartaoFidelidade.route) {
            CartaoFidelidadeScreen(
                onVoltarClick = { navController.popBackStack() }
            )
        }
    }
}