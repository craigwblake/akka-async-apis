package info.gamlor.db

import org.adbcj.{Field, Value, Row}


/**
 * @author roman.stoffel@gamlor.info
 * @since 30.03.12
 */

class DBResultRow(val row: Row) extends Seq[Value]{
  def get(index: Int):Value = row.get(index)
  def get(columnName: String):Value = apply(columnName)
  def apply(columnName: String):Value = row.get(getFieldByName(columnName))
  def apply(index: Int):Value = row.get(index)
  def apply(field: Field):Value = row.get(field)

  def length = row.size()

  def iterator = {
    val fieldsIterator = row.getResultSet.getFields.iterator
    new Iterator[Value] {
      def hasNext = fieldsIterator.hasNext

      def next() = {
        val field = fieldsIterator.next()
        row.get(field)
      }
    }
  }

  private def getFieldByName(fieldName:String) = {
    row.getResultSet.getField(fieldName.toUpperCase) match{
      case f:Field => f
      case null => throw new IllegalArgumentException("Field '"+fieldName+"' does not exist")
    }
  }
}
