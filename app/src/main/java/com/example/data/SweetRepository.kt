package com.example.data

import com.example.R
import com.example.model.Category
import com.example.model.DoughOption
import com.example.model.FillingOption
import com.example.model.FrostingOption
import com.example.model.SizeOption
import com.example.model.SweetItem
import com.example.model.ToppingOption

import androidx.compose.runtime.mutableStateListOf

object SweetRepository {

  val defaultDoughOptions = listOf(
    DoughOption("dough_vanilla", "Baunilha Amanteigada", "Massa fofinha com favas de baunilha"),
    DoughOption("dough_chocolate", "Chocolate 50% Cacau", "Massa úmida e aveludada de chocolate belga"),
    DoughOption("dough_red_velvet", "Red Velvet Suave", "Massa vermelha aveludada clássica"),
    DoughOption("dough_carrot", "Cenoura Caseira", "Cenoura fresca fofinha tradicional"),
  )

  val defaultFillingOptions = listOf(
    FillingOption("fill_brigadeiro", "Brigadeiro Belga Tradicional", "Feito com chocolate nobre e textura cremosa"),
    FillingOption("fill_ninho", "Ninho Trufado Cremoso", "O queridinho da Edilicias, suave e cremoso"),
    FillingOption("fill_dulce", "Doce de Leite com Nozes", "Doce de leite mineiro cozido lentamente"),
    FillingOption("fill_berries", "Geleia de Frutas Vermelhas", "Morangos, framboesas e amoras frescas"),
    FillingOption("fill_maracuja", "Ganache de Maracujá", "Toque cítrico e refrescante perfeito"),
    FillingOption("fill_nutella", "Puro Creme de Nutella", "Camada generosa de avelã com cacau"),
  )

  val defaultFrostingOptions = listOf(
    FrostingOption("frost_chantininho", "Chantininho Sedoso", "Cobertura leve, estruturada e saborosa"),
    FrostingOption("frost_ganache", "Ganache de Chocolate Belga", "Acabamento brilhante e sabor intenso"),
    FrostingOption("frost_buttercream", "Buttercream de Baunilha", "Estilo vintage e acabamento delicado"),
    FrostingOption("frost_vulcao", "Cobertura Vulcão Cremosa", "Escorre deliciosamente ao cortar"),
  )

  val defaultToppings = listOf(
    ToppingOption("top_strawberries", "Morangos Frescos Selecionados", 4.50, "🍓"),
    ToppingOption("top_callebaut", "Granulado Belga Callebaut", 3.50, "🍫"),
    ToppingOption("top_nutella_drizzle", "Fios Quentes de Nutella", 5.00, "🌰"),
    ToppingOption("top_flowers", "Flores Comestíveis Delicadas", 6.00, "🌸"),
    ToppingOption("top_gold_glitter", "Glitter Culinário Dourado", 3.00, "✨"),
    ToppingOption("top_brigadeiros_mini", "Mini Brigadeiros no Topo (4 un)", 7.00, "🧁"),
  )

  val cakeSizes = listOf(
    SizeOption("size_bento", "Bento Cake (10cm)", "Serve 2 a 3 pessoas (400g)", 1.0, 0.0),
    SizeOption("size_medium", "Bolo Médio (15cm)", "Serve 10 a 12 pessoas (1.5kg)", 1.8, 42.0),
    SizeOption("size_large", "Bolo Festa (20cm)", "Serve 20 a 25 pessoas (2.5kg)", 2.8, 88.0),
  )

  val brigadeiroSizes = listOf(
    SizeOption("box_4", "Caixa Degustação 4 Doces", "Ideal para presentear ou saborear sozinho", 1.0, 0.0),
    SizeOption("box_8", "Caixa Mimo 8 Doces", "Perfeita para compartilhar com afeto", 1.9, 14.0),
    SizeOption("box_12", "Caixa Luxo 12 Doces", "Seleção especial com laço de cetim", 2.8, 28.0),
    SizeOption("box_20", "Caixa Celebração 20 Doces", "Para momentos doces e inesquecíveis", 4.2, 49.0),
  )

