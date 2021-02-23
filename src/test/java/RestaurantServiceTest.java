import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;

    //Refactoring repeated code lines
    // Refactoring -- adding a before each for setting up restaurant before each test
    @BeforeEach
    public void initializeRestaurantByName_AmeliasCafe() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }


    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {


        Assertions.assertEquals(restaurant.getName(),
                service.findRestaurantByName("Amelie's cafe").getName());

    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {

        Assertions.assertThrows(restaurantNotFoundException.class, () -> {
            service.findRestaurantByName("Shimla Biryani").getName();
        });

    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Adding Failing Test method below for TDD feature :Part3
    @Test
    public void return_order_value_when_items_are_passed_to_it() throws restaurantNotFoundException {

        restaurant= service.findRestaurantByName("Amelie's cafe");
        List<Item> items = restaurant.getMenu();
        int items_sumtotal = 0;
        for(Item item:items){
            items_sumtotal+=item.getValue();
        }
        Assertions.assertEquals(388, items_sumtotal);

    }

    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        Assertions.assertEquals(initialNumberOfRestaurants - 1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {


        Assertions.assertThrows(restaurantNotFoundException.class, () -> {
            service.removeRestaurant("Pantry d' or");
        });

    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1() {


        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales", "Chennai", LocalTime.parse("12:00:00"), LocalTime.parse("23:00:00"));
        Assertions.assertEquals(initialNumberOfRestaurants + 1, service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>


}