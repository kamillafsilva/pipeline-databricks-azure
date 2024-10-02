// Databricks notebook source
import org.apache.spark.sql.functions.col

// COMMAND ----------

// MAGIC %md
// MAGIC # Checking folder

// COMMAND ----------

val MOUNT_NAME = "datalake"
val MOUNT_PATH = s"/mnt/$MOUNT_NAME"

// COMMAND ----------

display(dbutils.fs.ls(s"$MOUNT_PATH/landing"))

// COMMAND ----------

val FILE = "dados_brutos_imoveis.json"
val PATH_FILE = s"dbfs:$MOUNT_PATH/landing/$FILE"

// COMMAND ----------

val landing_data = spark.read.json(PATH_FILE)

// COMMAND ----------

display(landing_data.limit(5))

// COMMAND ----------

val subset_data = landing_data.drop("imagens", "usuario")

// COMMAND ----------

display(subset_data.limit(5))

// COMMAND ----------

val bronze_data = subset_data.withColumn("anuncio_id", col("anuncio.id"))

// COMMAND ----------

display(bronze_data.limit(5))

// COMMAND ----------

val BRONZE_FILE = "dataset_building"
val BRONZE_PATH = s"dbfs:$MOUNT_PATH/bronze/$BRONZE_FILE"

// COMMAND ----------

bronze_data.write.format("delta").mode(SaveMode.Overwrite).save(BRONZE_PATH)
