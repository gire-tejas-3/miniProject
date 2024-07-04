package com.ecommerce.application;

import java.sql.SQLException;
import java.util.Scanner;

import com.ecommerce.exceptions.OrderServiceException;
import com.ecommerce.exceptions.ProductServiceException;
import com.ecommerce.exceptions.UserServiceException;
import com.ecommerce.interfaces.AdminServiceInterface;
import com.ecommerce.interfaces.CartServiceInterface;
import com.ecommerce.interfaces.GuestServiceInterface;
import com.ecommerce.interfaces.ProductServiceInterface;
import com.ecommerce.interfaces.UserServiceInterface;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.services.AdminService;
import com.ecommerce.services.CartServices;
import com.ecommerce.services.GuestService;
import com.ecommerce.services.ProductService;
import com.ecommerce.services.UserService;

public class EcomApplication {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		UserServiceInterface userService = new UserService();
		ProductServiceInterface productService = new ProductService();
		CartServiceInterface cartService = new CartServices();
		AdminServiceInterface adminService = new AdminService();
		GuestServiceInterface guestService = new GuestService();
		User currentUser = null;
		try {
			while (true) {
				System.out.println("Welcome to E-Commerce based application");

				System.out.println();

				System.out.println("User Operation");
				System.out.println("1. User Registration");
				System.out.println("2. User Login");
				System.out.println("3. User view Product item as Sorted Order");
				System.out.println("4. Buy Product");
				System.out.println("5. View Cart ");
				System.out.println("6. Purchase the item ");

				System.out.println();

				System.out.println("Admin Operation");
				System.out.println("7. Add product item");
				System.out.println("8. Calculate Bill");
				System.out.println("9. Display amount to End User");
				System.out.println("10.Check Quantity");
				System.out.println("11. Check registered user");
				System.out.println("12. Check the particular user history");

				System.out.println();

				System.out.println("Guest Operation");
				System.out.println("13. View product item");
				System.out.println("14. Not purchase item");

				System.out.println();

				System.out.println("Enter Your Choice (or type 0 to exit): ");

				int choice = scanner.nextInt();

				if (choice == 0) {
					break;
				}

				switch (choice) {
				case 1:
					System.out.println("Enter the First Name : ");
					String firstName = scanner.next();
					System.out.println("Enter the Last Name : ");
					String lastName = scanner.next();
					System.out.println("Enter the Username : ");
					String username = scanner.next();
					System.out.println("Enter the Password : ");
					String password = scanner.next();
					System.out.println("Enter the City : ");
					String mailId = scanner.next();
					System.out.println("Enter the Mail Id : ");
					String phone = scanner.next();
					System.out.println("Enter the Phone : ");
					String city = scanner.next();
					System.out.println("Enter Your Role(admin/user/guest) : ");
					String role = scanner.next();
					System.out.println();

					User user = new User(firstName, lastName, username, password, mailId, phone, city, role);
					userService.registerUser(user);
					break;

				case 2:
					System.out.println("Enter the Username : ");
					String uname = scanner.next();
					System.out.println("Enter the Password : ");
					String pass = scanner.next();
					currentUser = userService.loginUser(uname, pass);
					System.out.println("Current User: " + currentUser);
					break;

				case 3:

					for (Product product : productService.getAllProductsSorted()) {
						System.out.println(product);
					}
					break;

				case 4:
					try {
						if (currentUser != null && "user".equalsIgnoreCase(currentUser.getRole())) {
							System.out.println("Enter product ID:");
							int productId = scanner.nextInt();

							System.out.println("Enter quantity:");
							int quantity = scanner.nextInt();

							if (quantity <= 0) {
								throw new ProductServiceException("Quantity must be greater than zero.");
							}

							Product product = productService.getProductById(productId);
							System.out.println(product);
							if (product != null) {
								if (product.getQuantity() >= quantity) {
									cartService.addToCart(product, quantity);
								} else {
									throw new ProductServiceException("Insufficient product quantity.");
								}
							} else {
								throw new ProductServiceException("Product not found.");
							}
						} else {
							System.out.println("Only User can add product to cart.");
							throw new UserServiceException("Login first to Buy product");
						}
					} catch (ProductServiceException | UserServiceException e) {
						System.err.println(e.getMessage());
					}
					break;

				case 5:
					try {
						if (currentUser != null && "user".equalsIgnoreCase(currentUser.getRole())) {
							cartService.viewCart();
						} else {
							throw new UserServiceException("Only registered User can view cart");
						}

					} catch (ProductServiceException | UserServiceException e) {
						System.err.println(e.getMessage());
					}

					break;
				case 6:
					try {
						if (currentUser != null && "user".equalsIgnoreCase(currentUser.getRole())) {
							Order order = cartService.checkout(currentUser);
							if (order != null) {
								System.out.println("New Order Placed Successfully " + order);
							}
						} else {
							throw new UserServiceException("Only registered User can place order");
						}

					} catch (UserServiceException | SQLException e) {
						System.err.println(e.getMessage());
					}

					break;

				case 7:
					try {
						if (currentUser != null && "admin".equalsIgnoreCase(currentUser.getRole())) {
							System.out.println("Enter product name:");
							String productName = scanner.next();

							System.out.println("Enter product description:");
							String productDescription = scanner.next();

							System.out.println("Enter product price:");
							double productPrice = scanner.nextDouble();

							System.out.println("Enter product quantity:");
							int productQuantity = scanner.nextInt();

							Product product = new Product(0, productName, productDescription, productPrice,
									productQuantity);
							productService.addProduct(product);
						} else {
							throw new UserServiceException("Only Admins can add Product");
						}
					} catch (UserServiceException | SQLException e) {
						System.err.println(e.getMessage());
					}
					break;

				case 8:
					try {
						if (currentUser != null && "admin".equalsIgnoreCase(currentUser.getRole())) {
							adminService.calculateBill();
						} else {
							throw new UserServiceException("Only Admins can Calculate Bill");
						}
					} catch (UserServiceException e) {
						System.err.println(e.getMessage());
					}
					break;
				case 9:
					try {
						if (currentUser != null && "admin".equalsIgnoreCase(currentUser.getRole())) {
							adminService.displayBill();
						} else {
							throw new UserServiceException("Only Admins can Calculate Bill");
						}
					} catch (UserServiceException e) {
						System.err.println(e.getMessage());
					}
					break;

				case 10:
					try {
						if (currentUser != null && "admin".equalsIgnoreCase(currentUser.getRole())) {
							System.out.println("Enter Product: ");
							int productId = scanner.nextInt();
							int quantity = productService.checkProductQuantity(productId);
							System.out.println(quantity);

							if (quantity == 0) {
								throw new ProductServiceException("Product item is 0");
							}
						}
					} catch (ProductServiceException e) {
						System.err.println(e.getMessage());
					}
					break;

				case 11:
					try {
						if (currentUser != null && "admin".equalsIgnoreCase(currentUser.getRole())) {
							adminService.viewRegisteredUsers();
						}
					} catch (ProductServiceException e) {
						System.err.println(e.getMessage());
					}
					break;

				case 12:
					try {
						if (currentUser != null && "admin".equalsIgnoreCase(currentUser.getRole())) {
							System.out.println("Enter username: ");
							String usernames = scanner.next();
							adminService.viewUserHistory(usernames);
						}
					} catch (ProductServiceException e) {
						System.err.println(e.getMessage());
					}
					break;

				case 13:
					try {
						for (Product product : guestService.getAllProducts()) {
							System.out.println(product);
						}

					} catch (ProductServiceException e) {
						System.err.println(e.getMessage());
					}
					break;
				default:
					System.out.println("Invalid Choice Number");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
