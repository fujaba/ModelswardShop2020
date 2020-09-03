package modelswarddemo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.Collection;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Order
{
   public static final String PROPERTY_orderId = "orderId";
   private String orderId;
   public static final String PROPERTY_date = "date";
   private String date;
   public static final String PROPERTY_customer = "customer";
   private Customer customer;
   protected PropertyChangeSupport listeners;
   public static final String PROPERTY_products = "products";
   private List<Product> products;

   public String getOrderId()
   {
      return this.orderId;
   }

   public Order setOrderId(String value)
   {
      if (Objects.equals(value, this.orderId))
      {
         return this;
      }

      final String oldValue = this.orderId;
      this.orderId = value;
      this.firePropertyChange(PROPERTY_orderId, oldValue, value);
      return this;
   }

   public String getDate()
   {
      return this.date;
   }

   public Order setDate(String value)
   {
      if (Objects.equals(value, this.date))
      {
         return this;
      }

      final String oldValue = this.date;
      this.date = value;
      this.firePropertyChange(PROPERTY_date, oldValue, value);
      return this;
   }

   public Customer getCustomer()
   {
      return this.customer;
   }

   public Order setCustomer(Customer value)
   {
      if (this.customer == value)
      {
         return this;
      }

      final Customer oldValue = this.customer;
      if (this.customer != null)
      {
         this.customer = null;
         oldValue.withoutOrders(this);
      }
      this.customer = value;
      if (value != null)
      {
         value.withOrders(this);
      }
      this.firePropertyChange(PROPERTY_customer, oldValue, value);
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
      result.append(' ').append(this.getOrderId());
      result.append(' ').append(this.getDate());
      return result.substring(1);
   }

   public void removeYou()
   {
      this.setCustomer(null);
      this.withoutProducts(new ArrayList<>(this.getProducts()));
   }

   public List<Product> getProducts()
   {
      return this.products != null ? Collections.unmodifiableList(this.products) : Collections.emptyList();
   }

   public Order withProducts(Product value)
   {
      if (this.products == null)
      {
         this.products = new ArrayList<>();
      }
      if (!this.products.contains(value))
      {
         this.products.add(value);
         value.withOrders(this);
         this.firePropertyChange(PROPERTY_products, null, value);
      }
      return this;
   }

   public Order withProducts(Product... value)
   {
      for (final Product item : value)
      {
         this.withProducts(item);
      }
      return this;
   }

   public Order withProducts(Collection<? extends Product> value)
   {
      for (final Product item : value)
      {
         this.withProducts(item);
      }
      return this;
   }

   public Order withoutProducts(Product value)
   {
      if (this.products != null && this.products.remove(value))
      {
         value.withoutOrders(this);
         this.firePropertyChange(PROPERTY_products, value, null);
      }
      return this;
   }

   public Order withoutProducts(Product... value)
   {
      for (final Product item : value)
      {
         this.withoutProducts(item);
      }
      return this;
   }

   public Order withoutProducts(Collection<? extends Product> value)
   {
      for (final Product item : value)
      {
         this.withoutProducts(item);
      }
      return this;
   }
}