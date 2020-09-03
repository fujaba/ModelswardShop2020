package modelswarddemo;

import org.fulib.builder.ClassModelDecorator;
import org.fulib.builder.ClassModelManager;
import org.fulib.classmodel.Clazz;

import static org.fulib.builder.Type.*;

public class GenModel implements ClassModelDecorator
{
	@Override
	public void decorate(ClassModelManager mm)
	{
		Clazz shop = mm.haveClass("Shop", c -> {
			c.attribute("name", STRING);
		});
		Clazz customer = mm.haveClass("Customer", c ->
		{
			c.attribute("customerId", STRING);
			c.attribute("name", STRING);
			c.attribute("address", STRING);
		});
		Clazz product = mm.haveClass("Product", c ->
		{
			c.attribute("productId", STRING);
			c.attribute("description", STRING);
			c.attribute("price", DOUBLE);
		});
		Clazz order = mm.haveClass("Order", c ->
		{
			c.attribute("orderId", STRING);
			c.attribute("date", STRING);
		});
		mm.associate(shop, "customers", MANY, customer, "shop", ONE);
		mm.associate(shop, "products", MANY, product, "shop", ONE);
		mm.associate(customer, "orders", MANY, order, "customer", ONE);
		mm.associate(order, "products", MANY, product, "orders", MANY);
	}
}
