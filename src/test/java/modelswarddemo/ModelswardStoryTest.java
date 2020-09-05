package modelswarddemo;
import org.fulib.FulibTools;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ModelswardStoryTest
{
   @Test
   public void testPayPal()
   {
      Shop uksShop = new Shop().setName("UKSShop");
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
      assertThat(o44.getState(), is("payed"));
   }

   @Test
   public void uniKasselShopStory()
   {
   }
}