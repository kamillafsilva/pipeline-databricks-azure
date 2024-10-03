// Databricks notebook source
import org.apache.spark.sql.functions.col

// COMMAND ----------

val MOUNT_NAME = "datalake"
val MOUNT_PATH = s"/mnt/$MOUNT_NAME"

val SOURCE_LAYER = "landing"
val DESTINATION_LAYER = "bronze"

// COMMAND ----------

val SOURCE_FILE = "dados_brutos_imoveis.json"
val PATH_SOURCE_FILE = s"dbfs:$MOUNT_PATH/$SOURCE_LAYER/$SOURCE_FILE"

// COMMAND ----------

val landing_data = spark.read.json(PATH_SOURCE_FILE)

// COMMAND ----------

val subset_data = landing_data.drop("imagens", "usuario")

// COMMAND ----------

val bronze_data = subset_data.withColumn("anuncio_id", col("anuncio.id"))

// COMMAND ----------

val DESTINATION_FILE = "dataset_building"
val PATH_DESTINATION_FILE = s"dbfs:$MOUNT_PATH/$DESTINATION_LAYER/$DESTINATION_FILE"

// COMMAND ----------

bronze_data.write.format("delta").mode(SaveMode.Overwrite).save(PATH_DESTINATION_FILE)
