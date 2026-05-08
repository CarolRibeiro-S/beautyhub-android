# 💄 BeautyHub — Aplicativo Android

Sistema de agendamento de serviços de beleza e bem-estar desenvolvido em Kotlin com Jetpack Compose.

---

## 📱 Sobre o Projeto

O **BeautyHub** é um aplicativo mobile Android que permite aos clientes:
- Criar conta e fazer login
- Visualizar serviços disponíveis
- Agendar horários
- Simular pagamento (Cartão de Crédito, Débito ou PIX)
- Acompanhar agendamentos
- Acumular pontos no Cartão Fidelidade
- Buscar salões e serviços
- Conhecer a plataforma na tela Sobre Nós

---

## 🚀 Tecnologias Utilizadas

- **Kotlin** — Linguagem principal
- **Jetpack Compose** — UI declarativa
- **Navigation Compose** — Navegação entre telas
- **Retrofit** — Consumo de API REST
- **SharedPreferences** — Armazenamento local do token JWT
- **Material Design 3** — Componentes de interface

---

## 🗂️ Telas do Aplicativo

| Tela | Descrição |
|---|---|
| Login | Autenticação do usuário |
| Cadastro | Criação de nova conta |
| Home | Lista de serviços disponíveis |
| Agendamento | Escolha de dia e horário |
| Pagamento | Simulação de pagamento |
| Meus Agendamentos | Histórico de agendamentos |
| Cartão Fidelidade | Programa de pontos e descontos |
| Buscar Serviços | Pesquisa de salões e serviços |
| Sobre Nós | Informações institucionais |

---

## 🎨 Identidade Visual

| Cor | Hex | Uso |
|---|---|---|
| Marrom Escuro | `#4A3525` | Header, botões principais |
| Branco Quente | `#FFF9F4` | Fundo das telas |
| Bege Claro | `#F6EAE0` | Cards e inputs |
| Dourado | `#C8905B` | Destaques e fidelidade |
| Vermelho | `#D45C5C` | Cancelar e alertas |

**Fonte:** Fraunces SemiBold (Google Fonts)

---

## ⚙️ Como Rodar o Projeto

### Pré-requisitos
- Android Studio (versão Meerkat ou superior)
- Celular Android com **depuração USB ativada** (Android 8.0+)
- Cabo USB para conexão

### Passos

1. Clone o repositório:
```bash
git clone https://github.com/CarolRibeiro-S/beautyhub-android.git
```

2. Abra o Android Studio

3. Clique em **Open** e selecione a pasta `BeautyHub`

4. Aguarde o Gradle sincronizar (pode levar alguns minutos)

5. Conecte seu celular via USB e ative a depuração USB

6. Clique no botão **▶ Play** no topo do Android Studio

7. O app será instalado e abrirá automaticamente no celular!

---

## 🔗 Backend

O backend do projeto está disponível em:
👉 [beautyhub (Spring Boot)](https://github.com/CarolRibeiro-S/beautyhub)

Desenvolvido com:
- Java 17 + Spring Boot 3
- Spring Security + JWT
- MySQL + Hibernate

---

## 📋 Funcionalidades do Cartão Fidelidade

| Marco | Recompensa |
|---|---|
| 1º procedimento | 30% de desconto |
| 3º procedimento | 20% de desconto |
| 10º procedimento | Serviço gratuito no salão favorito |

> Os pontos valem em qualquer salão cadastrado no BeautyHub — diferencial exclusivo em relação a concorrentes como o Booksy!

---

## 👩‍💻 Desenvolvido por

**Carol Ribeiro** — Engenharia de Software
