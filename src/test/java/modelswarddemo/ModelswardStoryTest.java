package modelswarddemo;
import modelswarddemo.tables.CustomerTable;
import modelswarddemo.tables.OrderTable;
import modelswarddemo.tables.ProductTable;
import modelswarddemo.tables.doubleTable;
import org.fulib.FulibTables;
import org.fulib.FulibTools;
import org.fulib.patterns.PatternBuilder;
import org.fulib.patterns.PatternMatcher;
import org.fulib.patterns.model.PatternObject;
import org.fulib.tables.ObjectTable;
import org.fulib.tables.PathTable;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ModelswardStoryTest
{
   @Test
   public void testPattern()
   {
      Store uksShop = new Store().setName("UKSShop");
      Customer alice = new Customer().setCustomerId("alice1").setName("Alice")
            .setAddress("Wonderland 1").setShop(uksShop);
      Customer bob = new Customer().setCustomerId("bob2").setName("Bob")
            .setAddress("Wonderland 1").setShop(uksShop);
      Product tshirt = new Product().setProductId("t42")
            .setDescription("Great UKS Tshirt").setPrice(13.37).setShop(uksShop);
      Product mug = new Product().setProductId("m43")
            .setDescription("UKS Coffee Mug").setPrice(4.20).setShop(uksShop);
      Order o44 = new Order().setOrderId("o44").setCustomer(alice)
            .setDate("2020.09.02").withProducts(tshirt, mug);

      PatternBuilder pb = FulibTables.patternBuilder();
      PatternObject customer = pb.buildPatternObject("customer");
      PatternObject order = pb.buildPatternObject("order");
      PatternObject state = pb.buildPatternObject("state");
      PatternObject product = pb.buildPatternObject("product");
      PatternObject price = pb.buildPatternObject("price");
      pb.buildPatternLink(customer, Order.PROPERTY_customer,
            Customer.PROPERTY_orders, order);
      pb.buildPatternLink(order, null, "state", state);
      pb.buildAttributeConstraint(state, str -> str.equals("initial"));
      pb.buildPatternLink(order, Product.PROPERTY_orders,
            Order.PROPERTY_products, product);
      pb.buildPatternLink(product, "price", price);
      PatternMatcher matcher = FulibTables.matcher(pb.getPattern());
      ObjectTable table = matcher.match("customer", alice);
      System.out.println(table);
   }

   @Test
   public void testTableQueries()
   {
      Store uksShop = new Store().setName("UKSShop");
      Customer alice = new Customer().setCustomerId("alice1").setName("Alice")
            .setAddress("Wonderland 1").setShop(uksShop);
      Customer bob = new Customer().setCustomerId("bob2").setName("Bob")
            .setAddress("Wonderland 1").setShop(uksShop);
      Product tshirt = new Product().setProductId("t42")
            .setDescription("Great UKS Tshirt").setPrice(13.37).setShop(uksShop);
      Product mug = new Product().setProductId("m43")
            .setDescription("UKS Coffee Mug").setPrice(4.20).setShop(uksShop);
      Order o44 = new Order().setOrderId("o44").setCustomer(alice)
            .setDate("2020.09.02").withProducts(tshirt, mug);

      PathTable table = new PathTable("Customer", alice);
      table.expand("Customer", Customer.PROPERTY_orders, "Order");
      table.filter("Order", order -> ((Order)order).getState().equals("initial"));
      table.expand("Order", Order.PROPERTY_products, "Product");
      table.expand("Product", Product.PROPERTY_price, "Price");
      System.out.println(table);
      System.out.println("Total: " + table.sum("Price"));

      table.toSet("Product").forEach(product -> uksShop.retrieve((Product) product));

   }

   @Test
   public void testGeneratedQueries()
   {
      Store uksShop = new Store().setName("UKSShop");
      Customer alice = new Customer().setCustomerId("alice1").setName("Alice")
            .setAddress("Wonderland 1").setShop(uksShop);
      Customer bob = new Customer().setCustomerId("bob2").setName("Bob")
            .setAddress("Wonderland 1").setShop(uksShop);
      Product tshirt = new Product().setProductId("t42")
            .setDescription("Great UKS Tshirt").setPrice(13.37).setShop(uksShop);
      Product mug = new Product().setProductId("m43")
            .setDescription("UKS Coffee Mug").setPrice(4.20).setShop(uksShop);
      Order o44 = new Order().setOrderId("o44").setCustomer(alice)
            .setDate("2020.09.02").withProducts(tshirt, mug);

      CustomerTable customerTable = new CustomerTable(alice);
      OrderTable orderTable = customerTable.expandOrders("Order");
      orderTable.filter( order -> order.getState().equals("initial"));
      ProductTable productTable = orderTable.expandProducts("Product");
      doubleTable priceTable = productTable.expandPrice("Price");
      System.out.println(productTable);
      System.out.println("Total: " + priceTable.sum());

      productTable.toSet().forEach(product -> uksShop.retrieve(product));

   }

   @Test
   public void testPayPal()
   {
      Store uksShop = new Store().setName("UKSShop");
      Customer alice = new Customer().setCustomerId("alice1").setName("Alice")
            .setAddress("Wonderland 1").setShop(uksShop);
      Customer bob = new Customer().setCustomerId("bob2").setName("Bob")
            .setAddress("Wonderland 1").setShop(uksShop);
      Product tshirt = new Product().setProductId("t42")
            .setDescription("Great UKS Tshirt").setPrice(13.37).setShop(uksShop);
      Product mug = new Product().setProductId("m43")
            .setDescription("UKS Coffee Mug").setPrice(4.20).setShop(uksShop);
      Order o44 = new Order().setOrderId("o44").setCustomer(alice)
            .setDate("2020.09.02").withProducts(tshirt, mug);

      uksShop.process(o44);
      FulibTools.objectDiagrams().dumpSVG("paper/paypalObjects.svg", uksShop);
      // assertThat(o44.getState(), is("payed"));
   }

   @Test
   public void uniKasselShopStory()
   {
   }
}