  val cupcakeSizes = listOf(
    SizeOption("cup_1", "Individual (1 unidade)", "Embalagem individual transparente", 1.0, 0.0),
    SizeOption("cup_2", "Dupla de Cupcakes (2 unidades)", "Presente clássico em caixinha", 1.9, 8.0),
    SizeOption("cup_4", "Caixa Especial (4 unidades)", "Sortidos para degustação", 3.6, 20.0),
  )

  val initialSweetItems = listOf(
    SweetItem(
      id = "cake_custom_festivo",
      name = "Bolo Festivo Personalizado",
      category = Category.CAKES,
      description = "Crie seu bolo dos sonhos! Escolha a massa fofinha, até 2 recheios artesanais, cobertura e a frase personalizada de afeto.",
      basePrice = 45.00,
      imageRes = R.drawable.img_bolo,
      isCustomizable = true,
      tags = listOf("Mais Pedido", "Personalizável", "Artesanal"),
      availableSizes = cakeSizes,
      availableDoughs = defaultDoughOptions,
      availableFillings = defaultFillingOptions,
      availableFrostings = defaultFrostingOptions,
      availableToppings = defaultToppings,
      maxFillingsAllowed = 2,
      allowsCustomMessage = true,
    ),
    SweetItem(
      id = "brigadeiro_box_gourmet",
      name = "Caixa de Brigadeiros Gourmet",
      category = Category.BRIGADEIROS,
      description = "Brigadeiros artesanais enrolados à mão com confeitos nobres. Escolha os sabores e personalize a dedicatória.",
      basePrice = 18.00,
      imageRes = R.drawable.img_brigadeiros,
      isCustomizable = true,
      tags = listOf("Cacau 100% Nobre", "Feito com Afeto"),
      availableSizes = brigadeiroSizes,
      availableDoughs = emptyList(),
      availableFillings = listOf(
        FillingOption("brig_trad", "Tradicional Belga", "Chocolate ao leite 33% Callebaut"),
        FillingOption("brig_ninho_nutella", "Ninho com Nutella", "Massa de Ninho com coração de Nutella pura"),
        FillingOption("brig_pistache", "Pistache Supremo", "Pasta pura de pistache e pedacinhos crocantes"),
        FillingOption("brig_moranguinho", "Bicho de Pé Morango", "Sabor nostálgico com açúcar cristal rosa"),
        FillingOption("brig_churros", "Churros com Doce de Leite", "Canela e açúcar com doce de leite artesanal"),
      ),
      availableFrostings = emptyList(),
      availableToppings = listOf(
        ToppingOption("top_ribbon", "Laço de Cetim Rosa & Verde Edilicias", 3.00, "🎀"),
        ToppingOption("top_gift_tag", "Tag com Frase Escrita à Mão", 2.50, "💌"),
      ),
      maxFillingsAllowed = 4,
      allowsCustomMessage = true,
    ),
    SweetItem(
      id = "cupcake_gourmet_afeto",
      name = "Cupcake Artesanal Decorado",
      category = Category.CUPCAKES,
      description = "Massa super macia com recheio cremoso e cobertura em espiral de chantininho ou buttercream aromatizado.",
      basePrice = 12.50,
      imageRes = R.drawable.img_cupcakes,
      isCustomizable = true,
      tags = listOf("Decoração Especial", "Massa Fresquinha"),
      availableSizes = cupcakeSizes,
      availableDoughs = defaultDoughOptions,
      availableFillings = defaultFillingOptions.take(4),
      availableFrostings = defaultFrostingOptions.take(3),
      availableToppings = defaultToppings.take(4),
      maxFillingsAllowed = 1,
      allowsCustomMessage = true,
    ),
    SweetItem(
      id = "cake_vulcao_ninho",
      name = "Bolo Vulcão Ninho & Nutella",
      category = Category.CAKES,
      description = "Massa de chocolate belga fofíssima com cascata generosa de brigadeiro de Ninho e Nutella aquecida.",
      basePrice = 52.00,
      imageRes = R.drawable.img_bolo,
      isCustomizable = true,
      tags = listOf("Explosão de Sabor", "Irresistível"),
      availableSizes = cakeSizes.take(2),
      availableDoughs = defaultDoughOptions.take(2),
      availableFillings = listOf(
        FillingOption("fill_ninho_vulc", "Vulcão Duplo de Ninho", "Creme sedoso transbordando"),
        FillingOption("fill_brigadeiro_vulc", "Vulcão Belga 50%", "Brigadeiro de panela cremoso"),
      ),
      availableFrostings = listOf(
        FrostingOption("frost_vulcao_especial", "Cascata de Brigadeiro", "Cobre o bolo inteiro generosamente"),
      ),
      availableToppings = defaultToppings,
      maxFillingsAllowed = 1,
      allowsCustomMessage = true,
    ),
    SweetItem(
      id = "taca_da_felicidade",
      name = "Taça da Felicidade Edilicias",
      category = Category.DESSERTS,
      description = "Sobremesa na taça com camadas de brownie chocolatudo, brigadeiro de colher, morangos frescos e chantininho.",
      basePrice = 26.00,
      imageRes = R.drawable.img_brigadeiros,
      isCustomizable = true,
      tags = listOf("Sobremesa do Dia", "Camadas Generosas"),
      availableSizes = listOf(
        SizeOption("taca_individual", "Taça Individual (350ml)", "Para matar a vontade de doce", 1.0, 0.0),
        SizeOption("taca_compartilhar", "Taça Família (800ml)", "Serve até 3 pessoas", 1.9, 18.0),
      ),
      availableDoughs = listOf(
        DoughOption("base_brownie", "Cubinhos de Brownie Intenso", "Casquinha crocante e interior denso"),
        DoughOption("base_cookie", "Massa de Cookie com Gotas de Chocolate", "Textura amanteigada deliciosa"),
      ),
      availableFillings = defaultFillingOptions.take(4),
      availableFrostings = defaultFrostingOptions.take(2),
      availableToppings = defaultToppings,
      maxFillingsAllowed = 2,
      allowsCustomMessage = false,
    ),
    SweetItem(
      id = "kit_bento_presente",
      name = "Kit Mimo: Bento Cake + 6 Doces",
      category = Category.KITS,
      description = "O presente perfeito! 1 Bento cake totalmente personalizado com frase especial acompanhado de 6 brigadeiros finos.",
      basePrice = 58.00,
      imageRes = R.drawable.img_bolo,
      isCustomizable = true,
      tags = listOf("Ideal para Presentear", "Com Embalagem Presente"),
      availableSizes = listOf(
        SizeOption("kit_padrao", "Kit Completo com Caixa Visor", "Acompanha vela palito e colher de bambu", 1.0, 0.0),
        SizeOption("kit_luxo", "Kit Luxo com Balão Personalizado", "Inclui mini balão metalizado e laço duplo", 1.3, 18.0),
      ),
      availableDoughs = defaultDoughOptions,
      availableFillings = defaultFillingOptions,
      availableFrostings = defaultFrostingOptions,
      availableToppings = defaultToppings,
      maxFillingsAllowed = 2,
      allowsCustomMessage = true,
    ),
  )

  val sweetItems = mutableStateListOf<SweetItem>().apply {
    addAll(initialSweetItems)
  }

  fun addItem(item: SweetItem) {
    sweetItems.add(0, item)
  }

  fun updateItem(updatedItem: SweetItem) {
    val index = sweetItems.indexOfFirst { it.id == updatedItem.id }
    if (index != -1) {
      sweetItems[index] = updatedItem
    }
  }

  fun deleteItem(itemId: String) {
    sweetItems.removeAll { it.id == itemId }
  }

  fun resetToDefaults() {
    sweetItems.clear()
    sweetItems.addAll(initialSweetItems)
  }
}
