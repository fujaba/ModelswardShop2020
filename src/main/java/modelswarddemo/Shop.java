package modelswarddemo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.Collection;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Shop
{
public static final String PROPERTY_name = "name";
private String name;
public static final String PROPERTY_customers = "customers";
private List<Customer> customers;
public static final String PROPERTY_products = "products";
private List<Product> products;
protected PropertyChangeSupport listeners;

public String getName()
   {
      return this.name;
   }

public Shop setName(String value)
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

public List<Customer> getCustomers()
   {
      return this.customers != null ? Collections.unmodifiableList(this.customers) : Collections.emptyList();
   }

public Shop withCustomers(Customer value)
   {
      if (this.customers == null)
      {
         this.customers = new ArrayList<>();
      }
      if (!this.customers.contains(value))
      {
         this.customers.add(value);
         value.setShop(this);
         this.firePropertyChange(PROPERTY_customers, null, value);
      }
      return this;
   }

public Shop withCustomers(Customer... value)
   {
      for (final Customer item : value)
      {
         this.withCustomers(item);
      }
      return this;
   }

public Shop withCustomers(Collection<? extends Customer> value)
   {
      for (final Customer item : value)
      {
         this.withCustomers(item);
      }
      return this;
   }

public Shop withoutCustomers(Customer value)
   {
      if (this.customers != null && this.customers.remove(value))
      {
         value.setShop(null);
         this.firePropertyChange(PROPERTY_customers, value, null);
      }
      return this;
   }

public Shop withoutCustomers(Customer... value)
   {
      for (final Customer item : value)
      {
         this.withoutCustomers(item);
      }
      return this;
   }

public Shop withoutCustomers(Collection<? extends Customer> value)
   {
      for (final Customer item : value)
      {
         this.withoutCustomers(item);
      }
      return this;
   }

public List<Product> getProducts()
   {
      return this.products != null ? Collections.unmodifiableList(this.products) : Collections.emptyList();
   }

public Shop withProducts(Product value)
   {
      if (this.products == null)
      {
         this.products = new ArrayList<>();
      }
      if (!this.products.contains(value))
      {
         this.products.add(value);
         value.setShop(this);
         this.firePropertyChange(PROPERTY_products, null, value);
      }
      return this;
   }

public Shop withProducts(Product... value)
   {
      for (final Product item : value)
      {
         this.withProducts(item);
      }
      return this;
   }

public Shop withProducts(Collection<? extends Product> value)
   {
      for (final Product item : value)
      {
         this.withProducts(item);
      }
      return this;
   }

public Shop withoutProducts(Product value)
   {
      if (this.products != null && this.products.remove(value))
      {
         value.setShop(null);
         this.firePropertyChange(PROPERTY_products, value, null);
      }
      return this;
   }

public Shop withoutProducts(Product... value)
   {
      for (final Product item : value)
      {
         this.withoutProducts(item);
      }
      return this;
   }

public Shop withoutProducts(Collection<? extends Product> value)
   {
      for (final Product item : value)
      {
         this.withoutProducts(item);
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
      result.append(' ').append(this.getName());
      return result.substring(1);
   }

public void removeYou()
   {
      this.withoutCustomers(new ArrayList<>(this.getCustomers()));
      this.withoutProducts(new ArrayList<>(this.getProducts()));
   }
}