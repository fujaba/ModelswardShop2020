package modelswarddemo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.Collection;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Customer
{
   public static final String PROPERTY_customerId = "customerId";
   private String customerId;
   public static final String PROPERTY_name = "name";
   private String name;
   public static final String PROPERTY_address = "address";
   private String address;
   public static final String PROPERTY_orders = "orders";
   private List<Order> orders;
   protected PropertyChangeSupport listeners;
   public static final String PROPERTY_shop = "shop";
   private Store shop;

   public String getCustomerId()
   {
      return this.customerId;
   }

   public Customer setCustomerId(String value)
   {
      if (Objects.equals(value, this.customerId))
      {
         return this;
      }

      final String oldValue = this.customerId;
      this.customerId = value;
      this.firePropertyChange(PROPERTY_customerId, oldValue, value);
      return this;
   }

   public String getName()
   {
      return this.name;
   }

   public Customer setName(String value)
   {
      if (Objects.equals(value, this.name))
      {
         return this;
      }

      final String oldValue = this.name;
      this.name = value;
      this.firePropertyChange(PROPERTY_name, oldValue, value);
      return this;
   }

   public String getAddress()
   {
      return this.address;
   }

   public Customer setAddress(String value)
   {
      if (Objects.equals(value, this.address))
      {
         return this;
      }

      final String oldValue = this.address;
      this.address = value;
      this.firePropertyChange(PROPERTY_address, oldValue, value);
      return this;
   }

   public List<Order> getOrders()
   {
      return this.orders != null ? Collections.unmodifiableList(this.orders) : Collections.emptyList();
   }

   public Customer withOrders(Order value)
   {
      if (this.orders == null)
      {
         this.orders = new ArrayList<>();
      }
      if (!this.orders.contains(value))
      {
         this.orders.add(value);
         value.setCustomer(this);
         this.firePropertyChange(PROPERTY_orders, null, value);
      }
      return this;
   }

   public Customer withOrders(Order... value)
   {
      for (final Order item : value)
      {
         this.withOrders(item);
      }
      return this;
   }

   public Customer withOrders(Collection<? extends Order> value)
   {
      for (final Order item : value)
      {
         this.withOrders(item);
      }
      return this;
   }

   public Customer withoutOrders(Order value)
   {
      if (this.orders != null && this.orders.remove(value))
      {
         value.setCustomer(null);
         this.firePropertyChange(PROPERTY_orders, value, null);
      }
      return this;
   }

   public Customer withoutOrders(Order... value)
   {
      for (final Order item : value)
      {
         this.withoutOrders(item);
      }
      return this;
   }

   public Customer withoutOrders(Collection<? extends Order> value)
   {
      for (final Order item : value)
      {
         this.withoutOrders(item);
      }
      return this;
   }

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (this.listeners != null)
      {
         this.listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public boolean addPropertyChangeListener(PropertyChangeListener listener)
   {
      if (this.listeners == null)
      {
         this.listeners = new PropertyChangeSupport(this);
      }
      this.listeners.addPropertyChangeListener(listener);
      return true;
   }

   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      if (this.listeners == null)
      {
         this.listeners = new PropertyChangeSupport(this);
      }
      this.listeners.addPropertyChangeListener(propertyName, listener);
      return true;
   }

   public boolean removePropertyChangeListener(PropertyChangeListener listener)
   {
      if (this.listeners != null)
      {
         this.listeners.removePropertyChangeListener(listener);
      }
      return true;
   }

   public boolean removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      if (this.listeners != null)
      {
         this.listeners.removePropertyChangeListener(propertyName, listener);
      }
      return true;
   }

   @Override
   public String toString()
   {
      final StringBuilder result = new StringBuilder();
      result.append(' ').append(this.getCustomerId());
      result.append(' ').append(this.getName());
      result.append(' ').append(this.getAddress());
      return result.substring(1);
   }

   public void removeYou()
   {
      this.setShop(null);
      this.withoutOrders(new ArrayList<>(this.getOrders()));
   }

   public Store getShop()
   {
      return this.shop;
   }

   public Customer setShop(Store value)
   {
      if (this.shop == value)
      {
         return this;
      }

      final Store oldValue = this.shop;
      if (this.shop != null)
      {
         this.shop = null;
         oldValue.withoutCustomers(this);
      }
      this.shop = value;
      if (value != null)
      {
         value.withCustomers(this);
      }
      this.firePropertyChange(PROPERTY_shop, oldValue, value);
      return this;
   }
}
