package modelswarddemo;
import java.util.Objects;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Collection;

public class Product
{
   public static final String PROPERTY_productId = "productId";
   private String productId;
   public static final String PROPERTY_description = "description";
   private String description;
   public static final String PROPERTY_price = "price";
   private double price;
   protected PropertyChangeSupport listeners;
   public static final String PROPERTY_orders = "orders";
   private List<Order> orders;
   public static final String PROPERTY_shop = "shop";
   private Shop shop;

   public String getProductId()
   {
      return this.productId;
   }

   public Product setProductId(String value)
   {
      if (Objects.equals(value, this.productId))
      {
         return this;
      }

      final String oldValue = this.productId;
      this.productId = value;
      this.firePropertyChange(PROPERTY_productId, oldValue, value);
      return this;
   }

   public String getDescription()
   {
      return this.description;
   }

   public Product setDescription(String value)
   {
      if (Objects.equals(value, this.description))
      {
         return this;
      }

      final String oldValue = this.description;
      this.description = value;
      this.firePropertyChange(PROPERTY_description, oldValue, value);
      return this;
   }

   public double getPrice()
   {
      return this.price;
   }

   public Product setPrice(double value)
   {
      if (value == this.price)
      {
         return this;
      }

      final double oldValue = this.price;
      this.price = value;
      this.firePropertyChange(PROPERTY_price, oldValue, value);
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
      result.append(' ').append(this.getProductId());
      result.append(' ').append(this.getDescription());
      return result.substring(1);
   }

   public List<Order> getOrders()
   {
      return this.orders != null ? Collections.unmodifiableList(this.orders) : Collections.emptyList();
   }

   public Product withOrders(Order value)
   {
      if (this.orders == null)
      {
         this.orders = new ArrayList<>();
      }
      if (!this.orders.contains(value))
      {
         this.orders.add(value);
         value.withProducts(this);
         this.firePropertyChange(PROPERTY_orders, null, value);
      }
      return this;
   }

   public Product withOrders(Order... value)
   {
      for (final Order item : value)
      {
         this.withOrders(item);
      }
      return this;
   }

   public Product withOrders(Collection<? extends Order> value)
   {
      for (final Order item : value)
      {
         this.withOrders(item);
      }
      return this;
   }

   public Product withoutOrders(Order value)
   {
      if (this.orders != null && this.orders.remove(value))
      {
         value.withoutProducts(this);
         this.firePropertyChange(PROPERTY_orders, value, null);
      }
      return this;
   }

   public Product withoutOrders(Order... value)
   {
      for (final Order item : value)
      {
         this.withoutOrders(item);
      }
      return this;
   }

   public Product withoutOrders(Collection<? extends Order> value)
   {
      for (final Order item : value)
      {
         this.withoutOrders(item);
      }
      return this;
   }

   public void removeYou()
   {
      this.setShop(null);
      this.withoutOrders(new ArrayList<>(this.getOrders()));
   }

   public Shop getShop()
   {
      return this.shop;
   }

   public Product setShop(Store value)
   {
      if (this.shop == value)
      {
         return this;
      }

      final Store oldValue = this.shop;
      if (this.shop != null)
      {
         this.shop = null;
         oldValue.withoutProducts(this);
      }
      this.shop = value;
      if (value != null)
      {
         value.withProducts(this);
      }
      this.firePropertyChange(PROPERTY_shop, oldValue, value);
      return this;
   }

public Product setShop(Shop value)
   {
      if (this.shop == value)
      {
         return this;
      }

      final Shop oldValue = this.shop;
      if (this.shop != null)
      {
         this.shop = null;
         oldValue.withoutProducts(this);
      }
      this.shop = value;
      if (value != null)
      {
         value.withProducts(this);
      }
      this.firePropertyChange(PROPERTY_shop, oldValue, value);
      return this;
   }
}