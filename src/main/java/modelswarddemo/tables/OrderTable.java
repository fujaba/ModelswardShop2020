package modelswarddemo.tables;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import modelswarddemo.Customer;
import modelswarddemo.Order;
import modelswarddemo.Product;

public class OrderTable
{
   public static final String PROPERTY_table = "table";
   private List<List<Object>> table = new ArrayList<>();
   public static final String PROPERTY_columnName = "columnName";
   private String columnName;
   public static final String PROPERTY_columnMap = "columnMap";
   private Map<String,Integer> columnMap = new LinkedHashMap<>();

   public List<List<Object>> getTable()
   {
      return this.table;
   }

   public OrderTable setTable(List<List<Object>> value)
   {
      this.table = value;
      return this;
   }

   public int getColumn()
   {
      return this.columnMap.get(this.columnName);
   }

   public String getColumnName()
   {
      return this.columnName;
   }

   public OrderTable setColumnName(String value)
   {
      this.columnName = value;
      return this;
   }

   public Map<String,Integer> getColumnMap()
   {
      return this.columnMap;
   }

   public OrderTable setColumnMap(Map<String,Integer> value)
   {
      this.columnMap = value;
      return this;
   }

   public StringTable expandOrderId(String columnName)
   {
      int newColumnNumber = this.table.isEmpty() ? 0 : this.table.get(0).size();
      this.columnMap.put(columnName, newColumnNumber);

      int column = this.getColumn();

      for (List<Object> row : this.table)
      {
         Order start = (Order) row.get(column);
         row.add(start.getOrderId());
      }

      StringTable result = new StringTable();
      result.setColumnMap(this.columnMap);
      result.setColumnName(columnName);
      result.setTable(this.table);
      return result;
   }

   public StringTable expandDate(String columnName)
   {
      int newColumnNumber = this.table.isEmpty() ? 0 : this.table.get(0).size();
      this.columnMap.put(columnName, newColumnNumber);

      int column = this.getColumn();

      for (List<Object> row : this.table)
      {
         Order start = (Order) row.get(column);
         row.add(start.getDate());
      }

      StringTable result = new StringTable();
      result.setColumnMap(this.columnMap);
      result.setColumnName(columnName);
      result.setTable(this.table);
      return result;
   }

   public CustomerTable expandCustomer(String columnName)
   {
      int newColumnNumber = this.table.isEmpty() ? 0 : this.table.get(0).size();
      this.columnMap.put(columnName, newColumnNumber);

      int column = this.getColumn();
      for (List<Object> row : this.table)
      {
         Order start = (Order) row.get(column);
         row.add(start.getCustomer());
      }

      CustomerTable result = new CustomerTable();
      result.setColumnMap(this.columnMap);
      result.setTable(this.table);
      result.setColumnName(columnName);
      return result;
   }

   public OrderTable hasCustomer(CustomerTable rowName)
   {
      int column = this.getColumn();
      int otherColumn = rowName.getColumn();

      this.table.removeIf(row -> {
         Order start = (Order) row.get(column);
         Customer other = (Customer) row.get(otherColumn);
         return start.getCustomer() != other;
      });

      return this;
   }

   public ProductTable expandProducts(String columnName)
   {
      int newColumnNumber = this.table.isEmpty() ? 0 : this.table.get(0).size();
      this.columnMap.put(columnName, newColumnNumber);

      int column = this.getColumn();
      List<List<Object>> oldTable = new ArrayList<>(this.table);
      this.table.clear();
      for (List<Object> row : oldTable)
      {
         Order start = (Order) row.get(column);
         for (Product current : start.getProducts())
         {
            List<Object> newRow = new ArrayList<>(row);
            newRow.add(current);
            this.table.add(newRow);
         }
      }
      ProductTable result = new ProductTable();
      result.setColumnMap(this.columnMap);
      result.setTable(this.table);
      result.setColumnName(columnName);
      return result;
   }

   public OrderTable hasProducts(ProductTable rowName)
   {
      int column = this.getColumn();
      int otherColumn = rowName.getColumn();

      this.table.removeIf(row -> {
         Order start = (Order) row.get(column);
         Product other = (Product) row.get(otherColumn);
         return !start.getProducts().contains(other);
      });

      return this;
   }

