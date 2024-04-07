# Booking App :houses:
This app simplifies the process of finding and booking apartments and homes. Whether you're a customer searching for a place to stay or a landlord looking to list your property, this app comes in hand.

### :bookmark: Application main menu:
1. User registration
2. Account management
3. View users
4. Delete user
5. Add property
6. Update property
7. Delete property
8. View properties
9. Add booking
10. See my bookings
11. Delete booking
12. Update booking

### :bookmark: Classes:
- User:
   - Customer
   - Landlord
- Property:
   - Apartment
   - House
- Account Balance
- Address
- Booking
- Payment

## Features

### :bookmark: For Customers:

1. **View Bookings:** View the list of bookings made.
2. **View Properties:** View the list of available properties (apartments/houses).
3. **Booking Process:**
    - Select a property.
    - Make a payment (create Payment) – enter the amount, validate payment.
    - Register Booking if payment successful.
4. **Account Management:**
    - Deposit money into the account (Account Balance) / withdraw money.
    - Delete Booking.
5. **Update Booking**

### :bookmark: For Landlords:

1. **List Properties:** View the list of properties.
2. **Update Property:** Edit property details such as price, address, etc.
3. **Add/Delete Property:** Add or delete properties from the list.
4. **Account Management:**
    - Deposit/withdraw money from Account Balance. :heavy_dollar_sign:
    - Account Balance will be updated, once a booking was made on landlord's property :heavy_dollar_sign:
    - Update user details      

### :bookmark: Default:

1. **View Menu:** View the available options.
2. **View Users:** View the list of users, categorized as customers or landlords.
3. **Delete Account:** Delete user account.
4. **Create User:** Register as a customer or landlord.
5. **Log in (Optional):** Log into the created account to manage account details.

### :bookmark: UML diagram:

<img src="./diagrams/uml-booking.png">