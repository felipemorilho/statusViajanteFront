package br.com.empiricus.statusviajante.model

import br.com.empiricus.statusviajante.model.model.Viagem

object MockListaViagens {

    val listaViagem: List<Viagem> =
        listOf(
            Viagem(
                id = 1,
                nome = "Ferias Caribe",
                origem = "Rio de Janeiro",
                destino = "Caribe",
                dataInicio = "25/1/2023",
                dataFinal = "7/2/2023",
                orcamentoTotak = 15000.00,
                orcamentoDiario = 1363.00,
                quantidadeViajantes = 3,
                descricao = "Viagem de ferias"
            ),
            Viagem(
                id = 2,
                nome = "Reuniao do trabalho",
                origem = "Rio de Janeiro",
                destino = "São Paulo",
                dataInicio = "23/11/2022",
                dataFinal = "25/11/2022",
                orcamentoTotak = 2000.00,
                orcamentoDiario = 680.00,
                quantidadeViajantes = 1,
                descricao = "Reunião na sede da empresa"
            ),
            Viagem(
                id = 3,
                nome = "Tour Europa",
                origem = "Rio de Janeiro",
                destino = "Paris",
                dataInicio = "10/6/2022",
                dataFinal = "26/6/2022",
                orcamentoTotak = 20000.00,
                orcamentoDiario = 1250.00,
                quantidadeViajantes = 3,
                descricao = "Viagem de ferias pela Europa"
            ),
        )
}