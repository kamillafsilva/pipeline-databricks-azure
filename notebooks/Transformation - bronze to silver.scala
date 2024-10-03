// Databricks notebook source
import org.apache.spark.sql.functions.col

// COMMAND ----------

val MOUNT_NAME = "datalake"
val MOUNT_PATH = s"/mnt/$MOUNT_NAME"

val SOURCE_LAYER = "bronze"
val DESTINATION_LAYER = "silver"

// COMMAND ----------

val SOURCE_FILE = "dataset_building"
val PATH_SOURCE_FILE = s"dbfs:$MOUNT_PATH/$SOURCE_LAYER/$SOURCE_FILE"

// COMMAND ----------

val bronze_data = spark.read.format("delta").load(PATH_SOURCE_FILE)

// COMMAND ----------

val anuncio = bronze_data.select("anuncio.*").drop("endereco", "caracteristicas", "imagens")
val endereco = bronze_data.select("anuncio_id", "anuncio.endereco.*")

// COMMAND ----------

val ANUNCIO_FILE = "dataset_anuncio"
val ENDERECO_FILE = "dataset_endereco"

val ANUNCIO_PATH = s"dbfs:$MOUNT_PATH/$DESTINATION_LAYER/$ANUNCIO_FILE"
val ENDERECO_PATH = s"dbfs:$MOUNT_PATH/$DESTINATION_LAYER/$ENDERECO_FILE"

// COMMAND ----------

anuncio.write.format("delta").mode(SaveMode.Overwrite).save(ANUNCIO_PATH)
endereco.write.format("delta").mode(SaveMode.Overwrite).save(ENDERECO_PATH)
