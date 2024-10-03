# Criando e orquestrando pipelines de dados usando Databricks + Azure

## Contexto
O stakeholder desse projeto possuí uma base de dados composta por vários anúncios de imvóveis no Rio de Janeiro. Esses dados brutos foram disponibilizados no formato JSON e precisam passar por processos de tranformação de dados para atender às necessidades dos analistas de negócio. Entre as transformações solicitadas estão a extração de informações aninhadas no JSON e a conversão dos arquivos para o formato Delta (Parquet). Além disso, novos dados brutos serão recebidos de hora em hora sendo necessária a automatização de todo esse processo.
A empresa em questão utiliza a cloud Microsoft Azure então é importante que a solução seja desenvolvida pensando nesse ecossistema.

## Desenho da solução
Do contexto acima identificamos que a solução para esse projeto deve conter os seguites recursos:
* Um local de armazenamento que aceite dados semiestruturados (JSON) e estruturados (Parquet). Também é importante que seja possível organizar esse armazenamento para separar dados brutos de dados transformados.
* Uma ferramenta onde seja possível ler/manipular arquivos JSON e salvar arquivos no formato Delta.
* Por fim, precisamos de um orquestrador para executar todas as etapas de leitura e transformação automáticamente a cada hora.

  ## Implementação
  Considerando que a cloud da empresa é a Microsoft Azure, podemos implentar o desenho da solução acima utilizando os seguintes recursos dessa plataforma:
  * Azure Data Lake Gen2: permite a construção e organização de um Data Lake para armazenamento de dados semiestruturados e estruturados. A organização é feita pelo mecanismo de [namespace hierárquico](https://learn.microsoft.com/pt-br/azure/storage/blobs/data-lake-storage-namespace) que possibilita uma hierarquia de diretórios e subdiretórios aninhados. Nesse projeto os subdiretórios correspondem as camadas do Data Lake: Landing, Bronze e Silver.
  * Databricks: possibilita a realização das etapas de leitura, manipulação e escrita dos arquivos de dados nas diferentes camadas do Data Lake do projeto. Essa plataforma se integra com a cloud Azure e nela podemos utilizar as linguagens Spark, Scala ou Python para realizar as trasformações solicitadas pelo stakeholder.
  * Data Factory: ferramenta de orquestração que permite o agendamento e execução automática dos notebooks do Databricks onde estão todas as etapas de transformação dos dados.
  
  
