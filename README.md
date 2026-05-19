# 💄 BeautyHub — Aplicativo Android

Sistema de agendamento de serviços de beleza e bem-estar desenvolvido em Kotlin com Jetpack Compose, com backend real integrado e banco de dados em produção.

---

## 📱 Sobre o Projeto

O **BeautyHub** é um aplicativo mobile Android que permite aos clientes:

- Criar conta e fazer login com autenticação real
- Visualizar serviços disponíveis do salão
- Agendar horários com validação de conflito em tempo real
- Pagar via Cartão de Crédito, Débito ou PIX (com código copiável)
- Acompanhar e cancelar agendamentos
- Reagendar serviços anteriores diretamente
- Acumular pontos no Cartão Fidelidade com dados reais
- Buscar salões por localidade e categoria
- Usar o app em Português, Inglês ou Espanhol
- Conhecer a plataforma na tela Sobre Nós

---

## 🚀 Tecnologias Utilizadas

- **Kotlin** — Linguagem principal
- **Jetpack Compose** — UI declarativa moderna
- **Navigation Compose** — Navegação entre telas
- **Retrofit** — Consumo de API REST
- **SharedPreferences** — Armazenamento local do token JWT
- **Material Design 3** — Componentes de interface
- **GitHub Actions** — Geração automática do APK

---

## 🗂️ Telas do Aplicativo

| Tela | Descrição |
|---|---|
| Login | Autenticação real com validação no banco de dados |
| Cadastro | Criação de conta com login automático após registro |
| Dashboard | Tela inicial com atalhos para todas as funcionalidades |
| Buscar Salão | Pesquisa por localidade e categoria com filtros |
| Home (Serviços) | Lista de serviços do salão selecionado |
| Agendamento | Escolha de dia e horário com bloqueio de horários ocupados |
| Pagamento | Simulação com validação de dados do cartão e PIX copiável |
| Meus Agendamentos | Histórico real com opção de cancelar ou reagendar |
| Cartão Fidelidade | Progresso real baseado nos agendamentos do usuário |
| Sobre Nós | Informações institucionais com modal Fale Conosco |

---

## ✅ Funcionalidades Implementadas

### Autenticação
- Login com validação real no banco de dados
- Cadastro com login automático após registro
- Token JWT salvo localmente
- Logout com limpeza completa da sessão

### Agendamentos
- Criação de agendamentos com serviço e horário reais
- Validação de conflito de horário no backend
- Horários já ocupados aparecem esmaecidos na tela
- Cancelamento de agendamentos via API
- Reagendamento direto para a tela de data/horário do mesmo serviço

### Pagamento
- Seleção de método: Cartão de Crédito, Débito ou PIX
- Validação dos 16 dígitos do cartão, validade e CVV
- PIX com código fictício e botão copiar para área de transferência

### Cartão Fidelidade
- Contagem real de procedimentos do usuário
- Barra de progresso visual
- Descontos progressivos: 20% no 3°, 25% no 5°, serviço grátis no 10°

### Internacionalização
- Suporte completo a Português, Inglês e Espanhol
- Troca de idioma em tempo real pelo app

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
4. Aguarde o Gradle sincronizar
5. Conecte seu celular via USB
6. Clique em **▶ Play**

### Baixar o APK direto

1. Acesse a aba **Actions** no GitHub
2. Clique no workflow mais recente **Build APK**
3. Baixe o arquivo em **Artifacts → BeautyHub-APK**
4. Instale o `.apk` no celular Android

---

## 🔗 Backend

O backend do projeto está disponível em:
👉 [beautyhub (Spring Boot)](https://github.com/CarolRibeiro-S/beautyhub)

- **URL de produção:** https://beautyhub-production.up.railway.app
- **Hospedagem:** Railway
- **Banco de dados:** MySQL em produção

---

## 🗄️ Serviços Disponíveis

19 serviços cadastrados em 5 categorias:

| Categoria | Serviços |
|---|---|
| Cabelo | Hidratação, Corte, Coloração, Escova Progressiva |
| Unhas | Manicure, Pedicure, Gel, Nail Art |
| Estética | Limpeza de Pele, Peeling, Drenagem, Design de Sobrancelha |
| Massagem | Relaxante, Desportiva, Pedras Quentes, Reflexologia |
| Maquiagem | Social, Noiva, Artística |

---

## 📋 Cartão Fidelidade

| Marco | Recompensa |
|---|---|
| 3° procedimento | 20% de desconto |
| 5° procedimento | 25% de desconto |
| 10° procedimento | Serviço gratuito |

---

## 👩‍💻 Desenvolvido por

**Carol Ribeiro** — Engenharia de Software

© 2026 Carol Ribeiro. Todos os direitos reservados.
