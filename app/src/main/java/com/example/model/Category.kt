package com.example.model

enum class Category(
  val id: String,
  val displayName: String,
  val iconEmoji: String,
) {
  ALL("all", "Todos os Doces", "✨"),
  CAKES("cakes", "Bolos & Tortas", "🎂"),
  BRIGADEIROS("brigadeiros", "Brigadeiros Gourmet", "🍫"),
  CUPCAKES("cupcakes", "Cupcakes", "🧁"),
  DESSERTS("desserts", "Doces Finos", "🍓"),
  KITS("kits", "Caixas & Presentes", "🎁"),
}
