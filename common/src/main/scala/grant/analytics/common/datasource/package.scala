package grant.analytics.common

import org.apache.spark.sql.types._

import scalapb.descriptors.{Descriptor, FieldDescriptor, ScalaType}



package object datasource {
  // transform protocol buffer structures to spark sql StructType

  private def toSparkSQLType(fd:FieldDescriptor): DataType = {

    val basicType: DataType =
      fd.scalaType match {
        case ScalaType.Boolean => BooleanType
        case ScalaType.String => StringType
        case ScalaType.Int => IntegerType
        case ScalaType.Long => LongType
        case ScalaType.Double => DoubleType
        case ScalaType.Message(descriptor) => parseDescriptor(descriptor)
        case ScalaType.ByteString => BinaryType
        case ScalaType.Float => FloatType
        case _ => StringType
      }

    if(fd.isRepeated)
      ArrayType(basicType)
    else
      basicType
  }

  private def parseFieldDescriptor(fd: FieldDescriptor): StructField = {
    StructField(fd.name, toSparkSQLType(fd), fd.isOptional)
  }

  def parseDescriptor(descriptor: Descriptor): StructType = {
    StructType(descriptor.fields.map(parseFieldDescriptor(_)))
  }

}
