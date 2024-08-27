
# Custom CardView with Swipe and Drag-and-Drop in Android

This guide demonstrates how to create a custom CardView in Android that allows both swiping and drag-and-drop actions. We'll leverage a `RecyclerView` for efficient data management and utilize `ItemTouchHelper` for swipe gestures and drag-and-drop functionality.

In this guide, we will not focus much on how to create a RecyclerView. For that, you can refer to [**`RecyclerView`**](../3.RecyclerViewPractice/RecyclerViewPractice.md).

## Steps to Create a Custom CardView

### 1. Create the Custom Layout File for the CardView

Design a custom layout for the CardView items. This layout defines how each card should appear in the RecyclerView. Typically, you'll create an XML file that specifies the layout structure, including views like `ImageView`, `TextView`, and `CardView`.

[**`card_item.xml`**](./app/src/main/res/layout/custom_card_view.xml)

### 2. Create a Data Class for Defining the Structure for CardItem

Create a data class that defines the structure of your card item. This class will include properties like the title, description, and image resource ID, which will be used to populate each CardView.

[**`CardItem.kt`**](./app/src/main/java/com/example/mycardview/MainActivity.kt)

```kotlin
data class CardItem(
    val image: Int,
    val title: String,
    val description: String
)
```

### 3. Edit `build.gradle.kts (Module :app)` to Turn on View Binding

To enable View Binding in your project, you'll need to edit your module-level `build.gradle` file. Add the following line inside the `android` block:

[**`build.gradle.kts (Module :app)`**](./app/build.gradle.kts)

```kotlin
android {
    ...
    buildFeatures {
        viewBinding = true
    }
}
```
**Remember to `sync` the gradle file** 

After enabling View Binding, sync your project with Gradle files. This will generate binding classes for each XML layout file, allowing you to interact with views in a type-safe manner.

It will be helpful to use `viewBinding` as in cards, usually we will be storing multiple views to enhance the look and feel of the card.

### 4. Implement RecyclerView

Implement the RecyclerView in your activity by setting up a layout manager and linking it to your custom adapter. The RecyclerView will serve as the container for your CardView items.

[**`MyCardAdapter.kt`**](./app/src/main/java/com/example/mycardview/RecyclerViewUtils.kt)

[**`MainActivity.kt`**](./app/src/main/java/com/example/mycardview/MainActivity.kt)

For detailed guide on creating [**`RecyclerView`**](../3.RecyclerViewPractice/RecyclerViewPractice.md) click here


### Now that you have created a `RecyclerView` with `CustomCardView`, we will now implement swipe and drag-and-drop

## Steps to Add swipe and drag-and-drop

### 1. Create a `MyItemTouchHelperCallback` class

The MyItemTouchHelperCallback class will handle the swipe and drag-and-drop actions. This class should extend ItemTouchHelper.SimpleCallback and override the necessary methods to define what happens when an item is swiped or moved.

[**`MyItemTouchHelperCallback.kt`**](./app/src/main/java/com/example/mycardview/MyItemTouchHelperCallback.kt)

### 2. Modify the `MyCardAdapter` to Handle Item Movement and Dismissal

- `getItem` - This function returns the item at a specified position
- `onItemMove` - This function moves the items from previous position to specified position
- `onItemDismiss` - This function recieves the position and removes the item at that position
- `undoDelete` - This function takes removed item and position and puts it back to the place

[**`MyCardAdapter.kt`**](./app/src/main/java/com/example/mycardview/RecyclerViewUtils.kt)

### 3. Attach the `ItemTouchHelper` to the `RecyclerView`

After implementing the MyItemTouchHelperCallback class and configuring the adapter, the next step is to attach the ItemTouchHelper to the RecyclerView. This enables the swipe and drag-and-drop functionality.

[**`MainActivity.kt`**](./app/src/main/java/com/example/mycardview/MainActivity.kt)

### 4. (OPTIONAL) Add Undo delete functionality using snack bar

- Create an interface [**`SnackbarHandler`**](./app/src/main/java/com/example/mycardview/MyItemTouchHelperCallback.kt) - Define a method to handle the undo action.
- Implement `SnackbarHandler` in your activity 
[**`MainActivity.kt`**](./app/src/main/java/com/example/mycardview/MainActivity.kt) - Handle the undo action when the user clicks on the "Undo" button.
- Call the snackbar handler whenever an item is deleted - Pass the deleted item and its position to the snackbar handler.

### AND ALL SET!!!

You have successfully created a custom CardView in your Android application. This component allows you to efficiently display and interact with a list of cards, offering a visually appealing and organized way to present your data with swipe and drag-and-drop functionality. This component allows you to efficiently manage and interact with a list of cards, providing a dynamic and user-friendly experience.