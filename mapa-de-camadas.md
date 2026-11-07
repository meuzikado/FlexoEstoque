Cliente (Postman)
   │
   ▼
Controller:  BobinaController ──────────▶ BobinaRepository
             MovimentacaoController ────▶ bobinaService
             RelatorioController ───────▶ bobinaService
   │
   ▼
Service:     BobinaService ─────────────▶ bobinaRepository e movimentacaoRepository ?
   │
   ▼
Repository:  BobinaRepository, MovimentacaoRepository
   │
   ▼
Banco (H2):  tb_bobina  ◀── bobina_id ──  tb_movimentacao
