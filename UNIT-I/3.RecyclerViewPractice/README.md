# Custom RecyclerView in Android

This guide provides a step-by-step approach to creating a custom RecyclerView in Android. Follow these steps to implement a custom RecyclerView using a custom adapter and layout.

## Steps to Create a Custom RecyclerView

### 1. Create the Custom Layout File for the RecyclerView Item

Design a custom layout for the RecyclerView items. This layout defines how each item should appear in the RecyclerView. You will typically create an XML file that specifies the layout structure, including any views such as ImageViews and TextViews.

[**list_item.xml**](app/src/main/res/layout/list_item.xml)

### 2. Add a RecyclerView to Your Activity's Layout

Add a RecyclerView to your activity’s layout XML file. This RecyclerView will serve as the container for displaying all your items. Make sure to specify its width and height, and provide it with an id so you can reference it in your activity code.

[**activity_main.xml**](app/src/main/res/layout/activity_main.xml)

### 3. Implement the Custom Recycler View Adapter

Create a file [**RecyclerViewUtils.kt**](app/src/main/java/com/example/recyclerviewpractice/RecyclerViewUtils.kt), which will contain all the classes and interfaces for your recycler view to work

#### Adapter & ViewHolder Setup

The custom adapter is a crucial component in a RecyclerView. It binds your data model to the RecyclerView, inflating the custom layout for each item and populating it with data.

**Steps to Create a Custom Adapter:**

1. **Create a Kotlin Class for Your Adapter:** [**MyAdapter**](app/src/main/java/com/example/recyclerviewpractice/RecyclerViewUtils.kt)
    - This class will extend `RecyclerView.Adapter` and use a custom `ViewHolder`.

2. **Define a ViewHolder:** [**MyViewHolder**](app/src/main/java/com/example/recyclerviewpractice/RecyclerViewUtils.kt)
    - Inside your RecyclerViewUtils.kt, define a class that extends `RecyclerView.ViewHolder`. The ViewHolder holds the views for each item and binds data to them.

3. **Override Adapter Methods:**
    - Override the following methods in your adapter:
        - `onCreateViewHolder`: Inflates the item layout and creates a ViewHolder instance.
        - `onBindViewHolder`: Binds data to the ViewHolder's views.
        - `getItemCount`: Returns the total number of items to display.

#### Click Listener Interface

To handle clicks on individual items, you need to define a click listener interface that your MainActivity will implement. This approach decouples the click handling from the adapter and ViewHolder, providing flexibility and reusability.

1. **Define an Interface:** [**MyItemClickListener**](app/src/main/java/com/example/recyclerviewpractice/RecyclerViewUtils.kt)
   - Create an interface in your adapter class (or a separate file) with a method like `onItemClick(androidVersion: AndroidVersion)`.

2. **Implement the Interface in MainActivity:** [**MainActivity**](app/src/main/java/com/example/recyclerviewpractice/MainActivity.kt)
   - Have your MainActivity implement the interface. Override the `onItemClick` method to define what happens when an item is clicked.

3. **Pass the Listener to the Adapter:**
   - Modify the adapter’s constructor to accept a listener object. Store this listener and use it to handle click events in the ViewHolder.

#### Using the Layout Manager

Setting up the layout manager is a critical step in defining how your RecyclerView displays items. The layout manager arranges your items in the RecyclerView and handles scrolling.

1. **LinearLayoutManager:**
   - This is the simplest and most commonly used layout manager. It arranges items linearly, either horizontally or vertically.
   - To use it, create an instance of `LinearLayoutManager` in your activity and set it to your RecyclerView. For example:
     ```kotlin
     recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
     ```
   - The first parameter is the context (usually `this` if in an Activity), the second is the orientation (`RecyclerView.VERTICAL` or `RecyclerView.HORIZONTAL`), and the third is whether the list is reversed.

2. **Other Layout Managers:**
   - **GridLayoutManager:** Use this to display items in a grid format. You can specify the number of columns and the orientation.
   - **StaggeredGridLayoutManager:** This manager displays items in a staggered grid format, where items can have varying heights or widths.

3. **Importance of Layout Manager:**
   - The layout manager determines how your data is laid out in the RecyclerView. Choosing the right layout manager can greatly affect the user experience and performance of your app.

By understanding the role of the adapter, the ViewHolder pattern, the click listener interface, and the layout manager, you can effectively create a flexible and powerful RecyclerView in your Android application.

### 4. Use the Adapter in Your Activity

In your activity, first create and populate a data source using your data class. Next, instantiate your custom adapter with this data source and set it to the RecyclerView. This will link your data with the RecyclerView and display the items using the custom layout.

[**MainActivity.kt**](app/src/main/java/com/example/recyclerviewpractice/MainActivity.kt)

### 5. (OPTIONAL) Define Click Listener

If you want to handle item clicks in the RecyclerView, set an OnClickListener for the RecyclerView items in Activity file. This listener allows you to perform actions based on user interactions with individual items, such as navigating to a new screen or showing a detailed view.

[**MainActivity.kt**](app/src/main/java/com/example/recyclerviewpractice/MainActivity.kt)

### AND ALL SET!

You have successfully created a custom RecyclerView in your Android application. This powerful component allows you to efficiently display and interact with a list of items.