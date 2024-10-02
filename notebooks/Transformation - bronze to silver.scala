// Databricks notebook source
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.functions.size
import org.apache.spark.sql.functions.max

// COMMAND ----------

// MAGIC %md
// MAGIC # Checking folder

// COMMAND ----------

val MOUNT_NAME = "datalake"
val MOUNT_PATH = s"/mnt/$MOUNT_NAME"

// COMMAND ----------

display(dbutils.fs.ls(s"$MOUNT_PATH/bronze"))

// COMMAND ----------

val FILE = "dataset_building"
val PATH_FILE = s"dbfs:$MOUNT_PATH/bronze/$FILE"

// COMMAND ----------

val bronze_data = spark.read.format("delta").load(PATH_FILE)

// COMMAND ----------

display(bronze_data.limit(5))

// COMMAND ----------

val anuncio = bronze_data.select("anuncio.*").drop("endereco", "caracteristicas", "imagens")
val endereco = bronze_data.select("anuncio_id", "anuncio.endereco.*")

// COMMAND ----------

display(anuncio.limit(5))

// COMMAND ----------

display(endereco.limit(5))

// COMMAND ----------

val ANUNCIO_FILE = "dataset_anuncio"
val ENDERECO_FILE = "dataset_endereco"

val ANUNCIO_PATH = s"dbfs:$MOUNT_PATH/bronze/$ANUNCIO_FILE"
val ENDERECO_PATH = s"dbfs:$MOUNT_PATH/bronze/$ENDERECO_FILE"

// COMMAND ----------

anuncio.write.format("delta").mode(SaveMode.Overwrite).save(ANUNCIO_PATH)
endereco.write.format("delta").mode(SaveMode.Overwrite).save(ENDERECO_PATH)
