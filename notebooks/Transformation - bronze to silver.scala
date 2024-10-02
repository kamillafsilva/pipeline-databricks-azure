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

val numCols = anuncio
  .withColumn("valores_size", size($"valores"))

// COMMAND ----------

df
  .select(
    (0 until numCols).map(i => $"letters".getItem(i).as(s"col$i")): _*
  )
  .show()

// COMMAND ----------

display(anuncio.limit(5))

// COMMAND ----------

display(endereco.limit(5))

// COMMAND ----------

val bronze_data = subset_data.withColumn("anuncio_id", col("anuncio.id"))

// COMMAND ----------

display(bronze_data.limit(5))

// COMMAND ----------

val BRONZE_FILE = "dataset_building"
val BRONZE_PATH = s"dbfs:$MOUNT_PATH/bronze/$BRONZE_FILE"

// COMMAND ----------

bronze_data.write.format("delta").mode(SaveMode.Overwrite).save(BRONZE_PATH)
