package com.empresa.civishop.backend.util;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.empresa.civishop.backend.Entity.Category;
import com.empresa.civishop.backend.Entity.Customer;
import com.empresa.civishop.backend.Entity.Order;
import com.empresa.civishop.backend.Entity.Product;
import com.empresa.civishop.backend.Repository.CategoryRepository;
import com.empresa.civishop.backend.Repository.CustomerRepository;
import com.empresa.civishop.backend.Repository.OrderRepository;
import com.empresa.civishop.backend.Repository.ProductRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() > 0) {
            System.out.println("THE DATADABE ALREADY CONTAINS INFORMATION. SKIPPING INITIALIZATION.");
            return;
        }

        System.out.println("====== LOADING DATA ======");

        // Create categories
        Category category1 = categoryRepository.save(new Category("Electronics"));
        Category category2 = categoryRepository.save(new Category("Home & Kitchen"));
        Category category3 = categoryRepository.save(new Category("Clothing"));

        //Create products
        Product product1 = productRepository.save(new Product("RGB Keyboard", "Gaming mechanical keyboard", 49.99, 10, category1, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fm.media-amazon.com%2Fimages%2FI%2F61MCVrX7qiL._AC_.jpg&f=1&nofb=1&ipt=d2e0d00c9e73d34b43788040a5fc933be69f40d6993fb71a77efc5949e567b83"));
        Product product2 = productRepository.save(new Product("Wireless Mouse", "Ergonomic 2.4GHz mouse", 25.50, 15, category1, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fimg.freepik.com%2Ffotos-premium%2Fraton-computadora-luz-colores_745528-1316.jpg&f=1&nofb=1&ipt=e419cf60dc9d5a2332c396a7fd626fd92c9b0efd067f52027cf5a634f901c43d"));
        Product product3 = productRepository.save(new Product("Coffee Maker", "Express programmable coffee machine", 89.99, 5, category2, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fm.media-amazon.com%2Fimages%2FI%2F71jJYt%2BniWL._AC_SL1500_.jpg&f=1&nofb=1&ipt=cba5b5865af89430cee13644b9eca7fb19f0eb400c6d178d02f46a5ecb237e6a"));
        Product product4 = productRepository.save(new Product("Blender", "High speed kitchen blender", 39.95, 8, category2, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fm.media-amazon.com%2Fimages%2FI%2F714AwxiuijL.jpg&f=1&nofb=1&ipt=6cc08e3a98c483160ffcacc970ee3646428f62e0b1aa2484186d1abf563ae790"));
        Product product5 = productRepository.save(new Product("Cotton T-Shirt", "100% organic cotton basic t-shirt", 15.00, 20, category3, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%2Fid%2FOIP.yaf4sOQCpJxfY38X9TUrtQHaIj%3Fr%3D0%26pid%3DApi&f=1&ipt=ce010dde0547d659911ab454b95c1394ddd3e1b6762e0a6d36a58976a04c8531&ipo=images"));
        Product product6 = productRepository.save(new Product("Desk Lamp", "LED desk lamp with wireless charger", 29.99, 12, category1, "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?q=80&w=1000&auto=format&fit=crop"));
        Product product7 = productRepository.save(new Product("Toaster", "2-slice stainless steel toaster", 34.50, 7, category2, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%2Fid%2FOIP.QJvb9XqBxCuhvJQCPlApvgHaHa%3Fr%3D0%26pid%3DApi&f=1&ipt=4a012de9594d87a375dfa81c36940997c7e550c2f5e34ff61ed31905fd522329&ipo=images"));
        Product product8 = productRepository.save(new Product("Running Shoes", "Lightweight breathable sports sneakers", 65.00, 14, category3, "https://images.unsplash.com/photo-1542291026-7eec264c27ff?q=80&w=1000&auto=format&fit=crop"));
        Product product9 = productRepository.save(new Product("Backpack", "Water-resistant laptop backpack", 45.90, 18, category3, "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?q=80&w=1000&auto=format&fit=crop"));
        Product product10 = productRepository.save(new Product("Water Bottle", "Insulated stainless steel sports bottle", 19.95, 25, category2, "https://images.unsplash.com/photo-1602143407151-7111542de6e8?q=80&w=1000&auto=format&fit=crop"));
        Product product11 = productRepository.save(new Product("Yoga Mat", "Non-slip eco-friendly exercise mat", 22.99, 30, category3, "https://images.unsplash.com/photo-1592432678016-e910b452f9a2?q=80&w=1000&auto=format&fit=crop"));
        Product product12 = productRepository.save(new Product("Smart Plug", "Wi-Fi smart outlet compatible with voice assistants", 18.50, 40, category1, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%2Fid%2FOIP.ZooCQ1eV6FKLqXJEUyn8UQHaFj%3Fr%3D0%26pid%3DApi&f=1&ipt=85e01f88800c0bf7885e0274b3783458b71397aa65b6cc06c4edf66f82da897c&ipo=images"));

        // Create cutomers
        //Admin
        Customer admin = new Customer();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEmail("admin@email.com");
        admin.setPhone(1);
        admin.setRegistrationDate(LocalDate.now());
        admin = customerRepository.save(admin);
        //Customer 1
        Customer customer1 = new Customer();
        customer1.setFirstName("Jonny");
        customer1.setLastName("Dee");
        customer1.setEmail("john.dee@email.com");
        customer1.setPhone(600123456);
        customer1.setRegistrationDate(LocalDate.now());
        customer1 = customerRepository.save(customer1);
        //Customer 2
        Customer customer2 = new Customer();
        customer2.setFirstName("Maik");
        customer2.setLastName("Jordan");
        customer2.setEmail("maik.jordan@email.com");
        customer2.setPhone(608423176);
        customer2.setRegistrationDate(LocalDate.now());
        customer2 = customerRepository.save(customer2);


        //Create orders
        //Order 1
        Order order1 = new Order();
        order1.setCustomer(customer1);
        order1.setOrderDate(LocalDate.now().minusDays(2)); 
        order1.setStatus("COMPLETED");
        order1.addProduct(product1, 2);
        product1.setStock(product1.getStock() - 2);
        productRepository.save(product1);
        orderRepository.save(order1);

        //Order 2
        Order order2 = new Order();
        order2.setCustomer(customer2);
        order2.setOrderDate(LocalDate.now());
        order2.setStatus("PENDING");
        order2.addProduct(product3, 1);
        order2.addProduct(product5, 1);
        product3.setStock(product3.getStock() - 1);
        product5.setStock(product5.getStock() - 1);
        productRepository.save(product5);
        productRepository.save(product5);
        orderRepository.save(order2);

        System.out.println("====== TEST DATA UPLOADED SUCCESSFULLY ======");
    }
}
