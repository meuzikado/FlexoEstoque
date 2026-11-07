Cliente (Postman)
   │
   ▼
Controller:  BobinaController ──────────▶ BobinaRepository (é o unico que não passa pelo service porque vai direto ao bobinaRepository. Isso significa que cadastras, alterar ou excluir hoje não passa por nenhuma regra de negocio)
             MovimentacaoController ────▶ BobinaService
             RelatorioController ───────▶ BobinaService
   │
   ▼
Service:     BobinaService ─────────────▶ BobinaRepository e MovimentacaoRepository 
   │
   ▼
Repository:  BobinaRepository, MovimentacaoRepository
   │
   ▼
Banco (H2):  tb_bobina  ◀── bobina_id ──  tb_movimentacao