   public OrderTable selectColumns(String... columnNames)
   {
      Map<String, Integer> oldColumnMap = new LinkedHashMap<>(this.columnMap);
      this.columnMap.clear();

      for (int i = 0; i < columnNames.length; i++)
      {
         String name = columnNames[i];
         if (oldColumnMap.get(name) == null)
         {
            throw new IllegalArgumentException("unknown column name: " + name);
         }
         this.columnMap.put(name, i);
      }

      List<List<Object>> oldTable = new ArrayList<>(this.table);
      this.table.clear();

      Set<List<Object>> rowSet = new HashSet<>();
      for (List<Object> row : oldTable)
      {
         List<Object> newRow = new ArrayList<>();
         for (String name : columnNames)
         {
            Object value = row.get(oldColumnMap.get(name));
            newRow.add(value);
         }
         if (rowSet.add(newRow))
         {
            this.table.add(newRow);
         }
      }

      return this;
   }

   public OrderTable dropColumns(String... columnNames)
   {
      Map<String, Integer> oldColumnMap = new LinkedHashMap<>(this.columnMap);
      this.columnMap.clear();

      Set<String> dropNames = new HashSet<>(Arrays.asList(columnNames));
      int i = 0;
      for (String name : oldColumnMap.keySet())
      {
         if (!dropNames.contains(name))
         {
            this.columnMap.put(name, i);
            i++;
         }
      }

      List<List<Object>> oldTable = new ArrayList<>(this.table);
      this.table.clear();

      Set<List<Object>> rowSet = new HashSet<>();
      for (List<Object> row : oldTable)
      {
         List<Object> newRow = new ArrayList<>();
         for (String name : this.columnMap.keySet())
         {
            Object value = row.get(oldColumnMap.get(name));
            newRow.add(value);
         }
         if (rowSet.add(newRow))
         {
            this.table.add(newRow);
         }
      }

      return this;
   }

   public void addColumn(String columnName, Function<? super Map<String, Object>, ?> function)
   {
      int newColumnNumber = this.table.isEmpty() ? 0 : this.table.get(0).size();
      for (List<Object> row : this.table)
      {
         Map<String, Object> map = new LinkedHashMap<>();
         for (Map.Entry<String, Integer> entry : this.columnMap.entrySet())
         {
            map.put(entry.getKey(), row.get(entry.getValue()));
         }
         Object result = function.apply(map);
         row.add(result);
      }
      this.columnMap.put(columnName, newColumnNumber);
   }

   public OrderTable filter(Predicate<? super Order> predicate)
   {
      int column = this.getColumn();
      this.table.removeIf(row -> {
         Order start = (Order) row.get(column);
         return !predicate.test(start);
      });
      return this;
   }

   public OrderTable filterRow(Predicate<? super Map<String, Object>> predicate)
   {
      this.table.removeIf(row -> {
         Map<String, Object> map = new LinkedHashMap<>();
         for (Map.Entry<String, Integer> entry : this.columnMap.entrySet())
         {
            map.put(entry.getKey(), row.get(entry.getValue()));
         }
         return !predicate.test(map);
      });
      return this;
   }

   public Set<Order> toSet()
   {
      int column = this.getColumn();
      Set<Order> result = new LinkedHashSet<>(this.table.size());
      for (List<?> row : this.table)
      {
         Order value = (Order) row.get(column);
         result.add(value);
      }
      return result;
   }

   public String toString()
   {
      StringBuilder buf = new StringBuilder();
      for (String key : this.columnMap.keySet())
      {
         buf.append(key).append(" \t");
      }
      buf.append("\n");
      for (List<?> row : this.table)
      {
         for (Object cell : row)
         {
            buf.append(cell).append(" \t");
         }
         buf.append("\n");
      }
      buf.append("\n");
      return buf.toString();
   }

   public OrderTable(Order... start)
   {
      this.setColumnName("Order");
      this.columnMap.put("Order", 0);
      for (Order current : start)
      {
         List<Object> row = new ArrayList<>();
         row.add(current);
         this.table.add(row);
      }
   }
}